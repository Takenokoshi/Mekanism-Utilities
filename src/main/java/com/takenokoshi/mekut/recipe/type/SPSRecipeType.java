package com.takenokoshi.mekut.recipe.type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekaddonlib.recipe.type.MekALRecipeType;
import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.recipe.inputcache.MUSingleInputRecipeCache;
import com.takenokoshi.mekut.recipe.recipe.basic.BasicSPSRecipe;

import mekanism.api.recipes.ChemicalToChemicalRecipe;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.api.recipes.vanilla_input.SingleChemicalRecipeInput;
import mekanism.common.config.MekanismConfig;
import mekanism.common.registries.MekanismChemicals;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

public class SPSRecipeType extends
        MekALRecipeType<SingleChemicalRecipeInput, ChemicalToChemicalRecipe, MUSingleInputRecipeCache.MUSingleChemical<ChemicalToChemicalRecipe>> {

    public SPSRecipeType(ResourceLocation name) {
        super(name, type -> new MUSingleInputRecipeCache.MUSingleChemical<>(type,
                r -> r.getInput().ingredient().getChemicalHolders().stream().map(Holder::value).toList()));
    }

    @Override
    protected @NotNull List<RecipeHolder<ChemicalToChemicalRecipe>> getRecipesUncached(
            @NotNull RecipeManager recipeManager, @Nullable RegistryAccess registryAccess) {
        List<RecipeHolder<ChemicalToChemicalRecipe>> list = new ArrayList<>(
                super.getRecipesUncached(recipeManager, registryAccess));
        list.add(new RecipeHolder<ChemicalToChemicalRecipe>(MekUtConstants.rl("/runtime_generated/sps/antimatter"),
                new BasicSPSRecipe(
                        IngredientCreatorAccess.chemicalStack().from(
                                MekanismChemicals.POLONIUM
                                        .asStack(MekanismConfig.general.spsInputPerAntimatter.getAsLong())),
                        MekanismChemicals.ANTIMATTER.asStack(1))));
        return Collections.unmodifiableList(list);
    }

}
