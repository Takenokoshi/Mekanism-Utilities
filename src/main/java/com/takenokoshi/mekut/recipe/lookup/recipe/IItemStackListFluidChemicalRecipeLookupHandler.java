package com.takenokoshi.mekut.recipe.lookup.recipe;

import java.util.List;

import com.takenokoshi.mekaddonlib.recipe.lookup.IMekALRecipeTypedLookupHandler;
import com.takenokoshi.mekut.recipe.input.ItemStackListInputHandler;
import com.takenokoshi.mekut.recipe.inputcache.ItemStackListFluidChemicalInputRecipeCache;
import com.takenokoshi.mekut.recipe.recipe.prefab.ItemStackListFluidChemicalToObjectsRecipe;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.inputs.IInputHandler;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;

public interface IItemStackListFluidChemicalRecipeLookupHandler<RECIPE extends ItemStackListFluidChemicalToObjectsRecipe>
        extends IMekALRecipeTypedLookupHandler<RECIPE, ItemStackListFluidChemicalInputRecipeCache<RECIPE>> {
    default boolean containsRecipeItem(ItemStack stack, int slotIndex) {
        return getRecipeType().getInputCache().containsItem(getLevel(), stack, slotIndex);
    }

    default boolean containsRecipeFluid(FluidStack stack) {
        return getRecipeType().getInputCache().containsFluid(getLevel(), stack);
    }

    default boolean containsRecipeChemical(ChemicalStack stack) {
        return getRecipeType().getInputCache().containsChemical(getLevel(), stack);
    }

    default boolean containsRecipeItemOther(ItemStack itemInput, int slotIndex, List<ItemStack> otherItemInputs,
            FluidStack fluidInput, ChemicalStack chemicalInput) {
        return getRecipeType().getInputCache().containsItemOther(getLevel(), itemInput, slotIndex,
                otherItemInputs, fluidInput, chemicalInput);
    }

    default boolean containsRecipeFluidOther(List<ItemStack> itemInputs, FluidStack fluidInput,
            ChemicalStack chemicalInput) {
        return getRecipeType().getInputCache().containsFluidOther(getLevel(), itemInputs, fluidInput,
                chemicalInput);
    }

    default boolean containsRecipeChemicalOther(List<ItemStack> itemInputs, FluidStack fluidInput,
            ChemicalStack chemicalInput) {
        return getRecipeType().getInputCache().containsChemicalOther(getLevel(), itemInputs, fluidInput,
                chemicalInput);
    }

    default RECIPE findFirstRecipe(List<ItemStack> itemInputs, FluidStack fluidInput, ChemicalStack chemicalInput) {
        return getRecipeType().getInputCache().findFirstRecipe(getLevel(), itemInputs, fluidInput,
                chemicalInput);
    }

    default RECIPE findFirstRecipe(ItemStackListInputHandler itemInputHandler,
            IInputHandler<FluidStack> fluidInputHandler, IInputHandler<ChemicalStack> chemicalInputHandler) {
        return findFirstRecipe(itemInputHandler.getInput(), fluidInputHandler.getInput(),
                chemicalInputHandler.getInput());
    }
}
