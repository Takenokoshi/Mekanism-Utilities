package com.takenokoshi.mekut.recipe.building;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.registries.MekUtChemicals;
import com.takenokoshi.mekut.registries.MekUtItems;

import appeng.core.AppEng;
import appeng.core.definitions.AEItems;
import mekanism.api.datagen.recipe.builder.ItemStackChemicalToItemStackRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IChemicalStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IItemStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismChemicals;
import mekanism.common.tags.MekanismTags;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

public class MetallurgicInfusingRecipes {

    public static void build(RecipeOutput output) {
        IItemStackIngredientCreator creatorI = IngredientCreatorAccess.item();
        IChemicalStackIngredientCreator creatorC = IngredientCreatorAccess.chemicalStack();
        ItemStackChemicalToItemStackRecipeBuilder
                .metallurgicInfusing(
                        creatorI.from(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ingots/tin"))),
                        creatorC.from(MekUtChemicals.AMETHYST.asStack(10)),
                        MekUtItems.ELASTIC_ALLOY.asStack(1),
                        false)
                .build(output, MekUtConstants.rl("metallurgic_infusing/elastic_alloy"));
        ItemStackChemicalToItemStackRecipeBuilder
                .metallurgicInfusing(
                        creatorI.from(MekUtItems.ELASTIC_ALLOY),
                        creatorC.from(MekUtChemicals.REFINED_LAPIS_LAZULI.asStack(20)),
                        MekUtItems.CONVERGENT_ALLOY.asStack(1),
                        false)
                .build(output, MekUtConstants.rl("metallurgic_infusing/convergent_alloy"));
        ItemStackChemicalToItemStackRecipeBuilder
                .metallurgicInfusing(
                        creatorI.from(MekUtItems.CONVERGENT_ALLOY),
                        creatorC.from(MekUtChemicals.ENRICHED_XP.asStack(100)),
                        MekUtItems.COMPISITE_ALLOY.asStack(1),
                        false)
                .build(output, MekUtConstants.rl("metallurgic_infusing/composite_alloy"));
        ItemStackChemicalToItemStackRecipeBuilder
                .metallurgicInfusing(
                        creatorI.from(MekanismTags.Items.DUSTS_LAPIS, 1),
                        creatorC.fromHolder(MekanismChemicals.DIAMOND, 10),
                        MekUtItems.REFINED_LAPIS_LAZULI_DUST.asStack(1),
                        false)
                .build(output, MekUtConstants.rl("metallurgic_infusing/refined_lapis_lazuli_dust"));
        ItemStackChemicalToItemStackRecipeBuilder
                .metallurgicInfusing(
                        creatorI.from(Items.REDSTONE, 16),
                        creatorC.from(MekanismChemicals.GOLD.asStack(640)),
                        MekUtItems.GOLDEN_REDSTONE.asStack(16),
                        false)
                .build(output, MekUtConstants.rl("metallurgic_infusing/golden_redstone"));
        ItemStackChemicalToItemStackRecipeBuilder
                .metallurgicInfusing(
                        creatorI.from(MekUtItems.GOLDEN_REDSTONE, 16),
                        creatorC.from(MekUtChemicals.XP.asStack(160)),
                        new ItemStack(Items.GLOWSTONE_DUST, 16),
                        false)
                .build(output, MekUtConstants.rl("metallurgic_infusing/glowstone_dust"));
        ItemStackChemicalToItemStackRecipeBuilder
                .metallurgicInfusing(
                        creatorI.from(AEItems.SINGULARITY.stack()),
                        creatorC.from(MekUtChemicals.XP.asStack(1000)),
                        new ItemStack(Items.ENDER_PEARL, 2),
                        false)
                .addCondition(new ModLoadedCondition(AppEng.MOD_ID))
                .build(output, MekUtConstants.rl("metallurgic_infusing/ender_pearl"));
    }
}
