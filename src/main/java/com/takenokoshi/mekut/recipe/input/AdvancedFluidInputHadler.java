package com.takenokoshi.mekut.recipe.input;

import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.ingredients.InputIngredient;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.inputs.InputHelper;
import net.neoforged.neoforge.fluids.FluidStack;

public class AdvancedFluidInputHadler implements IInputHandler<FluidStack> {

    protected final IInputHandler<FluidStack> delegate;
    private FluidStack suppliedStack = FluidStack.EMPTY;

    public AdvancedFluidInputHadler(IInputHandler<FluidStack> delegate) {
        this.delegate = delegate;
    }

    public static AdvancedFluidInputHadler create(IExtendedFluidTank fluidTank, RecipeError notEnoughError) {
        return new AdvancedFluidInputHadler(InputHelper.getInputHandler(fluidTank, notEnoughError));
    }

    @Override
    public FluidStack getInput() {
        return suppliedStack.isEmpty() ? delegate.getInput() : suppliedStack;
    }

    @Override
    public FluidStack getRecipeInput(InputIngredient<FluidStack> ingredient) {
        return ingredient.testType(suppliedStack)
                ? ingredient.getMatchingInstance(suppliedStack)
                : delegate.getRecipeInput(ingredient);
    }

    @Override
    public void calculateOperationsCanSupport(OperationTracker tracker, FluidStack recipeInput, int usageMultiplier) {
        if (suppliedStack.isEmpty() || !FluidStack.isSameFluidSameComponents(suppliedStack, recipeInput)) {
            delegate.calculateOperationsCanSupport(tracker, recipeInput, usageMultiplier);
        }
    }

    @Override
    public void use(FluidStack recipeInput, int operations) {
        if (suppliedStack.isEmpty() || !FluidStack.isSameFluidSameComponents(suppliedStack, recipeInput)) {
            delegate.use(recipeInput, operations);
        }
    }

    public void setSuppliedStack(FluidStack value) {
        suppliedStack = value;
    }

}
