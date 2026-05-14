package com.takenokoshi.mekut.recipe;

import java.util.concurrent.CompletableFuture;

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
        MUMaterialProcessRecipe.build(output, RecipeProvider::has);
    }
    
}
