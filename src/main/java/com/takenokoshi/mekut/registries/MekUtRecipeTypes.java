package com.takenokoshi.mekut.registries;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.recipe.inputcache.MUSingleInputRecipeCache;
import com.takenokoshi.mekut.recipe.type.SPSRecipeType;
import com.takenokoshi.mekut.registration.MekUtRecipeTypeDeferredRegister;
import com.takenokoshi.mekut.registration.MekUtRecipeTypeRegistryObject;

import mekanism.api.recipes.ChemicalToChemicalRecipe;
import mekanism.api.recipes.vanilla_input.SingleChemicalRecipeInput;

public class MekUtRecipeTypes {
    public static final MekUtRecipeTypeDeferredRegister RECIPE_TYPES = new MekUtRecipeTypeDeferredRegister(
            MekUtConstants.MODID);
    public static final MekUtRecipeTypeRegistryObject<SingleChemicalRecipeInput, ChemicalToChemicalRecipe, MUSingleInputRecipeCache.MUSingleChemical<ChemicalToChemicalRecipe>> SPS = RECIPE_TYPES
            .registerMekUt("sps", SPSRecipeType::new);
}
