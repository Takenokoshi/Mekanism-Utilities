package com.takenokoshi.mekut.recipe.building;

import com.fxd927.mekanismelements.common.registries.MSGases;
import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.recipe.builder.BiChemicalToItemRecipeBuilder;
import com.takenokoshi.mekut.registries.MekUtItems;

import mekanism.api.recipes.ingredients.creator.IChemicalStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismChemicals;
import net.minecraft.data.recipes.RecipeOutput;

public class BuildStellarGenesisRecipe {
    public static void build(RecipeOutput output) {
        IChemicalStackIngredientCreator creatorC = IngredientCreatorAccess.chemicalStack();
        BiChemicalToItemRecipeBuilder.stellarGenesis(
                creatorC.from(MekanismChemicals.HYDROGEN.asStack(1000000000)),
                creatorC.from(MSGases.HELIUM.asStack(200000000)),
                MekUtItems.ARTIFICIAL_STAR.asStack())
                .build(output, MekUtConstants.rl("stellar_genesis/artificial_star"));
    }
}
