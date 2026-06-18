package com.takenokoshi.mekut.blockentity.normalmachine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekut.blockentity.base.BEMultiScaledProgressMachine;
import com.takenokoshi.mekut.blockentity.interfaces.machine.IMekStyledReactionChamber;
import com.takenokoshi.mekut.capabilities.energy.VariableUsageMachineEnergyContainer;
import com.takenokoshi.mekut.core.EjectorComponentUtils;
import com.takenokoshi.mekut.recipe.cached.AbstractCachedRecipe;
import com.takenokoshi.mekut.recipe.cached.MekStyledReactionChamberCachedRecipe;
import com.takenokoshi.mekut.recipe.input.ItemStackListInputHandler;
import com.takenokoshi.mekut.recipe.output.ItemOutputHandler;

import mekanism.api.IContentsListener;
import mekanism.api.RelativeSide;
import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.api.inventory.IInventorySlot;
import mekanism.api.math.MathUtils;
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
import mekanism.common.config.MekanismConfig;
import mekanism.common.inventory.container.MekanismContainer;
import mekanism.common.inventory.slot.EnergyInventorySlot;
import mekanism.common.inventory.slot.InputInventorySlot;
import mekanism.common.inventory.slot.OutputInventorySlot;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.tile.component.TileComponentEjector;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;
import net.pedroksl.advanced_ae.recipes.ReactionChamberRecipe;

