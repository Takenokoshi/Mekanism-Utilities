package com.takenokoshi.mekut.blockentity.interfaces;

import java.util.List;

import com.takenokoshi.mekut.recipe.input.IngredientInputHandler;
import com.takenokoshi.mekut.recipe.inputcache.MUSingleInputRecipeCache;
import com.takenokoshi.mekut.recipe.lookup.IMekUtRecipeTypedLookupHandler;
import com.takenokoshi.mekut.recipe.type.IMekUtRecipeTypeProvider;
import com.takenokoshi.mekut.recipe.type.WrappedRecipeType;

import mekanism.api.chemical.IChemicalTank;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;

public interface ITweakedEnergizedSmelter
        extends IMekUtRecipeTypedLookupHandler<SmeltingRecipe, MUSingleInputRecipeCache.MUSingleItem<SmeltingRecipe>>,
        IHasMachineEnergyCntainer {

    public static final List<RecipeError> TRACKED_ERROR_TYPES = List.of(
            RecipeError.NOT_ENOUGH_ENERGY,
            RecipeError.NOT_ENOUGH_INPUT,
            RecipeError.NOT_ENOUGH_OUTPUT_SPACE,
            RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT);

    default IMekUtRecipeTypeProvider<SingleRecipeInput, SmeltingRecipe, MUSingleInputRecipeCache.MUSingleItem<SmeltingRecipe>> getRecipeType() {
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
