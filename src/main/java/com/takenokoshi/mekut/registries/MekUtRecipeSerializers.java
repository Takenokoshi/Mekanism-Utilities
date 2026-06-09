package com.takenokoshi.mekut.registries;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.recipe.basic.BasicSPSRecipe;

import mekanism.common.recipe.serializer.MekanismRecipeSerializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MekUtRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister
            .create(Registries.RECIPE_SERIALIZER, MekUtConstants.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, MekanismRecipeSerializer<BasicSPSRecipe>> SPS = RECIPE_SERIALIZERS
            .register("sps", () -> MekanismRecipeSerializer.chemicalToChemical(BasicSPSRecipe::new));
}
