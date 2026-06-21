package com.takenokoshi.mekut.recipe.recipe.basic;

import com.takenokoshi.mekut.recipe.recipe.prefab.BiChemicalToItemRecipe;
import com.takenokoshi.mekut.registries.MekUtRecipeSerializers;
import com.takenokoshi.mekut.registries.MekUtRecipeTypes;

import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class BasicStellarGenesisRecipe extends BiChemicalToItemRecipe {

    public BasicStellarGenesisRecipe(
            ChemicalStackIngredient leftInput, ChemicalStackIngredient rightInput, ItemStack output) {
        super(MekUtRecipeTypes.STELLAR_GENESIS.get(), leftInput, rightInput, output);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MekUtRecipeSerializers.STELLAR_GENESIS.get();
    }

}
