package com.takenokoshi.mekut.recipe.lookup.recipe;

import java.util.List;

import com.takenokoshi.mekut.recipe.input.ItemStackListInputHandler;
import com.takenokoshi.mekut.recipe.inputcache.ItemStackListFluidInputRecipeCache;
import com.takenokoshi.mekut.recipe.lookup.IMekUtRecipeTypedLookupHandler;

import mekanism.api.recipes.inputs.IInputHandler;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.fluids.FluidStack;

public interface IItemStackListFluidRecipeLookupHandler<RECIPE extends Recipe<?>>
        extends IMekUtRecipeTypedLookupHandler<RECIPE, ItemStackListFluidInputRecipeCache<RECIPE>> {

    default boolean containsRecipeItem(ItemStack stack, int slotIndex) {
        return getRecipeType().getInputCache().containsItem(getHandlerWorld(), stack, slotIndex);
    }

    default boolean containsRecipeFluid(FluidStack stack) {
        return getRecipeType().getInputCache().containsFluid(getHandlerWorld(), stack);
    }

    default boolean containsRecipeItemOther(ItemStack itemInput, int slotIndex, List<ItemStack> otherItemInputs,
            FluidStack fluidInput) {
        return getRecipeType().getInputCache().containsItemOther(getHandlerWorld(), itemInput, slotIndex,
                otherItemInputs, fluidInput);
    }

    default boolean containsRecipeFluidOther(List<ItemStack> itemInputs, FluidStack fluidInput) {
        return getRecipeType().getInputCache().containsFluidOther(getHandlerWorld(), itemInputs, fluidInput);
    }

    default RECIPE findFirstRecipe(List<ItemStack> itemInputs, FluidStack fluidInput) {
        return getRecipeType().getInputCache().findFirstRecipe(getHandlerWorld(), itemInputs, fluidInput);
    }

    default RECIPE findFirstRecipe(ItemStackListInputHandler itemInputHandler,
            IInputHandler<FluidStack> fluidInputHandler) {
        return findFirstRecipe(itemInputHandler.getInput(), fluidInputHandler.getInput());
    }
}
