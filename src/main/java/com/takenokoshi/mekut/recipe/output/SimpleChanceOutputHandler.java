package com.takenokoshi.mekut.recipe.output;

import java.util.concurrent.ThreadLocalRandom;

import mekanism.api.inventory.IInventorySlot;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.outputs.IOutputHandler;
import net.minecraft.world.item.ItemStack;

public class SimpleChanceOutputHandler extends BasicChanceOutputHandler {

    public SimpleChanceOutputHandler(IOutputHandler<ItemStack> delegate) {
        super(delegate);
    }

    public static SimpleChanceOutputHandler create(IInventorySlot slot, RecipeError notEnoughSpaceError) {
        return new SimpleChanceOutputHandler(new ItemOutputHandler(slot, notEnoughSpaceError));
    }

    @Override
    protected int sampleSuccesses(int operations, double chance) {
        int successes = 0;
        for (int i = 0; i < operations; i++) {
            if (ThreadLocalRandom.current().nextDouble() < chance) {
                successes++;
            }
        }
        return successes;
    }
}