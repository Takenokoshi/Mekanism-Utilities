package com.takenokoshi.mekut.recipe.output;

import mekanism.api.recipes.cache.CachedRecipe.OperationTracker;
import mekanism.api.recipes.outputs.IOutputHandler;
import net.minecraft.world.item.ItemStack;

public abstract class BasicChanceOutputHandler implements IOutputHandler<MekUtChanceOutput> {

    protected final IOutputHandler<ItemStack> delegate;

    protected BasicChanceOutputHandler(IOutputHandler<ItemStack> delegate) {
        this.delegate = delegate;
    }

    // caluculate without chance
    @Override
    public final void calculateOperationsCanSupport(OperationTracker tracker, MekUtChanceOutput output) {
        delegate.calculateOperationsCanSupport(tracker, output.value());
    }

    protected abstract int sampleSuccesses(int operations, double chance);

    @Override
    public final void handleOutput(MekUtChanceOutput output, int operations) {
        ItemStack v = output.value();
        if (v.isEmpty() || operations < 1) {
            return;
        }
        double chance = output.chance();
        if (chance <= 0.0d) {
            return;
        }
        if (chance >= 1.0d) {
            delegate.handleOutput(v, operations);
            return;
        }
        int successes = sampleSuccesses(operations, chance);
        if (successes > 0) {
            delegate.handleOutput(v, successes);
        }
    }
}