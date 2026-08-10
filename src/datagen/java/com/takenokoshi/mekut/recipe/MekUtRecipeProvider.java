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
        AntiprotonicNucleoSynthesizeRecipes.build(output);
        ChemicalConvertionRecipes.build(output);
        ChemicalCutRecipes.build(output);
        ChemicalDissolutionRecipes.build(output);
        ChemicalWashingRecipes.build(output);
        CombiningRecipes.build(output);
        CraftingRecipes.build(output, RecipeProvider::has);
        CrystallizingRecipes.build(output);
        EnrichingRecipes.build(output);
        IceMakerRecipes.build(output);
        InjectingRecipes.build(output);
        LazerCompressNucleoSynthesizeRecipes.build(output);
        MekReactionRecipes.build(output);
        MetallurgicInfusingRecipes.build(output);
        MaterialProcessRecipes.build(output, RecipeProvider::has);
        PaintingRecipes.build(output);
        PigmentExtractingRecipes.build(output);
        RotaryRecipes.build(output);
        SDARecipes.build(output);
        SDRCRecipes.build(output);
        StellarGenesisRecipes.build(output);
    }

}
