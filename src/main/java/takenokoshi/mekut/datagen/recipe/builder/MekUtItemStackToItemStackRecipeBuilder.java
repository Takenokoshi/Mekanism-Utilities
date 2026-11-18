package takenokoshi.mekut.datagen.recipe.builder;

import mekanism.api.datagen.recipe.builder.ItemStackToItemStackRecipeBuilder;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.world.item.ItemStack;
import takenokoshi.mekut.api.recipes.basic.BasicMineralExtractingRecipe;

public class MekUtItemStackToItemStackRecipeBuilder extends ItemStackToItemStackRecipeBuilder {

    protected MekUtItemStackToItemStackRecipeBuilder(ItemStackIngredient input, ItemStack output, Factory factory) {
        super(input, output, factory);
    }

    public static MekUtItemStackToItemStackRecipeBuilder mineralExtracting(ItemStackIngredient input, ItemStack output){
        return new MekUtItemStackToItemStackRecipeBuilder(input, output, BasicMineralExtractingRecipe::new);
    }
    
}
