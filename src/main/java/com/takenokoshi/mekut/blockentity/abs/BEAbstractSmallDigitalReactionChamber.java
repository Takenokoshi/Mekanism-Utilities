package com.takenokoshi.mekut.blockentity.abs;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekaddonlib.blockentity.base.BEMultiScaledProgressMachine;
import com.takenokoshi.mekaddonlib.blockentity.component.EjectorComponentUtils;
import com.takenokoshi.mekaddonlib.blockentity.interfaces.IHasGuiSizeOffset;
import com.takenokoshi.mekaddonlib.inventory.slot.LimitChangedOutputInventorySlot;
import com.takenokoshi.mekaddonlib.recipe.cached.ICachedRecipe;
import com.takenokoshi.mekaddonlib.recipe.type.IMekALRecipeTypeProvider;
import com.takenokoshi.mekut.blockentity.interfaces.IHasMachineEnergyContainer;
import com.takenokoshi.mekut.blockentity.interfaces.IRecipeViewerTypeProvider;
import com.takenokoshi.mekut.blockentity.interfaces.machine.IItemStackListFluidChemicalToItemFluidChemicalRecipeMachine;
import com.takenokoshi.mekut.capabilities.energy.VariableUsageMachineEnergyContainer;
import com.takenokoshi.mekut.inventory.slot.ChemicalFillConvertOrSupplyingSlot;
import com.takenokoshi.mekut.inventory.slot.FluidFillOrSupplierSlot;
import com.takenokoshi.mekut.inventory.slot.InputOrSupplyingSlot;
import com.takenokoshi.mekut.recipe.cached.ItemStackListFluidChemicalToItemFluidChemicalCachedRecipe;
import com.takenokoshi.mekut.recipe.input.AdvancedChemicalInputHandler;
import com.takenokoshi.mekut.recipe.input.AdvancedFluidInputHandler;
import com.takenokoshi.mekut.recipe.input.ItemStackListInputHandler;
import com.takenokoshi.mekut.recipe.inputcache.ItemStackListFluidChemicalInputRecipeCache;
import com.takenokoshi.mekut.recipe.output.ItemOutputHandler;
import com.takenokoshi.mekut.recipe.recipe.prefab.ItemStackListFluidChemicalToItemFluidChemicalRecipe;
import com.takenokoshi.mekut.recipe_viewer.type.MekUtRecipeViewerRecipeType;
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
import mekanism.api.recipes.outputs.IOutputHandler;
import mekanism.api.recipes.outputs.OutputHelper;
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
import mekanism.common.inventory.slot.FluidInventorySlot;
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

