package com.takenokoshi.mekut.blockentity.abs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekaddonlib.blockentity.base.BEMultiScaledProgressMachine;
import com.takenokoshi.mekaddonlib.blockentity.interfaces.IHasGuiSizeOffset;
import com.takenokoshi.mekaddonlib.inventory.slot.LimitChangedInputInventorySlot;
import com.takenokoshi.mekaddonlib.inventory.slot.LimitChangedOutputInventorySlot;
import com.takenokoshi.mekaddonlib.recipe.cached.ICachedRecipe;
import com.takenokoshi.mekaddonlib.recipe.type.IMekALRecipeTypeProvider;
import com.takenokoshi.mekut.blockentity.interfaces.IHasMachineEnergyContainer;
import com.takenokoshi.mekut.blockentity.interfaces.IRecipeViewerTypeProvider;
import com.takenokoshi.mekut.blockentity.interfaces.machine.IItemStackListFluidChemicalToItemRecipeMachine;
import com.takenokoshi.mekut.inventory.slot.ChemicalFillConvertOrSupplyingSlot;
import com.takenokoshi.mekut.inventory.slot.FluidFillOrSupplierSlot;
import com.takenokoshi.mekut.recipe.cached.ItemStackListFluidChemicalToItemCachedRecipe;
import com.takenokoshi.mekut.recipe.input.AdvancedChemicalInputHandler;
import com.takenokoshi.mekut.recipe.input.AdvancedFluidInputHandler;
import com.takenokoshi.mekut.recipe.input.ItemStackListInputHandler;
import com.takenokoshi.mekut.recipe.inputcache.ItemStackListFluidChemicalInputRecipeCache;
import com.takenokoshi.mekut.recipe.output.ItemOutputHandler;
import com.takenokoshi.mekut.recipe.recipe.prefab.ItemStackListFluidChemicalToItemRecipe;
import com.takenokoshi.mekut.recipe_viewer.type.MekUtRecipeViewerRecipeType;
import com.takenokoshi.mekut.registries.MekUtRecipeTypes;

import mekanism.api.AutomationType;
import mekanism.api.IContentsListener;
import mekanism.api.chemical.BasicChemicalTank;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.chemical.attribute.ChemicalAttributeValidator;
import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.api.inventory.IInventorySlot;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.outputs.IOutputHandler;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
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
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.inventory.slot.EnergyInventorySlot;
import mekanism.common.inventory.slot.InputInventorySlot;
import mekanism.common.inventory.slot.OutputInventorySlot;
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

