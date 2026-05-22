package com.takenokoshi.mekut.recipe.inputcache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekut.recipe.MekUtRecipeType;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;

public class SingleItemRecipeCache<RECIPE extends Recipe<?>> extends SimpleInputRecipeCache<RECIPE> {

    private final Function<RECIPE, @NotNull List<Item>> inputExtractor;
    private final Map<Item, RECIPE> recipeMap;

    public SingleItemRecipeCache(MekUtRecipeType<?, RECIPE, ?> recipeType,
            Function<RECIPE, List<Item>> inputExtractor) {
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
            inputExtractor.apply(recipe).forEach(item -> {
                // Use first recipe registered for an item.
                recipeMap.putIfAbsent(item, recipe);
            });
        }
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
