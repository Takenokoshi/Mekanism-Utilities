package com.takenokoshi.mekut.blockentity.normalmachine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekut.blockentity.base.BEMultiScaledProgressMachine;
import com.takenokoshi.mekut.blockentity.interfaces.IHasGuiSizeOffset;
import com.takenokoshi.mekut.blockentity.interfaces.IHasMachineEnergyContainer;
import com.takenokoshi.mekut.blockentity.interfaces.machine.IItemStackListFluidChemicalToItemFluidChemicalRecipeMachine;
import com.takenokoshi.mekut.capabilities.energy.VariableUsageMachineEnergyContainer;
import com.takenokoshi.mekut.core.EjectorComponentUtils;
import com.takenokoshi.mekut.recipe.cached.ICachedRecipe;
import com.takenokoshi.mekut.recipe.cached.ItemStackListFluidChemicalToItemFluidChemicalCachedRecipe;
import com.takenokoshi.mekut.recipe.input.ItemStackListInputHandler;
import com.takenokoshi.mekut.recipe.inputcache.ItemStackListFluidChemicalInputRecipeCache;
import com.takenokoshi.mekut.recipe.output.ItemOutputHandler;
import com.takenokoshi.mekut.recipe.recipe.prefab.ItemStackListFluidChemicalToItemFluidChemicalRecipe;
import com.takenokoshi.mekut.recipe.type.IMekUtRecipeTypeProvider;
import com.takenokoshi.mekut.registries.MekUtRecipeTypes;

import mekanism.api.AutomationType;
import mekanism.api.IContentsListener;
import mekanism.api.RelativeSide;
import mekanism.api.chemical.BasicChemicalTank;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.chemical.attribute.ChemicalAttributeValidator;
import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.api.inventory.IInventorySlot;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.inputs.InputHelper;
import mekanism.api.recipes.outputs.IOutputHandler;
import mekanism.api.recipes.outputs.OutputHelper;
import mekanism.common.capabilities.energy.MachineEnergyContainer;
import mekanism.common.capabilities.fluid.BasicFluidTank;
import mekanism.common.capabilities.holder.chemical.ChemicalTankHelper;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import mekanism.common.capabilities.holder.energy.EnergyContainerHelper;
import mekanism.common.capabilities.holder.energy.IEnergyContainerHolder;
import mekanism.common.capabilities.holder.fluid.FluidTankHelper;
import mekanism.common.capabilities.holder.fluid.IFluidTankHolder;
import mekanism.common.capabilities.holder.slot.IInventorySlotHolder;
import mekanism.common.capabilities.holder.slot.InventorySlotHelper;
import mekanism.common.inventory.slot.EnergyInventorySlot;
import mekanism.common.inventory.slot.FluidInventorySlot;
import mekanism.common.inventory.slot.InputInventorySlot;
import mekanism.common.inventory.slot.OutputInventorySlot;
import mekanism.common.inventory.slot.chemical.ChemicalInventorySlot;
import mekanism.common.inventory.warning.WarningTracker.WarningType;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.tile.component.config.DataType;
import mekanism.common.tile.component.config.slot.InventorySlotInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;

