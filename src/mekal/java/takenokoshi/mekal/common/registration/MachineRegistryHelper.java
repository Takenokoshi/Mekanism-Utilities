package takenokoshi.mekal.common.registration;

import mekanism.api.recipes.ItemStackToFluidRecipe;
import mekanism.api.recipes.ItemStackToItemStackRecipe;
import mekanism.api.recipes.vanilla_input.SingleFluidRecipeInput;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.attachments.containers.fluid.FluidTanksBuilder;
import mekanism.common.attachments.containers.item.ItemSlotsBuilder;
import mekanism.common.recipe.IMekanismRecipeTypeProvider;
import mekanism.common.recipe.lookup.cache.DoubleInputRecipeCache;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.SingleFluid;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.SingleItem;
import mekanism.common.recipe.lookup.cache.SingleInputRecipeCache;
import mekanism.common.registration.impl.ItemRegistryObject;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import takenokoshi.mekal.api.recipes.FluidToItemStackRecipe;
import takenokoshi.mekal.api.recipes.ItemStackFluidToItemStackRecipe;
import takenokoshi.mekal.api.recipes.vanilla_input.SingleItemFluidRecipeInput;
import takenokoshi.mekal.common.recipe.lookup.cache.MekALInputRecipeCache.ItemFluid;
import takenokoshi.mekal.common.tile.prefab.TileEntityFluidToItemStackMachine;
import takenokoshi.mekal.common.tile.prefab.TileEntityItemStackFluidToItemStackMachine;
import takenokoshi.mekal.common.tile.prefab.TileEntityItemStackToFluidMachine;

public class MachineRegistryHelper {
    public static <ITEM extends Item> void addAttachmentOnlyContainersElectricMachine(
            ItemRegistryObject<ITEM> itemHolder,
            IMekanismRecipeTypeProvider<SingleRecipeInput, ItemStackToItemStackRecipe, SingleItem<ItemStackToItemStackRecipe>> recipeType) {
        itemHolder
                .addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                        .addInput(recipeType, SingleInputRecipeCache::containsInput)
                        .addOutput()
                        .addEnergy()
                        .build());
    }

    public static <ITEM extends Item> void addAttachmentOnlyContainersItemFluidToItemMachine(
            ItemRegistryObject<ITEM> itemHolder,
            IMekanismRecipeTypeProvider<SingleItemFluidRecipeInput, ItemStackFluidToItemStackRecipe, ItemFluid<ItemStackFluidToItemStackRecipe>> recipeType) {
        itemHolder
                .addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                        .addInput(recipeType, DoubleInputRecipeCache::containsInputA)
                        .addBasic(1)
                        .addOutput(2)
                        .addEnergy()
                        .build())
                .addAttachmentOnlyContainers(ContainerType.FLUID, () -> FluidTanksBuilder.builder()
                        .addBasic(TileEntityItemStackFluidToItemStackMachine.MAX_FLUID,
                                fluid -> recipeType.getInputCache().containsInputB(null, fluid))
                        .build());
    }

    public static <ITEM extends Item> void addAttachmentOnlyContainersItemToFluidMachine(
            ItemRegistryObject<ITEM> itemHolder,
            IMekanismRecipeTypeProvider<SingleRecipeInput, ItemStackToFluidRecipe, SingleItem<ItemStackToFluidRecipe>> recipeType) {
        itemHolder
                .addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                        .addInput(recipeType, SingleInputRecipeCache::containsInput)
                        .addOutput()
                        .addFluidDrainSlot(0)
                        .addEnergy()
                        .build())
                .addAttachmentOnlyContainers(ContainerType.FLUID, () -> FluidTanksBuilder.builder()
                        .addBasic(TileEntityItemStackToFluidMachine.MAX_FLUID)
                        .build());
    }

    public static <ITEM extends Item> void addAttachmentOnlyContainersFluidToItemMachine(
            ItemRegistryObject<ITEM> itemHolder,
            IMekanismRecipeTypeProvider<SingleFluidRecipeInput, FluidToItemStackRecipe, SingleFluid<FluidToItemStackRecipe>> recipeType) {
        itemHolder
                .addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                        .addBasic(1)
                        .addOutput(2)
                        .addEnergy()
                        .build())
                .addAttachmentOnlyContainers(ContainerType.FLUID, () -> FluidTanksBuilder.builder()
                        .addBasic(TileEntityFluidToItemStackMachine.MAX_FLUID,
                                fluid -> recipeType.getInputCache().containsInput(null, fluid))
                        .build());
    }
}
