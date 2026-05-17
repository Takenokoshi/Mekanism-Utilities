package com.takenokoshi.mekut.recipe.lookup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekut.recipe.cached.AbstractCachedRecipe;

import net.minecraft.world.item.crafting.Recipe;

public interface IMekUtCachedRecipeHolder<RECIPE extends Recipe<?>> {

    @Nullable
    default AbstractCachedRecipe<RECIPE> getUpdatedCache(int cacheIndex) {
        boolean cacheInvalid = invalidateCache();
        AbstractCachedRecipe<RECIPE> currentCache = cacheInvalid ? null : getCachedRecipe(cacheIndex);
        if (currentCache == null || !currentCache.isInputValid()) {
            if (cacheInvalid || !hasNoRecipe(cacheIndex)) {
                RECIPE recipe = getRecipe(cacheIndex);
                if (recipe == null) {
                    setHasNoRecipe(cacheIndex);
                } else {
                    AbstractCachedRecipe<RECIPE> cached = createNewCachedRecipe(recipe, cacheIndex);
                    if (currentCache == null || cached != null) {
                        if (currentCache == null && cached != null) {
                            loadSavedData(cached, cacheIndex);
                        }
                        return cached;
                    }
                }
            }
        }
        return currentCache;
    }

    default void loadSavedData(@NotNull AbstractCachedRecipe<RECIPE> cached, int cacheIndex) {
        cached.loadSavedOperatingTicks(getSavedOperatingTicks(cacheIndex));
    }

    default int getSavedOperatingTicks(int cacheIndex) {
        return 0;
    }

    @Nullable
    AbstractCachedRecipe<RECIPE> getCachedRecipe(int cacheIndex);

    @Nullable
    RECIPE getRecipe(int cacheIndex);

    @Nullable
    AbstractCachedRecipe<RECIPE> createNewCachedRecipe(@NotNull RECIPE recipe, int cacheIndex);

    default boolean invalidateCache() {
        return false;
    }

    default void setHasNoRecipe(int cacheIndex) {
    }

    default boolean hasNoRecipe(int cacheIndex) {
        return false;
    }
}