package com.takenokoshi.mekut.recipe.building;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.registration.MachineRegistryObject;
import com.takenokoshi.mekut.registries.MekUtItems;
import com.takenokoshi.mekut.registries.MekUtMachines;

import appeng.core.definitions.AEBlocks;
import mekanism.common.registries.MekanismBlocks;
import mekanism.common.registries.MekanismItems;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.ItemLike;

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
