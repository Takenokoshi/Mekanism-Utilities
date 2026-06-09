package com.takenokoshi.mekut.mixin.mekanism.inventory.slot;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.SoftOverride;

import mekanism.common.inventory.slot.OutputInventorySlot;

@Mixin(value = { OutputInventorySlot.class }, remap = false)
public class OutputInventorySlotMixin extends BasicInventorySlotMixin {

    @Override
    @SoftOverride
    protected int mekanism_utilities$modifyGetLimit(int original) {
        return 0x3fffffff;
    }
}
