package com.takenokoshi.mekut.recipe.builder;

import java.util.ArrayList;
import java.util.List;

import com.mojang.datafixers.util.Function4;
import com.takenokoshi.mekut.recipe.output.MekUtChanceOutput;
import com.takenokoshi.mekut.recipe.recipe.basic.BasicGreenHouseCropRecipe;

import mekanism.api.datagen.recipe.MekanismRecipeBuilder;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.world.item.crafting.Recipe;

public class GreenHouseCropRecipeBuilder extends MekanismRecipeBuilder<GreenHouseCropRecipeBuilder> {

    protected final ItemStackIngredient cropIngredient;
    protected final ItemStackIngredient soilIngredient;
    protected final List<MekUtChanceOutput> outputs = new ArrayList<>();
    protected final int duration;

    protected final Function4<ItemStackIngredient, ItemStackIngredient, List<MekUtChanceOutput>, Integer, Recipe<?>> factory;

    protected GreenHouseCropRecipeBuilder(ItemStackIngredient cropIngredient, ItemStackIngredient soilIngredient,
            int duration,
            Function4<ItemStackIngredient, ItemStackIngredient, List<MekUtChanceOutput>, Integer, Recipe<?>> factory) {
        this.cropIngredient = cropIngredient;
        this.soilIngredient = soilIngredient;
        this.duration = duration;
        this.factory = factory;
    }

    public static GreenHouseCropRecipeBuilder greenHouseCrop(ItemStackIngredient cropIngredient,
            ItemStackIngredient soilIngredient,
            int duration) {
        return new GreenHouseCropRecipeBuilder(cropIngredient, soilIngredient, duration,
                BasicGreenHouseCropRecipe::new);
    }

    public GreenHouseCropRecipeBuilder addOutput(MekUtChanceOutput output) {
        outputs.add(output);
        return this;
    }

    @Override
    protected Recipe<?> asRecipe() {
        return factory.apply(cropIngredient, soilIngredient, List.copyOf(outputs), duration);
    }

}
