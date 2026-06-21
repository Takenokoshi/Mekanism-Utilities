package com.takenokoshi.mekut.recipe.recipe.basic;

import com.takenokoshi.mekut.recipe.recipe.prefab.FluidToItemRecipe;
import com.takenokoshi.mekut.registries.MekUtRecipeSerializers;
import com.takenokoshi.mekut.registries.MekUtRecipeTypes;

import mekanism.api.recipes.ingredients.FluidStackIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class BasicIceMakingRecipe extends FluidToItemRecipe {

    public BasicIceMakingRecipe(FluidStackIngredient input,
            ItemStack output) {
        super(MekUtRecipeTypes.ICE_MAKING.get(), input, output);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MekUtRecipeSerializers.ICE_MAKING.get();
    }
    
}
