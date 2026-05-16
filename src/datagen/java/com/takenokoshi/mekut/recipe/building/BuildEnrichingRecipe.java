package com.takenokoshi.mekut.recipe.building;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.registries.MekUtItems;

import appeng.core.definitions.AEItems;
import mekanism.api.datagen.recipe.builder.ItemStackToItemStackRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IItemStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.data.recipes.RecipeOutput;

public class BuildEnrichingRecipe {

    public static void build(RecipeOutput output) {
        IItemStackIngredientCreator creatorI = IngredientCreatorAccess.item();
        ItemStackToItemStackRecipeBuilder
                .enriching(
                        creatorI.from(MekUtItems.ACTIVATED_LAPIS_LAZULI),
                        MekUtItems.ENRICHED_LAPIS_LAZULI.asStack(1))
                .build(output, MekUtConstants.rl("enriching/enriched_lapis_lazuli"));
        ItemStackToItemStackRecipeBuilder
                .enriching(
                        creatorI.from(AEItems.SINGULARITY),
                        MekUtItems.ENRICHED_SINGULARITY.asStack(1))
                .build(output, MekUtConstants.rl("enriching/enriched_singurality"));
    }
}
