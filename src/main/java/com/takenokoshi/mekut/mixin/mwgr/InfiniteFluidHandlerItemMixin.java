package com.takenokoshi.mekut.mixin.mwgr;

import org.spongepowered.asm.mixin.Mixin;
import com.github.misosouptgit.mwgr.compat.InfiniteFluidHandlerItem;
import com.takenokoshi.mekut.item.IFluidSupplier;

import net.neoforged.neoforge.fluids.FluidStack;

@Mixin(value = InfiniteFluidHandlerItem.class, remap = false)
public class InfiniteFluidHandlerItemMixin implements IFluidSupplier {

    @Override
    public FluidStack getSupplyingFluidStack() {
        return new FluidStack(((InfiniteFluidHandlerItem) (Object) this).getFluid(), 0x7fffffff);
    }
}
