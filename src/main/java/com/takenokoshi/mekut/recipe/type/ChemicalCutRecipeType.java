package com.takenokoshi.mekut.recipe.type;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.glodblock.github.extendedae.recipe.CircuitCutterRecipe;
import com.takenokoshi.mekut.recipe.inputcache.MekUtDoubleInputRecipeCache;
import com.takenokoshi.mekut.recipe.recipe.basic.BasicChemicalCutRecipe;

import mekanism.api.recipes.ItemStackChemicalToItemStackRecipe;
import mekanism.api.recipes.vanilla_input.SingleItemChemicalRecipeInput;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

public class ChemicalCutRecipeType extends
        MekUtRecipeType<SingleItemChemicalRecipeInput, ItemStackChemicalToItemStackRecipe, MekUtDoubleInputRecipeCache.MekUtItemChemical<ItemStackChemicalToItemStackRecipe>> {

    public ChemicalCutRecipeType(ResourceLocation name) {
        super(name, MekUtDoubleInputRecipeCache.MekUtItemChemical::toItem);
    }

    @Override
    protected @NotNull List<RecipeHolder<ItemStackChemicalToItemStackRecipe>> getRecipesUncached(
            @NotNull RecipeManager recipeManager, @Nullable RegistryAccess registryAccess) {
        var result = new ArrayList<>(super.getRecipesUncached(recipeManager, registryAccess));
        recipeManager.getAllRecipesFor(CircuitCutterRecipe.TYPE).forEach(recipe -> {
            result.add(new RecipeHolder<>(
                    ResourceLocation.fromNamespaceAndPath(recipe.id().getNamespace(),
                            "chemical_cut/runtime_generated/" + recipe.id().getPath()),
                    BasicChemicalCutRecipe.convertFromCircuitCutter(recipe.value())));
        });
        return List.copyOf(result);
    }

}
