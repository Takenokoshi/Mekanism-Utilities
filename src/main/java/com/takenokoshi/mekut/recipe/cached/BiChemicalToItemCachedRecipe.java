package com.takenokoshi.mekut.recipe.cached;

import java.util.function.BooleanSupplier;

import com.takenokoshi.mekaddonlib.recipe.cached.AbstractCachedRecipe;
import com.takenokoshi.mekut.recipe.recipe.prefab.BiChemicalToItemRecipe;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.outputs.IOutputHandler;
import net.minecraft.world.item.ItemStack;

public class BiChemicalToItemCachedRecipe extends AbstractCachedRecipe<BiChemicalToItemRecipe> {

    private final IInputHandler<ChemicalStack> leftInputHandler;
    private final IInputHandler<ChemicalStack> rightInputHandler;
    private final IOutputHandler<ItemStack> outputHandler;
    private ChemicalStack leftHandlerInput = ChemicalStack.EMPTY;
    private ChemicalStack rightHandlerInput = ChemicalStack.EMPTY;
    private ItemStack output;

    public BiChemicalToItemCachedRecipe(BiChemicalToItemRecipe recipe, BooleanSupplier recheckAllErrors,
            IInputHandler<ChemicalStack> leftInputHandler, IInputHandler<ChemicalStack> rightInputHandler,
            IOutputHandler<ItemStack> outputHandler) {
        super(recipe, recheckAllErrors);
        this.leftInputHandler = leftInputHandler;
        this.rightInputHandler = rightInputHandler;
        this.outputHandler = outputHandler;
    }

    @Override
    protected void calculateOperationsThisTick(OperationTracker tracker) {
        super.calculateOperationsThisTick(tracker);
        if (!tracker.shouldContinueChecking()) {
            return;
        }
        leftHandlerInput = leftInputHandler.getRecipeInput(recipe.getLeftInput());
        rightHandlerInput = rightInputHandler.getRecipeInput(recipe.getRightInput());
        if (leftHandlerInput.isEmpty() || rightHandlerInput.isEmpty()) {
            // try invert input
            leftHandlerInput = leftInputHandler.getRecipeInput(recipe.getRightInput());
            rightHandlerInput = rightInputHandler.getRecipeInput(recipe.getLeftInput());
            if (leftHandlerInput.isEmpty() || rightHandlerInput.isEmpty()) {
                tracker.mismatchedRecipe();
                return;
            }
        }
        leftInputHandler.calculateOperationsCanSupport(tracker, leftHandlerInput);
        rightInputHandler.calculateOperationsCanSupport(tracker, rightHandlerInput);
        output = recipe.getOutput(rightHandlerInput, leftHandlerInput);
        outputHandler.calculateOperationsCanSupport(tracker, output);
    }

    @Override
    public boolean isInputValid() {
        return recipe.test(leftInputHandler.getInput(), rightInputHandler.getInput());
    }

    @Override
    protected void finishProcessing(int operations) {
        leftInputHandler.use(leftHandlerInput, operations);
        rightInputHandler.use(rightHandlerInput, operations);
        outputHandler.handleOutput(output, operations);
    }

}