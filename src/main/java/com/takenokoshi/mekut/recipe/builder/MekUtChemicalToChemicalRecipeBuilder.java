package com.takenokoshi.mekut.recipe.builder;

import com.takenokoshi.mekut.recipe.recipe.basic.BasicSPSRecipe;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.datagen.recipe.builder.ChemicalToChemicalRecipeBuilder;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;

public class MekUtChemicalToChemicalRecipeBuilder extends ChemicalToChemicalRecipeBuilder {

    protected MekUtChemicalToChemicalRecipeBuilder(ChemicalStackIngredient input, ChemicalStack output,
            Factory factory) {
        super(input, output, factory);
    }

    public static MekUtChemicalToChemicalRecipeBuilder sps(ChemicalStackIngredient input, ChemicalStack output) {
        return new MekUtChemicalToChemicalRecipeBuilder(input, output, BasicSPSRecipe::new);
    }

}
