package com.takenokoshi.mekut.item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;

public interface IFluidSupplierItem {
    FluidStack getSupplyingFluidStack(ItemStack stack);
}