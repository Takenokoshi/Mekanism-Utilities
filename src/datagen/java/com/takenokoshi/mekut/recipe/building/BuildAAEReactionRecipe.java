package com.takenokoshi.mekut.recipe.building;

import com.takenokoshi.mekut.core.MekUtConstants;

import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.pedroksl.advanced_ae.recipes.ReactionChamberRecipeBuilder;

public class BuildAAEReactionRecipe {

    public static void build(RecipeOutput output) {
        ReactionChamberRecipeBuilder.react(Items.OBSIDIAN, 100)
                .fluid(new FluidStack(Fluids.LAVA, 1000))
                .input(Items.ICE)
                .save(output, MekUtConstants.rl("aae_reaction/obsidian"));
    }
}
