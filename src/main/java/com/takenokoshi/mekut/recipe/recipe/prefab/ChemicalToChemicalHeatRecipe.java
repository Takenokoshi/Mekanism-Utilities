package com.takenokoshi.mekut.recipe.recipe.prefab;

import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.Contract;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ChemicalToChemicalRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import net.minecraft.world.item.crafting.RecipeType;

public abstract class ChemicalToChemicalHeatRecipe extends ChemicalToChemicalRecipe {

    private final RecipeType<ChemicalToChemicalHeatRecipe> recipeType;
    protected final ChemicalStack output;
    private final ChemicalStackIngredient input;
    public final double heatGeneration;

    protected ChemicalToChemicalHeatRecipe(ChemicalStackIngredient input, ChemicalStack output, double heatGeneration,
            RecipeType<ChemicalToChemicalHeatRecipe> recipeType) {
        this.recipeType = recipeType;
        this.output = output;
        this.input = input;
        this.heatGeneration = heatGeneration;
    }

    public final RecipeType<ChemicalToChemicalHeatRecipe> getType() {
        return this.recipeType;
    }

    public boolean test(ChemicalStack chemicalStack) {
        return this.input.test(chemicalStack);
    }

    public ChemicalStackIngredient getInput() {
        return this.input;
    }

    public List<ChemicalStack> getOutputDefinition() {
        return Collections.singletonList(this.output);
    }

    @Contract(value = "_ -> new", pure = true)
    public ChemicalStack getOutput(ChemicalStack input) {
        return this.output.copy();
    }

    public ChemicalStack getOutputRaw() {
        return this.output;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            ChemicalToChemicalHeatRecipe other = (ChemicalToChemicalHeatRecipe) o;
            return this.output.equals(other.output)
                    && this.input.equals(other.input)
                    && this.heatGeneration == other.heatGeneration;
        } else {
            return false;
        }
    }

    public int hashCode() {
        int result = this.output.hashCode();
        result = 31 * result + this.input.hashCode();
        result = 31 * result + Double.hashCode(this.heatGeneration);
        return result;
    }
}
