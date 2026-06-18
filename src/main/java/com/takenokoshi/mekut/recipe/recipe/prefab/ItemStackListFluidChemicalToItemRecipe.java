package com.takenokoshi.mekut.recipe.recipe.prefab;

import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;

public abstract class ItemStackListFluidChemicalToItemRecipe extends ItemStackListFluidChemicalToObjectsRecipe {

    @NotNull
    public final ItemStack outputItem;

    public final long energyRequired;

    public ItemStackListFluidChemicalToItemRecipe(
            RecipeType<? extends ItemStackListFluidChemicalToItemRecipe> recipeType,
            List<ItemStackIngredient> itemInputs, FluidStackIngredient fluidInput,
            ChemicalStackIngredient chemicalInput, ItemStack outputItem, long energyRequired) {
        super(recipeType, itemInputs, fluidInput, chemicalInput);
        this.outputItem = outputItem;
        this.energyRequired = energyRequired;
    }

    public List<ItemStack> getOutputDefinition() {
        return List.of(outputItem);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof final ItemStackListFluidChemicalToItemRecipe recipe) {
            if (!this.recipeType.equals(recipe.recipeType)) {
                return false;
            }
            if (!this.outputItem.equals(recipe.outputItem)) {
                return false;
            }
            if (!(this.chemicalInput == null
                    ? recipe.chemicalInput == null
                    : this.chemicalInput.equals(recipe.chemicalInput))) {
                return false;
            }
            if (!(this.fluidInput == null
                    ? recipe.fluidInput == null
                    : this.fluidInput.equals(recipe.fluidInput))) {
                return false;
            }
            if (!(this.itemInputs.size() == recipe.itemInputs.size())) {
                return false;
            }
            for (int i = 0; i < this.itemInputs.size(); i++) {
                if (!this.itemInputs.get(i).equals(recipe.itemInputs.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public ItemStack getOutputItem() {
        return outputItem;
    }

    public long getEnergyRequired() {
        return energyRequired;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                recipeType,
                itemInputs,
                fluidInput,
                chemicalInput,
                outputItem,
                energyRequired);
    }

}
