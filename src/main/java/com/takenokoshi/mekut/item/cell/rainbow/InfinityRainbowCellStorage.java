package com.takenokoshi.mekut.item.cell.rainbow;

import java.util.List;

import com.takenokoshi.mekut.lang.MekUtDescription;
import com.takenokoshi.mekut.registries.MekUtItems;

import appeng.api.config.Actionable;
import appeng.api.networking.security.IActionSource;
import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKey;
import appeng.api.stacks.KeyCounter;
import appeng.api.storage.cells.CellState;
import appeng.api.storage.cells.StorageCell;
import me.ramidzkh.mekae2.ae2.MekanismKey;
import mekanism.common.registries.MekanismChemicals;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;

public class InfinityRainbowCellStorage implements StorageCell {

    public static final long COUNT = 1l << 42;
    public static final List<AEItemKey> DYES = List.of(
            AEItemKey.of(Items.BLACK_DYE),
            AEItemKey.of(Items.BLUE_DYE),
            AEItemKey.of(Items.BROWN_DYE),
            AEItemKey.of(Items.CYAN_DYE),
            AEItemKey.of(Items.GRAY_DYE),
            AEItemKey.of(Items.GREEN_DYE),
            AEItemKey.of(Items.LIGHT_BLUE_DYE),
            AEItemKey.of(Items.LIGHT_GRAY_DYE),
            AEItemKey.of(Items.LIME_DYE),
            AEItemKey.of(Items.MAGENTA_DYE),
            AEItemKey.of(Items.ORANGE_DYE),
            AEItemKey.of(Items.PINK_DYE),
            AEItemKey.of(Items.PURPLE_DYE),
            AEItemKey.of(Items.RED_DYE),
            AEItemKey.of(Items.WHITE_DYE),
            AEItemKey.of(Items.YELLOW_DYE),
            AEItemKey.of(MekUtItems.DARK_RED_DYE),
            AEItemKey.of(MekUtItems.AQUA_DYE));

    public static final List<MekanismKey> PIGMENTS = MekanismChemicals.PIGMENT_COLOR_LOOKUP
            .values().stream()
            .map(def -> MekanismKey.of(def.asStack(Long.MAX_VALUE))).toList();

    @Override
    public Component getDescription() {
        return MekUtDescription.INFINITY_RAINBOW_CELL.translate();
    }

    @Override
    public double getIdleDrain() {
        return 20d;
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
        if (what instanceof AEItemKey itemKey) {
            for (AEItemKey aeItemKey : DYES) {
                if (aeItemKey.getItem().equals(itemKey.getItem())) {
                    return amount;
                }
            }
        } else if (what instanceof MekanismKey mKey) {
            for (MekanismKey mekanismKey : PIGMENTS) {
                if (mekanismKey.getStack().getChemical().equals(mKey.getStack().getChemical())) {
                    return amount;
                }
            }
        }
        return 0;
    }

    @Override
    public long extract(AEKey what, long amount, Actionable mode, IActionSource source) {
        if (what instanceof AEItemKey itemKey) {
            for (AEItemKey aeItemKey : DYES) {
                if (aeItemKey.getItem().equals(itemKey.getItem())) {
                    return amount;
                }
            }
        } else if (what instanceof MekanismKey mKey) {
            for (MekanismKey mekanismKey : PIGMENTS) {
                if (mekanismKey.getStack().getChemical().equals(mKey.getStack().getChemical())) {
                    return amount;
                }
            }
        }
        return 0;
    }

    @Override
    public void getAvailableStacks(KeyCounter out) {
        DYES.forEach(k -> out.add(k, COUNT));
        PIGMENTS.forEach(k -> out.add(k, COUNT));
    }

}
