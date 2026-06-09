package com.takenokoshi.mekut.blockentity.standardmachine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.glodblock.github.extendedae.recipe.CrystalAssemblerRecipe;
import com.takenokoshi.mekut.blockentity.base.BEMultiScaledProgressMachine;
import com.takenokoshi.mekut.blockentity.interfaces.IMekStyledCrystalAssembler;
import com.takenokoshi.mekut.core.MekUtMathUtils;
import com.takenokoshi.mekut.recipe.cached.AbstractCachedRecipe;
import com.takenokoshi.mekut.recipe.cached.MekStyledCrystalAssemblerCachedRecipe;
import com.takenokoshi.mekut.recipe.input.ItemStackListInputHandler;
import com.takenokoshi.mekut.recipe.output.ItemOutputHandler;

import mekanism.api.IContentsListener;
import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.api.inventory.IInventorySlot;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.inputs.InputHelper;
import mekanism.common.capabilities.energy.MachineEnergyContainer;
import mekanism.common.capabilities.fluid.BasicFluidTank;
import mekanism.common.capabilities.holder.energy.EnergyContainerHelper;
import mekanism.common.capabilities.holder.energy.IEnergyContainerHolder;
import mekanism.common.capabilities.holder.fluid.FluidTankHelper;
import mekanism.common.capabilities.holder.fluid.IFluidTankHolder;
import mekanism.common.capabilities.holder.slot.IInventorySlotHolder;
import mekanism.common.capabilities.holder.slot.InventorySlotHelper;
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

public class BEStandardMekStyledCrystalAssembler extends BEMultiScaledProgressMachine<CrystalAssemblerRecipe>
        implements IMekStyledCrystalAssembler {

    private List<IInventorySlot> inputSlots;
    private OutputInventorySlot outputSlot;
    private BasicFluidTank inputTank;
    private EnergyInventorySlot energySlot;
    private MachineEnergyContainer<?> energyContainer;
    private final ItemStackListInputHandler itemInputHandler;
    private final IInputHandler<FluidStack> fluidInputHandler;
    private final ItemOutputHandler itemOutputHandler;

    public BEStandardMekStyledCrystalAssembler(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state, TRACKED_ERROR_TYPES,
                MekUtMathUtils.getTicksAccelerated(100, 6),
                r -> MekUtMathUtils.getTicksAccelerated(100, 6),
                MekUtMathUtils.getBaselineAccelerated(100, 6));
        configComponent.setupItemIOConfig(inputSlots, List.<IInventorySlot>of(outputSlot), energySlot, false);
        configComponent.setupInputConfig(TransmissionType.ENERGY, energyContainer);
        configComponent.setupInputConfig(TransmissionType.FLUID, inputTank);
        ejectorComponent = new TileComponentEjector(this)
                .setOutputData(configComponent, TransmissionType.ITEM);
        this.itemInputHandler = new ItemStackListInputHandler(inputSlots, RecipeError.NOT_ENOUGH_INPUT);
        this.fluidInputHandler = InputHelper.getInputHandler(inputTank, RecipeError.NOT_ENOUGH_INPUT);
        this.itemOutputHandler = new ItemOutputHandler(outputSlot, RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
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
        builder.addContainer(energyContainer = MachineEnergyContainer.input(this, recipeCacheUnpauseListener));
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
        builder.addTank(inputTank = BasicFluidTank.input(4000000,
                stack -> containsRecipeFluidOther(getStackInSlots(), stack),
                this::containsRecipeFluid,
                recipeCacheListener));
        return builder.build();
    }

    @Override
    public @Nullable CrystalAssemblerRecipe getRecipe(int cacheIndex) {
        return findFirstRecipe(itemInputHandler, fluidInputHandler);
    }

    @Override
    public @NotNull AbstractCachedRecipe<CrystalAssemblerRecipe> createNewCachedRecipe(
            @NotNull CrystalAssemblerRecipe recipe, int cacheIndex) {
        return new MekStyledCrystalAssemblerCachedRecipe(recipe, recheckAllRecipeErrors, itemInputHandler,
                fluidInputHandler, itemOutputHandler)
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

}
