package com.takenokoshi.mekut.recipe.building;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.registration.MachineRegistryObject;
import com.takenokoshi.mekut.registries.MekUtItems;
import com.takenokoshi.mekut.registries.MekUtMachines;

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

    private static final List<TweakedMachineRecipeData> TWEAKED_MACHINES = new ArrayList<>();

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
        TWEAKED_MACHINES.forEach(data -> {
            ShapedRecipeBuilder
                    .shaped(RecipeCategory.REDSTONE, data.output)
                    .define('C', data.centerInput)
                    .define('A', MekUtItems.ELASTIC_ALLOY)
                    .define('B', MekUtItems.DIGITAL_CONTROL_CIRCUIT)
                    .define('D', ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ingots/copper")))
                    .pattern("ABA")
                    .pattern("DCD")
                    .pattern("ABA")
                    .unlockedBy("unlock", has.apply(data.centerInput))
                    .save(output, MekUtConstants.rl("crafting/machine/" + data.name));
        });
    }

    private static record TweakedMachineRecipeData(String name, ItemLike output, ItemLike centerInput) {
        private TweakedMachineRecipeData(MachineRegistryObject<?, ?, ?, ?> output, ItemLike centerInput) {
            this(output.getBlockObject().getId().getPath(), output.getBlockObject(), centerInput);
        }
    }

    static {
        TWEAKED_MACHINES.add(new TweakedMachineRecipeData(MekUtMachines.TWEAKED_ENERGIZED_SMELTER,
                MekanismBlocks.ENERGIZED_SMELTER));
    }
}
