package com.takenokoshi.mekut.blockentity.abs;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekut.blockentity.base.BEMultiScaledProgressMachine;
import com.takenokoshi.mekut.blockentity.interfaces.IHasMachineEnergyContainer;
import com.takenokoshi.mekut.recipe.cached.ICachedRecipe;
import com.takenokoshi.mekut.recipe.inputcache.MUSingleInputRecipeCache;
import com.takenokoshi.mekut.recipe.lookup.IMekUtRecipeTypedLookupHandler;
import com.takenokoshi.mekut.recipe.type.IMekUtRecipeTypeProvider;
import com.takenokoshi.mekut.registries.MekUtRecipeTypes;

import mekanism.api.AutomationType;
import mekanism.api.IContentsListener;
import mekanism.api.RelativeSide;
import mekanism.api.chemical.BasicChemicalTank;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.chemical.attribute.ChemicalAttributeValidator;
import mekanism.api.math.MathUtils;
import mekanism.api.recipes.ChemicalToChemicalRecipe;
import mekanism.api.recipes.cache.OneInputCachedRecipe;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.inputs.InputHelper;
import mekanism.api.recipes.outputs.IOutputHandler;
import mekanism.api.recipes.outputs.OutputHelper;
import mekanism.api.recipes.vanilla_input.SingleChemicalRecipeInput;
import mekanism.common.attachments.component.AttachedSideConfig;
import mekanism.common.attachments.component.AttachedSideConfig.LightConfigInfo;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.attachments.containers.chemical.ChemicalTanksBuilder;
import mekanism.common.capabilities.energy.FixedUsageEnergyContainer;
import mekanism.common.capabilities.energy.MachineEnergyContainer;
import mekanism.common.capabilities.holder.chemical.ChemicalTankHelper;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import mekanism.common.capabilities.holder.energy.EnergyContainerHelper;
import mekanism.common.capabilities.holder.energy.IEnergyContainerHolder;
import mekanism.common.config.MekanismConfig;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.registration.impl.ItemRegistryObject;
import mekanism.common.tile.component.TileComponentEjector;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BEAbstractCompactSPS extends BEMultiScaledProgressMachine<ChemicalToChemicalRecipe> implements
        IMekUtRecipeTypedLookupHandler<ChemicalToChemicalRecipe, MUSingleInputRecipeCache.MUSingleChemical<ChemicalToChemicalRecipe>>,
        IHasMachineEnergyContainer {

    public static final AttachedSideConfig SIDE_CONFIG = Util.make(() -> {
        Map<TransmissionType, LightConfigInfo> configInfo = new EnumMap<>(TransmissionType.class);
        configInfo.put(TransmissionType.CHEMICAL, AttachedSideConfig.LightConfigInfo.OUT_EJECT);
        return new AttachedSideConfig(configInfo);
    });

    public static final List<RecipeError> TRACKED_ERROR_TYPES = List.of(
            RecipeError.NOT_ENOUGH_ENERGY,
            RecipeError.NOT_ENOUGH_INPUT,
            RecipeError.NOT_ENOUGH_OUTPUT_SPACE,
            RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT);

    protected FixedUsageEnergyContainer<BEAbstractCompactSPS> energyContainer;
    protected IChemicalTank inputTank;
    protected IChemicalTank outputTank;

    protected final IInputHandler<ChemicalStack> inputHandler;
    protected final IOutputHandler<ChemicalStack> outputHandler;

    protected long inputUsagePerTick = 1;

    protected BEAbstractCompactSPS(Holder<Block> blockProvider, BlockPos pos, BlockState state,
            int baselineMaxOperations, double speedModifier) {
        super(blockProvider, pos, state, TRACKED_ERROR_TYPES,
                MathUtils.clampToInt(MekanismConfig.general.spsInputPerAntimatter.getAsInt() / 1000d * speedModifier),
                r -> MathUtils.clampToInt(r.getInput().amount() / 1000d * speedModifier),
                baselineMaxOperations);
        configComponent.setupIOConfig(TransmissionType.CHEMICAL, inputTank, outputTank, RelativeSide.RIGHT, false);
        configComponent.setupInputConfig(TransmissionType.ENERGY, energyContainer);
        ejectorComponent = new TileComponentEjector(this).setOutputData(configComponent, TransmissionType.CHEMICAL);
        this.inputHandler = InputHelper.getInputHandler(inputTank, RecipeError.NOT_ENOUGH_INPUT);
        this.outputHandler = OutputHelper.getOutputHandler(outputTank, RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
    }

    public static Consumer<ItemRegistryObject<?>> getContainerAdder(long tankCapacity) {
        return value -> {
            value.addAttachmentOnlyContainers(ContainerType.CHEMICAL,
                    () -> ChemicalTanksBuilder.builder()
                            .addBasic(tankCapacity)
                            .addBasic(tankCapacity)
                            .build());
        };
    }

    @NotNull
    @Override
    protected IEnergyContainerHolder getInitialEnergyContainers(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        EnergyContainerHelper builder = EnergyContainerHelper.forSideWithConfig(this);
        builder.addContainer(energyContainer = FixedUsageEnergyContainer.input(this, this::calculateEnergyUsase,
                recipeCacheUnpauseListener));
        return builder.build();
    }

    protected long calculateEnergyUsase(long def, BEAbstractCompactSPS tile) {
        return MathUtils.clampToLong(1.0 * inputUsagePerTick * MekanismConfig.general.spsEnergyPerInput.get());
    }

    @NotNull
    @Override
    public IChemicalTankHolder getInitialChemicalTanks(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        ChemicalTankHelper builder = ChemicalTankHelper.forSideWithConfig(this);
        builder.addTank(inputTank = BasicChemicalTank.createModern(initTankCapacity(),
                (stack, type) -> type == AutomationType.MANUAL,
                (stack, type) -> containsRecipe(stack),
                this::containsRecipe,
                ChemicalAttributeValidator.ALWAYS_ALLOW, recipeCacheListener));
        builder.addTank(outputTank = BasicChemicalTank.createModern(initTankCapacity(),
                (stack, type) -> true,
                (stack, type) -> type == AutomationType.INTERNAL,
                (stack) -> true,
                ChemicalAttributeValidator.ALWAYS_ALLOW,
                recipeCacheUnpauseListener));
        return builder.build();
    }

    protected abstract long initTankCapacity();

    protected boolean containsRecipe(ChemicalStack input) {
        return getRecipeType().getInputCache().containsInput(getLevel(), input);
    }

    protected @Nullable ChemicalToChemicalRecipe findfirstRecipe(ChemicalStack input) {
        return getRecipeType().getInputCache().findFirstRecipe(getLevel(), input);
    }

    @Override
    public @Nullable ChemicalToChemicalRecipe getRecipe(int cacheIndex) {
        return findfirstRecipe(inputHandler.getInput());
    }

    @Override
    public @NotNull ICachedRecipe<ChemicalToChemicalRecipe> createNewCachedRecipe(
            @NotNull ChemicalToChemicalRecipe recipe, int cacheIndex) {
        return ICachedRecipe.fromMekanism(
                OneInputCachedRecipe.chemicalToChemical(recipe, recheckAllRecipeErrors, inputHandler, outputHandler)
                        .setErrorsChanged(this::onErrorsChanged)
                        .setCanHolderFunction(this::canFunction)
                        .setActive(this::setActive)
                        .setEnergyRequirements(energyContainer::getEnergyPerTick, energyContainer)
                        .setRequiredTicks(this::getTicksRequired)
                        .setOnFinish(this::markForSave)
                        .setOperatingTicksChanged(this::setOperatingTicks)
                        .setBaselineMaxOperations(this::getOperationsPerTick));
    }

    @Override
    public void onCachedRecipeChanged(@Nullable ICachedRecipe<ChemicalToChemicalRecipe> cachedRecipe, int cacheIndex) {
        super.onCachedRecipeChanged(cachedRecipe, cacheIndex);
        inputUsagePerTick = cachedRecipe.getRecipe().getInput().amount() / ticksRequired;
    }

    @Override
    public @NotNull IMekUtRecipeTypeProvider<SingleChemicalRecipeInput, ChemicalToChemicalRecipe, MUSingleInputRecipeCache.MUSingleChemical<ChemicalToChemicalRecipe>> getRecipeType() {
        return MekUtRecipeTypes.SPS;
    }

    protected boolean onUpdateServer() {
        boolean v = super.onUpdateServer();
        clientEnergyUsed = recipeCacheLookupMonitor.updateAndProcess(energyContainer);
        return v;
    }

    @Override
    public MachineEnergyContainer<?> getEnergyContainer() {
        return energyContainer;
    }

    public IChemicalTank getInputTank() {
        return inputTank;
    }

    public IChemicalTank getOutputTank() {
        return outputTank;
    }

}
