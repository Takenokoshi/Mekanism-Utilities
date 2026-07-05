package com.takenokoshi.mekut.recipe.building;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.registries.MekUtItems;

import mekanism.api.datagen.recipe.builder.NucleosynthesizingRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismChemicals;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class AntiprotonicNucleoSynthesizeRecipes {

    public static void build(RecipeOutput output) {
        NucleosynthesizingRecipeBuilder
                .nucleosynthesizing(
                        IngredientCreatorAccess.item().from(MekUtItems.ARTIFICIAL_STAR, 1),
                        IngredientCreatorAccess.chemicalStack().from(MekanismChemicals.ANTIMATTER.asStack(5)),
                        new ItemStack(Items.HEART_OF_THE_SEA, 1),
                        1250,
                        false)
                .build(output, MekUtConstants.rl("antiprotonic_nucleosynthesizing/artificial_star/heart_of_the_sea"));
    }
}
