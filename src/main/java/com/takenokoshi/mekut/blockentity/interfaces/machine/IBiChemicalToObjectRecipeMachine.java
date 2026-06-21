package com.takenokoshi.mekut.blockentity.interfaces.machine;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

import com.takenokoshi.mekut.recipe.lookup.recipe.IEithersideChemicalRecipeLookupHandler;

import mekanism.api.RelativeSide;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.vanilla_input.BiChemicalRecipeInput;
import mekanism.common.attachments.component.AttachedSideConfig;
import mekanism.common.attachments.component.AttachedSideConfig.LightConfigInfo;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.attachments.containers.chemical.ChemicalTanksBuilder;
import mekanism.common.attachments.containers.item.ItemSlotsBuilder;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.registration.impl.ItemRegistryObject;
import mekanism.common.tile.component.config.DataType;
import net.minecraft.Util;

public interface IBiChemicalToObjectRecipeMachine<RECIPE extends MekanismRecipe<BiChemicalRecipeInput> & BiPredicate<ChemicalStack, ChemicalStack>>
        extends IEithersideChemicalRecipeLookupHandler<RECIPE> {

    public static final List<RecipeError> TRACKED_ERROR_TYPES = List.of(RecipeError.NOT_ENOUGH_ENERGY,
            RecipeError.NOT_ENOUGH_ENERGY_REDUCED_RATE, RecipeError.NOT_ENOUGH_LEFT_INPUT,
            RecipeError.NOT_ENOUGH_RIGHT_INPUT, RecipeError.NOT_ENOUGH_OUTPUT_SPACE,
            RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT);

    IChemicalTank getLeftTank();

    IChemicalTank getRightTank();

    double getScaledProgress();

    public static void addContainersBiChemicalToItem(ItemRegistryObject<?> item) {
        item
                .addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                        .addChemicalFillOrConvertSlot(0)
                        .addChemicalFillOrConvertSlot(1)
                        .addOutput(1)
                        .addEnergy()
                        .build())
                .addAttachmentOnlyContainers(ContainerType.CHEMICAL, () -> ChemicalTanksBuilder.builder()
                        .addBasic(Long.MAX_VALUE)
                        .addBasic(Long.MAX_VALUE)
                        .build());
    }

    public static void addContainersBiChemicalToChemical(ItemRegistryObject<?> item) {
        item
                .addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                        .addChemicalFillOrConvertSlot(0)
                        .addChemicalFillOrConvertSlot(1)
                        .addChemicalDrainSlot(2)
                        .addEnergy()
                        .build())
                .addAttachmentOnlyContainers(ContainerType.CHEMICAL, () -> ChemicalTanksBuilder.builder()
                        .addBasic(20000)
                        .addBasic(20000)
                        .addBasic(200000)
                        .build());
    }

    public static final AttachedSideConfig SIDE_CONFIG_TO_ITEM = Util.make(() -> {
        Map<TransmissionType, LightConfigInfo> configInfo = new EnumMap<>(TransmissionType.class);
        configInfo.put(TransmissionType.ITEM, AttachedSideConfig.LightConfigInfo.TWO_INPUT_ITEM);
        Map<RelativeSide, DataType> sideConfig = new EnumMap<>(RelativeSide.class);
        sideConfig.put(RelativeSide.LEFT, DataType.INPUT_1);
        sideConfig.put(RelativeSide.RIGHT, DataType.INPUT_2);
        configInfo.put(TransmissionType.CHEMICAL, new LightConfigInfo(sideConfig, false));
        configInfo.put(TransmissionType.ENERGY, AttachedSideConfig.LightConfigInfo.INPUT_ONLY);
        return new AttachedSideConfig(configInfo);
    });
}
