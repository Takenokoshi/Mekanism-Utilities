package com.takenokoshi.mekut.blockentity.abs;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;

import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.IContentsListener;
import mekanism.api.RelativeSide;
import mekanism.api.Upgrade;
import mekanism.api.chemical.BasicChemicalTank;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.energy.IEnergyContainer;
import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.api.math.MathUtils;
import mekanism.common.attachments.component.AttachedSideConfig;
import mekanism.common.attachments.component.AttachedSideConfig.LightConfigInfo;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.attachments.containers.chemical.ChemicalTanksBuilder;
import mekanism.common.attachments.containers.fluid.FluidTanksBuilder;
import mekanism.common.attachments.containers.item.ItemSlotsBuilder;
import mekanism.common.capabilities.energy.BasicEnergyContainer;
import mekanism.common.capabilities.fluid.BasicFluidTank;
import mekanism.common.capabilities.holder.chemical.ChemicalTankHelper;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import mekanism.common.capabilities.holder.energy.EnergyContainerHelper;
import mekanism.common.capabilities.holder.energy.IEnergyContainerHolder;
import mekanism.common.capabilities.holder.fluid.FluidTankHelper;
import mekanism.common.capabilities.holder.fluid.IFluidTankHolder;
import mekanism.common.capabilities.holder.slot.IInventorySlotHolder;
import mekanism.common.capabilities.holder.slot.InventorySlotHelper;
import mekanism.common.config.MekanismConfig;
import mekanism.common.inventory.container.MekanismContainer;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.inventory.container.sync.SyncableLong;
import mekanism.common.inventory.slot.EnergyInventorySlot;
import mekanism.common.inventory.slot.FluidInventorySlot;
import mekanism.common.inventory.slot.OutputInventorySlot;
import mekanism.common.inventory.slot.chemical.ChemicalInventorySlot;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.registration.impl.ItemRegistryObject;
import mekanism.common.registries.MekanismChemicals;
import mekanism.common.registries.MekanismFluids;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.tile.component.config.DataType;
import mekanism.common.tile.prefab.TileEntityConfigurableMachine;
import mekanism.generators.common.config.MekanismGeneratorsConfig;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;

public abstract class BEAbstractCompactIndustrialTurbine extends TileEntityConfigurableMachine {

    protected IExtendedFluidTank waterTank;
    protected IChemicalTank steamTank;
    protected IEnergyContainer energyContainer;

    protected long lastEnergyGenerated = 0l;

    protected final long lowerVolume;
    protected final long vents;

    private FluidInventorySlot fluidSlot;
    private OutputInventorySlot outputSlot;
    private ChemicalInventorySlot inputSlot;
    private EnergyInventorySlot energySlot;

    private boolean filterUpgradeInstalled = false;

    public static final AttachedSideConfig SIDE_CONFIG = Util.make(() -> {
        Map<TransmissionType, LightConfigInfo> configInfo = new EnumMap<>(TransmissionType.class);
        configInfo.put(TransmissionType.CHEMICAL, AttachedSideConfig.LightConfigInfo.INPUT_ONLY);
        Map<RelativeSide, DataType> sideConfig = new EnumMap<>(RelativeSide.class);
        for (var side : RelativeSide.values()) {
            sideConfig.put(side, DataType.OUTPUT);
        }
        AttachedSideConfig.LightConfigInfo outputOnly = new AttachedSideConfig.LightConfigInfo(sideConfig, true);
        configInfo.put(TransmissionType.ENERGY, outputOnly);
        configInfo.put(TransmissionType.FLUID, outputOnly);
        configInfo.put(TransmissionType.ITEM, AttachedSideConfig.LightConfigInfo.EXTRA_MACHINE);
        return new AttachedSideConfig(configInfo);
    });

