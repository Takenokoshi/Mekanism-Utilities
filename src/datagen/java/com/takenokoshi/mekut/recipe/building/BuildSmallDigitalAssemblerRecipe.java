package com.takenokoshi.mekut.recipe.building;

import com.glodblock.github.appflux.AppFlux;
import com.glodblock.github.appflux.common.AFSingletons;
import com.glodblock.github.extendedae.common.EAESingletons;
import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.recipe.builder.ItemStackListFluidChemicalToItemRecipeBuilder;
import com.takenokoshi.mekut.registries.MekUtChemicals;
import com.takenokoshi.mekut.registries.MekUtItems;
import com.takenokoshi.mekut.registries.MekUtMachines;

import appeng.core.definitions.AEBlocks;
import appeng.core.definitions.AEItems;
import gripe._90.megacells.definition.MEGAItems;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismBlocks;
import mekanism.common.registries.MekanismChemicals;
import mekanism.common.registries.MekanismFluids;
import mekanism.common.registries.MekanismItems;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.pedroksl.advanced_ae.common.definitions.AAEItems;

public class BuildSmallDigitalAssemblerRecipe {
    public static void build(RecipeOutput output) {
        ItemStackListFluidChemicalToItemRecipeBuilder.smallDigitalAssembler(AEItems.LOGIC_PROCESSOR.stack(64))
                .addItemInput(AEItems.LOGIC_PROCESSOR_PRINT.stack(64))
                .addItemInput(AEItems.SILICON_PRINT.stack(64))
                .setFluidInput(IngredientCreatorAccess.fluid().from(Tags.Fluids.WATER, 1000))
                .setChemicalInput(MekanismChemicals.REDSTONE.asStack(640))
                .build(output, MekUtConstants.rl("small_digital_assembler/logic_processor"));
        ItemStackListFluidChemicalToItemRecipeBuilder.smallDigitalAssembler(AEItems.CALCULATION_PROCESSOR.stack(64))
                .addItemInput(AEItems.CALCULATION_PROCESSOR_PRINT.stack(64))
                .addItemInput(AEItems.SILICON_PRINT.stack(64))
                .setFluidInput(IngredientCreatorAccess.fluid().from(Tags.Fluids.WATER, 1000))
                .setChemicalInput(MekanismChemicals.REDSTONE.asStack(640))
                .build(output, MekUtConstants.rl("small_digital_assembler/calculation_processor"));
        ItemStackListFluidChemicalToItemRecipeBuilder.smallDigitalAssembler(AEItems.ENGINEERING_PROCESSOR.stack(64))
                .addItemInput(AEItems.ENGINEERING_PROCESSOR_PRINT.stack(64))
                .addItemInput(AEItems.SILICON_PRINT.stack(64))
                .setFluidInput(IngredientCreatorAccess.fluid().from(Tags.Fluids.WATER, 1000))
                .setChemicalInput(MekanismChemicals.REDSTONE.asStack(640))
                .build(output, MekUtConstants.rl("small_digital_assembler/enrineering_processor"));
        ItemStackListFluidChemicalToItemRecipeBuilder
                .smallDigitalAssembler(new ItemStack(EAESingletons.CONCURRENT_PROCESSOR, 64))
                .addItemInput(new ItemStack(EAESingletons.CONCURRENT_PROCESSOR_PRINT, 64))
                .addItemInput(AEItems.SILICON_PRINT.stack(64))
                .setFluidInput(IngredientCreatorAccess.fluid().from(Tags.Fluids.WATER, 1000))
                .setChemicalInput(MekanismChemicals.REDSTONE.asStack(640))
                .build(output, MekUtConstants.rl("small_digital_assembler/concurrent_processor"));
        ItemStackListFluidChemicalToItemRecipeBuilder.smallDigitalAssembler(MEGAItems.ACCUMULATION_PROCESSOR.stack(64))
                .addItemInput(MEGAItems.ACCUMULATION_PROCESSOR_PRINT.stack(64))
                .addItemInput(AEItems.SILICON_PRINT.stack(64))
                .setFluidInput(IngredientCreatorAccess.fluid().from(Tags.Fluids.LAVA, 1000))
                .setChemicalInput(MekUtChemicals.FLUIX.asStack(640))
                .build(output, MekUtConstants.rl("small_digital_assembler/accumulation_processor"));
        ItemStackListFluidChemicalToItemRecipeBuilder
                .smallDigitalAssembler(new ItemStack(AFSingletons.ENERGY_PROCESSOR, 64))
                .addItemInput(new ItemStack(AFSingletons.ENERGY_PROCESSOR_PRINT, 64))
                .addItemInput(AEItems.SILICON_PRINT.stack(64))
                .setFluidInput(IngredientCreatorAccess.fluid().from(Tags.Fluids.WATER, 1000))
                .setChemicalInput(MekanismChemicals.REDSTONE.asStack(640))
                .addCondition(new ModLoadedCondition(AppFlux.MODID))
                .build(output, MekUtConstants.rl("small_digital_assembler/energy_processor"));
        ItemStackListFluidChemicalToItemRecipeBuilder.smallDigitalAssembler(MekUtItems.COMET_CONTROL_CIRCUIT.asStack())
                .addItemInput(MekanismItems.ULTIMATE_CONTROL_CIRCUIT.asStack())
                .addItemInput(MekUtItems.KNOWLEDGE_CONTROL_CIRCUIT.asStack())
                .addItemInput(MekUtItems.STARDUST_ALLOY.asStack(4))
                .addItemInput(AAEItems.QUANTUM_PROCESSOR.stack(2))
                .addItemInput(AEItems.SPATIAL_128_CELL_COMPONENT.stack(2))
                .addItemInput(AEBlocks.NOT_SO_MYSTERIOUS_CUBE.stack(8))
                .setFluidInput(IngredientCreatorAccess.fluid().from(Tags.Fluids.WATER, 1000))
                .setChemicalInput(MekUtChemicals.XP.asStack(10000))
                .build(output, MekUtConstants.rl("small_digital_assembler/comet_control_circuit"));
        ItemStackListFluidChemicalToItemRecipeBuilder
                .smallDigitalAssembler(new ItemStack(MekUtMachines.COMPACT_SUPERCRITICAL_PHASE_SHIFTER))
                .addItemInput(MekanismBlocks.SPS_CASING, 60)
                .addItemInput(MekanismBlocks.STRUCTURAL_GLASS, 120)
                .addItemInput(MekanismBlocks.SPS_PORT, 6)
                .addItemInput(MekanismBlocks.SUPERCHARGED_COIL, 2)
                .addItemInput(MekanismItems.ULTIMATE_CONTROL_CIRCUIT.asStack(2))
                .addItemInput(MekanismItems.ATOMIC_ALLOY.asStack(4))
                .addItemInput(MekUtItems.KNOWLEDGE_CONTROL_CIRCUIT.asStack(8))
                .addItemInput(MekUtItems.XP_ALLOY.asStack(16))
                .setFluidInput(MekanismFluids.HEAVY_WATER.asStack(1000))
                .setChemicalInput(MekanismChemicals.TIN.asStack(2000))
                .build(output, MekUtConstants.rl("small_digitalassembler/compact_sps"));
    }
}
