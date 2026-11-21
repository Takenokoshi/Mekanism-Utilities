package takenokoshi.mekut.common.recipe.serializer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import mekanism.api.SerializationConstants;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.recipe.serializer.MekanismRecipeSerializer;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import takenokoshi.mekut.api.MekUtSerializationConstants;
import takenokoshi.mekut.api.recipes.basic.BasicSpallationRecipe;

public class MekUtRecipeSerializer {
    public static MekanismRecipeSerializer<BasicSpallationRecipe> spallation() {
        return new MekanismRecipeSerializer<>(
                RecordCodecBuilder.mapCodec(instance -> instance.group(
                        IngredientCreatorAccess.chemicalStack().codec().fieldOf(SerializationConstants.INPUT)
                                .forGetter(BasicSpallationRecipe::getInput),
                        ChemicalStack.CODEC.listOf(1, 9).fieldOf(SerializationConstants.OUTPUT)
                                .forGetter(BasicSpallationRecipe::getOutputRaw),
                        Codec.DOUBLE.fieldOf(MekUtSerializationConstants.PROTON_REQUIRED)
                                .forGetter(BasicSpallationRecipe::getProtonRequired),
                        Codec.DOUBLE.fieldOf(MekUtSerializationConstants.NEUTRON_PRODUCTION)
                                .forGetter(BasicSpallationRecipe::getNeutronProduction))
                        .apply(instance, BasicSpallationRecipe::new)),
                StreamCodec.composite(
                        IngredientCreatorAccess.chemicalStack().streamCodec(),
                        BasicSpallationRecipe::getInput,
                        ChemicalStack.STREAM_CODEC.apply(ByteBufCodecs.list()),
                        BasicSpallationRecipe::getOutputRaw,
                        ByteBufCodecs.DOUBLE,
                        BasicSpallationRecipe::getProtonRequired,
                        ByteBufCodecs.DOUBLE,
                        BasicSpallationRecipe::getNeutronProduction,
                        BasicSpallationRecipe::new));
    }
}
