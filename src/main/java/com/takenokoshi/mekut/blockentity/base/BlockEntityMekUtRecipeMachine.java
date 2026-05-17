package com.takenokoshi.mekut.blockentity.base;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.function.ToIntFunction;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekut.core.MekUtUpgradeUtils;
import com.takenokoshi.mekut.recipe.cached.AbstractCachedRecipe;
import com.takenokoshi.mekut.recipe.lookup.IMekUtRecipeLookUpHandler;
import com.takenokoshi.mekut.recipe.lookup.MekUtRecipeCacheLookupMonitor;

import mekanism.api.IContentsListener;
import mekanism.api.Upgrade;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.common.capabilities.heat.CachedAmbientTemperature;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import mekanism.common.capabilities.holder.energy.IEnergyContainerHolder;
import mekanism.common.capabilities.holder.fluid.IFluidTankHolder;
import mekanism.common.capabilities.holder.heat.IHeatCapacitorHolder;
import mekanism.common.capabilities.holder.slot.IInventorySlotHolder;
import mekanism.common.integration.computer.annotation.ComputerMethod;
import mekanism.common.inventory.container.MekanismContainer;
import mekanism.common.inventory.container.sync.SyncableInt;
import mekanism.common.tile.prefab.TileEntityConfigurableMachine;
import mekanism.common.tile.prefab.TileEntityRecipeMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BlockEntityMekUtRecipeMachine<RECIPE extends Recipe<?>> extends TileEntityConfigurableMachine
        implements IMekUtRecipeLookUpHandler<RECIPE> {

    protected final BooleanSupplier recheckAllRecipeErrors;
    private final List<RecipeError> errorTypes;
    private final boolean[] trackedErrors;
    protected MekUtRecipeCacheLookupMonitor<RECIPE> recipeCacheLookupMonitor;
    private @Nullable IContentsListener recipeCacheSaveOnlyListener;
    private @Nullable IContentsListener recipeCacheUnpauseListener;
    private @Nullable IContentsListener recipeCacheUnpauseSaveOnlyListener;

    private int operatingTicks;
    protected final int baseTicksRequired;
    protected int ticksRequired;
    protected int operationsPerTick = 1;
    protected int recipeTicksRequired;
    protected final ToIntFunction<RECIPE> recipeTicksGetter;
    protected final int baselineMaxOperations;

    public BlockEntityMekUtRecipeMachine(Holder<Block> blockProvider, BlockPos pos, BlockState state,
            List<RecipeError> errorTypes, int baseTicksRequired, ToIntFunction<RECIPE> recipeTicksGetter, int baselineMaxOperations) {
        super(blockProvider, pos, state);
        this.recheckAllRecipeErrors = TileEntityRecipeMachine.shouldRecheckAllErrors(this);
        this.errorTypes = List.copyOf(errorTypes);
        trackedErrors = new boolean[this.errorTypes.size()];
        recipeCacheSaveOnlyListener = null;
        this.baseTicksRequired = baseTicksRequired;
        this.ticksRequired = this.baseTicksRequired;
        this.recipeTicksRequired = this.baseTicksRequired;
        this.recipeTicksGetter = recipeTicksGetter;
        this.baselineMaxOperations = baselineMaxOperations;
    }

    @Override
    protected void presetVariables() {
        super.presetVariables();
        recipeCacheLookupMonitor = createNewCacheMonitor();
    }

    protected MekUtRecipeCacheLookupMonitor<RECIPE> createNewCacheMonitor() {
        return new MekUtRecipeCacheLookupMonitor<>(this);
    }

    protected IContentsListener getRecipeCacheSaveOnlyListener() {
        if (supportsComparator()) {
            if (recipeCacheSaveOnlyListener == null) {
                recipeCacheSaveOnlyListener = () -> {
                    markForSave();
                    recipeCacheLookupMonitor.onChange();
                };
            }
            return recipeCacheSaveOnlyListener;
        }
        return recipeCacheLookupMonitor;
    }

    protected IContentsListener getRecipeCacheUnpauseListener(@Nullable IContentsListener listener) {
        if (listener == this) {
            if (this.recipeCacheUnpauseListener == null) {
                this.recipeCacheUnpauseListener = () -> {
                    this.onContentsChanged();
                    this.recipeCacheLookupMonitor.unpause();
                };
            }

            return this.recipeCacheUnpauseListener;
        } else {
            if (this.recipeCacheUnpauseSaveOnlyListener == null) {
                this.recipeCacheUnpauseSaveOnlyListener = () -> {
                    this.markForSave();
                    this.recipeCacheLookupMonitor.unpause();
                };
            }

            return this.recipeCacheUnpauseSaveOnlyListener;
        }
    }

    @Override
    public void addContainerTrackers(MekanismContainer container) {
        super.addContainerTrackers(container);
        container.trackArray(trackedErrors);
        container.track(SyncableInt.create(this::getOperatingTicks, this::setOperatingTicks));
        container.track(SyncableInt.create(this::getTicksRequired, (value) -> this.ticksRequired = value));
        container.track(SyncableInt.create(() -> recipeTicksRequired, (value) -> this.recipeTicksRequired = value));
    }

    @Override
    public void clearRecipeErrors(int cacheIndex) {
        Arrays.fill(trackedErrors, false);
    }

    protected void onErrorsChanged(Set<RecipeError> errors) {
        for (int i = 0; i < trackedErrors.length; i++) {
            trackedErrors[i] = errors.contains(errorTypes.get(i));
        }
    }

    public BooleanSupplier getWarningCheck(RecipeError error) {
        int errorIndex = errorTypes.indexOf(error);
        if (errorIndex == -1) {
            return () -> false;
        }
        return () -> trackedErrors[errorIndex];
    }

    public final @Nullable IChemicalTankHolder getInitialChemicalTanks(IContentsListener listener) {
        return this.getInitialChemicalTanks(listener,
                (IContentsListener) (listener == this ? this.recipeCacheLookupMonitor
                        : this.getRecipeCacheSaveOnlyListener()),
                this.getRecipeCacheUnpauseListener(listener));
    }

    protected @Nullable IChemicalTankHolder getInitialChemicalTanks(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        return null;
    }

    protected final @Nullable IFluidTankHolder getInitialFluidTanks(IContentsListener listener) {
        return this.getInitialFluidTanks(listener, (IContentsListener) (listener == this ? this.recipeCacheLookupMonitor
                : this.getRecipeCacheSaveOnlyListener()), this.getRecipeCacheUnpauseListener(listener));
    }

    protected @Nullable IFluidTankHolder getInitialFluidTanks(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        return null;
    }

    protected final @Nullable IEnergyContainerHolder getInitialEnergyContainers(IContentsListener listener) {
        return this.getInitialEnergyContainers(listener,
                (IContentsListener) (listener == this ? this.recipeCacheLookupMonitor
                        : this.getRecipeCacheSaveOnlyListener()),
                this.getRecipeCacheUnpauseListener(listener));
    }

    protected @Nullable IEnergyContainerHolder getInitialEnergyContainers(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        return null;
    }

    protected final @Nullable IInventorySlotHolder getInitialInventory(IContentsListener listener) {
        return this.getInitialInventory(listener, (IContentsListener) (listener == this ? this.recipeCacheLookupMonitor
                : this.getRecipeCacheSaveOnlyListener()), this.getRecipeCacheUnpauseListener(listener));
    }

    protected @Nullable IInventorySlotHolder getInitialInventory(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        return null;
    }

    protected final @Nullable IHeatCapacitorHolder getInitialHeatCapacitors(IContentsListener listener,
            CachedAmbientTemperature ambientTemperature) {
        return this.getInitialHeatCapacitors(listener,
                (IContentsListener) (listener == this ? this.recipeCacheLookupMonitor
                        : this.getRecipeCacheSaveOnlyListener()),
                this.getRecipeCacheUnpauseListener(listener), ambientTemperature);
    }

    protected @Nullable IHeatCapacitorHolder getInitialHeatCapacitors(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener,
            CachedAmbientTemperature ambientTemperature) {
        return null;
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

    public void onCachedRecipeChanged(@Nullable AbstractCachedRecipe<RECIPE> cachedRecipe, int cacheIndex){
        IMekUtRecipeLookUpHandler.super.onCachedRecipeChanged(cachedRecipe,cacheIndex);
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
}
