package com.takenokoshi.mekut.item.cell.stone;

import java.util.List;

import com.takenokoshi.mekut.lang.MekUtDescription;

import appeng.api.config.Actionable;
import appeng.api.networking.security.IActionSource;
import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKey;
import appeng.api.stacks.KeyCounter;
import appeng.api.storage.cells.CellState;
import appeng.api.storage.cells.StorageCell;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;

public class InfinityStoneCellStorage implements StorageCell {
    public static final long COUNT = 1l << 42;
    public static final List<AEItemKey> STONES = List.of(
            AEItemKey.of(Items.COBBLESTONE),
            AEItemKey.of(Items.STONE),
            AEItemKey.of(Items.SMOOTH_STONE),
            AEItemKey.of(Items.STONE_BRICKS),
            AEItemKey.of(Items.DEEPSLATE),
            AEItemKey.of(Items.COBBLED_DEEPSLATE),
            AEItemKey.of(Items.DEEPSLATE_BRICKS),
            AEItemKey.of(Items.DEEPSLATE_TILES),
            AEItemKey.of(Items.ANDESITE),
            AEItemKey.of(Items.DIORITE),
            AEItemKey.of(Items.GRANITE),
            AEItemKey.of(Items.POLISHED_ANDESITE),
            AEItemKey.of(Items.POLISHED_DIORITE),
            AEItemKey.of(Items.POLISHED_GRANITE));

    @Override
    public Component getDescription() {
        return MekUtDescription.INFINITY_STONE_CELL.translate();
    }

    @Override
    public double getIdleDrain() {
        return 10d;
    }

    @Override
    public CellState getStatus() {
        return CellState.FULL;
    }

    @Override
    public void persist() {
    }

    @Override
    public long insert(AEKey what, long amount, Actionable mode, IActionSource source) {
        if (STONES.contains(what)) {
            return amount;
        }
        return 0;
    }

    @Override
    public long extract(AEKey what, long amount, Actionable mode, IActionSource source) {
        if (STONES.contains(what)) {
            return amount;
        }
        return 0;
    }

    @Override
    public void getAvailableStacks(KeyCounter out) {
        STONES.forEach(k -> out.add(k, COUNT));
    }

}
