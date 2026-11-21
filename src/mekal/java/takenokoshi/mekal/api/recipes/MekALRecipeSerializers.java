package takenokoshi.mekal.api.recipes;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import takenokoshi.mekal.api.recipes.basic.BasicMekALCompressingRecipe;
import takenokoshi.mekal.api.recipes.basic.BasicMekALCrushingRecipe;
import takenokoshi.mekal.api.recipes.basic.BasicMekALEnrichingRecipe;
import takenokoshi.mekal.api.recipes.basic.BasicMekALInjectingRecipe;
import takenokoshi.mekal.api.recipes.basic.BasicMekALMetallurgicInfuserRecipe;
import takenokoshi.mekal.api.recipes.basic.BasicMekALPaintingRecipe;
import takenokoshi.mekal.api.recipes.basic.BasicMekALPurifyingRecipe;

public class MekALRecipeSerializers {

    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<BasicMekALCompressingRecipe>> MEKAL_COMPRESSING;
    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<BasicMekALCrushingRecipe>> MEKAL_CRUSHING;
    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<BasicMekALEnrichingRecipe>> MEKAL_ENRICHING;
    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<BasicMekALInjectingRecipe>> MEKAL_INJECTING;
    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<BasicMekALMetallurgicInfuserRecipe>> MEKAL_METALLURGIC_INFUSING;
    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<BasicMekALPaintingRecipe>> MEKAL_PAINTING;
    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<BasicMekALPurifyingRecipe>> MEKAL_PURIFYING;
}
