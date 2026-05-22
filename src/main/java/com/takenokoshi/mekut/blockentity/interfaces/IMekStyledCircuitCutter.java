package com.takenokoshi.mekut.blockentity.interfaces;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.glodblock.github.extendedae.recipe.CircuitCutterRecipe;
import com.takenokoshi.mekut.recipe.IMekUtRecipeTypeProvider;
import com.takenokoshi.mekut.recipe.WrappedRecipeType;
import com.takenokoshi.mekut.recipe.inputcache.SingleItemRecipeCache;
import com.takenokoshi.mekut.recipe.lookup.IMekUtRecipeTypedLookupHandler;

import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.inputs.IInputHandler;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public interface IMekStyledCircuitCutter
        extends IMekUtRecipeTypedLookupHandler<CircuitCutterRecipe, SingleItemRecipeCache<CircuitCutterRecipe>>,
        IHasMachineEnergyCntainer {

    public static final List<RecipeError> TRACKED_ERROR_TYPES = List.of(
            RecipeError.NOT_ENOUGH_ENERGY,
            RecipeError.NOT_ENOUGH_INPUT,
            RecipeError.NOT_ENOUGH_OUTPUT_SPACE,
            RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT);

    default IMekUtRecipeTypeProvider<RecipeInput, CircuitCutterRecipe, SingleItemRecipeCache<CircuitCutterRecipe>> getRecipeType() {
        return WrappedRecipeType.EXTENDEDAE_CIRCUIT_CUTTER;
    }

    default boolean containsRecipe(ItemStack input) {
        return getRecipeType().getInputCache().containsInput(getHandlerWorld(), input);
    }

    default @Nullable CircuitCutterRecipe findFirstRecipe(IInputHandler<ItemStack> inputHandler) {
        return getRecipeType().getInputCache().findFirstRecipe(getHandlerWorld(), inputHandler.getInput());
    }

    double getScaledProgress();

}
