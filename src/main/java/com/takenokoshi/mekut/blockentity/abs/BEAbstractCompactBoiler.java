package com.takenokoshi.mekut.blockentity.abs;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekaddonlib.blockentity.component.EjectorComponentUtils;
import com.takenokoshi.mekaddonlib.blockentity.interfaces.IHasGuiSizeOffset;
import com.takenokoshi.mekut.inventory.slot.FluidFillOrSupplierSlot;

import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.IContentsListener;
import mekanism.api.RelativeSide;
import mekanism.api.chemical.BasicChemicalTank;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.datamaps.IMekanismDataMapTypes;
import mekanism.api.datamaps.chemical.attribute.HeatedCoolant;
import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.api.heat.IHeatCapacitor;
import mekanism.api.math.MathUtils;
import mekanism.common.attachments.component.AttachedSideConfig;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.attachments.containers.chemical.ChemicalTanksBuilder;
import mekanism.common.attachments.containers.fluid.FluidTanksBuilder;
import mekanism.common.attachments.containers.item.ItemSlotsBuilder;
import mekanism.common.capabilities.fluid.BasicFluidTank;
import mekanism.common.capabilities.heat.BasicHeatCapacitor;
import mekanism.common.capabilities.heat.CachedAmbientTemperature;
import mekanism.common.capabilities.holder.chemical.ChemicalTankHelper;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import mekanism.common.capabilities.holder.fluid.FluidTankHelper;
import mekanism.common.capabilities.holder.fluid.IFluidTankHolder;
import mekanism.common.capabilities.holder.heat.HeatCapacitorHelper;
import mekanism.common.capabilities.holder.heat.IHeatCapacitorHolder;
import mekanism.common.capabilities.holder.slot.IInventorySlotHolder;
import mekanism.common.capabilities.holder.slot.InventorySlotHelper;
import mekanism.common.config.MekanismConfig;
import mekanism.common.inventory.container.MekanismContainer;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.inventory.container.sync.SyncableDouble;
import mekanism.common.inventory.container.sync.SyncableLong;
import mekanism.common.inventory.slot.OutputInventorySlot;
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
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.Tags;

public abstract class BEAbstractCompactBoiler extends TileEntityConfigurableMachine implements IHasGuiSizeOffset {

    public static final AttachedSideConfig SIDE_CONFIG = Util.make(() -> {
        Map<TransmissionType, AttachedSideConfig.LightConfigInfo> configInfo = new EnumMap<>(TransmissionType.class);
        Map<RelativeSide, DataType> chemicalConfig = new EnumMap<>(RelativeSide.class);
        chemicalConfig.put(RelativeSide.FRONT, DataType.INPUT);
        chemicalConfig.put(RelativeSide.BACK, DataType.INPUT);
        chemicalConfig.put(RelativeSide.BOTTOM, DataType.INPUT);
        chemicalConfig.put(RelativeSide.LEFT, DataType.INPUT);
        chemicalConfig.put(RelativeSide.RIGHT, DataType.OUTPUT_1);
        chemicalConfig.put(RelativeSide.TOP, DataType.OUTPUT_2);
        configInfo.put(TransmissionType.CHEMICAL, new AttachedSideConfig.LightConfigInfo(chemicalConfig, true));
        configInfo.put(TransmissionType.FLUID, AttachedSideConfig.LightConfigInfo.INPUT_ONLY);
        Map<RelativeSide, DataType> heatConfig = new EnumMap<>(RelativeSide.class);
        for (RelativeSide side : RelativeSide.values()) {
            heatConfig.put(side, DataType.INPUT_OUTPUT);
        }
        configInfo.put(TransmissionType.HEAT, new AttachedSideConfig.LightConfigInfo(heatConfig, true));
        return new AttachedSideConfig(configInfo);
    });

