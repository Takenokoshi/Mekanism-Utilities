package com.takenokoshi.mekut.recipe.builder;

import com.takenokoshi.mekut.recipe.recipe.basic.BasicStellarGenesisRecipe;
import com.takenokoshi.mekut.recipe.recipe.prefab.BiChemicalToItemRecipe;

import mekanism.api.datagen.recipe.MekanismRecipeBuilder;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public class BiChemicalToItemRecipeBuilder extends MekanismRecipeBuilder<BiChemicalToItemRecipeBuilder> {

    protected final Factory factory;
    protected final ChemicalStackIngredient leftInput;
    protected final ChemicalStackIngredient rightInput;
    protected final ItemStack output;

    protected BiChemicalToItemRecipeBuilder(Factory factory, ChemicalStackIngredient leftInput,
            ChemicalStackIngredient rightInput, ItemStack output) {
        this.factory = factory;
        this.leftInput = leftInput;
        this.rightInput = rightInput;
        this.output = output;
    }

    public static BiChemicalToItemRecipeBuilder stellarGenesis(ChemicalStackIngredient leftInput,
            ChemicalStackIngredient rightInput, ItemStack output) {
        return new BiChemicalToItemRecipeBuilder(BasicStellarGenesisRecipe::new, leftInput, rightInput, output);
    }

    @Override
    protected Recipe<?> asRecipe() {
        return factory.create(leftInput, rightInput, output);
    }

    @FunctionalInterface
    protected static interface Factory {
        BiChemicalToItemRecipe create(ChemicalStackIngredient left, ChemicalStackIngredient right,
                ItemStack output);
    }

}
