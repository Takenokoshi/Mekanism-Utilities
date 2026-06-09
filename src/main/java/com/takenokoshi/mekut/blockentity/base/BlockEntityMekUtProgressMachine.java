package com.takenokoshi.mekut.blockentity.base;

import java.util.List;
import java.util.function.ToIntFunction;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekut.core.MekUtUpgradeUtils;
import com.takenokoshi.mekut.recipe.cached.ICachedRecipe;

import mekanism.api.Upgrade;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.common.integration.computer.annotation.ComputerMethod;
import mekanism.common.inventory.container.MekanismContainer;
import mekanism.common.inventory.container.sync.SyncableInt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BlockEntityMekUtProgressMachine<RECIPE extends Recipe<?>> extends BlockEntityMekUtRecipeMachine<RECIPE> {

    private int operatingTicks;
    protected final int baseTicksRequired;
    protected int ticksRequired;
    protected int recipeTicksRequired;
    protected final ToIntFunction<RECIPE> recipeTicksGetter;

    public BlockEntityMekUtProgressMachine(Holder<Block> blockProvider, BlockPos pos, BlockState state,
            List<RecipeError> errorTypes, int baseTicksRequired, ToIntFunction<RECIPE> recipeTicksGetter,
            int baselineMaxOperations) {
        super(blockProvider, pos, state, errorTypes, baselineMaxOperations);
        this.baseTicksRequired = baseTicksRequired;
        this.ticksRequired = this.baseTicksRequired;
        this.recipeTicksRequired = this.baseTicksRequired;
        this.recipeTicksGetter = recipeTicksGetter;
    }

    @Override
    public void addContainerTrackers(MekanismContainer container) {
        super.addContainerTrackers(container);
        container.track(SyncableInt.create(this::getOperatingTicks, this::setOperatingTicks));
        container.track(SyncableInt.create(this::getTicksRequired, (value) -> this.ticksRequired = value));
        container.track(SyncableInt.create(() -> recipeTicksRequired, (value) -> this.recipeTicksRequired = value));
    }

    public double getScaledProgress() {
        return (double) this.getOperatingTicks() / (double) this.ticksRequired;
    }

    protected void setOperatingTicks(int ticks) {
        this.operatingTicks = ticks;
    }

    @ComputerMethod(nameOverride = "getRecipeProgress")
    public int getOperatingTicks() {
        return this.operatingTicks;
    }

    @ComputerMethod
    public int getTicksRequired() {
        return this.ticksRequired;
    }

    public int getSavedOperatingTicks(int cacheIndex) {
        return this.getOperatingTicks();
    }

    public void loadAdditional(@NotNull CompoundTag nbt, HolderLookup.@NotNull Provider provider) {
        super.loadAdditional(nbt, provider);
        this.operatingTicks = nbt.getInt("progress");
    }

    public void saveAdditional(@NotNull CompoundTag nbtTags, HolderLookup.@NotNull Provider provider) {
        super.saveAdditional(nbtTags, provider);
        nbtTags.putInt("progress", this.getOperatingTicks());
    }

    public int getOperationsPerTick() {
        return this.operationsPerTick;
    }

    public void onCachedRecipeChanged(@Nullable ICachedRecipe<RECIPE> cachedRecipe, int cacheIndex) {
        super.onCachedRecipeChanged(cachedRecipe, cacheIndex);
        recipeTicksRequired = recipeTicksGetter.applyAsInt(cachedRecipe.getRecipe());
        recaluculateProcessingSpeed();
    }

    protected abstract void recaluculateProcessingSpeed();

    public void recalculateUpgrades(Upgrade upgrade) {
        super.recalculateUpgrades(upgrade);
        if (upgrade == Upgrade.SPEED || MekUtUpgradeUtils.isEmpoweredSpeed(upgrade)) {
            recaluculateProcessingSpeed();
        }
    }

    public long getEnergyUsed() {
        return clientEnergyUsed;
    }
}
