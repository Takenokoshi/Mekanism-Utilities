package com.takenokoshi.mekut.item;

import java.util.function.Function;

import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ChemicalSupplierItem extends Item implements IChemicalSupplierItem {

    private final Holder<Chemical> holder;

    public ChemicalSupplierItem(Properties properties, Holder<Chemical> holder) {
        super(properties);
        this.holder = holder;
    }

    public static Function<Item.Properties, ChemicalSupplierItem> getCreator(Holder<Chemical> holder) {
        return props -> new ChemicalSupplierItem(props, holder);
    }

    public final ChemicalStack getSupplyingChemicalStack(ItemStack stack) {
        return new ChemicalStack(holder, Long.MAX_VALUE);
    }

}
