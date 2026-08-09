package com.takenokoshi.mekut.recipe.building;

import com.github.misosouptgit.mwgr.MekanismWaterGeneratorRebuild;
import com.glodblock.github.appflux.AppFlux;
import com.glodblock.github.appflux.common.AFSingletons;
import com.glodblock.github.extendedae.ExtendedAE;
import com.glodblock.github.extendedae.common.EAESingletons;
import com.jerry.genextras.common.registries.GenExtraBlocks;
import com.jerry.mekextras.MekanismExtras;
import com.jerry.mekextras.common.registries.ExtraChemicals;
import com.jerry.mekextras.common.registries.ExtraItems;
import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.recipe.builder.ItemStackListFluidChemicalToItemRecipeBuilder;
import com.takenokoshi.mekut.registries.MekUtChemicals;
import com.takenokoshi.mekut.registries.MekUtEvolvedMachines;
import com.takenokoshi.mekut.registries.MekUtExtrasMachines;
import com.takenokoshi.mekut.registries.MekUtItems;
import com.takenokoshi.mekut.registries.MekUtMachines;

import appeng.core.AppEng;
import appeng.core.definitions.AEBlocks;
import appeng.core.definitions.AEItems;
import fr.iglee42.evolvedmekanism.EvolvedMekanism;
import fr.iglee42.evolvedmekanism.registries.EMBlocks;
import fr.iglee42.evolvedmekanism.registries.EMItems;
import gripe._90.megacells.MEGACells;
import gripe._90.megacells.definition.MEGAItems;
import me.ramidzkh.mekae2.AppliedMekanistics;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismBlocks;
import mekanism.common.registries.MekanismChemicals;
import mekanism.common.registries.MekanismFluids;
import mekanism.common.registries.MekanismItems;
import mekanism.common.resource.PrimaryResource;
import mekanism.common.resource.ResourceType;
import mekanism.common.tags.MekanismTags;
import mekanism.generators.common.registries.GeneratorsBlocks;
import mekanism.generators.common.registries.GeneratorsFluids;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

