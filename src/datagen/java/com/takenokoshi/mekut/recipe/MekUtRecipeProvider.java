package com.takenokoshi.mekut.recipe;

import java.util.concurrent.CompletableFuture;

import com.takenokoshi.mekut.recipe.building.*;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

public class MekUtRecipeProvider extends RecipeProvider {

    public MekUtRecipeProvider(PackOutput output, CompletableFuture<Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        BuildAAEReactionRecipe.build(output);
        BuildAdsorptionRecipe.build(output);
        BuildAntiprotonicNucleoSynthesizeRecipe.build(output);
        BuildChemicalConvertionRecipe.build(output);
        BuildChemicalCutRecipe.build(output);
        BuildChemicalDissolutionRecipe.build(output);
        BuildChemicalWashingRecipe.build(output);
        BuildCombiningRecipe.build(output);
        BuildCompressingRecipe.build(output);
        BuildCraftingRecipe.build(output, RecipeProvider::has);
        BuildCrystalAssemblerRecipe.build(output);
        BuildCrystallizingRecipe.build(output);
        BuildEnrichingRecipe.build(output);
        BuildIceMakerRecipe.build(output);
        BuildInjectingRecipe.build(output);
        BuildLazerCompressNucleoSynthesizeRecipe.build(output);
        BuildMekReactionRecipe.build(output);
        BuildMetallurgicInfusingRecipe.build(output);
        BuildMUMaterialProcessRecipe.build(output, RecipeProvider::has);
        BuildPaintingRecipe.build(output);
        BuildPigmentExtractingRecipe.build(output);
        BuildRotaryRecipe.build(output);
        BuildSmallDigitalAssemblerRecipe.build(output);
        BuildSmallDigitalReactionChamberRecipe.build(output);
        BuildStellarGenesisRecipe.build(output);
    }

}
