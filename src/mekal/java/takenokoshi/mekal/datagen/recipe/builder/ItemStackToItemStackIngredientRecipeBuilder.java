package takenokoshi.mekal.datagen.recipe.builder;

import java.util.function.BiFunction;

import mekanism.api.datagen.recipe.MekanismRecipeBuilder;
import mekanism.api.recipes.ItemStackToItemStackRecipe;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.world.item.crafting.Recipe;
import takenokoshi.mekal.api.recipes.basic.BasicMekALCrushingRecipe;
import takenokoshi.mekal.api.recipes.basic.BasicMekALEnrichingRecipe;

public class ItemStackToItemStackIngredientRecipeBuilder
        extends MekanismRecipeBuilder<ItemStackToItemStackIngredientRecipeBuilder> {

    protected final BiFunction<ItemStackIngredient, ItemStackIngredient, ItemStackToItemStackRecipe> factory;
    protected final ItemStackIngredient input, output;

    protected ItemStackToItemStackIngredientRecipeBuilder(
            BiFunction<ItemStackIngredient, ItemStackIngredient, ItemStackToItemStackRecipe> factory,
            ItemStackIngredient input, ItemStackIngredient output) {
        this.factory = factory;
        this.input = input;
        this.output = output;
    }

    public static ItemStackToItemStackIngredientRecipeBuilder crushing(ItemStackIngredient input,
            ItemStackIngredient output) {
        return new ItemStackToItemStackIngredientRecipeBuilder(BasicMekALCrushingRecipe::new, input, output);
    }

    public static ItemStackToItemStackIngredientRecipeBuilder enriching(ItemStackIngredient input,
            ItemStackIngredient output) {
        return new ItemStackToItemStackIngredientRecipeBuilder(BasicMekALEnrichingRecipe::new, input, output);
    }

    @Override
    protected Recipe<?> asRecipe() {
        return factory.apply(input, output);
    }

}
