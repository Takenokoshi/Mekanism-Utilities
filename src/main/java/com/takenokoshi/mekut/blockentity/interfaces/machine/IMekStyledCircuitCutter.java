package com.takenokoshi.mekut.blockentity.interfaces.machine;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.glodblock.github.extendedae.recipe.CircuitCutterRecipe;
import com.takenokoshi.mekut.blockentity.interfaces.IHasMachineEnergyContainer;
import com.takenokoshi.mekut.recipe.inputcache.MUSingleInputRecipeCache;
import com.takenokoshi.mekut.recipe.lookup.IMekUtRecipeTypedLookupHandler;
import com.takenokoshi.mekut.recipe.type.IMekUtRecipeTypeProvider;
import com.takenokoshi.mekut.recipe.type.WrappedRecipeType;

import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.inputs.IInputHandler;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public interface IMekStyledCircuitCutter
        extends IMekUtRecipeTypedLookupHandler<CircuitCutterRecipe, MUSingleInputRecipeCache.MUSingleItem<CircuitCutterRecipe>>,
        IHasMachineEnergyContainer {

    public static final List<RecipeError> TRACKED_ERROR_TYPES = List.of(
            RecipeError.NOT_ENOUGH_ENERGY,
            RecipeError.NOT_ENOUGH_INPUT,
            RecipeError.NOT_ENOUGH_OUTPUT_SPACE,
            RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT);

    default IMekUtRecipeTypeProvider<RecipeInput, CircuitCutterRecipe, MUSingleInputRecipeCache.MUSingleItem<CircuitCutterRecipe>> getRecipeType() {
        return WrappedRecipeType.EXTENDEDAE_CIRCUIT_CUTTER;
    }

    default boolean containsRecipe(ItemStack input) {
        return getRecipeType().getInputCache().containsInput(getLevel(), input);
    }

    default @Nullable CircuitCutterRecipe findFirstRecipe(IInputHandler<ItemStack> inputHandler) {
        return getRecipeType().getInputCache().findFirstRecipe(getLevel(), inputHandler.getInput());
    }

    double getScaledProgress();

}
