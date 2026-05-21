package com.takenokoshi.mekut.item.cell.rainbow;

import org.jetbrains.annotations.Nullable;

import appeng.api.storage.cells.ICellHandler;
import appeng.api.storage.cells.ISaveProvider;
import appeng.api.storage.cells.StorageCell;
import net.minecraft.world.item.ItemStack;

public class InfinityRainbowCellHandler implements ICellHandler {

    @Override
    public @Nullable StorageCell getCellInventory(ItemStack stack, @Nullable ISaveProvider arg1) {
        return new InfinityRainbowCellStorage();
    }

    @Override
    public boolean isCell(ItemStack stack) {
        return stack.getItem() instanceof InfinityRainbowCellItem;
    }
    
}
