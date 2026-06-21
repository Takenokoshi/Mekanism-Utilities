package com.takenokoshi.mekut.recipe.building;

import java.util.function.Function;

import com.jerry.mekextras.MekanismExtras;
import com.jerry.mekextras.common.registries.ExtraItems;
import com.jerry.mekextras.common.resource.ExtraResource;
import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.enums.MUMaterial;
import com.takenokoshi.mekut.enums.MUMaterialDatagen;
import com.takenokoshi.mekut.recipe.builder.ItemStackListFluidChemicalToItemFluidChemicalRecipeBuilder;
import com.takenokoshi.mekut.recipe.data.OreAndRawData;
import com.takenokoshi.mekut.registries.MekUtBlocks;
import com.takenokoshi.mekut.registries.MekUtChemicals;
import com.takenokoshi.mekut.registries.MekUtItems;
import com.takenokoshi.mekut.tag.MekUtItemTags;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.datagen.recipe.builder.ChemicalCrystallizerRecipeBuilder;
import mekanism.api.datagen.recipe.builder.ChemicalDissolutionRecipeBuilder;
import mekanism.api.datagen.recipe.builder.FluidChemicalToChemicalRecipeBuilder;
import mekanism.api.datagen.recipe.builder.ItemStackChemicalToItemStackRecipeBuilder;
import mekanism.api.datagen.recipe.builder.ItemStackToItemStackRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IChemicalStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IFluidStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IItemStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismChemicals;
import mekanism.common.resource.ResourceType;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.fluids.FluidStack;

