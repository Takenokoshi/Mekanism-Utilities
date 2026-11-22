package takenokoshi.mekal.common.tile.prefab;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import mekanism.api.IContentsListener;
import mekanism.api.SerializationConstants;
import mekanism.api.Upgrade;
import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.api.math.MathUtils;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.inputs.InputHelper;
import mekanism.api.recipes.outputs.IOutputHandler;
import mekanism.api.recipes.outputs.OutputHelper;
import mekanism.common.capabilities.energy.MachineEnergyContainer;
import mekanism.common.capabilities.fluid.BasicFluidTank;
import mekanism.common.capabilities.holder.energy.EnergyContainerHelper;
import mekanism.common.capabilities.holder.energy.IEnergyContainerHolder;
import mekanism.common.capabilities.holder.fluid.FluidTankHelper;
import mekanism.common.capabilities.holder.fluid.IFluidTankHolder;
import mekanism.common.capabilities.holder.slot.IInventorySlotHolder;
import mekanism.common.capabilities.holder.slot.InventorySlotHelper;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.inventory.slot.EnergyInventorySlot;
import mekanism.common.inventory.slot.FluidInventorySlot;
import mekanism.common.inventory.slot.InputInventorySlot;
import mekanism.common.inventory.slot.OutputInventorySlot;
import mekanism.common.inventory.warning.WarningTracker.WarningType;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.recipe.lookup.monitor.RecipeCacheLookupMonitor;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.tile.prefab.TileEntityProgressMachine;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.StatUtils;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import takenokoshi.mekal.api.recipes.ItemStackFluidToItemStackRecipe;
import takenokoshi.mekal.api.recipes.cache.ItemStackConstantFluidToObjectCachedRecipe;
import takenokoshi.mekal.api.recipes.cache.MekALTwoInputCachedRecipe;
import takenokoshi.mekal.api.recipes.inputs.AdvancedFluidInputHandler;
import takenokoshi.mekal.common.inventory.slot.AdvancedFluidInputSlot;
import takenokoshi.mekal.common.recipe.lookup.ItemFluidRecipeLookupHandler;
import takenokoshi.mekal.common.recipe.lookup.monitor.ItemStackConstantFluidRecipeLookupMonitor;
import takenokoshi.mekal.common.upgrade.ItemStackFluidToItemStackMachineUpgradeData;
import takenokoshi.mekal.common.util.MekALUpgradeUtils;

