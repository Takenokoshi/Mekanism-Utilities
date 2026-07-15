package com.takenokoshi.mekut.recipe.input;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.ingredients.InputIngredient;
import mekanism.api.recipes.inputs.ILongInputHandler;
import mekanism.api.recipes.inputs.InputHelper;

public class AdvancedChemicalInputHandler implements ILongInputHandler<ChemicalStack> {

    protected final ILongInputHandler<ChemicalStack> delegate;

    private ChemicalStack suppliedStack = ChemicalStack.EMPTY;

    public AdvancedChemicalInputHandler(ILongInputHandler<ChemicalStack> delegate) {
        this.delegate = delegate;
    }

    public static AdvancedChemicalInputHandler create(IChemicalTank tank, RecipeError notEnoughError) {
        return new AdvancedChemicalInputHandler(InputHelper.getInputHandler(tank, notEnoughError));
    }

    public static AdvancedChemicalInputHandler createConstant(IChemicalTank tank) {
        return new AdvancedChemicalInputHandler(InputHelper.getConstantInputHandler(tank));
    }

    @Override
    public ChemicalStack getInput() {
        return suppliedStack.isEmpty() ? delegate.getInput() : suppliedStack;
    }

    @Override
    public ChemicalStack getRecipeInput(InputIngredient<ChemicalStack> ingredient) {
        return ingredient.testType(suppliedStack)
                ? ingredient.getMatchingInstance(suppliedStack)
                : delegate.getRecipeInput(ingredient);
    }

    @Override
    public void calculateOperationsCanSupport(OperationTracker tracker, ChemicalStack recipeInput,
            long usageMultiplier) {
        if (suppliedStack.isEmpty() || !ChemicalStack.isSameChemical(suppliedStack, recipeInput)) {
            delegate.calculateOperationsCanSupport(tracker, recipeInput, usageMultiplier);
        }
    }

    @Override
    public void use(ChemicalStack recipeInput, long operations) {
        if (suppliedStack.isEmpty() || !ChemicalStack.isSameChemical(suppliedStack, recipeInput)) {
            delegate.use(recipeInput, operations);
        }
    }

    public void setSuppliedStack(ChemicalStack value) {
        suppliedStack = value;
    }
}
