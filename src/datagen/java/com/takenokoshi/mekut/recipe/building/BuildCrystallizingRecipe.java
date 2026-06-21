package com.takenokoshi.mekut.recipe.building;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.registries.MekUtChemicals;
import com.takenokoshi.mekut.registries.MekUtItems;

import mekanism.api.datagen.recipe.builder.ChemicalCrystallizerRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IChemicalStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismItems;
import net.minecraft.data.recipes.RecipeOutput;

public class BuildCrystallizingRecipe {
    public static void build(RecipeOutput output) {
        IChemicalStackIngredientCreator creatorC = IngredientCreatorAccess.chemicalStack();
        ChemicalCrystallizerRecipeBuilder
                .crystallizing(
                        creatorC.from(MekUtChemicals.XP.asStack(100000)),
                        MekUtItems.XP_CRYSTAL.asStack(1))
                .build(output, MekUtConstants.rl("crystallizing/xp_crystal"));
        ChemicalCrystallizerRecipeBuilder
                .crystallizing(
                        creatorC.from(MekUtChemicals.BLAZE_ETHER.asStack(200)),
                        MekUtItems.BLAZE_CRYSTAL.asStack(1))
                .build(output, MekUtConstants.rl("crystallizing/blaze_crystal"));
        ChemicalCrystallizerRecipeBuilder
                .crystallizing(
                        creatorC.from(MekUtChemicals.IRIDIUM.asStack(1000)),
                        MekUtItems.IRIDIUM_DUST.asStack(1))
                .build(output, MekUtConstants.rl("crystallizing/iridium"));
        ChemicalCrystallizerRecipeBuilder
                .crystallizing(
                        creatorC.from(MekUtChemicals.NETHERITE.asStack(1000)),
                        MekanismItems.NETHERITE_DUST.asStack(1))
                .build(output, MekUtConstants.rl("crystallizing/netherite"));
    }
}
