package com.takenokoshi.mekut.recipe.building;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.registries.MekUtItems;

import mekanism.api.datagen.recipe.builder.ItemStackChemicalToItemStackRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IChemicalStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IItemStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismChemicals;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;

public class CompressingRecipes {

    public static void build(RecipeOutput output) {
        IItemStackIngredientCreator creatorI = IngredientCreatorAccess.item();
        IChemicalStackIngredientCreator creatorC = IngredientCreatorAccess.chemicalStack();
        ItemStackChemicalToItemStackRecipeBuilder
                .compressing(
                        creatorI.from(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "dusts/amethyst"))),
                        creatorC.from(MekanismChemicals.OSMIUM.asStack(200)),
                        MekUtItems.REFINED_AMETHYST_INGOT.asStack(1), false)
                .build(output, MekUtConstants.rl("compressing/refined_amethyst_ingot"));
    }
}
