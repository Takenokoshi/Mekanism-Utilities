package com.takenokoshi.mekut.recipe.building;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.registries.MekUtItems;

import appeng.core.definitions.AEItems;
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
                        creatorI.from(AEItems.SINGULARITY),
                        MekUtItems.ENRICHED_SINGULARITY.asStack(1))
                .build(output, MekUtConstants.rl("enriching/enriched_singurality"));
        ItemStackToItemStackRecipeBuilder
                .enriching(
                        creatorI.from(1, new ItemLike[] { AEItems.FLUIX_CRYSTAL, AEItems.FLUIX_DUST }),
                        MekUtItems.ENRICHED_FLUIX.asStack(1))
                .build(output, MekUtConstants.rl("enriching/enriched_fluix"));
        ItemStackToItemStackRecipeBuilder
                .enriching(
                        creatorI.from(Items.SNOWBALL, 4),
                        Items.ICE.getDefaultInstance())
                .build(output, MekUtConstants.rl("enriching/ice"));
    }
}
