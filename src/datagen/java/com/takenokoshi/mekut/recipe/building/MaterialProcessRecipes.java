package com.takenokoshi.mekut.recipe.building;

import java.util.List;
import java.util.function.Function;

import com.jerry.mekextras.MekanismExtras;
import com.jerry.mekextras.common.registries.ExtraItems;
import com.jerry.mekextras.common.resource.ExtraResource;
import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.enums.MekUtMaterial;
import com.takenokoshi.mekut.recipe.builder.ItemStackListFluidChemicalToItemFluidChemicalRecipeBuilder;
import com.takenokoshi.mekut.recipe.data.OreAndRawData;
import com.takenokoshi.mekut.registries.MekUtChemicals;
import com.takenokoshi.mekut.registries.MekUtItems;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.datagen.recipe.builder.ChemicalCrystallizerRecipeBuilder;
import mekanism.api.datagen.recipe.builder.ChemicalDissolutionRecipeBuilder;
import mekanism.api.datagen.recipe.builder.FluidChemicalToChemicalRecipeBuilder;
import mekanism.api.datagen.recipe.builder.ItemStackChemicalToItemStackRecipeBuilder;
import mekanism.api.datagen.recipe.builder.ItemStackToItemStackRecipeBuilder;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.common.conditions.NotCondition;
import net.neoforged.neoforge.common.conditions.TagEmptyCondition;
import net.neoforged.neoforge.fluids.FluidStack;

