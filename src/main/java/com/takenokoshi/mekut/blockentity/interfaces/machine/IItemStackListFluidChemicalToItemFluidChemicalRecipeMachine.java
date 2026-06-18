package com.takenokoshi.mekut.blockentity.interfaces.machine;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.takenokoshi.mekut.recipe.lookup.recipe.IItemStackListFluidChemicalRecipeLookupHandler;
import com.takenokoshi.mekut.recipe.recipe.prefab.ItemStackListFluidChemicalToItemFluidChemicalRecipe;

import mekanism.api.chemical.IChemicalTank;
import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.common.attachments.component.AttachedSideConfig;
import mekanism.common.attachments.component.AttachedSideConfig.LightConfigInfo;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.attachments.containers.chemical.ChemicalTanksBuilder;
import mekanism.common.attachments.containers.fluid.FluidTanksBuilder;
import mekanism.common.attachments.containers.item.ItemSlotsBuilder;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.registration.impl.ItemRegistryObject;
import net.minecraft.Util;

public interface IItemStackListFluidChemicalToItemFluidChemicalRecipeMachine
        extends IItemStackListFluidChemicalRecipeLookupHandler<ItemStackListFluidChemicalToItemFluidChemicalRecipe> {

    public static final List<RecipeError> TRACKED_ERROR_TYPES = List.of(
            RecipeError.NOT_ENOUGH_ENERGY,
            RecipeError.NOT_ENOUGH_INPUT,
            RecipeError.NOT_ENOUGH_OUTPUT_SPACE,
            RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT);

    IExtendedFluidTank getInputFluidTank();

    IChemicalTank getInputChemicalTank();

    IExtendedFluidTank getOutputFluidTank();

    IChemicalTank getOutputChemicalTank();

    double getScaledProgress();

    public static void addContainersToItem(ItemRegistryObject<?> item) {
        item
                .addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                        .addInput(9)
                        .addFluidFillSlot(0)
                        .addChemicalFillOrConvertSlot(0)
                        .addChemicalDrainSlot(1)
                        .addFluidDrainSlot(1)
                        .addOutput(3)
                        .addEnergy()
                        .build())
                .addAttachmentOnlyContainers(ContainerType.FLUID, () -> FluidTanksBuilder.builder()
                        .addBasic(20000)
                        .addBasic(20000)
                        .build())
                .addAttachmentOnlyContainers(ContainerType.CHEMICAL, () -> ChemicalTanksBuilder.builder()
                        .addBasic(20000)
                        .addBasic(20000)
                        .build());
    }

    public static final AttachedSideConfig SIDE_CONFIG = Util.make(() -> {
        Map<TransmissionType, LightConfigInfo> configInfo = new EnumMap<>(TransmissionType.class);
        configInfo.put(TransmissionType.ITEM, AttachedSideConfig.LightConfigInfo.MACHINE);
        configInfo.put(TransmissionType.FLUID, AttachedSideConfig.LightConfigInfo.OUT_EJECT);
        configInfo.put(TransmissionType.CHEMICAL, AttachedSideConfig.LightConfigInfo.OUT_EJECT);
        configInfo.put(TransmissionType.ENERGY, AttachedSideConfig.LightConfigInfo.INPUT_ONLY);
        return new AttachedSideConfig(configInfo);
    });
}
