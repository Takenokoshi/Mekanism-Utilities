package takenokoshi.mekal.common.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;
import takenokoshi.mekal.api.recipes.MekALRecipeSerializers;
import takenokoshi.mekal.api.recipes.basic.BasicMekALCrushingRecipe;
import takenokoshi.mekal.api.recipes.basic.BasicMekALEnrichingRecipe;
import takenokoshi.mekal.common.MekAL;
import takenokoshi.mekal.common.recipe.serializer.MekALRecipeSerializer;

public class MekALRecipeSerializersInternal {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(
            Registries.RECIPE_SERIALIZER, MekAL.MODID);

    static {
        MekALRecipeSerializers.MEKAL_CRUSHING = RECIPE_SERIALIZERS.register(
                "mekal_crushing",
                () -> MekALRecipeSerializer.itemToItemIngredient(BasicMekALCrushingRecipe::new));
        MekALRecipeSerializers.MEKAL_ENRICHING = RECIPE_SERIALIZERS.register(
                "mekal_enriching",
                () -> MekALRecipeSerializer.itemToItemIngredient(BasicMekALEnrichingRecipe::new));
    }

}