public abstract class TileEntityItemStackFluidToItemStackMachine
        extends TileEntityProgressMachine<ItemStackFluidToItemStackRecipe>
        implements ItemFluidRecipeLookupHandler<ItemStackFluidToItemStackRecipe> {

    private static final List<RecipeError> TRACKED_ERROR_TYPES = List.of(
            RecipeError.NOT_ENOUGH_ENERGY,
            RecipeError.NOT_ENOUGH_INPUT,
            RecipeError.NOT_ENOUGH_SECONDARY_INPUT,
            RecipeError.NOT_ENOUGH_OUTPUT_SPACE,
            RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT);

    public static final int BASE_TICKS_REQUIRED = 10 * SharedConstants.TICKS_PER_SECOND;
    public static final int MAX_FLUID = 1050;

    protected final ItemStackConstantFluidToObjectCachedRecipe.FluidUsageMultiplier fluidUsageMultiplier;
    protected double fluidPerTickMeanMultiplier = 1.0D;
    protected int baseTotalUsage;
    protected int usedSoFar;

    public IExtendedFluidTank fluidTank;

    protected final IOutputHandler<@NotNull ItemStack> outputHandler;
    protected final IInputHandler<@NotNull ItemStack> itemInputHandler;
    protected final AdvancedFluidInputHandler fluidInputHandler;

    protected MachineEnergyContainer<TileEntityItemStackFluidToItemStackMachine> energyContainer;

    protected InputInventorySlot inputSlot;
    protected OutputInventorySlot outputSlot;
    protected FluidInventorySlot fluidSlot;
    protected OutputInventorySlot bucketSlot;
    protected EnergyInventorySlot energySlot;

    protected TileEntityItemStackFluidToItemStackMachine(Holder<Block> blockProvider, BlockPos pos, BlockState state,
            int baseTicksRequired) {
        super(blockProvider, pos, state, TRACKED_ERROR_TYPES, baseTicksRequired);
        configComponent.setupItemIOExtraConfig(inputSlot, outputSlot, fluidSlot, energySlot);
        configComponent.setupInputConfig(TransmissionType.FLUID, fluidTank);
        configComponent.setupInputConfig(TransmissionType.ENERGY, energyContainer);
        ejectorComponent = new TileComponentEjector(this);
        ejectorComponent.setOutputData(configComponent, TransmissionType.ITEM);

        itemInputHandler = InputHelper.getInputHandler(inputSlot, RecipeError.NOT_ENOUGH_INPUT);
        fluidInputHandler = AdvancedFluidInputHandler.create(fluidTank, RecipeError.NOT_ENOUGH_SECONDARY_INPUT);
        outputHandler = OutputHelper.getOutputHandler(outputSlot, RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
        baseTotalUsage = this.baseTicksRequired;
        if (useStatisticalMechanics()) {
            fluidUsageMultiplier = (usedSoFar, operatingTicks) -> StatUtils.inversePoisson(fluidPerTickMeanMultiplier);
        } else {
            fluidUsageMultiplier = ItemStackConstantFluidToObjectCachedRecipe.FluidUsageMultiplier
                    .constantUse(() -> baseTotalUsage, this::getTicksRequired);
        }
    }

    protected boolean useStatisticalMechanics() {
        return false;
    }

    @Override
    protected RecipeCacheLookupMonitor<ItemStackFluidToItemStackRecipe> createNewCacheMonitor() {
        return new ItemStackConstantFluidRecipeLookupMonitor<>(this, this::getSavedUsedSoFar);
    }

    @NotNull
    @Override
    protected IEnergyContainerHolder getInitialEnergyContainers(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        EnergyContainerHelper builder = EnergyContainerHelper.forSideWithConfig(this);
        builder.addContainer(energyContainer = MachineEnergyContainer.input(this, recipeCacheUnpauseListener));
        return builder.build();
    }

    @NotNull
    @Override
    protected IFluidTankHolder getInitialFluidTanks(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        FluidTankHelper builder = FluidTankHelper.forSideWithConfig(this);
        builder.addTank(fluidTank = BasicFluidTank.input(getInitialFluidTankCapacity(),
                stack -> containsRecipeBA(inputSlot.getStack(), stack),
                this::containsRecipeB,
                recipeCacheListener));
        return builder.build();
    }

    protected int getInitialFluidTankCapacity() {
        return MAX_FLUID;
    }

    @NotNull
    @Override
    protected IInventorySlotHolder getInitialInventory(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        InventorySlotHelper builder = InventorySlotHelper.forSideWithConfig(this);
        builder.addSlot(inputSlot = InputInventorySlot.at(
                item -> containsRecipeAB(item, fluidTank.getFluid()),
                this::containsRecipeA,
                recipeCacheListener,
                64, 17))
                .tracksWarnings(slot -> slot.warning(WarningType.NO_MATCHING_RECIPE,
                        getWarningCheck(RecipeError.NOT_ENOUGH_INPUT)));
        builder.addSlot(fluidSlot = AdvancedFluidInputSlot.create(fluidTank, () -> {
            listener.onContentsChanged();
            this.onFluidSlotChanged();
        }, 64, 53));
        builder.addSlot(outputSlot = OutputInventorySlot.at(recipeCacheUnpauseListener, 116, 35))
                .tracksWarnings(slot -> slot.warning(WarningType.NO_SPACE_IN_OUTPUT,
                        getWarningCheck(RecipeError.NOT_ENOUGH_OUTPUT_SPACE)));
        builder.addSlot(bucketSlot = OutputInventorySlot.at(listener, 90, 53));
        builder.addSlot(
                energySlot = EnergyInventorySlot.fillOrConvert(energyContainer, this::getLevel, listener, 39, 35));
        fluidSlot.setSlotOverlay(SlotOverlay.MINUS);
        return builder.build();
    }

    private void onFluidSlotChanged() {
        fluidInputHandler.setSuppliedByItem(fluidSlot.getStack());
    }

    @Override
    protected boolean onUpdateServer() {
        boolean sendUpdatePacket = super.onUpdateServer();
        energySlot.fillContainerOrConvert();
        fluidSlot.fillTank(bucketSlot);
        recipeCacheLookupMonitor.updateAndProcess();
        return sendUpdatePacket;
    }

    @Override
    public @Nullable ItemStackFluidToItemStackRecipe getRecipe(int arg0) {
        return findFirstRecipe(itemInputHandler, fluidInputHandler);
    }

    @Override
    public @NotNull CachedRecipe<ItemStackFluidToItemStackRecipe> createNewCachedRecipe(
            @NotNull ItemStackFluidToItemStackRecipe recipe, int cacheIndex) {
        return (recipe.perTickUsage()
                ? ItemStackConstantFluidToObjectCachedRecipe.toItem(recipe, recheckAllRecipeErrors, itemInputHandler,
                        fluidInputHandler, fluidUsageMultiplier, used -> usedSoFar = used, outputHandler)
                : MekALTwoInputCachedRecipe.itemFluidToItem(recipe, recheckAllRecipeErrors, itemInputHandler,
                        fluidInputHandler, outputHandler))
                .setErrorsChanged(this::onErrorsChanged)
                .setCanHolderFunction(this::canFunction)
                .setActive(this::setActive)
                .setEnergyRequirements(energyContainer::getEnergyPerTick, energyContainer)
                .setRequiredTicks(this::getTicksRequired)
                .setOnFinish(this::markForSave)
                .setOperatingTicksChanged(this::setOperatingTicks)
                .setBaselineMaxOperations(this::getOperationsPerTick);
    }

    @Override
    public void recalculateUpgrades(Upgrade upgrade) {
        super.recalculateUpgrades(upgrade);
        if (MekALUpgradeUtils.isSpeedModifier(upgrade)
                || (upgrade == Upgrade.CHEMICAL && supportsUpgrade(Upgrade.CHEMICAL))) {
            if (useStatisticalMechanics()) {
                fluidPerTickMeanMultiplier = MekanismUtils.getGasPerTickMeanMultiplier(this);
            } else {
                baseTotalUsage = MathUtils.clampToInt(MekanismUtils.getBaseUsage(this, baseTicksRequired));
            }
        }
    }

    @Override
    public ItemStackFluidToItemStackMachineUpgradeData getUpgradeData(HolderLookup.Provider provider) {
        return new ItemStackFluidToItemStackMachineUpgradeData(provider,
                redstone, getControlType(), energyContainer,
                getOperatingTicks(), usedSoFar,
                fluidTank, fluidSlot, bucketSlot,
                energySlot, inputSlot, outputSlot, getComponents());
    }

    public MachineEnergyContainer<TileEntityItemStackFluidToItemStackMachine> getEnergyContainer() {
        return energyContainer;
    }

    public IExtendedFluidTank getFluidTank() {
        return fluidTank;
    }

    public int getSavedUsedSoFar() {
        return usedSoFar;
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag nbt, @NotNull HolderLookup.Provider provider) {
        super.loadAdditional(nbt, provider);
        usedSoFar = nbt.getInt(SerializationConstants.USED_SO_FAR);
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbtTags, @NotNull HolderLookup.Provider provider) {
        super.saveAdditional(nbtTags, provider);
        nbtTags.putInt(SerializationConstants.USED_SO_FAR, usedSoFar);
    }

    public long getEnergyUsage() {
        return getActive() ? energyContainer.getEnergyPerTick() : 0L;
    }

}
