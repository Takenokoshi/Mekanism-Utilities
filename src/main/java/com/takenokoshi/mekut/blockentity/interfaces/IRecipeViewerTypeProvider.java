package com.takenokoshi.mekut.blockentity.interfaces;

import org.jetbrains.annotations.Nullable;

import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;

public interface IRecipeViewerTypeProvider {
    default @Nullable IRecipeViewerRecipeType<?> recipeViewerType() {
        return null;
    }
}
