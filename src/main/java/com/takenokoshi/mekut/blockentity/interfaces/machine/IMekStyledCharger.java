package com.takenokoshi.mekut.blockentity.interfaces.machine;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekut.blockentity.interfaces.IHasMachineEnergyContainer;
import com.takenokoshi.mekut.recipe.input.IngredientInputHandler;
import com.takenokoshi.mekut.recipe.inputcache.MUSingleInputRecipeCache;
import com.takenokoshi.mekut.recipe.lookup.IMekUtRecipeTypedLookupHandler;
import com.takenokoshi.mekut.recipe.type.IMekUtRecipeTypeProvider;
import com.takenokoshi.mekut.recipe.type.WrappedRecipeType;

import appeng.recipes.handlers.ChargerRecipe;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public interface IMekStyledCharger
        extends IMekUtRecipeTypedLookupHandler<ChargerRecipe, MUSingleInputRecipeCache.MUSingleItem<ChargerRecipe>> ,IHasMachineEnergyContainer{

    public static final List<RecipeError> TRACKED_ERROR_TYPES = List.of(
            RecipeError.NOT_ENOUGH_ENERGY,
            RecipeError.NOT_ENOUGH_INPUT,
            RecipeError.NOT_ENOUGH_OUTPUT_SPACE,
            RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT);

    default IMekUtRecipeTypeProvider<RecipeInput, ChargerRecipe, MUSingleInputRecipeCache.MUSingleItem<ChargerRecipe>> getRecipeType() {
        return WrappedRecipeType.AE2_CHARGER;
    }

    default boolean containsRecipe(ItemStack input){
        return getRecipeType().getInputCache().containsInput(getLevel(), input);
    }

    default @Nullable ChargerRecipe findFirstRecipe(IngredientInputHandler inputHandler){
        return getRecipeType().getInputCache().findFirstRecipe(getLevel(), inputHandler.getInput());
    }

    double getScaledProgress();
}
