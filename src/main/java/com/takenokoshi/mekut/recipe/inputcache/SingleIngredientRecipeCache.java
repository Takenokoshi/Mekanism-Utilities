package com.takenokoshi.mekut.recipe.inputcache;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekut.recipe.MekUtRecipeType;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;

public class SingleIngredientRecipeCache<RECIPE extends Recipe<?>> extends SimpleInputRecipeCache<RECIPE> {

    private final Function<RECIPE, Ingredient> inputExtractor;
    private final Map<Item, List<RECIPE>> recipeSetMap = new LinkedHashMap<>();

    public SingleIngredientRecipeCache(MekUtRecipeType<?, RECIPE, ?> recipeType,
            Function<RECIPE, Ingredient> inputExtractor) {
        super(recipeType);
        this.inputExtractor = inputExtractor;
    }

    @Override
    public void clear() {
        super.clear();
        recipeSetMap.clear();
    }

    @Override
    protected void initCache(List<RecipeHolder<RECIPE>> recipes) {
        for (RecipeHolder<RECIPE> recipeHolder : recipes) {
            RECIPE recipe = recipeHolder.value();
            Ingredient ingredient = inputExtractor.apply(recipe);
            for (ItemStack stack : ingredient.getItems()) {
                Item item = stack.getItem();
                if (!recipeSetMap.containsKey(item)) {
                    recipeSetMap.put(item, new ArrayList<>());
                }
                recipeSetMap.get(item).add(recipe);
            }
        }
    }

    public boolean containsInput(Level world, ItemStack input) {
        if (input.isEmpty()) {
            return false;
        }
        initCacheIfNeeded(world);
        return recipeSetMap.containsKey(input.getItem());
    }

    @Nullable
    public RECIPE findFirstRecipe(Level world, ItemStack input) {
        if (input.isEmpty()) {
            return null;
        }
        initCacheIfNeeded(world);
        Item item = input.getItem();
        if (recipeSetMap.containsKey(item)) {
            return recipeSetMap.get(item).get(0);
        }
        return null;
    }

}
