package com.takenokoshi.mekut.recipe.cached;

import java.util.function.BooleanSupplier;
import java.util.function.LongUnaryOperator;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ItemStackChemicalToItemStackRecipe;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.outputs.IOutputHandler;
import net.minecraft.world.item.ItemStack;

public class MekUtItemChemicalToChemicalCachedRecipe extends AbstractCachedRecipe<ItemStackChemicalToItemStackRecipe> {

    private final IInputHandler<ItemStack> itemInputHandler;
    private final IInputHandler<ChemicalStack> chemicalInputHandler;
    private final IOutputHandler<ItemStack> outputHandler;
    private final LongUnaryOperator chemicalUsageModifier;

    private long perTickUsage;
    private long lastTickUsage;
    private ItemStack inputItem = ItemStack.EMPTY;
    private ChemicalStack inputChemical = ChemicalStack.EMPTY;
    private ItemStack output = ItemStack.EMPTY;

    public MekUtItemChemicalToChemicalCachedRecipe(ItemStackChemicalToItemStackRecipe recipe,
            BooleanSupplier recheckAllErrors,
            IInputHandler<ItemStack> itemInputHandler,
            IInputHandler<ChemicalStack> chemicalInputHandler,
            IOutputHandler<ItemStack> outputHandler, LongUnaryOperator chemicalUsageModifier) {
        super(recipe, recheckAllErrors);
        this.itemInputHandler = itemInputHandler;
        this.chemicalInputHandler = chemicalInputHandler;
        this.outputHandler = outputHandler;
        this.chemicalUsageModifier = chemicalUsageModifier;
    }

    @Override
    protected void calculateOperationsThisTick(OperationTracker tracker) {
        super.calculateOperationsThisTick(tracker);
        if (!tracker.shouldContinueChecking()) {
            return;
        }
        inputChemical = chemicalInputHandler.getRecipeInput(recipe.getChemicalInput());
        inputItem = itemInputHandler.getRecipeInput(recipe.getItemInput());
        if (inputChemical.isEmpty() || inputItem.isEmpty()) {
            tracker.mismatchedRecipe();
            return;
        }
        if (recipe.perTickUsage()) {
            int ticksRequired = requiredTicks.getAsInt();
            long totalUsage = chemicalUsageModifier.applyAsLong(recipe.getChemicalInput().amount() * 200);
            perTickUsage = totalUsage / ticksRequired;
            lastTickUsage = perTickUsage + totalUsage % ticksRequired;
        } else {
            perTickUsage = 0;
            lastTickUsage = recipe.getChemicalInput().amount();
        }
        chemicalInputHandler.calculateOperationsCanSupport(tracker, inputChemical.copyWithAmount(lastTickUsage));
        itemInputHandler.calculateOperationsCanSupport(tracker, inputItem);
        output = recipe.getOutput(inputItem, inputChemical);
        outputHandler.calculateOperationsCanSupport(tracker, output);
    }

    @Override
    public boolean isInputValid() {
        return recipe.test(itemInputHandler.getInput(), chemicalInputHandler.getInput());
    }

    @Override
    protected void useResources(int operations) {
        chemicalInputHandler.use(inputChemical.copyWithAmount(perTickUsage), operations);
    }

    @Override
    protected void finishProcessing(int operations) {
        itemInputHandler.use(inputItem, operations);
        chemicalInputHandler.use(inputChemical.copyWithAmount(lastTickUsage), operations);
        outputHandler.handleOutput(output, operations);
    }

}
