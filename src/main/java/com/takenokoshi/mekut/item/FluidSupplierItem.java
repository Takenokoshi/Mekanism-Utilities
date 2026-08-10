package com.takenokoshi.mekut.item;

import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;

public class FluidSupplierItem extends Item implements IFluidSupplierItem {

    private final Supplier<Fluid> supplier;

    public FluidSupplierItem(Properties properties, Supplier<Fluid> supplier) {
        super(properties);
        this.supplier = supplier;
    }

    public static Function<Item.Properties, FluidSupplierItem> getCreator(Supplier<Fluid> supplier) {
        return props -> new FluidSupplierItem(props, supplier);
    }

    public final FluidStack getSupplyingFluidStack(ItemStack stack) {
        return new FluidStack(supplier.get(), 0x7fffffff);
    }

}
