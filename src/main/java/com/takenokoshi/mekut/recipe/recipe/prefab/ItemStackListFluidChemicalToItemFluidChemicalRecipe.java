package com.takenokoshi.mekut.recipe.recipe.prefab;

import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.fluids.FluidStack;

public abstract class ItemStackListFluidChemicalToItemFluidChemicalRecipe
        extends ItemStackListFluidChemicalToObjectsRecipe {

    @NotNull
    public final ItemStack outputItem;
    @NotNull
    public final FluidStack outputFluid;
    @NotNull
    public final ChemicalStack outputChemical;

    public final long energyRequired;

    public final int duration;

    public ItemStackListFluidChemicalToItemFluidChemicalRecipe(
            RecipeType<ItemStackListFluidChemicalToItemFluidChemicalRecipe> recipeType,
            List<ItemStackIngredient> itemInputs,
            FluidStackIngredient fluidInput,
            ChemicalStackIngredient chemicalInput,
            ItemStack outputItem,
            FluidStack outputFluid,
            ChemicalStack outputChemical,
            long energyRequired,
            int duration) {
        super(recipeType, itemInputs, fluidInput, chemicalInput);
        this.outputItem = outputItem;
        this.outputFluid = outputFluid;
        this.outputChemical = outputChemical;
        this.energyRequired = energyRequired;
        this.duration = duration;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof final ItemStackListFluidChemicalToItemFluidChemicalRecipe recipe) {

            if (!this.recipeType.equals(recipe.recipeType)) {
                return false;
            }
            if (!this.outputItem.equals(recipe.outputItem)) {
                return false;
            }
            if (!this.outputFluid.equals(recipe.outputFluid)) {
                return false;
            }
            if (!this.outputChemical.equals(recipe.outputChemical)) {
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

    public ItemStack getItemOutput() {
        return outputItem;
    }

    public List<ItemStack> getItemOutputDefinition() {
        return List.of(outputItem);
    }

    public FluidStack getFluidOutput() {
        return outputFluid;
    }

    public List<FluidStack> getFluidOutoutDefinition() {
        return List.of(outputFluid);
    }

    public ChemicalStack getChemicalOutput() {
        return outputChemical;
    }

    public List<ChemicalStack> getChemicalOutputDefinition() {
        return List.of(outputChemical);
    }

    public long getEnergyRequired() {
        return energyRequired;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                recipeType,
                itemInputs,
                fluidInput,
                chemicalInput,
                outputItem,
                outputFluid,
                outputChemical,
                energyRequired,
                duration);
    }

}
