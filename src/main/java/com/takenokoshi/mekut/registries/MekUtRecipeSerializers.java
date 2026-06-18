package com.takenokoshi.mekut.registries;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.recipe.recipe.basic.BasicSmallDigitalReactionChamberRecipe;
import com.takenokoshi.mekut.recipe.MekUtRecipeConstants;
import com.takenokoshi.mekut.recipe.recipe.basic.BasicSPSRecipe;
import com.takenokoshi.mekut.recipe.recipe.basic.BasicSmallDigitalAssemblerRecipe;
import com.takenokoshi.mekut.recipe.serializer.MekUtRecipeSerializerBuilder;

import mekanism.common.recipe.serializer.MekanismRecipeSerializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MekUtRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister
            .create(Registries.RECIPE_SERIALIZER, MekUtConstants.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, MekanismRecipeSerializer<BasicSPSRecipe>> SPS = RECIPE_SERIALIZERS
            .register(MekUtRecipeConstants.SPS, () -> MekanismRecipeSerializer.chemicalToChemical(BasicSPSRecipe::new));

    public static final DeferredHolder<RecipeSerializer<?>, MekanismRecipeSerializer<BasicSmallDigitalAssemblerRecipe>> SMALL_DIGITAL_ASSEMBLER = RECIPE_SERIALIZERS
            .register(MekUtRecipeConstants.SMALL_DIGITAL_ASSEMBLER,
                    () -> MekUtRecipeSerializerBuilder
                            .itemStackListFluidChemicalToItem(BasicSmallDigitalAssemblerRecipe::new));

    public static final DeferredHolder<RecipeSerializer<?>, MekanismRecipeSerializer<BasicSmallDigitalReactionChamberRecipe>> SMALL_DIGITAL_REACTION_CHAMBER = RECIPE_SERIALIZERS
            .register(MekUtRecipeConstants.SMALL_DIGITAL_REACTION_CHAMBER, () -> MekUtRecipeSerializerBuilder
                    .itemStackListFluidChemicalToItemFluidChemical(BasicSmallDigitalReactionChamberRecipe::new));
}
