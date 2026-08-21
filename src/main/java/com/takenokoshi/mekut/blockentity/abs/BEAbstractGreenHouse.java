package com.takenokoshi.mekut.blockentity.abs;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekaddonlib.blockentity.base.BEMultiScaledProgressMachine;
import com.takenokoshi.mekaddonlib.blockentity.component.EjectorComponentUtils;
import com.takenokoshi.mekaddonlib.blockentity.component.RelativeEjectionTargetModifier;
import com.takenokoshi.mekaddonlib.blockentity.interfaces.IHasGuiSizeOffset;
import com.takenokoshi.mekaddonlib.inventory.slot.LimitChangedInputInventorySlot;
import com.takenokoshi.mekaddonlib.inventory.slot.LimitChangedOutputInventorySlot;
import com.takenokoshi.mekaddonlib.recipe.cached.ICachedRecipe;
import com.takenokoshi.mekaddonlib.recipe.type.IMekALRecipeTypeProvider;
import com.takenokoshi.mekut.blockentity.interfaces.IHasMachineEnergyContainer;
import com.takenokoshi.mekut.inventory.slot.FluidFillOrSupplierSlot;
import com.takenokoshi.mekut.recipe.cached.GreenHouseCachedRecipe;
import com.takenokoshi.mekut.recipe.input.AdvancedFluidInputHandler;
import com.takenokoshi.mekut.recipe.inputcache.MekUtTripleInputRecipeCache;
import com.takenokoshi.mekut.recipe.lookup.recipe.ITripleInputRecipeLookupHandler;
import com.takenokoshi.mekut.recipe.output.BasicChanceOutputHandler;
import com.takenokoshi.mekut.recipe.recipe.prefab.GreenHouseRecipe;
import com.takenokoshi.mekut.registries.MekUtRecipeTypes;

import mekanism.api.IContentsListener;
import mekanism.api.RelativeSide;
import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.api.inventory.IInventorySlot;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.inputs.InputHelper;
import mekanism.common.attachments.component.AttachedSideConfig;
import mekanism.common.attachments.component.AttachedSideConfig.LightConfigInfo;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.attachments.containers.fluid.FluidTanksBuilder;
import mekanism.common.attachments.containers.item.ItemSlotsBuilder;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.capabilities.energy.MachineEnergyContainer;
import mekanism.common.capabilities.fluid.BasicFluidTank;
import mekanism.common.capabilities.holder.energy.EnergyContainerHelper;
import mekanism.common.capabilities.holder.energy.IEnergyContainerHolder;
import mekanism.common.capabilities.holder.fluid.FluidTankHelper;
import mekanism.common.capabilities.holder.fluid.IFluidTankHolder;
import mekanism.common.capabilities.holder.slot.IInventorySlotHolder;
import mekanism.common.capabilities.holder.slot.InventorySlotHelper;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.inventory.slot.EnergyInventorySlot;
import mekanism.common.inventory.slot.OutputInventorySlot;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.registration.impl.ItemRegistryObject;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.tile.interfaces.IBoundingBlock;
import mekanism.common.util.WorldUtils;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;

