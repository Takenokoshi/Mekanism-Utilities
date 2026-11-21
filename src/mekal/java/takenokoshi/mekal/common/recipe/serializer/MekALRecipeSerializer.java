package takenokoshi.mekal.common.recipe.serializer;

import java.util.function.BiFunction;
import java.util.function.Function;

import com.mojang.datafixers.util.Function4;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import mekanism.api.SerializationConstants;
import mekanism.api.recipes.basic.BasicItemStackToFluidRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.recipe.serializer.MekanismRecipeSerializer;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import takenokoshi.mekal.api.recipes.basic.BasicFluidToItemStackRecipe;
import takenokoshi.mekal.api.recipes.basic.BasicItemStackChemicalToItemStackIngredientRecipe;
import takenokoshi.mekal.api.recipes.basic.BasicItemStackToItemStackIngredientRecipe;

public class MekALRecipeSerializer {
    public static <RECIPE extends BasicFluidToItemStackRecipe> MekanismRecipeSerializer<RECIPE> fluidToItem(
            BiFunction<FluidStackIngredient, ItemStack, RECIPE> factory) {
        return new MekanismRecipeSerializer<>(
                RecordCodecBuilder.mapCodec(instance -> instance.group(
                        IngredientCreatorAccess.fluid().codec().fieldOf(SerializationConstants.INPUT)
                                .forGetter(BasicFluidToItemStackRecipe::getInput),
                        ItemStack.CODEC.fieldOf(SerializationConstants.OUTPUT)
                                .forGetter(BasicFluidToItemStackRecipe::getOutputRaw))
                        .apply(instance, factory)),
                StreamCodec.composite(
                        IngredientCreatorAccess.fluid().streamCodec(),
                        BasicFluidToItemStackRecipe::getInput,
                        ItemStack.STREAM_CODEC,
                        BasicFluidToItemStackRecipe::getOutputRaw,
                        factory));
    }

    public static <RECIPE extends BasicItemStackToFluidRecipe> MekanismRecipeSerializer<RECIPE> itemToFluid(
            BiFunction<ItemStackIngredient, FluidStack, RECIPE> factory, Function<RECIPE, FluidStack> outputExtractor) {
        return new MekanismRecipeSerializer<>(
                RecordCodecBuilder.mapCodec(instance -> instance.group(
                        IngredientCreatorAccess.item().codec().fieldOf(SerializationConstants.INPUT)
                                .forGetter(BasicItemStackToFluidRecipe::getInput),
                        FluidStack.CODEC.fieldOf(SerializationConstants.OUTPUT)
                                .forGetter(outputExtractor))
                        .apply(instance, factory)),
                StreamCodec.composite(
                        IngredientCreatorAccess.item().streamCodec(),
                        BasicItemStackToFluidRecipe::getInput,
                        FluidStack.STREAM_CODEC,
                        outputExtractor,
                        factory));
    }

    public static <RECIPE extends BasicItemStackToItemStackIngredientRecipe> MekanismRecipeSerializer<RECIPE> itemToItemIngredient(
            BiFunction<ItemStackIngredient, ItemStackIngredient, RECIPE> factory) {
        return new MekanismRecipeSerializer<>(
                RecordCodecBuilder.mapCodec(instance -> instance.group(
                        IngredientCreatorAccess.item().codec().fieldOf(SerializationConstants.INPUT)
                                .forGetter(BasicItemStackToItemStackIngredientRecipe::getInput),
                        IngredientCreatorAccess.item().codec().fieldOf(SerializationConstants.OUTPUT)
                                .forGetter(BasicItemStackToItemStackIngredientRecipe::getOutputRaw))
                        .apply(instance, factory)),
                StreamCodec.composite(
                        IngredientCreatorAccess.item().streamCodec(),
                        BasicItemStackToItemStackIngredientRecipe::getInput,
                        IngredientCreatorAccess.item().streamCodec(),
                        BasicItemStackToItemStackIngredientRecipe::getOutputRaw,
                        factory));
    }

    public static <RECIPE extends BasicItemStackChemicalToItemStackIngredientRecipe> MekanismRecipeSerializer<RECIPE> itemChemicalToItemIngredient(
            Function4<ItemStackIngredient, ChemicalStackIngredient, ItemStackIngredient, Boolean, RECIPE> factory) {
        return new MekanismRecipeSerializer<>(
                RecordCodecBuilder.mapCodec(instance -> instance.group(
                        IngredientCreatorAccess.item().codec().fieldOf(SerializationConstants.ITEM_INPUT)
                                .forGetter(BasicItemStackChemicalToItemStackIngredientRecipe::getItemInput),
                        IngredientCreatorAccess.chemicalStack().codec().fieldOf(SerializationConstants.CHEMICAL_INPUT)
                                .forGetter(BasicItemStackChemicalToItemStackIngredientRecipe::getChemicalInput),
                        IngredientCreatorAccess.item().codec().fieldOf(SerializationConstants.OUTPUT)
                                .forGetter(BasicItemStackChemicalToItemStackIngredientRecipe::getOutputRaw),
                        Codec.BOOL.fieldOf(SerializationConstants.PER_TICK_USAGE)
                                .forGetter(BasicItemStackChemicalToItemStackIngredientRecipe::perTickUsage))
                        .apply(instance, factory)),
                StreamCodec.composite(
                        IngredientCreatorAccess.item().streamCodec(),
                        BasicItemStackChemicalToItemStackIngredientRecipe::getItemInput,
                        IngredientCreatorAccess.chemicalStack().streamCodec(),
                        BasicItemStackChemicalToItemStackIngredientRecipe::getChemicalInput,
                        IngredientCreatorAccess.item().streamCodec(),
                        BasicItemStackChemicalToItemStackIngredientRecipe::getOutputRaw,
                        ByteBufCodecs.BOOL,
                        BasicItemStackChemicalToItemStackIngredientRecipe::perTickUsage,
                        factory));
    }
}
