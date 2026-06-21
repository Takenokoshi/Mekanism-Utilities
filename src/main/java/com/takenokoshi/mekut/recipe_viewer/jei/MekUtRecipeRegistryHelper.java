package com.takenokoshi.mekut.recipe_viewer.jei;

import com.takenokoshi.mekut.recipe.type.IMekUtRecipeTypeProvider;

import mekanism.client.recipe_viewer.jei.MekanismJEI;
import mekanism.client.recipe_viewer.jei.RecipeRegistryHelper;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.crafting.Recipe;

public class MekUtRecipeRegistryHelper {
    RecipeRegistryHelper helper;

    public static <RECIPE extends Recipe<?>> void register(
            IRecipeRegistration registry,
            IRecipeViewerRecipeType<RECIPE> recipeType,
            IMekUtRecipeTypeProvider<?, RECIPE, ?> type) {
        registry.addRecipes(MekanismJEI.holderRecipeType(recipeType), type.getRecipes(Minecraft.getInstance().level));
    }
}
