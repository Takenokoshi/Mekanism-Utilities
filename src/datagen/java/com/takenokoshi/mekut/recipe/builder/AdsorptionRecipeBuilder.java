package com.takenokoshi.mekut.recipe.builder;

import com.fxd927.mekanismelements.api.recipes.AdsorptionRecipe;
import com.fxd927.mekanismelements.common.recipe.impl.AdsorptionIRecipe;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.datagen.recipe.MekanismRecipeBuilder;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;

public class AdsorptionRecipeBuilder extends MekanismRecipeBuilder<AdsorptionRecipeBuilder> {

    protected final Factory factory;
    protected final ItemStackIngredient item;
    protected final FluidStackIngredient fluid;
    protected final ChemicalStack output;
    protected final ResourceLocation id;

    protected AdsorptionRecipeBuilder(Factory factory, ItemStackIngredient item, FluidStackIngredient fluid,
            ChemicalStack output, ResourceLocation id) {
        this.factory = factory;
        this.item = item;
        this.fluid = fluid;
        this.output = output;
        this.id = id;
    }

    @Override
    protected AdsorptionRecipe asRecipe() {
        AdsorptionRecipe recipe = factory.create(item, fluid, output);
        recipe.setId(id);
        return recipe;
    }

    public void build(RecipeOutput recipeOutput) {
        build(recipeOutput, this.id);
    }

    @FunctionalInterface
    protected static interface Factory {
        AdsorptionRecipe create(ItemStackIngredient item, FluidStackIngredient fluid, ChemicalStack output);
    }

    public static AdsorptionRecipeBuilder adsorption(ItemStackIngredient item, FluidStackIngredient fluid,
            ChemicalStack output, ResourceLocation id) {
        return new AdsorptionRecipeBuilder(AdsorptionIRecipe::new, item, fluid, output, id);
    }

}
