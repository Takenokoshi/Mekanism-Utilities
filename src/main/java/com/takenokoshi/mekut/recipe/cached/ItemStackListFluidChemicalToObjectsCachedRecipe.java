package com.takenokoshi.mekut.recipe.cached;

import java.util.List;
import java.util.function.BooleanSupplier;

import com.takenokoshi.mekaddonlib.recipe.cached.AbstractCachedRecipe;
import com.takenokoshi.mekut.recipe.input.ItemStackListInputHandler;
import com.takenokoshi.mekut.recipe.recipe.prefab.ItemStackListFluidChemicalToObjectsRecipe;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.inputs.IInputHandler;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;

public abstract class ItemStackListFluidChemicalToObjectsCachedRecipe<RECIPE extends ItemStackListFluidChemicalToObjectsRecipe>
        extends AbstractCachedRecipe<RECIPE> {
    private final ItemStackListInputHandler itemInputHandler;
    private final IInputHandler<FluidStack> fluidInputHandler;
    private final IInputHandler<ChemicalStack> chemicalInputHandler;

    private List<ItemStack> recipeInputItems = List.of();
    private int[] slotIndexCache = new int[0];
    private FluidStack recipeInputFluid = FluidStack.EMPTY;
    private ChemicalStack recipeInputChemical = ChemicalStack.EMPTY;

    protected ItemStackListFluidChemicalToObjectsCachedRecipe(RECIPE recipe, BooleanSupplier recheckAllErrors,
            ItemStackListInputHandler itemInputHandler, IInputHandler<FluidStack> fluidInputHandler,
            IInputHandler<ChemicalStack> chemicalInputHandler) {
        super(recipe, recheckAllErrors);
        this.itemInputHandler = itemInputHandler;
        this.fluidInputHandler = fluidInputHandler;
        this.chemicalInputHandler = chemicalInputHandler;
    }

    @Override
    protected final void calculateOperationsThisTick(OperationTracker tracker) {
        super.calculateOperationsThisTick(tracker);
        if (!tracker.shouldContinueChecking()) {
            return;
        }
        if (!recipe.itemInputs.isEmpty()) {
            recipeInputItems = itemInputHandler.getRecipeInput(recipe.itemInputs, v -> slotIndexCache = v);
            if (recipeInputItems.isEmpty()) {
                tracker.resetProgress(RecipeError.NOT_ENOUGH_INPUT);
                return;
            }
            itemInputHandler.calculateOperationsCanSupport(tracker, recipeInputItems, slotIndexCache);
        }
        if (recipe.fluidInput != null) {
            recipeInputFluid = fluidInputHandler.getRecipeInput(recipe.fluidInput);
            if (recipeInputFluid.isEmpty()) {
                tracker.resetProgress(RecipeError.NOT_ENOUGH_INPUT);
                return;
            }
            fluidInputHandler.calculateOperationsCanSupport(tracker, recipeInputFluid);
        }
        if (recipe.chemicalInput != null) {
            recipeInputChemical = chemicalInputHandler.getRecipeInput(recipe.chemicalInput);
            if (recipeInputChemical.isEmpty()) {
                tracker.resetProgress(RecipeError.NOT_ENOUGH_INPUT);
                return;
            }
            chemicalInputHandler.calculateOperationsCanSupport(tracker, recipeInputChemical);
        }
        calculateOutputOperationsThisTick(tracker);
    }

    protected abstract void calculateOutputOperationsThisTick(OperationTracker tracker);

    @Override
    public boolean isInputValid() {
        return recipe.test(itemInputHandler.getInput(), fluidInputHandler.getInput(), chemicalInputHandler.getInput());
    }

    @Override
    protected final void finishProcessing(int operations) {
        itemInputHandler.use(recipeInputItems, slotIndexCache, operations);
        fluidInputHandler.use(recipeInputFluid, operations);
        chemicalInputHandler.use(recipeInputChemical, operations);
        handleOutputs(operations);
    }

    protected abstract void handleOutputs(int operations);

}
