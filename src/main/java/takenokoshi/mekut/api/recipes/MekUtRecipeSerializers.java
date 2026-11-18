package takenokoshi.mekut.api.recipes;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import takenokoshi.mekut.api.recipes.basic.BasicMineralExtractingRecipe;

public class MekUtRecipeSerializers {
    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<BasicMineralExtractingRecipe>> MINERAL_EXTRACTING;
}
