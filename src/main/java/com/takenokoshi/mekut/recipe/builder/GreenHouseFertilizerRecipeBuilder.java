package com.takenokoshi.mekut.recipe.builder;

import com.mojang.datafixers.util.Function3;
import com.takenokoshi.mekut.recipe.recipe.basic.BasicGreenHouseFertilizerRecipe;

import mekanism.api.datagen.recipe.MekanismRecipeBuilder;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import net.minecraft.world.item.crafting.Recipe;

public class GreenHouseFertilizerRecipeBuilder extends MekanismRecipeBuilder<GreenHouseFertilizerRecipeBuilder> {

    public final FluidStackIngredient fertilizerIngredient;
    public final int outputMultiplier;
    public final double durationMultiplier;
    protected final Function3<FluidStackIngredient, Integer, Double, Recipe<?>> factory;

    protected GreenHouseFertilizerRecipeBuilder(
            FluidStackIngredient fertilizerIngredient, int outputMultiplier, double durationMultiplier,
            Function3<FluidStackIngredient, Integer, Double, Recipe<?>> factory) {
        this.fertilizerIngredient = fertilizerIngredient;
        this.outputMultiplier = outputMultiplier;
        this.durationMultiplier = durationMultiplier;
        this.factory = factory;

    }

    public static GreenHouseFertilizerRecipeBuilder greenHouseFertilizer(FluidStackIngredient fertilizerIngredient,
            int outputMultiplier, double durationMultiplier) {
        return new GreenHouseFertilizerRecipeBuilder(fertilizerIngredient, outputMultiplier, durationMultiplier,
                BasicGreenHouseFertilizerRecipe::new);
    }

    @Override
    protected Recipe<?> asRecipe() {
        return factory.apply(fertilizerIngredient, outputMultiplier, durationMultiplier);
    }

}
