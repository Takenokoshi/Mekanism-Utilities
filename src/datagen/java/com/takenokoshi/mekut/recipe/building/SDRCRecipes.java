package com.takenokoshi.mekut.recipe.building;

import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.registries.MSGases;
import fixdol.mekanismelements.common.registries.MSItems;
import com.glodblock.github.extendedae.ExtendedAE;
import com.glodblock.github.extendedae.common.EAESingletons;
import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.recipe.builder.ItemStackListFluidChemicalToItemFluidChemicalRecipeBuilder;
import com.takenokoshi.mekut.registries.MekUtChemicals;
import com.takenokoshi.mekut.registries.MekUtItems;

import appeng.core.AppEng;
import appeng.core.definitions.AEItems;
import gripe._90.megacells.MEGACells;
import gripe._90.megacells.definition.MEGAItems;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismChemicals;
import mekanism.common.registries.MekanismItems;
import mekanism.common.resource.PrimaryResource;
import mekanism.common.resource.ResourceType;
import mekanism.common.tags.MekanismTags;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.fluids.FluidStack;
import net.pedroksl.advanced_ae.AdvancedAE;
import net.pedroksl.advanced_ae.common.definitions.AAEFluids;
import net.pedroksl.advanced_ae.common.definitions.AAEItems;

