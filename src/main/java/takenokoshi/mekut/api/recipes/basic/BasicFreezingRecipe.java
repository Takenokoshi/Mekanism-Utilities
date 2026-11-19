package takenokoshi.mekut.api.recipes.basic;

import mekanism.api.recipes.ingredients.FluidStackIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import takenokoshi.mekal.api.recipes.basic.BasicFluidToItemStackRecipe;
import takenokoshi.mekut.api.recipes.MekUtRecipeSerializers;
import takenokoshi.mekut.api.recipes.MekUtRecipeTypes;

public class BasicFreezingRecipe extends BasicFluidToItemStackRecipe {

    public BasicFreezingRecipe(FluidStackIngredient input, ItemStack output) {
        super(MekUtRecipeTypes.TYPE_FREEZING.value(), output, input);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MekUtRecipeSerializers.FREEZING.value();
    }

}
