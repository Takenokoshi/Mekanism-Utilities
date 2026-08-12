package com.takenokoshi.mekut.recipe.lookup.recipe;

import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekaddonlib.recipe.lookup.IMekALRecipeTypedLookupHandler;
import com.takenokoshi.mekut.recipe.inputcache.MekUtTripleInputRecipeCache;
import com.takenokoshi.mekut.recipe.recipe.prefab.GreenHouseRecipe;
import com.takenokoshi.mekut.recipe.recipe.util.TriTypePredicate;

import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.inputs.IInputHandler;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.util.TriPredicate;
import net.neoforged.neoforge.fluids.FluidStack;

public interface ITripleInputRecipeLookupHandler<INPUT_A, INPUT_B, INPUT_C, RECIPE extends MekanismRecipe<?> & TriPredicate<INPUT_A, INPUT_B, INPUT_C> & TriTypePredicate<INPUT_A, INPUT_B, INPUT_C>, RECIPE_CACHE extends MekUtTripleInputRecipeCache<INPUT_A, ?, INPUT_B, ?, INPUT_C, ?, RECIPE, ?, ?, ?>>
        extends IMekALRecipeTypedLookupHandler<RECIPE, RECIPE_CACHE> {

    default boolean containsRecipeA(INPUT_A inputA) {
        return getRecipeType().getInputCache().containsInputA(getLevel(), inputA);
    }

    default boolean containsRecipeB(INPUT_B inputB) {
        return getRecipeType().getInputCache().containsInputB(getLevel(), inputB);
    }

    default boolean containsRecipeC(INPUT_C inputC) {
        return getRecipeType().getInputCache().containsInputC(getLevel(), inputC);
    }

    default boolean containsRecipeABC(INPUT_A inputA, INPUT_B inputB, INPUT_C inputC) {
        return getRecipeType().getInputCache().containsInputABC(getLevel(), inputA, inputB, inputC);
    }

    default boolean containsRecipeBAC(INPUT_A inputA, INPUT_B inputB, INPUT_C inputC) {
        return getRecipeType().getInputCache().containsInputBAC(getLevel(), inputA, inputB, inputC);
    }

    default boolean containsRecipeCAB(INPUT_A inputA, INPUT_B inputB, INPUT_C inputC) {
        return getRecipeType().getInputCache().containsInputCAB(getLevel(), inputA, inputB, inputC);
    }

    @Nullable
    default RECIPE findFirstRecipe(INPUT_A inputA, INPUT_B inputB, INPUT_C inputC) {
        return getRecipeType().getInputCache().findFirstRecipe(getLevel(), inputA, inputB, inputC);
    }

    default RECIPE findFirstRecipe(IInputHandler<INPUT_A> inputHandlerA, IInputHandler<INPUT_B> inputHandlerB,
            IInputHandler<INPUT_C> inputHandlerC) {
        return findFirstRecipe(inputHandlerA.getInput(), inputHandlerB.getInput(), inputHandlerC.getInput());
    }


    public static interface IGreenHouseRecipeLookupHandler extends ITripleInputRecipeLookupHandler<ItemStack,ItemStack,FluidStack,GreenHouseRecipe,MekUtTripleInputRecipeCache.ItemItemFluid<GreenHouseRecipe>> {
    
        
    }
}