public abstract class BEAbstractSmallDigitalReactionChamber
        extends BEMultiScaledProgressMachine<ItemStackListFluidChemicalToItemFluidChemicalRecipe>
        implements IItemStackListFluidChemicalToItemFluidChemicalRecipeMachine, IHasMachineEnergyContainer,
        IHasGuiSizeOffset, IRecipeViewerTypeProvider {

    private InputOrSupplyingSlot[] inputSlots;
    private LimitChangedOutputInventorySlot outputSlot;
    private EnergyInventorySlot energySlot;

    private FluidFillOrSupplierSlot fluidInputSlot;
    private ChemicalFillConvertOrSupplyingSlot chemicalInputSlot;
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
    private final AdvancedFluidInputHandler fluidInputHandler;
    private final AdvancedChemicalInputHandler chemicalInputHandler;
    private final IOutputHandler<ItemStack> itemOutputHandler;
    private final IOutputHandler<FluidStack> fluidOutputHandler;
    private final IOutputHandler<ChemicalStack> chemicalOutputHandler;

    protected BEAbstractSmallDigitalReactionChamber(Holder<Block> blockProvider, BlockPos pos, BlockState state,
            int baselineMaxOperations) {
        super(blockProvider, pos, state, TRACKED_ERROR_TYPES, baselineMaxOperations,
                ItemStackListFluidChemicalToItemFluidChemicalRecipe::getDuration);
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
        this.fluidInputHandler = AdvancedFluidInputHandler.create(inputFluidTank, RecipeError.NOT_ENOUGH_INPUT);
        this.chemicalInputHandler = AdvancedChemicalInputHandler.create(inputChemicalTank,
                RecipeError.NOT_ENOUGH_INPUT);
        this.itemOutputHandler = new ItemOutputHandler(outputSlot, RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
        this.fluidOutputHandler = OutputHelper.getOutputHandler(outputFluidTank, RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
        this.chemicalOutputHandler = OutputHelper.getOutputHandler(outputChemicalTank,
                RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
        for (int i = 0; i < inputSlots.length; i++) {
            int index = i;
            inputSlots[i].setSupplyingStackSetter(stack -> itemInputHandler.setSuppliedStack(stack, index));
        }
        fluidInputSlot.setSupplyingStackSetter(fluidInputHandler::setSuppliedStack);
        chemicalInputSlot.setSupplyingStackSetter(chemicalInputHandler::setSuppliedStack);
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
        inputSlots = new InputOrSupplyingSlot[9];
        for (int index = 0; index < 9; index++) {
            int value = index;
            builder.addSlot(inputSlots[index] = InputOrSupplyingSlot.at(
                    stack -> containsRecipeItemOther(stack, value, itemInputHandler.getOtherSlotInput(value),
                            fluidInputHandler.getInput(), chemicalInputHandler.getInput()),
                    stack -> containsRecipeItem(stack, value),
                    recipeCacheListener, 54 + index % 3 * 18, 22 + index / 3 * 18, initItemSlotCapacity()))
                    .tracksWarnings(warning -> warning.warning(WarningType.NO_MATCHING_RECIPE,
                            getWarningCheck(RecipeError.NOT_ENOUGH_INPUT)));
        }
        builder.addSlot(fluidInputSlot = FluidFillOrSupplierSlot.create(inputFluidTank,
                recipeCacheListener, 6, 58))
                .setSlotOverlay(SlotOverlay.MINUS);
        builder.addSlot(chemicalInputSlot = ChemicalFillConvertOrSupplyingSlot.create(inputChemicalTank,
                this::getLevel, recipeCacheListener, 29, 58))
                .setSlotOverlay(SlotOverlay.MINUS);
        builder.addSlot(chemicalOutputSlot = ChemicalInventorySlot.drain(outputChemicalTank,
                listener, 177, 58)).setSlotOverlay(SlotOverlay.PLUS);
        builder.addSlot(fluidOutputSlot = FluidInventorySlot.drain(outputFluidTank,
                listener, 200, 58)).setSlotOverlay(SlotOverlay.PLUS);
        builder.addSlot(fluidInputReturnSlot = OutputInventorySlot.at(listener, 06, 89));
        builder.addSlot(fluidOutputReturnSlot = OutputInventorySlot.at(listener, 200, 89));
        builder.addSlot(outputSlot = LimitChangedOutputInventorySlot.at(recipeCacheUnpauseListener, 152, 40,
                initItemSlotCapacity()))
                .tracksWarnings(warning -> warning.warning(WarningType.NO_SPACE_IN_OUTPUT,
                        getWarningCheck(RecipeError.NOT_ENOUGH_OUTPUT_SPACE)));
        builder.addSlot(energySlot = EnergyInventorySlot.fillOrConvert(energyContainer, this::getLevel,
                listener, 200, 4));
        return builder.build();
    }

    protected abstract int initItemSlotCapacity();

    @Override
    protected @NotNull IFluidTankHolder getInitialFluidTanks(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        FluidTankHelper builder = FluidTankHelper.forSideWithConfig(this);
        builder.addTank(inputFluidTank = BasicFluidTank.input(initFluidTankCapacity(),
                stack -> containsRecipeFluidOther(itemInputHandler.getInput(), stack, chemicalInputHandler.getInput()),
                this::containsRecipeFluid, recipeCacheListener));
        builder.addTank(outputFluidTank = BasicFluidTank.output(initFluidTankCapacity(), recipeCacheUnpauseListener));
        return builder.build();
    }

    protected abstract int initFluidTankCapacity();

    @Override
    protected @Nullable IChemicalTankHolder getInitialChemicalTanks(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        ChemicalTankHelper builder = ChemicalTankHelper.forSideWithConfig(this);
        builder.addTank(inputChemicalTank = BasicChemicalTank.create(initChemicalTankCapacity(),
                (stack, type) -> type != AutomationType.EXTERNAL,
                (stack, type) -> containsRecipeChemicalOther(itemInputHandler.getInput(), fluidInputHandler.getInput(), stack.getStack(1)),
                chemical -> containsRecipeChemical(chemical.getStack(1)),
                ChemicalAttributeValidator.ALWAYS_ALLOW,
                recipeCacheListener));
        builder.addTank(
                outputChemicalTank = BasicChemicalTank.output(initChemicalTankCapacity(), recipeCacheUnpauseListener));
        return builder.build();
    }

    protected abstract long initChemicalTankCapacity();

    @Override
    public int getExtraWidth() {
        return 60;
    }

    @Override
    public int getExtraHeight() {
        return 5;
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
    public @NotNull IMekALRecipeTypeProvider<?, ItemStackListFluidChemicalToItemFluidChemicalRecipe, ItemStackListFluidChemicalInputRecipeCache<ItemStackListFluidChemicalToItemFluidChemicalRecipe>> getRecipeType() {
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

    @Override
    public @Nullable IRecipeViewerRecipeType<ItemStackListFluidChemicalToItemFluidChemicalRecipe> recipeViewerType() {
        return MekUtRecipeViewerRecipeType.SMALL_DIGITAL_REACTION_CHAMBER;
    }

}
