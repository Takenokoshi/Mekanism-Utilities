package com.takenokoshi.mekut.recipe.building;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.recipe.builder.AdsorptionRecipeBuilder;
import com.takenokoshi.mekut.registries.MekUtChemicals;

import mekanism.api.recipes.ingredients.creator.IFluidStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IItemStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;

public class AdsorptionRecipes {

    public static void build(RecipeOutput output) {
        IItemStackIngredientCreator creatorI = IngredientCreatorAccess.item();
        IFluidStackIngredientCreator creatorF = IngredientCreatorAccess.fluid();
        AdsorptionRecipeBuilder
                .adsorption(
                        creatorI.from(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "dusts/tin"))),
                        creatorF.from(new FluidStack(Fluids.WATER, 1000)),
                        MekUtChemicals.HEAVY_WATER_STEAM.asStack(50),
                        MekUtConstants.rl("adsoption/heavy_water_steam"))
                .build(output);
    }
}
