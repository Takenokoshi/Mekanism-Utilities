package com.takenokoshi.mekut.recipe.building;

import com.fxd927.mekanismelements.common.registries.MSGases;
import com.glodblock.github.appflux.AppFlux;
import com.glodblock.github.appflux.common.AFSingletons;
import com.glodblock.github.extendedae.common.EAESingletons;
import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.enums.MUMaterial;
import com.takenokoshi.mekut.recipe.builder.MekUtItemChemicalToItemRecipeBuilder;
import com.takenokoshi.mekut.registries.MekUtItems;

import appeng.core.definitions.AEItems;
import gripe._90.megacells.definition.MEGAItems;
import mekanism.api.recipes.ingredients.creator.IChemicalStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IItemStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismItems;
import mekanism.common.resource.PrimaryResource;
import mekanism.common.resource.ResourceType;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.pedroksl.advanced_ae.common.definitions.AAEItems;

public class ChemicalCutRecipes {

    public static void build(RecipeOutput output) {
        IItemStackIngredientCreator creatorI = IngredientCreatorAccess.item();
        IChemicalStackIngredientCreator creatorC = IngredientCreatorAccess.chemicalStack();
        MekUtItemChemicalToItemRecipeBuilder
                .chemicalCut(
                        creatorI.from(AEItems.SILICON, 16),
                        creatorC.from(MSGases.COMPRESSED_AIR.asStack(1)),
                        AEItems.SILICON_PRINT.stack(16),
                        true)
                .build(output, MekUtConstants.rl("chemical_cut/silicon_print_simple"));
        MekUtItemChemicalToItemRecipeBuilder
                .chemicalCut(
                        creatorI.from(Tags.Items.INGOTS_GOLD, 16),
                        creatorC.from(MSGases.COMPRESSED_AIR.asStack(1)),
                        AEItems.LOGIC_PROCESSOR_PRINT.stack(16),
                        true)
                .build(output, MekUtConstants.rl("chemical_cut/logic_processor_print_simple"));
        MekUtItemChemicalToItemRecipeBuilder
                .chemicalCut(
                        creatorI.from(AEItems.CERTUS_QUARTZ_CRYSTAL, 16),
                        creatorC.from(MSGases.COMPRESSED_AIR.asStack(1)),
                        AEItems.CALCULATION_PROCESSOR_PRINT.stack(16),
                        true)
                .build(output, MekUtConstants.rl("chemical_cut/calculation_processor_print_simple"));
        MekUtItemChemicalToItemRecipeBuilder
                .chemicalCut(
                        creatorI.from(Tags.Items.GEMS_DIAMOND, 16),
                        creatorC.from(MSGases.COMPRESSED_AIR.asStack(1)),
                        AEItems.ENGINEERING_PROCESSOR_PRINT.stack(16),
                        true)
                .build(output, MekUtConstants.rl("chemical_cut/engineering_processor_print_simple"));
        MekUtItemChemicalToItemRecipeBuilder
                .chemicalCut(
                        creatorI.from(EAESingletons.ENTRO_INGOT, 16),
                        creatorC.from(MSGases.COMPRESSED_AIR.asStack(1)),
                        new ItemStack(EAESingletons.CONCURRENT_PROCESSOR_PRINT, 16),
                        true)
                .build(output, MekUtConstants.rl("chemical_cut/concurrent_processor_print_simple"));
        MekUtItemChemicalToItemRecipeBuilder
                .chemicalCut(
                        creatorI.from(MEGAItems.SKY_STEEL_INGOT, 16),
                        creatorC.from(MSGases.COMPRESSED_AIR.asStack(1)),
                        MEGAItems.ACCUMULATION_PROCESSOR_PRINT.stack(16),
                        true)
                .build(output, MekUtConstants.rl("chemical_cut/accumulation_processor_print_simple"));
        MekUtItemChemicalToItemRecipeBuilder
                .chemicalCut(
                        creatorI.from(AAEItems.QUANTUM_ALLOY, 16),
                        creatorC.from(MSGases.COMPRESSED_AIR.asStack(1)),
                        AAEItems.QUANTUM_PROCESSOR_PRINT.stack(16),
                        true)
                .build(output, MekUtConstants.rl("chemical_cut/quantum_processor_print_simple"));
        MekUtItemChemicalToItemRecipeBuilder
                .chemicalCut(
                        creatorI.from(AFSingletons.CHARGED_REDSTONE, 16),
                        creatorC.from(MSGases.COMPRESSED_AIR.asStack(1)),
                        new ItemStack(AFSingletons.ENERGY_PROCESSOR_PRINT, 16),
                        true)
                .addCondition(new ModLoadedCondition(AppFlux.MODID))
                .build(output, MekUtConstants.rl("chemical_cut/energy_processor_print_simple"));
        MekUtItemChemicalToItemRecipeBuilder
                .chemicalCut(
                        creatorI.from(MekanismItems.PROCESSED_RESOURCES.get(ResourceType.CRYSTAL, PrimaryResource.GOLD),
                                64),
                        creatorC.from(MSGases.NITRIC_ACID.asStack(2)),
                        AEItems.LOGIC_PROCESSOR_PRINT.stack(64),
                        true)
                .build(output, MekUtConstants.rl("chemical_cut/logic_processor_print_adv"));
        MekUtItemChemicalToItemRecipeBuilder
                .chemicalCut(
                        creatorI.from(MekUtItems.MU_MATERIALS_CRYSTAL.get(MUMaterial.CERTUS_QUARTZ), 64),
                        creatorC.from(MSGases.NITRIC_ACID.asStack(2)),
                        AEItems.CALCULATION_PROCESSOR_PRINT.stack(64),
                        true)
                .build(output, MekUtConstants.rl("chemical_cut/calculation_processor_print_adv"));
        MekUtItemChemicalToItemRecipeBuilder
                .chemicalCut(
                        creatorI.from(MekUtItems.MU_MATERIALS_CRYSTAL.get(MUMaterial.DIAMOND), 64),
                        creatorC.from(MSGases.NITRIC_ACID.asStack(2)),
                        AEItems.ENGINEERING_PROCESSOR_PRINT.stack(64),
                        true)
                .build(output, MekUtConstants.rl("chemical_cut/engineering_processor_print_adv"));
    }
}
