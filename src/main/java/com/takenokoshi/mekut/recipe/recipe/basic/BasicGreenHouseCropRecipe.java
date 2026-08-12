package com.takenokoshi.mekut.recipe.recipe.basic;

import java.util.List;

import com.takenokoshi.mekut.recipe.output.MekUtChanceOutput;
import com.takenokoshi.mekut.recipe.recipe.prefab.GreenHouseCropRecipe;
import com.takenokoshi.mekut.registries.MekUtRecipeSerializers;
import com.takenokoshi.mekut.registries.MekUtRecipeTypes;

import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class BasicGreenHouseCropRecipe extends GreenHouseCropRecipe {

    public BasicGreenHouseCropRecipe(
            ItemStackIngredient cropIngredient, ItemStackIngredient soilIngredient, List<MekUtChanceOutput> outputs,
            int duration) {
        super(MekUtRecipeTypes.GREEN_HOUSE_CROP.get(), cropIngredient, soilIngredient, outputs, duration);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MekUtRecipeSerializers.GREEN_HOUSE_CROP.get();
    }

}
