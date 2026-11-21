package takenokoshi.mekal.common.recipe.lookup;

import java.util.function.BiPredicate;

import mekanism.api.recipes.MekanismRecipe;
import mekanism.common.recipe.lookup.IDoubleRecipeLookupHandler;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import takenokoshi.mekal.common.recipe.lookup.cache.MekALInputRecipeCache;

public interface ItemFluidRecipeLookupHandler<RECIPE extends MekanismRecipe<?> & BiPredicate<ItemStack, FluidStack>>
        extends IDoubleRecipeLookupHandler<ItemStack, FluidStack, RECIPE, MekALInputRecipeCache.ItemFluid<RECIPE>> {

}
