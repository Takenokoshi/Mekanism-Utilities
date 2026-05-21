package com.takenokoshi.mekut.recipe.building;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.registries.MekUtItems;

import mekanism.api.datagen.recipe.builder.ItemStackChemicalToItemStackRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IChemicalStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IItemStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.api.text.EnumColor;
import mekanism.common.registries.MekanismChemicals;
import mekanism.common.registries.MekanismItems;
import net.minecraft.data.recipes.RecipeOutput;

public class BuildPaintingRecipe {

    public static void build(RecipeOutput output) {
        IItemStackIngredientCreator creatorI = IngredientCreatorAccess.item();
        IChemicalStackIngredientCreator creatorC = IngredientCreatorAccess.chemicalStack();
        ItemStackChemicalToItemStackRecipeBuilder
                .painting(
                        creatorI.from(MekanismItems.DYE_BASE.asStack(1)),
                        creatorC.from(MekanismChemicals.PIGMENT_COLOR_LOOKUP.get(EnumColor.DARK_RED).asStack(256)),
                        MekUtItems.DARK_RED_DYE.asStack(1),
                        false)
                .build(output, MekUtConstants.rl("painting/dye/dark_red"));
        ItemStackChemicalToItemStackRecipeBuilder
                .painting(
                        creatorI.from(MekanismItems.DYE_BASE.asStack(1)),
                        creatorC.from(MekanismChemicals.PIGMENT_COLOR_LOOKUP.get(EnumColor.AQUA).asStack(256)),
                        MekUtItems.AQUA_DYE.asStack(1),
                        false)
                .build(output, MekUtConstants.rl("painting/dye/aqua"));
    }
}
