package com.takenokoshi.mekut.recipe.inputcache;

import java.util.function.BiPredicate;
import java.util.function.Function;

import com.takenokoshi.mekaddonlib.recipe.inputcache.MekALDoubleInputRecipeCache;
import com.takenokoshi.mekaddonlib.recipe.type.MekALRecipeType;
import com.takenokoshi.mekut.recipe.recipe.prefab.GreenHouseCropRecipe;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ItemStackChemicalToItemStackRecipe;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.common.recipe.lookup.cache.type.ChemicalInputCache;
import mekanism.common.recipe.lookup.cache.type.ItemInputCache;
import net.minecraft.world.item.ItemStack;

public class MekUtDoubleInputRecipeCache {

    public static class MekUtItemChemical<RECIPE extends MekanismRecipe<?> & BiPredicate<ItemStack, ChemicalStack>>
            extends
            MekALDoubleInputRecipeCache<ItemStack, ItemStackIngredient, ChemicalStack, ChemicalStackIngredient, RECIPE, ItemInputCache<RECIPE>, ChemicalInputCache<RECIPE>> {

        protected MekUtItemChemical(MekALRecipeType<?, RECIPE, ?> recipeType,
                Function<RECIPE, ItemStackIngredient> inputAExtractor,
                Function<RECIPE, ChemicalStackIngredient> inputBExtractor) {
            super(recipeType, inputAExtractor, new ItemInputCache<>(), inputBExtractor, new ChemicalInputCache<>());
        }

        public static MekUtItemChemical<ItemStackChemicalToItemStackRecipe> toItem(
                MekALRecipeType<?, ItemStackChemicalToItemStackRecipe, ?> recipeType) {
            return new MekUtItemChemical<>(recipeType,
                    ItemStackChemicalToItemStackRecipe::getItemInput,
                    ItemStackChemicalToItemStackRecipe::getChemicalInput);
        }

    }

    public static class ItemItem<RECIPE extends MekanismRecipe<?> & BiPredicate<ItemStack, ItemStack>>
            extends
            MekALDoubleInputRecipeCache<ItemStack, ItemStackIngredient, ItemStack, ItemStackIngredient, RECIPE, ItemInputCache<RECIPE>, ItemInputCache<RECIPE>> {

        protected ItemItem(MekALRecipeType<?, RECIPE, ?> recipeType,
                Function<RECIPE, ItemStackIngredient> inputAExtractor,
                Function<RECIPE, ItemStackIngredient> inputBExtractor) {
            super(recipeType, inputAExtractor, new ItemInputCache<>(), inputBExtractor, new ItemInputCache<>());
        }

        public static ItemItem<GreenHouseCropRecipe> greenHouseCrop(
                MekALRecipeType<?, GreenHouseCropRecipe, ?> recipeType) {
            return new ItemItem<>(recipeType,
                    GreenHouseCropRecipe::getCropIngredient,
                    GreenHouseCropRecipe::getSoilIngredient);
        }
    }

}
