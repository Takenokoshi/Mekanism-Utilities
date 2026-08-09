package com.takenokoshi.mekut.recipe.type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekaddonlib.recipe.type.MekALRecipeType;
import com.takenokoshi.mekut.recipe.inputcache.ItemStackListFluidChemicalInputRecipeCache;
import com.takenokoshi.mekut.recipe.recipe.basic.BasicSmallDigitalReactionChamberRecipe;
import com.takenokoshi.mekut.recipe.recipe.prefab.ItemStackListFluidChemicalToItemFluidChemicalRecipe;

import mekanism.common.recipe.MekanismRecipeType;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.fml.ModList;

public class SmallDigitalReactionChamberRecipeType extends
        MekALRecipeType<RecipeInput, ItemStackListFluidChemicalToItemFluidChemicalRecipe, ItemStackListFluidChemicalInputRecipeCache<ItemStackListFluidChemicalToItemFluidChemicalRecipe>> {

    public SmallDigitalReactionChamberRecipeType(ResourceLocation name) {
        super(name, ItemStackListFluidChemicalInputRecipeCache::new);
    }

    @Override
    protected @NotNull List<RecipeHolder<ItemStackListFluidChemicalToItemFluidChemicalRecipe>> getRecipesUncached(
            @NotNull RecipeManager recipeManager, @Nullable RegistryAccess registryAccess) {
        List<RecipeHolder<ItemStackListFluidChemicalToItemFluidChemicalRecipe>> result = new ArrayList<>(
                super.getRecipesUncached(recipeManager, registryAccess));
        recipeManager.getAllRecipesFor(MekanismRecipeType.REACTION.get()).forEach(holder -> {
            result.add(new RecipeHolder<>(
                    ResourceLocation.fromNamespaceAndPath(holder.id().getNamespace(),
                            "/runtime_generated/small_digital_reaction_chamber/from_prc/"
                                    + holder.id().getPath()),
                    BasicSmallDigitalReactionChamberRecipe.convertPRC(holder.value())));
        });
        if (ModList.get().isLoaded("advanced_ae")) {
            result.addAll(AAERecipeProvider.getConvertedRecipes(recipeManager, registryAccess));
        }
        return Collections.unmodifiableList(result);
    }

}
