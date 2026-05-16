package com.takenokoshi.mekut.recipe.building;

import java.util.function.Function;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.registries.MekUtItems;

import mekanism.common.registries.MekanismItems;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.level.ItemLike;

public class BuildCraftingRecipe {
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
    }
}
