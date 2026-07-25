package com.takenokoshi.mekut.blockentity.abs;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.github.misosouptgit.mwgr.MekanismWaterGeneratorRebuild;
import com.takenokoshi.mekaddonlib.blockentity.component.EjectorComponentUtils;
import com.takenokoshi.mekaddonlib.blockentity.interfaces.IHasGuiSizeOffset;
import com.takenokoshi.mekut.blockentity.packet.IBurnRatePacketAcceptor;

import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.IContentsListener;
import mekanism.api.RelativeSide;
import mekanism.api.SerializationConstants;
import mekanism.api.chemical.BasicChemicalTank;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.energy.IEnergyContainer;
import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.api.heat.HeatAPI;
import mekanism.api.heat.HeatAPI.HeatTransfer;
import mekanism.api.heat.IHeatCapacitor;
import mekanism.api.math.MathUtils;
import mekanism.common.attachments.component.AttachedSideConfig;
import mekanism.common.attachments.component.AttachedSideConfig.LightConfigInfo;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.attachments.containers.chemical.ChemicalTanksBuilder;
import mekanism.common.attachments.containers.fluid.FluidTanksBuilder;
import mekanism.common.attachments.containers.item.ItemSlotsBuilder;
import mekanism.common.capabilities.chemical.VariableCapacityChemicalTank;
import mekanism.common.capabilities.energy.BasicEnergyContainer;
import mekanism.common.capabilities.fluid.VariableCapacityFluidTank;
import mekanism.common.capabilities.heat.BasicHeatCapacitor;
import mekanism.common.capabilities.heat.CachedAmbientTemperature;
import mekanism.common.capabilities.holder.chemical.ChemicalTankHelper;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import mekanism.common.capabilities.holder.energy.EnergyContainerHelper;
import mekanism.common.capabilities.holder.energy.IEnergyContainerHolder;
import mekanism.common.capabilities.holder.fluid.FluidTankHelper;
import mekanism.common.capabilities.holder.fluid.IFluidTankHolder;
import mekanism.common.capabilities.holder.heat.HeatCapacitorHelper;
import mekanism.common.capabilities.holder.heat.IHeatCapacitorHolder;
import mekanism.common.capabilities.holder.slot.IInventorySlotHolder;
import mekanism.common.capabilities.holder.slot.InventorySlotHelper;
import mekanism.common.config.value.CachedDoubleValue;
import mekanism.common.config.value.CachedIntValue;
import mekanism.common.config.value.CachedLongValue;
import mekanism.common.inventory.container.MekanismContainer;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.inventory.container.sync.SyncableDouble;
import mekanism.common.inventory.container.sync.SyncableLong;
import mekanism.common.inventory.slot.BasicInventorySlot;
import mekanism.common.inventory.slot.EnergyInventorySlot;
import mekanism.common.inventory.slot.chemical.ChemicalInventorySlot;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.registration.impl.ItemRegistryObject;
import mekanism.common.registries.MekanismChemicals;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.tile.component.config.ConfigInfo;
import mekanism.common.tile.component.config.DataType;
import mekanism.common.tile.component.config.slot.ChemicalSlotInfo;
import mekanism.common.tile.component.config.slot.HeatSlotInfo;
import mekanism.common.tile.prefab.TileEntityConfigurableMachine;
import mekanism.common.util.HeatUtils;
import mekanism.common.util.NBTUtils;
import mekanism.generators.common.GeneratorTags;
import mekanism.generators.common.config.MekanismGeneratorsConfig;
import mekanism.generators.common.registries.GeneratorsChemicals;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.Tags;

