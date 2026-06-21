package com.takenokoshi.mekut.recipe.building;

import com.glodblock.github.extendedae.common.EAESingletons;
import com.glodblock.github.extendedae.recipe.CrystalAssemblerRecipeBuilder;
import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.registries.MekUtItems;
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
import net.minecraft.world.level.material.Fluids;

public class BuildCrystalAssemblerRecipe {

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
    }
}
