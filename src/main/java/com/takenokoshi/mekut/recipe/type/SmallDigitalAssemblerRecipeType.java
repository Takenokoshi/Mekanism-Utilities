package com.takenokoshi.mekut.recipe.type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.glodblock.github.extendedae.recipe.CrystalAssemblerRecipe;
import com.takenokoshi.mekut.recipe.inputcache.ItemStackListFluidChemicalInputRecipeCache;
import com.takenokoshi.mekut.recipe.recipe.basic.BasicSmallDigitalAssemblerRecipe;
import com.takenokoshi.mekut.recipe.recipe.prefab.ItemStackListFluidChemicalToItemRecipe;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeManager;

public class SmallDigitalAssemblerRecipeType extends
        MekUtRecipeType<RecipeInput, ItemStackListFluidChemicalToItemRecipe, ItemStackListFluidChemicalInputRecipeCache<ItemStackListFluidChemicalToItemRecipe>> {

    public SmallDigitalAssemblerRecipeType(ResourceLocation name) {
        super(name, ItemStackListFluidChemicalInputRecipeCache::new);
    }

    @Override
    protected @NotNull List<RecipeHolder<ItemStackListFluidChemicalToItemRecipe>> getRecipesUncached(
            @NotNull RecipeManager recipeManager, @Nullable RegistryAccess registryAccess) {
        List<RecipeHolder<ItemStackListFluidChemicalToItemRecipe>> result = new ArrayList<>(
                super.getRecipesUncached(recipeManager, registryAccess));
        List<RecipeHolder<CrystalAssemblerRecipe>> crystalAssemblerRecipes = recipeManager
                .getAllRecipesFor(CrystalAssemblerRecipe.TYPE);
        for (RecipeHolder<CrystalAssemblerRecipe> holder : crystalAssemblerRecipes) {
            CrystalAssemblerRecipe assemblerRecipe = holder.value();
            result.add(new RecipeHolder<>(
                    ResourceLocation.fromNamespaceAndPath(holder.id().getNamespace(),
                            "small_digital_assembler/runtime_generated/eae_crystal_assembler/" + holder.id().getPath()),
                    BasicSmallDigitalAssemblerRecipe.convertCrystalAssembler(assemblerRecipe)));
        }
        return Collections.unmodifiableList(result);
    }

}
