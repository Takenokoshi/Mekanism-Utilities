package com.takenokoshi.mekut.recipe.type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekaddonlib.recipe.type.MekALRecipeType;
import com.takenokoshi.mekut.recipe.inputcache.MekUtTripleInputRecipeCache;
import com.takenokoshi.mekut.recipe.recipe.basic.BasicGreenHouseRecipe;
import com.takenokoshi.mekut.recipe.recipe.prefab.GreenHouseCropRecipe;
import com.takenokoshi.mekut.recipe.recipe.prefab.GreenHouseFertilizerRecipe;
import com.takenokoshi.mekut.recipe.recipe.prefab.GreenHouseRecipe;
import com.takenokoshi.mekut.registries.MekUtRecipeTypes;

import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeManager;

public class GreenHouseRecipeType extends
        MekALRecipeType<RecipeInput, GreenHouseRecipe, MekUtTripleInputRecipeCache.ItemItemFluid<GreenHouseRecipe>> {

    public GreenHouseRecipeType(ResourceLocation name) {
        super(name, MekUtTripleInputRecipeCache.ItemItemFluid::greenHouse);
    }

    @Override
    protected @NotNull List<RecipeHolder<GreenHouseRecipe>> getRecipesUncached(@NotNull RecipeManager recipeManager,
            @Nullable RegistryAccess registryAccess) {
        List<RecipeHolder<GreenHouseRecipe>> result = new ArrayList<>(
                super.getRecipesUncached(recipeManager, registryAccess));
        List<RecipeHolder<GreenHouseCropRecipe>> crops = recipeManager
                .getAllRecipesFor(MekUtRecipeTypes.GREEN_HOUSE_CROP.getRecipeType());
        List<RecipeHolder<GreenHouseFertilizerRecipe>> fertilizers = recipeManager
                .getAllRecipesFor(MekUtRecipeTypes.GREEN_HOUSE_FERTILIZER.getRecipeType());
        for (RecipeHolder<GreenHouseCropRecipe> cropHolder : crops) {
            GreenHouseCropRecipe crop = cropHolder.value();
            for (RecipeHolder<GreenHouseFertilizerRecipe> fertilizerHolder : fertilizers) {
                GreenHouseFertilizerRecipe fertilizer = fertilizerHolder.value();
                result.add(new RecipeHolder<>(
                        ResourceLocation.fromNamespaceAndPath(
                                cropHolder.id().getNamespace() + "." + fertilizerHolder.id().getNamespace(),
                                "/runtime_generated/"
                                        + cropHolder.id().getPath() + "."
                                        + fertilizerHolder.id().getPath()),
                        new BasicGreenHouseRecipe(
                                crop.cropIngredient,
                                crop.soilIngredient,
                                fertilizer.fertilizerIngredient,
                                fertilizer.multiplyOutputs(crop.outputs),
                                fertilizer.multiplyDuration(crop.duration))));
            }
        }
        return Collections.unmodifiableList(result);
    }

}
