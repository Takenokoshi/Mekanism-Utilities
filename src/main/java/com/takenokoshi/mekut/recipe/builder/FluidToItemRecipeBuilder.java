package com.takenokoshi.mekut.recipe.builder;

import java.util.function.BiFunction;

import com.takenokoshi.mekut.recipe.recipe.basic.BasicIceMakingRecipe;
import com.takenokoshi.mekut.recipe.recipe.prefab.FluidToItemRecipe;

import mekanism.api.datagen.recipe.MekanismRecipeBuilder;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public class FluidToItemRecipeBuilder extends MekanismRecipeBuilder<FluidToItemRecipeBuilder> {

    protected final BiFunction<FluidStackIngredient, ItemStack, FluidToItemRecipe> factory;
    protected final FluidStackIngredient input;
    protected final ItemStack output;

    protected FluidToItemRecipeBuilder(BiFunction<FluidStackIngredient, ItemStack, FluidToItemRecipe> factory,
            FluidStackIngredient input, ItemStack output) {
        this.factory = factory;
        this.input = input;
        this.output = output;

    }

    public static FluidToItemRecipeBuilder iceMaking(FluidStackIngredient input, ItemStack output) {
        return new FluidToItemRecipeBuilder(BasicIceMakingRecipe::new, input, output);
    }

    @Override
    protected Recipe<?> asRecipe() {
        return factory.apply(input, output);
    }

}
