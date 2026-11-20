package takenokoshi.mekal.api.recipes.basic;

import org.jetbrains.annotations.NotNull;

import mekanism.api.recipes.basic.IBasicItemStackOutput;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.fluids.FluidStack;
import takenokoshi.mekal.api.recipes.ItemStackFluidToItemStackRecipe;

public abstract class BasicItemStackFluidToItemStackRecipe extends ItemStackFluidToItemStackRecipe implements IBasicItemStackOutput {

    private final RecipeType<? extends ItemStackFluidToItemStackRecipe> recipeType;
    private final ItemStackIngredient itemInput;
    private final FluidStackIngredient fluidInput;
    protected final ItemStack output;
    private final boolean perTickUsage;

    public BasicItemStackFluidToItemStackRecipe(RecipeType<? extends ItemStackFluidToItemStackRecipe> recipeType, ItemStackIngredient itemInput, FluidStackIngredient fluidInput, ItemStack output, boolean perTickUsage) {
        this.recipeType = recipeType;
        this.itemInput = itemInput;
        this.fluidInput = fluidInput;
        this.output = output;
        this.perTickUsage = perTickUsage;
    }

    @Override
    public RecipeType<? extends ItemStackFluidToItemStackRecipe> getType() {
        return recipeType;
    }

    @Override
    public ItemStackIngredient getItemInput() {
        return itemInput;
    }

    @Override
    public FluidStackIngredient getFluidInput() {
        return fluidInput;
    }

    @Override
    public ItemStack getOutput(ItemStack inputItem, FluidStack inputFluid) {
        return output.copy();
    }

    @Override
    public final boolean perTickUsage() {
        return perTickUsage;
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public boolean test(ItemStack itemStack, FluidStack fluidStack) {
        return itemInput.test(itemStack) && fluidInput.test(fluidStack);
    }

    @Override
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
        BasicItemStackFluidToItemStackRecipe other = (BasicItemStackFluidToItemStackRecipe) o;
        //Note: We don't need to compare the recipe type as that gets covered by the explicit class type check above
        return perTickUsage == other.perTickUsage && itemInput.equals(other.itemInput) && fluidInput.equals(other.fluidInput) && ItemStack.matches(output, other.output);
    }
}
