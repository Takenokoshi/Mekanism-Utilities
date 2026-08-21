package com.takenokoshi.mekut.blockentity.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekaddonlib.blockentity.base.BEMultiScaledProgressFactory;
import com.takenokoshi.mekaddonlib.inventory.slot.LimitChangedOutputInventorySlot;
import com.takenokoshi.mekaddonlib.recipe.cached.ICachedRecipe;
import com.takenokoshi.mekaddonlib.recipe.lookup.IMekALRecipeTypedLookupHandler;
import com.takenokoshi.mekaddonlib.recipe.type.IMekALRecipeTypeProvider;
import com.takenokoshi.mekut.blockentity.interfaces.IHasMachineEnergyContainer;
import com.takenokoshi.mekut.blockentity.upgradedata.EnergizedSmelterUpgradeData;
import com.takenokoshi.mekut.inventory.slot.InputOrSupplyingSlot;
import com.takenokoshi.mekut.recipe.cached.TweakedSmeltingCachedRecipe;
import com.takenokoshi.mekut.recipe.input.AdvancedIngredientInputHandler;
import com.takenokoshi.mekut.recipe.inputcache.MUSingleInputRecipeCache;
import com.takenokoshi.mekut.recipe.inputcache.MUSingleInputRecipeCache.MUSingleItem;
import com.takenokoshi.mekut.recipe.output.ChemicalOutputHandler;
import com.takenokoshi.mekut.recipe.output.ItemOutputHandler;
import com.takenokoshi.mekut.recipe.type.WrappedRecipeType;

