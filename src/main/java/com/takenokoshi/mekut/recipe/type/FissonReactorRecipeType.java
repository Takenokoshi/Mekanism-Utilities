package com.takenokoshi.mekut.recipe.type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.recipe.inputcache.MUSingleInputRecipeCache;
import com.takenokoshi.mekut.recipe.recipe.basic.BasicFissionReactorRecipe;
import com.takenokoshi.mekut.recipe.recipe.prefab.ChemicalToChemicalHeatRecipe;

import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.api.recipes.vanilla_input.SingleChemicalRecipeInput;
import mekanism.common.registries.MekanismChemicals;
import mekanism.generators.common.config.MekanismGeneratorsConfig;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

public class FissonReactorRecipeType extends
        MekUtRecipeType<SingleChemicalRecipeInput, ChemicalToChemicalHeatRecipe, MUSingleInputRecipeCache.MUSingleChemical<ChemicalToChemicalHeatRecipe>> {

    public FissonReactorRecipeType(ResourceLocation name) {
        super(name, type -> new MUSingleInputRecipeCache.MUSingleChemical<>(type,
                r -> r.getInput().ingredient().getChemicalHolders().stream().map(Holder::value).toList()));
    }

    @Override
    protected @NotNull List<RecipeHolder<ChemicalToChemicalHeatRecipe>> getRecipesUncached(
            @NotNull RecipeManager recipeManager, @Nullable RegistryAccess registryAccess) {
        List<RecipeHolder<ChemicalToChemicalHeatRecipe>> list = new ArrayList<>(
                super.getRecipesUncached(recipeManager, registryAccess));
        list.add(new RecipeHolder<>(MekUtConstants.rl("/runtime_generated/fission_reactor/nuclear_waste"),
                new BasicFissionReactorRecipe(
                        IngredientCreatorAccess.chemicalStack().from(MekanismChemicals.FISSILE_FUEL.asStack(1)),
                        MekanismChemicals.NUCLEAR_WASTE.asStack(1),
                        MekanismGeneratorsConfig.generators.energyPerFissionFuel.getOrDefault())));
        return Collections.unmodifiableList(list);
    }

}
