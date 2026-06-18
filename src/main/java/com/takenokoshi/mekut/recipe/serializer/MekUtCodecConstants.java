package com.takenokoshi.mekut.recipe.serializer;

import java.util.List;
import java.util.Optional;

import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public final class MekUtCodecConstants {
    public static final StreamCodec<RegistryFriendlyByteBuf, List<ItemStackIngredient>> ITEMSTACK_INGREDIENT_LIST_STREAM_CODEC = ItemStackIngredient.STREAM_CODEC
            .apply(ByteBufCodecs.list());

    public static final StreamCodec<RegistryFriendlyByteBuf, Optional<FluidStackIngredient>> FLUIDSTACK_INGREDIENT_OPTIONAL_STREAM_CODEC = ByteBufCodecs
            .optional(FluidStackIngredient.STREAM_CODEC);

    public static final StreamCodec<RegistryFriendlyByteBuf, Optional<ChemicalStackIngredient>> CHEMICALSTACK_INGREDIENT_OPTIONAL_STREAM_CODEC = ByteBufCodecs
            .optional(ChemicalStackIngredient.STREAM_CODEC);
}