public class BuildMUMaterialProcessRecipe {
    public static void build(RecipeOutput output,
            Function<ItemLike, Criterion<InventoryChangeTrigger.TriggerInstance>> has) {
        IItemStackIngredientCreator creatorI = IngredientCreatorAccess.item();
        IFluidStackIngredientCreator creatorF = IngredientCreatorAccess.fluid();
        IChemicalStackIngredientCreator creatorC = IngredientCreatorAccess.chemicalStack();
        for (MUMaterial material : MUMaterial.values()) {
            ShapelessRecipeBuilder
                    .shapeless(RecipeCategory.BUILDING_BLOCKS, MekUtBlocks.RAW_MU_MATERIALS_BLOCK.get(material))
                    .requires(Ingredient.of(MekUtItemTags.RAW_MU_MATERIALS.get(material)), 9)
                    .unlockedBy("has", has.apply(MekUtItems.RAW_MU_MATERIALS.get(material)))
                    .save(output, MekUtConstants.rl("processing/" + material.name + "/crafting/raw_block"));
            ShapelessRecipeBuilder
                    .shapeless(RecipeCategory.BUILDING_BLOCKS, MekUtItems.RAW_MU_MATERIALS.get(material).asStack(9))
                    .requires(Ingredient.of(MekUtItemTags.RAW_MU_MATERIALS_BLOCK.get(material)), 1)
                    .unlockedBy("has", has.apply(MekUtBlocks.RAW_MU_MATERIALS_BLOCK.get(material)))
                    .save(output, MekUtConstants.rl("processing/" + material.name + "/crafting/raw_from_block"));

            ChemicalDissolutionRecipeBuilder
                    .dissolution(
                            creatorI.from(MekUtItemTags.RAW_MU_MATERIALS.get(material), 3),
                            creatorC.from(MekanismChemicals.SULFURIC_ACID.asStack(1)),
                            MekUtChemicals.MU_MATERIALS_DIRTY_SLURRY.get(material).asStack(2000),
                            true)
                    .build(output, MekUtConstants.rl("processing/" + material.name + "/dirty_slurry/from_raw"));
            ChemicalDissolutionRecipeBuilder
                    .dissolution(
                            creatorI.from(MekUtItemTags.RAW_MU_MATERIALS_BLOCK.get(material), 1),
                            creatorC.from(MekanismChemicals.SULFURIC_ACID.asStack(2)),
                            MekUtChemicals.MU_MATERIALS_DIRTY_SLURRY.get(material).asStack(6000),
                            true)
                    .build(output, MekUtConstants.rl("processing/" + material.name + "/dirty_slurry/from_raw_block"));
            FluidChemicalToChemicalRecipeBuilder
                    .washing(
                            creatorF.from(Fluids.WATER, 5),
                            creatorC.from(MekUtChemicals.MU_MATERIALS_DIRTY_SLURRY.get(material).asStack(1)),
                            MekUtChemicals.MU_MATERIALS_CLEAN_SLURRY.get(material).asStack(1))
                    .build(output, MekUtConstants.rl("processing/" + material.name + "/clean_slurry"));
            ChemicalCrystallizerRecipeBuilder
                    .crystallizing(
                            creatorC.from(MekUtChemicals.MU_MATERIALS_CLEAN_SLURRY.get(material).asStack(200)),
                            MekUtItems.MU_MATERIALS_CRYSTAL.get(material).asStack(1))
                    .build(output, MekUtConstants.rl("processing/" + material.name + "/crystal"));
            ItemStackChemicalToItemStackRecipeBuilder
                    .injecting(
                            creatorI.from(MekUtItemTags.MU_MATERIALS_CRYSTAL.get(material), 1),
                            creatorC.from(MekanismChemicals.HYDROGEN_CHLORIDE.asStack(1)),
                            MekUtItems.MU_MATERIALS_SHARD.get(material).asStack(1),
                            true)
                    .build(output, MekUtConstants.rl("processing/" + material.name + "/shard/from_crystal"));
            ItemStackChemicalToItemStackRecipeBuilder
                    .injecting(
                            creatorI.from(MekUtItemTags.RAW_MU_MATERIALS.get(material), 3),
                            creatorC.from(MekanismChemicals.HYDROGEN_CHLORIDE.asStack(1)),
                            MekUtItems.MU_MATERIALS_SHARD.get(material).asStack(8),
                            true)
                    .build(output, MekUtConstants.rl("processing/" + material.name + "/shard/from_raw"));
            ItemStackChemicalToItemStackRecipeBuilder
                    .injecting(
                            creatorI.from(MekUtItemTags.RAW_MU_MATERIALS_BLOCK.get(material), 1),
                            creatorC.from(MekanismChemicals.HYDROGEN_CHLORIDE.asStack(2)),
                            MekUtItems.MU_MATERIALS_SHARD.get(material).asStack(24),
                            true)
                    .build(output, MekUtConstants.rl("processing/" + material.name + "/shard/from_raw_block"));
            ItemStackChemicalToItemStackRecipeBuilder
                    .purifying(
                            creatorI.from(MekUtItemTags.MU_MATERIALS_SHARD.get(material), 1),
                            creatorC.from(MekanismChemicals.OXYGEN.asStack(1)),
                            MekUtItems.MU_MATERIALS_CLUMP.get(material).asStack(1),
                            true)
                    .build(output, MekUtConstants.rl("processing/" + material.name + "/clump/from_shard"));
            ItemStackChemicalToItemStackRecipeBuilder
                    .purifying(
                            creatorI.from(MekUtItemTags.RAW_MU_MATERIALS.get(material), 1),
                            creatorC.from(MekanismChemicals.OXYGEN.asStack(1)),
                            MekUtItems.MU_MATERIALS_CLUMP.get(material).asStack(2),
                            true)
                    .build(output, MekUtConstants.rl("processing/" + material.name + "/clump/from_raw"));
            ItemStackChemicalToItemStackRecipeBuilder
                    .purifying(
                            creatorI.from(MekUtItemTags.RAW_MU_MATERIALS_BLOCK.get(material), 1),
                            creatorC.from(MekanismChemicals.OXYGEN.asStack(2)),
                            MekUtItems.MU_MATERIALS_CLUMP.get(material).asStack(18),
                            true)
                    .build(output, MekUtConstants.rl("processing/" + material.name + "/clump/from_raw_block"));
            if (material.isGem) {
                ItemStackToItemStackRecipeBuilder
                        .enriching(
                                creatorI.from(MekUtItemTags.MU_MATERIALS_CLUMP.get(material), 1),
                                MUMaterialDatagen.FINAL_ITEMS_MAP.get(material).copyWithCount(1))
                        .build(output, MekUtConstants.rl("processing/" + material.name + "/gem/from_clump"));
                ItemStackToItemStackRecipeBuilder
                        .enriching(
                                creatorI.from(MekUtItemTags.RAW_MU_MATERIALS.get(material), 3),
                                MUMaterialDatagen.FINAL_ITEMS_MAP.get(material).copyWithCount(4))
                        .build(output, MekUtConstants.rl("processing/" + material.name + "/gem/from_raw"));
                ItemStackToItemStackRecipeBuilder
                        .enriching(
                                creatorI.from(MekUtItemTags.RAW_MU_MATERIALS_BLOCK.get(material), 1),
                                MUMaterialDatagen.FINAL_ITEMS_MAP.get(material).copyWithCount(12))
                        .build(output, MekUtConstants.rl("processing/" + material.name + "/gem/from_raw_block"));
            } else {
                ItemStackToItemStackRecipeBuilder
                        .crushing(
                                creatorI.from(MekUtItemTags.MU_MATERIALS_CLUMP.get(material), 1),
                                MekUtItems.MU_MATERIALS_DIRTY_DUST.get(material).asStack(1))
                        .build(output, MekUtConstants.rl("processing/" + material.name + "/dirty_dust/from_clump"));
                ItemStackToItemStackRecipeBuilder
                        .enriching(
                                creatorI.from(MekUtItemTags.MU_MATERIALS_DIRTY_DUST.get(material), 1),
                                MUMaterialDatagen.FINAL_ITEMS_MAP.get(material).copyWithCount(1))
                        .build(output, MekUtConstants.rl("processing/" + material.name + "/dust/from_dirty_dust"));
                ItemStackToItemStackRecipeBuilder
                        .enriching(
                                creatorI.from(MekUtItemTags.RAW_MU_MATERIALS.get(material), 3),
                                MUMaterialDatagen.FINAL_ITEMS_MAP.get(material).copyWithCount(4))
                        .build(output, MekUtConstants.rl("processing/" + material.name + "/dust/from_raw"));
            }
            SimpleCookingRecipeBuilder
                    .smelting(
                            Ingredient.of(MekUtItemTags.RAW_MU_MATERIALS.get(material)),
                            RecipeCategory.MISC,
                            MUMaterialDatagen.FINAL_ITEMS_MAP.get(material),
                            0.6f, 200)
                    .unlockedBy("unlock", has.apply(MekUtItems.RAW_MU_MATERIALS.get(material)))
                    .save(output, MekUtConstants.rl("processing/" + material.name + "/final/from_raw_smelting"));
            SimpleCookingRecipeBuilder
                    .blasting(
                            Ingredient.of(MekUtItemTags.RAW_MU_MATERIALS.get(material)),
                            RecipeCategory.MISC,
                            MUMaterialDatagen.FINAL_ITEMS_MAP.get(material),
                            0.8f, 100)
                    .unlockedBy("unlock", has.apply(MekUtItems.RAW_MU_MATERIALS.get(material)))
                    .save(output, MekUtConstants.rl("processing/" + material.name + "/final/from_raw_blasting"));
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MUMaterialDatagen.FINAL_ITEMS_MAP.get(material))
                    .requires(MekUtItemTags.RAW_MU_MATERIALS.get(material))
                    .unlockedBy("unlock", has.apply(MekUtItems.RAW_MU_MATERIALS.get(material)))
                    .save(output, MekUtConstants.rl("processing/" + material.name + "/final/from_raw_crafting"));
        }

