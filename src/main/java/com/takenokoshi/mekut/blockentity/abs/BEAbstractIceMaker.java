package com.takenokoshi.mekut.blockentity.abs;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekaddonlib.blockentity.base.BEMultiScaledProgressMachine;
import com.takenokoshi.mekaddonlib.inventory.slot.LimitChangedOutputInventorySlot;
import com.takenokoshi.mekaddonlib.recipe.cached.ICachedRecipe;
import com.takenokoshi.mekaddonlib.recipe.type.IMekALRecipeTypeProvider;
import com.takenokoshi.mekut.blockentity.interfaces.IHasMachineEnergyContainer;
import com.takenokoshi.mekut.blockentity.interfaces.IRecipeViewerTypeProvider;
import com.takenokoshi.mekut.blockentity.interfaces.machine.IFluidToObjectMachine;
import com.takenokoshi.mekut.inventory.slot.FluidFillOrSupplierSlot;
import com.takenokoshi.mekut.recipe.cached.FluidToItemCachedRecipe;
import com.takenokoshi.mekut.recipe.input.AdvancedFluidInputHadler;
import com.takenokoshi.mekut.recipe.inputcache.MUSingleInputRecipeCache;
import com.takenokoshi.mekut.recipe.recipe.prefab.FluidToItemRecipe;
import com.takenokoshi.mekut.recipe_viewer.type.MekUtRecipeViewerRecipeType;
import com.takenokoshi.mekut.registries.MekUtRecipeTypes;

import mekanism.api.IContentsListener;
import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.outputs.IOutputHandler;
import mekanism.api.recipes.outputs.OutputHelper;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
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
import mekanism.common.inventory.slot.OutputInventorySlot;
import mekanism.common.inventory.warning.WarningTracker.WarningType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BEAbstractIceMaker extends BEMultiScaledProgressMachine<FluidToItemRecipe>
        implements IFluidToObjectMachine<FluidToItemRecipe>, IHasMachineEnergyContainer, IRecipeViewerTypeProvider {

    private FluidFillOrSupplierSlot inputSlot;
    private OutputInventorySlot fluidReturnSlot;
    private LimitChangedOutputInventorySlot outputSlot;
    private EnergyInventorySlot energySlot;

    private IExtendedFluidTank inputTank;
    private MachineEnergyContainer<?> energyContainer;

    private final AdvancedFluidInputHadler inputHandler;
    private final IOutputHandler<ItemStack> outputHandler;

    protected BEAbstractIceMaker(Holder<Block> blockProvider, BlockPos pos, BlockState state, int baselineMaxOperations) {
        super(blockProvider, pos, state, TRACKED_ERROR_TYPES, baselineMaxOperations, r -> 200);
        ejectorComponent = IFluidToObjectMachine.setUpToItemConfig(this, configComponent, inputSlot, outputSlot,
                fluidReturnSlot, energySlot, inputTank, energyContainer);
        this.inputHandler = AdvancedFluidInputHadler.create(inputTank, RecipeError.NOT_ENOUGH_INPUT);
        this.outputHandler = OutputHelper.getOutputHandler(outputSlot, RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
        inputSlot.setSupplyingStackSetter(inputHandler::setSuppliedStack);
    }

    @Override
    protected IEnergyContainerHolder getInitialEnergyContainers(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        EnergyContainerHelper builder = EnergyContainerHelper.forSideWithConfig(this);
        builder.addContainer(energyContainer = MachineEnergyContainer.input(this, recipeCacheUnpauseListener));
        return builder.build();
    }

    @Override
    protected @Nullable IInventorySlotHolder getInitialInventory(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        InventorySlotHelper builder = InventorySlotHelper.forSideWithConfig(this);
        builder.addSlot(inputSlot = FluidFillOrSupplierSlot.create(inputTank, recipeCacheListener, 8, 34))
                .setSlotOverlay(SlotOverlay.MINUS);
        builder.addSlot(fluidReturnSlot = OutputInventorySlot.at(listener, 8, 65));
        builder.addSlot(outputSlot = LimitChangedOutputInventorySlot.at(recipeCacheUnpauseListener,
                129, 57, initItemSlotCapacity()))
                .tracksWarnings(warning -> warning.warning(WarningType.NO_SPACE_IN_OUTPUT,
                        getWarningCheck(RecipeError.NOT_ENOUGH_OUTPUT_SPACE)));
        builder.addSlot(energySlot = EnergyInventorySlot.fillOrConvert(energyContainer, this::getLevel, listener,
                152, 5));
        return builder.build();
    }

    protected abstract int initItemSlotCapacity();

    @Override
    protected @Nullable IFluidTankHolder getInitialFluidTanks(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        FluidTankHelper builder = FluidTankHelper.forSideWithConfig(this);
        builder.addTank(inputTank = BasicFluidTank.input(initFluidTankCapacity(), this::containsRecipe, recipeCacheListener));
        return builder.build();
    }

    protected abstract int initFluidTankCapacity();

    @Override
    protected boolean onUpdateServer() {
        boolean value = super.onUpdateServer();
        clientEnergyUsed = recipeCacheLookupMonitor.updateAndProcess(energyContainer);
        inputSlot.fillTank(fluidReturnSlot);
        energySlot.fillContainerOrConvert();
        return value;
    }

    @Override
    public @Nullable FluidToItemRecipe getRecipe(int cacheIndex) {
        return findFirstRecipe(inputHandler);
    }

    @Override
    public @NotNull ICachedRecipe<FluidToItemRecipe> createNewCachedRecipe(@NotNull FluidToItemRecipe recipe,
            int cacheIndex) {
        return new FluidToItemCachedRecipe(recipe, recheckAllRecipeErrors, inputHandler, outputHandler)
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
    public @NotNull IMekALRecipeTypeProvider<?, FluidToItemRecipe, MUSingleInputRecipeCache.MUSingleFluid<FluidToItemRecipe>> getRecipeType() {
        return MekUtRecipeTypes.ICE_MAKING;
    }

    @Override
    public IExtendedFluidTank getInputTank() {
        return inputTank;
    }

    @Override
    public MachineEnergyContainer<?> getEnergyContainer() {
        return energyContainer;
    }

    @Override
    public @Nullable IRecipeViewerRecipeType<FluidToItemRecipe> recipeViewerType() {
        return MekUtRecipeViewerRecipeType.ICE_MAKING;
    }

}
