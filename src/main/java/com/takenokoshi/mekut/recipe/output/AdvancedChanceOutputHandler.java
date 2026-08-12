package com.takenokoshi.mekut.recipe.output;

import java.util.concurrent.ThreadLocalRandom;

import mekanism.api.inventory.IInventorySlot;
import mekanism.api.math.MathUtils;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.outputs.IOutputHandler;
import net.minecraft.world.item.ItemStack;

public class AdvancedChanceOutputHandler extends BasicChanceOutputHandler {

    public AdvancedChanceOutputHandler(IOutputHandler<ItemStack> delegate) {
        super(delegate);
    }

    public static AdvancedChanceOutputHandler create(IInventorySlot slot, RecipeError notEnoughSpaceError) {
        return new AdvancedChanceOutputHandler(new ItemOutputHandler(slot, notEnoughSpaceError));
    }

    @Override
    protected int sampleSuccesses(int operations, double chance) {
        double mean = operations * chance;
        double stddev = Math.sqrt(mean * (1.0d - chance));
        int successes = MathUtils.clampToInt(Math.round(ThreadLocalRandom.current().nextGaussian() * stddev + mean));
        return Math.max(0, Math.min(operations, successes));
    }

}