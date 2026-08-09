package com.takenokoshi.mekut.recipe.building;

import fixdol.mekanismelements.common.MekanismElements;
import fixdol.mekanismelements.common.registries.MSItems;
import com.takenokoshi.mekut.core.MekUtConstants;

import mekanism.api.datagen.recipe.builder.ItemStackChemicalToItemStackRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IChemicalStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IItemStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismChemicals;
import mekanism.common.registries.MekanismItems;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

public class InjectingRecipes {

    public static void build(RecipeOutput output) {
        IItemStackIngredientCreator creatorI = IngredientCreatorAccess.item();
        IChemicalStackIngredientCreator creatorC = IngredientCreatorAccess.chemicalStack();
        ItemStackChemicalToItemStackRecipeBuilder
                .injecting(
                        creatorI.from(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ingots/iron"))),
                        creatorC.from(MekanismChemicals.OXYGEN.asStack(1)),
                        MekanismItems.STEEL_INGOT.asStack(1),
                        true)
                .build(output, MekUtConstants.rl("injecting/steel_ingot"));
        ItemStackChemicalToItemStackRecipeBuilder
                .injecting(
                        creatorI.from(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "dusts/iron"))),
                        creatorC.from(MekanismChemicals.OXYGEN.asStack(1)),
                        MekanismItems.STEEL_DUST.asStack(1),
                        true)
                .build(output, MekUtConstants.rl("injecting/steel_dust"));
        ItemStackChemicalToItemStackRecipeBuilder
                .injecting(
                        creatorI.from(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "dusts/emerald")), 5),
                        creatorC.from(MekanismChemicals.SULFURIC_ACID.asStack(1)),
                        MSItems.DUST_BERYLLIUM.asStack(1),
                        true)
                .addCondition(new ModLoadedCondition(MekanismElements.MODID))
                .build(output, MekUtConstants.rl("injecting/beryllium_dust"));
    }
}
