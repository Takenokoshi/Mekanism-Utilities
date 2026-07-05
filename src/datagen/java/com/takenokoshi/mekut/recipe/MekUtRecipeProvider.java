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
        AAEReactionRecipes.build(output);
        AdsorptionRecipes.build(output);
        AntiprotonicNucleoSynthesizeRecipes.build(output);
        ChemicalConvertionRecipes.build(output);
        ChemicalCutRecipes.build(output);
        ChemicalDissolutionRecipes.build(output);
        ChemicalWashingRecipes.build(output);
        CombiningRecipes.build(output);
        CompressingRecipes.build(output);
        CraftingRecipes.build(output, RecipeProvider::has);
        CrystalAssemblerRecipes.build(output);
        CrystallizingRecipes.build(output);
        EnrichingRecipes.build(output);
        IceMakerRecipes.build(output);
        InjectingRecipes.build(output);
        LazerCompressNucleoSynthesizeRecipes.build(output);
        MekReactionRecipes.build(output);
        MetallurgicInfusingRecipes.build(output);
        MUMaterialProcessRecipes.build(output, RecipeProvider::has);
        PaintingRecipes.build(output);
        PigmentExtractingRecipes.build(output);
        RotaryRecipes.build(output);
        SmallDigitalAssemblerRecipes.build(output);
        SmallDigitalReactionChamberRecipes.build(output);
        StellarGenesisRecipes.build(output);
    }

}
