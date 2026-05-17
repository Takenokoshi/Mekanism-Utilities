package com.takenokoshi.mekut.recipe.output;

import com.takenokoshi.mekut.recipe.cached.OperationTracker2;

import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.math.MathUtils;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;

public class ChemicalOutputHandler {
    private final IChemicalTank tank;
    private final RecipeError notEnoughSpaceError;

    public ChemicalOutputHandler(IChemicalTank tank, RecipeError notEnoughSpaceError) {
        this.tank = tank;
        this.notEnoughSpaceError = notEnoughSpaceError;
    }

    public void calculateOperationsCanSupport(OperationTracker2 tracker, ChemicalStack toOutput) {
        if (!toOutput.isEmpty()) {
            ChemicalStack maxOutput = toOutput.copyWithAmount(Long.MAX_VALUE);
            ChemicalStack remainder = tank.insert(maxOutput, Action.SIMULATE, AutomationType.INTERNAL);
            long amountUsed = maxOutput.getAmount() - remainder.getAmount();
            int operations = MathUtils.clampToInt(amountUsed / toOutput.getAmount());
            tracker.updateOperations(operations);
            if (operations == 0) {
                if (amountUsed == 0 && tank.getNeeded() > 0) {
                    tracker.addError(RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT);
                } else {
                    tracker.addError(notEnoughSpaceError);
                }
            }
        }
    }

    public void handleOutput(ChemicalStack toOutput, int operations) {
        if (operations == 0) {
            return;
        }
        ChemicalStack output = toOutput.copyWithAmount(toOutput.getAmount() * operations);
        tank.insert(output, Action.EXECUTE, AutomationType.INTERNAL);
    }
}