public class MaterialProcessRecipes {
    public static void build(RecipeOutput output,
            Function<ItemLike, Criterion<InventoryChangeTrigger.TriggerInstance>> has) {
        IItemStackIngredientCreator creatorI = IngredientCreatorAccess.item();
        IFluidStackIngredientCreator creatorF = IngredientCreatorAccess.fluid();
        IChemicalStackIngredientCreator creatorC = IngredientCreatorAccess.chemicalStack();

        MekUtMaterial.MATERIALS.forEach(material -> {
            final ItemStackIngredient oreIngredient = creatorI.from(material.oreTag(), material.oreNeeded());
            final ItemStackIngredient rawIngredient = creatorI.from(material.rawTag(), 3);
            final ItemStackIngredient rawBlockIngredient = creatorI.from(material.rawBlockTag(), 1);
            final ICondition oreCondition = new NotCondition(new TagEmptyCondition(material.oreTag()));
            ItemStackChemicalToItemStackRecipeBuilder
                    .metallurgicInfusing(
                            oreIngredient,
                            creatorC.fromHolder(MekUtChemicals.XP, 100),
                            material.raw().asStack(material.produceRate()),
                            false)
                    .addCondition(new NotCondition(new TagEmptyCondition(material.oreTag())))
                    .build(output, MekUtConstants.rl("processing/" + material.name() + "/raw_from_ore"));
            ChemicalDissolutionRecipeBuilder
                    .dissolution(
                            oreIngredient,
                            creatorC.fromHolder(MekanismChemicals.SULFURIC_ACID, 1L),
                            new ChemicalStack(material.dirtySlurry(), material.produceRate() / 3 * 1000),
                            true)
                    .addCondition(oreCondition)
                    .build(output, MekUtConstants.rl("processing/" + material.name() + "/slurry/dirty/from_ore"));
            ChemicalDissolutionRecipeBuilder
                    .dissolution(
                            rawIngredient,
                            creatorC.fromHolder(MekanismChemicals.SULFURIC_ACID, 1L),
                            new ChemicalStack(material.dirtySlurry(), 2000),
                            true)
                    .build(output, MekUtConstants.rl("processing/" + material.name() + "/slurry/dirty/from_raw"));
            ChemicalDissolutionRecipeBuilder
                    .dissolution(
                            rawBlockIngredient,
                            creatorC.fromHolder(MekanismChemicals.SULFURIC_ACID, 2L),
                            new ChemicalStack(material.dirtySlurry(), 6000),
                            true)
                    .build(output, MekUtConstants.rl("processing/" + material.name() + "/slurry/dirty/from_raw_block"));
            FluidChemicalToChemicalRecipeBuilder
                    .washing(
                            creatorF.from(Tags.Fluids.WATER, 5),
                            creatorC.fromHolder(material.dirtySlurry(), 1L),
                            new ChemicalStack(material.cleanSlurry(), 1L))
                    .build(output, MekUtConstants.rl("processing/" + material.name() + "/slurry/clean"));
            ChemicalCrystallizerRecipeBuilder
                    .crystallizing(
                            creatorC.fromHolder(material.cleanSlurry(), 200L),
                            material.crystal().asStack(1))
                    .build(output, MekUtConstants.rl("processing/" + material.name() + "/crystal/from_slurry"));
            ItemStackChemicalToItemStackRecipeBuilder
                    .injecting(
                            oreIngredient,
                            creatorC.fromHolder(MekanismChemicals.HYDROGEN_CHLORIDE, 1),
                            material.shard().asStack(material.produceRate() / 3 * 4),
                            true)
                    .addCondition(oreCondition)
                    .build(output, MekUtConstants.rl("processing/" + material.name() + "/shard/from_ore"));
            ItemStackChemicalToItemStackRecipeBuilder
                    .injecting(
                            rawIngredient,
                            creatorC.fromHolder(MekanismChemicals.HYDROGEN_CHLORIDE, 1),
                            material.shard().asStack(8),
                            true)
                    .build(output, MekUtConstants.rl("processing/" + material.name() + "/shard/from_raw"));
            ItemStackChemicalToItemStackRecipeBuilder
                    .injecting(
                            rawBlockIngredient,
                            creatorC.fromHolder(MekanismChemicals.HYDROGEN_CHLORIDE, 2),
                            material.shard().asStack(24),
                            true)
                    .build(output, MekUtConstants.rl("processing/" + material.name() + "/shard/from_raw_block"));
            ItemStackChemicalToItemStackRecipeBuilder
                    .injecting(
                            creatorI.from(material.crystalTag(), 1),
                            creatorC.fromHolder(MekanismChemicals.HYDROGEN_CHLORIDE, 1),
                            material.shard().asStack(1),
                            true)
                    .build(output, MekUtConstants.rl("processing/" + material.name() + "/shard/crystal"));
            ItemStackChemicalToItemStackRecipeBuilder
                    .purifying(
                            oreIngredient,
                            creatorC.fromHolder(MekanismChemicals.OXYGEN, 1),
                            material.clump().asStack(material.produceRate()),
                            true)
                    .addCondition(oreCondition)
                    .build(output, MekUtConstants.rl("processing/" + material.name() + "/clump/from_ore"));
            ItemStackChemicalToItemStackRecipeBuilder
                    .purifying(
                            creatorI.from(material.rawTag(), material.oreNeeded()),
                            creatorC.fromHolder(MekanismChemicals.OXYGEN, 1),
                            material.clump().asStack(2),
                            true)
                    .build(output, MekUtConstants.rl("processing/" + material.name() + "/clump/from_raw"));
            ItemStackChemicalToItemStackRecipeBuilder
                    .purifying(
                            rawBlockIngredient,
                            creatorC.fromHolder(MekanismChemicals.OXYGEN, 2),
                            material.clump().asStack(18),
                            true)
                    .build(output, MekUtConstants.rl("processing/" + material.name() + "/clump/from_raw_block"));
            ItemStackChemicalToItemStackRecipeBuilder
                    .purifying(
                            creatorI.from(material.shardTag(), 1),
                            creatorC.fromHolder(MekanismChemicals.OXYGEN, 1),
                            material.clump().asStack(1),
                            true)
                    .build(output, MekUtConstants.rl("processing/" + material.name() + "/clump/from_shard"));
            ItemStackToItemStackRecipeBuilder crushingBuilder = ItemStackToItemStackRecipeBuilder
                    .crushing(
                            creatorI.from(material.clumpTag(), 1),
                            new ItemStack(material.dust(), 1));
            ItemStackToItemStackRecipeBuilder enrichingBuilder = ItemStackToItemStackRecipeBuilder
                    .enriching(
                            creatorI.from(material.clumpTag(), 1),
                            new ItemStack(material.finalItem(), 1));
            ItemStackToItemStackRecipeBuilder enrichingBuilder2 = ItemStackToItemStackRecipeBuilder
                    .enriching(
                            rawIngredient,
                            new ItemStack(material.finalItem(), 4));
            ItemStackToItemStackRecipeBuilder enrichingBuilder3 = ItemStackToItemStackRecipeBuilder
                    .enriching(
                            rawBlockIngredient,
                            new ItemStack(material.finalItem(), 12));
            for (String id : material.requiredMods()) {
                crushingBuilder.addCondition(new ModLoadedCondition(id));
                enrichingBuilder.addCondition(new ModLoadedCondition(id));
                enrichingBuilder2.addCondition(new ModLoadedCondition(id));
                enrichingBuilder3.addCondition(new ModLoadedCondition(id));
            }
            crushingBuilder
                    .build(output, MekUtConstants.rl("processing/" + material.name() + "/dust/from_clump"));
            enrichingBuilder
                    .build(output, MekUtConstants.rl("processing/" + material.name() + "/final/from_clump"));
            enrichingBuilder2
                    .build(output, MekUtConstants.rl("processing/" + material.name() + "/final/from_raw"));
            enrichingBuilder3
                    .build(output, MekUtConstants.rl("processing/" + material.name() + "/final/from_raw_block"));
            ShapelessRecipeBuilder
                    .shapeless(RecipeCategory.MISC, new ItemStack(material.finalItem()))
                    .requires(material.rawTag())
                    .unlockedBy("has", has.apply(material.raw()))
                    .save(output, MekUtConstants.rl("processing/" + material.name() + "/final/from_raw_craft"));
            ShapelessRecipeBuilder
                    .shapeless(RecipeCategory.MISC, material.raw().asStack(9))
                    .requires(material.rawBlockTag())
                    .unlockedBy("has", has.apply(material.rawBlock()))
                    .save(output, MekUtConstants.rl("processing/" + material.name() + "/raw/from_block"));
            ShapelessRecipeBuilder
                    .shapeless(RecipeCategory.MISC, new ItemStack(material.rawBlock(), 1))
                    .requires(material.raw(), 9)
                    .unlockedBy("has", has.apply(material.raw()))
                    .save(output, MekUtConstants.rl("processing/" + material.name() + "/raw_block/from_raw"));

        });

        OreAndRawData.LIST.forEach(data -> {
            ItemStackChemicalToItemStackRecipeBuilder
                    .metallurgicInfusing(
                            creatorI.from(
                                    ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ores/" + data.name())),
                                    data.oreAmount()),
                            creatorC.fromHolder(MekUtChemicals.XP, 100),
                            data.raw(),
                            false)
                    .build(output, MekUtConstants.rl("processing/" + data.name() + "raw_from_ore"));
        });

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
                .smallDigitalReactionChamber(MekUtItems.RAW_NETHERITE.asStack(1),
                        FluidStack.EMPTY, ChemicalStack.EMPTY)
                .addItemInput(Items.ANCIENT_DEBRIS, 4)
                .addItemInput(Items.RAW_GOLD, 4)
                .setFluidInput(Tags.Fluids.LAVA, 100)
                .setChemicalInput(MekUtChemicals.XP.asStack(1000))
                .build(output, MekUtConstants.rl("processing/netherite/raw_from_ancient_debris"));
        ItemStackListFluidChemicalToItemFluidChemicalRecipeBuilder
                .smallDigitalReactionChamber(MekUtItems.RAW_SILICON.asStack(36),
                        FluidStack.EMPTY, ChemicalStack.EMPTY)
                .addItemInput(IngredientCreatorAccess.item().from(1, List.of(
                        ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "raw_materials/quartz")),
                        ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "raw_materials/certus_quartz")))))
                .addItemInput(Items.SAND, 6)
                .setFluidInput(Tags.Fluids.LAVA, 100)
                .setChemicalInput(MekanismChemicals.CARBON.asStack(160))
                .build(output, MekUtConstants.rl("processing/silicon/raw_from_raw_quartz"));

        ItemStackToItemStackRecipeBuilder
                .crushing(
                        creatorI.from(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "gems/amethyst"))),
                        MekUtItems.AMETHYST_DUST.asStack())
                .build(output, MekUtConstants.rl("processing/amethyst/dust"));
    }
}
