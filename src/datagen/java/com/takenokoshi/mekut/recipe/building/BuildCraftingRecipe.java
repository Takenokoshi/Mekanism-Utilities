package com.takenokoshi.mekut.recipe.building;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.fxd927.mekanismelements.common.registries.MSItems;
import com.glodblock.github.extendedae.common.EAESingletons;
import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.registration.MachineRegistryObject;
import com.takenokoshi.mekut.registries.MekUtItems;
import com.takenokoshi.mekut.registries.MekUtMachines;

import appeng.core.definitions.AEBlocks;
import appeng.core.definitions.AEItems;
import mekanism.common.registries.MekanismBlocks;
import mekanism.common.registries.MekanismItems;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.pedroksl.advanced_ae.common.definitions.AAEBlocks;

public class BuildCraftingRecipe {

    private static final List<SimpleMachineRecipeData> NORMAL_MACHINES = new ArrayList<>();
    private static final List<SimpleMachineRecipeData> TWEAKED_MACHINES = new ArrayList<>();
    private static final List<SimpleMachineRecipeData> STANDARD_MACHINES = new ArrayList<>();
    private static final List<SimpleMachineRecipeData> STANDARD_GAS_MACHINES = new ArrayList<>();

    public static void build(RecipeOutput output,
            Function<ItemLike, Criterion<InventoryChangeTrigger.TriggerInstance>> has) {
        ShapedRecipeBuilder
                .shaped(RecipeCategory.REDSTONE, MekUtItems.DIGITAL_CONTROL_CIRCUIT)
                .define('A', MekUtItems.ELASTIC_ALLOY)
                .define('B', MekanismItems.BASIC_CONTROL_CIRCUIT)
                .pattern("ABA")
                .unlockedBy("unlock", has.apply(MekUtItems.ELASTIC_ALLOY))
                .save(output, MekUtConstants.rl("crafting/digital_control_circuit"));
        ShapedRecipeBuilder
                .shaped(RecipeCategory.REDSTONE, MekUtItems.STANDARD_CONTROL_CIRCUIT)
                .define('A', MekUtItems.CONVERGENT_ALLOY)
                .define('B', MekUtItems.DIGITAL_CONTROL_CIRCUIT)
                .pattern("ABA")
                .unlockedBy("unlock", has.apply(MekUtItems.CONVERGENT_ALLOY))
                .save(output, MekUtConstants.rl("crafting/standard_control_circuit"));
        ShapedRecipeBuilder
                .shaped(RecipeCategory.REDSTONE, MekUtItems.KNOWLEDGE_CONTROL_CIRCUIT)
                .define('A', MekUtItems.XP_ALLOY)
                .define('B', MekUtItems.STANDARD_CONTROL_CIRCUIT)
                .pattern("ABA")
                .unlockedBy("unlock", has.apply(MekUtItems.XP_ALLOY))
                .save(output, MekUtConstants.rl("crafting/knowledge_control_circuit"));
        ShapedRecipeBuilder
                .shaped(RecipeCategory.REDSTONE, MekUtMachines.CHEMICAL_CUTTER)
                .define('C', MekanismBlocks.STEEL_CASING)
                .define('A', MekanismItems.REINFORCED_ALLOY)
                .define('B', MekUtItems.STANDARD_CONTROL_CIRCUIT)
                .define('D', EAESingletons.CIRCUIT_CUTTER)
                .pattern("ABA")
                .pattern("DCD")
                .pattern("ABA")
                .unlockedBy("unlock", has.apply(EAESingletons.CIRCUIT_CUTTER))
                .save(output, MekUtConstants.rl("crafting/machine/chemical_cutter"));
        ShapedRecipeBuilder
                .shaped(RecipeCategory.REDSTONE, MekUtMachines.ICE_MAKER)
                .define('C', MekanismBlocks.STEEL_CASING)
                .define('A', ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ingots/copper")))
                .define('B', MekUtItems.DIGITAL_CONTROL_CIRCUIT)
                .define('D', AEItems.ENTROPY_MANIPULATOR)
                .pattern("ABA")
                .pattern("DCD")
                .pattern("ABA")
                .unlockedBy("unlock", has.apply(Items.ICE))
                .save(output, MekUtConstants.rl("crafting/machine/ice_maker"));
        ShapedRecipeBuilder
                .shaped(RecipeCategory.REDSTONE, MekUtMachines.LAZER_COMPRESS_NUCLEO_SYNTHESIZER)
                .define('C', MekanismBlocks.STEEL_CASING)
                .define('A', ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ingots/netherite")))
                .define('B', MekUtItems.KNOWLEDGE_CONTROL_CIRCUIT)
                .define('D', MekanismBlocks.LASER)
                .pattern("ABA")
                .pattern("DCD")
                .pattern("ABA")
                .unlockedBy("unlock", has.apply(MekanismBlocks.LASER))
                .save(output, MekUtConstants.rl("crafting/machine/lazer_compress_nucleo_synthesizer"));
        ShapedRecipeBuilder
                .shaped(RecipeCategory.REDSTONE, MekUtMachines.SMALL_DIGITAL_ASSEMBLER)
                .define('C', MekanismBlocks.STEEL_CASING)
                .define('A', ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ingots/bronze")))
                .define('B', MekUtItems.DIGITAL_CONTROL_CIRCUIT)
                .define('D', EAESingletons.CRYSTAL_ASSEMBLER)
                .pattern("ABA")
                .pattern("DCD")
                .pattern("ABA")
                .unlockedBy("unlock", has.apply(EAESingletons.CRYSTAL_ASSEMBLER))
                .save(output, MekUtConstants.rl("crafting/machine/small_digital_assembler"));
        ShapedRecipeBuilder
                .shaped(RecipeCategory.REDSTONE, MekUtMachines.SMALL_DIGITAL_REACTION_CHAMBER)
                .define('C', MekanismBlocks.STEEL_CASING)
                .define('A', ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ingots/steel")))
                .define('B', MekUtItems.STANDARD_CONTROL_CIRCUIT)
                .define('D', AAEBlocks.REACTION_CHAMBER)
                .define('E', MekanismBlocks.PRESSURIZED_REACTION_CHAMBER)
                .pattern("ABA")
                .pattern("DCE")
                .pattern("ABA")
                .unlockedBy("unlock", has.apply(AAEBlocks.REACTION_CHAMBER))
                .save(output, MekUtConstants.rl("crafting/machine/small_digital_reaction_chamber"));
        ShapedRecipeBuilder
                .shaped(RecipeCategory.REDSTONE, MekUtMachines.STELLAR_GENESIS_CHAMBER)
                .define('C', MekanismBlocks.STEEL_CASING)
                .define('A', MekUtItems.STARDUST_ALLOY)
                .define('B', MekUtItems.COMET_CONTROL_CIRCUIT)
                .define('D', MekanismItems.ANTIMATTER_PELLET)
                .pattern("ABA")
                .pattern("DCD")
                .pattern("ABA")
                .unlockedBy("unlock", has.apply(MekUtItems.COMET_CONTROL_CIRCUIT))
                .save(output, MekUtConstants.rl("crafting/machine/stellar_genesis_chamber"));
        ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, Items.BEACON)
                .define('G', Items.GLASS)
                .define('S', MekUtItems.ARTIFICIAL_STAR)
                .define('O', Items.OBSIDIAN)
                .pattern("GGG")
                .pattern("GSG")
                .pattern("OOO")
                .unlockedBy("unlock", has.apply(MekUtItems.COMET_CONTROL_CIRCUIT))
                .save(output, MekUtConstants.rl("crafting/beacon"));
        ShapelessRecipeBuilder
                .shapeless(RecipeCategory.DECORATIONS, MSItems.HIGH_QUALITY_CONCRETE_POWDER_AQUA.asStack(8))
                .requires(MekUtItems.AQUA_DYE, 1)
                .requires(MSItems.HIGH_QUALITY_CONCRETE_POWDER, 8)
                .unlockedBy("unlock", has.apply(MekUtItems.AQUA_DYE))
                .save(output, MekUtConstants.rl("crafting/hq_concreate/aqua_powder"));
        ShapelessRecipeBuilder
                .shapeless(RecipeCategory.DECORATIONS, MSItems.HIGH_QUALITY_CONCRETE_POWDER_DARK_RED.asStack(8))
                .requires(MekUtItems.DARK_RED_DYE, 1)
                .requires(MSItems.HIGH_QUALITY_CONCRETE_POWDER, 8)
                .unlockedBy("unlock", has.apply(MekUtItems.DARK_RED_DYE))
                .save(output, MekUtConstants.rl("crafting/hq_concreate/dark_red_powder"));
        NORMAL_MACHINES.forEach(data -> {
            ShapedRecipeBuilder
                    .shaped(RecipeCategory.REDSTONE, data.output)
                    .define('C', MekanismBlocks.STEEL_CASING)
                    .define('A', MekUtItems.ELASTIC_ALLOY)
                    .define('B', MekUtItems.DIGITAL_CONTROL_CIRCUIT)
                    .define('D', data.input)
                    .pattern("ABA")
                    .pattern("DCD")
                    .pattern("ABA")
                    .unlockedBy("unlock", has.apply(data.input))
                    .save(output, MekUtConstants.rl("crafting/machine/" + data.name));
        });
        TWEAKED_MACHINES.forEach(data -> {
            ShapedRecipeBuilder
                    .shaped(RecipeCategory.REDSTONE, data.output)
                    .define('C', data.input)
                    .define('A', MekUtItems.ELASTIC_ALLOY)
                    .define('B', MekUtItems.DIGITAL_CONTROL_CIRCUIT)
                    .define('D', ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ingots/copper")))
                    .pattern("ABA")
                    .pattern("DCD")
                    .pattern("ABA")
                    .unlockedBy("unlock", has.apply(data.input))
                    .save(output, MekUtConstants.rl("crafting/machine/" + data.name));
        });
        STANDARD_MACHINES.forEach(data -> {
            ShapedRecipeBuilder
                    .shaped(RecipeCategory.REDSTONE, data.output)
                    .define('C', data.input)
                    .define('A', MekUtItems.CONVERGENT_ALLOY)
                    .define('D', ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ingots/refined_amethyst")))
                    .define('X', MekUtItems.ACCELERATION_CONTROL_CIRCUIT)
                    .define('B', MekUtItems.STANDARD_CONTROL_CIRCUIT)
                    .pattern("AXA")
                    .pattern("DCD")
                    .pattern("ABA")
                    .unlockedBy("unlock", has.apply(data.input))
                    .save(output, MekUtConstants.rl("crafting/machine/" + data.name));
        });
        STANDARD_GAS_MACHINES.forEach(data -> {
            ShapedRecipeBuilder
                    .shaped(RecipeCategory.REDSTONE, data.output)
                    .define('C', data.input)
                    .define('A', MekUtItems.CONVERGENT_ALLOY)
                    .define('D', ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ingots/refined_amethyst")))
                    .define('X', MekUtItems.ACCELERATION_CONTROL_CIRCUIT)
                    .define('B', MekUtItems.CHEMICAL_CONTROL_CIRCUIT)
                    .pattern("AXA")
                    .pattern("DCD")
                    .pattern("ABA")
                    .unlockedBy("unlock", has.apply(data.input))
                    .save(output, MekUtConstants.rl("crafting/machine/" + data.name));
        });
    }

    private static record SimpleMachineRecipeData(String name, ItemLike output, ItemLike input) {
        private SimpleMachineRecipeData(MachineRegistryObject<?, ?, ?, ?> output, ItemLike input) {
            this(output.getId().getPath(), output, input);
        }
    }

    static {
        NORMAL_MACHINES
                .add(new SimpleMachineRecipeData(MekUtMachines.SUBMATERIAL_CONVERTER, MekanismItems.ENRICHED_GOLD));
        TWEAKED_MACHINES.add(new SimpleMachineRecipeData(MekUtMachines.TWEAKED_ENERGIZED_SMELTER,
                MekanismBlocks.ENERGIZED_SMELTER));
        TWEAKED_MACHINES.add(new SimpleMachineRecipeData(MekUtMachines.MEKSTYLED_CHARGER, AEBlocks.CHARGER));
        STANDARD_GAS_MACHINES.add(new SimpleMachineRecipeData(MekUtMachines.STANDARD_CHEMICAL_INJECTION_CHAMBER,
                MekanismBlocks.CHEMICAL_INJECTION_CHAMBER));
        STANDARD_MACHINES.add(new SimpleMachineRecipeData(MekUtMachines.STANDARD_CRUSHER,
                MekanismBlocks.CRUSHER));
        STANDARD_MACHINES.add(new SimpleMachineRecipeData(MekUtMachines.STANDARD_ENERGIZED_SMELTER,
                MekUtMachines.TWEAKED_ENERGIZED_SMELTER));
        STANDARD_MACHINES.add(new SimpleMachineRecipeData(MekUtMachines.STANDARD_ENRICHMENR_CHAMBER,
                MekanismBlocks.ENRICHMENT_CHAMBER));
        STANDARD_MACHINES.add(new SimpleMachineRecipeData(MekUtMachines.STANDARD_MEKSTYLED_CHARGER,
                MekUtMachines.MEKSTYLED_CHARGER));
        STANDARD_GAS_MACHINES.add(new SimpleMachineRecipeData(MekUtMachines.STANDARD_PURIFICATION_CHAMBER,
                MekanismBlocks.PURIFICATION_CHAMBER));
    }
}
