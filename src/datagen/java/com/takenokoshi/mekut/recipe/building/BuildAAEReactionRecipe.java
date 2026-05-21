package com.takenokoshi.mekut.recipe.building;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.registries.MekUtItems;

import appeng.core.definitions.AEBlocks;
import appeng.core.definitions.AEItems;
import dev.lapis256.mekanism_empowered.common.init.MekEmpItems;
import mekanism.common.registries.MekanismBlocks;
import mekanism.common.registries.MekanismItems;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.material.Fluids;
import net.pedroksl.advanced_ae.recipes.ReactionChamberRecipeBuilder;

public class BuildAAEReactionRecipe {
    public static void build(RecipeOutput output) {

        ReactionChamberRecipeBuilder.react(MekUtItems.ACCELERATION_CONTROL_CIRCUIT, 2000000)
                .fluid(Fluids.LAVA, 400)
                .input(MekUtItems.STANDARD_CONTROL_CIRCUIT)
                .input(MekanismItems.SPEED_UPGRADE, 8)
                .input(MekanismItems.ENERGY_UPGRADE, 8)
                .input(MekEmpItems.INSTANCE.getEMPOWERED_SPEED(), 8)
                .input(MekEmpItems.INSTANCE.getEMPOWERED_ENERGY(), 8)
                .save(output, MekUtConstants.rl("aae_reaction/acceleration_control_circuit"));
        ReactionChamberRecipeBuilder.react(MekUtItems.CHEMICAL_CONTROL_CIRCUIT, 2000000)
                .fluid(Fluids.WATER, 2000)
                .input(MekUtItems.STANDARD_CONTROL_CIRCUIT)
                .input(MekanismItems.CHEMICAL_UPGRADE, 8)
                .save(output, MekUtConstants.rl("aae_reaction/chemical_control_circuit"));
        ReactionChamberRecipeBuilder.react(MekUtItems.ME_INFINITY_RAINBOW_CELL, 1000000)
                .fluid(Fluids.LAVA, 10000)
                .input(MekanismBlocks.PIGMENT_EXTRACTOR, 4)
                .input(AEItems.CELL_COMPONENT_4K, 2)
                .input(MekanismBlocks.DYNAMIC_TANK, 16)
                .input(MekanismItems.DYE_BASE, 16)
                .input(AEBlocks.QUARTZ_VIBRANT_GLASS, 8)
                .input(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ingots/tin")), 32)
                .save(output, MekUtConstants.rl("aae_reaction/me_infinity_rainbow_cell"));
    }
}
