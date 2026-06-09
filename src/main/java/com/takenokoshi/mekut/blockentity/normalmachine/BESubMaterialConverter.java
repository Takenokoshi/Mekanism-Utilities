package com.takenokoshi.mekut.blockentity.normalmachine;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekut.blockentity.base.BlockEntityMekUtProgressMachine;
import com.takenokoshi.mekut.recipe.cached.ICachedRecipe;
import com.takenokoshi.mekut.recipe.lookup.IMekUtRecipeTypedLookupHandler;
import com.takenokoshi.mekut.recipe.type.IMekUtRecipeTypeProvider;
import com.takenokoshi.mekut.recipe.type.WrappedMekanismRecipeType;

import mekanism.api.IContentsListener;
import mekanism.api.RelativeSide;
import mekanism.api.chemical.BasicChemicalTank;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.recipes.ItemStackToChemicalRecipe;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.cache.OneInputCachedRecipe;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.inputs.InputHelper;
import mekanism.api.recipes.outputs.IOutputHandler;
import mekanism.api.recipes.outputs.OutputHelper;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mekanism.client.recipe_viewer.type.RecipeViewerRecipeType;
import mekanism.common.capabilities.holder.chemical.ChemicalTankHelper;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import mekanism.common.capabilities.holder.slot.IInventorySlotHolder;
import mekanism.common.capabilities.holder.slot.InventorySlotHelper;
import mekanism.common.integration.computer.SpecialComputerMethodWrapper.ComputerChemicalTankWrapper;
import mekanism.common.integration.computer.SpecialComputerMethodWrapper.ComputerIInventorySlotWrapper;
import mekanism.common.integration.computer.annotation.WrappingComputerMethod;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.inventory.slot.InputInventorySlot;
import mekanism.common.inventory.slot.chemical.ChemicalInventorySlot;
import mekanism.common.inventory.warning.WarningTracker.WarningType;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.recipe.lookup.cache.InputRecipeCache;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.SingleItem;
import mekanism.common.tile.component.TileComponentEjector;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BESubMaterialConverter extends BlockEntityMekUtProgressMachine<ItemStackToChemicalRecipe>
        implements
        IMekUtRecipeTypedLookupHandler<ItemStackToChemicalRecipe, InputRecipeCache.SingleItem<ItemStackToChemicalRecipe>> {

    private static final List<RecipeError> TRACKED_ERROR_TYPES = List.of(
            RecipeError.NOT_ENOUGH_ENERGY,
            RecipeError.NOT_ENOUGH_INPUT,
            RecipeError.NOT_ENOUGH_OUTPUT_SPACE,
            RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT);
    public static final long MAX_GAS = Long.MAX_VALUE;

    @WrappingComputerMethod(wrapper = ComputerChemicalTankWrapper.class, methodNames = { "getOutput",
            "getOutputCapacity", "getOutputNeeded",
            "getOutputFilledPercentage" }, docPlaceholder = "output tank")
    public IChemicalTank gasTank;

    private final IOutputHandler<@NotNull ChemicalStack> outputHandler;
    private final IInputHandler<@NotNull ItemStack> inputHandler;
    @WrappingComputerMethod(wrapper = ComputerIInventorySlotWrapper.class, methodNames = "getInput", docPlaceholder = "input slot")
    InputInventorySlot inputSlot;
    @WrappingComputerMethod(wrapper = ComputerIInventorySlotWrapper.class, methodNames = "getOutputItem", docPlaceholder = "output item slot")
    ChemicalInventorySlot outputSlot;

    public BESubMaterialConverter(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state, TRACKED_ERROR_TYPES, 1, r -> 1, 0x7fffffff);
        configComponent.setupIOConfig(TransmissionType.ITEM, inputSlot, outputSlot, RelativeSide.RIGHT);
        configComponent.setupOutputConfig(TransmissionType.CHEMICAL, gasTank, RelativeSide.RIGHT);
        ejectorComponent = new TileComponentEjector(this, () -> Long.MAX_VALUE);
        ejectorComponent.setOutputData(configComponent, TransmissionType.CHEMICAL);

        inputHandler = InputHelper.getInputHandler(inputSlot, RecipeError.NOT_ENOUGH_INPUT);
        outputHandler = OutputHelper.getOutputHandler(gasTank, RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
    }

    @NotNull
    @Override
    public IChemicalTankHolder getInitialChemicalTanks(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        ChemicalTankHelper builder = ChemicalTankHelper.forSideWithConfig(this);
        builder.addTank(gasTank = BasicChemicalTank.output(MAX_GAS, recipeCacheUnpauseListener));
        return builder.build();
    }

    @NotNull
    @Override
    protected IInventorySlotHolder getInitialInventory(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        InventorySlotHelper builder = InventorySlotHelper.forSideWithConfig(this);
        builder.addSlot(inputSlot = InputInventorySlot.at(this::containsRecipe, recipeCacheListener, 26, 36))
                .tracksWarnings(slot -> slot.warning(WarningType.NO_MATCHING_RECIPE,
                        getWarningCheck(RecipeError.NOT_ENOUGH_INPUT)));
        builder.addSlot(outputSlot = ChemicalInventorySlot.drain(gasTank, listener, 152, 55));
        outputSlot.setSlotOverlay(SlotOverlay.PLUS);
        return builder.build();
    }

    private boolean containsRecipe(ItemStack input) {
        return getRecipeType().getInputCache().containsInput(getHandlerWorld(), input);
    }

    @Override
    protected boolean onUpdateServer() {
        boolean sendUpdatePacket = super.onUpdateServer();
        outputSlot.drainTank();
        recipeCacheLookupMonitor.updateAndProcess();
        return sendUpdatePacket;
    }

    @Override
    public IRecipeViewerRecipeType<ItemStackToChemicalRecipe> recipeViewerType() {
        return RecipeViewerRecipeType.CHEMICAL_CONVERSION;
    }

    @Nullable
    @Override
    public ItemStackToChemicalRecipe getRecipe(int cacheIndex) {
        return findFirstRecipe(inputHandler);
    }

    private @Nullable ItemStackToChemicalRecipe findFirstRecipe(ItemStack input) {
        return getRecipeType().getInputCache().findFirstRecipe(getHandlerWorld(), input);
    }

    private @Nullable ItemStackToChemicalRecipe findFirstRecipe(IInputHandler<ItemStack> handler) {
        return findFirstRecipe(handler.getInput());
    }

    @NotNull
    @Override
    public ICachedRecipe<ItemStackToChemicalRecipe> createNewCachedRecipe(@NotNull ItemStackToChemicalRecipe recipe,
            int cacheIndex) {
        return ICachedRecipe.fromMekanism(
                OneInputCachedRecipe.itemToChemical(recipe, recheckAllRecipeErrors, inputHandler, outputHandler)
                        .setErrorsChanged(this::onErrorsChanged)
                        .setCanHolderFunction(this::canFunction)
                        .setActive(this::setActive)
                        .setOnFinish(this::markForSave)
                        .setBaselineMaxOperations(() -> 0x7fffffff));
    }

    @Override
    public @NotNull IMekUtRecipeTypeProvider<?, ItemStackToChemicalRecipe, SingleItem<ItemStackToChemicalRecipe>> getRecipeType() {
        return WrappedMekanismRecipeType.CHEMICAL_CONVERSION;
    }

    @Override
    protected void recaluculateProcessingSpeed() {
    }

}
