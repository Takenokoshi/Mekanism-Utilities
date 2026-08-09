package com.takenokoshi.mekut.recipe.type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.glodblock.github.extendedae.recipe.CircuitCutterRecipe;
import com.glodblock.github.extendedae.recipe.CrystalAssemblerRecipe;
import com.takenokoshi.mekut.recipe.recipe.basic.BasicChemicalCutRecipe;
import com.takenokoshi.mekut.recipe.recipe.basic.BasicSmallDigitalAssemblerRecipe;
import com.takenokoshi.mekut.recipe.recipe.prefab.ItemStackListFluidChemicalToItemRecipe;

import mekanism.api.recipes.ItemStackChemicalToItemStackRecipe;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.registries.MekanismChemicals;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

public class EAERecipeProvider {
    public static List<RecipeHolder<ItemStackListFluidChemicalToItemRecipe>> getConvertedAssemblerRecipes(
            @NotNull RecipeManager recipeManager, @Nullable RegistryAccess registryAccess) {
        List<RecipeHolder<ItemStackListFluidChemicalToItemRecipe>> result = new ArrayList<>();
        List<RecipeHolder<CrystalAssemblerRecipe>> crystalAssemblerRecipes = recipeManager
                .getAllRecipesFor(CrystalAssemblerRecipe.TYPE);
        for (RecipeHolder<CrystalAssemblerRecipe> holder : crystalAssemblerRecipes) {
            CrystalAssemblerRecipe assemblerRecipe = holder.value();
            result.add(new RecipeHolder<>(
                    ResourceLocation.fromNamespaceAndPath(holder.id().getNamespace(),
                            "/runtime_generated/small_digital_assembler/from_crystal_assemblers/"
                                    + holder.id().getPath()),
                    convertCrystalAssembler(assemblerRecipe)));
        }
        return Collections.unmodifiableList(result);
    }

    public static BasicSmallDigitalAssemblerRecipe convertCrystalAssembler(CrystalAssemblerRecipe assemblerRecipe) {
        var fluid = assemblerRecipe.getFluid();
        return new BasicSmallDigitalAssemblerRecipe(
                assemblerRecipe.getInputs().stream()
                        .map(value -> IngredientCreatorAccess.item().from(value.getIngredient(),
                                value.getAmount()))
                        .toList(),
                (fluid == null || fluid.getIngredient().isEmpty()) ? null
                        : IngredientCreatorAccess.fluid().from(fluid.getIngredient(), fluid.getAmount()),
                null,
                assemblerRecipe.output, 0);
    }

    public static List<RecipeHolder<ItemStackChemicalToItemStackRecipe>> getConvertedCutterRecipes(
            @NotNull RecipeManager recipeManager, @Nullable RegistryAccess registryAccess) {
        List<RecipeHolder<ItemStackChemicalToItemStackRecipe>> result = new ArrayList<>();
        recipeManager.getAllRecipesFor(CircuitCutterRecipe.TYPE).forEach(recipe -> {
            result.add(new RecipeHolder<>(
                    ResourceLocation.fromNamespaceAndPath(recipe.id().getNamespace(),
                            "/runtime_generated/chemical_cut/from_circuit_cutter/" + recipe.id().getPath()),
                    convertFromCircuitCutter(recipe.value())));
        });
        return List.copyOf(result);
    }

    public static BasicChemicalCutRecipe convertFromCircuitCutter(CircuitCutterRecipe recipe) {
        var input = recipe.getInput();
        return new BasicChemicalCutRecipe(
                IngredientCreatorAccess.item().from(input.getIngredient(), input.getAmount()),
                IngredientCreatorAccess.chemicalStack().from(MekanismChemicals.OXYGEN.asStack(1)),
                recipe.output.copy(),
                true);
    }
}
