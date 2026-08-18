package com.takenokoshi.mekut.recipe.type;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekaddonlib.recipe.type.MekALRecipeType;
import com.takenokoshi.mekut.recipe.inputcache.MekUtDoubleInputRecipeCache;
import mekanism.api.recipes.ItemStackChemicalToItemStackRecipe;
import mekanism.api.recipes.vanilla_input.SingleItemChemicalRecipeInput;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.fml.ModList;

public class ChemicalCutRecipeType extends
        MekALRecipeType<SingleItemChemicalRecipeInput, ItemStackChemicalToItemStackRecipe, MekUtDoubleInputRecipeCache.MekUtItemChemical<ItemStackChemicalToItemStackRecipe>> {

    public ChemicalCutRecipeType(ResourceLocation name) {
        super(name, MekUtDoubleInputRecipeCache.MekUtItemChemical::toItem);
    }

    @Override
    protected @NotNull List<RecipeHolder<ItemStackChemicalToItemStackRecipe>> getRecipesUncached(
            @NotNull RecipeManager recipeManager, @Nullable RegistryAccess registryAccess) {
        if (ModList.get().isLoaded("extendedae")) {
            var result = new ArrayList<>(super.getRecipesUncached(recipeManager, registryAccess));
            result.addAll(EAERecipeModule.getConvertedCutterRecipes(recipeManager, registryAccess));
        }
        return super.getRecipesUncached(recipeManager, registryAccess);
    }

}
