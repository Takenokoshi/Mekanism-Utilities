package com.takenokoshi.mekut.recipe.inputcache;

import java.util.List;
import java.util.function.Function;

import com.takenokoshi.mekaddonlib.recipe.inputcache.MekALAbstractInputRecipeCache;
import com.takenokoshi.mekaddonlib.recipe.type.MekALRecipeType;
import com.takenokoshi.mekut.recipe.recipe.prefab.GreenHouseRecipe;
import com.takenokoshi.mekut.recipe.recipe.util.TriTypePredicate;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.InputIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.common.recipe.lookup.cache.type.FluidInputCache;
import mekanism.common.recipe.lookup.cache.type.IInputCache;
import mekanism.common.recipe.lookup.cache.type.ItemInputCache;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.TriPredicate;
import net.neoforged.neoforge.fluids.FluidStack;

public class MekUtTripleInputRecipeCache<INPUT_A, INGREDIENT_A extends InputIngredient<INPUT_A>, INPUT_B, INGREDIENT_B extends InputIngredient<INPUT_B>, INPUT_C, INGREDIENT_C extends InputIngredient<INPUT_C>, RECIPE extends MekanismRecipe<?> & TriPredicate<INPUT_A, INPUT_B, INPUT_C> & TriTypePredicate<INPUT_A, INPUT_B, INPUT_C>, CACHE_A extends IInputCache<INPUT_A, INGREDIENT_A, RECIPE>, CACHE_B extends IInputCache<INPUT_B, INGREDIENT_B, RECIPE>, CACHE_C extends IInputCache<INPUT_C, INGREDIENT_C, RECIPE>>
        extends MekALAbstractInputRecipeCache<RECIPE> {

    private final Function<RECIPE, INGREDIENT_A> inputAExtractor;
    private final Function<RECIPE, INGREDIENT_B> inputBExtractor;
    private final Function<RECIPE, INGREDIENT_C> inputCExtractor;
    private final CACHE_A cacheA;
    private final CACHE_B cacheB;
    private final CACHE_C cacheC;

    protected MekUtTripleInputRecipeCache(MekALRecipeType<?, RECIPE, ?> recipeType,
            Function<RECIPE, INGREDIENT_A> inputAExtractor, Function<RECIPE, INGREDIENT_B> inputBExtractor,
            Function<RECIPE, INGREDIENT_C> inputCExtractor, CACHE_A cacheA, CACHE_B cacheB, CACHE_C cacheC) {
        super(recipeType);
        this.inputAExtractor = inputAExtractor;
        this.inputBExtractor = inputBExtractor;
        this.inputCExtractor = inputCExtractor;
        this.cacheA = cacheA;
        this.cacheB = cacheB;
        this.cacheC = cacheC;
    }

    @Override
    public void clear() {
        super.clear();
        cacheA.clear();
        cacheB.clear();
        cacheC.clear();
    }

    public boolean containsInputA(Level world, INPUT_A inputA) {
        if (cacheA.isEmpty(inputA)) {
            return false;
        }
        initCacheIfNeeded(world);
        return cacheA.contains(inputA);
    }

    public boolean containsInputB(Level world, INPUT_B inputB) {
        if (cacheB.isEmpty(inputB)) {
            return false;
        }
        initCacheIfNeeded(world);
        return cacheB.contains(inputB);
    }

    public boolean containsInputC(Level world, INPUT_C inputC) {
        if (cacheC.isEmpty(inputC)) {
            return false;
        }
        initCacheIfNeeded(world);
        return cacheC.contains(inputC);
    }

    public boolean containsInputABC(Level world, INPUT_A inputA, INPUT_B inputB, INPUT_C inputC) {
        if (cacheA.isEmpty(inputA)) {
            return false;
        }
        initCacheIfNeeded(world);
        return cacheA.contains(inputA, recipe -> recipe.testType(inputA, inputB, inputC));
    }

    public boolean containsInputBAC(Level world, INPUT_A inputA, INPUT_B inputB, INPUT_C inputC) {
        if (cacheB.isEmpty(inputB)) {
            return false;
        }
        initCacheIfNeeded(world);
        return cacheB.contains(inputB, recipe -> recipe.testType(inputA, inputB, inputC));
    }

    public boolean containsInputCAB(Level world, INPUT_A inputA, INPUT_B inputB, INPUT_C inputC) {
        if (cacheC.isEmpty(inputC)) {
            return false;
        }
        initCacheIfNeeded(world);
        return cacheC.contains(inputC, recipe -> recipe.testType(inputA, inputB, inputC));
    }

    public RECIPE findFirstRecipe(Level world, INPUT_A inputA, INPUT_B inputB, INPUT_C inputC) {
        if (cacheA.isEmpty(inputA) || cacheB.isEmpty(inputB) || cacheC.isEmpty(inputC)) {
            return null;
        }
        initCacheIfNeeded(world);
        return cacheA.findFirstRecipe(inputA, recipe -> recipe.test(inputA, inputB, inputC));
    }

    @Override
    protected void initCache(List<RecipeHolder<RECIPE>> recipeHolders) {
        for (RecipeHolder<RECIPE> recipeHolder : recipeHolders) {
            RECIPE recipe = recipeHolder.value();
            cacheA.mapInputs(recipe, inputAExtractor.apply(recipe));
            cacheB.mapInputs(recipe, inputBExtractor.apply(recipe));
            cacheC.mapInputs(recipe, inputCExtractor.apply(recipe));
        }
    }

    public static class ItemItemFluid<RECIPE extends MekanismRecipe<?> & TriPredicate<ItemStack, ItemStack, FluidStack> & TriTypePredicate<ItemStack, ItemStack, FluidStack>>
            extends
            MekUtTripleInputRecipeCache<ItemStack, ItemStackIngredient, ItemStack, ItemStackIngredient, FluidStack, FluidStackIngredient, RECIPE, ItemInputCache<RECIPE>, ItemInputCache<RECIPE>, FluidInputCache<RECIPE>> {

        protected ItemItemFluid(MekALRecipeType<?, RECIPE, ?> recipeType,
                Function<RECIPE, ItemStackIngredient> inputAExtractor,
                Function<RECIPE, ItemStackIngredient> inputBExtractor,
                Function<RECIPE, FluidStackIngredient> inputCExtractor) {
            super(recipeType, inputAExtractor, inputBExtractor, inputCExtractor,
                    new ItemInputCache<>(), new ItemInputCache<>(), new FluidInputCache<>());
        }

        public static ItemItemFluid<GreenHouseRecipe> greenHouse(MekALRecipeType<?, GreenHouseRecipe, ?> recipeType) {
            return new ItemItemFluid<>(recipeType, GreenHouseRecipe::getCropIngredient,
                    GreenHouseRecipe::getSoilIngredient, GreenHouseRecipe::getFertilizerIngredient);
        }

    }

}
