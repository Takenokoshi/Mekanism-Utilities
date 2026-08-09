package com.takenokoshi.mekut.recipe.type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekaddonlib.recipe.type.MekALRecipeType;
import com.takenokoshi.mekut.recipe.inputcache.ItemStackListFluidChemicalInputRecipeCache;
import com.takenokoshi.mekut.recipe.recipe.prefab.ItemStackListFluidChemicalToItemRecipe;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.fml.ModList;

public class SmallDigitalAssemblerRecipeType extends
        MekALRecipeType<RecipeInput, ItemStackListFluidChemicalToItemRecipe, ItemStackListFluidChemicalInputRecipeCache<ItemStackListFluidChemicalToItemRecipe>> {

    public SmallDigitalAssemblerRecipeType(ResourceLocation name) {
        super(name, ItemStackListFluidChemicalInputRecipeCache::new);
    }

    @Override
    protected @NotNull List<RecipeHolder<ItemStackListFluidChemicalToItemRecipe>> getRecipesUncached(
            @NotNull RecipeManager recipeManager, @Nullable RegistryAccess registryAccess) {
        if (ModList.get().isLoaded("extendedae")) {
            List<RecipeHolder<ItemStackListFluidChemicalToItemRecipe>> result = new ArrayList<>(
                    super.getRecipesUncached(recipeManager, registryAccess));
            result.addAll(EAERecipeProvider.getConvertedAssemblerRecipes(recipeManager, registryAccess));
            return Collections.unmodifiableList(result);
        }
        return super.getRecipesUncached(recipeManager, registryAccess);
    }

}
