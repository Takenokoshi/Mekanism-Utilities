package com.takenokoshi.mekut.recipe.building;

import com.fxd927.mekanismelements.common.registries.MSGases;
import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.registries.MekUtChemicals;
import com.takenokoshi.mekut.registries.MekUtFluids;

import appeng.core.definitions.AEItems;
import mekanism.api.datagen.recipe.builder.PressurizedReactionRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IChemicalStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IFluidStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IItemStackIngredientCreator;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismChemicals;
import mekanism.common.registries.MekanismFluids;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class BuildMekReactionRecipe {

    public static void build(RecipeOutput output) {
        IItemStackIngredientCreator creatorI = IngredientCreatorAccess.item();
        IFluidStackIngredientCreator creatorF = IngredientCreatorAccess.fluid();
        IChemicalStackIngredientCreator creatorC = IngredientCreatorAccess.chemicalStack();
        PressurizedReactionRecipeBuilder
                .reaction(
                        creatorI.from(AEItems.SINGULARITY),
                        creatorF.from(MekUtFluids.XP.asStack(100)),
                        creatorC.from(MekanismChemicals.SULFURIC_ACID.asStack(100)),
                        20,
                        new ItemStack(Items.ENDER_PEARL, 4),
                        MSGases.NITROGEN_DIOXIDE.asStack(100))
                .build(output, MekUtConstants.rl("mek_reaction/ender_pearl"));
        PressurizedReactionRecipeBuilder
                .reaction(
                        creatorI.from(Items.BLAZE_POWDER, 64),
                        creatorF.from(MekanismFluids.SODIUM.asStack(100)),
                        creatorC.from(MekanismChemicals.CARBON.asStack(200)),
                        20,
                        new ItemStack(Items.BLAZE_POWDER, 64),
                        MekUtChemicals.BLAZE_ETHER.asStack(200))
                .build(output, MekUtConstants.rl("mek_reaction/blaze_ether"));
    }
}
