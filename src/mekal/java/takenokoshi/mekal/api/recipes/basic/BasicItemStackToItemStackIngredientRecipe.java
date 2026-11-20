package takenokoshi.mekal.api.recipes.basic;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import mekanism.api.recipes.ItemStackToItemStackRecipe;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;

public abstract class BasicItemStackToItemStackIngredientRecipe extends ItemStackToItemStackRecipe {

    protected final ItemStackIngredient input, output;

    public BasicItemStackToItemStackIngredientRecipe(
            ItemStackIngredient input,
            ItemStackIngredient output,
            RecipeType<ItemStackToItemStackRecipe> recipeType) {
        super(recipeType);
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean test(ItemStack input) {
        return this.input.test(input);
    }

    @Override
    public ItemStackIngredient getInput() {
        return input;
    }

    @Override
    public ItemStack getOutput(ItemStack stack) {
        List<ItemStack> outputRepresentations = output.getRepresentations();
        return outputRepresentations.isEmpty() ? ItemStack.EMPTY : outputRepresentations.getFirst();
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull HolderLookup.Provider provider) {
        List<ItemStack> outputRepresentations = output.getRepresentations();
        return outputRepresentations.isEmpty() ? ItemStack.EMPTY : outputRepresentations.getFirst();
    }

    @Override
    public List<ItemStack> getOutputDefinition() {
        return output.getRepresentations();
    }

    public ItemStackIngredient getOutputRaw() {
        return output;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BasicItemStackToItemStackIngredientRecipe other = (BasicItemStackToItemStackIngredientRecipe) o;
        return input.equals(other.input) && output.equals(other.output);
    }

    @Override
    public boolean isIncomplete() {
        return super.isIncomplete() || output.hasNoMatchingInstances();
    }

    @Override
    public void logMissingTags() {
        super.logMissingTags();
        output.logMissingTags();
    }

}
