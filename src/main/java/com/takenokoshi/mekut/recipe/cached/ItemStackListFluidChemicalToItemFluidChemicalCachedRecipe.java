package com.takenokoshi.mekut.recipe.cached;

import java.util.function.BooleanSupplier;

import com.takenokoshi.mekut.recipe.input.ItemStackListInputHandler;
import com.takenokoshi.mekut.recipe.recipe.prefab.ItemStackListFluidChemicalToItemFluidChemicalRecipe;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.outputs.IOutputHandler;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;

public class ItemStackListFluidChemicalToItemFluidChemicalCachedRecipe
        extends ItemStackListFluidChemicalToObjectsCachedRecipe<ItemStackListFluidChemicalToItemFluidChemicalRecipe> {

    private final IOutputHandler<ItemStack> itemOutputHandler;
    private final IOutputHandler<FluidStack> fluidOutputHandler;
    private final IOutputHandler<ChemicalStack> chemicalOutputHandler;

    public ItemStackListFluidChemicalToItemFluidChemicalCachedRecipe(
            ItemStackListFluidChemicalToItemFluidChemicalRecipe recipe, BooleanSupplier recheckAllErrors,
            ItemStackListInputHandler itemInputHandler, IInputHandler<FluidStack> fluidInputHandler,
            IInputHandler<ChemicalStack> chemicalInputHandler,
            IOutputHandler<ItemStack> itemOutputHandler,
            IOutputHandler<FluidStack> fluidOutputHandler,
            IOutputHandler<ChemicalStack> chemicalOutputHandler) {
        super(recipe, recheckAllErrors, itemInputHandler, fluidInputHandler, chemicalInputHandler);
        this.itemOutputHandler = itemOutputHandler;
        this.fluidOutputHandler = fluidOutputHandler;
        this.chemicalOutputHandler = chemicalOutputHandler;
    }

    @Override
    protected void calculateOutputOperationsThisTick(OperationTracker tracker) {
        itemOutputHandler.calculateOperationsCanSupport(tracker, recipe.outputItem);
        fluidOutputHandler.calculateOperationsCanSupport(tracker, recipe.outputFluid);
        chemicalOutputHandler.calculateOperationsCanSupport(tracker, recipe.outputChemical);
    }

    @Override
    protected void handleOutputs(int operations) {
        itemOutputHandler.handleOutput(recipe.outputItem, operations);
        fluidOutputHandler.handleOutput(recipe.outputFluid, operations);
        chemicalOutputHandler.handleOutput(recipe.outputChemical, operations);
    }

}