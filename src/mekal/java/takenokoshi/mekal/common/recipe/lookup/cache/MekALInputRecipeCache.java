package takenokoshi.mekal.common.recipe.lookup.cache;

import java.util.function.BiPredicate;
import java.util.function.Function;

import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.common.recipe.MekanismRecipeType;
import mekanism.common.recipe.lookup.cache.DoubleInputRecipeCache;
import mekanism.common.recipe.lookup.cache.type.FluidInputCache;
import mekanism.common.recipe.lookup.cache.type.ItemInputCache;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import takenokoshi.mekal.api.recipes.ItemStackFluidToObjectRecipe;

public class MekALInputRecipeCache {
    public static class ItemFluid<RECIPE extends MekanismRecipe<?> & BiPredicate<ItemStack, FluidStack>>
            extends
            DoubleInputRecipeCache<ItemStack, ItemStackIngredient, FluidStack, FluidStackIngredient, RECIPE, ItemInputCache<RECIPE>, FluidInputCache<RECIPE>> {

        public ItemFluid(MekanismRecipeType<?, RECIPE, ?> recipeType,
                Function<RECIPE, ItemStackIngredient> inputAExtractor,
                Function<RECIPE, FluidStackIngredient> inputBExtractor) {
            super(recipeType, inputAExtractor, new ItemInputCache<>(), inputBExtractor, new FluidInputCache<>());
        }

        public static <RECIPE extends ItemStackFluidToObjectRecipe<?>> ItemFluid<RECIPE> createSimple(
                MekanismRecipeType<?, RECIPE, ?> recipeType) {
            return new ItemFluid<>(recipeType,
                    ItemStackFluidToObjectRecipe::getItemInput,
                    ItemStackFluidToObjectRecipe::getFluidInput);
        }

    }
}
