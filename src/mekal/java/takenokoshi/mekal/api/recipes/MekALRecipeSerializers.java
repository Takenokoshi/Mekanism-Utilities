package takenokoshi.mekal.api.recipes;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import takenokoshi.mekal.api.recipes.basic.BasicMekALCrushingRecipe;
import takenokoshi.mekal.api.recipes.basic.BasicMekALEnrichingRecipe;

public class MekALRecipeSerializers {

    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<BasicMekALCrushingRecipe>> MEKAL_CRUSHING;
    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<BasicMekALEnrichingRecipe>> MEKAL_ENRICHING;
}
