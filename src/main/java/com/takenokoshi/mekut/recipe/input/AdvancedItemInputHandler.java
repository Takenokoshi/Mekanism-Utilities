package com.takenokoshi.mekut.recipe.input;

import mekanism.api.inventory.IInventorySlot;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.ingredients.InputIngredient;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.inputs.InputHelper;
import net.minecraft.world.item.ItemStack;

public class AdvancedItemInputHandler implements IInputHandler<ItemStack> {

    protected final IInputHandler<ItemStack> delegate;
    private ItemStack suppliedStack = ItemStack.EMPTY;

    public AdvancedItemInputHandler(IInputHandler<ItemStack> delegate) {
        this.delegate = delegate;
    }

    public static AdvancedItemInputHandler create(IInventorySlot slot, RecipeError notEnoughError) {
        return new AdvancedItemInputHandler(InputHelper.getInputHandler(slot, notEnoughError));
    }

    @Override
    public ItemStack getInput() {
        return suppliedStack.isEmpty() ? delegate.getInput() : suppliedStack;
    }

    @Override
    public ItemStack getRecipeInput(InputIngredient<ItemStack> ingredient) {
        return ingredient.testType(suppliedStack)
                ? ingredient.getMatchingInstance(suppliedStack)
                : delegate.getRecipeInput(ingredient);
    }

    @Override
    public void calculateOperationsCanSupport(OperationTracker tracker, ItemStack recipeInput, int usageMultiplier) {
        if (suppliedStack.isEmpty() || !ItemStack.isSameItemSameComponents(suppliedStack, recipeInput)) {
            delegate.calculateOperationsCanSupport(tracker, recipeInput, usageMultiplier);
        }
    }

    @Override
    public void use(ItemStack recipeInput, int operations) {
        if (suppliedStack.isEmpty() || !ItemStack.isSameItemSameComponents(suppliedStack, recipeInput)) {
            delegate.use(recipeInput, operations);
        }
    }

    public void setSuppliedStack(ItemStack value) {
        suppliedStack = value;
    }
}
