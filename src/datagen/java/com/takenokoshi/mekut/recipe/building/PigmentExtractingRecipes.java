package com.takenokoshi.mekut.recipe.building;

import com.takenokoshi.mekut.core.MekUtConstants;

import mekanism.api.datagen.recipe.builder.ItemStackToChemicalRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IItemStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.api.text.EnumColor;
import mekanism.common.registries.MekanismChemicals;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;

public class PigmentExtractingRecipes {

    public static void build(RecipeOutput output) {
        IItemStackIngredientCreator creatorI = IngredientCreatorAccess.item();
        ItemStackToChemicalRecipeBuilder
                .pigmentExtracting(
                        creatorI.from(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "dyes/dark_red"))),
                        MekanismChemicals.PIGMENT_COLOR_LOOKUP.get(EnumColor.DARK_RED).asStack(256))
                .build(output, MekUtConstants.rl("pigment_extracting/dye/dark_red"));
        ItemStackToChemicalRecipeBuilder
                .pigmentExtracting(
                        creatorI.from(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "dyes/aqua"))),
                        MekanismChemicals.PIGMENT_COLOR_LOOKUP.get(EnumColor.AQUA).asStack(256))
                .build(output, MekUtConstants.rl("pigment_extracting/dye/aqua"));
    }
}
