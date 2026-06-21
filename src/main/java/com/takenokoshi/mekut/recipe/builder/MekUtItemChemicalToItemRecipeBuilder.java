package com.takenokoshi.mekut.recipe.builder;

import com.takenokoshi.mekut.recipe.recipe.basic.BasicChemicalCutRecipe;

import mekanism.api.datagen.recipe.builder.ItemStackChemicalToItemStackRecipeBuilder;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.world.item.ItemStack;

public class MekUtItemChemicalToItemRecipeBuilder extends ItemStackChemicalToItemStackRecipeBuilder {

    protected MekUtItemChemicalToItemRecipeBuilder(ItemStackIngredient itemInput, ChemicalStackIngredient chemicalInput,
            ItemStack output, boolean perTickUsage, Factory factory) {
        super(itemInput, chemicalInput, output, perTickUsage, factory);
    }

    public static MekUtItemChemicalToItemRecipeBuilder chemicalCut(ItemStackIngredient itemInput,
            ChemicalStackIngredient chemicalInput,
            ItemStack output, boolean perTickUsage) {
        return new MekUtItemChemicalToItemRecipeBuilder(itemInput, chemicalInput, output, perTickUsage,
                BasicChemicalCutRecipe::new);
    }

}
