package com.takenokoshi.mekut.recipe.inputcache;

import com.takenokoshi.mekaddonlib.recipe.type.MekALRecipeType;

import mekanism.common.recipe.lookup.cache.IInputRecipeCache;

public class EmptyInputRecipeCache implements IInputRecipeCache {

    public EmptyInputRecipeCache() {
    }

    public EmptyInputRecipeCache(MekALRecipeType<?, ?, ?> recipeType) {
        this();
    }

    @Override
    public void clear() {
    }

}
