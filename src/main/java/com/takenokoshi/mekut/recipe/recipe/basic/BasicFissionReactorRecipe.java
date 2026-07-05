package com.takenokoshi.mekut.recipe.recipe.basic;

import com.takenokoshi.mekut.recipe.recipe.prefab.ChemicalToChemicalHeatRecipe;
import com.takenokoshi.mekut.registries.MekUtRecipeSerializers;
import com.takenokoshi.mekut.registries.MekUtRecipeTypes;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class BasicFissionReactorRecipe extends ChemicalToChemicalHeatRecipe {

    public BasicFissionReactorRecipe(ChemicalStackIngredient input, ChemicalStack output, double heatGeneration) {
        super(input, output, heatGeneration, MekUtRecipeTypes.FISSION_REACTOR.get());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MekUtRecipeSerializers.FISSION_REACTOR.get();
    }

}
