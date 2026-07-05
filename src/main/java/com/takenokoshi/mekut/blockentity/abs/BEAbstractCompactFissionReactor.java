package com.takenokoshi.mekut.blockentity.abs;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekut.blockentity.base.BlockEntityMekUtRecipeMachine;
import com.takenokoshi.mekut.blockentity.interfaces.IHasGuiSizeOffset;
import com.takenokoshi.mekut.blockentity.packet.IBurnRatePacketAcceptor;
import com.takenokoshi.mekut.core.EjectorComponentUtils;
import com.takenokoshi.mekut.misc.CoolantHeatingLookupMonitor;
import com.takenokoshi.mekut.recipe.cached.ChemicalToChemicalHeatCachedRecipe;
import com.takenokoshi.mekut.recipe.cached.ICachedRecipe;
import com.takenokoshi.mekut.recipe.inputcache.MUSingleInputRecipeCache;
import com.takenokoshi.mekut.recipe.lookup.IMekUtRecipeTypedLookupHandler;
import com.takenokoshi.mekut.recipe.output.HeatOutputHandler;
import com.takenokoshi.mekut.recipe.recipe.prefab.ChemicalToChemicalHeatRecipe;
import com.takenokoshi.mekut.recipe.type.IMekUtRecipeTypeProvider;
import com.takenokoshi.mekut.registries.MekUtRecipeTypes;

