package com.takenokoshi.mekut.recipe.input;

import com.takenokoshi.mekut.recipe.cached.OperationTracker2;

import mekanism.api.Action;
import mekanism.api.inventory.IInventorySlot;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class IngredientInputHandler {
    private final IInventorySlot slot;
    private final RecipeError notEnoughError;

    public IngredientInputHandler(IInventorySlot slot, RecipeError notEnoughError) {
        this.slot = slot;
        this.notEnoughError = notEnoughError;
    }

    public ItemStack getInput() {
        return slot.getStack();
    }

    public ItemStack getRecipeInput(Ingredient ingredient) {
        ItemStack stack = getInput();
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        }
        if (ingredient.test(stack)) {
            return stack.copyWithCount(1);
        }
        return ItemStack.EMPTY;
    }

    public void calculateOperationsCanSupport(OperationTracker2 tracker, ItemStack inputStack) {
        if (slot.isEmpty()) {
            tracker.resetProgress(notEnoughError);
        } else if (ItemStack.isSameItemSameComponents(slot.getStack(), inputStack)) {
            int operations = slot.getCount() / inputStack.getCount();
            if (operations < 1) {
                tracker.resetProgress(notEnoughError);
            } else {
                tracker.updateOperations(operations);
            }
        } else {
            tracker.mismatchedRecipe();
        }
    }

    public void use(ItemStack inputStack, int operations) {
        slot.shrinkStack(inputStack.getCount() * operations, Action.EXECUTE);
    }
}
