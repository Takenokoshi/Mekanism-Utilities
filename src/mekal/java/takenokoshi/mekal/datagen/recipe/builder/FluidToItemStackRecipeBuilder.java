package takenokoshi.mekal.datagen.recipe.builder;

import java.util.function.BiFunction;

import mekanism.api.datagen.recipe.MekanismRecipeBuilder;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import takenokoshi.mekal.api.recipes.FluidToItemStackRecipe;

public class FluidToItemStackRecipeBuilder extends MekanismRecipeBuilder<FluidToItemStackRecipeBuilder> {

    protected final BiFunction<FluidStackIngredient, ItemStack, FluidToItemStackRecipe> factory;
    protected final FluidStackIngredient input;
    protected final ItemStack output;

    protected FluidToItemStackRecipeBuilder(BiFunction<FluidStackIngredient, ItemStack, FluidToItemStackRecipe> factory,
            FluidStackIngredient input, ItemStack output) {
        this.factory = factory;
        this.input = input;
        this.output = output;
    }

    @Override
    protected Recipe<?> asRecipe() {
        return factory.apply(input, output);
    }

}
