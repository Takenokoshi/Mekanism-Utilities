package com.takenokoshi.mekut.recipe.serializer;

import java.util.List;
import java.util.Optional;

import com.mojang.datafixers.util.Function5;
import com.mojang.datafixers.util.Function8;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.takenokoshi.mekut.recipe.recipe.prefab.ItemStackListFluidChemicalToItemFluidChemicalRecipe;
import com.takenokoshi.mekut.recipe.recipe.prefab.ItemStackListFluidChemicalToItemRecipe;

import mekanism.api.SerializationConstants;
import mekanism.api.SerializerHelper;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.recipe.serializer.MekanismRecipeSerializer;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;

public final class MekUtRecipeSerializerBuilder {

    public static <RECIPE extends ItemStackListFluidChemicalToItemRecipe> MekanismRecipeSerializer<RECIPE> itemStackListFluidChemicalToItem(
            Function5<List<ItemStackIngredient>, Optional<FluidStackIngredient>, Optional<ChemicalStackIngredient>, ItemStack, Long, RECIPE> factory) {
        return new MekanismRecipeSerializer<>(RecordCodecBuilder.mapCodec(instance -> instance.group(
                IngredientCreatorAccess.item().codec().listOf().fieldOf(MekUtSerializationConstants.LIST_ITEM_INPUT)
                        .forGetter(ItemStackListFluidChemicalToItemRecipe::getItemInputs),
                IngredientCreatorAccess.fluid().codec().optionalFieldOf(SerializationConstants.FLUID_INPUT)
                        .forGetter(ItemStackListFluidChemicalToItemRecipe::getFluidInputAsOptional),
                IngredientCreatorAccess.chemicalStack().codec().optionalFieldOf(SerializationConstants.CHEMICAL_INPUT)
                        .forGetter(ItemStackListFluidChemicalToItemRecipe::getChemicalInputAsOptional),
                ItemStack.CODEC.fieldOf(SerializationConstants.OUTPUT)
                        .forGetter(ItemStackListFluidChemicalToItemRecipe::getOutputItem),
                SerializerHelper.POSITIVE_LONG_CODEC.optionalFieldOf(SerializationConstants.ENERGY_REQUIRED, 0L)
                        .forGetter(ItemStackListFluidChemicalToItemRecipe::getEnergyRequired))
                .apply(instance, factory)),
                StreamCodec.composite(
                        MekUtCodecConstants.ITEMSTACK_INGREDIENT_LIST_STREAM_CODEC,
                        ItemStackListFluidChemicalToItemRecipe::getItemInputs,
                        MekUtCodecConstants.FLUIDSTACK_INGREDIENT_OPTIONAL_STREAM_CODEC,
                        ItemStackListFluidChemicalToItemRecipe::getFluidInputAsOptional,
                        MekUtCodecConstants.CHEMICALSTACK_INGREDIENT_OPTIONAL_STREAM_CODEC,
                        ItemStackListFluidChemicalToItemRecipe::getChemicalInputAsOptional,
                        ItemStack.STREAM_CODEC,
                        ItemStackListFluidChemicalToItemRecipe::getOutputItem,
                        ByteBufCodecs.VAR_LONG,
                        ItemStackListFluidChemicalToItemRecipe::getEnergyRequired, factory));
    }

    public static <RECIPE extends ItemStackListFluidChemicalToItemFluidChemicalRecipe> MekanismRecipeSerializer<RECIPE> itemStackListFluidChemicalToItemFluidChemical(
            Function8<List<ItemStackIngredient>, Optional<FluidStackIngredient>, Optional<ChemicalStackIngredient>, ItemStack, FluidStack, ChemicalStack, Long, Integer, RECIPE> factory) {
        return new MekanismRecipeSerializer<>(RecordCodecBuilder.mapCodec(instance -> instance.group(
                IngredientCreatorAccess.item().codec().listOf().fieldOf(MekUtSerializationConstants.LIST_ITEM_INPUT)
                        .forGetter(ItemStackListFluidChemicalToItemFluidChemicalRecipe::getItemInputs),
                IngredientCreatorAccess.fluid().codec().optionalFieldOf(SerializationConstants.FLUID_INPUT)
                        .forGetter(ItemStackListFluidChemicalToItemFluidChemicalRecipe::getFluidInputAsOptional),
                IngredientCreatorAccess.chemicalStack().codec().optionalFieldOf(SerializationConstants.CHEMICAL_INPUT)
                        .forGetter(ItemStackListFluidChemicalToItemFluidChemicalRecipe::getChemicalInputAsOptional),
                ItemStack.CODEC.optionalFieldOf(SerializationConstants.ITEM_OUTPUT, ItemStack.EMPTY)
                        .forGetter(ItemStackListFluidChemicalToItemFluidChemicalRecipe::getItemOutput),
                FluidStack.CODEC.optionalFieldOf(SerializationConstants.FLUID_OUTPUT, FluidStack.EMPTY)
                        .forGetter(ItemStackListFluidChemicalToItemFluidChemicalRecipe::getFluidOutput),
                ChemicalStack.CODEC.optionalFieldOf(SerializationConstants.CHEMICAL_OUTPUT, ChemicalStack.EMPTY)
                        .forGetter(ItemStackListFluidChemicalToItemFluidChemicalRecipe::getChemicalOutput),
                SerializerHelper.POSITIVE_LONG_CODEC.optionalFieldOf(SerializationConstants.ENERGY_REQUIRED, 0L)
                        .forGetter(ItemStackListFluidChemicalToItemFluidChemicalRecipe::getEnergyRequired),
                ExtraCodecs.POSITIVE_INT.fieldOf(SerializationConstants.DURATION)
                        .forGetter(ItemStackListFluidChemicalToItemFluidChemicalRecipe::getDuration))
                .apply(instance, factory)),
                MekUtStreamCodecBuilder.composite08(
                        MekUtCodecConstants.ITEMSTACK_INGREDIENT_LIST_STREAM_CODEC,
                        ItemStackListFluidChemicalToItemFluidChemicalRecipe::getItemInputs,
                        MekUtCodecConstants.FLUIDSTACK_INGREDIENT_OPTIONAL_STREAM_CODEC,
                        ItemStackListFluidChemicalToItemFluidChemicalRecipe::getFluidInputAsOptional,
                        MekUtCodecConstants.CHEMICALSTACK_INGREDIENT_OPTIONAL_STREAM_CODEC,
                        ItemStackListFluidChemicalToItemFluidChemicalRecipe::getChemicalInputAsOptional,
                        ItemStack.STREAM_CODEC,
                        ItemStackListFluidChemicalToItemFluidChemicalRecipe::getItemOutput,
                        FluidStack.STREAM_CODEC,
                        ItemStackListFluidChemicalToItemFluidChemicalRecipe::getFluidOutput,
                        ChemicalStack.STREAM_CODEC,
                        ItemStackListFluidChemicalToItemFluidChemicalRecipe::getChemicalOutput,
                        ByteBufCodecs.VAR_LONG,
                        ItemStackListFluidChemicalToItemFluidChemicalRecipe::getEnergyRequired,
                        ByteBufCodecs.VAR_INT,
                        ItemStackListFluidChemicalToItemFluidChemicalRecipe::getDuration,
                        factory));
    }

}