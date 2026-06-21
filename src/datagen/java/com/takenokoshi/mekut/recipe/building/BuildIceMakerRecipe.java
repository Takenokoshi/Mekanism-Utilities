package com.takenokoshi.mekut.recipe.building;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.recipe.builder.FluidToItemRecipeBuilder;

import mekanism.api.recipes.ingredients.creator.IFluidStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;

public class BuildIceMakerRecipe {
    public static void build(RecipeOutput output) {
        IFluidStackIngredientCreator creatorF = IngredientCreatorAccess.fluid();
        FluidToItemRecipeBuilder.iceMaking(
                creatorF.from(Tags.Fluids.WATER, 1000),
                new ItemStack(Items.ICE, 1))
                .build(output, MekUtConstants.rl("ice_making/ice"));
        FluidToItemRecipeBuilder.iceMaking(
                creatorF.from(Tags.Fluids.LAVA, 1000),
                new ItemStack(Items.OBSIDIAN, 1))
                .build(output, MekUtConstants.rl("ice_making/obsidian"));
    }
}