    public static Consumer<ItemRegistryObject<?>> getContainerAdder(long chemicalTankCapacity, int fluidTankCapacity) {
        return (value) -> {
            value.addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                    .addChemicalFillSlot(0)
                    .addFluidDrainSlot(0)
                    .addOutput(1)
                    .addEnergy()
                    .build());
            value.addAttachmentOnlyContainers(ContainerType.CHEMICAL, () -> ChemicalTanksBuilder.builder()
                    .addBasic(chemicalTankCapacity)
                    .build());
            value.addAttachmentOnlyContainers(ContainerType.FLUID, () -> FluidTanksBuilder.builder()
                    .addBasic(fluidTankCapacity)
                    .build());
        };
    }

    protected BEAbstractCompactIndustrialTurbine(Holder<Block> blockProvider, BlockPos pos, BlockState state,
            long lowerVolume, long vents) {
        super(blockProvider, pos, state);
        this.lowerVolume = lowerVolume;
        this.vents = vents;
        configComponent.setupInputConfig(TransmissionType.CHEMICAL, steamTank);
        configComponent.setupOutputConfig(TransmissionType.ENERGY, energyContainer, RelativeSide.values());
        configComponent.setupOutputConfig(TransmissionType.FLUID, waterTank, RelativeSide.values());
        configComponent.setupItemIOExtraConfig(inputSlot, outputSlot, fluidSlot, energySlot);
        ejectorComponent = new TileComponentEjector(this, () -> Long.MAX_VALUE, () -> 0x7fffffff, () -> Long.MAX_VALUE)
                .setOutputData(configComponent, new TransmissionType[] { TransmissionType.ENERGY,
                        TransmissionType.FLUID, TransmissionType.ITEM, });
    }

    @Override
    public @Nullable IChemicalTankHolder getInitialChemicalTanks(IContentsListener listener) {
        ChemicalTankHelper builder = ChemicalTankHelper.forSideWithConfig(this);
        builder.addTank(steamTank = BasicChemicalTank.createModern(
                initChemicalTankCapacity(),
                (stack) -> stack.is(MekanismChemicals.STEAM),
                listener));
        return builder.build();
    }

    protected abstract long initChemicalTankCapacity();

    @Override
    protected @Nullable IEnergyContainerHolder getInitialEnergyContainers(IContentsListener listener) {
        EnergyContainerHelper builder = EnergyContainerHelper.forSideWithConfig(this);
        builder.addContainer(
                energyContainer = BasicEnergyContainer.output(initEnergyContainerCapacity(), listener));
        return builder.build();
    }

    protected abstract long initEnergyContainerCapacity();

    @Override
    protected @Nullable IFluidTankHolder getInitialFluidTanks(IContentsListener listener) {
        FluidTankHelper builder = FluidTankHelper.forSideWithConfig(this);
        builder.addTank(waterTank = BasicFluidTank.output(initFluidTankCapacity(), listener));
        return builder.build();
    }

    protected abstract int initFluidTankCapacity();

    @Override
    protected @Nullable IInventorySlotHolder getInitialInventory(IContentsListener listener) {
        InventorySlotHelper builder = InventorySlotHelper.forSideWithConfig(this);
        builder.addSlot(inputSlot = ChemicalInventorySlot.fill(steamTank, listener, 5, 56))
                .setSlotOverlay(SlotOverlay.MINUS);
        builder.addSlot(fluidSlot = FluidInventorySlot.drain(waterTank, listener, 155, 25))
                .setSlotOverlay(SlotOverlay.PLUS);
        builder.addSlot(outputSlot = OutputInventorySlot.at(listener, 155, 56));
        builder.addSlot(energySlot = EnergyInventorySlot.drain(energyContainer, listener, 155, 5));
        return builder.build();
    }

    @Override
    protected boolean onUpdateServer() {
        boolean needsPacket = super.onUpdateServer();
        fluidSlot.drainTank(outputSlot);
        inputSlot.fillTank();
        energySlot.drainContainer();
        long beforeEnergy = energyContainer.getEnergy();
        operate();
        lastEnergyGenerated = energyContainer.getEnergy() - beforeEnergy;
        return needsPacket;
    }

    protected void operate() {
        double energyMultiplier = MekanismConfig.general.maxEnergyPerSteam.get() * 13.0d / 14.0d;
        double rate = Math.min(
                lowerVolume * (224 * MekanismGeneratorsConfig.generators.turbineDisperserChemicalFlow.get()),
                vents * MekanismGeneratorsConfig.generators.turbineVentChemicalFlow.get());
        double proportion = 1d * steamTank.getStored() / steamTank.getCapacity();
        rate = Math.min(Math.min(rate, steamTank.getStored()), energyContainer.getNeeded() / energyMultiplier)
                * proportion;
        long flow = MathUtils.clampToLong(rate);
        if (flow > 0) {
            setActive(true);
            energyContainer.insert(MathUtils.clampToLong(energyMultiplier * flow),
                    Action.EXECUTE, AutomationType.INTERNAL);
            steamTank.shrinkStack(flow, Action.EXECUTE);
            waterTank.insert(filterUpgradeInstalled
                    ? MekanismFluids.HEAVY_WATER.asStack(MathUtils.clampToInt(rate / 100))
                    : new FluidStack(Fluids.WATER, MathUtils.clampToInt(rate)),
                    Action.EXECUTE, AutomationType.INTERNAL);
        } else {
            setActive(false);
        }

    }

    public long getEnergyGenerated() {
        return lastEnergyGenerated;
    }

    @Override
    public void recalculateUpgrades(Upgrade upgrade) {
        super.recalculateUpgrades(upgrade);
        if (upgrade == Upgrade.FILTER) {
            filterUpgradeInstalled = upgradeComponent.isUpgradeInstalled(Upgrade.FILTER);
        }
    }

    @Override
    public void addContainerTrackers(MekanismContainer container) {
        super.addContainerTrackers(container);
        container.track(SyncableLong.create(this::getEnergyGenerated, v -> lastEnergyGenerated = v));
    }

    public IEnergyContainer getEnergyContainer() {
        return energyContainer;
    }

    public IExtendedFluidTank getFluidTank() {
        return waterTank;
    }

    public IChemicalTank getChemicalTank() {
        return steamTank;
    }

}