import mekanism.api.Action;
import mekanism.api.IContentsListener;
import mekanism.api.RelativeSide;
import mekanism.api.chemical.BasicChemicalTank;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.attachments.containers.chemical.ChemicalTanksBuilder;
import mekanism.common.attachments.containers.item.ItemSlotsBuilder;
import mekanism.common.capabilities.energy.MachineEnergyContainer;
import mekanism.common.capabilities.holder.chemical.ChemicalTankHelper;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import mekanism.common.capabilities.holder.energy.EnergyContainerHelper;
import mekanism.common.capabilities.holder.energy.IEnergyContainerHolder;
import mekanism.common.capabilities.holder.slot.IInventorySlotHolder;
import mekanism.common.capabilities.holder.slot.InventorySlotHelper;
import mekanism.common.inventory.container.MekanismContainer;
import mekanism.common.inventory.container.sync.SyncableInt;
import mekanism.common.inventory.slot.EnergyInventorySlot;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.registration.impl.ItemRegistryObject;
import mekanism.common.tile.component.ITileComponent;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.upgrade.IUpgradeData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BEEnergizedSmeltingFactory extends BEMultiScaledProgressFactory<SmeltingRecipe>
        implements
        IMekALRecipeTypedLookupHandler<SmeltingRecipe, MUSingleInputRecipeCache.MUSingleItem<SmeltingRecipe>>,
        IHasMachineEnergyContainer {

    public static final List<RecipeError> TRACKED_ERROR_TYPES = List.of(
            RecipeError.NOT_ENOUGH_INPUT,
            RecipeError.NOT_ENOUGH_OUTPUT_SPACE,
            RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT);

    public static final Set<RecipeError> GLOBAL_ERROR_TYPES = Set.of(
            RecipeError.NOT_ENOUGH_ENERGY);

    public static Consumer<ItemRegistryObject<?>> getContainerAdder(int processes) {
        return value -> {
            value.addAttachmentOnlyContainers(ContainerType.ITEM, () -> {
                var builder = ItemSlotsBuilder.builder();
                for (int i = 0; i < processes; i++) {
                    builder.addInput(1).addOutput(1);
                }
                return builder.addEnergy()
                        .build();
            });
            value.addAttachmentOnlyContainers(ContainerType.CHEMICAL, () -> ChemicalTanksBuilder.builder()
                    .addBasic(Long.MAX_VALUE)
                    .build());
        };
    }

    private InputOrSupplyingSlot[] inputSlots;
    private LimitChangedOutputInventorySlot[] outputSlots;
    private EnergyInventorySlot energySlot;
    private MachineEnergyContainer<?> energyContainer;
    private IChemicalTank xpTank;

    private final AdvancedIngredientInputHandler[] inputHandlers;
    private final ItemOutputHandler[] outputHandlers;
    private final ChemicalOutputHandler xpHandler;

    private int extraWidth;
    public int slotsLeftPos;

    public BEEnergizedSmeltingFactory(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state, TRACKED_ERROR_TYPES, GLOBAL_ERROR_TYPES, 1,
                AbstractCookingRecipe::getCookingTime);
        configComponent.setupItemIOConfig(List.of(inputSlots), List.of(outputSlots), energySlot, false);
        configComponent.setupInputConfig(TransmissionType.ENERGY, energyContainer);
        configComponent.setupOutputConfig(TransmissionType.CHEMICAL, xpTank, RelativeSide.RIGHT);
        ejectorComponent = new TileComponentEjector(this, () -> Long.MAX_VALUE)
                .setOutputData(configComponent, TransmissionType.ITEM, TransmissionType.CHEMICAL);
        this.inputHandlers = new AdvancedIngredientInputHandler[tier.processes];
        this.outputHandlers = new ItemOutputHandler[tier.processes];
        for (int i = 0; i < tier.processes; i++) {
            inputHandlers[i] = new AdvancedIngredientInputHandler(inputSlots[i], RecipeError.NOT_ENOUGH_INPUT);
            outputHandlers[i] = new ItemOutputHandler(outputSlots[i], RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
            inputSlots[i].setSupplyingStackSetter(inputHandlers[i]::setSuppliedStack);
        }
        this.xpHandler = new ChemicalOutputHandler(xpTank, RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
    }

    @Override
    protected void presetVariables() {
        super.presetVariables();
        int slotsTotalWidth = tier.processes * 20 - 2;
        if (slotsTotalWidth > 128) {
            slotsLeftPos = 24;
            extraWidth = slotsTotalWidth - 136;
        } else {
            slotsLeftPos = (176 - slotsTotalWidth) >> 1;
        }
    }

    @Override
    protected @Nullable IChemicalTankHolder getInitialChemicalTanks(IContentsListener listener,
            IContentsListener[] recipeCacheListeners, IContentsListener allRecipeCacheListener,
            IContentsListener[] recipeCacheUnpauseListeners, IContentsListener allRecipeCacheUnpauseListener) {
        ChemicalTankHelper builder = ChemicalTankHelper.forSideWithConfig(this);
        builder.addTank(xpTank = BasicChemicalTank.output(Long.MAX_VALUE, allRecipeCacheUnpauseListener));
        return builder.build();
    }

    @Override
    protected @Nullable IEnergyContainerHolder getInitialEnergyContainers(IContentsListener listener,
            IContentsListener[] recipeCacheListeners, IContentsListener allRecipeCacheListener,
            IContentsListener[] recipeCacheUnpauseListeners, IContentsListener allRecipeCacheUnpauseListener) {
        EnergyContainerHelper builder = EnergyContainerHelper.forSideWithConfig(this);
        builder.addContainer(energyContainer = MachineEnergyContainer.input(this, allRecipeCacheUnpauseListener));
        return builder.build();
    }

    @Override
    protected @Nullable IInventorySlotHolder getInitialInventory(IContentsListener listener,
            IContentsListener[] recipeCacheListeners, IContentsListener allRecipeCacheListener,
            IContentsListener[] recipeCacheUnpauseListeners, IContentsListener allRecipeCacheUnpauseListeners) {
        InventorySlotHelper builder = InventorySlotHelper.forSideWithConfig(this);
        inputSlots = new InputOrSupplyingSlot[tier.processes];
        outputSlots = new LimitChangedOutputInventorySlot[tier.processes];
        for (int i = 0; i < tier.processes; i++) {
            builder.addSlot(inputSlots[i] = InputOrSupplyingSlot.at(this::containsInput, this::containsInput,
                    recipeCacheListeners[i], 20 * i + slotsLeftPos, 13, 64));
            builder.addSlot(outputSlots[i] = LimitChangedOutputInventorySlot.at(recipeCacheUnpauseListeners[i],
                    20 * i + slotsLeftPos, 57, 64));
        }
        builder.addSlot(energySlot = EnergyInventorySlot.fillOrConvert(energyContainer, this::getLevel, listener,
                2, 13));
        return builder.build();
    }

    @Override
    public int getExtraWidth() {
        return extraWidth;
    }

    @Override
    public int getExtraHeight() {
        return 12;
    }

    @Override
    protected boolean onUpdateServer() {
        boolean sendUpdatePacket = super.onUpdateServer();
        energySlot.fillContainerOrConvert();
        updateAndProcess(energyContainer);
        return sendUpdatePacket;
    }

    @Override
    public @NotNull ICachedRecipe<SmeltingRecipe> createNewCachedRecipe(@NotNull SmeltingRecipe recipe,
            int cacheIndex) {
        return new TweakedSmeltingCachedRecipe(recipe, recheckAllRecipeErrors[cacheIndex],
                inputHandlers[cacheIndex], outputHandlers[cacheIndex], xpHandler)
                .setErrorsChanged(v -> errorTracker.onErrorsChanged(v, cacheIndex))
                .setCanHolderFunction(this::canFunction)
                .setActive(v -> setActiveState(v, cacheIndex))
                .setEnergyRequirements(energyContainer::getEnergyPerTick, energyContainer)
                .setRequiredTicks(() -> ticksRequired[cacheIndex])
                .setOnFinish(this::markForSave)
                .setOperatingTicksChanged(v -> operatingTicks[cacheIndex] = v)
                .setBaselineMaxOperations(() -> getOperationsPerTick(cacheIndex));
    }

    @Override
    public @Nullable SmeltingRecipe getRecipe(int cacheIndex) {
        return findFirstRecipe(inputHandlers[cacheIndex]);
    }

    @Override
    public @NotNull IMekALRecipeTypeProvider<?, SmeltingRecipe, MUSingleItem<SmeltingRecipe>> getRecipeType() {
        return WrappedRecipeType.VANILLA_SMELTING;
    }

    @Override
    public MachineEnergyContainer<?> getEnergyContainer() {
        return energyContainer;
    }

    public IChemicalTank getXpTank() {
        return xpTank;
    };

    protected boolean containsInput(ItemStack input) {
        return getRecipeType().getInputCache().containsInput(getLevel(), input);
    }

    protected SmeltingRecipe findFirstRecipe(AdvancedIngredientInputHandler inputHandler) {
        return getRecipeType().getInputCache().findFirstRecipe(getLevel(), inputHandler.getInput());
    }

    @Override
    public EnergizedSmelterUpgradeData getUpgradeData(Provider provider) {
        return new EnergizedSmelterUpgradeData(provider, redstone, getControlType(), energyContainer,
                operationsPerTicks, energySlot, List.of(inputSlots), List.of(outputSlots), xpTank, sorting,
                getComponents());
    }

    @Override
    public void parseUpgradeData(Provider provider, @NotNull IUpgradeData upgradeData) {
        if (upgradeData instanceof EnergizedSmelterUpgradeData data) {
            redstone = data.redstone;
            setControlType(data.controlType);
            getEnergyContainer().setEnergy(data.energyContainer.getEnergy());
            sorting = data.sorting;
            energySlot.deserializeNBT(provider, data.energySlot.serializeNBT(provider));
            System.arraycopy(data.progress, 0, operatingTicks, 0, data.progress.length);
            for (int i = 0; i < data.inputSlots.size(); i++) {
                inputSlots[i].deserializeNBT(provider, data.inputSlots.get(i).serializeNBT(provider));
            }
            for (int i = 0; i < data.outputSlots.size(); i++) {
                outputSlots[i].setStack(data.outputSlots.get(i).getStack());
            }
            xpTank.setStack(data.chemicalTank.getStack());
            for (ITileComponent component : getComponents()) {
                component.read(data.components, provider);
            }

        } else {
            super.parseUpgradeData(provider, upgradeData);
        }
    }

    @Override
    public void addContainerTrackers(MekanismContainer container) {
        super.addContainerTrackers(container);
        container.track(SyncableInt.create(() -> slotsLeftPos, v -> slotsLeftPos = v));
    }

    @Override
    protected void runSort() {
        int targetIndex = -1;
        int largestAmount = 0;
        List<InputOrSupplyingSlot> emptySlots = new ArrayList<>();
        for (int i = 0; i < tier.processes; i++) {
            if (inputSlots[i].isEmpty()) {
                emptySlots.add(inputSlots[i]);
                continue;
            }
            int amount = inputSlots[i].getCount();
            if (amount > largestAmount) {
                largestAmount = amount;
                targetIndex = i;
            }
        }
        if (targetIndex == -1 || emptySlots.isEmpty()) {
            return;
        }
        int countPerSlot = largestAmount / (emptySlots.size() + 1);
        int extra = largestAmount % (emptySlots.size() + 1);
        ItemStack sorting = inputSlots[targetIndex].getStack().copyWithCount(1);
        inputSlots[targetIndex].setStackSize(extra > 0 ? countPerSlot + 1 : countPerSlot, Action.EXECUTE);
        for (int i = 0; i < emptySlots.size(); i++) {
            int p = i + 1;
            emptySlots.get(i).setStack(p < extra ? sorting.copyWithCount(countPerSlot + 1) : sorting.copyWithCount(countPerSlot));
        }
    }

}
