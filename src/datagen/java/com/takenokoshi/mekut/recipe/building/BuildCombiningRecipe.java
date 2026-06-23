package com.takenokoshi.mekut.recipe.building;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.enums.MUMaterial;
import com.takenokoshi.mekut.registries.MekUtItems;
import com.takenokoshi.mekut.tag.MekUtItemTags;

import mekanism.api.datagen.recipe.builder.CombinerRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IItemStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismBlocks;
import mekanism.common.resource.ore.OreType;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class BuildCombiningRecipe {
    public static void build(RecipeOutput output) {
        IItemStackIngredientCreator creatorI = IngredientCreatorAccess.item();
        CombinerRecipeBuilder.combining(
                creatorI.from(Items.LIGHT_BLUE_DYE, 1),
                creatorI.from(Items.LIME_DYE, 1),
                MekUtItems.AQUA_DYE.asStack(4))
                .build(output, MekUtConstants.rl("combining/dye/aqua_1"));
        CombinerRecipeBuilder.combining(
                creatorI.from(Items.CYAN_DYE, 1),
                creatorI.from(Items.WHITE_DYE, 1),
                MekUtItems.AQUA_DYE.asStack(4))
                .build(output, MekUtConstants.rl("combining/dye/aqua_2"));
        CombinerRecipeBuilder.combining(
                creatorI.from(Items.BLACK_DYE, 1),
                creatorI.from(Items.RED_DYE, 4),
                MekUtItems.DARK_RED_DYE.asStack(10))
                .build(output, MekUtConstants.rl("combining/dye/dark_red_1"));
        CombinerRecipeBuilder.combining(
                creatorI.from(Items.GRAY_DYE, 1),
                creatorI.from(Items.RED_DYE, 2),
                MekUtItems.DARK_RED_DYE.asStack(6))
                .build(output, MekUtConstants.rl("combining/dye/dark_red_2"));
        CombinerRecipeBuilder.combining(
                creatorI.from(MekUtItemTags.RAW_MU_MATERIALS.get(MUMaterial.COAL), 8),
                creatorI.from(Items.COBBLESTONE, 1),
                new ItemStack(Items.COAL_ORE, 1))
                .build(output, MekUtConstants.rl("processing/coal/to_ore"));
        CombinerRecipeBuilder.combining(
                creatorI.from(MekUtItemTags.RAW_MU_MATERIALS.get(MUMaterial.COAL), 8),
                creatorI.from(Items.COBBLED_DEEPSLATE, 1),
                new ItemStack(Items.DEEPSLATE_COAL_ORE, 1))
                .build(output, MekUtConstants.rl("processing/coal/to_deepslate_ore"));
        CombinerRecipeBuilder.combining(
                creatorI.from(MekUtItemTags.RAW_MU_MATERIALS.get(MUMaterial.REDSTONE), 48),
                creatorI.from(Items.COBBLESTONE, 1),
                new ItemStack(Items.REDSTONE_ORE, 1))
                .build(output, MekUtConstants.rl("processing/redstone/to_ore"));
        CombinerRecipeBuilder.combining(
                creatorI.from(MekUtItemTags.RAW_MU_MATERIALS.get(MUMaterial.REDSTONE), 48),
                creatorI.from(Items.COBBLED_DEEPSLATE, 1),
                new ItemStack(Items.DEEPSLATE_REDSTONE_ORE, 1))
                .build(output, MekUtConstants.rl("processing/redstone/to_deepslate_ore"));
        CombinerRecipeBuilder.combining(
                creatorI.from(MekUtItemTags.RAW_MU_MATERIALS.get(MUMaterial.EMERALD), 8),
                creatorI.from(Items.COBBLESTONE, 1),
                new ItemStack(Items.EMERALD_ORE, 1))
                .build(output, MekUtConstants.rl("processing/emerald/to_ore"));
        CombinerRecipeBuilder.combining(
                creatorI.from(MekUtItemTags.RAW_MU_MATERIALS.get(MUMaterial.EMERALD), 8),
                creatorI.from(Items.COBBLED_DEEPSLATE, 1),
                new ItemStack(Items.DEEPSLATE_EMERALD_ORE, 1))
                .build(output, MekUtConstants.rl("processing/emerald/to_deepslate_ore"));
        CombinerRecipeBuilder.combining(
                creatorI.from(MekUtItemTags.RAW_MU_MATERIALS.get(MUMaterial.LAPIS_LAZULI), 48),
                creatorI.from(Items.COBBLESTONE, 1),
                new ItemStack(Items.LAPIS_ORE, 1))
                .build(output, MekUtConstants.rl("processing/lapis_lazuli/to_ore"));
        CombinerRecipeBuilder.combining(
                creatorI.from(MekUtItemTags.RAW_MU_MATERIALS.get(MUMaterial.LAPIS_LAZULI), 48),
                creatorI.from(Items.COBBLED_DEEPSLATE, 1),
                new ItemStack(Items.DEEPSLATE_LAPIS_ORE, 1))
                .build(output, MekUtConstants.rl("processing/lapis_lazuli/to_deepslate_ore"));
        CombinerRecipeBuilder.combining(
                creatorI.from(MekUtItemTags.RAW_MU_MATERIALS.get(MUMaterial.DIAMOND), 8),
                creatorI.from(Items.COBBLESTONE, 1),
                new ItemStack(Items.DIAMOND_ORE, 1))
                .build(output, MekUtConstants.rl("processing/diamond/to_ore"));
        CombinerRecipeBuilder.combining(
                creatorI.from(MekUtItemTags.RAW_MU_MATERIALS.get(MUMaterial.DIAMOND), 8),
                creatorI.from(Items.COBBLED_DEEPSLATE, 1),
                new ItemStack(Items.DEEPSLATE_DIAMOND_ORE, 1))
                .build(output, MekUtConstants.rl("processing/diamond/to_deepslate_ore"));
        CombinerRecipeBuilder.combining(
                creatorI.from(MekUtItemTags.RAW_MU_MATERIALS.get(MUMaterial.QUARTZ), 8),
                creatorI.from(Items.NETHERRACK, 1),
                new ItemStack(Items.NETHER_QUARTZ_ORE, 1))
                .build(output, MekUtConstants.rl("processing/quartz/to_nether_ore"));

        CombinerRecipeBuilder.combining(
                creatorI.from(MekUtItemTags.RAW_MU_MATERIALS.get(MUMaterial.FLUORITE), 12),
                creatorI.from(Items.COBBLESTONE, 1),
                new ItemStack(MekanismBlocks.ORES.get(OreType.FLUORITE).stone(), 1))
                .build(output, MekUtConstants.rl("processing/fluorite/to_ore"));
        CombinerRecipeBuilder.combining(
                creatorI.from(MekUtItemTags.RAW_MU_MATERIALS.get(MUMaterial.FLUORITE), 12),
                creatorI.from(Items.COBBLED_DEEPSLATE, 1),
                new ItemStack(MekanismBlocks.ORES.get(OreType.FLUORITE).deepslate(), 1))
                .build(output, MekUtConstants.rl("processing/fluorite/to_deepslate_ore"));
    }
}
