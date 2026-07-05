package com.takenokoshi.mekut.registries;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.recipe.MekUtRecipeConstants;
import com.takenokoshi.mekut.recipe.inputcache.ItemStackListFluidChemicalInputRecipeCache;
import com.takenokoshi.mekut.recipe.inputcache.MUEitherSideInputRecipeCache;
import com.takenokoshi.mekut.recipe.inputcache.MUSingleInputRecipeCache;
import com.takenokoshi.mekut.recipe.inputcache.MekUtDoubleInputRecipeCache;
import com.takenokoshi.mekut.recipe.recipe.prefab.BiChemicalToItemRecipe;
import com.takenokoshi.mekut.recipe.recipe.prefab.ChemicalToChemicalHeatRecipe;
import com.takenokoshi.mekut.recipe.recipe.prefab.FluidToItemRecipe;
import com.takenokoshi.mekut.recipe.recipe.prefab.ItemStackListFluidChemicalToItemFluidChemicalRecipe;
import com.takenokoshi.mekut.recipe.recipe.prefab.ItemStackListFluidChemicalToItemRecipe;
import com.takenokoshi.mekut.recipe.type.SmallDigitalReactionChamberRecipeType;
import com.takenokoshi.mekut.recipe.type.ChemicalCutRecipeType;
import com.takenokoshi.mekut.recipe.type.FissonReactorRecipeType;
import com.takenokoshi.mekut.recipe.type.MekUtRecipeType;
import com.takenokoshi.mekut.recipe.type.SPSRecipeType;
import com.takenokoshi.mekut.recipe.type.SmallDigitalAssemblerRecipeType;
import com.takenokoshi.mekut.registration.MekUtRecipeTypeDeferredRegister;
import com.takenokoshi.mekut.registration.MekUtRecipeTypeRegistryObject;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ChemicalChemicalToChemicalRecipe;
import mekanism.api.recipes.ChemicalToChemicalRecipe;
import mekanism.api.recipes.ItemStackChemicalToItemStackRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.vanilla_input.BiChemicalRecipeInput;
import mekanism.api.recipes.vanilla_input.SingleChemicalRecipeInput;
import mekanism.api.recipes.vanilla_input.SingleFluidRecipeInput;
import mekanism.api.recipes.vanilla_input.SingleItemChemicalRecipeInput;
import mekanism.common.recipe.lookup.cache.type.ChemicalInputCache;
import net.minecraft.world.item.crafting.RecipeInput;

public class MekUtRecipeTypes {
    public static final MekUtRecipeTypeDeferredRegister RECIPE_TYPES = new MekUtRecipeTypeDeferredRegister(
            MekUtConstants.MODID);

    public static final MekUtRecipeTypeRegistryObject<SingleItemChemicalRecipeInput, ItemStackChemicalToItemStackRecipe, MekUtDoubleInputRecipeCache.MekUtItemChemical<ItemStackChemicalToItemStackRecipe>> CHEMICAL_CUT = RECIPE_TYPES
            .registerMekUt(MekUtRecipeConstants.CHEMICAL_CUT, ChemicalCutRecipeType::new);

    public static final MekUtRecipeTypeRegistryObject<SingleFluidRecipeInput, FluidToItemRecipe, MUSingleInputRecipeCache.MUSingleFluid<FluidToItemRecipe>> ICE_MAKING = RECIPE_TYPES
            .registerMekUt(MekUtRecipeConstants.ICE_MAKING,
                    id -> new MekUtRecipeType<>(id, MUSingleInputRecipeCache.MUSingleFluid::toItem));

    public static final MekUtRecipeTypeRegistryObject<BiChemicalRecipeInput, ChemicalChemicalToChemicalRecipe, MUEitherSideInputRecipeCache<ChemicalStack, ChemicalStackIngredient, ChemicalChemicalToChemicalRecipe, ChemicalInputCache<ChemicalChemicalToChemicalRecipe>>> LAZER_COMPRESS = RECIPE_TYPES
            .registerMekUt(MekUtRecipeConstants.LAZER_COMPRESS,
                    id -> new MekUtRecipeType<>(id, MUEitherSideInputRecipeCache::chemicalToChemical));

    public static final MekUtRecipeTypeRegistryObject<RecipeInput, ItemStackListFluidChemicalToItemRecipe, ItemStackListFluidChemicalInputRecipeCache<ItemStackListFluidChemicalToItemRecipe>> SMALL_DIGITAL_ASSEMBLER = RECIPE_TYPES
            .registerMekUt(MekUtRecipeConstants.SMALL_DIGITAL_ASSEMBLER, SmallDigitalAssemblerRecipeType::new);

    public static final MekUtRecipeTypeRegistryObject<RecipeInput, ItemStackListFluidChemicalToItemFluidChemicalRecipe, ItemStackListFluidChemicalInputRecipeCache<ItemStackListFluidChemicalToItemFluidChemicalRecipe>> SMALL_DIGITAL_REACTION_CHAMBER = RECIPE_TYPES
            .registerMekUt(MekUtRecipeConstants.SMALL_DIGITAL_REACTION_CHAMBER,
                    SmallDigitalReactionChamberRecipeType::new);

    public static final MekUtRecipeTypeRegistryObject<SingleChemicalRecipeInput, ChemicalToChemicalRecipe, MUSingleInputRecipeCache.MUSingleChemical<ChemicalToChemicalRecipe>> SPS = RECIPE_TYPES
            .registerMekUt(MekUtRecipeConstants.SPS, SPSRecipeType::new);

    public static final MekUtRecipeTypeRegistryObject<BiChemicalRecipeInput, BiChemicalToItemRecipe, MUEitherSideInputRecipeCache<ChemicalStack, ChemicalStackIngredient, BiChemicalToItemRecipe, ChemicalInputCache<BiChemicalToItemRecipe>>> STELLAR_GENESIS = RECIPE_TYPES
            .registerMekUt(MekUtRecipeConstants.STELLAR_GENESIS,
                    id -> new MekUtRecipeType<>(id, MUEitherSideInputRecipeCache::chemicalToItem));

    public static final MekUtRecipeTypeRegistryObject<SingleChemicalRecipeInput, ChemicalToChemicalHeatRecipe, MUSingleInputRecipeCache.MUSingleChemical<ChemicalToChemicalHeatRecipe>> FISSION_REACTOR = RECIPE_TYPES
            .registerMekUt(MekUtRecipeConstants.FISSION_REACTOR, FissonReactorRecipeType::new);
}
