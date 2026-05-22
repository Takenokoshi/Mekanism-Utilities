package com.takenokoshi.mekut.recipe.building;

import java.util.ArrayList;
import java.util.List;

import com.glodblock.github.extendedae.common.EAESingletons;
import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.registries.MekUtFluids;
import com.takenokoshi.mekut.registries.MekUtItems;

import appeng.core.definitions.AEBlocks;
import appeng.core.definitions.AEItems;
import dev.lapis256.mekanism_empowered.common.init.MekEmpItems;
import gripe._90.megacells.definition.MEGAItems;
import mekanism.common.registries.MekanismBlocks;
import mekanism.common.registries.MekanismItems;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluids;
import net.pedroksl.advanced_ae.common.definitions.AAEItems;
import net.pedroksl.advanced_ae.recipes.ReactionChamberRecipeBuilder;

public class BuildAAEReactionRecipe {

    private static final List<ProcessorRecipeData> PROCESSORS = new ArrayList<>();

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
        PROCESSORS.forEach(data -> {
            ReactionChamberRecipeBuilder.react(new ItemStack(data.result, 64), 1000000)
                    .fluid(MekUtFluids.XP.get(), data.fluidAmount)
                    .input(data.printed, 64)
                    .input(data.dust, 64)
                    .input(AEItems.SILICON_PRINT, 64)
                    .save(output, MekUtConstants.rl("aae_reaction/" + data.nameString));
        });
    }

    private static record ProcessorRecipeData(ItemLike result, ItemLike printed, ItemLike dust, int fluidAmount,
            String nameString) {
    }

    static {
        PROCESSORS.add(new ProcessorRecipeData(AEItems.LOGIC_PROCESSOR, AEItems.LOGIC_PROCESSOR_PRINT, Items.REDSTONE,
                1000, "logic_processor"));
        PROCESSORS.add(new ProcessorRecipeData(AEItems.CALCULATION_PROCESSOR, AEItems.CALCULATION_PROCESSOR_PRINT,
                Items.REDSTONE,
                1000, "calculation_processor"));
        PROCESSORS.add(new ProcessorRecipeData(AEItems.ENGINEERING_PROCESSOR, AEItems.ENGINEERING_PROCESSOR_PRINT,
                Items.REDSTONE,
                1000, "engineering_processor"));
        PROCESSORS.add(new ProcessorRecipeData(MEGAItems.ACCUMULATION_PROCESSOR, MEGAItems.ACCUMULATION_PROCESSOR_PRINT,
                AEItems.FLUIX_DUST,
                10000, "accumulation_processor"));
        PROCESSORS.add(new ProcessorRecipeData(AAEItems.QUANTUM_PROCESSOR, AAEItems.QUANTUM_PROCESSOR_PRINT,
                AAEItems.QUANTUM_INFUSED_DUST,
                10000, "quantum_processor"));
        PROCESSORS.add(new ProcessorRecipeData(EAESingletons.CONCURRENT_PROCESSOR, EAESingletons.CONCURRENT_PROCESSOR_PRINT,
                Items.REDSTONE,
                10000, "concurrent_processor"));
    }
}
