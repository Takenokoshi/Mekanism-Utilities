package com.takenokoshi.mekut.recipe.cached;

import java.util.List;
import java.util.function.BooleanSupplier;

import com.takenokoshi.mekaddonlib.recipe.cached.AbstractCachedRecipe;
import com.takenokoshi.mekut.recipe.input.ItemStackListInputHandler;
import com.takenokoshi.mekut.recipe.output.ItemOutputHandler;

import mekanism.api.recipes.cache.CachedRecipe.OperationTracker;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.api.recipes.ingredients.creator.IItemStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.outputs.IOutputHandler;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.pedroksl.advanced_ae.recipes.ReactionChamberRecipe;

public class MekStyledReactionChamberCachedRecipe extends AbstractCachedRecipe<ReactionChamberRecipe> {

    private final ItemStackListInputHandler itemInputHandler;
    private final IInputHandler<FluidStack> fluidInputHandler;
    private final ItemOutputHandler itemOutputHandler;
    private final IOutputHandler<FluidStack> fluidOutputHandler;

    private final List<ItemStackIngredient> itemIngredients;
    private final FluidStackIngredient fluidIngredient;

    private List<ItemStack> recipeInputItems = List.of();
    private int[] slotIndexCache = new int[0];
    private FluidStack recipeInputFluid = FluidStack.EMPTY;

    private final ItemStack outputItem;
    private final FluidStack outputFluid;

    public MekStyledReactionChamberCachedRecipe(ReactionChamberRecipe recipe,
            BooleanSupplier recheckAllErrors,
            ItemStackListInputHandler itemInputHandler,
            IInputHandler<FluidStack> fluidInputHandler,
            ItemOutputHandler itemOutputHandler,
            IOutputHandler<FluidStack> fluidOutputHandler) {
        super(recipe, recheckAllErrors);
        this.itemInputHandler = itemInputHandler;
        this.fluidInputHandler = fluidInputHandler;
        this.itemOutputHandler = itemOutputHandler;
        this.fluidOutputHandler = fluidOutputHandler;
        IItemStackIngredientCreator creatorI = IngredientCreatorAccess.item();
        this.itemIngredients = recipe.getInputs().stream()
                .map(v -> creatorI.from(v.getIngredient(), v.getAmount()))
                .toList();
        this.fluidIngredient = IngredientCreatorAccess.fluid().from(recipe.getFluid().getIngredient(),
                recipe.getFluid().getAmount());
        this.outputItem = recipe.getResultItem();
        this.outputFluid = recipe.getResultFluid();
    }

    @Override
    protected void calculateOperationsThisTick(OperationTracker tracker) {
        super.calculateOperationsThisTick(tracker);
        if (!tracker.shouldContinueChecking()) {
            return;
        }
        recipeInputItems = itemInputHandler.getRecipeInput(itemIngredients, v -> slotIndexCache = v);
        recipeInputFluid = fluidInputHandler.getRecipeInput(fluidIngredient);
        if (recipeInputItems.isEmpty() || recipeInputFluid.isEmpty()) {
            tracker.resetProgress(RecipeError.NOT_ENOUGH_INPUT);
            return;
        }
        itemInputHandler.calculateOperationsCanSupport(tracker, recipeInputItems, slotIndexCache);
        fluidInputHandler.calculateOperationsCanSupport(tracker, recipeInputFluid);
        itemOutputHandler.calculateOperationsCanSupport(tracker, outputItem);
        fluidOutputHandler.calculateOperationsCanSupport(tracker, outputFluid);
    }

    @Override
    public boolean isInputValid() {
        if (fluidIngredient.testType(fluidInputHandler.getInput())) {
            List<ItemStack> inputs = itemInputHandler.getInput();
            if (inputs.size() == itemIngredients.size()) {
                boolean[] usedIndex = new boolean[itemIngredients.size()];
                boolean result = true;
                for (int i = 0; i < usedIndex.length && result; i++) {
                    boolean found = false;
                    for (int j = 0; j < usedIndex.length; j++) {
                        if (usedIndex[j]) {
                            continue;
                        }
                        if (itemIngredients.get(i).testType(inputs.get(j))) {
                            usedIndex[j] = true;
                            found = true;
                            break;
                        }
                    }
                    result &= found;
                }
                return result;
            }
        }
        return false;
    }

    @Override
    protected void finishProcessing(int operations) {
        itemInputHandler.use(recipeInputItems, slotIndexCache, operations);
        fluidInputHandler.use(recipeInputFluid, operations);
        itemOutputHandler.handleOutput(outputItem, operations);
        fluidOutputHandler.handleOutput(outputFluid, operations);
    }

}
