package takenokoshi.mekal.common.tile.prefab;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import mekanism.api.IContentsListener;
import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
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
import mekanism.common.inventory.slot.OutputInventorySlot;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.recipe.lookup.ISingleRecipeLookupHandler.FluidRecipeLookupHandler;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.tile.prefab.TileEntityProgressMachine;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidType;
import takenokoshi.mekal.api.recipes.FluidToItemStackRecipe;
import takenokoshi.mekal.api.recipes.cache.MekALOneInputCachedRecipe;
import takenokoshi.mekal.api.recipes.inputs.AdvancedFluidInputHandler;
import takenokoshi.mekal.common.inventory.slot.AdvancedFluidInputSlot;

public abstract class TileEntityFluidToItemStackMachine extends TileEntityProgressMachine<FluidToItemStackRecipe>
        implements
        FluidRecipeLookupHandler<FluidToItemStackRecipe> {

    private static final List<RecipeError> TRACKED_ERROR_TYPES = List.of(
            RecipeError.NOT_ENOUGH_ENERGY,
            RecipeError.NOT_ENOUGH_INPUT,
            RecipeError.NOT_ENOUGH_OUTPUT_SPACE,
            RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT);

    public static final int MAX_FLUID = 10 * FluidType.BUCKET_VOLUME;
    public static final int BASE_TICKS_REQUIRED = 5 * SharedConstants.TICKS_PER_SECOND;

    public IExtendedFluidTank fluidTank;
    private final IOutputHandler<ItemStack> outputHandler;
    private final AdvancedFluidInputHandler inputHandler;

    private MachineEnergyContainer<TileEntityFluidToItemStackMachine> energyContainer;
    private FluidInventorySlot inputSlot;
    private OutputInventorySlot outputSlot, bucketSlot;
    private EnergyInventorySlot energySlot;

    protected TileEntityFluidToItemStackMachine(Holder<Block> blockProvider, BlockPos pos, BlockState state,
            int baseTicksRequired) {
        super(blockProvider, pos, state, TRACKED_ERROR_TYPES, baseTicksRequired);
        configComponent.setupItemIOExtraConfig(inputSlot, outputSlot, bucketSlot, energySlot);
        configComponent.setupInputConfig(TransmissionType.FLUID, fluidTank);
        configComponent.setupInputConfig(TransmissionType.ENERGY, energyContainer);
        ejectorComponent = new TileComponentEjector(this);
        ejectorComponent.setOutputData(configComponent, TransmissionType.ITEM);

        inputHandler = AdvancedFluidInputHandler.create(fluidTank, RecipeError.NOT_ENOUGH_INPUT);
        outputHandler = OutputHelper.getOutputHandler(outputSlot, RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
    }

    @NotNull
    @Override
    protected IEnergyContainerHolder getInitialEnergyContainers(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        EnergyContainerHelper builder = EnergyContainerHelper.forSideWithConfig(this);
        builder.addContainer(energyContainer = MachineEnergyContainer.input(this, recipeCacheUnpauseListener));
        return builder.build();
    }

    @Override
    protected @Nullable IFluidTankHolder getInitialFluidTanks(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        FluidTankHelper builder = FluidTankHelper.forSideWithConfig(this);
        builder.addTank(fluidTank = BasicFluidTank.input(getInitialFluidTankCapacity(), this::containsRecipe,
                recipeCacheListener));
        return builder.build();
    }

    protected int getInitialFluidTankCapacity() {
        return MAX_FLUID;
    }

    @Override
    protected @Nullable IInventorySlotHolder getInitialInventory(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        InventorySlotHelper builder = InventorySlotHelper.forSideWithConfig(this);
        builder.addSlot(inputSlot = AdvancedFluidInputSlot.create(fluidTank, () -> {
            listener.onContentsChanged();
            this.onInputSlotChanged();
        }, 8, 34));
        builder.addSlot(outputSlot = OutputInventorySlot.at(recipeCacheUnpauseListener, 129, 57));
        builder.addSlot(bucketSlot = OutputInventorySlot.at(listener, 8, 65));
        builder.addSlot(
                energySlot = EnergyInventorySlot.fillOrConvert(energyContainer, this::getLevel, listener, 152, 5));
        inputSlot.setSlotOverlay(SlotOverlay.PLUS);
        return builder.build();
    }

    private void onInputSlotChanged() {
        inputHandler.setSuppliedByItem(inputSlot.getStack());
    }

    @Override
    protected boolean onUpdateServer() {
        boolean sendUpdatePacket = super.onUpdateServer();
        energySlot.fillContainerOrConvert();
        inputSlot.fillTank(bucketSlot);
        recipeCacheLookupMonitor.updateAndProcess();
        return sendUpdatePacket;
    }

    public int getEnergySlotX() {
        return energySlot.getGuiX();
    }

    @Override
    public @Nullable FluidToItemStackRecipe getRecipe(int arg0) {
        return findFirstRecipe(inputHandler);
    }

    @Override
    public @NotNull CachedRecipe<FluidToItemStackRecipe> createNewCachedRecipe(@NotNull FluidToItemStackRecipe recipe,
            int arg1) {
        return MekALOneInputCachedRecipe.fluidToItem(recipe, recheckAllRecipeErrors, inputHandler, outputHandler)
                .setErrorsChanged(this::onErrorsChanged)
                .setCanHolderFunction(this::canFunction)
                .setActive(this::setActive)
                .setEnergyRequirements(energyContainer::getEnergyPerTick, energyContainer)
                .setRequiredTicks(this::getTicksRequired)
                .setOnFinish(this::markForSave)
                .setOperatingTicksChanged(this::setOperatingTicks)
                .setBaselineMaxOperations(this::getOperationsPerTick);
    }

    public MachineEnergyContainer<TileEntityFluidToItemStackMachine> getEnergyContainer() {
        return energyContainer;
    }

    public IExtendedFluidTank getFluidTank() {
        return fluidTank;
    }

    public long getEnergyUsage() {
        return getActive() ? energyContainer.getEnergyPerTick() : 0L;
    }

}
