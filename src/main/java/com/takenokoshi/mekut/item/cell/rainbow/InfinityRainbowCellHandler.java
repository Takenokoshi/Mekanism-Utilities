package com.takenokoshi.mekut.item.cell.rainbow;

import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekut.registries.MekUtItems;

import appeng.api.storage.cells.ICellHandler;
import appeng.api.storage.cells.ISaveProvider;
import appeng.api.storage.cells.StorageCell;
import net.minecraft.world.item.ItemStack;

public class InfinityRainbowCellHandler implements ICellHandler {

    @Override
    public @Nullable StorageCell getCellInventory(ItemStack stack, @Nullable ISaveProvider arg1) {
        return isCell(stack) ? new InfinityRainbowCellStorage() : null;
    }

    @Override
    public boolean isCell(ItemStack stack) {
        return MekUtItems.ME_INFINITY_RAINBOW_CELL.is(stack.getItem());
    }

}
