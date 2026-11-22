package takenokoshi.mekal.common;

import mekanism.common.recipe.MekanismRecipeType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RecipeTypeReloadListener implements ResourceManagerReloadListener {

    private final DeferredRegister<RecipeType<?>> recipeTypeRegister;

    public RecipeTypeReloadListener(DeferredRegister<RecipeType<?>> recipeTypeRegister) {
        this.recipeTypeRegister = recipeTypeRegister;
    }

    @Override
    public void onResourceManagerReload(ResourceManager arg0) {
        MekanismRecipeType.clearCache(recipeTypeRegister);
    }
}
