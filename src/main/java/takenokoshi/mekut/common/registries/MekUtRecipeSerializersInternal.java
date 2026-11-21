package takenokoshi.mekut.common.registries;

import mekanism.common.recipe.serializer.MekanismRecipeSerializer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;
import takenokoshi.mekal.common.recipe.serializer.MekALRecipeSerializer;
import takenokoshi.mekut.api.recipes.MekUtRecipeSerializers;
import takenokoshi.mekut.api.recipes.basic.BasicFreezingRecipe;
import takenokoshi.mekut.api.recipes.basic.BasicMineralExtractingRecipe;
import takenokoshi.mekut.api.recipes.basic.BasicSqueezingRecipe;
import takenokoshi.mekut.common.MekUt;
import takenokoshi.mekut.common.recipe.serializer.MekUtRecipeSerializer;

public class MekUtRecipeSerializersInternal {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister
            .create(Registries.RECIPE_SERIALIZER, MekUt.MODID);

    static {
        MekUtRecipeSerializers.FREEZING = RECIPE_SERIALIZERS.register(
                "freezing",
                () -> MekALRecipeSerializer.fluidToItem(BasicFreezingRecipe::new));
        MekUtRecipeSerializers.MINERAL_EXTRACTING = RECIPE_SERIALIZERS.register(
                "mineral_extracting",
                () -> MekanismRecipeSerializer.itemToItem(BasicMineralExtractingRecipe::new));
        MekUtRecipeSerializers.SPALLATION = RECIPE_SERIALIZERS.register(
                "spallation",
                MekUtRecipeSerializer::spallation);
        MekUtRecipeSerializers.SQUEEZING = RECIPE_SERIALIZERS.register(
                "squeezing",
                () -> MekALRecipeSerializer.itemToFluid(BasicSqueezingRecipe::new, BasicSqueezingRecipe::getOutputRaw));
    }

}
