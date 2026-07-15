package com.takenokoshi.mekut.recipe.cached;

import java.util.function.BooleanSupplier;

import com.takenokoshi.mekaddonlib.recipe.cached.ICachedRecipe;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ItemStackChemicalToItemStackRecipe;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.outputs.IOutputHandler;
import net.minecraft.world.item.ItemStack;

public class TweakedItemChemicalToItemCachedRecipe extends CachedRecipe<ItemStackChemicalToItemStackRecipe>
        implements ICachedRecipe<ItemStackChemicalToItemStackRecipe> {

    private final IInputHandler<ItemStack> itemInputHandler;
    private final IInputHandler<ChemicalStack> chamicalInputHandler;
    private final IOutputHandler<ItemStack> outputHandler;

    private final ChemicalStackIngredient tweakedChemical;

    private ItemStack recipeInput = ItemStack.EMPTY;
    private ChemicalStack chemicalInput = ChemicalStack.EMPTY;
    private ItemStack recipeOutput = ItemStack.EMPTY;

    public TweakedItemChemicalToItemCachedRecipe(ItemStackChemicalToItemStackRecipe recipe,
            BooleanSupplier recheckAllErrors, IInputHandler<ItemStack> itemInputHandler,
            IInputHandler<ChemicalStack> chamicalInputHandler, IOutputHandler<ItemStack> outputHandler,
            ChemicalUsageModifier usageModifier) {
        super(recipe, recheckAllErrors);
        this.itemInputHandler = itemInputHandler;
        this.chamicalInputHandler = chamicalInputHandler;
        this.outputHandler = outputHandler;
        this.tweakedChemical = recipe.perTickUsage()
                ? IngredientCreatorAccess.chemicalStack().from(recipe.getChemicalInput().ingredient(),
                        usageModifier.modify(recipe.getChemicalInput().amount()))
                : recipe.getChemicalInput();
    }

    protected void calculateOperationsThisTick(OperationTracker tracker) {
        if (!tracker.shouldContinueChecking()) {
            return;
        }
        recipeInput = itemInputHandler.getRecipeInput(recipe.getItemInput());
        chemicalInput = chamicalInputHandler.getRecipeInput(tweakedChemical);
        if (recipeInput.isEmpty() || chemicalInput.isEmpty()) {
            tracker.resetProgress(RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT);
            return;
        }
        recipeOutput = recipe.getOutput(recipeInput, chemicalInput);
        itemInputHandler.calculateOperationsCanSupport(tracker, recipeInput);
        chamicalInputHandler.calculateOperationsCanSupport(tracker, chemicalInput);
        outputHandler.calculateOperationsCanSupport(tracker, recipeOutput);
    }

    @Override
    protected void finishProcessing(int operations) {
        itemInputHandler.use(recipeInput, operations);
        chamicalInputHandler.use(chemicalInput, operations);
        outputHandler.handleOutput(recipeOutput, operations);
    }

    @Override
    public boolean isInputValid() {
        return recipe.test(itemInputHandler.getInput(), chamicalInputHandler.getInput());
    }

    @FunctionalInterface
    public static interface ChemicalUsageModifier {
        long modify(long originalPerTick);
    }

}