public class SDRCRecipes {
    public static void build(RecipeOutput output) {
        ItemStackListFluidChemicalToItemFluidChemicalRecipeBuilder
                .smallDigitalReactionChamber(AEItems.FLUIX_CRYSTAL.stack(64), FluidStack.EMPTY, ChemicalStack.EMPTY)
                .addItemInput(AEItems.CERTUS_QUARTZ_CRYSTAL_CHARGED.stack(16))
                .addItemInput(new ItemStack(Items.QUARTZ, 16))
                .setFluidInput(IngredientCreatorAccess.fluid().from(Tags.Fluids.WATER, 100))
                .setChemicalInput(IngredientCreatorAccess.chemicalStack().from(MekanismChemicals.REDSTONE.asStack(160)))
                .addCondition(new ModLoadedCondition(AppEng.MOD_ID))
                .build(output, MekUtConstants.rl("small_digital_reaction_chamber/fluix_crystal"));
        ItemStackListFluidChemicalToItemFluidChemicalRecipeBuilder
                .smallDigitalReactionChamber(MekUtItems.STARDUST_ALLOY.asStack(), FluidStack.EMPTY, ChemicalStack.EMPTY)
                .addItemInput(MekanismItems.ATOMIC_ALLOY.asStack(4))
                .addItemInput(MekUtItems.COMPISITE_ALLOY.asStack(4))
                .addItemInput(MekUtItems.IRIDIUM_INGOT.asStack(4))
                .setFluidInput(IngredientCreatorAccess.fluid().from(Tags.Fluids.WATER, 100))
                .setChemicalInput(MekUtChemicals.ASTRAL_ETHER.asStack(200))
                .build(output, MekUtConstants.rl("small_digital_reaction_chamber/stardust_alloy"));
        ItemStackListFluidChemicalToItemFluidChemicalRecipeBuilder
                .smallDigitalReactionChamber(MSItems.NEUTRON_SOURCE_PELLET.asStack(), FluidStack.EMPTY,
                        ChemicalStack.EMPTY)
                .addItemInput(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "dusts/beryllium")), 1)
                .addItemInput(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "dusts/steel")), 1)
                .setFluidInput(IngredientCreatorAccess.fluid().from(Tags.Fluids.WATER, 100))
                .setChemicalInput(MSGases.AMERICIUM.asStack(100))
                .addCondition(new ModLoadedCondition(MekanismElements.MODID))
                .build(output, MekUtConstants.rl("small_digital_reaction_chamber/neutron_source_pellet"));
        ItemStackListFluidChemicalToItemFluidChemicalRecipeBuilder
                .smallDigitalReactionChamber(new ItemStack(Items.GLOWSTONE_DUST, 16), FluidStack.EMPTY,
                        ChemicalStack.EMPTY)
                .addItemInput(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "dusts/redstone")), 16)
                .addItemInput(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "dusts/gold")), 8)
                .setFluidInput(IngredientCreatorAccess.fluid().from(Tags.Fluids.LAVA, 100))
                .setChemicalInput(MekUtChemicals.XP.asStack(160))
                .build(output, MekUtConstants.rl("small_digital_reaction_chamber/glowstone_dust"));
        ItemStackListFluidChemicalToItemFluidChemicalRecipeBuilder
                .smallDigitalReactionChamber(AAEItems.QUANTUM_ALLOY.stack(16), FluidStack.EMPTY,
                        ChemicalStack.EMPTY)
                .addItemInput(MekanismItems.PROCESSED_RESOURCES.get(ResourceType.CRYSTAL, PrimaryResource.COPPER), 64)
                .addItemInput(AAEItems.SHATTERED_SINGULARITY, 64)
                .setFluidInput(AAEFluids.QUANTUM_INFUSION.stack(16000))
                .setChemicalInput(MekUtChemicals.REFINED_LAPIS_LAZULI.asStack(2560))
                .setEnergyRequired(8000000)
                .addCondition(new ModLoadedCondition(AdvancedAE.MOD_ID))
                .build(output, MekUtConstants.rl("small_digital_reaction_chamber/quantum_alloy"));
        ItemStackListFluidChemicalToItemFluidChemicalRecipeBuilder
                .smallDigitalReactionChamber(new ItemStack(EAESingletons.ENTRO_INGOT, 64), FluidStack.EMPTY,
                        MekUtChemicals.XP.asStack(10000))
                .addItemInput(EAESingletons.ENTRO_DUST, 32)
                .addItemInput(MekanismItems.PROCESSED_RESOURCES.get(ResourceType.CRYSTAL, PrimaryResource.GOLD), 32)
                .addItemInput(MekUtItems.LAPIS_LAZULI_CRYSTAL, 32)
                .setFluidInput(Tags.Fluids.WATER, 500)
                .setChemicalInput(MekanismChemicals.HYDROGEN.asStack(200))
                .setEnergyRequired(1250000)
                .addCondition(new ModLoadedCondition(ExtendedAE.MODID))
                .build(output, MekUtConstants.rl("small_digital_reaction_chamber/entro_ingot"));
        ItemStackListFluidChemicalToItemFluidChemicalRecipeBuilder
                .smallDigitalReactionChamber(MEGAItems.SKY_STEEL_INGOT.stack(64), FluidStack.EMPTY,
                        MekUtChemicals.XP.asStack(10000))
                .addItemInput(MekanismItems.PROCESSED_RESOURCES.get(ResourceType.CRYSTAL, PrimaryResource.IRON), 16)
                .addItemInput(AEItems.SKY_DUST, 16)
                .addItemInput(AEItems.CERTUS_QUARTZ_CRYSTAL_CHARGED, 16)
                .setFluidInput(Tags.Fluids.LAVA, 500)
                .setChemicalInput(MekanismChemicals.HYDROGEN.asStack(200))
                .setEnergyRequired(500000)
                .addCondition(new ModLoadedCondition(MEGACells.MODID))
                .build(output, MekUtConstants.rl("small_digital_reaction_chamber/sky_steel_ingot"));
        ItemStackListFluidChemicalToItemFluidChemicalRecipeBuilder
                .smallDigitalReactionChamber(MEGAItems.SKY_BRONZE_INGOT.stack(64), FluidStack.EMPTY,
                        MekUtChemicals.XP.asStack(10000))
                .addItemInput(MekanismItems.PROCESSED_RESOURCES.get(ResourceType.CRYSTAL, PrimaryResource.COPPER), 16)
                .addItemInput(AEItems.SKY_DUST, 16)
                .addItemInput(AEItems.CERTUS_QUARTZ_CRYSTAL_CHARGED, 16)
                .setFluidInput(Tags.Fluids.LAVA, 500)
                .setChemicalInput(MekanismChemicals.HYDROGEN.asStack(200))
                .setEnergyRequired(500000)
                .addCondition(new ModLoadedCondition(MEGACells.MODID))
                .build(output, MekUtConstants.rl("small_digital_reaction_chamber/sky_bronze_ingot"));
        ItemStackListFluidChemicalToItemFluidChemicalRecipeBuilder
                .smallDigitalReactionChamber(MEGAItems.SKY_OSMIUM_INGOT.stack(64), FluidStack.EMPTY,
                        MekUtChemicals.XP.asStack(10000))
                .addItemInput(MekanismItems.PROCESSED_RESOURCES.get(ResourceType.CRYSTAL, PrimaryResource.OSMIUM), 16)
                .addItemInput(AEItems.SKY_DUST, 16)
                .addItemInput(AEItems.CERTUS_QUARTZ_CRYSTAL_CHARGED, 16)
                .setFluidInput(Tags.Fluids.LAVA, 500)
                .setChemicalInput(MekanismChemicals.HYDROGEN.asStack(200))
                .setEnergyRequired(500000)
                .addCondition(new ModLoadedCondition(MEGACells.MODID))
                .build(output, MekUtConstants.rl("small_digital_reaction_chamber/sky_osmium_ingot"));
        ItemStackListFluidChemicalToItemFluidChemicalRecipeBuilder
                .smallDigitalReactionChamber(new ItemStack(Items.BLAZE_ROD, 1), FluidStack.EMPTY,
                        ChemicalStack.EMPTY)
                .addItemInput(Tags.Items.DUSTS_GLOWSTONE, 2)
                .addItemInput(MekanismTags.Items.DUSTS_LITHIUM, 1)
                .setFluidInput(Tags.Fluids.LAVA, 100)
                .setChemicalInput(MekanismChemicals.CARBON.asStack(200))
                .setEnergyRequired(200)
                .build(output, MekUtConstants.rl("small_digital_reaction_chamber/blaze_rod"));
        ItemStackListFluidChemicalToItemFluidChemicalRecipeBuilder
                .smallDigitalReactionChamber(new ItemStack(Items.NETHER_WART, 1), FluidStack.EMPTY,
                        ChemicalStack.EMPTY)
                .addItemInput(Items.WHEAT, 1)
                .addItemInput(Items.BLAZE_POWDER, 3)
                .setFluidInput(Tags.Fluids.LAVA, 200)
                .setChemicalInput(MekanismChemicals.REDSTONE.asStack(200))
                .setEnergyRequired(500)
                .build(output, MekUtConstants.rl("small_digital_reaction_chamber/nether_wart"));
        ItemStackListFluidChemicalToItemFluidChemicalRecipeBuilder
                .smallDigitalReactionChamber(new ItemStack(Items.END_STONE, 8), FluidStack.EMPTY,
                        ChemicalStack.EMPTY)
                .addItemInput(Tags.Items.COBBLESTONES, 8)
                .addItemInput(Tags.Items.DUSTS_GLOWSTONE, 4)
                .addItemInput(AEItems.ENDER_DUST.stack(1))
                .setFluidInput(AAEFluids.QUANTUM_INFUSION.stack(100))
                .setChemicalInput(MekanismChemicals.LITHIUM.asStack(200))
                .setEnergyRequired(200)
                .build(output, MekUtConstants.rl("small_digital_reaction_chamber/end_stone"));
    }
}