public abstract class BEAbstractSmallDigitalAssembler
        extends BEMultiScaledProgressMachine<ItemStackListFluidChemicalToItemRecipe>
        implements IItemStackListFluidChemicalToItemRecipeMachine, IHasMachineEnergyContainer, IHasGuiSizeOffset,
        IRecipeViewerTypeProvider {

    private InputInventorySlot[] inputSlots;
    private LimitChangedOutputInventorySlot outputSlot;
    private EnergyInventorySlot energySlot;

    private FluidFillOrSupplierSlot fluidInputSlot;
    private ChemicalFillConvertOrSupplyingSlot chemicalInputSlot;
    private OutputInventorySlot fluidReturnSlot;

    private IExtendedFluidTank inputFluidTank;
    private IChemicalTank inputChemicalTank;
    private MachineEnergyContainer<?> energyContainer;

    private final ItemStackListInputHandler itemInputHandler;
    private final AdvancedFluidInputHandler fluidInputHandler;
    private final AdvancedChemicalInputHandler chemicalInputHandler;
    private final IOutputHandler<ItemStack> outputHandler;

    protected BEAbstractSmallDigitalAssembler(Holder<Block> blockProvider, BlockPos pos, BlockState state,
            int baselineMaxOperations) {
        super(blockProvider, pos, state, TRACKED_ERROR_TYPES, baselineMaxOperations, r -> 100);
        configComponent.setupItemIOConfig(List.<IInventorySlot>of(inputSlots), List.<IInventorySlot>of(outputSlot),
                energySlot, false)
                .addSlotInfo(DataType.EXTRA, new InventorySlotInfo(true, true, List.<IInventorySlot>of(
                        fluidInputSlot, chemicalInputSlot, fluidReturnSlot)));
        configComponent.setupInputConfig(TransmissionType.FLUID, inputFluidTank);
        configComponent.setupInputConfig(TransmissionType.CHEMICAL, inputChemicalTank);
        configComponent.setupInputConfig(TransmissionType.ENERGY, energyContainer);
        ejectorComponent = new TileComponentEjector(this).setOutputData(configComponent,
                new TransmissionType[] { TransmissionType.ITEM });
        this.itemInputHandler = new ItemStackListInputHandler(List.of(inputSlots), RecipeError.NOT_ENOUGH_INPUT);
        this.fluidInputHandler = AdvancedFluidInputHandler.create(inputFluidTank, RecipeError.NOT_ENOUGH_INPUT);
        this.chemicalInputHandler = AdvancedChemicalInputHandler.create(inputChemicalTank,
                RecipeError.NOT_ENOUGH_INPUT);
        this.outputHandler = new ItemOutputHandler(outputSlot, RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
        fluidInputSlot.setSupplyingStackSetter(fluidInputHandler::setSuppliedStack);
        chemicalInputSlot.setSupplyingStackSetter(chemicalInputHandler::setSuppliedStack);
    }

    @Override
    protected IEnergyContainerHolder getInitialEnergyContainers(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        EnergyContainerHelper builder = EnergyContainerHelper.forSideWithConfig(this);
        builder.addContainer(energyContainer = MachineEnergyContainer.input(this, recipeCacheUnpauseListener));
        return builder.build();
    }

    @Override
    protected @NotNull IInventorySlotHolder getInitialInventory(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        InventorySlotHelper builder = InventorySlotHelper.forSideWithConfig(this);
        inputSlots = new InputInventorySlot[9];
        for (int index = 0; index < 9; index++) {
            int value = index;
            builder.addSlot(inputSlots[index] = LimitChangedInputInventorySlot.at(
                    stack -> containsRecipeItemOther(stack, value, getItemsInOtherSlots(value),
                            fluidInputHandler.getInput(), chemicalInputHandler.getInput()),
                    stack -> containsRecipeItem(stack, value),
                    recipeCacheListener, 54 + index % 3 * 18, 22 + index / 3 * 18, initItemSlotCapacity()))
                    .tracksWarnings(warning -> warning.warning(WarningType.NO_MATCHING_RECIPE,
                            getWarningCheck(RecipeError.NOT_ENOUGH_INPUT)));
        }
        builder.addSlot(fluidInputSlot = FluidFillOrSupplierSlot.create(inputFluidTank,
                recipeCacheListener, 06, 58))
                .setSlotOverlay(SlotOverlay.MINUS);
        builder.addSlot(chemicalInputSlot = ChemicalFillConvertOrSupplyingSlot.create(inputChemicalTank,
                this::getLevel, recipeCacheListener, 29, 58))
                .setSlotOverlay(SlotOverlay.MINUS);
        builder.addSlot(fluidReturnSlot = OutputInventorySlot.at(listener, 06, 89));
        builder.addSlot(outputSlot = LimitChangedOutputInventorySlot.at(recipeCacheUnpauseListener, 152, 40,
                initItemSlotCapacity()))
                .tracksWarnings(warning -> warning.warning(WarningType.NO_SPACE_IN_OUTPUT,
                        getWarningCheck(RecipeError.NOT_ENOUGH_OUTPUT_SPACE)));
        builder.addSlot(energySlot = EnergyInventorySlot.fillOrConvert(energyContainer, this::getLevel,
                listener, 177, 22));
        return builder.build();
    }

    protected abstract int initItemSlotCapacity();

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
        builder.addTank(inputFluidTank = BasicFluidTank.input(initFluidTankCapacity(),
                stack -> containsRecipeFluidOther(getItems(), stack, chemicalInputHandler.getInput()),
                this::containsRecipeFluid, recipeCacheListener));
        return builder.build();
    }

    protected abstract int initFluidTankCapacity();

    @Override
    protected @Nullable IChemicalTankHolder getInitialChemicalTanks(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        ChemicalTankHelper builder = ChemicalTankHelper.forSideWithConfig(this);
        builder.addTank(inputChemicalTank = BasicChemicalTank.create(initChemicalTankCapacity(),
                (stack, type) -> type != AutomationType.EXTERNAL,
                (stack, type) -> containsRecipeChemicalOther(getItems(), fluidInputHandler.getInput(), stack.getStack(1)),
                chemical -> containsRecipeChemical(chemical.getStack(1)),
                ChemicalAttributeValidator.ALWAYS_ALLOW,
                recipeCacheListener));
        return builder.build();
    }

    protected abstract long initChemicalTankCapacity();

    @Override
    public int getExtraWidth() {
        return 36;
    }

    @Override
    public int getExtraHeight() {
        return 5;
    }

    @Override
    protected boolean onUpdateServer() {
        boolean value = super.onUpdateServer();
        clientEnergyUsed = recipeCacheLookupMonitor.updateAndProcess(energyContainer);
        fluidInputSlot.fillTank(fluidReturnSlot);
        chemicalInputSlot.fillTankOrConvert();
        energySlot.fillContainerOrConvert();
        return value;
    }

    @Override
    public @NotNull IMekALRecipeTypeProvider<?, ItemStackListFluidChemicalToItemRecipe, ItemStackListFluidChemicalInputRecipeCache<ItemStackListFluidChemicalToItemRecipe>> getRecipeType() {
        return MekUtRecipeTypes.SMALL_DIGITAL_ASSEMBLER;
    }

    @Override
    public @Nullable ItemStackListFluidChemicalToItemRecipe getRecipe(int cacheIndex) {
        return findFirstRecipe(itemInputHandler, fluidInputHandler, chemicalInputHandler);
    }

    @Override
    public @NotNull ICachedRecipe<ItemStackListFluidChemicalToItemRecipe> createNewCachedRecipe(
            @NotNull ItemStackListFluidChemicalToItemRecipe recipe, int cacheIndex) {
        return new ItemStackListFluidChemicalToItemCachedRecipe(recipe, recheckAllRecipeErrors, itemInputHandler,
                fluidInputHandler, chemicalInputHandler, outputHandler)
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
    public @Nullable IRecipeViewerRecipeType<ItemStackListFluidChemicalToItemRecipe> recipeViewerType() {
        return MekUtRecipeViewerRecipeType.SMALL_DIGITAL_ASSEMBLER;
    }

}
