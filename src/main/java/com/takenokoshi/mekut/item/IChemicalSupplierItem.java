package com.takenokoshi.mekut.item;

import mekanism.api.chemical.ChemicalStack;
import net.minecraft.world.item.ItemStack;

public interface IChemicalSupplierItem {
    ChemicalStack getSupplyingChemicalStack(ItemStack stack);
}
