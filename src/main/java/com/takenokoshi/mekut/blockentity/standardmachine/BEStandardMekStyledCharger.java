package com.takenokoshi.mekut.blockentity.standardmachine;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekut.blockentity.base.BEMultiScaledProgressMachine;
import com.takenokoshi.mekut.blockentity.interfaces.IMekStyledCharger;
import com.takenokoshi.mekut.core.MekUtMathUtils;
import com.takenokoshi.mekut.recipe.cached.AbstractCachedRecipe;
import com.takenokoshi.mekut.recipe.cached.MekStyledChargerCachedRecipe;
import com.takenokoshi.mekut.recipe.input.IngredientInputHandler;
import com.takenokoshi.mekut.recipe.output.ItemOutputHandler;

import appeng.recipes.handlers.ChargerRecipe;
import mekanism.api.IContentsListener;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.common.capabilities.energy.MachineEnergyContainer;
import mekanism.common.capabilities.holder.energy.EnergyContainerHelper;
import mekanism.common.capabilities.holder.energy.IEnergyContainerHolder;
import mekanism.common.capabilities.holder.slot.IInventorySlotHolder;
import mekanism.common.capabilities.holder.slot.InventorySlotHelper;
import mekanism.common.inventory.slot.EnergyInventorySlot;
import mekanism.common.inventory.slot.InputInventorySlot;
import mekanism.common.inventory.slot.OutputInventorySlot;
import mekanism.common.inventory.warning.WarningTracker.WarningType;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.tile.component.TileComponentEjector;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BEStandardMekStyledCharger extends BEMultiScaledProgressMachine<ChargerRecipe> implements IMekStyledCharger {

    private InputInventorySlot inputSlot;
    private OutputInventorySlot outputSlot;
    private EnergyInventorySlot energySlot;
    private MachineEnergyContainer<?> energyContainer;
    private final IngredientInputHandler inputHandler;
    private final ItemOutputHandler outputHandler;

    public BEStandardMekStyledCharger(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state, TRACKED_ERROR_TYPES,
                MekUtMathUtils.getTicksAccelerated(60, 6),
                r -> MekUtMathUtils.getTicksAccelerated(60, 6),
                MekUtMathUtils.getBaselineAccelerated(60, 6));
        configComponent.setupItemIOConfig(inputSlot, outputSlot, energySlot);
        configComponent.setupInputConfig(TransmissionType.ENERGY, energyContainer);
        ejectorComponent = new TileComponentEjector(this)
                .setOutputData(configComponent, TransmissionType.ITEM);
        this.inputHandler = new IngredientInputHandler(inputSlot, RecipeError.NOT_ENOUGH_INPUT);
        this.outputHandler = new ItemOutputHandler(outputSlot, RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
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
        builder.addSlot(inputSlot = InputInventorySlot.at(this::containsRecipe, recipeCacheListener, 64, 17))
                .tracksWarnings(slot -> slot.warning(WarningType.NO_MATCHING_RECIPE,
                        getWarningCheck(RecipeError.NOT_ENOUGH_INPUT)));
        builder.addSlot(outputSlot = OutputInventorySlot.at(recipeCacheUnpauseListener, 116, 35))
                .tracksWarnings(slot -> slot.warning(WarningType.NO_SPACE_IN_OUTPUT,
                        getWarningCheck(RecipeError.NOT_ENOUGH_OUTPUT_SPACE)));
        builder.addSlot(
                energySlot = EnergyInventorySlot.fillOrConvert(energyContainer, this::getLevel, listener, 64, 53));
        return builder.build();
    }

    @Override
    public @Nullable ChargerRecipe getRecipe(int cacheIndex) {
        return findFirstRecipe(inputHandler);
    }

    @Override
    public @NotNull AbstractCachedRecipe<ChargerRecipe> createNewCachedRecipe(@NotNull ChargerRecipe recipe,
            int cacheIndex) {
        return new MekStyledChargerCachedRecipe(recipe, recheckAllRecipeErrors, inputHandler, outputHandler)
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

    protected boolean onUpdateServer() {
        boolean v = super.onUpdateServer();
        energySlot.fillContainerOrConvert();
        clientEnergyUsed = recipeCacheLookupMonitor.updateAndProcess(energyContainer);
        return v;
    }

}
