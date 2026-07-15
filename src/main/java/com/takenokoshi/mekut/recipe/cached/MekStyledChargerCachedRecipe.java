package com.takenokoshi.mekut.recipe.cached;

import java.util.function.BooleanSupplier;

import com.takenokoshi.mekaddonlib.recipe.cached.AbstractCachedRecipe;
import com.takenokoshi.mekut.recipe.input.AdvancedIngredientInputHandler;
import com.takenokoshi.mekut.recipe.output.ItemOutputHandler;

import appeng.recipes.handlers.ChargerRecipe;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import net.minecraft.world.item.ItemStack;

public class MekStyledChargerCachedRecipe extends AbstractCachedRecipe<ChargerRecipe> {

    private final AdvancedIngredientInputHandler inputHandler;
    private final ItemOutputHandler outputHandler;

    private ItemStack recipeInput = ItemStack.EMPTY;

    public MekStyledChargerCachedRecipe(ChargerRecipe recipe, BooleanSupplier recheckAllErrors,
            AdvancedIngredientInputHandler inputHandler, ItemOutputHandler outputHandler) {
        super(recipe, recheckAllErrors);
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
    }

    @Override
    protected void calculateOperationsThisTick(OperationTracker tracker) {
        super.calculateOperationsThisTick(tracker);
        recipeInput = inputHandler.getRecipeInput(recipe.ingredient);
        if (recipeInput.isEmpty()) {
            tracker.resetProgress(RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT);
            return;
        }
        inputHandler.calculateOperationsCanSupport(tracker, recipeInput);
        outputHandler.calculateOperationsCanSupport(tracker, recipe.result);
    }

    @Override
    public boolean isInputValid() {
        return recipe.ingredient.test(inputHandler.getInput());
    }

    @Override
    protected void finishProcessing(int operations) {
        inputHandler.use(recipeInput, operations);
        outputHandler.handleOutput(recipe.result, operations);
    }

}
