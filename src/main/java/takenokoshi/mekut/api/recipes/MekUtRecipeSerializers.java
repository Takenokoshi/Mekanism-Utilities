package takenokoshi.mekut.api.recipes;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import takenokoshi.mekut.api.recipes.basic.BasicFreezingRecipe;
import takenokoshi.mekut.api.recipes.basic.BasicMineralExtractingRecipe;
import takenokoshi.mekut.api.recipes.basic.BasicSpallationRecipe;
import takenokoshi.mekut.api.recipes.basic.BasicSqueezingRecipe;

public class MekUtRecipeSerializers {
    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<BasicFreezingRecipe>> FREEZING;
    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<BasicMineralExtractingRecipe>> MINERAL_EXTRACTING;
    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<BasicSpallationRecipe>> SPALLATION;
    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<BasicSqueezingRecipe>> SQUEEZING;
}
