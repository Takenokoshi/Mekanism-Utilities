package com.takenokoshi.mekut.recipe.building;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.registries.MekUtItems;

import appeng.core.AppEng;
import appeng.core.definitions.AEItems;
import mekanism.api.datagen.recipe.builder.ItemStackToItemStackRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IItemStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

public class EnrichingRecipes {

    public static void build(RecipeOutput output) {
        IItemStackIngredientCreator creatorI = IngredientCreatorAccess.item();
        ItemStackToItemStackRecipeBuilder
                .enriching(
                        creatorI.from(AEItems.SINGULARITY),
                        MekUtItems.ENRICHED_SINGULARITY.asStack(1))
                .addCondition(new ModLoadedCondition(AppEng.MOD_ID))
                .build(output, MekUtConstants.rl("enriching/enriched_singurality"));
        ItemStackToItemStackRecipeBuilder
                .enriching(
                        creatorI.from(1, new ItemLike[] { AEItems.FLUIX_CRYSTAL, AEItems.FLUIX_DUST }),
                        MekUtItems.ENRICHED_FLUIX.asStack(1))
                .addCondition(new ModLoadedCondition(AppEng.MOD_ID))
                .build(output, MekUtConstants.rl("enriching/enriched_fluix"));
    }
}
