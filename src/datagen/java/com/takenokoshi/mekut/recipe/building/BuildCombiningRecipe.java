package com.takenokoshi.mekut.recipe.building;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.registries.MekUtItems;

import mekanism.api.datagen.recipe.builder.CombinerRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IItemStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;

public class BuildCombiningRecipe {
    public static void build(RecipeOutput output) {
        IItemStackIngredientCreator creatorI = IngredientCreatorAccess.item();
        CombinerRecipeBuilder.combining(
                creatorI.from(Items.LIGHT_BLUE_DYE, 1),
                creatorI.from(Items.LIME_DYE, 1),
                MekUtItems.AQUA_DYE.asStack(4))
                .build(output, MekUtConstants.rl("combining/dye/aqua_1"));
        CombinerRecipeBuilder.combining(
                creatorI.from(Items.CYAN_DYE, 1),
                creatorI.from(Items.WHITE_DYE, 1),
                MekUtItems.AQUA_DYE.asStack(4))
                .build(output, MekUtConstants.rl("combining/dye/aqua_2"));
        CombinerRecipeBuilder.combining(
                creatorI.from(Items.BLACK_DYE, 1),
                creatorI.from(Items.RED_DYE, 4),
                MekUtItems.DARK_RED_DYE.asStack(10))
                .build(output, MekUtConstants.rl("combining/dye/dark_red_1"));
        CombinerRecipeBuilder.combining(
                creatorI.from(Items.GRAY_DYE, 1),
                creatorI.from(Items.RED_DYE, 2),
                MekUtItems.DARK_RED_DYE.asStack(6))
                .build(output, MekUtConstants.rl("combining/dye/dark_red_2"));
    }
}
