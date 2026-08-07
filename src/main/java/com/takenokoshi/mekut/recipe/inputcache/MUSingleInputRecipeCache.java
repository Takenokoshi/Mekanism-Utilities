package com.takenokoshi.mekut.recipe.inputcache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekaddonlib.recipe.type.MekALRecipeType;
import com.takenokoshi.mekut.recipe.recipe.prefab.FluidToItemRecipe;

import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.vanilla_input.SingleFluidRecipeInput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;

public class MUSingleInputRecipeCache<RECIPE extends Recipe<?>, INPUT_TYPE> extends MUAbstractInputRecipeCache<RECIPE> {

    protected final Function<RECIPE, List<INPUT_TYPE>> inputExtractor;
    protected final Map<INPUT_TYPE, RECIPE> recipeMap;

    protected MUSingleInputRecipeCache(MekALRecipeType<?, RECIPE, ?> recipeType,
            Function<RECIPE, List<INPUT_TYPE>> inputExtractor) {
        super(recipeType);
        this.inputExtractor = inputExtractor;
        this.recipeMap = new HashMap<>();
    }

    @Override
    public void clear() {
        super.clear();
        recipeMap.clear();
    }

    @Override
    protected void initCache(List<RecipeHolder<RECIPE>> recipes) {
        for (RecipeHolder<RECIPE> recipeHolder : recipes) {
            RECIPE recipe = recipeHolder.value();
            inputExtractor.apply(recipe).forEach(input -> {
                // Use first recipe registered for an input.
                recipeMap.putIfAbsent(input, recipe);
            });
        }
    }

    public static class MUSingleItem<RECIPE extends Recipe<?>> extends MUSingleInputRecipeCache<RECIPE, Item> {

        public MUSingleItem(MekALRecipeType<?, RECIPE, ?> recipeType,
                Function<RECIPE, List<Item>> inputExtractor) {
            super(recipeType, inputExtractor);
        }

        public boolean containsInput(Level world, ItemStack input) {
            if (input.isEmpty()) {
                return false;
            }
            initCacheIfNeeded(world);
            return recipeMap.containsKey(input.getItem());
        }

        @Nullable
        public RECIPE findFirstRecipe(Level world, ItemStack input) {
            if (input.isEmpty()) {
                return null;
            }
            initCacheIfNeeded(world);
            return recipeMap.get(input.getItem());
        }
    }

    public static class MUSingleFluid<RECIPE extends Recipe<?>> extends MUSingleInputRecipeCache<RECIPE, Fluid> {

        public MUSingleFluid(MekALRecipeType<?, RECIPE, ?> recipeType,
                Function<RECIPE, List<Fluid>> inputExtractor) {
            super(recipeType, inputExtractor);
        }

        public static <RECIPE extends FluidToItemRecipe> MUSingleFluid<RECIPE> toItem(
                MekALRecipeType<SingleFluidRecipeInput, RECIPE, ?> recipeType) {
            return new MUSingleFluid<>(recipeType,
                    recipe -> recipe.input.getRepresentations().stream().map(FluidStack::getFluid).toList());
        }

        public boolean containsInput(Level world, FluidStack input) {
            if (input.isEmpty()) {
                return false;
            }
            initCacheIfNeeded(world);
            return recipeMap.containsKey(input.getFluid());
        }

        @Nullable
        public RECIPE findFirstRecipe(Level world, FluidStack input) {
            if (input.isEmpty()) {
                return null;
            }
            initCacheIfNeeded(world);
            return recipeMap.get(input.getFluid());
        }

    }

    public static class MUSingleChemical<RECIPE extends Recipe<?>> extends MUSingleInputRecipeCache<RECIPE, Chemical> {

        public MUSingleChemical(MekALRecipeType<?, RECIPE, ?> recipeType,
                Function<RECIPE, List<Chemical>> inputExtractor) {
            super(recipeType, inputExtractor);
        }

        public boolean containsInput(Level world, ChemicalStack input) {
            if (input.isEmpty()) {
                return false;
            }
            initCacheIfNeeded(world);
            return recipeMap.containsKey(input.getChemical());
        }

        public @Nullable RECIPE findFirstRecipe(Level word, ChemicalStack input) {
            if (input.isEmpty()) {
                return null;
            }
            initCacheIfNeeded(word);
            return recipeMap.get(input.getChemical());
        }

    }

}
