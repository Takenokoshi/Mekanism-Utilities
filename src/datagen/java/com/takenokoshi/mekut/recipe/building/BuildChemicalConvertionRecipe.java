package com.takenokoshi.mekut.recipe.building;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.registries.MekUtChemicals;
import com.takenokoshi.mekut.registries.MekUtItems;

import appeng.core.definitions.AEItems;
import mekanism.api.datagen.recipe.builder.ItemStackToChemicalRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IItemStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.level.ItemLike;

public class BuildChemicalConvertionRecipe {
    public static void build(RecipeOutput output) {
        IItemStackIngredientCreator creatorI = IngredientCreatorAccess.item();
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
                        creatorI.from(1, new ItemLike[] { AEItems.FLUIX_CRYSTAL, AEItems.FLUIX_DUST }),
                        MekUtChemicals.FLUIX.asStack(10))
                .build(output, MekUtConstants.rl("chemical_conversion/fluix_1"));
        ItemStackToChemicalRecipeBuilder
                .chemicalConversion(
                        creatorI.from(MekUtItems.ENRICHED_FLUIX),
                        MekUtChemicals.FLUIX.asStack(80))
                .build(output, MekUtConstants.rl("chemical_conversion/fluix_2"));
        ItemStackToChemicalRecipeBuilder
                .chemicalConversion(
                        creatorI.from(MekUtItems.XP_CRYSTAL),
                        MekUtChemicals.XP.asStack(100000))
                .build(output, MekUtConstants.rl("chemical_conversion/xp"));
    }
}
