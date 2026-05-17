package com.takenokoshi.mekut.registration;

import com.takenokoshi.mekut.recipe.IMekUtRecipeTypeProvider;
import com.takenokoshi.mekut.recipe.MekUtRecipeType;

import mekanism.common.recipe.lookup.cache.IInputRecipeCache;
import mekanism.common.registration.MekanismDeferredHolder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;

public class MekUtRecipeTypeRegistryObject<VANILLA_INPUT extends RecipeInput, RECIPE extends Recipe<VANILLA_INPUT>, INPUT_CACHE extends IInputRecipeCache>
        extends MekanismDeferredHolder<RecipeType<?>, MekUtRecipeType<VANILLA_INPUT, RECIPE, INPUT_CACHE>>
        implements IMekUtRecipeTypeProvider<VANILLA_INPUT, RECIPE, INPUT_CACHE> {

    public MekUtRecipeTypeRegistryObject(ResourceKey<RecipeType<?>> key) {
        super(key);
    }

    @Override
    public MekUtRecipeType<VANILLA_INPUT, RECIPE, INPUT_CACHE> getRecipeType() {
        return this.value();
    }

}
