package com.takenokoshi.mekut.item;

import java.util.function.Function;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class ItemSupplierItem extends Item implements IItemSupplierItem {

    private final ItemLike itemLike;

    public ItemSupplierItem(Properties properties, ItemLike itemLike) {
        super(properties);
        this.itemLike = itemLike;
    }

    public static Function<Item.Properties, ItemSupplierItem> getCreator(ItemLike itemLike) {
        return props -> new ItemSupplierItem(props, itemLike);
    }

    public final ItemStack getSupplyingStack(ItemStack stack) {
        return new ItemStack(itemLike, 0x3fffffff);
    }

}