public abstract class BEAbstractCompactFusionReactor extends TileEntityConfigurableMachine
        implements IHasGuiSizeOffset, IBurnRatePacketAcceptor {

    protected static final double BURN_RATIO = 1.0d;
    // Thermal characteristics
    protected static final double PLASMA_HEAT_CAPACITY = 100.0d;
    protected static final double CASE_HEAT_CAPACITY = 1.0d;
    protected static final double INVERSE_INSULATION = 100_000.0d;
    // Heat transfer metrics
    protected static final double PLASMA_CASE_CONDUCTIVITY = 0.2d;

    public static final AttachedSideConfig SIDE_CONFIG = Util.make(() -> {
        Map<TransmissionType, LightConfigInfo> configInfo = new EnumMap<>(TransmissionType.class);
        configInfo.put(TransmissionType.CHEMICAL, AttachedSideConfig.LightConfigInfo.TWO_INPUT_AND_OUT);
        configInfo.put(TransmissionType.FLUID, AttachedSideConfig.LightConfigInfo.INPUT_ONLY);
        Map<RelativeSide, DataType> sideConfig = new EnumMap<>(RelativeSide.class);
        for (var side : RelativeSide.values()) {
            sideConfig.put(side, DataType.OUTPUT);
        }
        AttachedSideConfig.LightConfigInfo outputOnly = new AttachedSideConfig.LightConfigInfo(sideConfig, true);
        configInfo.put(TransmissionType.ENERGY, outputOnly);
        configInfo.put(TransmissionType.ITEM, AttachedSideConfig.LightConfigInfo.MACHINE);
        Map<RelativeSide, DataType> heatConfig = new EnumMap<>(RelativeSide.class);
        for (RelativeSide side : RelativeSide.values()) {
            heatConfig.put(side, DataType.INPUT_OUTPUT);
        }
        configInfo.put(TransmissionType.HEAT, new AttachedSideConfig.LightConfigInfo(heatConfig, true));
        return new AttachedSideConfig(configInfo);
    });

    public static Consumer<ItemRegistryObject<?>> getContainerAdder(long fuelTankCapacity) {
        return value -> {
            value.addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                    .addChemicalFillSlot(0)
                    .addChemicalFillSlot(1)
                    .addChemicalFillSlot(2)
                    .addBasic(1)
                    .addChemicalDrainSlot(3)
                    .addEnergy()
                    .build());
            value.addAttachmentOnlyContainers(ContainerType.FLUID, () -> FluidTanksBuilder.builder()
                    .addBasic(0x7fffffff)
                    .build());
            value.addAttachmentOnlyContainers(ContainerType.CHEMICAL, () -> ChemicalTanksBuilder.builder()
                    .addBasic(fuelTankCapacity)
                    .addBasic(fuelTankCapacity)
                    .addBasic(fuelTankCapacity)
                    .addBasic(Long.MAX_VALUE)
                    .build());
        };
    }

    protected IChemicalTank leftFuelTank, mixedFuelTank, rightFuelTank, steamTank;
    protected IExtendedFluidTank waterTank;

    protected IHeatCapacitor casingHeatCapacitor;

    protected IEnergyContainer energyContainer;

    protected ChemicalInventorySlot leftFuelSlot, mixedFuelSlot, rightFuelSlot, steamSlot;
    protected BasicInventorySlot waterSlot;
    protected EnergyInventorySlot energySlot;

    protected boolean shouldConsumeWater;

    protected final TagKey<Chemical> leftFuelTag, mixedFuelTag, rightFuelTag;

    protected final Holder<Chemical> mixedFuel, burningSteam, unburnedSteam;

    protected final double burnTemperature;

    protected final CachedIntValue waterPerInjection;
    protected final CachedLongValue steamPerInjection;

    protected final CachedLongValue energyPerFuel;
    protected final CachedDoubleValue waterHeatingRatio, casingThermalConductivity, thermocoupleEfficiency;

    protected double lastPlasmaTemperature;
    private double lastCaseTemperature;
    public double lastEnvironmentLoss;
    public double lastTransferLoss;

    protected long injectionRate;
    public double plasmaTemperature;

    protected int maxWater;
    protected long maxSteam;

    protected long lastEnergyGenerated;

    protected BEAbstractCompactFusionReactor(Holder<Block> blockProvider, BlockPos pos, BlockState state,
            TagKey<Chemical> leftFuelTag, TagKey<Chemical> mixedFuelTag, TagKey<Chemical> rightFuelTag,
            Holder<Chemical> mixedFuelHolder, Holder<Chemical> burningSteamHolder,
            Holder<Chemical> unburnedSteamHolder, double burnTemperature,
            CachedIntValue waterPerInjection, CachedLongValue steamPerInjection,
            CachedLongValue energyPerFuel, CachedDoubleValue waterHeatingRatio,
            CachedDoubleValue casingThermalConductivity, CachedDoubleValue thermocoupleEfficiency) {
        super(blockProvider, pos, state);
        this.leftFuelTag = leftFuelTag;
        this.mixedFuelTag = mixedFuelTag;
        this.rightFuelTag = rightFuelTag;
        this.mixedFuel = mixedFuelHolder;
        this.burningSteam = burningSteamHolder;
        this.unburnedSteam = unburnedSteamHolder;
        this.burnTemperature = burnTemperature;
        this.waterPerInjection = waterPerInjection;
        this.steamPerInjection = steamPerInjection;
        this.energyPerFuel = energyPerFuel;
        this.waterHeatingRatio = waterHeatingRatio;
        this.casingThermalConductivity = casingThermalConductivity;
        this.thermocoupleEfficiency = thermocoupleEfficiency;

        var chemicalInfo = configComponent.getConfig(TransmissionType.CHEMICAL);
        chemicalInfo.addSlotInfo(DataType.INPUT_1, new ChemicalSlotInfo(true, false, leftFuelTank));
        chemicalInfo.addSlotInfo(DataType.INPUT_2, new ChemicalSlotInfo(true, false, rightFuelTank));
        chemicalInfo.addSlotInfo(DataType.OUTPUT, new ChemicalSlotInfo(false, true, steamTank));
        chemicalInfo.addSlotInfo(DataType.INPUT_OUTPUT,
                new ChemicalSlotInfo(true, true, leftFuelTank, mixedFuelTank, rightFuelTank, steamTank));
        chemicalInfo.addSlotInfo(DataType.EXTRA, new ChemicalSlotInfo(true, false, mixedFuelTank));
        chemicalInfo.setCanEject(true);
        chemicalInfo.setEjecting(true);
        chemicalInfo.setDataType(DataType.INPUT_1, RelativeSide.LEFT);
        chemicalInfo.setDataType(DataType.INPUT_2, RelativeSide.RIGHT);
        chemicalInfo.setDataType(DataType.OUTPUT, RelativeSide.FRONT);
        configComponent.setupInputConfig(TransmissionType.FLUID, waterTank);
        configComponent.setupOutputConfig(TransmissionType.ENERGY, energyContainer, RelativeSide.values());
        configComponent.setupItemIOConfig(List.of(leftFuelSlot, mixedFuelSlot, rightFuelSlot, waterSlot),
                List.of(steamSlot), energySlot, true);
        ConfigInfo heatConfig = configComponent.getConfig(TransmissionType.HEAT);
        heatConfig.addSlotInfo(DataType.INPUT_OUTPUT, new HeatSlotInfo(true, true, List.of(casingHeatCapacitor)));
        ejectorComponent = new TileComponentEjector(this, () -> Long.MAX_VALUE, () -> 1, () -> Long.MAX_VALUE)
                .setOutputData(configComponent, TransmissionType.CHEMICAL, TransmissionType.ENERGY,
                        TransmissionType.ITEM);
        EjectorComponentUtils.setCanChemicalTankEject(ejectorComponent, (type, tank) -> {
            return type.canOutput() && tank == steamTank;
        });
        plasmaTemperature = ambientTemperature.getAsDouble();
        shouldConsumeWater = true;
    }

    protected BEAbstractCompactFusionReactor(Holder<Block> blockProvider, BlockPos pos, BlockState state,
            boolean fusionMarker) {
        this(blockProvider, pos, state,
                GeneratorTags.Chemicals.DEUTERIUM,
                GeneratorTags.Chemicals.FUSION_FUEL,
                GeneratorTags.Chemicals.TRITIUM,
                GeneratorsChemicals.FUSION_FUEL,
                MekanismChemicals.STEAM,
                MekanismChemicals.STEAM,
                100_000_000.0d,
                MekanismGeneratorsConfig.generators.fusionWaterPerInjection,
                MekanismGeneratorsConfig.generators.fusionSteamPerInjection,
                MekanismGeneratorsConfig.generators.energyPerFusionFuel,
                MekanismGeneratorsConfig.generators.fusionWaterHeatingRatio,
                MekanismGeneratorsConfig.generators.fusionCasingThermalConductivity,
                MekanismGeneratorsConfig.generators.fusionThermocoupleEfficiency);
    }

    @Override
    public @Nullable IChemicalTankHolder getInitialChemicalTanks(IContentsListener listener) {
        ChemicalTankHelper builder = ChemicalTankHelper.forSideWithConfig(this);
        builder.addTank(leftFuelTank = BasicChemicalTank.inputModern(initFuelTankCapacity(),
                stack -> stack.is(leftFuelTag), listener));
        builder.addTank(mixedFuelTank = BasicChemicalTank.inputModern(initFuelTankCapacity(),
                stack -> stack.is(mixedFuelTag) || stack.is(mixedFuel), listener));
        builder.addTank(rightFuelTank = BasicChemicalTank.inputModern(initFuelTankCapacity(),
                stack -> stack.is(rightFuelTag), listener));
        builder.addTank(steamTank = VariableCapacityChemicalTank.output(this::getMaxSteam, v -> true, listener));
        return builder.build();
    }

    protected abstract long initFuelTankCapacity();

    @Override
    protected @Nullable IEnergyContainerHolder getInitialEnergyContainers(IContentsListener listener) {
        EnergyContainerHelper builder = EnergyContainerHelper.forSideWithConfig(this);
        builder.addContainer(energyContainer = BasicEnergyContainer.create(initEnergyContainerCapacity(), listener));
        return builder.build();
    }

    protected abstract long initEnergyContainerCapacity();

    @Override
    protected @Nullable IFluidTankHolder getInitialFluidTanks(IContentsListener listener) {
        FluidTankHelper builder = FluidTankHelper.forSideWithConfig(this);
        builder.addTank(waterTank = VariableCapacityFluidTank.input(this::getMaxWater,
                stack -> stack.is(Tags.Fluids.WATER), listener));
        return builder.build();
    }

    @Override
    protected @Nullable IHeatCapacitorHolder getInitialHeatCapacitors(IContentsListener listener,
            CachedAmbientTemperature ambientTemperature) {
        HeatCapacitorHelper builder = HeatCapacitorHelper.forSideWithConfig(this);
        builder.addCapacitor(casingHeatCapacitor = BasicHeatCapacitor.create(CASE_HEAT_CAPACITY,
                initInverseConductionCoefficient(), INVERSE_INSULATION, ambientTemperature, listener));
        return builder.build();
    }

    protected double initInverseConductionCoefficient() {
        return 1 / MekanismGeneratorsConfig.generators.fusionCasingThermalConductivity.get();
    }

    @Override
    protected @Nullable IInventorySlotHolder getInitialInventory(IContentsListener listener) {
        InventorySlotHelper builder = InventorySlotHelper.forSideWithConfig(this);
        builder.addSlot(leftFuelSlot = ChemicalInventorySlot.fill(leftFuelTank, listener, 31, 95))
                .setSlotOverlay(SlotOverlay.MINUS);
        builder.addSlot(mixedFuelSlot = ChemicalInventorySlot.fill(mixedFuelTank, listener, 103, 93))
                .setSlotOverlay(SlotOverlay.MINUS);
        builder.addSlot(rightFuelSlot = ChemicalInventorySlot.fill(rightFuelTank, listener, 139, 95))
                .setSlotOverlay(SlotOverlay.MINUS);
        builder.addSlot(waterSlot = BasicInventorySlot.at(
                stack -> stack.is(MekanismWaterGeneratorRebuild.WATER_GENERATOR_ITEM), () -> {
                    listener.onContentsChanged();
                    this.shouldConsumeWater = waterSlot.isEmpty();
                }, 176 + 121, 115));
        builder.addSlot(steamSlot = ChemicalInventorySlot.drain(steamTank, listener, 176 + 147, 115))
                .setSlotOverlay(SlotOverlay.PLUS);
        builder.addSlot(energySlot = EnergyInventorySlot.drain(energyContainer, listener, 176 + 121, 29));
        return builder.build();
    }

    @Override
    public int getExtraWidth() {
        return 176;
    }

    @Override
    public int getExtraHeight() {
        return 56;
    }

    @Override
    protected boolean onUpdateServer() {
        boolean needsPacket = super.onUpdateServer();

        leftFuelSlot.fillTank();
        rightFuelSlot.fillTank();
        mixedFuelSlot.fillTank();

        steamSlot.drainTank();
        energySlot.drainContainer();

        injectFuel();
        if (canFunction()) {
            burnFuel();
        }
        transferHeat();
        updateTemperatures();
        return needsPacket;
    }

    protected void injectFuel() {
        if (!mixedFuelTank.isEmpty() && !mixedFuelTank.isTypeEqual(mixedFuel)) {
            return;
        }
        long toInject = Math.min(injectionRate >> 1, mixedFuelTank.getNeeded() >> 1);
        toInject = Math.min(toInject, leftFuelTank.getStored());
        toInject = Math.min(toInject, rightFuelTank.getStored());
        if (toInject < 1) {
            return;
        }
        if (mixedFuelTank.isEmpty()) {
            mixedFuelTank.setStack(new ChemicalStack(mixedFuel, toInject << 1));
        } else {
            mixedFuelTank.growStack(toInject << 1, Action.EXECUTE);
        }
        leftFuelTank.shrinkStack(toInject, Action.EXECUTE);
        rightFuelTank.shrinkStack(toInject, Action.EXECUTE);
    }

    protected long burnFuel() {
        long fuelBurned = MathUtils.clampToLong(
                Mth.clamp((lastPlasmaTemperature - burnTemperature) * BURN_RATIO, 0, mixedFuelTank.getStored()));
        if (fuelBurned < 1) {
            setActive(false);
            return 0;
        }
        mixedFuelTank.shrinkStack(fuelBurned, Action.EXECUTE);
        setPlasmaTemp(
                getPlasmaTemp() + (MathUtils.multiplyClamped(energyPerFuel.get(), fuelBurned) / PLASMA_HEAT_CAPACITY));
        setActive(true);
        return fuelBurned;
    }

    protected void transferHeat() {
        // Transfer from plasma to casing
        double plasmaCaseHeat = PLASMA_CASE_CONDUCTIVITY * (lastPlasmaTemperature - lastCaseTemperature);
        if (Math.abs(plasmaCaseHeat) > HeatAPI.EPSILON) {
            setPlasmaTemp(getPlasmaTemp() - plasmaCaseHeat / PLASMA_HEAT_CAPACITY);
            casingHeatCapacitor.handleHeat(plasmaCaseHeat);
        }

        // Transfer from casing to water if necessary
        double caseWaterHeat = waterHeatingRatio.get() * (lastCaseTemperature - ambientTemperature.getAsDouble());
        if (Math.abs(caseWaterHeat) > HeatAPI.EPSILON) {
            long waterToVaporize = MathUtils.clampToLong(
                    (HeatUtils.getSteamEnergyEfficiency() * caseWaterHeat / HeatUtils.getWaterThermalEnthalpy()));
            waterToVaporize = Math.min(waterToVaporize, steamTank.getNeeded());
            if (shouldConsumeWater) {
                waterToVaporize = Math.min(waterToVaporize, waterTank.getFluidAmount());
            }
            if (waterToVaporize > 0) {
                if (shouldConsumeWater) {
                    waterTank.shrinkStack((int) waterToVaporize, Action.EXECUTE);
                }
                if (getActive()) {
                    steamTank.insert(new ChemicalStack(burningSteam, waterToVaporize), Action.EXECUTE,
                            AutomationType.INTERNAL);
                } else {
                    steamTank.insert(new ChemicalStack(unburnedSteam, waterToVaporize), Action.EXECUTE,
                            AutomationType.INTERNAL);
                }
                caseWaterHeat = waterToVaporize * HeatUtils.getWaterThermalEnthalpy()
                        / HeatUtils.getSteamEnergyEfficiency();
                casingHeatCapacitor.handleHeat(-caseWaterHeat);
            }

            HeatTransfer heatTransfer = simulate();
            lastEnvironmentLoss = heatTransfer.environmentTransfer();
            lastTransferLoss = heatTransfer.adjacentTransfer();

            // Passive energy generation
            double caseAirHeat = casingThermalConductivity.get()
                    * (lastCaseTemperature - ambientTemperature.getAsDouble());
            if (Math.abs(caseAirHeat) > HeatAPI.EPSILON) {
                casingHeatCapacitor.handleHeat(-caseAirHeat);
                lastEnergyGenerated = MathUtils.clampToLong(caseAirHeat * thermocoupleEfficiency.get());
                energyContainer.insert(lastEnergyGenerated, Action.EXECUTE, AutomationType.INTERNAL);
            } else {
                lastEnergyGenerated = 0L;
            }
        }

    }

    public void updateTemperatures() {
        lastPlasmaTemperature = getPlasmaTemp();
        lastCaseTemperature = casingHeatCapacitor.getTemperature();
    }

    public int getMaxWater() {
        return maxWater;
    }

    public long getMaxSteam() {
        return maxSteam;
    }

    public void setInjectionRate(long injectionRate) {
        this.injectionRate = Math.min((injectionRate >> 1) << 1, (mixedFuelTank.getCapacity() >> 1) << 1);
        this.maxWater = MathUtils.clampToInt(1.0d * injectionRate * waterPerInjection.get());
        this.maxSteam = MathUtils.clampToLong(1.0d * injectionRate * steamPerInjection.getAsLong());
        waterTank.setStackSize(waterTank.getFluidAmount(), Action.EXECUTE);
        steamTank.setStackSize(steamTank.getStored(), Action.EXECUTE);
    }

    public void setLastPlasmaTemp(double temp) {
        lastPlasmaTemperature = temp;
    }

    public double getLastPlasmaTemp() {
        return lastPlasmaTemperature;
    }

    public double getPlasmaTemp() {
        return plasmaTemperature;
    }

    public void setPlasmaTemp(double temp) {
        if (plasmaTemperature != temp) {
            plasmaTemperature = temp;
        }
    }

    public double getLastCaseTemp() {
        return lastCaseTemperature;
    }

    public double getCaseTemp() {
        return casingHeatCapacitor.getTemperature();
    }

    public long getInjectionRate() {
        return injectionRate;
    }

    public long getLastEnergyGenerated() {
        return lastEnergyGenerated;
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbtTags, @NotNull Provider provider) {
        super.saveAdditional(nbtTags, provider);
        nbtTags.putDouble(SerializationConstants.PLASMA_TEMP, lastPlasmaTemperature);
        nbtTags.putLong(SerializationConstants.INJECTION_RATE, injectionRate);
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag nbt, @NotNull Provider provider) {
        super.loadAdditional(nbt, provider);
        NBTUtils.setDoubleIfPresent(nbt, SerializationConstants.PLASMA_TEMP, this::setPlasmaTemp);
        if (nbt.contains(SerializationConstants.INJECTION_RATE, 4)) {
            setInjectionRate(nbt.getLong(SerializationConstants.INJECTION_RATE));
        } else {
            setInjectionRate(2L);// tank capacity initialization needed
        }
        updateTemperatures();
    }

    @Override
    public void addContainerTrackers(MekanismContainer container) {
        super.addContainerTrackers(container);
        container.track(SyncableDouble.create(this::getLastPlasmaTemp, this::setLastPlasmaTemp));
        container.track(SyncableDouble.create(this::getLastCaseTemp, v -> lastCaseTemperature = v));
        container.track(SyncableDouble.create(() -> lastEnvironmentLoss, v -> lastEnvironmentLoss = v));
        container.track(SyncableDouble.create(() -> lastTransferLoss, v -> lastTransferLoss = v));
        container.track(SyncableLong.create(this::getInjectionRate, v -> injectionRate = v));
        container.track(SyncableLong.create(this::getLastEnergyGenerated, v -> lastEnergyGenerated = v));
    }

    public IChemicalTank getLeftFuelTank() {
        return leftFuelTank;
    }

    public IChemicalTank getMixedFuelTank() {
        return mixedFuelTank;
    }

    public IChemicalTank getRightFuelTank() {
        return rightFuelTank;
    }

    public IExtendedFluidTank getWaterTank() {
        return waterTank;
    }

    public IChemicalTank getSteamTank() {
        return steamTank;
    }

    public IEnergyContainer getEnergyContainer() {
        return energyContainer;
    }

    @Override
    public void setBurnRate(long rate) {
        setInjectionRate(rate);
    }

}
