package com.takenokoshi.mekut.blockentity.normalmachine;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekaddonlib.blockentity.base.BEMultiScaledProgressMachine;
import com.takenokoshi.mekaddonlib.recipe.cached.ICachedRecipe;
import com.takenokoshi.mekaddonlib.recipe.type.IMekALRecipeTypeProvider;
import com.takenokoshi.mekut.blockentity.interfaces.IHasMachineEnergyContainer;
import com.takenokoshi.mekut.blockentity.interfaces.IRecipeViewerTypeProvider;
import com.takenokoshi.mekut.blockentity.interfaces.machine.IBiChemicalToObjectRecipeMachine;
import com.takenokoshi.mekut.inventory.slot.ChemicalFillConvertOrSupplyingSlot;
import com.takenokoshi.mekut.recipe.cached.BiChemicalToItemCachedRecipe;
import com.takenokoshi.mekut.recipe.input.AdvancedChemicalInputHandler;
import com.takenokoshi.mekut.recipe.inputcache.MUEitherSideInputRecipeCache;
import com.takenokoshi.mekut.recipe.output.ItemOutputHandler;
import com.takenokoshi.mekut.recipe.recipe.prefab.BiChemicalToItemRecipe;
import com.takenokoshi.mekut.recipe_viewer.type.MekUtRecipeViewerRecipeType;
import com.takenokoshi.mekut.registries.MekUtRecipeTypes;

import mekanism.api.IContentsListener;
import mekanism.api.chemical.BasicChemicalTank;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.outputs.IOutputHandler;
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
import mekanism.common.inventory.slot.OutputInventorySlot;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.recipe.lookup.cache.type.ChemicalInputCache;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.tile.component.config.ConfigInfo;
import mekanism.common.tile.component.config.DataType;
import mekanism.common.tile.component.config.slot.ChemicalSlotInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BEStellarGenesisChamber extends BEMultiScaledProgressMachine<BiChemicalToItemRecipe>
        implements IBiChemicalToObjectRecipeMachine<BiChemicalToItemRecipe>, IHasMachineEnergyContainer,
        IRecipeViewerTypeProvider {

    private IChemicalTank leftTank;
    private IChemicalTank rightTank;
    private MachineEnergyContainer<?> energyContainer;

    private ChemicalFillConvertOrSupplyingSlot leftSlot;
    private ChemicalFillConvertOrSupplyingSlot rightSlot;
    private OutputInventorySlot outputSlot;
    private EnergyInventorySlot energySlot;

    private final AdvancedChemicalInputHandler leftInputHandler;
    private final AdvancedChemicalInputHandler rightInputHandler;
    private final IOutputHandler<ItemStack> outputHandler;

    public BEStellarGenesisChamber(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state, TRACKED_ERROR_TYPES, 1, r -> 1000);
        configComponent.setupItemIOConfig(List.of(leftSlot, rightSlot), List.of(outputSlot), energySlot, false);

        ConfigInfo gasConfig = configComponent.getConfig(TransmissionType.CHEMICAL);
        if (gasConfig != null) {
            gasConfig.addSlotInfo(DataType.INPUT_1, new ChemicalSlotInfo(true, false, leftTank));
            gasConfig.addSlotInfo(DataType.INPUT_2, new ChemicalSlotInfo(true, false, rightTank));
        }

        configComponent.setupInputConfig(TransmissionType.ENERGY, energyContainer);

        ejectorComponent = new TileComponentEjector(this);
        ejectorComponent.setOutputData(configComponent, TransmissionType.ITEM);
        this.leftInputHandler = AdvancedChemicalInputHandler.create(leftTank, RecipeError.NOT_ENOUGH_LEFT_INPUT);
        this.rightInputHandler = AdvancedChemicalInputHandler.create(rightTank, RecipeError.NOT_ENOUGH_RIGHT_INPUT);
        this.outputHandler = new ItemOutputHandler(outputSlot, RecipeError.NOT_ENOUGH_OUTPUT_SPACE);

        leftSlot.setSupplyingStackSetter(leftInputHandler::setSuppliedStack);
        rightSlot.setSupplyingStackSetter(rightInputHandler::setSuppliedStack);
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
        builder.addTank(leftTank = BasicChemicalTank.inputModern(Long.MAX_VALUE,
                gas -> containsRecipe(gas, rightInputHandler.getInput()), this::containsRecipe, recipeCacheListener));
        builder.addTank(rightTank = BasicChemicalTank.inputModern(Long.MAX_VALUE,
                gas -> containsRecipe(gas, leftInputHandler.getInput()), this::containsRecipe, recipeCacheListener));
        return builder.build();
    }

    @Override
    protected @Nullable IInventorySlotHolder getInitialInventory(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        InventorySlotHelper builder = InventorySlotHelper.forSideWithConfig(this);
        builder.addSlot(leftSlot = ChemicalFillConvertOrSupplyingSlot
                .create(leftTank, this::getLevel, listener, 6, 56))
                .setSlotOverlay(SlotOverlay.MINUS);
        builder.addSlot(rightSlot = ChemicalFillConvertOrSupplyingSlot
                .create(rightTank, this::getLevel, listener, 154, 56))
                .setSlotOverlay(SlotOverlay.MINUS);
        builder.addSlot(outputSlot = OutputInventorySlot.at(recipeCacheUnpauseListener, 80, 34));
        builder.addSlot(
                energySlot = EnergyInventorySlot.fillOrConvert(energyContainer, this::getLevel, listener, 154, 14));
        return builder.build();
    }

    @Override
    protected boolean onUpdateServer() {
        boolean needsPacket = super.onUpdateServer();
        clientEnergyUsed = recipeCacheLookupMonitor.updateAndProcess(energyContainer);
        leftSlot.fillTankOrConvert();
        rightSlot.fillTankOrConvert();
        energySlot.fillContainerOrConvert();
        return needsPacket;
    }

    @Override
    public @NotNull IMekALRecipeTypeProvider<?, BiChemicalToItemRecipe, MUEitherSideInputRecipeCache<ChemicalStack, ChemicalStackIngredient, BiChemicalToItemRecipe, ChemicalInputCache<BiChemicalToItemRecipe>>> getRecipeType() {
        return MekUtRecipeTypes.STELLAR_GENESIS;
    }

    @Override
    public @Nullable BiChemicalToItemRecipe getRecipe(int cacheIndex) {
        return findFirstRecipe(leftInputHandler, rightInputHandler);
    }

    @Override
    public @NotNull ICachedRecipe<BiChemicalToItemRecipe> createNewCachedRecipe(
            @NotNull BiChemicalToItemRecipe recipe, int cacheIndex) {
        return new BiChemicalToItemCachedRecipe(recipe, recheckAllRecipeErrors, leftInputHandler, rightInputHandler,
                outputHandler)
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
    public IChemicalTank getLeftTank() {
        return leftTank;
    }

    @Override
    public IChemicalTank getRightTank() {
        return rightTank;
    }

    @Override
    public MachineEnergyContainer<?> getEnergyContainer() {
        return energyContainer;
    }

    public @Nullable IRecipeViewerRecipeType<BiChemicalToItemRecipe> recipeViewerType() {
        return MekUtRecipeViewerRecipeType.STELLAR_GENESIS;
    }

}
