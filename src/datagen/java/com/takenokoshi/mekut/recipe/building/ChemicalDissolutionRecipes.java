package com.takenokoshi.mekut.recipe.building;

import com.fxd927.mekanismelements.common.registries.MSGases;
import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.registries.MekUtChemicals;
import com.takenokoshi.mekut.registries.MekUtItems;

import mekanism.api.datagen.recipe.builder.ChemicalDissolutionRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IChemicalStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IItemStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public class ChemicalDissolutionRecipes {
    public static void build(RecipeOutput output) {
        IItemStackIngredientCreator creatorI = IngredientCreatorAccess.item();
        IChemicalStackIngredientCreator creatorC = IngredientCreatorAccess.chemicalStack();
        ChemicalDissolutionRecipeBuilder
                .dissolution(
                        creatorI.from(1, new ItemLike[] { Items.NETHER_STAR, MekUtItems.ARTIFICIAL_STAR }),
                        creatorC.from(MSGases.AQUA_REGIA.asStack(10)),
                        MekUtChemicals.ASTRAL_ETHER.asStack(1000),
                        true)
                .build(output, MekUtConstants.rl("dissolution/astral_ether"));
    }
}
