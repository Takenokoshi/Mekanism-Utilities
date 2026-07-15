package com.takenokoshi.mekut.recipe.cached;

import java.util.function.BooleanSupplier;

import com.takenokoshi.mekaddonlib.recipe.cached.AbstractCachedRecipe;
import com.takenokoshi.mekut.recipe.output.HeatOutputHandler;
import com.takenokoshi.mekut.recipe.recipe.prefab.ChemicalToChemicalHeatRecipe;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.outputs.IOutputHandler;

public class ChemicalToChemicalHeatCachedRecipe extends AbstractCachedRecipe<ChemicalToChemicalHeatRecipe> {

    private final IInputHandler<ChemicalStack> inputHandler;
    private final IOutputHandler<ChemicalStack> outputHandler;
    private final HeatOutputHandler heatOutputHandler;

    private ChemicalStack input = ChemicalStack.EMPTY;
    private ChemicalStack output = ChemicalStack.EMPTY;

    public ChemicalToChemicalHeatCachedRecipe(ChemicalToChemicalHeatRecipe recipe,
            BooleanSupplier recheckAllErrors,
            IInputHandler<ChemicalStack> inputHandler,
            IOutputHandler<ChemicalStack> outputHandler, HeatOutputHandler heatOutputHandler) {
        super(recipe, recheckAllErrors);
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.heatOutputHandler = heatOutputHandler;
    }

    @Override
    protected void calculateOperationsThisTick(OperationTracker tracker) {
        super.calculateOperationsThisTick(tracker);
        if (!tracker.shouldContinueChecking()) {
            return;
        }
        input = inputHandler.getRecipeInput(recipe.getInput());
        if (input.isEmpty()) {
            tracker.mismatchedRecipe();
            return;
        }
        output = recipe.getOutput(input);
        inputHandler.calculateOperationsCanSupport(tracker, input);
        outputHandler.calculateOperationsCanSupport(tracker, output);
        heatOutputHandler.calculateOperationsCanSupport(tracker, recipe.heatGeneration);
    }

    @Override
    public boolean isInputValid() {
        return recipe.test(inputHandler.getInput());
    }

    @Override
    protected void finishProcessing(int operations) {
        inputHandler.use(input, operations);
        outputHandler.handleOutput(output, operations);
        heatOutputHandler.handleOutput(recipe.heatGeneration, operations);
    }

}
