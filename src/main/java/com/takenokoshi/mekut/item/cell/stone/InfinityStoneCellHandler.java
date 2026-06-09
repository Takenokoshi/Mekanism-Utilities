package com.takenokoshi.mekut.item.cell.stone;

import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekut.registries.MekUtItems;

import appeng.api.storage.cells.ICellHandler;
import appeng.api.storage.cells.ISaveProvider;
import appeng.api.storage.cells.StorageCell;
import net.minecraft.world.item.ItemStack;

public class InfinityStoneCellHandler implements ICellHandler {

    @Override
    public @Nullable StorageCell getCellInventory(ItemStack arg0, @Nullable ISaveProvider arg1) {
        return isCell(arg0) ? new InfinityStoneCellStorage() : null;
    }

    @Override
    public boolean isCell(ItemStack arg0) {
        return MekUtItems.ME_INFINITY_STONE_CELL.is(arg0.getItem());
    }

}
