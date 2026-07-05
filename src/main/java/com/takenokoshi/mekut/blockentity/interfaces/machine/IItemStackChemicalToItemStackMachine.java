package com.takenokoshi.mekut.blockentity.interfaces.machine;

import java.util.List;
import java.util.function.Consumer;

import com.takenokoshi.mekut.blockentity.interfaces.IHasInputChemicalTank;
import com.takenokoshi.mekut.blockentity.interfaces.IScaledProgressProvider;
import com.takenokoshi.mekut.recipe.inputcache.MekUtDoubleInputRecipeCache;
import com.takenokoshi.mekut.recipe.lookup.IMekUtRecipeTypedLookupHandler;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.energy.IEnergyContainer;
import mekanism.api.inventory.IInventorySlot;
import mekanism.api.recipes.ItemStackChemicalToItemStackRecipe;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.attachments.containers.chemical.ChemicalTanksBuilder;
import mekanism.common.attachments.containers.item.ItemSlotsBuilder;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.registration.impl.ItemRegistryObject;
import mekanism.common.tile.base.TileEntityMekanism;
import mekanism.common.tile.component.TileComponentConfig;
import mekanism.common.tile.component.TileComponentEjector;
import net.minecraft.world.item.ItemStack;

public interface IItemStackChemicalToItemStackMachine extends
        IMekUtRecipeTypedLookupHandler<ItemStackChemicalToItemStackRecipe, MekUtDoubleInputRecipeCache.MekUtItemChemical<ItemStackChemicalToItemStackRecipe>>,
        IHasInputChemicalTank, IScaledProgressProvider {

    public static final List<RecipeError> TRACKED_ERROR_TYPES = List.of(
            RecipeError.NOT_ENOUGH_ENERGY,
            RecipeError.NOT_ENOUGH_INPUT,
            RecipeError.NOT_ENOUGH_SECONDARY_INPUT,
            RecipeError.NOT_ENOUGH_OUTPUT_SPACE,
            RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT);

    default boolean containsRecipeA(ItemStack inputA) {
        return getRecipeType().getInputCache().containsInputA(getLevel(), inputA);
    }

    default boolean containsRecipeB(ChemicalStack inputB) {
        return getRecipeType().getInputCache().containsInputB(getLevel(), inputB);
    }

    default boolean containsRecipeAB(ItemStack inputA, ChemicalStack inputB) {
        return getRecipeType().getInputCache().containsInputAB(getLevel(), inputA, inputB);
    }

    default boolean containsRecipeBA(ItemStack inputA, ChemicalStack inputB) {
        return getRecipeType().getInputCache().containsInputBA(getLevel(), inputA, inputB);
    }

    default ItemStackChemicalToItemStackRecipe findFirstRecipe(IInputHandler<ItemStack> inputHandlerA,
            IInputHandler<ChemicalStack> inputHandlerB) {
        return getRecipeType().getInputCache().findFirstRecipe(getLevel(), inputHandlerA.getInput(),
                inputHandlerB.getInput());
    }

    public static void addContainersToItem(ItemRegistryObject<?> item) {
        item
                .addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                        .addInput(1)
                        .addChemicalFillOrConvertSlot(0)
                        .addOutput(1)
                        .addEnergy()
                        .build())
                .addAttachmentOnlyContainers(ContainerType.CHEMICAL, () -> ChemicalTanksBuilder.builder()
                        .addBasic(200000)
                        .build());
    }

    static Consumer<ItemRegistryObject<?>> getContainerAdder(long chemicalTankCapacity) {
        return value -> {
            value.addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                    .addInput(1)
                    .addChemicalFillOrConvertSlot(0)
                    .addOutput(1)
                    .addEnergy()
                    .build());
            value.addAttachmentOnlyContainers(ContainerType.CHEMICAL, () -> ChemicalTanksBuilder.builder()
                    .addBasic(chemicalTankCapacity)
                    .build());
        };
    }

    public static TileComponentEjector setUpConfig(TileEntityMekanism machine, TileComponentConfig config,
            IInventorySlot inputSlot, IInventorySlot outputSlot, IInventorySlot secondarySlot,
            IInventorySlot energySlot, IChemicalTank chemicalTank, IEnergyContainer energyContainer) {
        config.setupItemIOExtraConfig(inputSlot, outputSlot, secondarySlot, energySlot);
        config.setupInputConfig(TransmissionType.CHEMICAL, chemicalTank);
        config.setupInputConfig(TransmissionType.ENERGY, energyContainer);
        return new TileComponentEjector(machine).setOutputData(config,
                new TransmissionType[] { TransmissionType.ITEM });
    }

}
