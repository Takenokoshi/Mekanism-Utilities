package com.takenokoshi.mekut.recipe.cached;

import java.util.function.BooleanSupplier;

import com.takenokoshi.mekut.recipe.input.IngredientInputHandler;
import com.takenokoshi.mekut.recipe.output.ChemicalOutputHandler;
import com.takenokoshi.mekut.recipe.output.ItemOutputHandler;
import com.takenokoshi.mekut.registries.MekUtChemicals;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmeltingRecipe;

public class TweakedSmeltingCachedRecipe extends AbstractCachedRecipe<SmeltingRecipe> {

    private final IngredientInputHandler inputHandler;
    private final ItemOutputHandler outputHandler;
    private final ChemicalOutputHandler xpOutputHandler;

    private ItemStack recipeInput = ItemStack.EMPTY;
    private final Ingredient ingredient;
    private final ItemStack recipeOutput;
    private final ChemicalStack xpOutput;

    public TweakedSmeltingCachedRecipe(SmeltingRecipe recipe, BooleanSupplier recheckAllErrors,
            IngredientInputHandler inputHandler, ItemOutputHandler outputHandler,
            ChemicalOutputHandler xpOutputHandler) {
        super(recipe, recheckAllErrors);
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.xpOutputHandler = xpOutputHandler;
        this.ingredient = recipe.getIngredients().get(0);
        this.recipeOutput = recipe.getResultItem(null);
        long xp = (long) (recipe.getExperience() * 100);
        xpOutput = xp < 1 ? ChemicalStack.EMPTY : MekUtChemicals.XP.asStack(xp);
    }

    protected void calculateOperationsThisTick(OperationTracker2 tracker) {
        super.calculateOperationsThisTick(tracker);
        recipeInput = inputHandler.getRecipeInput(ingredient);
        if (recipeInput.isEmpty()) {
            tracker.resetProgress(RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT);
        }
        inputHandler.calculateOperationsCanSupport(tracker, recipeInput);
        outputHandler.calculateOperationsCanSupport(tracker, recipeOutput);
    }

    @Override
    public boolean isInputValid() {
        return ingredient.test(inputHandler.getInput());
    }

    @Override
    protected void finishProcessing(int operations) {
        inputHandler.use(recipeInput, operations);
        outputHandler.handleOutput(recipeOutput, operations);
        xpOutputHandler.handleOutput(xpOutput, operations);
    }

}
