package takenokoshi.mekal.common.inventory.slot;

import java.util.function.Predicate;

import org.jetbrains.annotations.NotNull;
import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.IContentsListener;
import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.api.functions.ConstantPredicates;
import mekanism.common.inventory.slot.FluidInventorySlot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import takenokoshi.mekal.common.util.MekALFluidUtils;

public class AdvancedFluidInputSlot extends FluidInventorySlot {

    public static AdvancedFluidInputSlot create(IExtendedFluidTank fluidTank, @NotNull IContentsListener listener, int x,
            int y) {
        return new AdvancedFluidInputSlot(fluidTank, ConstantPredicates.alwaysFalse(), getInputPredicate(fluidTank),
                listener, x, y);
    }

    protected AdvancedFluidInputSlot(IExtendedFluidTank fluidTank, Predicate<@NotNull ItemStack> canExtract,
            Predicate<@NotNull ItemStack> canInsert, @NotNull IContentsListener listener, int x, int y) {
        super(fluidTank, canExtract, canInsert, listener, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if (super.isItemValid(stack)) {
            return true;
        }
        if (stack.isEmpty()) {
            return false;
        }
        FluidStack suppliedByItem = MekALFluidUtils.getSuppliedFluid(stack.getItem());
        if (suppliedByItem.isEmpty()) {
            return false;
        }
        return fluidTank.insert(suppliedByItem.copyWithAmount(1), Action.SIMULATE, AutomationType.INTERNAL).isEmpty();
    }
}