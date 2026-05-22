package com.takenokoshi.mekut.mixin.mekanism.recipe;

import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import mekanism.api.recipes.cache.CachedRecipe.OperationTracker;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;

@Mixin(value = { OperationTracker.class }, remap = false)
public interface OperationTrackerMixin {

    @Invoker(value = "<init>")
    static OperationTracker mekanism_utilities$invokeInit(Set<RecipeError> lastErrors, boolean checkAll,
            int startingMax) {
        throw new AssertionError();
    };

    @Invoker("capAtMaxForEnergy")
    boolean mekanism_utilities$invokeCapAtMaxForEnergy();

    @Invoker("hasErrorsToCopy")
    boolean mekanism_utilities$invokeHasErrorsToCopy();

    @Accessor("currentMax")
    int mekanism_utilities$getCurrentMax();

    @Accessor("errors")
    Set<RecipeError> mekanism_utilities$getErrors();

    @Accessor("maxForEnergy")
    void mekanism_utilities$setMaxForEnergy(int value);
}
