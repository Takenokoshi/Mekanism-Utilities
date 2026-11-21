package takenokoshi.mekal.common.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;
import takenokoshi.mekal.api.recipes.MekALRecipeSerializers;
import takenokoshi.mekal.api.recipes.basic.*;
import takenokoshi.mekal.common.MekAL;
import takenokoshi.mekal.common.recipe.serializer.MekALRecipeSerializer;

public class MekALRecipeSerializersInternal {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(
            Registries.RECIPE_SERIALIZER, MekAL.MODID);

    static {
        MekALRecipeSerializers.MEKAL_COMPRESSING = RECIPE_SERIALIZERS.register(
                "mekal_compressing",
                () -> MekALRecipeSerializer.itemChemicalToItemIngredient(BasicMekALCompressingRecipe::new));
        MekALRecipeSerializers.MEKAL_CRUSHING = RECIPE_SERIALIZERS.register(
                "mekal_crushing",
                () -> MekALRecipeSerializer.itemToItemIngredient(BasicMekALCrushingRecipe::new));
        MekALRecipeSerializers.MEKAL_ENRICHING = RECIPE_SERIALIZERS.register(
                "mekal_enriching",
                () -> MekALRecipeSerializer.itemToItemIngredient(BasicMekALEnrichingRecipe::new));
        MekALRecipeSerializers.MEKAL_INJECTING = RECIPE_SERIALIZERS.register(
                "mekal_injecting",
                () -> MekALRecipeSerializer.itemChemicalToItemIngredient(BasicMekALInjectingRecipe::new));
        MekALRecipeSerializers.MEKAL_METALLURGIC_INFUSING = RECIPE_SERIALIZERS.register(
                "mekal_metallurgic_infusing",
                () -> MekALRecipeSerializer.itemChemicalToItemIngredient(BasicMekALMetallurgicInfuserRecipe::new));
        MekALRecipeSerializers.MEKAL_PAINTING = RECIPE_SERIALIZERS.register(
                "mekal_painting",
                () -> MekALRecipeSerializer.itemChemicalToItemIngredient(BasicMekALPaintingRecipe::new));
        MekALRecipeSerializers.MEKAL_PURIFYING = RECIPE_SERIALIZERS.register(
                "mekal_purifying",
                () -> MekALRecipeSerializer.itemChemicalToItemIngredient(BasicMekALPurifyingRecipe::new));
    }

}
