package com.takenokoshi.mekut.mixin.mekanism.inventory.slot;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import mekanism.common.inventory.slot.BasicInventorySlot;

@Mixin(value = { BasicInventorySlot.class }, remap = false)
public class BasicInventorySlotMixin {

    @ModifyReturnValue(at = { @At("RETURN") }, method = { "getLimit" })
    protected int mekanism_utilities$modifyGetLimit(int original) {
        return original;
    }
}
