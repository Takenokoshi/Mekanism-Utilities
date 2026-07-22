package com.takenokoshi.mekut.recipe.building;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.registries.MekUtItems;

import mekanism.common.registries.MekanismFluids;
import mekanism.common.registries.MekanismItems;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.Tags;
import net.pedroksl.advanced_ae.common.definitions.AAEFluids;
import net.pedroksl.advanced_ae.common.definitions.AAEItems;
import net.pedroksl.advanced_ae.recipes.ReactionChamberRecipeBuilder;

public class AAEReactionRecipes {

    public static void build(RecipeOutput output) {
        ReactionChamberRecipeBuilder.react(AAEItems.QUANTUM_ALLOY_PLATE, 1000000)
                .fluid(AAEFluids.QUANTUM_INFUSION.stack(1000))
                .input(AAEItems.QUANTUM_ALLOY.stack(8))
                .input(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ingots/netherite")), 2)
                .input(MekUtItems.ARTIFICIAL_STAR)
                .save(output, MekUtConstants.rl("aae_reaction/quantum_alloy_plate"));
        ReactionChamberRecipeBuilder.react(MekanismFluids.BRINE.asStack(150), 100)
                .fluid(Tags.Fluids.WATER, 100)
                .input(MekanismItems.SALT, 10)
                .save(output, MekUtConstants.rl("aae_reaction/brine"));
    }
}
