package com.takenokoshi.mekut.blockentity.normalmachine;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekut.blockentity.base.BEExpScaledRecipeMachine;
import com.takenokoshi.mekut.blockentity.interfaces.IHasMachineEnergyContainer;
import com.takenokoshi.mekut.blockentity.interfaces.machine.IBiChemicalToChemicalRecipeMachine;
import com.takenokoshi.mekut.recipe.cached.ICachedRecipe;
import com.takenokoshi.mekut.recipe.inputcache.MUEitherSideInputRecipeCache;
import com.takenokoshi.mekut.recipe.type.IMekUtRecipeTypeProvider;
import com.takenokoshi.mekut.recipe_viewer.type.MekUtRecipeViewerRecipeType;
import com.takenokoshi.mekut.registries.MekUtRecipeTypes;

import mekanism.api.AutomationType;
import mekanism.api.IContentsListener;
import mekanism.api.chemical.BasicChemicalTank;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.chemical.attribute.ChemicalAttributeValidator;
import mekanism.api.recipes.ChemicalChemicalToChemicalRecipe;
import mekanism.api.recipes.cache.ChemicalChemicalToChemicalCachedRecipe;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.inputs.InputHelper;
import mekanism.api.recipes.outputs.IOutputHandler;
import mekanism.api.recipes.outputs.OutputHelper;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mekanism.common.capabilities.energy.MachineEnergyContainer;
import mekanism.common.capabilities.holder.chemical.ChemicalTankHelper;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import mekanism.common.capabilities.holder.energy.EnergyContainerHelper;
import mekanism.common.capabilities.holder.energy.IEnergyContainerHolder;
import mekanism.common.capabilities.holder.slot.IInventorySlotHolder;
import mekanism.common.capabilities.holder.slot.InventorySlotHelper;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.inventory.slot.EnergyInventorySlot;
import mekanism.common.inventory.slot.chemical.ChemicalInventorySlot;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.recipe.lookup.cache.type.ChemicalInputCache;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.tile.component.config.ConfigInfo;
import mekanism.common.tile.component.config.DataType;
import mekanism.common.tile.component.config.slot.ChemicalSlotInfo;
import mekanism.common.tile.component.config.slot.InventorySlotInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BELazerCompressNucleoSynthesizer extends BEExpScaledRecipeMachine<ChemicalChemicalToChemicalRecipe>
        implements IBiChemicalToChemicalRecipeMachine, IHasMachineEnergyContainer {

    private IChemicalTank leftTank;
    private IChemicalTank rightTank;
    private IChemicalTank outputTank;
    private MachineEnergyContainer<?> energyContainer;
    private ChemicalInventorySlot leftSlot;
    private ChemicalInventorySlot rightSlot;
    private ChemicalInventorySlot outputSlot;
    private EnergyInventorySlot energySlot;

    private final IInputHandler<ChemicalStack> leftInputHandler;
    private final IInputHandler<ChemicalStack> rightInputHandler;
    private final IOutputHandler<ChemicalStack> outputHandler;

    public BELazerCompressNucleoSynthesizer(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state, TRACKED_ERROR_TYPES, 3);
        ConfigInfo itemConfig = configComponent.getConfig(TransmissionType.ITEM);
        if (itemConfig != null) {
            itemConfig.addSlotInfo(DataType.INPUT_1, new InventorySlotInfo(true, true, leftSlot));
            itemConfig.addSlotInfo(DataType.INPUT_2, new InventorySlotInfo(true, true, rightSlot));
            itemConfig.addSlotInfo(DataType.OUTPUT, new InventorySlotInfo(true, true, outputSlot));
            itemConfig.addSlotInfo(DataType.INPUT_OUTPUT,
                    new InventorySlotInfo(true, true, leftSlot, rightSlot, outputSlot));
            itemConfig.addSlotInfo(DataType.ENERGY, new InventorySlotInfo(true, true, energySlot));
        }

        ConfigInfo gasConfig = configComponent.getConfig(TransmissionType.CHEMICAL);
        if (gasConfig != null) {
            gasConfig.addSlotInfo(DataType.INPUT_1, new ChemicalSlotInfo(true, false, leftTank));
            gasConfig.addSlotInfo(DataType.INPUT_2, new ChemicalSlotInfo(true, false, rightTank));
            gasConfig.addSlotInfo(DataType.OUTPUT, new ChemicalSlotInfo(false, true, outputTank));
            gasConfig.addSlotInfo(DataType.INPUT_OUTPUT,
                    new ChemicalSlotInfo(true, true, leftTank, rightTank, outputTank));
        }

        configComponent.setupInputConfig(TransmissionType.ENERGY, energyContainer);

        ejectorComponent = new TileComponentEjector(this);
        ejectorComponent.setOutputData(configComponent, TransmissionType.ITEM, TransmissionType.CHEMICAL)
                .setCanTankEject(tank -> tank == outputTank);
        this.leftInputHandler = InputHelper.getInputHandler(leftTank, RecipeError.NOT_ENOUGH_LEFT_INPUT);
        this.rightInputHandler = InputHelper.getInputHandler(rightTank, RecipeError.NOT_ENOUGH_RIGHT_INPUT);
        this.outputHandler = OutputHelper.getOutputHandler(outputTank, RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
    }

    @Override
    protected IEnergyContainerHolder getInitialEnergyContainers(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        EnergyContainerHelper builder = EnergyContainerHelper.forSideWithConfig(this);
        builder.addContainer(energyContainer = MachineEnergyContainer.input(this, recipeCacheUnpauseListener));
        return builder.build();
    }

    @NotNull
    @Override
    public IChemicalTankHolder getInitialChemicalTanks(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        ChemicalTankHelper builder = ChemicalTankHelper.forSideWithConfig(this);
        builder.addTank(leftTank = BasicChemicalTank.create(200000,
                (stack, type) -> type == AutomationType.MANUAL,
                (gas, type) -> containsRecipe(gas.getStack(200000), rightTank.getStack()),
                gas -> containsRecipe(gas.getStack(200000)),
                ChemicalAttributeValidator.ALWAYS_ALLOW, recipeCacheListener));
        builder.addTank(rightTank = BasicChemicalTank.create(200000,
                (stack, type) -> type == AutomationType.MANUAL,
                (gas, type) -> containsRecipe(gas.getStack(200000), leftTank.getStack()),
                gas -> containsRecipe(gas.getStack(200000)),
                ChemicalAttributeValidator.ALWAYS_ALLOW, recipeCacheListener));
        builder.addTank(outputTank = BasicChemicalTank.output(200000, recipeCacheUnpauseListener));
        return builder.build();
    }

    @Override
    protected @Nullable IInventorySlotHolder getInitialInventory(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        InventorySlotHelper builder = InventorySlotHelper.forSideWithConfig(this);
        builder.addSlot(leftSlot = ChemicalInventorySlot.fillOrConvert(leftTank, this::getLevel, listener, 6, 56))
                .setSlotOverlay(SlotOverlay.MINUS);
        builder.addSlot(rightSlot = ChemicalInventorySlot.fillOrConvert(rightTank, this::getLevel, listener, 154, 56))
                .setSlotOverlay(SlotOverlay.MINUS);
        builder.addSlot(outputSlot = ChemicalInventorySlot.drain(outputTank, listener, 80, 65));
        builder.addSlot(
                energySlot = EnergyInventorySlot.fillOrConvert(energyContainer, this::getLevel, listener, 154, 14));
        return builder.build();
    }

    @Override
    protected boolean onUpdateServer() {
        boolean needsPacket = super.onUpdateServer();
        outputSlot.drainTank();
        clientEnergyUsed = recipeCacheLookupMonitor.updateAndProcess(energyContainer);
        leftSlot.fillTankOrConvert();
        rightSlot.fillTankOrConvert();
        energySlot.fillContainerOrConvert();
        return needsPacket;
    }

    @Override
    public @Nullable ChemicalChemicalToChemicalRecipe getRecipe(int cacheIndex) {
        return findFirstRecipe(leftInputHandler, rightInputHandler);
    }

    @Override
    public @NotNull ICachedRecipe<ChemicalChemicalToChemicalRecipe> createNewCachedRecipe(
            @NotNull ChemicalChemicalToChemicalRecipe recipe, int cacheIndex) {
        return ICachedRecipe.fromMekanism(new ChemicalChemicalToChemicalCachedRecipe<>(recipe, recheckAllRecipeErrors,
                leftInputHandler, rightInputHandler, outputHandler)
                .setErrorsChanged(this::onErrorsChanged)
                .setCanHolderFunction(this::canFunction)
                .setActive(this::setActive)
                .setEnergyRequirements(energyContainer::getEnergyPerTick, energyContainer)
                .setOnFinish(this::markForSave)
                .setBaselineMaxOperations(this::getOperationsPerTick));
    }

    @Override
    public IChemicalTank getLeftTank() {
        return leftTank;
    }

    @Override
    public IChemicalTank getRightTank() {
        return rightTank;
    }

    @Override
    public IChemicalTank getOutputTank() {
        return outputTank;
    }

    @Override
    public @NotNull IMekUtRecipeTypeProvider<?, ChemicalChemicalToChemicalRecipe, MUEitherSideInputRecipeCache<ChemicalStack, ChemicalStackIngredient, ChemicalChemicalToChemicalRecipe, ChemicalInputCache<ChemicalChemicalToChemicalRecipe>>> getRecipeType() {
        return MekUtRecipeTypes.LAZER_COMPRESS;
    }

    @Override
    public MachineEnergyContainer<?> getEnergyContainer() {
        return energyContainer;
    }

    @Override
    public @Nullable IRecipeViewerRecipeType<ChemicalChemicalToChemicalRecipe> recipeViewerType() {
        return MekUtRecipeViewerRecipeType.LAZER_COMPRESS;
    }

}
