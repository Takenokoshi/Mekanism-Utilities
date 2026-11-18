package takenokoshi.mekut.common.recipe;

import java.util.function.Function;

import mekanism.api.recipes.ItemStackToItemStackRecipe;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.common.recipe.MekanismRecipeType;
import mekanism.common.recipe.lookup.cache.IInputRecipeCache;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.SingleItem;
import mekanism.common.registration.impl.RecipeTypeDeferredRegister;
import mekanism.common.registration.impl.RecipeTypeRegistryObject;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.neoforged.neoforge.registries.DeferredHolder;
import takenokoshi.mekut.api.recipes.MekUtRecipeTypes;
import takenokoshi.mekut.common.MekUt;

public class MekUtRecipeType {

    public static final RecipeTypeDeferredRegister RECIPE_TYPES = new RecipeTypeDeferredRegister(MekUt.MODID);

    public static final RecipeTypeRegistryObject<SingleRecipeInput, ItemStackToItemStackRecipe, SingleItem<ItemStackToItemStackRecipe>> MINERAL_EXTRACTING = register(
            MekUtRecipeTypes.NAME_MINERAL_EXTRACTING,
            recipeType -> new SingleItem<>(recipeType, ItemStackToItemStackRecipe::getInput));

    private static <VANILLA_INPUT extends RecipeInput, RECIPE extends MekanismRecipe<VANILLA_INPUT>, INPUT_CACHE extends IInputRecipeCache> RecipeTypeRegistryObject<VANILLA_INPUT, RECIPE, INPUT_CACHE> register(
            ResourceLocation name,
            Function<MekanismRecipeType<VANILLA_INPUT, RECIPE, INPUT_CACHE>, INPUT_CACHE> inputCacheCreator) {
        if (!MekUt.MODID.equals(name.getNamespace())) {
            throw new IllegalStateException("Name must be in " + MekUt.MODID + " namespace");
        }
        return RECIPE_TYPES.registerMek(name.getPath(),
                registryName -> new MekanismRecipeType<>(registryName, inputCacheCreator));
    }

    @SuppressWarnings("unused")
    public static void clearCache() {
        for (Holder<RecipeType<?>> entry : RECIPE_TYPES.getEntries()) {
            if (entry.value() instanceof MekanismRecipeType<?, ?, ?> recipeType) {
                // recipeType.clearCaches();
            }
        }
    }

    public static boolean checkIncompleteRecipes(MinecraftServer server) {
        return checkIncompleteRecipes(server.getRecipeManager(), server.registryAccess());
    }

    @SuppressWarnings("unused")
    public static boolean checkIncompleteRecipes(RecipeManager recipeManager, RegistryAccess registryAccess) {
        boolean foundIncompleteRecipes = false;
        for (DeferredHolder<RecipeType<?>, ? extends RecipeType<?>> holder : RECIPE_TYPES.getEntries()) {
            MekanismRecipeType<?, ?, ?> recipeType = (MekanismRecipeType<?, ?, ?>) holder.value();
            // foundIncompleteRecipes |= recipeType.checkMyIncompleteRecipes(recipeManager,
            // registryAccess);
        }
        return foundIncompleteRecipes;
    }
}
