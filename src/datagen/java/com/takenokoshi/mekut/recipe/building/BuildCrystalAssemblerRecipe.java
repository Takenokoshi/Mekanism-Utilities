package com.takenokoshi.mekut.recipe.building;

import java.util.ArrayList;
import java.util.List;

import com.glodblock.github.extendedae.common.EAESingletons;
import com.glodblock.github.extendedae.recipe.CrystalAssemblerRecipeBuilder;
import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.registries.MekUtFluids;
import com.takenokoshi.mekut.registries.MekUtItems;
import com.takenokoshi.mekut.registries.MekUtMachines;

import appeng.core.definitions.AEBlocks;
import appeng.core.definitions.AEItems;
import dev.lapis256.mekanism_empowered.common.init.MekEmpItems;
import gripe._90.megacells.definition.MEGAItems;
import mekanism.common.registries.MekanismBlocks;
import mekanism.common.registries.MekanismFluids;
import mekanism.common.registries.MekanismItems;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluids;
import net.pedroksl.advanced_ae.common.definitions.AAEItems;

public class BuildCrystalAssemblerRecipe {

    private static final List<ProcessorRecipeData> PROCESSORS = new ArrayList<>();

    public static void build(RecipeOutput output) {
        CrystalAssemblerRecipeBuilder.assemble(MekUtItems.ACCELERATION_CONTROL_CIRCUIT)
                .fluid(Fluids.LAVA, 200)
                .input(MekUtItems.STANDARD_CONTROL_CIRCUIT)
                .input(MekanismItems.SPEED_UPGRADE, 8)
                .input(MekanismItems.ENERGY_UPGRADE, 8)
                .input(MekEmpItems.INSTANCE.getEMPOWERED_SPEED(), 8)
                .input(MekEmpItems.INSTANCE.getEMPOWERED_ENERGY(), 8)
                .save(output, MekUtConstants.rl("crystal_assembler/acceleration_control_circuit"));
        CrystalAssemblerRecipeBuilder.assemble(MekUtItems.CHEMICAL_CONTROL_CIRCUIT)
                .fluid(Fluids.WATER, 1000)
                .input(MekUtItems.STANDARD_CONTROL_CIRCUIT)
                .input(MekanismItems.CHEMICAL_UPGRADE, 8)
                .save(output, MekUtConstants.rl("crystal_assembler/chemical_control_circuit"));
        CrystalAssemblerRecipeBuilder.assemble(MekUtItems.ME_INFINITY_RAINBOW_CELL)
                .fluid(Fluids.LAVA, 10000)
                .input(MekanismBlocks.PIGMENT_EXTRACTOR, 4)
                .input(AEItems.CELL_COMPONENT_4K, 2)
                .input(MekanismBlocks.DYNAMIC_TANK, 16)
                .input(MekanismItems.DYE_BASE, 16)
                .input(AEBlocks.QUARTZ_VIBRANT_GLASS, 8)
                .input(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ingots/tin")), 32)
                .save(output, MekUtConstants.rl("crystal_assembler/me_infinity_rainbow_cell"));
        CrystalAssemblerRecipeBuilder.assemble(MekUtItems.ME_INFINITY_STONE_CELL)
                .fluid(Fluids.LAVA, 10000)
                .input(EAESingletons.INFINITY_COBBLESTONE_CELL)
                .input(Items.QUARTZ, 64)
                .save(output, MekUtConstants.rl("crystal_assembler/me_infinity_stone_cell"));
        CrystalAssemblerRecipeBuilder.assemble(MekUtItems.MEGA_BULK_FLUID_STORAGE_CELL)
                .fluid(MekanismFluids.HEAVY_WATER.asStack(5000))
                .input(MEGAItems.BULK_CELL_COMPONENT)
                .input(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ingots/netherite")), 4)
                .input(AEBlocks.QUARTZ_GLASS, 4)
                .input(AEItems.SKY_DUST, 4)
                .save(output, MekUtConstants.rl("crystal_assembler/mega_bulk_fluid_storage_cell"));
        CrystalAssemblerRecipeBuilder.assemble(MekUtItems.MEGA_BULK_CHEMICAL_STORAGE_CELL)
                .fluid(MekanismFluids.ETHENE.asStack(5000))
                .input(MEGAItems.BULK_CELL_COMPONENT)
                .input(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ingots/refined_obsidian")), 4)
                .input(AEBlocks.QUARTZ_GLASS, 4)
                .input(AEItems.SKY_DUST, 4)
                .save(output, MekUtConstants.rl("crystal_assembler/mega_bulk_chemical_storage_cell"));
        CrystalAssemblerRecipeBuilder.assemble(MekUtMachines.COMPACT_SUPERCRITICAL_PHASE_SHIFTER)
                .fluid(MekUtFluids.XP.asStack(1000))
                .input(MekanismBlocks.SPS_CASING, 60)
                .input(MekanismBlocks.STRUCTURAL_GLASS, 120)
                .input(MekanismBlocks.SPS_PORT, 6)
                .input(MekanismBlocks.SUPERCHARGED_COIL, 2)
                .input(MekanismItems.ULTIMATE_CONTROL_CIRCUIT, 2)
                .input(MekanismItems.ATOMIC_ALLOY, 4)
                .input(MekUtItems.KNOWLEDGE_CONTROL_CIRCUIT, 8)
                .input(MekUtItems.XP_ALLOY, 16)
                .save(output, MekUtConstants.rl("crystal_assembler/compact_sps"));
        PROCESSORS.forEach(data -> {
            CrystalAssemblerRecipeBuilder.assemble(data.result, 64)
                    .fluid(MekUtFluids.XP.get(), data.fluidAmount)
                    .input(data.printed, 64)
                    .input(data.dust, 64)
                    .input(AEItems.SILICON_PRINT, 64)
                    .save(output, MekUtConstants.rl("crystal_assembler/" + data.nameString));
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
        PROCESSORS.add(
                new ProcessorRecipeData(EAESingletons.CONCURRENT_PROCESSOR, EAESingletons.CONCURRENT_PROCESSOR_PRINT,
                        Items.REDSTONE,
                        10000, "concurrent_processor"));
    }
}
