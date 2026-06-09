package com.takenokoshi.mekut.recipe.inputcache;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekut.recipe.type.MekUtRecipeType;

import mekanism.common.recipe.lookup.cache.IInputRecipeCache;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;

//another ver of AbstractInputRecipeCache
public abstract class MUAbstractInputRecipeCache<RECIPE extends Recipe<?>> implements IInputRecipeCache {
    protected final MekUtRecipeType<?, RECIPE, ?> recipeType;
    protected boolean initialized;

    protected MUAbstractInputRecipeCache(MekUtRecipeType<?, RECIPE, ?> recipeType) {
        this.recipeType = recipeType;
    }

    protected void initCacheIfNeeded(@Nullable Level world) {
        if (!initialized) {
            initialized = true;
            initCache(recipeType.getRecipes(world));
        }
    }

    @Override
    public void clear() {
        initialized = false;
    }

    protected abstract void initCache(List<RecipeHolder<RECIPE>> recipes);
}
