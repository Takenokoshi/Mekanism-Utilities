package takenokoshi.mekal.api.recipes;

import java.util.function.BiPredicate;

import mekanism.api.recipes.MekanismRecipe;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import takenokoshi.mekal.api.recipes.vanilla_input.ItemFluidRecipeInput;

public abstract class ItemStackFluidToItemStackRecipe extends MekanismRecipe<ItemFluidRecipeInput>
        implements BiPredicate<ItemStack, FluidStack> {

}
