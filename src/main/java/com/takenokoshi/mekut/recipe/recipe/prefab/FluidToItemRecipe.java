package com.takenokoshi.mekut.recipe.recipe.prefab;

import java.util.List;
import java.util.function.Predicate;

import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.vanilla_input.SingleFluidRecipeInput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;

public abstract class FluidToItemRecipe extends MekanismRecipe<SingleFluidRecipeInput>
        implements Predicate<FluidStack> {

    protected final RecipeType<FluidToItemRecipe> recipeType;
    public final FluidStackIngredient input;
    public final ItemStack output;

    protected FluidToItemRecipe(RecipeType<FluidToItemRecipe> recipeType, FluidStackIngredient input,
            ItemStack output) {
        this.recipeType = recipeType;
        this.input = input;
        this.output = output;
    }

    @Override
    public RecipeType<?> getType() {
        return recipeType;
    }

    public FluidStackIngredient getInput() {
        return input;
    }

    public List<ItemStack> getOutputDefinition() {
        return List.of(output);
    }

    public ItemStack getOutputRaw() {
        return output;
    }

    @Override
    public boolean test(FluidStack t) {
        return input.test(t);
    }

    @Override
    public boolean isIncomplete() {
        return input.hasNoMatchingInstances();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FluidToItemRecipe other = (FluidToItemRecipe) o;
        return input.equals(other.input) && output.equals(other.output);
    }

    @Override
    public int hashCode() {
        int result = input.hashCode();
        result = 31 * result + output.hashCode();
        return result;
    }

    @Override
    public boolean matches(SingleFluidRecipeInput input, Level world) {
        return !isIncomplete() && test(input.fluid());
    }

    @Override
    public void logMissingTags() {
        input.logMissingTags();
    }
}
