package com.takenokoshi.mekut.recipe.building;

import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.registries.MSGases;
import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.recipe.builder.MUChemicalChemicalToChemicalRecipeBuilder;
import com.takenokoshi.mekut.registries.MekUtChemicals;

import mekanism.api.recipes.ingredients.creator.IChemicalStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismChemicals;
import net.minecraft.data.recipes.RecipeOutput;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

public class LazerCompressNucleoSynthesizeRecipes {

    public static void build(RecipeOutput output) {
        IChemicalStackIngredientCreator creatorC = IngredientCreatorAccess.chemicalStack();
        MUChemicalChemicalToChemicalRecipeBuilder
                .nuclearSynthesize(
                        creatorC.from(MekanismChemicals.PLUTONIUM.asStack(2)),
                        creatorC.from(MekanismChemicals.REDSTONE.asStack(2)),
                        MSGases.AMERICIUM.asStack(1))
                .addCondition(new ModLoadedCondition(MekanismElements.MODID))
                .build(output, MekUtConstants.rl("lazer_compress/americium"));
        MUChemicalChemicalToChemicalRecipeBuilder
                .nuclearSynthesize(
                        creatorC.from(MekanismChemicals.PLUTONIUM.asStack(1)),
                        creatorC.from(MekUtChemicals.REFINED_LAPIS_LAZULI.asStack(1)),
                        MekanismChemicals.POLONIUM.asStack(1))
                .build(output, MekUtConstants.rl("lazer_compress/polonium"));
        MUChemicalChemicalToChemicalRecipeBuilder
                .nuclearSynthesize(
                        creatorC.from(MekanismChemicals.POLONIUM.asStack(100)),
                        creatorC.from(MekanismChemicals.REFINED_OBSIDIAN.asStack(100)),
                        MekUtChemicals.IRIDIUM.asStack(1))
                .build(output, MekUtConstants.rl("lazer_compress/iridium"));
        MUChemicalChemicalToChemicalRecipeBuilder
                .nuclearSynthesize(
                        creatorC.from(MekUtChemicals.IRIDIUM.asStack(1)),
                        creatorC.from(MekanismChemicals.REDSTONE.asStack(5)),
                        MekUtChemicals.NETHERITE.asStack(1))
                .build(output, MekUtConstants.rl("lazer_compress/netherite"));
    }
}
