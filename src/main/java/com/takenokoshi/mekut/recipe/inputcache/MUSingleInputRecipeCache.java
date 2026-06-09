package com.takenokoshi.mekut.recipe.inputcache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekut.recipe.type.MekUtRecipeType;

import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;

public class MUSingleInputRecipeCache<RECIPE extends Recipe<?>, INPUT_TYPE> extends MUAbstractInputRecipeCache<RECIPE> {

    protected final Function<RECIPE, @NotNull List<INPUT_TYPE>> inputExtractor;
    protected final Map<INPUT_TYPE, RECIPE> recipeMap;

    protected MUSingleInputRecipeCache(MekUtRecipeType<?, RECIPE, ?> recipeType,
            Function<RECIPE, @NotNull List<INPUT_TYPE>> inputExtractor) {
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

        public MUSingleItem(MekUtRecipeType<?, RECIPE, ?> recipeType,
                Function<RECIPE, @NotNull List<Item>> inputExtractor) {
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

    public static class MUSingleChemical<RECIPE extends Recipe<?>> extends MUSingleInputRecipeCache<RECIPE, Chemical> {

        public MUSingleChemical(MekUtRecipeType<?, RECIPE, ?> recipeType,
                Function<RECIPE, @NotNull List<Chemical>> inputExtractor) {
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
