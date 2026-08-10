package com.takenokoshi.mekut.item;

import net.minecraft.world.item.ItemStack;

public interface IItemSupplierItem {
    ItemStack getSupplyingStack(ItemStack stack);
}
