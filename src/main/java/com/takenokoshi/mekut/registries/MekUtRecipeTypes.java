package com.takenokoshi.mekut.registries;

import com.takenokoshi.mekaddonlib.recipe.type.MekALRecipeType;
import com.takenokoshi.mekaddonlib.registration.MekALRecipeTypeDeferredRegister;
import com.takenokoshi.mekaddonlib.registration.MekALRecipeTypeRegistryObject;
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
import com.takenokoshi.mekut.recipe.type.SPSRecipeType;
import com.takenokoshi.mekut.recipe.type.SmallDigitalAssemblerRecipeType;
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
    public static final MekALRecipeTypeDeferredRegister RECIPE_TYPES = new MekALRecipeTypeDeferredRegister(
            MekUtConstants.MODID);

    public static final MekALRecipeTypeRegistryObject<SingleItemChemicalRecipeInput, ItemStackChemicalToItemStackRecipe, MekUtDoubleInputRecipeCache.MekUtItemChemical<ItemStackChemicalToItemStackRecipe>> CHEMICAL_CUT = RECIPE_TYPES
            .registerMekAL(MekUtRecipeConstants.CHEMICAL_CUT, ChemicalCutRecipeType::new);

    public static final MekALRecipeTypeRegistryObject<SingleFluidRecipeInput, FluidToItemRecipe, MUSingleInputRecipeCache.MUSingleFluid<FluidToItemRecipe>> ICE_MAKING = RECIPE_TYPES
            .registerMekAL(MekUtRecipeConstants.ICE_MAKING,
                    id -> new MekALRecipeType<>(id, MUSingleInputRecipeCache.MUSingleFluid::toItem));

    public static final MekALRecipeTypeRegistryObject<BiChemicalRecipeInput, ChemicalChemicalToChemicalRecipe, MUEitherSideInputRecipeCache<ChemicalStack, ChemicalStackIngredient, ChemicalChemicalToChemicalRecipe, ChemicalInputCache<ChemicalChemicalToChemicalRecipe>>> LAZER_COMPRESS = RECIPE_TYPES
            .registerMekAL(MekUtRecipeConstants.LAZER_COMPRESS,
                    id -> new MekALRecipeType<>(id, MUEitherSideInputRecipeCache::chemicalToChemical));

    public static final MekALRecipeTypeRegistryObject<RecipeInput, ItemStackListFluidChemicalToItemRecipe, ItemStackListFluidChemicalInputRecipeCache<ItemStackListFluidChemicalToItemRecipe>> SMALL_DIGITAL_ASSEMBLER = RECIPE_TYPES
            .registerMekAL(MekUtRecipeConstants.SMALL_DIGITAL_ASSEMBLER, SmallDigitalAssemblerRecipeType::new);

    public static final MekALRecipeTypeRegistryObject<RecipeInput, ItemStackListFluidChemicalToItemFluidChemicalRecipe, ItemStackListFluidChemicalInputRecipeCache<ItemStackListFluidChemicalToItemFluidChemicalRecipe>> SMALL_DIGITAL_REACTION_CHAMBER = RECIPE_TYPES
            .registerMekAL(MekUtRecipeConstants.SMALL_DIGITAL_REACTION_CHAMBER,
                    SmallDigitalReactionChamberRecipeType::new);

    public static final MekALRecipeTypeRegistryObject<SingleChemicalRecipeInput, ChemicalToChemicalRecipe, MUSingleInputRecipeCache.MUSingleChemical<ChemicalToChemicalRecipe>> SPS = RECIPE_TYPES
            .registerMekAL(MekUtRecipeConstants.SPS, SPSRecipeType::new);

    public static final MekALRecipeTypeRegistryObject<BiChemicalRecipeInput, BiChemicalToItemRecipe, MUEitherSideInputRecipeCache<ChemicalStack, ChemicalStackIngredient, BiChemicalToItemRecipe, ChemicalInputCache<BiChemicalToItemRecipe>>> STELLAR_GENESIS = RECIPE_TYPES
            .registerMekAL(MekUtRecipeConstants.STELLAR_GENESIS,
                    id -> new MekALRecipeType<>(id, MUEitherSideInputRecipeCache::chemicalToItem));

    public static final MekALRecipeTypeRegistryObject<SingleChemicalRecipeInput, ChemicalToChemicalHeatRecipe, MUSingleInputRecipeCache.MUSingleChemical<ChemicalToChemicalHeatRecipe>> FISSION_REACTOR = RECIPE_TYPES
            .registerMekAL(MekUtRecipeConstants.FISSION_REACTOR, FissonReactorRecipeType::new);
}
