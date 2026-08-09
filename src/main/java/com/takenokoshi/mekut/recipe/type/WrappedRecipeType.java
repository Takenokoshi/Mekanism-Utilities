package com.takenokoshi.mekut.recipe.type;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekaddonlib.recipe.type.MekALRecipeType;
import com.takenokoshi.mekut.recipe.inputcache.MUSingleInputRecipeCache;
import mekanism.common.recipe.lookup.cache.IInputRecipeCache;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;

// For already registered recipetype such as smelting(vanilla) or charging(AE2).
public class WrappedRecipeType<VANILLA_INPUT extends RecipeInput, RECIPE extends Recipe<VANILLA_INPUT>, INPUT_CACHE extends IInputRecipeCache>
        extends MekALRecipeType<VANILLA_INPUT, RECIPE, INPUT_CACHE> {

    protected final RecipeType<RECIPE> wrappedType;

    public WrappedRecipeType(ResourceLocation name,
            Function<MekALRecipeType<VANILLA_INPUT, RECIPE, INPUT_CACHE>, INPUT_CACHE> inputCacheCreator,
            RecipeType<RECIPE> wrappedType) {
        super(name, inputCacheCreator);
        this.wrappedType = wrappedType;
    }

    @Override
    protected @NotNull List<RecipeHolder<RECIPE>> getRecipesUncached(@NotNull RecipeManager recipeManager,
            @Nullable RegistryAccess registryAccess) {
        return recipeManager.getAllRecipesFor(wrappedType);
    }

    public static final WrappedRecipeType<SingleRecipeInput, SmeltingRecipe, MUSingleInputRecipeCache.MUSingleItem<SmeltingRecipe>> VANILLA_SMELTING = new WrappedRecipeType<>(
            ResourceLocation.withDefaultNamespace("smelting"),
            type -> new MUSingleInputRecipeCache.MUSingleItem<>(type, recipe -> recipe
                    .getIngredients()
                    .stream()
                    .flatMap(ingredient -> Arrays.stream(ingredient.getItems()))
                    .map(ItemStack::getItem)
                    .toList()),
            RecipeType.SMELTING);
}
