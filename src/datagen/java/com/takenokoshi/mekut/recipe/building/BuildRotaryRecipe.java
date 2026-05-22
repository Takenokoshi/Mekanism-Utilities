package com.takenokoshi.mekut.recipe.building;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.registries.MekUtChemicals;
import com.takenokoshi.mekut.registries.MekUtFluids;

import mekanism.api.datagen.recipe.builder.RotaryRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IChemicalStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IFluidStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismFluids;
import net.minecraft.data.recipes.RecipeOutput;

public class BuildRotaryRecipe {

    public static void build(RecipeOutput output) {
        IFluidStackIngredientCreator creatorF = IngredientCreatorAccess.fluid();
        IChemicalStackIngredientCreator creatorC = IngredientCreatorAccess.chemicalStack();
        RotaryRecipeBuilder
                .rotary(
                        creatorF.from(MekUtFluids.XP.asStack(1)),
                        creatorC.from(MekUtChemicals.XP.asStack(100)),
                        MekUtChemicals.XP.asStack(100),
                        MekUtFluids.XP.asStack(1))
                .build(output, MekUtConstants.rl("rotary/xp"));
        RotaryRecipeBuilder
                .rotary(
                        creatorF.from(MekanismFluids.HEAVY_WATER.asStack(10)),
                        creatorC.from(MekUtChemicals.HEAVY_WATER_STEAM.asStack(10)),
                        MekUtChemicals.HEAVY_WATER_STEAM.asStack(10),
                        MekanismFluids.HEAVY_WATER.asStack(10))
                .build(output, MekUtConstants.rl("rotary/heavy_water"));
    }
}
