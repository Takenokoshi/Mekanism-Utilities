package takenokoshi.mekut.datagen.recipe;

import mekanism.api.datagen.recipe.builder.ChemicalCrystallizerRecipeBuilder;
import mekanism.api.datagen.recipe.builder.ChemicalDissolutionRecipeBuilder;
import mekanism.api.datagen.recipe.builder.FluidChemicalToChemicalRecipeBuilder;
import mekanism.api.datagen.recipe.builder.ItemStackChemicalToItemStackRecipeBuilder;
import mekanism.api.datagen.recipe.builder.ItemStackToItemStackRecipeBuilder;
import mekanism.api.datagen.recipe.builder.PressurizedReactionRecipeBuilder;
import mekanism.api.datagen.recipe.builder.RotaryRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismChemicals;
import mekanism.common.registries.MekanismItems;
import mekanism.common.resource.PrimaryResource;
import mekanism.common.resource.ResourceType;
import mekanism.common.resource.ore.OreType;
import mekanism.common.tags.MekanismTags;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.NotCondition;
import net.neoforged.neoforge.common.conditions.TagEmptyCondition;
import takenokoshi.mekal.datagen.recipe.builder.AbstractCookingRecipeBuilder;
import takenokoshi.mekut.common.MekUt;
import takenokoshi.mekut.common.registries.MekUtChemicals;
import takenokoshi.mekut.common.registries.MekUtFluids;
import takenokoshi.mekut.common.registries.MekUtItems;
import takenokoshi.mekut.common.registries.MekUtMetalType;
import takenokoshi.mekut.datagen.recipe.builder.MekUtItemStackToItemStackRecipeBuilder;

public class ProcessingRecipeProvider {

