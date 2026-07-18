package com.takenokoshi.mekut.recipe.cached;

import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;

import com.takenokoshi.mekaddonlib.recipe.cached.ICachedRecipe;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import mekanism.api.energy.IEnergyContainer;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;

public abstract class BasicCachedRecipe<RECIPE extends MekanismRecipe<?>> extends CachedRecipe<RECIPE>
        implements ICachedRecipe<RECIPE> {

    protected BasicCachedRecipe(RECIPE recipe, BooleanSupplier recheckAllErrors) {
        super(recipe, recheckAllErrors);
    }

    @Override
    public BasicCachedRecipe<RECIPE> setActive(BooleanConsumer setActive) {
        super.setActive(setActive);
        return this;
    }

    @Override
    public BasicCachedRecipe<RECIPE> setBaselineMaxOperations(IntSupplier baselineMaxOperations) {
        super.setBaselineMaxOperations(baselineMaxOperations);
        return this;
    }

    @Override
    public BasicCachedRecipe<RECIPE> setCanHolderFunction(BooleanSupplier canHolderFunction) {
        super.setCanHolderFunction(canHolderFunction);
        return this;
    }

    @Override
    public BasicCachedRecipe<RECIPE> setEnergyRequirements(LongSupplier perTickEnergy, IEnergyContainer energyContainer) {
        super.setEnergyRequirements(perTickEnergy, energyContainer);
        return this;
    }

    @Override
    public BasicCachedRecipe<RECIPE> setErrorsChanged(Consumer<Set<RecipeError>> onErrorsChange) {
        super.setErrorsChanged(onErrorsChange);
        return this;
    }

    @Override
    public BasicCachedRecipe<RECIPE> setOnFinish(Runnable onFinish) {
        super.setOnFinish(onFinish);
        return this;
    }

    @Override
    public BasicCachedRecipe<RECIPE> setOperatingTicksChanged(IntConsumer operatingTicksChanged) {
        super.setOperatingTicksChanged(operatingTicksChanged);
        return this;
    }

    @Override
    public BasicCachedRecipe<RECIPE> setPostProcessOperations(Consumer<OperationTracker> postProcessOperations) {
        super.setPostProcessOperations(postProcessOperations);
        return this;
    }

    @Override
    public BasicCachedRecipe<RECIPE> setRequiredTicks(IntSupplier requiredTicks) {
        super.setRequiredTicks(requiredTicks);
        return this;
    }

}
