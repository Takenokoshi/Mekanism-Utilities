package com.takenokoshi.mekut.recipe.basic;

import com.takenokoshi.mekut.registries.MekUtRecipeSerializers;
import com.takenokoshi.mekut.registries.MekUtRecipeTypes;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.basic.BasicChemicalToChemicalRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class BasicSPSRecipe extends BasicChemicalToChemicalRecipe {

    public BasicSPSRecipe(ChemicalStackIngredient input, ChemicalStack output) {
        super(input, output, MekUtRecipeTypes.SPS.get());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MekUtRecipeSerializers.SPS.get();
    }

}
