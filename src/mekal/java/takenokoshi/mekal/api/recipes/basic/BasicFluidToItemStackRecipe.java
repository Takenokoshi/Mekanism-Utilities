package takenokoshi.mekal.api.recipes.basic;

import java.util.Collections;
import java.util.List;

import mekanism.api.recipes.ingredients.FluidStackIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.fluids.FluidStack;
import takenokoshi.mekal.api.recipes.FluidToItemStackRecipe;

public abstract class BasicFluidToItemStackRecipe extends FluidToItemStackRecipe {

    private final RecipeType<FluidToItemStackRecipe> recipeType;
    protected final ItemStack output;
    private final FluidStackIngredient input;

    public BasicFluidToItemStackRecipe(RecipeType<FluidToItemStackRecipe> recipeType, ItemStack output,
            FluidStackIngredient input) {
        this.recipeType = recipeType;
        this.output = output;
        this.input = input;
    }

    @Override
    public final RecipeType<FluidToItemStackRecipe> getType() {
        return recipeType;
    }

    @Override
    public boolean test(FluidStack fluidStack) {
        return input.test(fluidStack);
    }

    @Override
    public FluidStackIngredient getInput() {
        return input;
    }

    @Override
    public List<ItemStack> getOutputDefinition() {
        return Collections.singletonList(output);
    }

    @Override
    public ItemStack getOutput(FluidStack input) {
        return output.copy();
    }

    public ItemStack getOutputRaw() {
        return output;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BasicFluidToItemStackRecipe other = (BasicFluidToItemStackRecipe) o;
        return output.equals(other.output) && input.equals(other.input);
    }

    @Override
    public int hashCode() {
        return ItemStack.hashItemAndComponents(output) * 961 + output.getCount() * 31 + input.hashCode();
    }
}