    public static Consumer<ItemRegistryObject<?>> getContainerAdder(long heated, long steam, long cooled, int water) {
        return value -> {
            value.addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                    .addBasic(5)
                    .build());
            value.addAttachmentOnlyContainers(ContainerType.FLUID, () -> FluidTanksBuilder.builder()
                    .addBasic(water)
                    .build());
            value.addAttachmentOnlyContainers(ContainerType.CHEMICAL, () -> ChemicalTanksBuilder.builder()
                    .addBasic(heated)
                    .addBasic(steam)
                    .addBasic(cooled)
                    .build());
        };
    }

    public static final double CASING_HEAT_CAPACITY = 50;
    private static final double CASING_INVERSE_INSULATION_COEFFICIENT = 100_000;
    private static final double CASING_INVERSE_CONDUCTION_COEFFICIENT = 1;
    protected IChemicalTank heatedCoolantTank;
    protected IChemicalTank cooledCoolantTank;
    protected IChemicalTank steamTank;
    protected IExtendedFluidTank waterTank;

    protected FluidFillOrSupplierSlot fluidInputSlot;
    protected OutputInventorySlot fluidReturnSlot;
    protected ChemicalInventorySlot steamSlot;
    protected ChemicalInventorySlot heatedCoolantSlot;
    protected ChemicalInventorySlot cooledCoolantSlot;

    protected IHeatCapacitor heatCapacitor;

    protected boolean shouldConsumeWater;

    protected double lastEnvironmentLoss;
    protected long lastBoilRate;
    protected long lastMaxBoil;
    public final int superheatingElements;

    protected BEAbstractCompactBoiler(Holder<Block> blockProvider, BlockPos pos, BlockState state,
            int superheatingElements) {
        super(blockProvider, pos, state);
        ConfigInfo chemicalConfig = configComponent.getConfig(TransmissionType.CHEMICAL);
        chemicalConfig.addSlotInfo(DataType.INPUT, new ChemicalSlotInfo(true, false, List.of(heatedCoolantTank)));
        chemicalConfig.addSlotInfo(DataType.OUTPUT_1, new ChemicalSlotInfo(false, true, List.of(cooledCoolantTank)));
        chemicalConfig.addSlotInfo(DataType.INPUT_OUTPUT,
                new ChemicalSlotInfo(true, true, List.of(heatedCoolantTank, cooledCoolantTank)));
        chemicalConfig.addSlotInfo(DataType.OUTPUT_2, new ChemicalSlotInfo(false, true, List.of(steamTank)));
        chemicalConfig.setDataType(DataType.INPUT, RelativeSide.LEFT);
        chemicalConfig.setDataType(DataType.INPUT, RelativeSide.FRONT);
        chemicalConfig.setDataType(DataType.INPUT, RelativeSide.BACK);
        chemicalConfig.setDataType(DataType.INPUT, RelativeSide.BOTTOM);
        chemicalConfig.setDataType(DataType.OUTPUT_1, RelativeSide.RIGHT);
        chemicalConfig.setDataType(DataType.OUTPUT_2, RelativeSide.TOP);
        chemicalConfig.setCanEject(true);
        chemicalConfig.setEjecting(true);
        configComponent.setupInputConfig(TransmissionType.FLUID, waterTank);
        ConfigInfo heatConfig = configComponent.getConfig(TransmissionType.HEAT);
        heatConfig.addSlotInfo(DataType.INPUT_OUTPUT, new HeatSlotInfo(true, true, List.of(heatCapacitor)));
        ejectorComponent = new TileComponentEjector(this, () -> Long.MAX_VALUE).setOutputData(configComponent,
                new TransmissionType[] { TransmissionType.CHEMICAL, });
        EjectorComponentUtils.setCanChemicalTankEject(ejectorComponent, (dataType, tank) -> {
            if (dataType == DataType.OUTPUT_1 || dataType == DataType.INPUT_OUTPUT) {
                return tank == cooledCoolantTank;
            } else if (dataType == DataType.OUTPUT_2) {
                return tank == steamTank;
            } else {
                return false;
            }
        });
        fluidInputSlot.setSupplyingStackSetter((stack) -> {
            shouldConsumeWater = stack.isEmpty();
        });
        this.superheatingElements = superheatingElements;
    }

    @Override
    public @Nullable IChemicalTankHolder getInitialChemicalTanks(IContentsListener listener) {
        ChemicalTankHelper builder = ChemicalTankHelper.forSideWithConfig(this);
        builder.addTank(heatedCoolantTank = BasicChemicalTank.createModern(
                initHeatedCoolantTankCapacity(),
                (chemical) -> chemical.getData(IMekanismDataMapTypes.INSTANCE.heatedChemicalCoolant()) != null,
                listener));
        builder.addTank(steamTank = BasicChemicalTank.output(initSteamTankCapacity(), listener));
        builder.addTank(cooledCoolantTank = BasicChemicalTank.output(initCooledCoolantTankCapacity(), listener));
        return builder.build();
    }

    protected abstract long initHeatedCoolantTankCapacity();

    protected abstract long initSteamTankCapacity();

    protected abstract long initCooledCoolantTankCapacity();

    @Override
    protected @Nullable IFluidTankHolder getInitialFluidTanks(IContentsListener listener) {
        FluidTankHelper builder = FluidTankHelper.forSideWithConfig(this);
        builder.addTank(waterTank = BasicFluidTank.input(
                initFluidTankCapacity(),
                (stack) -> stack.is(Tags.Fluids.WATER),
                listener));
        return builder.build();
    }

    protected abstract int initFluidTankCapacity();

    @Override
    protected @Nullable IHeatCapacitorHolder getInitialHeatCapacitors(IContentsListener listener,
            CachedAmbientTemperature ambientTemperature) {
        HeatCapacitorHelper builder = HeatCapacitorHelper.forSideWithConfig(this);
        builder.addCapacitor(
                heatCapacitor = BasicHeatCapacitor.create(
                        CASING_HEAT_CAPACITY * 1_736.0d,
                        CASING_INVERSE_CONDUCTION_COEFFICIENT,
                        CASING_INVERSE_INSULATION_COEFFICIENT,
                        ambientTemperature,
                        listener));
        return builder.build();
    }

    @Override
    protected @Nullable IInventorySlotHolder getInitialInventory(IContentsListener listener) {
        InventorySlotHelper builder = InventorySlotHelper.forSideWithConfig(this);
        builder.addSlot(heatedCoolantSlot = ChemicalInventorySlot.fill(heatedCoolantTank, listener, 7, 56))
                .setSlotOverlay(SlotOverlay.MINUS);
        builder.addSlot(fluidInputSlot = FluidFillOrSupplierSlot.create(waterTank, listener, 27, 56))
                .setSlotOverlay(SlotOverlay.MINUS);
        builder.addSlot(fluidReturnSlot = OutputInventorySlot.at(listener, 53, 56));
        builder.addSlot(steamSlot = ChemicalInventorySlot.drain(steamTank, listener, 175, 56))
                .setSlotOverlay(SlotOverlay.PLUS);
        builder.addSlot(cooledCoolantSlot = ChemicalInventorySlot.drain(cooledCoolantTank, listener, 195, 56))
                .setSlotOverlay(SlotOverlay.PLUS);
        return builder.build();
    }

    @Override
    protected boolean onUpdateServer() {
        boolean needsPacket = super.onUpdateServer();
        heatedCoolantSlot.fillTank();
        fluidInputSlot.fillTank(fluidReturnSlot);
        steamSlot.drainTank();
        cooledCoolantSlot.drainTank();
        operate();
        return needsPacket;
    }

    protected void operate() {
        lastEnvironmentLoss = simulateEnvironment();
        handleCoolant();
        handleWater();
    }

    protected void handleCoolant() {
        if (heatedCoolantTank.isEmpty()) {
            return;
        }
        HeatedCoolant coolantType = heatedCoolantTank.getStack()
                .getData(IMekanismDataMapTypes.INSTANCE.heatedChemicalCoolant());
        if (coolantType == null) {
            return;
        }
        double portionToCool = coolantType.conductivity() * heatedCoolantTank.getStored();
        long toCool = Math.round(portionToCool * (1 - heatCapacitor.getTemperature() / coolantType.temperature()));
        ChemicalStack cooledCoolant = coolantType.cool(toCool);
        long amountCooled = toCool
                - cooledCoolantTank.insert(cooledCoolant, Action.EXECUTE, AutomationType.INTERNAL).getAmount();
        if (amountCooled > 0) {
            double heatEnergy = amountCooled * coolantType.thermalEnthalpy();
            heatCapacitor.handleHeat(heatEnergy);
            heatedCoolantTank.shrinkStack(amountCooled, Action.EXECUTE);
        }
    }

    protected void handleWater() {
        if (getTotalTemperature() < HeatUtils.BASE_BOIL_TEMP) {
            lastBoilRate = 0;
            lastMaxBoil = 0;
            setActive(false);
            return;
        }
        if (shouldConsumeWater && waterTank.isEmpty()) {
            lastBoilRate = 0;
            lastMaxBoil = 0;
            setActive(false);
            return;
        }
        double heatAvailable = getHeatAvailable();
        lastMaxBoil = Mth
                .lfloor(HeatUtils.getSteamEnergyEfficiency() * heatAvailable / HeatUtils.getWaterThermalEnthalpy());
        long amountToBoil = shouldConsumeWater ? Math.min(lastMaxBoil, waterTank.getFluidAmount()) : lastMaxBoil;
        amountToBoil = Math.min(amountToBoil, MathUtils.clampToInt(steamTank.getNeeded()));
        if (amountToBoil < 1) {
            lastBoilRate = 0;
            setActive(false);
            return;
        }
        if (shouldConsumeWater) {
            waterTank.shrinkStack((int) amountToBoil, Action.EXECUTE);
        }
        if (steamTank.isEmpty()) {
            steamTank.setStack(MekanismChemicals.STEAM.asStack(amountToBoil));
        } else {
            steamTank.growStack(amountToBoil, Action.EXECUTE);
        }
        heatCapacitor
                .handleHeat(-amountToBoil * HeatUtils.getWaterThermalEnthalpy() / HeatUtils.getSteamEnergyEfficiency());
        lastBoilRate = amountToBoil;
        setActive(true);
    }

    private double getHeatAvailable() {
        double heatAvailable = (heatCapacitor.getTemperature() - HeatUtils.BASE_BOIL_TEMP)
                * (heatCapacitor.getHeatCapacity() * MekanismConfig.general.boilerWaterConductivity.get());
        return Math.min(heatAvailable, MekanismConfig.general.superheatingHeatTransfer.get() * superheatingElements);
    }

    @Override
    public void addContainerTrackers(MekanismContainer container) {
        super.addContainerTrackers(container);
        container.track(SyncableDouble.create(this::getEnvironmentLoss, v -> lastEnvironmentLoss = v));
        container.track(SyncableLong.create(this::getMaxBoil, v -> lastMaxBoil = v));
        container.track(SyncableLong.create(this::getBoilRate, v -> lastBoilRate = v));
    }

    public double getEnvironmentLoss() {
        return lastEnvironmentLoss;
    }

    public long getMaxBoil() {
        return lastMaxBoil;
    }

    public long getBoilRate() {
        return lastBoilRate;
    }

    @Override
    public int getExtraWidth() {
        return 42;
    }

    public IChemicalTank getHeatedCoolantTank() {
        return heatedCoolantTank;
    }

    public IExtendedFluidTank getWaterTank() {
        return waterTank;
    }

    public IChemicalTank getSteamTank() {
        return steamTank;
    }

    public IChemicalTank getCooledCoolantTank() {
        return cooledCoolantTank;
    }

    public IHeatCapacitor getHeatCapacitor() {
        return heatCapacitor;
    }

}
