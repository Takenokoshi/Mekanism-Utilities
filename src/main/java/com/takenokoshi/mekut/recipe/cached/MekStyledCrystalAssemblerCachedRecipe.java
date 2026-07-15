package com.takenokoshi.mekut.recipe.cached;

import java.util.List;
import java.util.function.BooleanSupplier;

import com.glodblock.github.extendedae.recipe.CrystalAssemblerRecipe;
import com.glodblock.github.glodium.recipe.stack.IngredientStack;
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
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;

public class MekStyledCrystalAssemblerCachedRecipe extends AbstractCachedRecipe<CrystalAssemblerRecipe> {

    private final ItemStackListInputHandler itemInputHandler;
    private final IInputHandler<FluidStack> fluidInputHandler;
    private final ItemOutputHandler itemOutputHandler;

    private final List<ItemStackIngredient> itemIngredients;
    private final FluidStackIngredient fluidIngredient;

    private List<ItemStack> recipeInputItems = List.of();
    private int[] slotIndexCache = new int[0];
    private FluidStack recipeInputFluid = FluidStack.EMPTY;

    private final ItemStack outputItem;

    public MekStyledCrystalAssemblerCachedRecipe(CrystalAssemblerRecipe recipe,
            BooleanSupplier recheckAllErrors,
            ItemStackListInputHandler itemInputHandler,
            IInputHandler<FluidStack> fluidInputHandler,
            ItemOutputHandler itemOutputHandler) {
        super(recipe, recheckAllErrors);
        this.itemInputHandler = itemInputHandler;
        this.fluidInputHandler = fluidInputHandler;
        this.itemOutputHandler = itemOutputHandler;
        IItemStackIngredientCreator creatorI = IngredientCreatorAccess.item();
        this.itemIngredients = recipe.getInputs().stream()
                .map(v -> creatorI.from(v.getIngredient(), v.getAmount()))
                .toList();
        IngredientStack.Fluid fluid = recipe.getFluid();
        this.fluidIngredient = fluid == null
                ? null
                : IngredientCreatorAccess.fluid().from(fluid.getIngredient(), fluid.getAmount());
        this.outputItem = recipe.output;
    }

    @Override
    protected void calculateOperationsThisTick(OperationTracker tracker) {
        super.calculateOperationsThisTick(tracker);
        if (!tracker.shouldContinueChecking()) {
            return;
        }
        if (!itemIngredients.isEmpty()) {
            recipeInputItems = itemInputHandler.getRecipeInput(itemIngredients, v -> slotIndexCache = v);
            if (recipeInputItems.isEmpty()) {
                tracker.resetProgress(RecipeError.NOT_ENOUGH_INPUT);
                return;
            }
            itemInputHandler.calculateOperationsCanSupport(tracker, recipeInputItems, slotIndexCache);
        }
        if (fluidIngredient != null) {
            recipeInputFluid = fluidInputHandler.getRecipeInput(fluidIngredient);
            if (recipeInputFluid.isEmpty()) {
                tracker.resetProgress(RecipeError.NOT_ENOUGH_INPUT);
                return;
            }
            fluidInputHandler.calculateOperationsCanSupport(tracker, recipeInputFluid);
        }
        itemOutputHandler.calculateOperationsCanSupport(tracker, outputItem);
    }

    @Override
    public boolean isInputValid() {
        if ((fluidIngredient == null && fluidInputHandler.getInput().isEmpty())
                || fluidIngredient.testType(fluidInputHandler.getInput())) {
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
        if (fluidInputHandler != null) {
            fluidInputHandler.use(recipeInputFluid, operations);
        }
        itemOutputHandler.handleOutput(outputItem, operations);
    }

}
