package com.takenokoshi.mekut.recipe.builder;

import com.takenokoshi.mekut.recipe.recipe.basic.BasicLazerCompressRecipe;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.datagen.recipe.builder.ChemicalChemicalToChemicalRecipeBuilder;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;

public class MUChemicalChemicalToChemicalRecipeBuilder extends ChemicalChemicalToChemicalRecipeBuilder {

    protected MUChemicalChemicalToChemicalRecipeBuilder(ChemicalStackIngredient leftInput,
            ChemicalStackIngredient rightInput, ChemicalStack output, Factory factory) {
        super(leftInput, rightInput, output, factory);
    }

    public static MUChemicalChemicalToChemicalRecipeBuilder nuclearSynthesize(ChemicalStackIngredient leftInput,
            ChemicalStackIngredient rightInput, ChemicalStack output) {
        return new MUChemicalChemicalToChemicalRecipeBuilder(leftInput, rightInput, output,
                BasicLazerCompressRecipe::new);
    }

}