        for (OreAndRawData rawData : OreAndRawData.LIST) {
            ItemStackChemicalToItemStackRecipeBuilder
                    .metallurgicInfusing(
                            creatorI.from(
                                    ItemTags.create(
                                            ResourceLocation.fromNamespaceAndPath("c", "ores/" + rawData.name())),
                                    rawData.oreAmount()),
                            creatorC.from(MekUtChemicals.XP.get(), 100),
                            rawData.raw(),
                            false)
                    .build(output, MekUtConstants.rl("processing/" + rawData.name() + "/raw_from_ore"));
        }

        ItemStackChemicalToItemStackRecipeBuilder
                .metallurgicInfusing(
                        creatorI.from(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ores/naquadah"))),
                        creatorC.from(MekUtChemicals.XP.get(), 100),
                        ExtraItems.PROCESSED_RESOURCES.get(ResourceType.RAW, ExtraResource.NAQUADAH).asStack(3),
                        false)
                .addCondition(new ModLoadedCondition(MekanismExtras.MOD_ID))
                .build(output, MekUtConstants.rl("processing/naquadah/raw_from_ore"));

        SimpleCookingRecipeBuilder
                .smelting(Ingredient.of(MekUtItems.IRIDIUM_DUST), RecipeCategory.MISC, MekUtItems.IRIDIUM_INGOT, 400,
                        200)
                .unlockedBy("unlock", has.apply(MekUtItems.IRIDIUM_DUST))
                .save(output, MekUtConstants.rl("processing/iridium/ingot_smelting"));
        ItemStackToItemStackRecipeBuilder
                .crushing(creatorI.from(MekUtItems.IRIDIUM_INGOT.asStack()), MekUtItems.IRIDIUM_DUST.asStack())
                .build(output, MekUtConstants.rl("processing/iridium/dust"));

        ItemStackListFluidChemicalToItemFluidChemicalRecipeBuilder
                .smallDigitalReactionChamber(MekUtItems.RAW_MU_MATERIALS.get(MUMaterial.NETHERITE).asStack(1),
                        FluidStack.EMPTY, ChemicalStack.EMPTY)
                .addItemInput(Items.ANCIENT_DEBRIS, 4)
                .addItemInput(Items.RAW_GOLD, 4)
                .setFluidInput(Tags.Fluids.LAVA, 100)
                .setChemicalInput(MekUtChemicals.XP.asStack(1000))
                .build(output, MekUtConstants.rl("processing/netherite/raw_from_ancient_debris"));

        ItemStackToItemStackRecipeBuilder
                .crushing(
                        creatorI.from(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "gems/amethyst"))),
                        MekUtItems.AMETHYST_DUST.asStack())
                .build(output, MekUtConstants.rl("processing/amethyst/dust"));
    }
}
