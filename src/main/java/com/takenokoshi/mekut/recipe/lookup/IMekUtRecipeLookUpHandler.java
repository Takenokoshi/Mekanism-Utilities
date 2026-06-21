package com.takenokoshi.mekut.recipe.lookup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekut.blockentity.interfaces.IRecipeViewerTypeProvider;
import com.takenokoshi.mekut.recipe.cached.ICachedRecipe;
import com.takenokoshi.mekut.recipe.type.IMekUtRecipeTypeProvider;

import mekanism.api.IContentsListener;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IMekUtRecipeLookUpHandler<RECIPE extends Recipe<?>>
        extends IContentsListener, IRecipeViewerTypeProvider {

    @Nullable
    default Level getLevel() {
        if (this instanceof BlockEntity tile) {
            return tile.getLevel();
        } else if (this instanceof Entity entity) {
            return entity.level();
        }
        return null;
    }

    default @Nullable IRecipeViewerRecipeType<RECIPE> recipeViewerType() {
        return null;
    }

    default int getSavedOperatingTicks(int cacheIndex) {
        return 0;
    }

    @Nullable
    RECIPE getRecipe(int cacheIndex);

    @NotNull
    ICachedRecipe<RECIPE> createNewCachedRecipe(@NotNull RECIPE recipe, int cacheIndex);

    default void onCachedRecipeChanged(@Nullable ICachedRecipe<RECIPE> cachedRecipe, int cacheIndex) {
        clearRecipeErrors(cacheIndex);
    }

    default void clearRecipeErrors(int cacheIndex) {
    }

    @NotNull
    IMekUtRecipeTypeProvider<?, RECIPE, ?> getRecipeType();
}