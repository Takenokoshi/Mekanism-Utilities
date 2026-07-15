package com.takenokoshi.mekut.blockentity.interfaces.machine;

import java.util.List;

import com.takenokoshi.mekaddonlib.recipe.lookup.IMekALRecipeTypedLookupHandler;
import com.takenokoshi.mekaddonlib.recipe.type.IMekALRecipeTypeProvider;
import com.takenokoshi.mekut.blockentity.interfaces.IHasMachineEnergyContainer;
import com.takenokoshi.mekut.recipe.input.AdvancedIngredientInputHandler;
import com.takenokoshi.mekut.recipe.inputcache.MUSingleInputRecipeCache;
import com.takenokoshi.mekut.recipe.type.WrappedRecipeType;

import mekanism.api.chemical.IChemicalTank;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;

public interface ITweakedEnergizedSmelter
        extends IMekALRecipeTypedLookupHandler<SmeltingRecipe, MUSingleInputRecipeCache.MUSingleItem<SmeltingRecipe>>,
        IHasMachineEnergyContainer {

    public static final List<RecipeError> TRACKED_ERROR_TYPES = List.of(
            RecipeError.NOT_ENOUGH_ENERGY,
            RecipeError.NOT_ENOUGH_INPUT,
            RecipeError.NOT_ENOUGH_OUTPUT_SPACE,
            RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT);

    default IMekALRecipeTypeProvider<SingleRecipeInput, SmeltingRecipe, MUSingleInputRecipeCache.MUSingleItem<SmeltingRecipe>> getRecipeType() {
        return WrappedRecipeType.VANILLA_SMELTING;
    };

    IChemicalTank getXpTank();

    default boolean containsInput(ItemStack input) {
        return getRecipeType().getInputCache().containsInput(getLevel(), input);
    }

    default SmeltingRecipe findFirstRecipe(AdvancedIngredientInputHandler inputHandler) {
        return getRecipeType().getInputCache().findFirstRecipe(getLevel(), inputHandler.getInput());
    }

    double getScaledProgress();
}
