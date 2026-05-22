package com.takenokoshi.mekut.blockentity.interfaces;

import java.util.List;

import com.takenokoshi.mekut.recipe.IMekUtRecipeTypeProvider;
import com.takenokoshi.mekut.recipe.WrappedRecipeType;
import com.takenokoshi.mekut.recipe.input.IngredientInputHandler;
import com.takenokoshi.mekut.recipe.inputcache.SingleItemRecipeCache;
import com.takenokoshi.mekut.recipe.lookup.IMekUtRecipeTypedLookupHandler;

import mekanism.api.chemical.IChemicalTank;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;

public interface ITweakedEnergizedSmelter
        extends IMekUtRecipeTypedLookupHandler<SmeltingRecipe, SingleItemRecipeCache<SmeltingRecipe>>,
        IHasMachineEnergyCntainer {

    public static final List<RecipeError> TRACKED_ERROR_TYPES = List.of(
            RecipeError.NOT_ENOUGH_ENERGY,
            RecipeError.NOT_ENOUGH_INPUT,
            RecipeError.NOT_ENOUGH_OUTPUT_SPACE,
            RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT);

    default IMekUtRecipeTypeProvider<SingleRecipeInput, SmeltingRecipe, SingleItemRecipeCache<SmeltingRecipe>> getRecipeType() {
        return WrappedRecipeType.VANILLA_SMELTING;
    };

    IChemicalTank getXpTank();

    default boolean containsInput(ItemStack input) {
        return getRecipeType().getInputCache().containsInput(getHandlerWorld(), input);
    }

    default SmeltingRecipe findFirstRecipe(IngredientInputHandler inputHandler) {
        return getRecipeType().getInputCache().findFirstRecipe(getHandlerWorld(), inputHandler.getInput());
    }

    double getScaledProgress();
}
