package takenokoshi.mekut.datagen.recipe;

import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
import takenokoshi.mekut.common.MekUt;
import takenokoshi.mekut.datagen.recipe.builder.MekUtItemStackToItemStackRecipeBuilder;

public class MineralExtractingRecipeProvider {
    static void buildRecipes(RecipeOutput recipeOutput) {
        MekUtItemStackToItemStackRecipeBuilder
                .mineralExtracting(
                        IngredientCreatorAccess.item().from(Tags.Items.GRAVELS, 1),
                        new ItemStack(Items.FLINT, 2))
                .build(recipeOutput, MekUt.rl("mineral_extracting/flint"));
    }
}
