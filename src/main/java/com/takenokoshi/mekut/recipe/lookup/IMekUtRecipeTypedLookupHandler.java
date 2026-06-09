package com.takenokoshi.mekut.recipe.lookup;

import org.jetbrains.annotations.NotNull;

import com.takenokoshi.mekut.recipe.type.IMekUtRecipeTypeProvider;

import mekanism.common.recipe.lookup.cache.IInputRecipeCache;
import net.minecraft.world.item.crafting.Recipe;

public interface IMekUtRecipeTypedLookupHandler<RECIPE extends Recipe<?>, INPUT_CACHE extends IInputRecipeCache>
        extends IMekUtRecipeLookUpHandler<RECIPE> {

    @NotNull
    @Override
    IMekUtRecipeTypeProvider<?, RECIPE, INPUT_CACHE> getRecipeType();
}