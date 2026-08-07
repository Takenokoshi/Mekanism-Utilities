package com.takenokoshi.mekut.blockentity.abs;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekaddonlib.blockentity.component.EjectorComponentUtils;
import com.takenokoshi.mekaddonlib.blockentity.interfaces.IHasGuiSizeOffset;
import com.takenokoshi.mekut.inventory.slot.FluidFillOrSupplierSlot;
import com.takenokoshi.mekut.recipe.input.AdvancedFluidInputHandler;

import mekanism.api.IContentsListener;
import mekanism.api.RelativeSide;
import mekanism.api.SerializationConstants;
import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.api.heat.HeatAPI;
import mekanism.api.heat.IHeatCapacitor;
import mekanism.api.math.MathUtils;
import mekanism.api.recipes.FluidToFluidRecipe;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.api.recipes.cache.OneInputCachedRecipe;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.outputs.IOutputHandler;
import mekanism.api.recipes.outputs.OutputHelper;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mekanism.client.recipe_viewer.type.RecipeViewerRecipeType;
import mekanism.common.attachments.component.AttachedSideConfig;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.attachments.containers.fluid.FluidTanksBuilder;
import mekanism.common.attachments.containers.item.ItemSlotsBuilder;
import mekanism.common.capabilities.fluid.BasicFluidTank;
import mekanism.common.capabilities.heat.BasicHeatCapacitor;
import mekanism.common.capabilities.heat.CachedAmbientTemperature;
import mekanism.common.capabilities.holder.fluid.FluidTankHelper;
import mekanism.common.capabilities.holder.fluid.IFluidTankHolder;
import mekanism.common.capabilities.holder.heat.HeatCapacitorHelper;
import mekanism.common.capabilities.holder.heat.IHeatCapacitorHolder;
import mekanism.common.capabilities.holder.slot.IInventorySlotHolder;
import mekanism.common.capabilities.holder.slot.InventorySlotHelper;
import mekanism.common.config.MekanismConfig;
import mekanism.common.integration.computer.annotation.ComputerMethod;
import mekanism.common.inventory.container.MekanismContainer;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.inventory.container.sync.SyncableDouble;
import mekanism.common.inventory.container.sync.SyncableInt;
import mekanism.common.inventory.slot.FluidInventorySlot;
import mekanism.common.inventory.slot.OutputInventorySlot;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.recipe.IMekanismRecipeTypeProvider;
import mekanism.common.recipe.MekanismRecipeType;
import mekanism.common.recipe.lookup.ISingleRecipeLookupHandler;
import mekanism.common.recipe.lookup.cache.InputRecipeCache;
import mekanism.common.registration.impl.ItemRegistryObject;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.tile.component.config.ConfigInfo;
import mekanism.common.tile.component.config.DataType;
import mekanism.common.tile.component.config.slot.HeatSlotInfo;
import mekanism.common.tile.component.config.slot.InventorySlotInfo;
import mekanism.common.tile.prefab.TileEntityRecipeMachine;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;

