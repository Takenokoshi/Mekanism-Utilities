package com.takenokoshi.mekut.mixin.mwgr;

import org.spongepowered.asm.mixin.Mixin;
import com.github.misosouptgit.mwgr.compat.InfiniteFluidHandlerItem;
import com.takenokoshi.mekut.item.IFluidSupplierItem;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;

@Mixin(value = InfiniteFluidHandlerItem.class, remap = false)
public class InfiniteFluidHandlerItemMixin implements IFluidSupplierItem {

    @Override
    public FluidStack getSupplyingFluidStack(ItemStack stack) {
        return new FluidStack(((InfiniteFluidHandlerItem) (Object) this).getFluid(), 0x7fffffff);
    }
}
