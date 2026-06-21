package com.takenokoshi.mekut.recipe.recipe.basic;

import com.takenokoshi.mekut.registries.MekUtRecipeSerializers;
import com.takenokoshi.mekut.registries.MekUtRecipeTypes;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.basic.BasicChemicalChemicalToChemicalRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class BasicLazerCompressRecipe extends BasicChemicalChemicalToChemicalRecipe {

    public BasicLazerCompressRecipe(ChemicalStackIngredient leftInput, ChemicalStackIngredient rightInput,
            ChemicalStack output) {
        super(leftInput, rightInput, output, MekUtRecipeTypes.LAZER_COMPRESS.get());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MekUtRecipeSerializers.LAZER_COMPRESS.get();
    }
    
}
