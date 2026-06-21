package com.takenokoshi.mekut.recipe.building;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.registries.MekUtChemicals;
import com.takenokoshi.mekut.registries.MekUtItems;

import appeng.core.definitions.AEItems;
import mekanism.api.datagen.recipe.builder.ItemStackChemicalToItemStackRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IChemicalStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IItemStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismChemicals;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class BuildMetallurgicInfusingRecipe {

    public static void build(RecipeOutput output) {
        IItemStackIngredientCreator creatorI = IngredientCreatorAccess.item();
        IChemicalStackIngredientCreator creatorC = IngredientCreatorAccess.chemicalStack();
        ItemStackChemicalToItemStackRecipeBuilder
                .metallurgicInfusing(
                        creatorI.from(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ingots/tin"))),
                        creatorC.from(MekUtChemicals.FLUIX.asStack(10)),
                        MekUtItems.ELASTIC_ALLOY.asStack(1),
                        false)
                .build(output, MekUtConstants.rl("metallurgic_infusing/elastic_alloy"));
        ItemStackChemicalToItemStackRecipeBuilder
                .metallurgicInfusing(
                        creatorI.from(MekUtItems.ELASTIC_ALLOY),
                        creatorC.from(MekUtChemicals.SINGULARITY.asStack(20)),
                        MekUtItems.CONVERGENT_ALLOY.asStack(1),
                        false)
                .build(output, MekUtConstants.rl("metallurgic_infusing/convergent_alloy"));
        ItemStackChemicalToItemStackRecipeBuilder
                .metallurgicInfusing(
                        creatorI.from(MekUtItems.CONVERGENT_ALLOY),
                        creatorC.from(MekUtChemicals.XP.asStack(2000000)),
                        MekUtItems.XP_ALLOY.asStack(1),
                        false)
                .build(output, MekUtConstants.rl("metallurgic_infusing/xp_alloy"));
        ItemStackChemicalToItemStackRecipeBuilder
                .metallurgicInfusing(
                        creatorI.from(Items.REDSTONE, 20),
                        creatorC.from(MekanismChemicals.GOLD.asStack(800)),
                        MekUtItems.GOLDEN_REDSTONE.asStack(20),
                        false)
                .build(output, MekUtConstants.rl("metallurgic_infusing/golden_redstone"));
        ItemStackChemicalToItemStackRecipeBuilder
                .metallurgicInfusing(
                        creatorI.from(MekUtItems.GOLDEN_REDSTONE, 20),
                        creatorC.from(MekUtChemicals.XP.asStack(200000)),
                        new ItemStack(Items.GLOWSTONE_DUST, 20),
                        false)
                .build(output, MekUtConstants.rl("metallurgic_infusing/glowstone_dust"));
        ItemStackChemicalToItemStackRecipeBuilder
                .metallurgicInfusing(
                        creatorI.from(MekUtItems.BLAZE_CRYSTAL, 1),
                        creatorC.from(MekanismChemicals.GOLD.asStack(80)),
                        new ItemStack(Items.BLAZE_ROD, 1),
                        false)
                .build(output, MekUtConstants.rl("metallurgic_infusing/blaze_rod"));
        ItemStackChemicalToItemStackRecipeBuilder
                .metallurgicInfusing(
                        creatorI.from(Items.SLIME_BALL, 1),
                        creatorC.from(MekUtChemicals.BLAZE_ETHER.asStack(50)),
                        new ItemStack(Items.MAGMA_CREAM, 1),
                        false)
                .build(output, MekUtConstants.rl("metallurgic_infusing/magma_cream"));
        ItemStackChemicalToItemStackRecipeBuilder
                .metallurgicInfusing(
                        creatorI.from(Items.ENDER_PEARL, 1),
                        creatorC.from(MekUtChemicals.FLUIX.asStack(80)),
                        AEItems.FLUIX_PEARL.stack(1),
                        false)
                .build(output, MekUtConstants.rl("metallurgic_infusing/fluix_pearl"));
    }
}
