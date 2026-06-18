package com.takenokoshi.mekut.registries;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.recipe.MekUtRecipeConstants;
import com.takenokoshi.mekut.recipe.inputcache.ItemStackListFluidChemicalInputRecipeCache;
import com.takenokoshi.mekut.recipe.inputcache.MUSingleInputRecipeCache;
import com.takenokoshi.mekut.recipe.recipe.prefab.ItemStackListFluidChemicalToItemFluidChemicalRecipe;
import com.takenokoshi.mekut.recipe.recipe.prefab.ItemStackListFluidChemicalToItemRecipe;
import com.takenokoshi.mekut.recipe.type.SmallDigitalReactionChamberRecipeType;
import com.takenokoshi.mekut.recipe.type.SPSRecipeType;
import com.takenokoshi.mekut.recipe.type.SmallDigitalAssemblerRecipeType;
import com.takenokoshi.mekut.registration.MekUtRecipeTypeDeferredRegister;
import com.takenokoshi.mekut.registration.MekUtRecipeTypeRegistryObject;

import mekanism.api.recipes.ChemicalToChemicalRecipe;
import mekanism.api.recipes.vanilla_input.SingleChemicalRecipeInput;
import net.minecraft.world.item.crafting.RecipeInput;

public class MekUtRecipeTypes {
    public static final MekUtRecipeTypeDeferredRegister RECIPE_TYPES = new MekUtRecipeTypeDeferredRegister(
            MekUtConstants.MODID);
    public static final MekUtRecipeTypeRegistryObject<SingleChemicalRecipeInput, ChemicalToChemicalRecipe, MUSingleInputRecipeCache.MUSingleChemical<ChemicalToChemicalRecipe>> SPS = RECIPE_TYPES
            .registerMekUt(MekUtRecipeConstants.SPS, SPSRecipeType::new);

    public static final MekUtRecipeTypeRegistryObject<RecipeInput, ItemStackListFluidChemicalToItemRecipe, ItemStackListFluidChemicalInputRecipeCache<ItemStackListFluidChemicalToItemRecipe>> SMALL_DIGITAL_ASSEMBLER = RECIPE_TYPES
            .registerMekUt(MekUtRecipeConstants.SMALL_DIGITAL_ASSEMBLER, SmallDigitalAssemblerRecipeType::new);

    public static final MekUtRecipeTypeRegistryObject<RecipeInput, ItemStackListFluidChemicalToItemFluidChemicalRecipe, ItemStackListFluidChemicalInputRecipeCache<ItemStackListFluidChemicalToItemFluidChemicalRecipe>> SMALL_DIGITAL_REACTION_CHAMBER = RECIPE_TYPES
            .registerMekUt(MekUtRecipeConstants.SMALL_DIGITAL_REACTION_CHAMBER, SmallDigitalReactionChamberRecipeType::new);
}
