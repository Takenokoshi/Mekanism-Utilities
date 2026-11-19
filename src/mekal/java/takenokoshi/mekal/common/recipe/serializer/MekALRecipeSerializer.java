package takenokoshi.mekal.common.recipe.serializer;

import java.util.function.BiFunction;
import java.util.function.Function;

import com.mojang.serialization.codecs.RecordCodecBuilder;

import mekanism.api.SerializationConstants;
import mekanism.api.recipes.basic.BasicItemStackToFluidRecipe;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.recipe.serializer.MekanismRecipeSerializer;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import takenokoshi.mekal.api.recipes.basic.BasicFluidToItemStackRecipe;

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
}
