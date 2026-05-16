package com.takenokoshi.mekut.recipe.building;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.registries.MekUtChemicals;
import com.takenokoshi.mekut.registries.MekUtItems;

import appeng.core.definitions.AEItems;
import mekanism.api.datagen.recipe.builder.ItemStackToChemicalRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IItemStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.data.recipes.RecipeOutput;

public class BuildChemicalConvertionRecipe {
    public static void build(RecipeOutput output) {
        IItemStackIngredientCreator creatorI = IngredientCreatorAccess.item();
        ItemStackToChemicalRecipeBuilder
                .chemicalConversion(
                        creatorI.from(MekUtItems.ACTIVATED_LAPIS_LAZULI),
                        MekUtChemicals.ACTIVATED_LAPIS_LAZULI.asStack(10))
                .build(output, MekUtConstants.rl("chemical_conversion/activated_lapis_lazuli_1"));
        ItemStackToChemicalRecipeBuilder
                .chemicalConversion(
                        creatorI.from(MekUtItems.ENRICHED_LAPIS_LAZULI),
                        MekUtChemicals.ACTIVATED_LAPIS_LAZULI.asStack(80))
                .build(output, MekUtConstants.rl("chemical_conversion/activated_lapis_lazuli_2"));
        ItemStackToChemicalRecipeBuilder
                .chemicalConversion(
                        creatorI.from(AEItems.SINGULARITY),
                        MekUtChemicals.SINGULARITY.asStack(10))
                .build(output, MekUtConstants.rl("chemical_conversion/singularity_1"));
        ItemStackToChemicalRecipeBuilder
                .chemicalConversion(
                        creatorI.from(MekUtItems.ENRICHED_SINGULARITY),
                        MekUtChemicals.SINGULARITY.asStack(80))
                .build(output, MekUtConstants.rl("chemical_conversion/singularity_2"));
        ItemStackToChemicalRecipeBuilder
                .chemicalConversion(
                        creatorI.from(MekUtItems.XP_CRYSTAL),
                        MekUtChemicals.XP.asStack(100000))
                .build(output, MekUtConstants.rl("chemical_conversion/xp"));
    }
}
