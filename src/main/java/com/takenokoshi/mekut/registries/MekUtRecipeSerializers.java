package com.takenokoshi.mekut.registries;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.recipe.recipe.basic.BasicSmallDigitalReactionChamberRecipe;
import com.takenokoshi.mekut.recipe.recipe.basic.BasicStellarGenesisRecipe;
import com.takenokoshi.mekut.recipe.MekUtRecipeConstants;
import com.takenokoshi.mekut.recipe.recipe.basic.BasicChemicalCutRecipe;
import com.takenokoshi.mekut.recipe.recipe.basic.BasicFissionReactorRecipe;
import com.takenokoshi.mekut.recipe.recipe.basic.BasicIceMakingRecipe;
import com.takenokoshi.mekut.recipe.recipe.basic.BasicLazerCompressRecipe;
import com.takenokoshi.mekut.recipe.recipe.basic.BasicSPSRecipe;
import com.takenokoshi.mekut.recipe.recipe.basic.BasicSmallDigitalAssemblerRecipe;
import com.takenokoshi.mekut.recipe.serializer.MekUtRecipeSerializerBuilder;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.recipe.serializer.MekanismRecipeSerializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MekUtRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister
            .create(Registries.RECIPE_SERIALIZER, MekUtConstants.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, MekanismRecipeSerializer<BasicChemicalCutRecipe>> CHEMICAL_CUT = RECIPE_SERIALIZERS
            .register(MekUtRecipeConstants.CHEMICAL_CUT,
                    () -> MekanismRecipeSerializer.itemChemicalToItem(BasicChemicalCutRecipe::new));

    public static final DeferredHolder<RecipeSerializer<?>, MekanismRecipeSerializer<BasicIceMakingRecipe>> ICE_MAKING = RECIPE_SERIALIZERS
            .register(MekUtRecipeConstants.ICE_MAKING,
                    () -> MekUtRecipeSerializerBuilder.fluidToItem(BasicIceMakingRecipe::new));

    public static final DeferredHolder<RecipeSerializer<?>, MekanismRecipeSerializer<BasicLazerCompressRecipe>> LAZER_COMPRESS = RECIPE_SERIALIZERS
            .register(MekUtRecipeConstants.LAZER_COMPRESS,
                    () -> MekanismRecipeSerializer.chemicalChemicalToChemical(
                            BasicLazerCompressRecipe::new, IngredientCreatorAccess.chemicalStack(),
                            ChemicalStack.MAP_CODEC, ChemicalStack.STREAM_CODEC));

    public static final DeferredHolder<RecipeSerializer<?>, MekanismRecipeSerializer<BasicSmallDigitalAssemblerRecipe>> SMALL_DIGITAL_ASSEMBLER = RECIPE_SERIALIZERS
            .register(MekUtRecipeConstants.SMALL_DIGITAL_ASSEMBLER,
                    () -> MekUtRecipeSerializerBuilder
                            .itemStackListFluidChemicalToItem(BasicSmallDigitalAssemblerRecipe::new));

    public static final DeferredHolder<RecipeSerializer<?>, MekanismRecipeSerializer<BasicSmallDigitalReactionChamberRecipe>> SMALL_DIGITAL_REACTION_CHAMBER = RECIPE_SERIALIZERS
            .register(MekUtRecipeConstants.SMALL_DIGITAL_REACTION_CHAMBER, () -> MekUtRecipeSerializerBuilder
                    .itemStackListFluidChemicalToItemFluidChemical(BasicSmallDigitalReactionChamberRecipe::new));

    public static final DeferredHolder<RecipeSerializer<?>, MekanismRecipeSerializer<BasicSPSRecipe>> SPS = RECIPE_SERIALIZERS
            .register(MekUtRecipeConstants.SPS, () -> MekanismRecipeSerializer.chemicalToChemical(BasicSPSRecipe::new));

    public static final DeferredHolder<RecipeSerializer<?>, MekanismRecipeSerializer<BasicStellarGenesisRecipe>> STELLAR_GENESIS = RECIPE_SERIALIZERS
            .register(MekUtRecipeConstants.STELLAR_GENESIS,
                    () -> MekUtRecipeSerializerBuilder.chemicalChemicalToItem(BasicStellarGenesisRecipe::new));

    public static final DeferredHolder<RecipeSerializer<?>, MekanismRecipeSerializer<BasicFissionReactorRecipe>> FISSION_REACTOR = RECIPE_SERIALIZERS
            .register(MekUtRecipeConstants.FISSION_REACTOR,
                    () -> MekUtRecipeSerializerBuilder.chemicalToChemicalHeat(BasicFissionReactorRecipe::new));
}
