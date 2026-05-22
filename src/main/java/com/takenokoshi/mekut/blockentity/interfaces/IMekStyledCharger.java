package com.takenokoshi.mekut.blockentity.interfaces;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekut.recipe.IMekUtRecipeTypeProvider;
import com.takenokoshi.mekut.recipe.WrappedRecipeType;
import com.takenokoshi.mekut.recipe.input.IngredientInputHandler;
import com.takenokoshi.mekut.recipe.inputcache.SingleItemRecipeCache;
import com.takenokoshi.mekut.recipe.lookup.IMekUtRecipeTypedLookupHandler;

import appeng.recipes.handlers.ChargerRecipe;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public interface IMekStyledCharger
        extends IMekUtRecipeTypedLookupHandler<ChargerRecipe, SingleItemRecipeCache<ChargerRecipe>> ,IHasMachineEnergyCntainer{

    public static final List<RecipeError> TRACKED_ERROR_TYPES = List.of(
            RecipeError.NOT_ENOUGH_ENERGY,
            RecipeError.NOT_ENOUGH_INPUT,
            RecipeError.NOT_ENOUGH_OUTPUT_SPACE,
            RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT);

    default IMekUtRecipeTypeProvider<RecipeInput, ChargerRecipe, SingleItemRecipeCache<ChargerRecipe>> getRecipeType() {
        return WrappedRecipeType.AE2_CHARGER;
    }

    default boolean containsRecipe(ItemStack input){
        return getRecipeType().getInputCache().containsInput(getHandlerWorld(), input);
    }

    default @Nullable ChargerRecipe findFirstRecipe(IngredientInputHandler inputHandler){
        return getRecipeType().getInputCache().findFirstRecipe(getHandlerWorld(), inputHandler.getInput());
    }

    double getScaledProgress();
}
