package com.takenokoshi.mekut.recipe.building;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.registries.MekUtItems;

import mekanism.api.datagen.recipe.builder.ItemStackToItemStackRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IItemStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public class EnrichingRecipes {

    public static void build(RecipeOutput output) {
        IItemStackIngredientCreator creatorI = IngredientCreatorAccess.item();
        ItemStackToItemStackRecipeBuilder
                .enriching(
                        creatorI.from(MekUtItems.REFINED_LAPIS_LAZULI_DUST, 1),
                        MekUtItems.ENRICHED_LAPIS_LAZULI.asStack(1))
                .build(output, MekUtConstants.rl("enriching/enriched_lapis_lazuli"));
        ItemStackToItemStackRecipeBuilder
                .enriching(
                        creatorI.from(1, new ItemLike[] { Items.AMETHYST_SHARD, MekUtItems.AMETHYST_DUST, }),
                        MekUtItems.ENRICHED_AMETHYST.asStack(1))
                .build(output, MekUtConstants.rl("enriching/enriched_amethyst"));
        ItemStackToItemStackRecipeBuilder
                .enriching(
                        creatorI.from(Items.GLOWSTONE_DUST,1),
                        MekUtItems.ENRICHED_GLOWSTONE.asStack(1))
                .build(output, MekUtConstants.rl("enriching/enriched_glowstone"));
    }
}