public abstract class BEAbstractCompactThermalEvaporationPlant extends TileEntityRecipeMachine<FluidToFluidRecipe>
        implements ISingleRecipeLookupHandler.FluidRecipeLookupHandler<FluidToFluidRecipe>, IHasGuiSizeOffset {

    public static final AttachedSideConfig SIDE_CONFIG = Util.make(() -> {
        Map<TransmissionType, AttachedSideConfig.LightConfigInfo> configInfo = new EnumMap<>(TransmissionType.class);
        configInfo.put(TransmissionType.FLUID, AttachedSideConfig.LightConfigInfo.OUT_EJECT);
        configInfo.put(TransmissionType.ITEM, AttachedSideConfig.LightConfigInfo.OUT_EJECT);
        configInfo.put(TransmissionType.HEAT, AttachedSideConfig.LightConfigInfo.INPUT_OUT_ALL);
        return new AttachedSideConfig(configInfo);
    });

    public static Consumer<ItemRegistryObject<?>> getContainerAdder(int tankCapacity) {
        return value -> {
            value.addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                    .addBasic(4)
                    .build());
            value.addAttachmentOnlyContainers(ContainerType.FLUID, () -> FluidTanksBuilder.builder()
                    .addBasic(tankCapacity)
                    .addBasic(tankCapacity)
                    .build());
        };
    }

    public static final List<RecipeError> TRACKED_ERROR_TYPES = List.of(
            RecipeError.NOT_ENOUGH_INPUT,
            RecipeError.NOT_ENOUGH_OUTPUT_SPACE,
            RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT);

    protected int operatingTicks = 1;
    protected int ticksRequired = 1;
    protected int operationsPerTick = 0;
    protected double operationsPerTickD = 0.0d;
    protected double lastEnvironmentLoss;

    protected FluidFillOrSupplierSlot inputSlot;
    protected OutputInventorySlot inputReturnSlot;
    protected FluidInventorySlot outputSlot;
    protected OutputInventorySlot outputReturnSlot;

    protected IExtendedFluidTank inputTank;
    protected IExtendedFluidTank outputTank;

    protected IHeatCapacitor heatCapacitor;

    protected final AdvancedFluidInputHandler inputHandler;
    protected final IOutputHandler<FluidStack> outputHandler;
    protected final double maxMultiplierTemp;

    protected BEAbstractCompactThermalEvaporationPlant(Holder<Block> blockProvider, BlockPos pos, BlockState state,
            double maxMultiplierTemp) {
        super(blockProvider, pos, state, TRACKED_ERROR_TYPES);
        configComponent.setupIOConfig(TransmissionType.FLUID, inputTank, outputTank, RelativeSide.RIGHT, false);
        ConfigInfo itemConfig = configComponent.getConfig(TransmissionType.ITEM);
        itemConfig.addSlotInfo(DataType.INPUT, new InventorySlotInfo(true, false,
                List.of(inputSlot, outputSlot)));
        itemConfig.addSlotInfo(DataType.OUTPUT,
                new InventorySlotInfo(false, true, List.of(inputReturnSlot, outputReturnSlot)));
        itemConfig.addSlotInfo(DataType.INPUT_OUTPUT,
                new InventorySlotInfo(true, true, List.of(inputSlot, outputSlot, inputReturnSlot, outputReturnSlot)));
        itemConfig.setDataType(DataType.INPUT, RelativeSide.TOP);
        itemConfig.setDataType(DataType.INPUT, RelativeSide.BOTTOM);
        itemConfig.setDataType(DataType.INPUT, RelativeSide.FRONT);
        itemConfig.setDataType(DataType.INPUT, RelativeSide.BACK);
        itemConfig.setDataType(DataType.INPUT, RelativeSide.LEFT);
        itemConfig.setDataType(DataType.OUTPUT, RelativeSide.RIGHT);
        itemConfig.setCanEject(true);
        ConfigInfo heatConfig = configComponent.getConfig(TransmissionType.HEAT);
        heatConfig.addSlotInfo(DataType.INPUT_OUTPUT, new HeatSlotInfo(true, true, List.of(heatCapacitor)));
        ejectorComponent = new TileComponentEjector(this, () -> 0, () -> 0x7fffffff)
                .setOutputData(configComponent, TransmissionType.FLUID, TransmissionType.ITEM);
        EjectorComponentUtils.setCanFluidTankEject(ejectorComponent,
                (dataType, tank) -> dataType.canOutput() && tank == outputTank);
        this.inputHandler = AdvancedFluidInputHandler.create(inputTank, RecipeError.NOT_ENOUGH_INPUT);
        this.outputHandler = OutputHelper.getOutputHandler(outputTank, RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
        this.maxMultiplierTemp = maxMultiplierTemp;
        inputSlot.setSupplyingStackSetter(inputHandler::setSuppliedStack);
        caluculateOperationsPerTick();
    }

    @Override
    protected @Nullable IFluidTankHolder getInitialFluidTanks(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        FluidTankHelper builder = FluidTankHelper.forSideWithConfig(this);
        builder.addTank(
                inputTank = BasicFluidTank.create(initFluidTankCapacity(), this::containsRecipe, recipeCacheListener));
        builder.addTank(outputTank = BasicFluidTank.output(initFluidTankCapacity(), recipeCacheUnpauseListener));
        return builder.build();
    }

    protected abstract int initFluidTankCapacity();

    @Override
    protected @Nullable IInventorySlotHolder getInitialInventory(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        InventorySlotHelper builder = InventorySlotHelper.forSideWithConfig(this);
        builder.addSlot(inputSlot = FluidFillOrSupplierSlot.create(inputTank, recipeCacheListener, 28, 20))
                .setSlotOverlay(SlotOverlay.MINUS);
        builder.addSlot(inputReturnSlot = OutputInventorySlot.at(listener, 28, 51));
        builder.addSlot(outputSlot = FluidInventorySlot.drain(outputTank, listener, 152, 20))
                .setSlotOverlay(SlotOverlay.PLUS);
        builder.addSlot(outputReturnSlot = OutputInventorySlot.at(listener, 152, 51));
        return builder.build();
    }

    @Override
    protected @Nullable IHeatCapacitorHolder getInitialHeatCapacitors(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener,
            CachedAmbientTemperature ambientTemperature) {
        HeatCapacitorHelper builder = HeatCapacitorHelper.forSideWithConfig(this);
        builder.addCapacitor(
                heatCapacitor = BasicHeatCapacitor.create(MekanismConfig.general.evaporationHeatCapacity.get() * 18,
                        ambientTemperature, () -> {
                            listener.onContentsChanged();
                            this.caluculateOperationsPerTick();
                        }));
        return builder.build();
    }

    @Override
    public int getExtraWidth() {
        return 20;
    }

    @Override
    protected boolean onUpdateServer() {
        boolean needsPacket = super.onUpdateServer();
        inputSlot.fillTank(inputReturnSlot);
        outputSlot.drainTank(outputReturnSlot);
        lastEnvironmentLoss = simulateEnvironment();
        recipeCacheLookupMonitor.updateAndProcess();
        return needsPacket;
    }

    @Override
    public @NotNull IMekanismRecipeTypeProvider<?, FluidToFluidRecipe, InputRecipeCache.SingleFluid<FluidToFluidRecipe>> getRecipeType() {
        return MekanismRecipeType.EVAPORATING;
    }

    @Override
    public IRecipeViewerRecipeType<FluidToFluidRecipe> recipeViewerType() {
        return RecipeViewerRecipeType.EVAPORATING;
    }

    @Nullable
    @Override
    public FluidToFluidRecipe getRecipe(int cacheIndex) {
        return findFirstRecipe(inputHandler);
    }

    @Override
    public @NotNull CachedRecipe<FluidToFluidRecipe> createNewCachedRecipe(@NotNull FluidToFluidRecipe recipe,
            int cacheIndex) {
        return OneInputCachedRecipe.fluidToFluid(recipe, recheckAllRecipeErrors, inputHandler, outputHandler)
                .setErrorsChanged(this::onErrorsChanged)
                .setCanHolderFunction(this::canFunction)
                .setActive(this::setActive)
                .setRequiredTicks(this::getTicksRequired)
                .setOnFinish(this::markForSave)
                .setOperatingTicksChanged(this::setOperatingTicks)
                .setBaselineMaxOperations(this::getOperationsPerTick);
    }

    protected void caluculateOperationsPerTick() {
        operationsPerTickD = (Math.min(maxMultiplierTemp, heatCapacitor.getTemperature()) - HeatAPI.AMBIENT_TEMP)
                * MekanismConfig.general.evaporationTempMultiplier.get();
        if (operationsPerTickD <= 0) {
            operationsPerTick = 0;
            ticksRequired = 0x7fffffff;
        } else if (operationsPerTickD < 1) {
            operationsPerTick = 1;
            ticksRequired = MathUtils.clampToInt(Math.ceil(1.0d / operationsPerTickD));
        } else {
            operationsPerTick = MathUtils.clampToInt(operationsPerTickD);
            ticksRequired = 1;
        }
    }

    public int getOperationsPerTick() {
        return operationsPerTick;
    }

    public double getOperationsPerTickD() {
        return operationsPerTickD;
    }

    public double getEnvironmentLoss() {
        return lastEnvironmentLoss;
    }

    @Override
    public void addContainerTrackers(MekanismContainer container) {
        super.addContainerTrackers(container);
        container.track(SyncableInt.create(this::getOperatingTicks, this::setOperatingTicks));
        container.track(SyncableInt.create(this::getTicksRequired, (value) -> this.ticksRequired = value));
        container.track(SyncableInt.create(this::getOperationsPerTick, v -> operationsPerTick = v));
        container.track(SyncableDouble.create(this::getOperationsPerTickD, v -> operationsPerTickD = v));
        container.track(SyncableDouble.create(this::getEnvironmentLoss, v -> lastEnvironmentLoss = v));
    }

    public double getScaledProgress() {
        return (double) this.getOperatingTicks() / (double) this.ticksRequired;
    }

    protected void setOperatingTicks(int ticks) {
        this.operatingTicks = ticks;
    }

    @ComputerMethod(nameOverride = "getRecipeProgress")
    public int getOperatingTicks() {
        return this.operatingTicks;
    }

    @ComputerMethod
    public int getTicksRequired() {
        return this.ticksRequired;
    }

    public int getSavedOperatingTicks(int cacheIndex) {
        return this.getOperatingTicks();
    }

    public void loadAdditional(@NotNull CompoundTag nbt, HolderLookup.Provider provider) {
        super.loadAdditional(nbt, provider);
        this.operatingTicks = nbt.getInt(SerializationConstants.PROGRESS);
        caluculateOperationsPerTick();
    }

    public void saveAdditional(@NotNull CompoundTag nbtTags, HolderLookup.Provider provider) {
        super.saveAdditional(nbtTags, provider);
        nbtTags.putInt(SerializationConstants.PROGRESS, this.getOperatingTicks());
    }

    public IExtendedFluidTank getInputTank() {
        return inputTank;
    }

    public IExtendedFluidTank getOutputTank() {
        return outputTank;
    }

    public IHeatCapacitor getHeatCapacitor(){
        return heatCapacitor;
    }

}
