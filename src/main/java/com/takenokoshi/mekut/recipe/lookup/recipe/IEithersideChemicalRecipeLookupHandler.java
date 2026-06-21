package com.takenokoshi.mekut.recipe.lookup.recipe;

import java.util.function.BiPredicate;

import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekut.recipe.inputcache.MUEitherSideInputRecipeCache;
import com.takenokoshi.mekut.recipe.lookup.IMekUtRecipeTypedLookupHandler;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.vanilla_input.BiChemicalRecipeInput;
import mekanism.common.recipe.lookup.cache.type.ChemicalInputCache;

public interface IEithersideChemicalRecipeLookupHandler<RECIPE extends MekanismRecipe<BiChemicalRecipeInput> & BiPredicate<ChemicalStack, ChemicalStack>>
        extends
        IMekUtRecipeTypedLookupHandler<RECIPE, MUEitherSideInputRecipeCache<ChemicalStack, ChemicalStackIngredient, RECIPE, ChemicalInputCache<RECIPE>>> {

    default boolean containsRecipe(ChemicalStack input) {
        return getRecipeType().getInputCache().containsInput(getLevel(), input);
    }

    default boolean containsRecipe(ChemicalStack inputA, ChemicalStack inputB) {
        return getRecipeType().getInputCache().containsInput(getLevel(), inputA, inputB);
    }

    @Nullable
    default RECIPE findFirstRecipe(ChemicalStack inputA, ChemicalStack inputB) {
        return getRecipeType().getInputCache().findFirstRecipe(getLevel(), inputA, inputB);
    }

    @Nullable
    default RECIPE findFirstRecipe(IInputHandler<ChemicalStack> handlerA, IInputHandler<ChemicalStack> handlerB) {
        return findFirstRecipe(handlerA.getInput(), handlerB.getInput());
    }

}
