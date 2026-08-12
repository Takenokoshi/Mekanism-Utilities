package com.takenokoshi.mekut.recipe.building;

import com.blakebr0.mysticalagriculture.MysticalAgriculture;
import com.blakebr0.mysticalagriculture.registry.CropRegistry;
import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.recipe.builder.GreenHouseCropRecipeBuilder;
import com.takenokoshi.mekut.recipe.builder.GreenHouseFertilizerRecipeBuilder;
import com.takenokoshi.mekut.recipe.output.MekUtChanceOutput;

import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismFluids;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

public class GreenHouseRecipes {
    public static void build(RecipeOutput output) {
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Tags.Items.SEEDS_WHEAT),
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.WHEAT, 1), 1.0d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.WHEAT_SEEDS, 2), 0.2d))
                .build(output, MekUtConstants.rl("green_house/crop/wheat"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Tags.Items.SEEDS_BEETROOT),
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.BEETROOT, 1), 1.0d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.BEETROOT_SEEDS, 2), 0.2d))
                .build(output, MekUtConstants.rl("green_house/crop/beetroot"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Tags.Items.SEEDS_MELON),
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.MELON, 1), 1.0d))
                .build(output, MekUtConstants.rl("green_house/crop/melon"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Tags.Items.SEEDS_PUMPKIN),
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.PUMPKIN, 1), 1.0d))
                .build(output, MekUtConstants.rl("green_house/crop/pumpkin"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.CARROT),
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.CARROT, 3), 1.0d))
                .build(output, MekUtConstants.rl("green_house/crop/carrot"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.POTATO),
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.POTATO, 3), 1.0d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.POISONOUS_POTATO, 1), 0.02d))
                .build(output, MekUtConstants.rl("green_house/crop/potato"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.SWEET_BERRIES),
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK, Items.PODZOL),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.SWEET_BERRIES, 3), 1.0d))
                .build(output, MekUtConstants.rl("green_house/crop/SWEET_BERRIES"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.GLOW_BERRIES),
                        IngredientCreatorAccess.item().from(Items.STONE, Items.DEEPSLATE, Items.MOSS_BLOCK),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.GLOW_BERRIES, 2), 1.0d))
                .build(output, MekUtConstants.rl("green_house/crop/GLOW_BERRIES"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.TORCHFLOWER_SEEDS),
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.TORCHFLOWER, 1), 1.0d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.TORCHFLOWER_SEEDS, 1), 0.2d))
                .build(output, MekUtConstants.rl("green_house/crop/TORCHFLOWER"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.PITCHER_POD),
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.PITCHER_PLANT, 1), 1.0d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.PITCHER_POD, 1), 0.2d))
                .build(output, MekUtConstants.rl("green_house/crop/PITCHER_PLANT"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.NETHER_WART),
                        IngredientCreatorAccess.item().from(Items.SOUL_SAND, Items.SOUL_SOIL),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.NETHER_WART, 2), 1.0d))
                .build(output, MekUtConstants.rl("green_house/crop/NETHER_WART"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.SUGAR_CANE),
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK, Items.SAND, Items.RED_SAND),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.SUGAR_CANE, 3), 1.0d))
                .build(output, MekUtConstants.rl("green_house/crop/SUGAR_CANE"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.COCOA_BEANS),
                        IngredientCreatorAccess.item().from(Items.JUNGLE_LOG),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.COCOA_BEANS, 3), 1.0d))
                .build(output, MekUtConstants.rl("green_house/crop/COCOA_BEANS"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.BAMBOO),
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK, Items.PODZOL),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.BAMBOO, 3), 1.0d))
                .build(output, MekUtConstants.rl("green_house/crop/BAMBOO"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.RED_MUSHROOM),
                        IngredientCreatorAccess.item().from(Items.MYCELIUM, Items.PODZOL),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.RED_MUSHROOM, 3), 1.0d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.BROWN_MUSHROOM, 1), 1.0d))
                .build(output, MekUtConstants.rl("green_house/crop/RED_MUSHROOM"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.BROWN_MUSHROOM),
                        IngredientCreatorAccess.item().from(Items.MYCELIUM, Items.PODZOL),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.BROWN_MUSHROOM, 3), 1.0d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.RED_MUSHROOM, 1), 1.0d))
                .build(output, MekUtConstants.rl("green_house/crop/BROWN_MUSHROOM"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.CHORUS_FLOWER),
                        IngredientCreatorAccess.item().from(Items.END_STONE),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.CHORUS_FRUIT, 4), 1.0d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.CHORUS_FLOWER, 1), 0.02d))
                .build(output, MekUtConstants.rl("green_house/crop/CHORUS_FRUIT"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.KELP),
                        IngredientCreatorAccess.item().from(Items.WATER_BUCKET),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.KELP, 8), 1.0d))
                .build(output, MekUtConstants.rl("green_house/crop/kelp"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.SEA_PICKLE),
                        IngredientCreatorAccess.item().from(Items.WATER_BUCKET),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.SEA_PICKLE, 8), 1.0d))
                .build(output, MekUtConstants.rl("green_house/crop/SEA_PICKLE"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.CACTUS),
                        IngredientCreatorAccess.item().from(Items.SAND, Items.RED_SAND),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.CACTUS, 2), 1.0d))
                .build(output, MekUtConstants.rl("green_house/crop/CACTUS"));

        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.OAK_SAPLING),
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.OAK_LOG, 2), 1.0d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.OAK_LEAVES, 5), 0.2d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.OAK_SAPLING, 1), 0.4d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.STICK, 4), 0.5d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.APPLE, 1), 0.2d))
                .build(output, MekUtConstants.rl("green_house/crop/oak"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.BIRCH_SAPLING),
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.BIRCH_LOG, 2), 1.0d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.BIRCH_LEAVES, 5), 0.2d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.BIRCH_SAPLING, 1), 0.4d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.STICK, 4), 0.5d))
                .build(output, MekUtConstants.rl("green_house/crop/birch"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.SPRUCE_SAPLING),
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.SPRUCE_LOG, 2), 1.0d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.SPRUCE_LEAVES, 5), 0.2d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.SPRUCE_SAPLING, 1), 0.4d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.STICK, 4), 0.5d))
                .build(output, MekUtConstants.rl("green_house/crop/spruce"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.JUNGLE_SAPLING),
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.JUNGLE_LOG, 2), 1.0d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.JUNGLE_LEAVES, 5), 0.2d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.JUNGLE_SAPLING, 1), 0.4d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.STICK, 4), 0.5d))
                .build(output, MekUtConstants.rl("green_house/crop/jungle"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.ACACIA_SAPLING),
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.ACACIA_LOG, 2), 1.0d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.ACACIA_LEAVES, 5), 0.2d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.ACACIA_SAPLING, 1), 0.4d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.STICK, 4), 0.5d))
                .build(output, MekUtConstants.rl("green_house/crop/acacia"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.DARK_OAK_SAPLING),
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.DARK_OAK_LOG, 2), 1.0d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.DARK_OAK_LEAVES, 5), 0.2d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.DARK_OAK_SAPLING, 1), 0.4d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.STICK, 4), 0.5d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.APPLE, 1), 0.2d))
                .build(output, MekUtConstants.rl("green_house/crop/dark_oak"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.MANGROVE_PROPAGULE),
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.MANGROVE_LOG, 2), 1.0d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.MANGROVE_LEAVES, 5), 0.2d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.MANGROVE_PROPAGULE, 1), 0.4d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.MANGROVE_ROOTS, 5), 0.4d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.STICK, 4), 0.5d))
                .build(output, MekUtConstants.rl("green_house/crop/mangrove"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.CHERRY_SAPLING),
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.CHERRY_LOG, 2), 1.0d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.CHERRY_LEAVES, 5), 0.2d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.CHERRY_SAPLING, 1), 0.4d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.STICK, 4), 0.5d))
                .build(output, MekUtConstants.rl("green_house/crop/cherry"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.AZALEA),
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK, Items.ROOTED_DIRT,
                                Items.MUD),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.OAK_LOG, 2), 1.0d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.AZALEA_LEAVES, 5), 0.2d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.AZALEA, 1), 0.4d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.STICK, 4), 0.5d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.HANGING_ROOTS, 1), 0.2d))
                .build(output, MekUtConstants.rl("green_house/crop/azalea"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.FLOWERING_AZALEA),
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK, Items.ROOTED_DIRT,
                                Items.MUD),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.OAK_LOG, 2), 1.0d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.FLOWERING_AZALEA_LEAVES, 5), 0.2d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.FLOWERING_AZALEA, 1), 0.4d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.STICK, 4), 0.5d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.HANGING_ROOTS, 1), 0.2d))
                .build(output, MekUtConstants.rl("green_house/crop/FLOWERING_AZALEA"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.CRIMSON_FUNGUS),
                        IngredientCreatorAccess.item().from(Items.CRIMSON_NYLIUM, Items.WARPED_NYLIUM),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.CRIMSON_STEM, 2), 1.0d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.NETHER_WART_BLOCK, 5), 0.2d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.SHROOMLIGHT, 4), 0.5d))
                .build(output, MekUtConstants.rl("green_house/crop/CRIMSON_FUNGUS"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.WARPED_FUNGUS),
                        IngredientCreatorAccess.item().from(Items.WARPED_NYLIUM, Items.CRIMSON_NYLIUM),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.WARPED_STEM, 2), 1.0d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.WARPED_WART_BLOCK, 5), 0.2d))
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.SHROOMLIGHT, 4), 0.5d))
                .build(output, MekUtConstants.rl("green_house/crop/WARPED_FUNGUS"));

        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.POPPY),
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.POPPY, 2), 1.0d))
                .build(output, MekUtConstants.rl("green_house/crop/POPPY"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.DANDELION),
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.DANDELION, 2), 1.0d))
                .build(output, MekUtConstants.rl("green_house/crop/DANDELION"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.AZURE_BLUET),
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.AZURE_BLUET, 2), 1.0d))
                .build(output, MekUtConstants.rl("green_house/crop/AZURE_BLUET"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.ALLIUM),
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.ALLIUM, 2), 1.0d))
                .build(output, MekUtConstants.rl("green_house/crop/ALLIUM"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.OXEYE_DAISY),
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.OXEYE_DAISY, 2), 1.0d))
                .build(output, MekUtConstants.rl("green_house/crop/OXEYE_DAISY"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.CORNFLOWER),
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.CORNFLOWER, 2), 1.0d))
                .build(output, MekUtConstants.rl("green_house/crop/CORNFLOWER"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.LILY_OF_THE_VALLEY),
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.LILY_OF_THE_VALLEY, 2), 1.0d))
                .build(output, MekUtConstants.rl("green_house/crop/LILY_OF_THE_VALLEY"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.RED_TULIP),
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.RED_TULIP, 2), 1.0d))
                .build(output, MekUtConstants.rl("green_house/crop/RED_TULIP"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.ORANGE_TULIP),
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.ORANGE_TULIP, 2), 1.0d))
                .build(output, MekUtConstants.rl("green_house/crop/ORANGE_TULIP"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.PINK_TULIP),
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.PINK_TULIP, 2), 1.0d))
                .build(output, MekUtConstants.rl("green_house/crop/PINK_TULIP"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.WHITE_TULIP),
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.WHITE_TULIP, 2), 1.0d))
                .build(output, MekUtConstants.rl("green_house/crop/WHITE_TULIP"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.SUNFLOWER),
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.SUNFLOWER, 2), 1.0d))
                .build(output, MekUtConstants.rl("green_house/crop/SUNFLOWER"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.LILAC),
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.LILAC, 2), 1.0d))
                .build(output, MekUtConstants.rl("green_house/crop/LILAC"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.ROSE_BUSH),
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.ROSE_BUSH, 2), 1.0d))
                .build(output, MekUtConstants.rl("green_house/crop/ROSE_BUSH"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.PEONY),
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.PEONY, 2), 1.0d))
                .build(output, MekUtConstants.rl("green_house/crop/PEONY"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.PINK_PETALS),
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.PINK_PETALS, 2), 1.0d))
                .build(output, MekUtConstants.rl("green_house/crop/PINK_PETALS"));
        GreenHouseCropRecipeBuilder
                .greenHouseCrop(
                        IngredientCreatorAccess.item().from(Items.WITHER_ROSE),
                        IngredientCreatorAccess.item().from(Items.SOUL_SAND, Items.SOUL_SOIL),
                        3000)
                .addOutput(new MekUtChanceOutput(new ItemStack(Items.WITHER_ROSE, 2), 1.0d))
                .build(output, MekUtConstants.rl("green_house/crop/WITHER_ROSE"));

        GreenHouseFertilizerRecipeBuilder
                .greenHouseFertilizer(
                        IngredientCreatorAccess.fluid().from(Tags.Fluids.WATER, 2000),
                        1,
                        1.0d)
                .build(output, MekUtConstants.rl("green_house/fertilizer/water"));
        GreenHouseFertilizerRecipeBuilder
                .greenHouseFertilizer(
                        IngredientCreatorAccess.fluid().from(MekanismFluids.NUTRITIONAL_PASTE.asStack(25)),
                        6,
                        0.2d)
                .build(output, MekUtConstants.rl("green_house/fertilizer/nutritional_paste"));

        CropRegistry.getInstance().getCrops().forEach(crop -> {
            GreenHouseCropRecipeBuilder
                    .greenHouseCrop(
                        IngredientCreatorAccess.item().from(crop.getSeedsItem()), 
                        IngredientCreatorAccess.item().from(Items.DIRT, Items.GRASS_BLOCK), 
                        3000)
                    .addOutput(new MekUtChanceOutput(new ItemStack(crop.getEssenceItem(),1), 1.0d))
                    .addCondition(new ModLoadedCondition(MysticalAgriculture.MOD_ID))
                    .build(output, MekUtConstants.rl("green_house/mysticalagriculture_crop/" + crop.getName()));
        });
    }
}