    static void buildRecipes(RecipeOutput recipeOutput) {
        for (final MekUtMetalType metalType : MekUtMetalType.values()) {
            MekUtItemStackToItemStackRecipeBuilder
                    .mineralExtracting(
                            IngredientCreatorAccess.item().from(metalType.oreTag, 1),
                            MekUtItems.METAL_RAW_MATERIALS.get(metalType).asStack(3))
                    .build(recipeOutput.withConditions(new ICondition[] {
                            new NotCondition(new TagEmptyCondition(metalType.oreTag))
                    }), MekUt.rl("processing/" + metalType.name + "/raw_material/from_ore"));

            ChemicalDissolutionRecipeBuilder.dissolution(
                    IngredientCreatorAccess.item().from(metalType.oreTag, 1),
                    IngredientCreatorAccess.chemicalStack().fromHolder(MekanismChemicals.SULFURIC_ACID, 1),
                    MekUtChemicals.METAL_DIRTY_SLURRIES.get(metalType).asStack(1000L * metalType.multiplier),
                    true)
                    .build(recipeOutput.withConditions(new ICondition[] {
                            new NotCondition(new TagEmptyCondition(metalType.oreTag))
                    }), MekUt.rl("processing/" + metalType.name + "/slurry/dirty/from_ore"));
            ChemicalDissolutionRecipeBuilder
                    .dissolution(
                            IngredientCreatorAccess.item().from(metalType.rawTag, 3),
                            IngredientCreatorAccess.chemicalStack().fromHolder(MekanismChemicals.SULFURIC_ACID, 1),
                            MekUtChemicals.METAL_DIRTY_SLURRIES.get(metalType).asStack(2000L * metalType.multiplier),
                            true)
                    .build(recipeOutput, MekUt.rl("processing/" + metalType.name + "/slurry/dirty/from_raw_ore"));
            ChemicalDissolutionRecipeBuilder
                    .dissolution(
                            IngredientCreatorAccess.item().from(metalType.rawBlockTag, 1),
                            IngredientCreatorAccess.chemicalStack().fromHolder(MekanismChemicals.SULFURIC_ACID, 2),
                            MekUtChemicals.METAL_DIRTY_SLURRIES.get(metalType).asStack(6000L * metalType.multiplier),
                            true)
                    .build(recipeOutput.withConditions(new ICondition[] {
                            new NotCondition(new TagEmptyCondition(metalType.rawBlockTag))
                    }), MekUt.rl("processing/" + metalType.name + "/slurry/dirty/from_raw_block"));

            FluidChemicalToChemicalRecipeBuilder
                    .washing(
                            IngredientCreatorAccess.fluid().from(Tags.Fluids.WATER, 5),
                            IngredientCreatorAccess.chemicalStack()
                                    .fromHolder(MekUtChemicals.METAL_DIRTY_SLURRIES.get(metalType), 1L),
                            MekUtChemicals.METAL_CLEAN_SLURRIES.get(metalType).asStack(1L))
                    .build(recipeOutput, MekUt.rl("processing/" + metalType.name + "/slurry/clean"));

            ChemicalCrystallizerRecipeBuilder
                    .crystallizing(
                            IngredientCreatorAccess.chemicalStack()
                                    .fromHolder(MekUtChemicals.METAL_CLEAN_SLURRIES.get(metalType), 200L),
                            MekUtItems.METAL_CRYSTALS.get(metalType).asStack(1))
                    .build(recipeOutput, MekUt.rl("processing/" + metalType.name + "/crystal/from_slurry"));

            ItemStackChemicalToItemStackRecipeBuilder
                    .injecting(
                            IngredientCreatorAccess.item().from(metalType.oreTag, 1),
                            IngredientCreatorAccess.chemicalStack().fromHolder(MekanismChemicals.HYDROGEN_CHLORIDE, 1),
                            MekUtItems.METAL_SHARDS.get(metalType).asStack(4 * metalType.multiplier),
                            true)
                    .build(recipeOutput.withConditions(new ICondition[] {
                            new NotCondition(new TagEmptyCondition(metalType.oreTag))
                    }), MekUt.rl("processing/" + metalType.name + "/shard/from_ore"));
            ItemStackChemicalToItemStackRecipeBuilder
                    .injecting(
                            IngredientCreatorAccess.item().from(metalType.rawTag, 3),
                            IngredientCreatorAccess.chemicalStack().fromHolder(MekanismChemicals.HYDROGEN_CHLORIDE, 1),
                            MekUtItems.METAL_SHARDS.get(metalType).asStack(8 * metalType.multiplier),
                            true)
                    .build(recipeOutput, MekUt.rl("processing/" + metalType.name + "/shard/from_raw_ore"));
            ItemStackChemicalToItemStackRecipeBuilder
                    .injecting(
                            IngredientCreatorAccess.item().from(metalType.rawBlockTag, 1),
                            IngredientCreatorAccess.chemicalStack().fromHolder(MekanismChemicals.HYDROGEN_CHLORIDE, 2),
                            MekUtItems.METAL_SHARDS.get(metalType).asStack(24 * metalType.multiplier),
                            true)
                    .build(recipeOutput.withConditions(new ICondition[] {
                            new NotCondition(new TagEmptyCondition(metalType.rawBlockTag))
                    }), MekUt.rl("processing/" + metalType.name + "/shard/from_raw_block"));
            ItemStackChemicalToItemStackRecipeBuilder
                    .injecting(
                            IngredientCreatorAccess.item().from(metalType.shardTag, 1),
                            IngredientCreatorAccess.chemicalStack().fromHolder(MekanismChemicals.HYDROGEN_CHLORIDE, 1),
                            MekUtItems.METAL_SHARDS.get(metalType).asStack(1),
                            true)
                    .build(recipeOutput, MekUt.rl("processing/" + metalType.name + "/shard/from_crystal"));

            ItemStackChemicalToItemStackRecipeBuilder
                    .purifying(
                            IngredientCreatorAccess.item().from(metalType.oreTag, 1),
                            IngredientCreatorAccess.chemicalStack().fromHolder(MekanismChemicals.OXYGEN, 1),
                            MekUtItems.METAL_CLUMPS.get(metalType).asStack(3 * metalType.multiplier),
                            true)
                    .build(recipeOutput.withConditions(new ICondition[] {
                            new NotCondition(new TagEmptyCondition(metalType.oreTag))
                    }), MekUt.rl("processing/" + metalType.name + "/clump/from_ore"));
            ItemStackChemicalToItemStackRecipeBuilder
                    .purifying(
                            IngredientCreatorAccess.item().from(metalType.rawTag, 1),
                            IngredientCreatorAccess.chemicalStack().fromHolder(MekanismChemicals.OXYGEN, 1),
                            MekUtItems.METAL_CLUMPS.get(metalType).asStack(2 * metalType.multiplier),
                            true)
                    .build(recipeOutput, MekUt.rl("processing/" + metalType.name + "/clump/from_raw_ore"));
            ItemStackChemicalToItemStackRecipeBuilder
                    .purifying(
                            IngredientCreatorAccess.item().from(metalType.rawBlockTag, 1),
                            IngredientCreatorAccess.chemicalStack().fromHolder(MekanismChemicals.OXYGEN, 2),
                            MekUtItems.METAL_CLUMPS.get(metalType).asStack(18 * metalType.multiplier),
                            true)
                    .build(recipeOutput.withConditions(new ICondition[] {
                            new NotCondition(new TagEmptyCondition(metalType.rawBlockTag))
                    }), MekUt.rl("processing/" + metalType.name + "/clump/from_raw_block"));
            ItemStackChemicalToItemStackRecipeBuilder
                    .purifying(
                            IngredientCreatorAccess.item().from(metalType.shardTag, 1),
                            IngredientCreatorAccess.chemicalStack().fromHolder(MekanismChemicals.OXYGEN, 1),
                            MekUtItems.METAL_CLUMPS.get(metalType).asStack(1),
                            true)
                    .build(recipeOutput, MekUt.rl("processing/" + metalType.name + "/clump/from_shard"));

            ItemStackToItemStackRecipeBuilder
                    .crushing(
                            IngredientCreatorAccess.item().from(metalType.clumpTag, 1),
                            MekUtItems.METAL_DIRTY_DUSTS.get(metalType).asStack(1))
                    .build(recipeOutput, MekUt.rl("processing/" + metalType.name + "/dirty_dust/from_clump"));

            if (metalType == MekUtMetalType.REDSTONE) {
                continue;
            }

            ItemStackToItemStackRecipeBuilder
                    .enriching(
                            IngredientCreatorAccess.item().from(metalType.oreTag, 1),
                            MekUtItems.METAL_DUSTS.get(metalType).asStack(2 * metalType.multiplier))
                    .build(recipeOutput.withConditions(new ICondition[] {
                            new NotCondition(new TagEmptyCondition(metalType.oreTag))
                    }), MekUt.rl("processing/" + metalType.name + "/dust/from_ore"));
            ItemStackToItemStackRecipeBuilder
                    .enriching(
                            IngredientCreatorAccess.item().from(metalType.rawTag, 3),
                            MekUtItems.METAL_DUSTS.get(metalType).asStack(4 * metalType.multiplier))
                    .build(recipeOutput, MekUt.rl("processing/" + metalType.name + "/dust/from_raw_ore"));
            ItemStackToItemStackRecipeBuilder
                    .enriching(
                            IngredientCreatorAccess.item().from(metalType.rawBlockTag, 1),
                            MekUtItems.METAL_DUSTS.get(metalType).asStack(12 * metalType.multiplier))
                    .build(recipeOutput.withConditions(new ICondition[] {
                            new NotCondition(new TagEmptyCondition(metalType.rawBlockTag))
                    }), MekUt.rl("processing/" + metalType.name + "/dust/from_raw_block"));
            ItemStackToItemStackRecipeBuilder
                    .enriching(
                            IngredientCreatorAccess.item().from(metalType.dirtyDustTag, 1),
                            MekUtItems.METAL_DUSTS.get(metalType).asStack(1))
                    .build(recipeOutput, MekUt.rl("processing/" + metalType.name + "/dust/from_dirty_dust"));
            ItemStackToItemStackRecipeBuilder
                    .crushing(
                            IngredientCreatorAccess.item().from(metalType.ingotTag, 1),
                            MekUtItems.METAL_DUSTS.get(metalType).asStack(1))
                    .build(recipeOutput, MekUt.rl("processing/" + metalType.name + "/dust/from_ingot"));

            // 鉱石や原石からの精錬は書かない。こちらで鉱石の入手手段を用意するつもりはない為
            AbstractCookingRecipeBuilder
                    .smelting(
                            Ingredient.of(metalType.dustTag),
                            MekUtItems.METAL_INGOTS.get(metalType).asStack(1))
                    .build(recipeOutput, MekUt.rl("processing/" + metalType.name + "/ingot/from_dust_smelting"));
            AbstractCookingRecipeBuilder
                    .blasting(
                            Ingredient.of(metalType.dustTag),
                            MekUtItems.METAL_INGOTS.get(metalType).asStack(1))
                    .build(recipeOutput, MekUt.rl("processing/" + metalType.name + "/ingot/from_dust_blasting"));

        }

        // Redstone.
        ItemStackToItemStackRecipeBuilder
                .enriching(
                        IngredientCreatorAccess.item().from(MekUtMetalType.REDSTONE.dirtyDustTag, 1),
                        new ItemStack(Items.REDSTONE, 1))
                .build(recipeOutput, MekUt.rl("processing/redstone/dust/from_dirty_dust"));

        // Metals suported by Mekanism.
        MekUtItemStackToItemStackRecipeBuilder
                .mineralExtracting(
                        IngredientCreatorAccess.item().from(Tags.Items.ORES_COPPER, 1),
                        new ItemStack(Items.RAW_COPPER, 12))
                .build(recipeOutput, MekUt.rl("processing/copper/raw_material/from_ore"));
        MekUtItemStackToItemStackRecipeBuilder
                .mineralExtracting(
                        IngredientCreatorAccess.item().from(Tags.Items.ORES_GOLD, 1),
                        new ItemStack(Items.RAW_GOLD, 3))
                .build(recipeOutput, MekUt.rl("processing/gold/raw_material/from_ore"));
        MekUtItemStackToItemStackRecipeBuilder
                .mineralExtracting(
                        IngredientCreatorAccess.item().from(Tags.Items.ORES_IRON, 1),
                        new ItemStack(Items.RAW_IRON, 3))
                .build(recipeOutput, MekUt.rl("processing/iron/raw_material/from_ore"));
        MekUtItemStackToItemStackRecipeBuilder
                .mineralExtracting(
                        IngredientCreatorAccess.item().from(MekanismTags.Items.ORES.get(OreType.LEAD), 1),
                        MekanismItems.PROCESSED_RESOURCES.get(ResourceType.RAW, PrimaryResource.LEAD).asStack(3))
                .build(recipeOutput, MekUt.rl("processing/lead/raw_material/from_ore"));
        MekUtItemStackToItemStackRecipeBuilder
                .mineralExtracting(
                        IngredientCreatorAccess.item().from(MekanismTags.Items.ORES.get(OreType.OSMIUM), 1),
                        MekanismItems.PROCESSED_RESOURCES.get(ResourceType.RAW, PrimaryResource.OSMIUM).asStack(3))
                .build(recipeOutput, MekUt.rl("processing/osmium/raw_material/from_ore"));
        MekUtItemStackToItemStackRecipeBuilder
                .mineralExtracting(
                        IngredientCreatorAccess.item().from(MekanismTags.Items.ORES.get(OreType.TIN), 1),
                        MekanismItems.PROCESSED_RESOURCES.get(ResourceType.RAW, PrimaryResource.TIN).asStack(3))
                .build(recipeOutput, MekUt.rl("processing/tin/raw_material/from_ore"));
        MekUtItemStackToItemStackRecipeBuilder
                .mineralExtracting(
                        IngredientCreatorAccess.item().from(MekanismTags.Items.ORES.get(OreType.URANIUM), 1),
                        MekanismItems.PROCESSED_RESOURCES.get(ResourceType.RAW, PrimaryResource.URANIUM).asStack(3))
                .build(recipeOutput, MekUt.rl("processing/uranium/raw_material/from_ore"));

        // Artificial Diamond
        PressurizedReactionRecipeBuilder
                .reaction(
                        IngredientCreatorAccess.item().from(MekanismTags.Items.DUSTS_DIAMOND, 1),
                        IngredientCreatorAccess.fluid().fromHolder(MekUtFluids.GLOWSTONE_SOLVENT, 2000),
                        IngredientCreatorAccess.chemicalStack().fromHolder(MekanismChemicals.CARBON, 5120),
                        400,
                        MekUtItems.RAW_DIAMOND.asStack(2),
                        MekUtChemicals.SPENT_GLOWSTONE_SOLVENT.asStack(2000L))
                .build(recipeOutput, MekUt.rl("processing/diamond/raw_from_carbon"));
        MekUtItemStackToItemStackRecipeBuilder
                .mineralExtracting(
                        IngredientCreatorAccess.item().from(MekUtItems.RAW_DIAMOND, 1),
                        new ItemStack(Items.DIAMOND, 1))
                .build(recipeOutput, MekUt.rl("processing/diamond/from_raw_ore"));
        RotaryRecipeBuilder
                .rotary(
                        IngredientCreatorAccess.fluid().fromHolder(MekUtFluids.GLOWSTONE_SOLVENT, 100),
                        IngredientCreatorAccess.chemicalStack().fromHolder(MekUtChemicals.GLOWSTONE_SOLVENT, 100L),
                        MekUtChemicals.GLOWSTONE_SOLVENT.asStack(100L),
                        MekUtFluids.GLOWSTONE_SOLVENT.asStack(100))
                .build(recipeOutput, MekUt.rl("processing/diamond/solvent_rotary"));
        FluidChemicalToChemicalRecipeBuilder
                .washing(
                        IngredientCreatorAccess.fluid().from(MekanismTags.Fluids.SODIUM, 1),
                        IngredientCreatorAccess.chemicalStack().fromHolder(MekUtChemicals.SPENT_GLOWSTONE_SOLVENT, 1L),
                        MekUtChemicals.GLOWSTONE_SOLVENT.asStack(1L))
                .build(recipeOutput, MekUt.rl("processing/diamond/solvent_recycle"));

        // Netherite
        ItemStackChemicalToItemStackRecipeBuilder
                .injecting(
                        IngredientCreatorAccess.item().from(Items.ANCIENT_DEBRIS, 1),
                        IngredientCreatorAccess.chemicalStack().fromHolder(MekanismChemicals.SULFURIC_ACID, 2L),
                        MekUtItems.NETHERITE_CRYSTAL.asStack(5),
                        true)
                .build(recipeOutput, MekUt.rl("processing/netherite/ancient_debris_to_crystal"));
        MekUtItemStackToItemStackRecipeBuilder
                .mineralExtracting(
                        IngredientCreatorAccess.item().from(MekUtItems.NETHERITE_CRYSTAL, 1),
                        MekanismItems.DIRTY_NETHERITE_SCRAP.asStack(1))
                .build(recipeOutput, MekUt.rl("processing/netherite/crystal_to_dirty_scrap"));

    }
}
