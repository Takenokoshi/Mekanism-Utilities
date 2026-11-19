package takenokoshi.mekut.api.recipes.basic;

import mekanism.api.recipes.basic.BasicItemStackToFluidRecipe;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.fluids.FluidStack;
import takenokoshi.mekut.api.recipes.MekUtRecipeSerializers;
import takenokoshi.mekut.api.recipes.MekUtRecipeTypes;

public class BasicSqueezingRecipe extends BasicItemStackToFluidRecipe {

    public BasicSqueezingRecipe(ItemStackIngredient input, FluidStack output) {
        super(input, output);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MekUtRecipeSerializers.SQUEEZING.value();
    }

    @Override
    public RecipeType<?> getType() {
        return MekUtRecipeTypes.TYPE_SQUEEZING.value();
    }

    public FluidStack getOutputRaw(){
        return output;
    }

}
