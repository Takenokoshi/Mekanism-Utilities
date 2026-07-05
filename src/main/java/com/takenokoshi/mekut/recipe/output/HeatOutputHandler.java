package com.takenokoshi.mekut.recipe.output;

import mekanism.api.heat.IHeatCapacitor;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;

public record HeatOutputHandler(IHeatCapacitor heatCapacitor, double tempCap, RecipeError toHotError) {
    public void calculateOperationsCanSupport(OperationTracker tracker, double toOutput) {
        if (heatCapacitor.getTemperature() > tempCap) {
            tracker.resetProgress(toHotError);
        }
    }

    public void handleOutput(double toOutput, int operations) {
        heatCapacitor.handleHeat(toOutput * operations);
    }
}