public class BEMekStyledReactionChamber extends BEMultiScaledProgressMachine<ReactionChamberRecipe>
        implements IMekStyledReactionChamber {

    private List<IInventorySlot> inputSlots;
    private OutputInventorySlot outputSlot;

    private BasicFluidTank inputTank;
    private BasicFluidTank outputTank;

    private EnergyInventorySlot energySlot;
    private VariableUsageMachineEnergyContainer<?> energyContainer;

    private final ItemStackListInputHandler itemInputHandler;
    private final IInputHandler<FluidStack> fluidInputHandler;
    private final ItemOutputHandler itemOutputHandler;
    private final IOutputHandler<FluidStack> fluidOutputHandler;

    public BEMekStyledReactionChamber(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state, TRACKED_ERROR_TYPES, 100, r -> 100, 1);
        configComponent.setupItemIOConfig(inputSlots, List.<IInventorySlot>of(outputSlot), energySlot, false);
        configComponent.setupInputConfig(TransmissionType.ENERGY, energyContainer);
        configComponent.setupIOConfig(TransmissionType.FLUID, inputTank, outputTank, RelativeSide.RIGHT);
        ejectorComponent = new TileComponentEjector(this, () -> 0, () -> 1000)
                .setOutputData(configComponent, TransmissionType.ITEM, TransmissionType.FLUID);
        EjectorComponentUtils.setCanFluidTankEject(ejectorComponent,
                (type, tank) -> type.canOutput() && tank == outputTank);
        this.itemInputHandler = new ItemStackListInputHandler(inputSlots, RecipeError.NOT_ENOUGH_INPUT);
        this.fluidInputHandler = InputHelper.getInputHandler(inputTank, RecipeError.NOT_ENOUGH_INPUT);
        this.itemOutputHandler = new ItemOutputHandler(outputSlot, RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
        this.fluidOutputHandler = OutputHelper.getOutputHandler(outputTank, RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
    }

    protected List<ItemStack> getStackInSlots() {
        return inputSlots.stream().map(IInventorySlot::getStack).filter(stack -> !stack.isEmpty()).toList();
    }

    protected List<ItemStack> getStackInSlots(int exceptSlotIndex) {
        List<ItemStack> list = new ArrayList<>();
        for (int index = 0; index < inputSlots.size(); index++) {
            if (index != exceptSlotIndex && !inputSlots.get(index).isEmpty()) {
                list.add(inputSlots.get(index).getStack());
            }
        }
        return Collections.unmodifiableList(list);
    }

    @Override
    protected IEnergyContainerHolder getInitialEnergyContainers(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        EnergyContainerHelper builder = EnergyContainerHelper.forSideWithConfig(this);
        builder.addContainer(
                energyContainer = VariableUsageMachineEnergyContainer.input(this, recipeCacheUnpauseListener));
        return builder.build();
    }

    @Override
    protected IInventorySlotHolder getInitialInventory(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        InventorySlotHelper builder = InventorySlotHelper.forSideWithConfig(this);
        List<IInventorySlot> slots = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            int slotIndex = i;
            slots.add(builder.addSlot(InputInventorySlot.at(
                    stack -> containsRecipeItemOther(stack, slotIndex, getStackInSlots(slotIndex), inputTank.getFluid()),
                    stack -> containsRecipeItem(stack, slotIndex),
                    recipeCacheListener, slotIndex % 3 * 18 + 28, slotIndex / 3 * 18 + 17)));
        }
        inputSlots = Collections.unmodifiableList(slots);
        builder.addSlot(outputSlot = OutputInventorySlot.at(recipeCacheUnpauseListener, 116, 35));
        builder.addSlot(
                energySlot = EnergyInventorySlot.fillOrConvert(energyContainer, this::getLevel, listener, 116, 17));
        return builder.build();
    }

    protected @Nullable IFluidTankHolder getInitialFluidTanks(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        FluidTankHelper builder = FluidTankHelper.forSideWithConfig(this);
        builder.addTank(inputTank = BasicFluidTank.input(40000,
                stack -> containsRecipeFluidOther(getStackInSlots(), stack),
                this::containsRecipeFluid,
                recipeCacheListener));
        builder.addTank(outputTank = BasicFluidTank.output(40000, recipeCacheUnpauseListener));
        return builder.build();
    }

    @Override
    public @Nullable ReactionChamberRecipe getRecipe(int cacheIndex) {
        return findFirstRecipe(itemInputHandler, fluidInputHandler);
    }

    @Override
    public @NotNull AbstractCachedRecipe<ReactionChamberRecipe> createNewCachedRecipe(
            @NotNull ReactionChamberRecipe recipe, int cacheIndex) {
        return new MekStyledReactionChamberCachedRecipe(recipe, recheckAllRecipeErrors, itemInputHandler,
                fluidInputHandler, itemOutputHandler, fluidOutputHandler)
                .setErrorsChanged(this::onErrorsChanged)
                .setCanHolderFunction(this::canFunction)
                .setActive(this::setActive)
                .setEnergyRequirements(energyContainer::getEnergyPerTick, energyContainer)
                .setRequiredTicks(this::getTicksRequired)
                .setOnFinish(this::markForSave)
                .setOperatingTicksChanged(this::setOperatingTicks)
                .setBaselineMaxOperations(this::getOperationsPerTick);
    }

    protected boolean onUpdateServer() {
        boolean v = super.onUpdateServer();
        energySlot.fillContainerOrConvert();
        clientEnergyUsed = recipeCacheLookupMonitor.updateAndProcess(energyContainer);
        return v;
    }

    @Override
    public MachineEnergyContainer<?> getEnergyContainer() {
        return energyContainer;
    }

    @Override
    public IExtendedFluidTank getInputTank() {
        return inputTank;
    }

    @Override
    public IExtendedFluidTank getOutputTank() {
        return outputTank;
    }

    @Override
    public void addContainerTrackers(MekanismContainer container) {
        super.addContainerTrackers(container);
        energyContainer.track(container);
    }

    public void onCachedRecipeChanged(@Nullable AbstractCachedRecipe<ReactionChamberRecipe> cachedRecipe,
            int cacheIndex) {
        super.onCachedRecipeChanged(cachedRecipe, cacheIndex);
        energyContainer.updateAdditionalUsage(MathUtils.clampToLong(
                MekanismConfig.general.forgeConversionRate.getAsDouble() * cachedRecipe.getRecipe().getEnergy()));
    }

}
