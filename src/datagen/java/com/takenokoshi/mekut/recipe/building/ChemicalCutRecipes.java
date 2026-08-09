package com.takenokoshi.mekut.recipe.building;

import com.glodblock.github.extendedae.ExtendedAE;
import com.glodblock.github.extendedae.common.EAESingletons;
import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.recipe.builder.MekUtItemChemicalToItemRecipeBuilder;
import com.takenokoshi.mekut.registries.MekUtItems;

import appeng.core.AppEng;
import appeng.core.definitions.AEItems;
import gripe._90.megacells.MEGACells;
import gripe._90.megacells.definition.MEGAItems;
import mekanism.api.recipes.ingredients.creator.IChemicalStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IItemStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismChemicals;
import mekanism.common.registries.MekanismItems;
import mekanism.common.resource.PrimaryResource;
import mekanism.common.resource.ResourceType;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.pedroksl.advanced_ae.AdvancedAE;
import net.pedroksl.advanced_ae.common.definitions.AAEItems;

public class ChemicalCutRecipes {

    public static void build(RecipeOutput output) {
        IItemStackIngredientCreator creatorI = IngredientCreatorAccess.item();
        IChemicalStackIngredientCreator creatorC = IngredientCreatorAccess.chemicalStack();
        MekUtItemChemicalToItemRecipeBuilder
                .chemicalCut(
                        creatorI.from(MekUtItems.SILICON_CRYSTAL, 32),
                        creatorC.fromHolder(MekanismChemicals.HYDROGEN_CHLORIDE, 1),
                        AEItems.SILICON_PRINT.stack(64),
                        true)
                .addCondition(new ModLoadedCondition(AppEng.MOD_ID))
                .build(output, MekUtConstants.rl("chemical_cut/silicon_print"));
        MekUtItemChemicalToItemRecipeBuilder
                .chemicalCut(
                        creatorI.from(MekanismItems.PROCESSED_RESOURCES.get(ResourceType.CRYSTAL, PrimaryResource.GOLD),
                                32),
                        creatorC.fromHolder(MekanismChemicals.HYDROGEN_CHLORIDE, 1),
                        AEItems.LOGIC_PROCESSOR_PRINT.stack(64),
                        true)
                .addCondition(new ModLoadedCondition(AppEng.MOD_ID))
                .build(output, MekUtConstants.rl("chemical_cut/logic_processor_print"));
        MekUtItemChemicalToItemRecipeBuilder
                .chemicalCut(
                        creatorI.from(MekUtItems.CERTUS_QUARTZ_CRYSTAL, 32),
                        creatorC.fromHolder(MekanismChemicals.HYDROGEN_CHLORIDE, 1),
                        AEItems.CALCULATION_PROCESSOR_PRINT.stack(64),
                        true)
                .addCondition(new ModLoadedCondition(AppEng.MOD_ID))
                .build(output, MekUtConstants.rl("chemical_cut/calculation_processor_print"));
        MekUtItemChemicalToItemRecipeBuilder
                .chemicalCut(
                        creatorI.from(MekUtItems.DIAMOND_CRYSTAL, 32),
                        creatorC.fromHolder(MekanismChemicals.HYDROGEN_CHLORIDE, 1),
                        AEItems.ENGINEERING_PROCESSOR_PRINT.stack(64),
                        true)
                .addCondition(new ModLoadedCondition(AppEng.MOD_ID))
                .build(output, MekUtConstants.rl("chemical_cut/engineering_processor_print"));
        MekUtItemChemicalToItemRecipeBuilder
                .chemicalCut(
                        creatorI.from(EAESingletons.ENTRO_INGOT, 64),
                        creatorC.fromHolder(MekanismChemicals.HYDROGEN_CHLORIDE, 1),
                        new ItemStack(EAESingletons.CONCURRENT_PROCESSOR_PRINT, 64),
                        true)
                .addCondition(new ModLoadedCondition(ExtendedAE.MODID))
                .build(output, MekUtConstants.rl("chemical_cut/concurrent_processor_print"));
        MekUtItemChemicalToItemRecipeBuilder
                .chemicalCut(
                        creatorI.from(MEGAItems.SKY_STEEL_INGOT, 64),
                        creatorC.fromHolder(MekanismChemicals.HYDROGEN_CHLORIDE, 1),
                        MEGAItems.ACCUMULATION_PROCESSOR_PRINT.stack(64),
                        true)
                .addCondition(new ModLoadedCondition(MEGACells.MODID))
                .build(output, MekUtConstants.rl("chemical_cut/accumulation_processor_print"));
        MekUtItemChemicalToItemRecipeBuilder
                .chemicalCut(
                        creatorI.from(AAEItems.QUANTUM_ALLOY, 64),
                        creatorC.fromHolder(MekanismChemicals.HYDROGEN_CHLORIDE, 1),
                        AAEItems.QUANTUM_PROCESSOR_PRINT.stack(64),
                        true)
                .addCondition(new ModLoadedCondition(AdvancedAE.MOD_ID))
                .build(output, MekUtConstants.rl("chemical_cut/quantum_processor_print"));
    }
}