public class BESmallDigitalReactionChamber
        extends BEMultiScaledProgressMachine<ItemStackListFluidChemicalToItemFluidChemicalRecipe>
        implements IItemStackListFluidChemicalToItemFluidChemicalRecipeMachine, IHasMachineEnergyContainer,
        IHasGuiSizeOffset {

    private InputInventorySlot[] inputSlots;
    private OutputInventorySlot outputSlot;
    private EnergyInventorySlot energySlot;

    private FluidInventorySlot fluidInputSlot;
    private ChemicalInventorySlot chemicalInputSlot;
    private FluidInventorySlot fluidOutputSlot;
    private ChemicalInventorySlot chemicalOutputSlot;
    private OutputInventorySlot fluidInputReturnSlot;
    private OutputInventorySlot fluidOutputReturnSlot;

    private IExtendedFluidTank inputFluidTank;
    private IChemicalTank inputChemicalTank;
    private IExtendedFluidTank outputFluidTank;
    private IChemicalTank outputChemicalTank;
    private VariableUsageMachineEnergyContainer<?> energyContainer;

    private final ItemStackListInputHandler itemInputHandler;
    private final IInputHandler<FluidStack> fluidInputHandler;
    private final IInputHandler<ChemicalStack> chemicalInputHandler;
    private final IOutputHandler<ItemStack> itemOutputHandler;
    private final IOutputHandler<FluidStack> fluidOutputHandler;
    private final IOutputHandler<ChemicalStack> chemicalOutputHandler;

    public BESmallDigitalReactionChamber(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state, TRACKED_ERROR_TYPES, 100,
                ItemStackListFluidChemicalToItemFluidChemicalRecipe::getDuration, 2);
        configComponent.setupItemIOConfig(List.<IInventorySlot>of(inputSlots), List.<IInventorySlot>of(outputSlot),
                energySlot, false).addSlotInfo(DataType.EXTRA,
                        new InventorySlotInfo(true, true, List.<IInventorySlot>of(fluidInputSlot, chemicalInputSlot,
                                fluidOutputSlot, chemicalOutputSlot, fluidInputReturnSlot, fluidOutputReturnSlot)));
        configComponent.setupIOConfig(TransmissionType.FLUID, inputFluidTank, outputFluidTank, RelativeSide.RIGHT,
                false);
        configComponent.setupIOConfig(TransmissionType.CHEMICAL, inputChemicalTank, outputChemicalTank,
                RelativeSide.RIGHT, false);
        configComponent.setupInputConfig(TransmissionType.ENERGY, energyContainer);
        ejectorComponent = new TileComponentEjector(this).setOutputData(configComponent, new TransmissionType[] {
                TransmissionType.ITEM, TransmissionType.FLUID, TransmissionType.CHEMICAL
        });
        EjectorComponentUtils.setCanFluidTankEject(ejectorComponent,
                (type, tank) -> type.canOutput() && tank == outputFluidTank);
        EjectorComponentUtils.setCanChemicalTankEject(ejectorComponent,
                (type, tank) -> type.canOutput() && tank == outputChemicalTank);
        this.itemInputHandler = new ItemStackListInputHandler(List.of(inputSlots), RecipeError.NOT_ENOUGH_INPUT);
        this.fluidInputHandler = InputHelper.getInputHandler(inputFluidTank, RecipeError.NOT_ENOUGH_INPUT);
        this.chemicalInputHandler = InputHelper.getInputHandler(inputChemicalTank, RecipeError.NOT_ENOUGH_INPUT);
        this.itemOutputHandler = new ItemOutputHandler(outputSlot, RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
        this.fluidOutputHandler = OutputHelper.getOutputHandler(outputFluidTank, RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
        this.chemicalOutputHandler = OutputHelper.getOutputHandler(outputChemicalTank,
                RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
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
    protected @NotNull IInventorySlotHolder getInitialInventory(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        InventorySlotHelper builder = InventorySlotHelper.forSideWithConfig(this);
        inputSlots = new InputInventorySlot[9];
        for (int index = 0; index < 9; index++) {
            int value = index;
            builder.addSlot(inputSlots[index] = InputInventorySlot.at(
                    stack -> containsRecipeItemOther(stack, value, getItemsInOtherSlots(value),
                            inputFluidTank.getFluid(), inputChemicalTank.getStack()),
                    stack -> containsRecipeItem(stack, value),
                    recipeCacheListener, 54 + index % 3 * 18, 22 + index / 3 * 18))
                    .tracksWarnings(warning -> warning.warning(WarningType.NO_MATCHING_RECIPE,
                            getWarningCheck(RecipeError.NOT_ENOUGH_INPUT)));
        }
        builder.addSlot(fluidInputSlot = FluidInventorySlot.fill(inputFluidTank,
                listener, 06, 58));
        builder.addSlot(chemicalInputSlot = ChemicalInventorySlot.fillOrConvert(inputChemicalTank, this::getLevel,
                listener, 29, 58));
        builder.addSlot(chemicalOutputSlot = ChemicalInventorySlot.drain(outputChemicalTank,
                listener, 177, 58));
        builder.addSlot(fluidOutputSlot = FluidInventorySlot.drain(outputFluidTank,
                listener, 200, 58));
        builder.addSlot(fluidInputReturnSlot = OutputInventorySlot.at(listener, 06, 89));
        builder.addSlot(fluidOutputReturnSlot = OutputInventorySlot.at(listener, 200, 89));
        builder.addSlot(outputSlot = OutputInventorySlot.at(recipeCacheUnpauseListener, 152, 40))
                .tracksWarnings(warning -> warning.warning(WarningType.NO_SPACE_IN_OUTPUT,
                        getWarningCheck(RecipeError.NOT_ENOUGH_OUTPUT_SPACE)));
        builder.addSlot(energySlot = EnergyInventorySlot.fillOrConvert(energyContainer, this::getLevel,
                listener, 200, 4));
        return builder.build();
    }

    private List<ItemStack> getItemsInOtherSlots(int slotIndex) {
        List<ItemStack> result = new ArrayList<>();
        for (int index = 0; index < 9; index++) {
            if (index != slotIndex) {
                ItemStack stack = inputSlots[index].getStack();
                if (!stack.isEmpty()) {
                    result.add(stack);
                }
            }
        }
        return result;
    }

    private List<ItemStack> getItems() {
        return Arrays.stream(inputSlots).map(IInventorySlot::getStack).filter(stack -> !stack.isEmpty()).toList();
    }

    @Override
    protected @NotNull IFluidTankHolder getInitialFluidTanks(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        FluidTankHelper builder = FluidTankHelper.forSideWithConfig(this);
        builder.addTank(inputFluidTank = BasicFluidTank.input(20000,
                stack -> containsRecipeFluidOther(getItems(), stack, inputChemicalTank.getStack()),
                this::containsRecipeFluid, recipeCacheListener));
        builder.addTank(outputFluidTank = BasicFluidTank.output(20000, recipeCacheUnpauseListener));
        return builder.build();
    }

    @Override
    protected @Nullable IChemicalTankHolder getInitialChemicalTanks(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        ChemicalTankHelper builder = ChemicalTankHelper.forSideWithConfig(this);
        builder.addTank(inputChemicalTank = BasicChemicalTank.create(200000,
                (stack, type) -> type == AutomationType.MANUAL,
                (stack, type) -> containsRecipeChemicalOther(getItems(), inputFluidTank.getFluid(), stack.getStack(1)),
                chemical -> containsRecipeChemical(chemical.getStack(1)),
                ChemicalAttributeValidator.ALWAYS_ALLOW,
                recipeCacheUnpauseListener));
        builder.addTank(outputChemicalTank = BasicChemicalTank.output(200000, recipeCacheUnpauseListener));
        return builder.build();
    }

    @Override
    public int getExtraWidth() {
        return 60;
    }

    @Override
    protected boolean onUpdateServer() {
        boolean value = super.onUpdateServer();
        fluidOutputSlot.drainTank(fluidOutputReturnSlot);
        chemicalOutputSlot.drainTank();
        clientEnergyUsed = recipeCacheLookupMonitor.updateAndProcess(energyContainer);
        fluidInputSlot.fillTank(fluidInputReturnSlot);
        chemicalInputSlot.fillTankOrConvert();
        energySlot.fillContainerOrConvert();
        return value;
    }

    @Override
    public @NotNull IMekUtRecipeTypeProvider<?, ItemStackListFluidChemicalToItemFluidChemicalRecipe, ItemStackListFluidChemicalInputRecipeCache<ItemStackListFluidChemicalToItemFluidChemicalRecipe>> getRecipeType() {
        return MekUtRecipeTypes.SMALL_DIGITAL_REACTION_CHAMBER;
    }

    @Override
    public @Nullable ItemStackListFluidChemicalToItemFluidChemicalRecipe getRecipe(int cacheIndex) {
        return findFirstRecipe(itemInputHandler, fluidInputHandler, chemicalInputHandler);
    }

    @Override
    public @NotNull ICachedRecipe<ItemStackListFluidChemicalToItemFluidChemicalRecipe> createNewCachedRecipe(
            @NotNull ItemStackListFluidChemicalToItemFluidChemicalRecipe recipe, int cacheIndex) {
        return new ItemStackListFluidChemicalToItemFluidChemicalCachedRecipe(recipe, recheckAllRecipeErrors,
                itemInputHandler, fluidInputHandler, chemicalInputHandler, itemOutputHandler, fluidOutputHandler,
                chemicalOutputHandler)
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
    public void onCachedRecipeChanged(
            @Nullable ICachedRecipe<ItemStackListFluidChemicalToItemFluidChemicalRecipe> cachedRecipe, int cacheIndex) {
        super.onCachedRecipeChanged(cachedRecipe, cacheIndex);
        if (cachedRecipe != null) {
            energyContainer.updateAdditionalUsage(cachedRecipe.getRecipe().energyRequired);
        }
    }

    @Override
    public MachineEnergyContainer<?> getEnergyContainer() {
        return energyContainer;
    }

    @Override
    public IExtendedFluidTank getInputFluidTank() {
        return inputFluidTank;
    }

    @Override
    public IChemicalTank getInputChemicalTank() {
        return inputChemicalTank;
    }

    @Override
    public IExtendedFluidTank getOutputFluidTank() {
        return outputFluidTank;
    }

    @Override
    public IChemicalTank getOutputChemicalTank() {
        return outputChemicalTank;
    }

}
