package takenokoshi.mekal.api.recipes.vanilla_input;

import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.fluids.FluidStack;

public interface ItemFluidRecipeInput extends RecipeInput {

    FluidStack getFluid(int index);

    @Override
    default boolean isEmpty() {
        for (int i = 0; i < size(); i++) {
            if (!getItem(i).isEmpty() && !getFluid(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
