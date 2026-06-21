package com.takenokoshi.mekut.recipe.recipe.prefab;

import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.vanilla_input.BiChemicalRecipeInput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public abstract class BiChemicalToItemRecipe extends MekanismRecipe<BiChemicalRecipeInput>
        implements BiPredicate<ChemicalStack, ChemicalStack> {

    protected final RecipeType<BiChemicalToItemRecipe> recipeType;
    protected final ChemicalStackIngredient leftInput;
    protected final ChemicalStackIngredient rightInput;
    protected final ItemStack output;

    protected BiChemicalToItemRecipe(RecipeType<BiChemicalToItemRecipe> recipeType,
            ChemicalStackIngredient leftInput, ChemicalStackIngredient rightInput, ItemStack output) {
        this.recipeType = recipeType;
        this.leftInput = leftInput;
        this.rightInput = rightInput;
        this.output = output;
    }

    @Override
    public final RecipeType<?> getType() {
        return recipeType;
    }

    @Override
    public boolean test(ChemicalStack input1, ChemicalStack input2) {
        return (leftInput.test(input1) && rightInput.test(input2))
                || (rightInput.test(input1) && leftInput.test(input2));
    }

    public ItemStack getOutput(ChemicalStack input1, ChemicalStack input2) {
        return output.copy();
    }

    public ItemStack getOutputRaw() {
        return output;
    }

    public List<ItemStack> getOutputDefinition() {
        return Collections.singletonList(output);
    }

    public ChemicalStackIngredient getLeftInput() {
        return leftInput;
    }

    public ChemicalStackIngredient getRightInput() {
        return rightInput;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BiChemicalToItemRecipe other = (BiChemicalToItemRecipe) o;
        // Note: We don't need to compare the recipe type as that gets covered by the
        // explicit class type check above
        return leftInput.equals(other.leftInput) && rightInput.equals(other.rightInput) && output.equals(other.output);
    }

    @Override
    public int hashCode() {
        int result = leftInput.hashCode();
        result = 31 * result + rightInput.hashCode();
        result = 31 * result + output.hashCode();
        return result;
    }

    @Override
    public boolean isIncomplete() {
        return getLeftInput().hasNoMatchingInstances() || getRightInput().hasNoMatchingInstances();
    }

    @Override
    public void logMissingTags() {
        getLeftInput().logMissingTags();
        getRightInput().logMissingTags();
    }

    @Override
    public boolean matches(BiChemicalRecipeInput input, Level level) {
        // Don't match incomplete recipes or ones that don't match
        return !isIncomplete() && test(input.left(), input.right());
    }

}