public abstract class BEAbstractGreenHouse extends BEMultiScaledProgressMachine<GreenHouseRecipe>
        implements ITripleInputRecipeLookupHandler.IGreenHouseRecipeLookupHandler, IHasMachineEnergyContainer,
        IHasGuiSizeOffset, IBoundingBlock {

    public static final List<RecipeError> TRACKED_ERROR_TYPES = List.of(
            RecipeError.NOT_ENOUGH_ENERGY,
            RecipeError.NOT_ENOUGH_INPUT,
            RecipeError.NOT_ENOUGH_OUTPUT_SPACE,
            RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT);

    public static final AttachedSideConfig SIDE_CONFIG = Util.make(() -> {
        Map<TransmissionType, LightConfigInfo> configInfo = new EnumMap<>(TransmissionType.class);
        configInfo.put(TransmissionType.ITEM, LightConfigInfo.MACHINE);
        configInfo.put(TransmissionType.FLUID, LightConfigInfo.INPUT_ONLY);
        configInfo.put(TransmissionType.ENERGY, LightConfigInfo.INPUT_ONLY);
        return new AttachedSideConfig(configInfo);
    });

    public static Consumer<ItemRegistryObject<?>> getContainerAdder(int fluidTankCapacity) {
        return (value) -> {
            value.addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                    .addBasic(3)
                    .addOutput(13)
                    .addEnergy()
                    .build());
            value.addAttachmentOnlyContainers(ContainerType.FLUID, () -> FluidTanksBuilder.builder()
                    .addBasic(fluidTankCapacity)
                    .build());
        };
    }

    private IExtendedFluidTank fertilizerTank;
    private MachineEnergyContainer<?> energyContainer;

    private LimitChangedInputInventorySlot cropSlot;
    private LimitChangedInputInventorySlot soilSlot;
    private FluidFillOrSupplierSlot fertilizerSlot;
    private OutputInventorySlot fertilizerReturnSlot;
    private LimitChangedOutputInventorySlot[] outputSlots;
    private EnergyInventorySlot energySlot;

    private final IInputHandler<ItemStack> cropHandler;
    private final IInputHandler<ItemStack> soilHandler;
    private final AdvancedFluidInputHandler fertilizerHandler;
    private final BasicChanceOutputHandler[] outputHandlers;

    public BEAbstractGreenHouse(Holder<Block> blockProvider, BlockPos pos, BlockState state,
            int baselineMaxOperations) {
        super(blockProvider, pos, state, TRACKED_ERROR_TYPES, baselineMaxOperations, GreenHouseRecipe::getDuration);

        configComponent.setupItemIOConfig(List.of(cropSlot, soilSlot), List.of(outputSlots), energySlot, false);
        configComponent.setupInputConfig(TransmissionType.FLUID, fertilizerTank);
        configComponent.setupInputConfig(TransmissionType.ENERGY, energyContainer);
        ejectorComponent = new TileComponentEjector(this).setOutputData(configComponent, TransmissionType.ITEM);

        this.cropHandler = InputHelper.getInputHandler(cropSlot, RecipeError.NOT_ENOUGH_INPUT);
        this.soilHandler = InputHelper.getInputHandler(soilSlot, RecipeError.NOT_ENOUGH_INPUT);
        this.fertilizerHandler = AdvancedFluidInputHandler.create(fertilizerTank, RecipeError.NOT_ENOUGH_INPUT);
        this.outputHandlers = new BasicChanceOutputHandler[12];
        for (int i = 0; i < outputHandlers.length; i++) {
            outputHandlers[i] = initOutputHandler(outputSlots[i], RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
        }
        fertilizerSlot.setSupplyingStackSetter(fertilizerHandler::setSuppliedStack);
        EjectorComponentUtils.setEjectionTargetModifier(ejectorComponent, TransmissionType.ITEM, RelativeSide.FRONT,
                new RelativeEjectionTargetModifier(1, 0, 0, RelativeSide.BACK));
        EjectorComponentUtils.setEjectionTargetModifier(ejectorComponent, TransmissionType.ITEM, RelativeSide.BACK,
                new RelativeEjectionTargetModifier(-1, 0, 0, RelativeSide.FRONT));
        EjectorComponentUtils.setEjectionTargetModifier(ejectorComponent, TransmissionType.ITEM, RelativeSide.LEFT,
                new RelativeEjectionTargetModifier(0, 1, 0, RelativeSide.RIGHT));
        EjectorComponentUtils.setEjectionTargetModifier(ejectorComponent, TransmissionType.ITEM, RelativeSide.RIGHT,
                new RelativeEjectionTargetModifier(0, -1, 0, RelativeSide.LEFT));
        EjectorComponentUtils.setEjectionTargetModifier(ejectorComponent, TransmissionType.ITEM, RelativeSide.TOP,
                new RelativeEjectionTargetModifier(0, 0, 2, RelativeSide.BOTTOM));
    }

    protected abstract BasicChanceOutputHandler initOutputHandler(IInventorySlot slot, RecipeError notEnoughSpaceError);

    @Override
    protected IEnergyContainerHolder getInitialEnergyContainers(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        EnergyContainerHelper builder = EnergyContainerHelper.forSideWithConfig(this);
        builder.addContainer(energyContainer = MachineEnergyContainer.input(this, recipeCacheUnpauseListener));
        return builder.build();
    }

    @Override
    protected @Nullable IFluidTankHolder getInitialFluidTanks(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        FluidTankHelper builder = FluidTankHelper.forSideWithConfig(this);
        builder.addTank(fertilizerTank = BasicFluidTank.input(initFluidTankCapacity(),
                (stack) -> containsRecipeCAB(cropHandler.getInput(), soilHandler.getInput(), stack),
                this::containsRecipeC,
                recipeCacheListener));
        return builder.build();
    }

    protected abstract int initFluidTankCapacity();

    @Override
    protected @Nullable IInventorySlotHolder getInitialInventory(IContentsListener listener,
            IContentsListener recipeCacheListener, IContentsListener recipeCacheUnpauseListener) {
        InventorySlotHelper builder = InventorySlotHelper.forSideWithConfig(this);
        builder.addSlot(cropSlot = LimitChangedInputInventorySlot.at(
                (stack) -> containsRecipeABC(stack, soilHandler.getInput(), fertilizerHandler.getInput()),
                this::containsRecipeA,
                recipeCacheListener, 64, 17, initItemSlotCapacity()));
        builder.addSlot(soilSlot = LimitChangedInputInventorySlot.at(
                (stack) -> containsRecipeBAC(cropHandler.getInput(), stack, fertilizerHandler.getInput()),
                this::containsRecipeB,
                recipeCacheListener, 64, 53, initItemSlotCapacity()));
        builder.addSlot(fertilizerSlot = FluidFillOrSupplierSlot.create(fertilizerTank, recipeCacheListener, 21, 22))
                .setSlotOverlay(SlotOverlay.MINUS);
        builder.addSlot(fertilizerReturnSlot = OutputInventorySlot.at(listener, 21, 53));
        outputSlots = new LimitChangedOutputInventorySlot[12];
        for (int index = 0; index < outputSlots.length; index++) {
            builder.addSlot(outputSlots[index] = LimitChangedOutputInventorySlot.at(recipeCacheUnpauseListener,
                    index % 4 * 18 + 116, index / 4 * 18 + 17, initItemSlotCapacity()));
        }
        builder.addSlot(
                energySlot = EnergyInventorySlot.fillOrConvert(energyContainer, this::getLevel, listener, 197, 35));
        return builder.build();
    }

    protected abstract int initItemSlotCapacity();

    @Override
    public int getExtraWidth() {
        return 54;
    }

    @Override
    protected boolean onUpdateServer() {
        boolean value = super.onUpdateServer();
        clientEnergyUsed = recipeCacheLookupMonitor.updateAndProcess(energyContainer);
        fertilizerSlot.fillTank(fertilizerReturnSlot);
        energySlot.fillContainerOrConvert();
        return value;
    }

    @Override
    public @Nullable GreenHouseRecipe getRecipe(int arg0) {
        return findFirstRecipe(cropHandler, soilHandler, fertilizerHandler);
    }

    @Override
    public @NotNull ICachedRecipe<GreenHouseRecipe> createNewCachedRecipe(@NotNull GreenHouseRecipe recipe, int arg1) {
        return new GreenHouseCachedRecipe(recipe, recheckAllRecipeErrors, cropHandler, soilHandler, fertilizerHandler,
                outputHandlers)
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
    public @NotNull IMekALRecipeTypeProvider<?, GreenHouseRecipe, MekUtTripleInputRecipeCache.ItemItemFluid<GreenHouseRecipe>> getRecipeType() {
        return MekUtRecipeTypes.GREEN_HOUSE;
    }

    public IExtendedFluidTank getFertilizerTank() {
        return fertilizerTank;
    }

    @Override
    public MachineEnergyContainer<?> getEnergyContainer() {
        return energyContainer;
    }

    @Override
    public <T> @Nullable T getOffsetCapabilityIfEnabled(@NotNull BlockCapability<T, @Nullable Direction> capability,
            Direction side, @NotNull Vec3i offset) {
        if (side == Direction.UP || offset.getY() >= 1) {
            return null;
        } else if (capability == Capabilities.ITEM.block()) {
            return itemHandlerManager.resolve(capability, side);
        } else if (capability == Capabilities.FLUID.block()) {
            return fluidHandlerManager.resolve(capability, side);
        } else if (capability == Capabilities.ENERGY.block()) {
            return energyHandlerManager.resolve(capability, side);
        }
        return WorldUtils.getCapability(level, capability, worldPosition, null, this, side);
    }

    @Override
    public boolean isOffsetCapabilityDisabled(@NotNull BlockCapability<?, @Nullable Direction> capability,
            Direction side, @NotNull Vec3i offset) {
        return side == Direction.UP || offset.getY() >= 1;
    }

}
