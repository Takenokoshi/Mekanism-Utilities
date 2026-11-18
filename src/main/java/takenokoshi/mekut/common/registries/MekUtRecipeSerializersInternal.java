package takenokoshi.mekut.common.registries;

import mekanism.common.recipe.serializer.MekanismRecipeSerializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;
import takenokoshi.mekut.api.recipes.MekUtRecipeSerializers;
import takenokoshi.mekut.api.recipes.basic.BasicMineralExtractingRecipe;
import takenokoshi.mekut.common.MekUt;

public class MekUtRecipeSerializersInternal {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister
            .create(Registries.RECIPE_SERIALIZER, MekUt.MODID);

    static {
        MekUtRecipeSerializers.MINERAL_EXTRACTING = RECIPE_SERIALIZERS.register(
                "mineral_extracting",
                () -> MekanismRecipeSerializer.itemToItem(BasicMineralExtractingRecipe::new));
    }

}
