package com.takenokoshi.mekut.blockentity.normalmachine;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekut.blockentity.base.BEMultiScaledProgressMachine;
import com.takenokoshi.mekut.blockentity.interfaces.IHasMachineEnergyContainer;
import com.takenokoshi.mekut.blockentity.interfaces.machine.IItemStackChemicalToItemStackMachine;
import com.takenokoshi.mekut.recipe.cached.ICachedRecipe;
import com.takenokoshi.mekut.recipe.cached.MekUtItemChemicalToChemicalCachedRecipe;
import com.takenokoshi.mekut.recipe.inputcache.MekUtDoubleInputRecipeCache.MekUtItemChemical;
import com.takenokoshi.mekut.recipe.type.IMekUtRecipeTypeProvider;
import com.takenokoshi.mekut.recipe_viewer.type.MekUtRecipeViewerRecipeType;
import com.takenokoshi.mekut.registries.MekUtRecipeTypes;

import mekanism.api.IContentsListener;
import mekanism.api.Upgrade;
import mekanism.api.chemical.BasicChemicalTank;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.functions.ConstantPredicates;
import mekanism.api.math.MathUtils;
import mekanism.api.recipes.ItemStackChemicalToItemStackRecipe;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
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
import mekanism.common.config.MekanismConfig;
import mekanism.common.inventory.slot.BasicInventorySlot;
import mekanism.common.inventory.slot.EnergyInventorySlot;
import mekanism.common.inventory.slot.InputInventorySlot;
import mekanism.common.inventory.slot.OutputInventorySlot;
import mekanism.common.inventory.slot.chemical.ChemicalInventorySlot;
import mekanism.common.inventory.warning.WarningTracker.WarningType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BEChemicalCutter extends BEMultiScaledProgressMachine<ItemStackChemicalToItemStackRecipe>
        implements IItemStackChemicalToItemStackMachine, IHasMachineEnergyContainer {

    BasicInventorySlot inputSlot;
    BasicInventorySlot outputSlot;
    ChemicalInventorySlot secondarySlot;
    EnergyInventorySlot energySlot;

    private IChemicalTank chemicalTank;
    private MachineEnergyContainer<?> energyContainer;
    private double chemicalUsageModifier = 1.0d;

    protected final IOutputHandler<@NotNull ItemStack> outputHandler;
    protected final IInputHandler<@NotNull ItemStack> itemInputHandler;
    protected final IInputHandler<@NotNull ChemicalStack> chemicalInputHandler;

    public BEChemicalCutter(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state, TRACKED_ERROR_TYPES, 200, r -> 200, 1);
        ejectorComponent = IItemStackChemicalToItemStackMachine.setUpConfig(this, configComponent, inputSlot,
                outputSlot, secondarySlot, energySlot, chemicalTank, energyContainer);
        itemInputHandler = InputHelper.getInputHandler(inputSlot, RecipeError.NOT_ENOUGH_INPUT);
        chemicalInputHandler = InputHelper.getInputHandler(chemicalTank, RecipeError.NOT_ENOUGH_SECONDARY_INPUT);
        outputHandler = OutputHelper.getOutputHandler(outputSlot, RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
    }

    @NotNull
    @Override
    public IChemicalTankHolder getInitialChemicalTanks(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        ChemicalTankHelper builder = ChemicalTankHelper.forSideWithConfig(this);
        builder.addTank(chemicalTank = BasicChemicalTank.createModern(200000, ConstantPredicates.notExternal(),
                (gas, automationType) -> containsRecipeBA(inputSlot.getStack(), gas), this::containsRecipeB,
                recipeCacheListener));
        return builder.build();
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
    protected IInventorySlotHolder getInitialInventory(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        InventorySlotHelper builder = InventorySlotHelper.forSideWithConfig(this);
        builder.addSlot(inputSlot = InputInventorySlot.at(item -> containsRecipeAB(item, chemicalTank.getStack()),
                this::containsRecipeA, recipeCacheListener, 64, 17))
                .tracksWarnings(slot -> slot.warning(WarningType.NO_MATCHING_RECIPE,
                        getWarningCheck(RecipeError.NOT_ENOUGH_INPUT)));
        builder.addSlot(
                secondarySlot = ChemicalInventorySlot.fillOrConvert(chemicalTank, this::getLevel, listener, 64, 53));
        builder.addSlot(outputSlot = OutputInventorySlot.at(recipeCacheUnpauseListener, 116, 35))
                .tracksWarnings(slot -> slot.warning(WarningType.NO_SPACE_IN_OUTPUT,
                        getWarningCheck(RecipeError.NOT_ENOUGH_OUTPUT_SPACE)));
        builder.addSlot(
                energySlot = EnergyInventorySlot.fillOrConvert(energyContainer, this::getLevel, listener, 39, 35));
        return builder.build();
    }

    @Override
    protected boolean onUpdateServer() {
        boolean sendUpdatePacket = super.onUpdateServer();
        energySlot.fillContainerOrConvert();
        secondarySlot.fillTankOrConvert();
        clientEnergyUsed = recipeCacheLookupMonitor.updateAndProcess(energyContainer);
        return sendUpdatePacket;
    }

    @Override
    public @NotNull IMekUtRecipeTypeProvider<?, ItemStackChemicalToItemStackRecipe, MekUtItemChemical<ItemStackChemicalToItemStackRecipe>> getRecipeType() {
        return MekUtRecipeTypes.CHEMICAL_CUT;
    }

    @Override
    public @Nullable ItemStackChemicalToItemStackRecipe getRecipe(int cacheIndex) {
        return findFirstRecipe(itemInputHandler, chemicalInputHandler);
    }

    @Override
    public @NotNull ICachedRecipe<ItemStackChemicalToItemStackRecipe> createNewCachedRecipe(
            @NotNull ItemStackChemicalToItemStackRecipe recipe, int cacheIndex) {
        return new MekUtItemChemicalToChemicalCachedRecipe(recipe, recheckAllRecipeErrors, itemInputHandler,
                chemicalInputHandler, outputHandler, this::getChemicalUsage)
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
    public void recalculateUpgrades(Upgrade upgrade) {
        super.recalculateUpgrades(upgrade);
        if (upgrade == Upgrade.CHEMICAL) {
            chemicalUsageModifier = Math.pow(MekanismConfig.general.maxUpgradeMultiplier.getAsInt(),
                    -upgradeComponent.getUpgrades(Upgrade.CHEMICAL) / 8.0d);
        }
    }

    private long getChemicalUsage(long defaultValue) {
        return MathUtils.clampToLong(defaultValue * chemicalUsageModifier);
    }

    @Override
    public MachineEnergyContainer<?> getEnergyContainer() {
        return energyContainer;
    }

    @Override
    public IChemicalTank getInputTank() {
        return chemicalTank;
    }

    @Override
    public @Nullable IRecipeViewerRecipeType<ItemStackChemicalToItemStackRecipe> recipeViewerType() {
        return MekUtRecipeViewerRecipeType.CHEMICAL_CUT;
    }

}