import mekanism.api.IContentsListener;
import mekanism.api.RelativeSide;
import mekanism.api.chemical.BasicChemicalTank;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.datamaps.IMekanismDataMapTypes;
import mekanism.api.heat.IHeatCapacitor;
import mekanism.api.math.MathUtils;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.inputs.InputHelper;
import mekanism.api.recipes.outputs.IOutputHandler;
import mekanism.api.recipes.outputs.OutputHelper;
import mekanism.common.attachments.component.AttachedSideConfig;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.attachments.containers.chemical.ChemicalTanksBuilder;
import mekanism.common.attachments.containers.fluid.FluidTanksBuilder;
import mekanism.common.attachments.containers.heat.HeatCapacitorsBuilder;
import mekanism.common.capabilities.fluid.BasicFluidTank;
import mekanism.common.capabilities.heat.BasicHeatCapacitor;
import mekanism.common.capabilities.heat.CachedAmbientTemperature;
import mekanism.common.capabilities.holder.chemical.ChemicalTankHelper;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import mekanism.common.capabilities.holder.fluid.FluidTankHelper;
import mekanism.common.capabilities.holder.fluid.IFluidTankHolder;
import mekanism.common.capabilities.holder.heat.HeatCapacitorHelper;
import mekanism.common.capabilities.holder.heat.IHeatCapacitorHolder;
import mekanism.common.capabilities.merged.MergedTank;
import mekanism.common.inventory.container.MekanismContainer;
import mekanism.common.inventory.container.sync.SyncableDouble;
import mekanism.common.inventory.container.sync.SyncableInt;
import mekanism.common.inventory.container.sync.SyncableLong;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.registration.impl.ItemRegistryObject;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.tile.component.config.ConfigInfo;
import mekanism.common.tile.component.config.DataType;
import mekanism.common.tile.component.config.slot.ChemicalSlotInfo;
import mekanism.common.tile.component.config.slot.HeatSlotInfo;
import mekanism.common.util.NBTUtils;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BEAbstractCompactFissionReactor
        extends BlockEntityMekUtRecipeMachine<ChemicalToChemicalHeatRecipe>
        implements IHasGuiSizeOffset, IBurnRatePacketAcceptor,
        IMekUtRecipeTypedLookupHandler<ChemicalToChemicalHeatRecipe, MUSingleInputRecipeCache.MUSingleChemical<ChemicalToChemicalHeatRecipe>> {

    public static final RecipeError TOO_HOT = RecipeError.create();

    public static final List<RecipeError> TRACKED_ERROR_TYPES = List.of(
            RecipeError.NOT_ENOUGH_INPUT,
            RecipeError.NOT_ENOUGH_OUTPUT_SPACE,
            RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT,
            TOO_HOT);

    private static final double INVERSE_INSULATION_COEFFICIENT = 10_000;
    private static final double INVERSE_CONDUCTION_COEFFICIENT = 10;

    public static final String RATE_LIMIT = "rate_limit";

    public static final AttachedSideConfig SIDE_CONFIG = Util.make(() -> {
        Map<TransmissionType, AttachedSideConfig.LightConfigInfo> configInfo = new EnumMap<>(TransmissionType.class);
        configInfo.put(TransmissionType.FLUID, AttachedSideConfig.LightConfigInfo.INPUT_ONLY);
        Map<RelativeSide, DataType> chemicalConfig = new EnumMap<>(RelativeSide.class);
        chemicalConfig.put(RelativeSide.LEFT, DataType.INPUT_1);
        chemicalConfig.put(RelativeSide.FRONT, DataType.INPUT_1);
        chemicalConfig.put(RelativeSide.BACK, DataType.INPUT_1);
        chemicalConfig.put(RelativeSide.RIGHT, DataType.OUTPUT_1);
        chemicalConfig.put(RelativeSide.TOP, DataType.INPUT_2);
        chemicalConfig.put(RelativeSide.BOTTOM, DataType.OUTPUT_2);
        configInfo.put(TransmissionType.CHEMICAL, new AttachedSideConfig.LightConfigInfo(chemicalConfig, true));
        Map<RelativeSide, DataType> heatConfig = new EnumMap<>(RelativeSide.class);
        for (RelativeSide side : RelativeSide.values()) {
            heatConfig.put(side, DataType.INPUT_OUTPUT);
        }
        configInfo.put(TransmissionType.HEAT, new AttachedSideConfig.LightConfigInfo(heatConfig, true));
        return new AttachedSideConfig(configInfo);
    });

    protected IChemicalTank inputTank;
    protected IChemicalTank outputTank;
    protected IHeatCapacitor heatCapacitor;
    protected MergedTank coolantTank;
    protected IChemicalTank heatedCoolantTank;
    public double lastEnvironmentLoss = 0;
    public double lastAdjacentLoss = 0;
    public int rateLimit = 1;

    protected double lastTemperatureLoss = 0.0d;
    protected long lastBurnRate = 0;
    public final double tempLimit;

    protected final IInputHandler<ChemicalStack> inputHandler;
    protected final IOutputHandler<ChemicalStack> outputHandler;
    protected final HeatOutputHandler heatOutputHandler;
    public final CoolantHeatingLookupMonitor coolantHeatingLookupMonitor;

    public BEAbstractCompactFissionReactor(Holder<Block> blockProvider, BlockPos pos, BlockState state,
            int baselineMaxOperations, double tempLimit, double boilEfficiency) {
        super(blockProvider, pos, state, TRACKED_ERROR_TYPES, baselineMaxOperations);
        configComponent.setupInputConfig(TransmissionType.FLUID, coolantTank.getFluidTank());
        ConfigInfo chemicalConfig = configComponent.getConfig(TransmissionType.CHEMICAL);
        chemicalConfig.addSlotInfo(DataType.INPUT_1, new ChemicalSlotInfo(true, false, List.of(inputTank)));
        chemicalConfig.addSlotInfo(DataType.INPUT_2,
                new ChemicalSlotInfo(true, false, List.of(coolantTank.getChemicalTank())));
        chemicalConfig.addSlotInfo(DataType.OUTPUT_1, new ChemicalSlotInfo(false, true, List.of(outputTank)));
        chemicalConfig.addSlotInfo(DataType.OUTPUT_2, new ChemicalSlotInfo(false, true, List.of(heatedCoolantTank)));
        chemicalConfig.addSlotInfo(DataType.INPUT_OUTPUT, new ChemicalSlotInfo(true, true,
                List.of(inputTank, coolantTank.getChemicalTank(), outputTank, heatedCoolantTank)));
        chemicalConfig.setDataType(DataType.INPUT_1, RelativeSide.LEFT);
        chemicalConfig.setDataType(DataType.INPUT_1, RelativeSide.FRONT);
        chemicalConfig.setDataType(DataType.INPUT_1, RelativeSide.BACK);
        chemicalConfig.setDataType(DataType.INPUT_2, RelativeSide.TOP);
        chemicalConfig.setDataType(DataType.OUTPUT_1, RelativeSide.RIGHT);
        chemicalConfig.setDataType(DataType.OUTPUT_2, RelativeSide.BOTTOM);
        chemicalConfig.setCanEject(true);
        chemicalConfig.setEjecting(true);
        ConfigInfo heatConfig = configComponent.getConfig(TransmissionType.HEAT);
        heatConfig.addSlotInfo(DataType.INPUT_OUTPUT, new HeatSlotInfo(true, true, List.of(heatCapacitor)));
        ejectorComponent = new TileComponentEjector(this, () -> Long.MAX_VALUE).setOutputData(configComponent,
                TransmissionType.CHEMICAL);
        EjectorComponentUtils.setCanChemicalTankEject(ejectorComponent, (type, tank) -> {
            if (type == DataType.OUTPUT_1) {
                return tank == outputTank;
            }
            if (type == DataType.OUTPUT_2) {
                return tank == heatedCoolantTank;
            }
            if (type == DataType.INPUT_OUTPUT) {
                return tank == outputTank || tank == heatedCoolantTank;
            }
            return false;
        });
        this.inputHandler = InputHelper.getInputHandler(inputTank, RecipeError.NOT_ENOUGH_INPUT);
        this.outputHandler = OutputHelper.getOutputHandler(outputTank, RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
        this.heatOutputHandler = new HeatOutputHandler(heatCapacitor, tempLimit, TOO_HOT);
        this.coolantHeatingLookupMonitor = new CoolantHeatingLookupMonitor(coolantTank, heatedCoolantTank,
                this::canFunction);
        this.coolantHeatingLookupMonitor.boilEfficiency = boilEfficiency;
        this.tempLimit = tempLimit;
    }

    public static void addContainers(ItemRegistryObject<?> value, long fuelTankCapacity, double heatCapacity,
            int fluidCoolantTankCapacity, long chemicalCoolantTankCapacity, long heatedCoolantTankCapacity) {
        value.addAttachmentOnlyContainers(ContainerType.FLUID, () -> FluidTanksBuilder.builder()
                .addBasic(fluidCoolantTankCapacity)
                .build());
        value.addAttachmentOnlyContainers(ContainerType.CHEMICAL, () -> ChemicalTanksBuilder.builder()
                .addBasic(fuelTankCapacity)
                .addBasic(fuelTankCapacity)
                .addBasic(chemicalCoolantTankCapacity)
                .addBasic(heatedCoolantTankCapacity)
                .build());
        value.addAttachmentOnlyContainers(ContainerType.HEAT, () -> HeatCapacitorsBuilder.builder()
                .addBasic(heatCapacity, INVERSE_CONDUCTION_COEFFICIENT, INVERSE_INSULATION_COEFFICIENT)
                .build());
    }

    @Override
    protected void presetVariables() {
        super.presetVariables();
        coolantTank = MergedTank.create(
                BasicFluidTank.input(initFluidCoolantTankCapacity(), fluid -> fluid.is(FluidTags.WATER),
                        () -> {
                            coolantHeatingLookupMonitor.onInputTankChanged();
                        }),
                BasicChemicalTank.inputModern(initChemicalCoolantTankCapacity(),
                        (chemical) -> chemical.getData(IMekanismDataMapTypes.INSTANCE.cooledChemicalCoolant()) != null,
                        () -> {
                            coolantHeatingLookupMonitor.onInputTankChanged();
                        })// coolantHeatingLookupMonitor is null when this method called.
                          // Delayed reference required.
        );
    }

    protected abstract int initFluidCoolantTankCapacity();

    protected abstract long initChemicalCoolantTankCapacity();

    @Override
    protected @NotNull IChemicalTankHolder getInitialChemicalTanks(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        ChemicalTankHelper builder = ChemicalTankHelper.forSideWithConfig(this);
        builder.addTank(inputTank = BasicChemicalTank.inputModern(initFuelTankCapacity(), this::containsRecipe,
                recipeCacheListener));
        builder.addTank(outputTank = BasicChemicalTank.output(initFuelTankCapacity(), recipeCacheUnpauseListener));
        builder.addTank(coolantTank.getChemicalTank());
        builder.addTank(heatedCoolantTank = BasicChemicalTank.output(initHeatedCoolantTankCapacity(), () -> {
            coolantHeatingLookupMonitor.refreshActiveState();
        }));
        // coolantHeatingLookupMonitor is null when this method called.
        // Delayed reference required.
        return builder.build();
    }

    protected abstract long initFuelTankCapacity();

    protected abstract long initHeatedCoolantTankCapacity();

    @Override
    protected @Nullable IFluidTankHolder getInitialFluidTanks(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        FluidTankHelper builder = FluidTankHelper.forSideWithConfig(this);
        builder.addTank(coolantTank.getFluidTank());
        return builder.build();
    }

    @Override
    protected @Nullable IHeatCapacitorHolder getInitialHeatCapacitors(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener,
            CachedAmbientTemperature ambientTemperature) {
        HeatCapacitorHelper builder = HeatCapacitorHelper.forSideWithConfig(this);
        builder.addCapacitor(
                heatCapacitor = BasicHeatCapacitor.create(initHeatCapacity(),
                        INVERSE_CONDUCTION_COEFFICIENT,
                        INVERSE_INSULATION_COEFFICIENT,
                        ambientTemperature, recipeCacheUnpauseListener));
        return builder.build();
    }

    protected abstract double initHeatCapacity();

    @Override
    public int getExtraHeight() {
        return 89;
    }

    @Override
    public int getExtraWidth() {
        return 20;
    }

    protected boolean containsRecipe(ChemicalStack input) {
        return getRecipeType().getInputCache().containsInput(getLevel(), input);
    }

    protected @Nullable ChemicalToChemicalHeatRecipe findfirstRecipe(ChemicalStack input) {
        return getRecipeType().getInputCache().findFirstRecipe(getLevel(), input);
    }

    @Override
    public @Nullable ChemicalToChemicalHeatRecipe getRecipe(int cacheIndex) {
        return findfirstRecipe(inputHandler.getInput());
    }

    @Override
    public @NotNull ICachedRecipe<ChemicalToChemicalHeatRecipe> createNewCachedRecipe(
            @NotNull ChemicalToChemicalHeatRecipe recipe, int cacheIndex) {
        return new ChemicalToChemicalHeatCachedRecipe(recipe, recheckAllRecipeErrors, inputHandler, outputHandler,
                heatOutputHandler)
                .setErrorsChanged(this::onErrorsChanged)
                .setCanHolderFunction(this::canFunction)
                .setActive(this::setActive)
                .setOnFinish(this::markForSave)
                .setBaselineMaxOperations(this::getOperationsPerTick);
    }

    @Override
    public @NotNull IMekUtRecipeTypeProvider<?, ChemicalToChemicalHeatRecipe, MUSingleInputRecipeCache.MUSingleChemical<ChemicalToChemicalHeatRecipe>> getRecipeType() {
        return MekUtRecipeTypes.FISSION_REACTOR;
    }

    @Override
    protected boolean onUpdateServer() {
        boolean v = super.onUpdateServer();
        long before = inputTank.getStored();
        recipeCacheLookupMonitor.updateAndProcess();
        lastBurnRate = before - inputTank.getStored();
        lastTemperatureLoss = coolantHeatingLookupMonitor.updateAndProcess(heatCapacitor);
        lastEnvironmentLoss = simulateEnvironment();
        lastAdjacentLoss = simulateAdjacent();
        return v;
    }

    @Override
    public void addContainerTrackers(MekanismContainer container) {
        super.addContainerTrackers(container);
        container.track(SyncableDouble.create(this::getLastTemperatureLoss, v -> lastTemperatureLoss = v));
        container.track(SyncableLong.create(this::getBurnRate, v -> lastBurnRate = v));
        container.track(SyncableDouble.create(() -> this.lastEnvironmentLoss, v -> lastEnvironmentLoss = v));
        container.track(SyncableDouble.create(() -> this.lastAdjacentLoss, v -> lastAdjacentLoss = v));
        container.track(SyncableInt.create(this::getOperationsPerTick, v -> operationsPerTick = v));
        coolantHeatingLookupMonitor.trackContainer(container);
    }

    public double getLastTemperatureLoss() {
        return lastTemperatureLoss;
    }

    public IChemicalTank getInputTank() {
        return inputTank;
    }

    public IChemicalTank getOutputTank() {
        return outputTank;
    }

    public MergedTank getCooledCoolantTank() {
        return coolantTank;
    }

    public IChemicalTank getHeatedCoolantTank() {
        return heatedCoolantTank;
    }

    public IHeatCapacitor getHeatCapacitor() {
        return heatCapacitor;
    }

    @Override
    public void setBurnRate(long rate) {
        rateLimit = Math.min(MathUtils.clampToInt(rate), baselineMaxOperations);
        operationsPerTick = rateLimit;
    }

    public long getBurnRate() {
        return lastBurnRate;
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag nbt, @NotNull Provider provider) {
        super.loadAdditional(nbt, provider);
        NBTUtils.setIntIfPresent(nbt, RATE_LIMIT, this::setBurnRate);
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbtTags, @NotNull Provider provider) {
        super.saveAdditional(nbtTags, provider);
        nbtTags.putInt(RATE_LIMIT, rateLimit);
    }

}
