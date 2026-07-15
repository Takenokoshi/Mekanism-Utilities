package com.takenokoshi.mekut.recipe.cached;

import java.util.function.BooleanSupplier;

import com.glodblock.github.extendedae.recipe.CircuitCutterRecipe;
import com.takenokoshi.mekaddonlib.recipe.cached.AbstractCachedRecipe;

import mekanism.api.recipes.cache.CachedRecipe.OperationTracker;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.outputs.IOutputHandler;
import net.minecraft.world.item.ItemStack;

public class MekStyledCircuitCutterCachedRecipe extends AbstractCachedRecipe<CircuitCutterRecipe> {

    private final IInputHandler<ItemStack> inputHandler;
    private final IOutputHandler<ItemStack> outputHandler;

    private final ItemStackIngredient inputIngredient;

    private ItemStack recipeInput = ItemStack.EMPTY;
    private final ItemStack recipeOutput;

    public MekStyledCircuitCutterCachedRecipe(CircuitCutterRecipe recipe, BooleanSupplier recheckAllErrors,
            IInputHandler<ItemStack> inputHandler, IOutputHandler<ItemStack> outputHandler) {
        super(recipe, recheckAllErrors);
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.inputIngredient = IngredientCreatorAccess.item().from(recipe.getInput().getIngredient(),
                recipe.getInput().getAmount());
        this.recipeOutput=recipe.output.copy();
    }

    protected void calculateOperationsThisTick(OperationTracker tracker) {
        super.calculateOperationsThisTick(tracker);
        if (!tracker.shouldContinueChecking()) {
            return;
        }
        recipeInput = inputHandler.getRecipeInput(inputIngredient);
        if (recipeInput.isEmpty()) {
            tracker.resetProgress(RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT);
            return;
        }
        inputHandler.calculateOperationsCanSupport(tracker, recipeInput);
        outputHandler.calculateOperationsCanSupport(tracker, recipeOutput);
    }

    @Override
    public boolean isInputValid() {
        return inputIngredient.test(inputHandler.getInput());
    }

    @Override
    protected void finishProcessing(int operations) {
        inputHandler.use(recipeInput, operations);
        outputHandler.handleOutput(recipeOutput, operations);
    }

}
