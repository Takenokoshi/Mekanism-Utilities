package takenokoshi.mekal.api.recipes;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import takenokoshi.mekal.api.recipes.vanilla_input.SingleItemFluidRecipeInput;

public abstract class ItemStackFluidToItemStackRecipe extends ItemStackFluidToObjectRecipe<ItemStack> {

    @NotNull
    @Override
    public abstract ItemStack getResultItem(@NotNull HolderLookup.Provider provider);

    @NotNull
    @Override
    public ItemStack assemble(SingleItemFluidRecipeInput input, HolderLookup.Provider provider) {
        if (!isIncomplete() && test(input.item(), input.fluid())) {
            return getOutput(input.item(), input.fluid());
        }
        return ItemStack.EMPTY;
    }
}
