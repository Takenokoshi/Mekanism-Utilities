package takenokoshi.mekal.api.recipes.vanilla_input;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;

public record SingleItemFluidRecipeInput(ItemStack item, FluidStack fluid) implements ItemFluidRecipeInput {

    @Override
    public ItemStack getItem(int index) {
        if (index != 0) {
            throw new IllegalArgumentException("No item for index " + index);
        }
        return item;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public FluidStack getFluid(int index) {
        if (index != 0) {
            throw new IllegalArgumentException("No fluid for index " + index);
        }
        return fluid;
    }

    @Override
    public boolean isEmpty() {
        return item.isEmpty() || fluid.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SingleItemFluidRecipeInput other = (SingleItemFluidRecipeInput) o;
        return fluid.equals(other.fluid) && ItemStack.matches(item, other.item);
    }

    @Override
    public int hashCode() {
        int hash = fluid.hashCode();
        hash = 31 * hash + ItemStack.hashItemAndComponents(item);
        hash = 31 + hash + item.getCount();
        return hash;
    }

}
