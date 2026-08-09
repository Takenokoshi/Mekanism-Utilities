package com.takenokoshi.mekut.recipe.type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekut.recipe.recipe.basic.BasicSmallDigitalReactionChamberRecipe;
import com.takenokoshi.mekut.recipe.recipe.prefab.ItemStackListFluidChemicalToItemFluidChemicalRecipe;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.math.MathUtils;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.config.MekanismConfig;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.pedroksl.advanced_ae.recipes.ReactionChamberRecipe;

public class AAERecipeProvider {
    public static List<RecipeHolder<ItemStackListFluidChemicalToItemFluidChemicalRecipe>> getConvertedRecipes(
            @NotNull RecipeManager recipeManager, @Nullable RegistryAccess registryAccess) {
        List<RecipeHolder<ItemStackListFluidChemicalToItemFluidChemicalRecipe>> result = new ArrayList<>();
        recipeManager.getAllRecipesFor(ReactionChamberRecipe.TYPE).forEach(holder -> {
            result.add(new RecipeHolder<>(
                    ResourceLocation.fromNamespaceAndPath(holder.id().getNamespace(),
                            "/runtime_generated/small_digital_reaction_chamber/from_reaction_chamber/"
                                    + holder.id().getPath()),
                    convertAAE(holder.value())));
        });
        return Collections.unmodifiableList(result);
    }

    public static BasicSmallDigitalReactionChamberRecipe convertAAE(ReactionChamberRecipe recipe) {
        var fluid = recipe.getFluid();
        return new BasicSmallDigitalReactionChamberRecipe(
                recipe.getInputs().stream()
                        .map(value -> IngredientCreatorAccess.item().from(value.getIngredient(), value.getAmount()))
                        .toList(),
                fluid == null ? null
                        : IngredientCreatorAccess.fluid().from(fluid.getIngredient(), fluid.getAmount()),
                null,
                recipe.getResultItem(), recipe.getResultFluid(), ChemicalStack.EMPTY,
                MathUtils.clampToLong(recipe.getEnergy() * MekanismConfig.general.forgeConversionRate.getAsDouble()),
                100);
    }
}