public class SDARecipes {
    public static void build(RecipeOutput output) {
        ItemStackListFluidChemicalToItemRecipeBuilder.smallDigitalAssembler(AEItems.LOGIC_PROCESSOR.stack(64))
                .addItemInput(AEItems.LOGIC_PROCESSOR_PRINT.stack(64))
                .addItemInput(AEItems.SILICON_PRINT.stack(64))
                .setFluidInput(IngredientCreatorAccess.fluid().from(Tags.Fluids.WATER, 1000))
                .setChemicalInput(MekanismChemicals.REDSTONE.asStack(640))
                .addCondition(new ModLoadedCondition(AppEng.MOD_ID))
                .build(output, MekUtConstants.rl("small_digital_assembler/logic_processor"));
        ItemStackListFluidChemicalToItemRecipeBuilder.smallDigitalAssembler(AEItems.CALCULATION_PROCESSOR.stack(64))
                .addItemInput(AEItems.CALCULATION_PROCESSOR_PRINT.stack(64))
                .addItemInput(AEItems.SILICON_PRINT.stack(64))
                .setFluidInput(IngredientCreatorAccess.fluid().from(Tags.Fluids.WATER, 1000))
                .setChemicalInput(MekanismChemicals.REDSTONE.asStack(640))
                .addCondition(new ModLoadedCondition(AppEng.MOD_ID))
                .build(output, MekUtConstants.rl("small_digital_assembler/calculation_processor"));
        ItemStackListFluidChemicalToItemRecipeBuilder.smallDigitalAssembler(AEItems.ENGINEERING_PROCESSOR.stack(64))
                .addItemInput(AEItems.ENGINEERING_PROCESSOR_PRINT.stack(64))
                .addItemInput(AEItems.SILICON_PRINT.stack(64))
                .setFluidInput(IngredientCreatorAccess.fluid().from(Tags.Fluids.WATER, 1000))
                .setChemicalInput(MekanismChemicals.REDSTONE.asStack(640))
                .addCondition(new ModLoadedCondition(AppEng.MOD_ID))
                .build(output, MekUtConstants.rl("small_digital_assembler/enrineering_processor"));
        ItemStackListFluidChemicalToItemRecipeBuilder
                .smallDigitalAssembler(new ItemStack(EAESingletons.CONCURRENT_PROCESSOR, 64))
                .addItemInput(new ItemStack(EAESingletons.CONCURRENT_PROCESSOR_PRINT, 64))
                .addItemInput(AEItems.SILICON_PRINT.stack(64))
                .setFluidInput(IngredientCreatorAccess.fluid().from(Tags.Fluids.WATER, 1000))
                .setChemicalInput(MekanismChemicals.REDSTONE.asStack(640))
                .addCondition(new ModLoadedCondition(ExtendedAE.MODID))
                .build(output, MekUtConstants.rl("small_digital_assembler/concurrent_processor"));
        ItemStackListFluidChemicalToItemRecipeBuilder.smallDigitalAssembler(MEGAItems.ACCUMULATION_PROCESSOR.stack(64))
                .addItemInput(MEGAItems.ACCUMULATION_PROCESSOR_PRINT.stack(64))
                .addItemInput(AEItems.SILICON_PRINT.stack(64))
                .setFluidInput(IngredientCreatorAccess.fluid().from(Tags.Fluids.LAVA, 1000))
                .setChemicalInput(MekUtChemicals.FLUIX.asStack(640))
                .addCondition(new ModLoadedCondition(MEGACells.MODID))
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
                .addItemInput(MekUtItems.AUGMENT_CONTROL_CIRCUIT.asStack())
                .addItemInput(MekUtItems.STARDUST_ALLOY.asStack(4))
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
                .addItemInput(MekUtItems.AUGMENT_CONTROL_CIRCUIT.asStack(8))
                .addItemInput(MekUtItems.COMPISITE_ALLOY.asStack(16))
                .setFluidInput(MekanismFluids.HEAVY_WATER.asStack(1000))
                .setChemicalInput(MekanismChemicals.TIN.asStack(2000))
                .build(output, MekUtConstants.rl("small_digital_assembler/compact_sps"));
        ItemStackListFluidChemicalToItemRecipeBuilder
                .smallDigitalAssembler(new ItemStack(MekUtMachines.COMPACT_FISSION_REACTOR))
                .addItemInput(GeneratorsBlocks.FISSION_REACTOR_CASING, 456)
                .addItemInput(GeneratorsBlocks.REACTOR_GLASS, 1264)
                .addItemInput(GeneratorsBlocks.FISSION_REACTOR_LOGIC_ADAPTER, 8)
                .addItemInput(GeneratorsBlocks.FISSION_REACTOR_PORT, 8)
                .addItemInput(GeneratorsBlocks.FISSION_FUEL_ASSEMBLY, 1920)
                .addItemInput(GeneratorsBlocks.CONTROL_ROD_ASSEMBLY, 128)
                .addItemInput(MekUtItems.STANDARD_CONTROL_CIRCUIT.asStack(4))
                .addItemInput(MekUtItems.CONVERGENT_ALLOY.asStack(8))
                .setFluidInput(MekanismFluids.HEAVY_WATER.asStack(100))
                .setChemicalInput(MekanismChemicals.REDSTONE.asStack(160))
                .build(output, MekUtConstants.rl("small_digital_assembler/compact_fission_reactor"));
        ItemStackListFluidChemicalToItemRecipeBuilder
                .smallDigitalAssembler(MekUtItems.SUPPLIER_BASE.asStack(4))
                .addItemInput(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ingots/bronze")), 4)
                .addItemInput(MekanismItems.HDPE_ROD, 1)
                .addItemInput(MekanismItems.ENRICHED_IRON.asStack(2))
                .setChemicalInput(MekanismChemicals.CARBON.asStack(1600))
                .setFluidInput(MekanismFluids.HEAVY_WATER.asStack(2000))
                .build(output, MekUtConstants.rl("small_digital_assembler/supplier_base"));
        ItemStackListFluidChemicalToItemRecipeBuilder
                .smallDigitalAssembler(MekUtItems.COBBLESTONE_SUPPLIER.asStack(1))
                .addItemInput(MekUtItems.SUPPLIER_BASE, 1)
                .addItemInput(new ItemStack(MekanismBlocks.ULTIMATE_BIN, 1))
                .setFluidInput(Tags.Fluids.LAVA, 20000)
                .setChemicalInput(MekanismChemicals.TIN.asStack(160))
                .build(output, MekUtConstants.rl("small_digital_assembler/cobblestone_supplier"));
        ItemStackListFluidChemicalToItemRecipeBuilder
                .smallDigitalAssembler(new ItemStack(MekUtMachines.COMPACT_INDUSTRIAL_TURBINE, 1))
                .addItemInput(GeneratorsBlocks.TURBINE_CASING, 1002)
                .addItemInput(Items.IRON_BARS, 1098)
                .addItemInput(MekanismTags.Items.INGOTS_STEEL, 1082)
                .addItemInput(MekanismItems.ADVANCED_CONTROL_CIRCUIT, 4)
                .addItemInput(MekanismItems.INFUSED_ALLOY, 292)
                .addItemInput(GeneratorsBlocks.SATURATING_CONDENSER, 203)
                .addItemInput(MekanismBlocks.STRUCTURAL_GLASS, 1001)
                .addItemInput(GeneratorsBlocks.ELECTROMAGNETIC_COIL, 7)
                .addItemInput(MekUtItems.DIGITAL_CONTROL_CIRCUIT, 16)
                .setChemicalInput(MekanismChemicals.TIN.asStack(1280))
                .setFluidInput(Tags.Fluids.WATER, 1000)
                .build(output, MekUtConstants.rl("small_digital_assembler/compact_industrial_turbine"));
        ItemStackListFluidChemicalToItemRecipeBuilder
                .smallDigitalAssembler(new ItemStack(MekUtMachines.COMPACT_BOILER, 1))
                .addItemInput(MekanismBlocks.BOILER_CASING, 200)
                .addItemInput(MekanismBlocks.BOILER_VALVE, 4)
                .addItemInput(MekanismBlocks.STRUCTURAL_GLASS, 1532)
                .addItemInput(MekanismBlocks.PRESSURE_DISPERSER, 256)
                .addItemInput(MekanismBlocks.SUPERHEATING_ELEMENT, 128)
                .setFluidInput(Tags.Fluids.WATER, 2000)
                .setChemicalInput(MekanismChemicals.GOLD.asStack(2560))
                .build(output, MekUtConstants.rl("small_digital_assembler/compact_boiler"));
        ItemStackListFluidChemicalToItemRecipeBuilder
                .smallDigitalAssembler(new ItemStack(MekUtMachines.COMPACT_THERMAL_EVAPOLATION_PLANT, 1))
                .addItemInput(MekanismBlocks.THERMAL_EVAPORATION_BLOCK, 92)
                .addItemInput(MekanismBlocks.THERMAL_EVAPORATION_CONTROLLER, 1)
                .addItemInput(MekanismBlocks.THERMAL_EVAPORATION_VALVE, 2)
                .addItemInput(MekanismBlocks.STRUCTURAL_GLASS, 125)
                .addItemInput(MekUtItems.DIGITAL_CONTROL_CIRCUIT, 2)
                .addItemInput(MekUtItems.ELASTIC_ALLOY, 4)
                .setFluidInput(Tags.Fluids.LAVA, 1000)
                .setChemicalInput(MekanismChemicals.GOLD.asStack(160))
                .build(output, MekUtConstants.rl("small_digital_assembler/compact_tep"));
        ItemStackListFluidChemicalToItemRecipeBuilder
                .smallDigitalAssembler(
                        new ItemStack(MekanismWaterGeneratorRebuild.WATER_GENERATOR_ITEM.getDelegate(), 64))
                .addItemInput(MekanismTags.Items.INGOTS_STEEL, 32)
                .addItemInput(Tags.Items.GLASS_BLOCKS, 32)
                .setFluidInput(Tags.Fluids.WATER, 2000)
                .setChemicalInput(MekanismChemicals.OSMIUM.asStack(6400))
                .addCondition(new ModLoadedCondition(MekanismWaterGeneratorRebuild.MODID))
                .build(output, MekUtConstants.rl("small_digital_assembler/water_generator"));
        ItemStackListFluidChemicalToItemRecipeBuilder
                .smallDigitalAssembler(new ItemStack(MekUtMachines.COMPACT_FUSION_REACTOR, 1))
                .addItemInput(GeneratorsBlocks.FUSION_REACTOR_CONTROLLER, 1)
                .addItemInput(GeneratorsBlocks.FUSION_REACTOR_FRAME, 36)
                .addItemInput(GeneratorsBlocks.REACTOR_GLASS, 24)
                .addItemInput(GeneratorsBlocks.LASER_FOCUS_MATRIX, 1)
                .addItemInput(GeneratorsBlocks.FUSION_REACTOR_PORT, 4)
                .addItemInput(MekanismItems.ULTIMATE_CONTROL_CIRCUIT, 2)
                .addItemInput(MekanismItems.ATOMIC_ALLOY, 4)
                .addItemInput(MekUtItems.AUGMENT_CONTROL_CIRCUIT, 8)
                .addItemInput(MekUtItems.COMPISITE_ALLOY, 16)
                .setFluidInput(Tags.Fluids.LAVA, 1000)
                .setChemicalInput(MekanismChemicals.OSMIUM.asStack(1600))
                .build(output, MekUtConstants.rl("small_digital_assembler/compact_fusion_reactor"));
        ItemStackListFluidChemicalToItemRecipeBuilder
                .smallDigitalAssembler(new ItemStack(MekUtMachines.XP_TANK, 1))
                .addItemInput(MekanismBlocks.STEEL_CASING, 1)
                .addItemInput(MekanismBlocks.DYNAMIC_TANK, 4)
                .setFluidInput(Tags.Fluids.WATER, 100)
                .setChemicalInput(MekUtChemicals.XP.asStack(100))
                .build(output, MekUtConstants.rl("small_digital_assembler/xp_tank"));
        ItemStackListFluidChemicalToItemRecipeBuilder
                .smallDigitalAssembler(MekUtItems.ME_INFINITY_RAINBOW_CELL.asStack(1))
                .addItemInput(MekanismBlocks.PIGMENT_EXTRACTOR, 4)
                .addItemInput(AEItems.CELL_COMPONENT_4K, 2)
                .addItemInput(MekanismBlocks.DYNAMIC_TANK, 16)
                .addItemInput(MekanismItems.DYE_BASE, 16)
                .addItemInput(AEBlocks.QUARTZ_VIBRANT_GLASS, 8)
                .addItemInput(MekanismTags.Items.PROCESSED_RESOURCES.get(ResourceType.INGOT, PrimaryResource.TIN), 32)
                .setFluidInput(Tags.Fluids.LAVA, 10000)
                .setChemicalInput(MekanismChemicals.ETHENE.asStack(10000L))
                .addCondition(new ModLoadedCondition(AppliedMekanistics.ID))
                .build(output, MekUtConstants.rl("small_digital_assembler/me_infinity_rainbow_cell"));

        ItemStackListFluidChemicalToItemRecipeBuilder
                .smallDigitalAssembler(
                        new ItemStack(MekUtEvolvedMachines.COMPACT_ANTIMATTER_PROTOMOLECULAR_TRANSMUTATOR, 1))
                .addItemInput(EMItems.QUANTUM_CONTROL_CIRCUIT, 4)
                .addItemInput(EMItems.SUBATOMIC_ALLOY, 8)
                .addItemInput(EMBlocks.APT_CASING, 52)
                .addItemInput(EMBlocks.APT_PORT, 4)
                .addItemInput(MekanismBlocks.STRUCTURAL_GLASS, 82)
                .setFluidInput(GeneratorsFluids.FUSION_FUEL.asStack(5000))
                .setChemicalInput(MekanismChemicals.ANTIMATTER.asStack(5))
                .addCondition(new ModLoadedCondition(EvolvedMekanism.MODID))
                .build(output, MekUtConstants
                        .rl("small_digital_assembler/compact_antimatter_protomolecular_transmutator"));
        ItemStackListFluidChemicalToItemRecipeBuilder
                .smallDigitalAssembler(new ItemStack(MekUtExtrasMachines.COMPACT_NAQUADAH_REACTOR, 1))
                .addItemInput(GenExtraBlocks.NAQUADAH_REACTOR_CONTROLLER, 1)
                .addItemInput(GenExtraBlocks.NAQUADAH_REACTOR_CASING, 108)
                .addItemInput(GenExtraBlocks.NAQUADAH_REACTOR_PORT, 4)
                .addItemInput(GeneratorsBlocks.REACTOR_GLASS, 217)
                .addItemInput(ExtraItems.COSMIC_CONTROL_CIRCUIT, 4)
                .addItemInput(ExtraItems.SHINING_ALLOY, 16)
                .setFluidInput(MekanismFluids.ETHENE.asStack(4000))
                .setChemicalInput(ExtraChemicals.LEAD.asStack(8000L))
                .addCondition(new ModLoadedCondition(MekanismExtras.MOD_ID))
                .build(output, MekUtConstants
                        .rl("small_digital_assembler/compact_naquadah_reactor"));
    }
}
