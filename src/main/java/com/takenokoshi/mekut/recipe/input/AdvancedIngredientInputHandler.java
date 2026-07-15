package com.takenokoshi.mekut.recipe.input;

import mekanism.api.Action;
import mekanism.api.inventory.IInventorySlot;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class AdvancedIngredientInputHandler {
    private final IInventorySlot slot;
    private final RecipeError notEnoughError;
    private ItemStack suppliedStack = ItemStack.EMPTY;

    public AdvancedIngredientInputHandler(IInventorySlot slot, RecipeError notEnoughError) {
        this.slot = slot;
        this.notEnoughError = notEnoughError;
    }

    public ItemStack getInput() {
        return suppliedStack.isEmpty() ? slot.getStack() : suppliedStack;
    }

    public ItemStack getRecipeInput(Ingredient ingredient) {
        if (ingredient.test(suppliedStack)) {
            return suppliedStack.copyWithCount(1);
        }
        ItemStack stack = slot.getStack();
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        }
        if (ingredient.test(stack)) {
            return stack.copyWithCount(1);
        }
        return ItemStack.EMPTY;
    }

    public void calculateOperationsCanSupport(OperationTracker tracker, ItemStack inputStack) {
        if (suppliedStack.isEmpty() || !ItemStack.isSameItemSameComponents(suppliedStack, inputStack)) {
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
    }

    public void use(ItemStack inputStack, int operations) {
        if (suppliedStack.isEmpty() || !ItemStack.isSameItemSameComponents(suppliedStack, inputStack)) {
            slot.shrinkStack(inputStack.getCount() * operations, Action.EXECUTE);
        }
    }

    public void setSuppliedStack(ItemStack value) {
        suppliedStack = value;
    }
}
