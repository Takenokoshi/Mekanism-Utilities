package com.takenokoshi.mekut.recipe.cached;

import java.util.function.BooleanSupplier;

import com.takenokoshi.mekut.recipe.recipe.prefab.FluidToItemRecipe;

import mekanism.api.recipes.cache.CachedRecipe.OperationTracker;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.outputs.IOutputHandler;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;

public class FluidToItemCachedRecipe extends AbstractCachedRecipe<FluidToItemRecipe> {

    private final IInputHandler<FluidStack> inputHandler;
    private final IOutputHandler<ItemStack> outputHandler;

    private FluidStack input = FluidStack.EMPTY;

    public FluidToItemCachedRecipe(FluidToItemRecipe recipe, BooleanSupplier recheckAllErrors,
            IInputHandler<FluidStack> inputHandler, IOutputHandler<ItemStack> outputHandler) {
        super(recipe, recheckAllErrors);
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
    }

    @Override
    protected void calculateOperationsThisTick(OperationTracker tracker) {
        super.calculateOperationsThisTick(tracker);
        if (!tracker.shouldContinueChecking()) {
            return;
        }
        input = inputHandler.getRecipeInput(recipe.input);
        if (input.isEmpty()) {
            tracker.mismatchedRecipe();
            return;
        }
        inputHandler.calculateOperationsCanSupport(tracker, input);
        outputHandler.calculateOperationsCanSupport(tracker, recipe.output);
    }


    @Override
    public boolean isInputValid() {
        return recipe.test(input);
    }

    @Override
    protected void finishProcessing(int operations) {
        inputHandler.use(input, operations);
        outputHandler.handleOutput(recipe.output, operations);
    }

}
