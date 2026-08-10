package com.takenokoshi.mekut.recipe.building;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.registries.MekUtChemicals;
import com.takenokoshi.mekut.registries.MekUtItems;

import mekanism.api.datagen.recipe.builder.ItemStackToChemicalRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IItemStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public class ChemicalConvertionRecipes {
    public static void build(RecipeOutput output) {
        IItemStackIngredientCreator creatorI = IngredientCreatorAccess.item();
        ItemStackToChemicalRecipeBuilder
                .chemicalConversion(
                        creatorI.from(MekUtItems.REFINED_LAPIS_LAZULI_DUST,1),
                        MekUtChemicals.REFINED_LAPIS_LAZULI.asStack(10))
                .build(output, MekUtConstants.rl("chemical_conversion/refined_lapis_lazuli_1"));
        ItemStackToChemicalRecipeBuilder
                .chemicalConversion(
                        creatorI.from(MekUtItems.ENRICHED_LAPIS_LAZULI),
                        MekUtChemicals.REFINED_LAPIS_LAZULI.asStack(80))
                .build(output, MekUtConstants.rl("chemical_conversion/refined_lapis_lazuli_2"));
        ItemStackToChemicalRecipeBuilder
                .chemicalConversion(
                        creatorI.from(1, new ItemLike[] { Items.AMETHYST_SHARD, MekUtItems.AMETHYST_DUST,  }),
                        MekUtChemicals.AMETHYST.asStack(10))
                .build(output, MekUtConstants.rl("chemical_conversion/amethyst_1"));
        ItemStackToChemicalRecipeBuilder
                .chemicalConversion(
                        creatorI.from(MekUtItems.ENRICHED_AMETHYST),
                        MekUtChemicals.AMETHYST.asStack(80))
                .build(output, MekUtConstants.rl("chemical_conversion/amethyst_2"));
        ItemStackToChemicalRecipeBuilder
                .chemicalConversion(
                        creatorI.from(MekUtItems.XP_CRYSTAL),
                        MekUtChemicals.XP.asStack(100000))
                .build(output, MekUtConstants.rl("chemical_conversion/xp"));
    }
}
