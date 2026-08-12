package com.takenokoshi.mekut.recipe.recipe.basic;

import com.takenokoshi.mekut.recipe.recipe.prefab.GreenHouseFertilizerRecipe;
import com.takenokoshi.mekut.registries.MekUtRecipeSerializers;
import com.takenokoshi.mekut.registries.MekUtRecipeTypes;

import mekanism.api.recipes.ingredients.FluidStackIngredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class BasicGreenHouseFertilizerRecipe extends GreenHouseFertilizerRecipe {

    public BasicGreenHouseFertilizerRecipe(
            FluidStackIngredient fertilizerInredient, int outputMultiplier, double durationMultiplier) {
        super(MekUtRecipeTypes.GREEN_HOUSE_FERTILIZER.get(), fertilizerInredient, outputMultiplier, durationMultiplier);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MekUtRecipeSerializers.GREEN_HOUSE_FERTILIZER.get();
    }

}
