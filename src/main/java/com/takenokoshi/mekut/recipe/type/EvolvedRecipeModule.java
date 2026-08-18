package com.takenokoshi.mekut.recipe.type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekut.recipe.recipe.basic.BasicSmallDigitalReactionChamberRecipe;
import com.takenokoshi.mekut.recipe.recipe.prefab.ItemStackListFluidChemicalToItemFluidChemicalRecipe;

import fr.iglee42.evolvedmekanism.recipes.AlloyerRecipe;
import fr.iglee42.evolvedmekanism.registries.EMRecipeType;
import mekanism.api.chemical.ChemicalStack;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.neoforge.fluids.FluidStack;

public class EvolvedRecipeModule {
    public static List<RecipeHolder<ItemStackListFluidChemicalToItemFluidChemicalRecipe>> getConvertedRCRecipes(
            @NotNull RecipeManager recipeManager, @Nullable RegistryAccess registryAccess) {
        List<RecipeHolder<ItemStackListFluidChemicalToItemFluidChemicalRecipe>> result = new ArrayList<>();
        recipeManager.getAllRecipesFor(EMRecipeType.ALLOYING.get()).forEach(holder -> {
            result.add(new RecipeHolder<>(
                    ResourceLocation.fromNamespaceAndPath(holder.id().getNamespace(),
                            "/runtime_generated/small_digital_reaction_chamber/"
                                    + holder.id().getPath()),
                    convertAlloying(holder.value())));
        });
        return Collections.unmodifiableList(result);
    }

    public static ItemStackListFluidChemicalToItemFluidChemicalRecipe convertAlloying(AlloyerRecipe recipe) {
        return new BasicSmallDigitalReactionChamberRecipe(
                List.of(recipe.getMainInput(), recipe.getExtraInput(), recipe.getTertiaryExtraInput()),
                Optional.empty(), Optional.empty(),
                recipe.getOutputRaw(), FluidStack.EMPTY, ChemicalStack.EMPTY, 0L, 100);
    }
}
