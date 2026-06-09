package com.takenokoshi.mekut.recipe.input;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import mekanism.api.Action;
import mekanism.api.inventory.IInventorySlot;
import mekanism.api.math.MathUtils;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.world.item.ItemStack;

public class ItemStackListInputHandler {

    private final List<IInventorySlot> slots;
    private final RecipeError notEnoughError;

    public ItemStackListInputHandler(List<IInventorySlot> slots, RecipeError notEnoughError) {
        this.slots = slots;
        this.notEnoughError = notEnoughError;
    }

    public List<ItemStack> getInput() {
        List<ItemStack> result = new ArrayList<>();
        for (IInventorySlot slot : slots) {
            if (!slot.isEmpty()) {
                result.add(slot.getStack());
            }
        }
        return Collections.unmodifiableList(result);
    }

    public List<ItemStack> getRecipeInput(List<ItemStackIngredient> ingredients,
            Consumer<@NotNull int[]> slotIndexCacheSaver) {
        List<ItemStack> list = new ArrayList<>();
        int[] slotIndexCache = new int[ingredients.size()];
        boolean[] usedSlots = new boolean[slots.size()];
        for (int i = 0; i < ingredients.size(); i++) {
            ItemStackIngredient ingredient = ingredients.get(i);
            for (int j = 0; j < slots.size(); j++) {
                if (!usedSlots[j]) {
                    IInventorySlot slot = slots.get(j);
                    if (slot.isEmpty()) {
                        usedSlots[j] = true;
                        continue;
                    }
                    ItemStack stack = slot.getStack();
                    if (ingredient.test(stack)) {
                        usedSlots[j] = true;
                        slotIndexCache[i] = j;
                        list.add(stack.copyWithCount(MathUtils.clampToInt(ingredient.getNeededAmount(stack))));
                        break;
                    }
                }
            }
        }
        if (list.size() != ingredients.size()) {
            slotIndexCacheSaver.accept(new int[0]);
            return List.of();
        }
        slotIndexCacheSaver.accept(slotIndexCache);
        return Collections.unmodifiableList(list);
    }

    public void calculateOperationsCanSupport(OperationTracker tracker, List<ItemStack> inputStacks,
            @NotNull int[] slotIndexCache) {
        if (inputStacks.size() != slotIndexCache.length) {
            tracker.resetProgress(RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT);
            return;
        }
        for (int i = 0; i < slotIndexCache.length; i++) {
            int operations = slots.get(slotIndexCache[i]).getCount() / inputStacks.get(i).getCount();
            if (operations < 1) {
                tracker.resetProgress(notEnoughError);
            } else {
                tracker.updateOperations(operations);
            }
        }
    }

    public void use(List<ItemStack> inputStacks, @NotNull int[] slotIndexCache, int operations) {
        if (inputStacks.size() != slotIndexCache.length) {
            return;
        }
        for (int i = 0; i < slotIndexCache.length; i++) {
            slots.get(slotIndexCache[i]).shrinkStack(inputStacks.get(i).getCount() * operations, Action.EXECUTE);
        }
    }
}
