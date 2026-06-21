package com.takenokoshi.mekut.blockentity.interfaces.machine;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.takenokoshi.mekut.recipe.inputcache.MUSingleInputRecipeCache;
import com.takenokoshi.mekut.recipe.lookup.IMekUtRecipeTypedLookupHandler;

import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.api.inventory.IInventorySlot;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.common.attachments.component.AttachedSideConfig;
import mekanism.common.attachments.component.AttachedSideConfig.LightConfigInfo;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.attachments.containers.fluid.FluidTanksBuilder;
import mekanism.common.attachments.containers.item.ItemSlotsBuilder;
import mekanism.common.capabilities.energy.MachineEnergyContainer;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.registration.impl.ItemRegistryObject;
import mekanism.common.tile.component.TileComponentConfig;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.tile.prefab.TileEntityConfigurableMachine;
import net.minecraft.Util;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.fluids.FluidStack;

public interface IFluidToObjectMachine<RECIPE extends Recipe<?>> extends
        IMekUtRecipeTypedLookupHandler<RECIPE, MUSingleInputRecipeCache.MUSingleFluid<RECIPE>> {

    public static final List<RecipeError> TRACKED_ERROR_TYPES = List.of(
            RecipeError.NOT_ENOUGH_ENERGY,
            RecipeError.NOT_ENOUGH_INPUT,
            RecipeError.NOT_ENOUGH_OUTPUT_SPACE,
            RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT);

    IExtendedFluidTank getInputTank();

    double getScaledProgress();

    default boolean containsRecipe(FluidStack stack) {
        return getRecipeType().getInputCache().containsInput(getLevel(), stack);
    }

    default RECIPE findFirstRecipe(IInputHandler<FluidStack> inputHandler) {
        return getRecipeType().getInputCache().findFirstRecipe(getLevel(), inputHandler.getInput());
    }

    public static TileComponentEjector setUpToItemConfig(TileEntityConfigurableMachine machine,
            TileComponentConfig config, IInventorySlot inputSlot, IInventorySlot outputSlot,
            IInventorySlot bucketReturnSlot, IInventorySlot energySlot, IExtendedFluidTank inputTank,
            MachineEnergyContainer<?> energyContainer) {
        config.setupItemIOExtraConfig(inputSlot, outputSlot, bucketReturnSlot, energySlot);
        config.setupInputConfig(TransmissionType.FLUID, inputTank);
        config.setupInputConfig(TransmissionType.ENERGY, energyContainer);
        return new TileComponentEjector(machine).setOutputData(config,
                new TransmissionType[] { TransmissionType.ITEM });
    }

    public static void addContainersFluidToItem(ItemRegistryObject<?> item) {
        item
                .addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                        .addFluidFillSlot(0)
                        .addOutput(2)
                        .addEnergy()
                        .build())
                .addAttachmentOnlyContainers(ContainerType.FLUID, () -> FluidTanksBuilder.builder()
                        .addBasic(20000)
                        .build());
    }

    public static final AttachedSideConfig SIDE_CONFIG_TO_ITEM = Util.make(() -> {
        Map<TransmissionType, LightConfigInfo> configInfo = new EnumMap<>(TransmissionType.class);
        configInfo.put(TransmissionType.ITEM, AttachedSideConfig.LightConfigInfo.EXTRA_MACHINE);
        configInfo.put(TransmissionType.FLUID, AttachedSideConfig.LightConfigInfo.INPUT_ONLY);
        configInfo.put(TransmissionType.ENERGY, AttachedSideConfig.LightConfigInfo.INPUT_ONLY);
        return new AttachedSideConfig(configInfo);
    });
}
