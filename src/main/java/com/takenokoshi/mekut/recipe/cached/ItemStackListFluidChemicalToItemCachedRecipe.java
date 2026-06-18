package com.takenokoshi.mekut.recipe.cached;

import java.util.function.BooleanSupplier;

import com.takenokoshi.mekut.recipe.input.ItemStackListInputHandler;
import com.takenokoshi.mekut.recipe.recipe.prefab.ItemStackListFluidChemicalToItemRecipe;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.outputs.IOutputHandler;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;

public class ItemStackListFluidChemicalToItemCachedRecipe
        extends ItemStackListFluidChemicalToObjectsCachedRecipe<ItemStackListFluidChemicalToItemRecipe> {

    private final IOutputHandler<ItemStack> outputHandler;

    public ItemStackListFluidChemicalToItemCachedRecipe(ItemStackListFluidChemicalToItemRecipe recipe,
            BooleanSupplier recheckAllErrors, ItemStackListInputHandler itemInputHandler,
            IInputHandler<FluidStack> fluidInputHandler, IInputHandler<ChemicalStack> chemicalInputHandler,
            IOutputHandler<ItemStack> outputHandler) {
        super(recipe, recheckAllErrors, itemInputHandler, fluidInputHandler, chemicalInputHandler);
        this.outputHandler = outputHandler;
    }

    @Override
    protected void calculateOutputOperationsThisTick(OperationTracker tracker) {
        outputHandler.calculateOperationsCanSupport(tracker, recipe.outputItem);
    }

    @Override
    protected void handleOutputs(int operations) {
        outputHandler.handleOutput(recipe.outputItem, operations);
    }

}
