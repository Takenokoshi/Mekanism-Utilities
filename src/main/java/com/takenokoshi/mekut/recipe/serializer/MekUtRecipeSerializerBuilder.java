package com.takenokoshi.mekut.recipe.serializer;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

import com.mojang.datafixers.util.Function3;
import com.mojang.datafixers.util.Function4;
import com.mojang.datafixers.util.Function5;
import com.mojang.datafixers.util.Function8;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.takenokoshi.mekut.recipe.output.MekUtChanceOutput;
import com.takenokoshi.mekut.recipe.recipe.prefab.BiChemicalToItemRecipe;
import com.takenokoshi.mekut.recipe.recipe.prefab.ChemicalToChemicalHeatRecipe;
import com.takenokoshi.mekut.recipe.recipe.prefab.FluidToItemRecipe;
import com.takenokoshi.mekut.recipe.recipe.prefab.GreenHouseCropRecipe;
import com.takenokoshi.mekut.recipe.recipe.prefab.GreenHouseFertilizerRecipe;
import com.takenokoshi.mekut.recipe.recipe.prefab.GreenHouseRecipe;
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
                        ItemStack.OPTIONAL_STREAM_CODEC,
                        ItemStackListFluidChemicalToItemFluidChemicalRecipe::getItemOutput,
                        FluidStack.OPTIONAL_STREAM_CODEC,
                        ItemStackListFluidChemicalToItemFluidChemicalRecipe::getFluidOutput,
                        ChemicalStack.OPTIONAL_STREAM_CODEC,
                        ItemStackListFluidChemicalToItemFluidChemicalRecipe::getChemicalOutput,
                        ByteBufCodecs.VAR_LONG,
                        ItemStackListFluidChemicalToItemFluidChemicalRecipe::getEnergyRequired,
                        ByteBufCodecs.VAR_INT,
                        ItemStackListFluidChemicalToItemFluidChemicalRecipe::getDuration,
                        factory));
    }

    public static <RECIPE extends BiChemicalToItemRecipe> MekanismRecipeSerializer<RECIPE> chemicalChemicalToItem(
            Function3<ChemicalStackIngredient, ChemicalStackIngredient, ItemStack, RECIPE> factory) {
        return new MekanismRecipeSerializer<>(RecordCodecBuilder.mapCodec(instance -> instance.group(
                IngredientCreatorAccess.chemicalStack().codec().fieldOf(SerializationConstants.LEFT_INPUT)
                        .forGetter(BiChemicalToItemRecipe::getLeftInput),
                IngredientCreatorAccess.chemicalStack().codec().fieldOf(SerializationConstants.RIGHT_INPUT)
                        .forGetter(BiChemicalToItemRecipe::getRightInput),
                ItemStack.CODEC.fieldOf(SerializationConstants.OUTPUT)
                        .forGetter(BiChemicalToItemRecipe::getOutputRaw))
                .apply(instance, factory)),
                StreamCodec.composite(IngredientCreatorAccess.chemicalStack().streamCodec(),
                        BiChemicalToItemRecipe::getLeftInput,
                        IngredientCreatorAccess.chemicalStack().streamCodec(),
                        BiChemicalToItemRecipe::getRightInput,
                        ItemStack.STREAM_CODEC, BiChemicalToItemRecipe::getOutputRaw, factory));
    }

    public static <RECIPE extends FluidToItemRecipe> MekanismRecipeSerializer<RECIPE> fluidToItem(
            BiFunction<FluidStackIngredient, ItemStack, RECIPE> factory) {
        return new MekanismRecipeSerializer<>(RecordCodecBuilder.mapCodec(instance -> instance.group(
                IngredientCreatorAccess.fluid().codec().fieldOf(SerializationConstants.INPUT)
                        .forGetter(FluidToItemRecipe::getInput),
                ItemStack.CODEC.fieldOf(SerializationConstants.OUTPUT)
                        .forGetter(FluidToItemRecipe::getOutputRaw))
                .apply(instance, factory)),
                StreamCodec.composite(
                        IngredientCreatorAccess.fluid().streamCodec(),
                        FluidToItemRecipe::getInput,
                        ItemStack.STREAM_CODEC,
                        FluidToItemRecipe::getOutputRaw,
                        factory));
    }

    public static <RECIPE extends ChemicalToChemicalHeatRecipe> MekanismRecipeSerializer<RECIPE> chemicalToChemicalHeat(
            Function3<ChemicalStackIngredient, ChemicalStack, Double, RECIPE> factory) {
        return new MekanismRecipeSerializer<>(RecordCodecBuilder.mapCodec(instance -> instance.group(
                IngredientCreatorAccess.chemicalStack().codec().fieldOf(SerializationConstants.INPUT)
                        .forGetter(ChemicalToChemicalHeatRecipe::getInput),
                ChemicalStack.CODEC.fieldOf(SerializationConstants.OUTPUT)
                        .forGetter(ChemicalToChemicalHeatRecipe::getOutputRaw),
                Codec.DOUBLE.fieldOf(MekUtSerializationConstants.HEAT_GENERATION).forGetter(r -> r.heatGeneration))
                .apply(instance, factory)),
                StreamCodec.composite(
                        IngredientCreatorAccess.chemicalStack().streamCodec(),
                        ChemicalToChemicalHeatRecipe::getInput,
                        ChemicalStack.STREAM_CODEC,
                        ChemicalToChemicalHeatRecipe::getOutputRaw,
                        ByteBufCodecs.DOUBLE,
                        r -> r.heatGeneration,
                        factory));
    }

    public static <RECIPE extends GreenHouseCropRecipe> MekanismRecipeSerializer<RECIPE> greenHouseCrop(
            Function4<ItemStackIngredient, ItemStackIngredient, List<MekUtChanceOutput>, Integer, RECIPE> factory) {
        return new MekanismRecipeSerializer<>(RecordCodecBuilder.mapCodec(instance -> instance.group(
                IngredientCreatorAccess.item().codec().fieldOf("crop")
                        .forGetter(GreenHouseCropRecipe::getCropIngredient),
                IngredientCreatorAccess.item().codec().fieldOf("soil")
                        .forGetter(GreenHouseCropRecipe::getSoilIngredient),
                MekUtChanceOutput.CODEC.listOf(1, 12).fieldOf(SerializationConstants.OUTPUT)
                        .forGetter(GreenHouseCropRecipe::getOutputsRaw),
                Codec.INT.fieldOf(SerializationConstants.DURATION)
                        .forGetter(GreenHouseCropRecipe::getDuration))
                .apply(instance, factory)),
                StreamCodec.composite(
                        IngredientCreatorAccess.item().streamCodec(),
                        GreenHouseCropRecipe::getCropIngredient,
                        IngredientCreatorAccess.item().streamCodec(),
                        GreenHouseCropRecipe::getSoilIngredient,
                        MekUtChanceOutput.STREAM_CODEC.apply(ByteBufCodecs.list()),
                        GreenHouseCropRecipe::getOutputsRaw,
                        ByteBufCodecs.INT,
                        GreenHouseCropRecipe::getDuration,
                        factory));
    }

    public static <RECIPE extends GreenHouseFertilizerRecipe> MekanismRecipeSerializer<RECIPE> greenHouseFertilizer(
            Function3<FluidStackIngredient, Integer, Double, RECIPE> factory) {
        return new MekanismRecipeSerializer<>(RecordCodecBuilder.mapCodec(instance -> instance.group(
                IngredientCreatorAccess.fluid().codec().fieldOf("fertilizer")
                        .forGetter(GreenHouseFertilizerRecipe::getFertilizerIngredient),
                Codec.INT.fieldOf("output_multiplier")
                        .forGetter(GreenHouseFertilizerRecipe::getOutputMultiplier),
                Codec.DOUBLE.fieldOf("duration_multiplier")
                        .forGetter(GreenHouseFertilizerRecipe::getDurationMultiplier))
                .apply(instance, factory)),
                StreamCodec.composite(
                        IngredientCreatorAccess.fluid().streamCodec(),
                        GreenHouseFertilizerRecipe::getFertilizerIngredient,
                        ByteBufCodecs.INT,
                        GreenHouseFertilizerRecipe::getOutputMultiplier,
                        ByteBufCodecs.DOUBLE,
                        GreenHouseFertilizerRecipe::getDurationMultiplier,
                        factory));
    }

    public static <RECIPE extends GreenHouseRecipe> MekanismRecipeSerializer<RECIPE> greenHouse(
            Function5<ItemStackIngredient, ItemStackIngredient, FluidStackIngredient, List<MekUtChanceOutput>, Integer, RECIPE> factory) {
        return new MekanismRecipeSerializer<>(RecordCodecBuilder.mapCodec(instance -> instance.group(
                IngredientCreatorAccess.item().codec().fieldOf("crop")
                        .forGetter(GreenHouseRecipe::getCropIngredient),
                IngredientCreatorAccess.item().codec().fieldOf("soil")
                        .forGetter(GreenHouseRecipe::getSoilIngredient),
                IngredientCreatorAccess.fluid().codec().fieldOf("fertilizer")
                        .forGetter(GreenHouseRecipe::getFertilizerIngredient),
                MekUtChanceOutput.CODEC.listOf(1, 12).fieldOf(SerializationConstants.OUTPUT)
                        .forGetter(GreenHouseRecipe::getOutputsRaw),
                Codec.INT.fieldOf(SerializationConstants.DURATION)
                        .forGetter(GreenHouseRecipe::getDuration))
                .apply(instance, factory)),
                StreamCodec.composite(
                        IngredientCreatorAccess.item().streamCodec(),
                        GreenHouseRecipe::getCropIngredient,
                        IngredientCreatorAccess.item().streamCodec(),
                        GreenHouseRecipe::getSoilIngredient,
                        IngredientCreatorAccess.fluid().streamCodec(),
                        GreenHouseRecipe::getFertilizerIngredient,
                        MekUtChanceOutput.STREAM_CODEC.apply(ByteBufCodecs.list()),
                        GreenHouseRecipe::getOutputsRaw,
                        ByteBufCodecs.INT,
                        GreenHouseRecipe::getDuration,
                        factory));
    }

}