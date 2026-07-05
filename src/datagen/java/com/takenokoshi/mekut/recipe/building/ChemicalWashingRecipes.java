package com.takenokoshi.mekut.recipe.building;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.registries.MekUtChemicals;

import mekanism.api.datagen.recipe.builder.FluidChemicalToChemicalRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.data.recipes.RecipeOutput;
import net.neoforged.neoforge.common.Tags;

public class ChemicalWashingRecipes {
    public static void build(RecipeOutput output) {
        FluidChemicalToChemicalRecipeBuilder
                .washing(
                        IngredientCreatorAccess.fluid().from(Tags.Fluids.WATER, 1),
                        IngredientCreatorAccess.chemicalStack().from(MekUtChemicals.XP.asStack(10000)),
                        MekUtChemicals.ENRICHED_XP.asStack(1))
                .build(output, MekUtConstants.rl("chemical_washing/enriched_xp"));
    }

}
