package com.takenokoshi.mekut.item.cell.bulk;

import java.math.BigInteger;
import java.util.function.Function;

import com.takenokoshi.mekut.registries.MekUtDataComponents;

import appeng.api.config.Actionable;
import appeng.api.networking.security.IActionSource;
import appeng.api.stacks.AEKey;
import appeng.api.stacks.KeyCounter;
import appeng.api.storage.cells.CellState;
import appeng.api.storage.cells.ISaveProvider;
import appeng.api.storage.cells.StorageCell;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class MUBulkCellInventory<AEKEY extends AEKey> implements StorageCell {

    private static final BigInteger STACK_LIMIT = BigInteger.ONE.shiftLeft(42);
    private static final BigInteger LONG_MAX = BigInteger.valueOf(Long.MAX_VALUE);

    private final ISaveProvider container;
    private final ItemStack stack;

    private AEKEY storedKey;
    private final AEKEY filterKey;

    private BigInteger unitCount;

    private boolean isPersisted = true;

    private final Function<AEKey, AEKEY> castFunction;

    public MUBulkCellInventory(ItemStack stack, ISaveProvider container, Function<AEKey, AEKEY> castFunction) {
        this.container = container;
        this.stack = stack;

        @SuppressWarnings("unchecked")
        MUBulkCellItem<AEKEY> cell = (MUBulkCellItem<AEKEY>) stack.getItem();
        this.filterKey = castFunction.apply(cell.getConfigInventory(stack).getKey(0));
        AEKey stored = stack.get(MekUtDataComponents.BULK_CELL_KEY);
        this.storedKey = stored == null
                ? null
                : castFunction.apply(stored);
        this.unitCount = stack.getOrDefault(MekUtDataComponents.BULK_CELL_UNIT_COUNT, BigInteger.ZERO);
        this.castFunction = castFunction;

    }

    private boolean isFilterMismatched() {
        if (storedKey == null) {
            return false;
        }
        if (storedKey.equals(filterKey)) {
            return false;
        }
        return true;
    }

    @Override
    public CellState getStatus() {
        if (storedKey == null || unitCount.signum() < 1) {
            return CellState.EMPTY;
        }
        if (isFilterMismatched()) {
            return CellState.FULL;
        }
        return CellState.NOT_EMPTY;
    }

    AEKEY getStoredKey() {
        return storedKey;
    }

    AEKEY getFilterKey() {
        return filterKey;
    }

    boolean isAlot() {
        return unitCount.compareTo(LONG_MAX) > 0;
    }

    long getStoredQuantity() {
        return isAlot() ? Long.MAX_VALUE : unitCount.longValue();
    }

    @Override
    public Component getDescription() {
        return stack.getHoverName();
    }

    @Override
    public double getIdleDrain() {
        return 5.0f;
    }

    @Override
    public long insert(AEKey what, long amount, Actionable mode, IActionSource source) {
        if (amount < 1) {
            return amount;
        }
        AEKEY whatKey = castFunction.apply(what);
        if (whatKey == null) {
            return 0;
        }
        if (isFilterMismatched()) {
            //filter should be partitioned by CellWorkbench only.
            return 0;
        }
        if (!whatKey.equals(filterKey)) {
            return 0;
        }
        if (mode == Actionable.MODULATE) {
            if (storedKey == null) {
                //always whatKey == filterKey.
                storedKey = filterKey;
            }
            unitCount = unitCount.add(BigInteger.valueOf(amount));
            saveChanges();
        }
        return amount;
    }

    @Override
    public long extract(AEKey what, long amount, Actionable mode, IActionSource source) {
        if (storedKey == null || unitCount.signum() < 1) {
            return 0;
        }
        AEKEY whatKey = castFunction.apply(what);
        if (whatKey == null || !whatKey.equals(storedKey)) {
            return 0;
        }
        if (mode == Actionable.MODULATE) {
            BigInteger val = BigInteger.valueOf(amount);
            if (unitCount.compareTo(val) < 1) {
                storedKey = null;
                long result = unitCount.longValue();
                unitCount = BigInteger.ZERO;
                saveChanges();
                return result;
            } else {
                unitCount = unitCount.subtract(val);
                saveChanges();
                return amount;
            }
        }
        return unitCount.min(BigInteger.valueOf(amount)).longValue();
    }

    @Override
    public void getAvailableStacks(KeyCounter out) {
        if (storedKey != null && unitCount.signum() > 0) {
            out.add(storedKey, unitCount.min(STACK_LIMIT).longValue());
        }
    }

    private void saveChanges() {
        isPersisted = false;

        if (container != null) {
            container.saveChanges();
        } else {
            persist();
        }
    }

    @Override
    public void persist() {
        if (isPersisted) {
            return;
        }
        if (storedKey == null || unitCount.signum() < 1) {
            stack.remove(MekUtDataComponents.BULK_CELL_KEY);
            stack.remove(MekUtDataComponents.BULK_CELL_UNIT_COUNT);
        } else {
            stack.set(MekUtDataComponents.BULK_CELL_KEY, storedKey);
            stack.set(MekUtDataComponents.BULK_CELL_UNIT_COUNT, unitCount);
        }
        isPersisted = true;
    }

    @Override
    public boolean isPreferredStorageFor(AEKey what, IActionSource source) {
        AEKEY whatKey = castFunction.apply(what);
        if (whatKey == null) {
            return false;
        }
        return whatKey.equals(storedKey) || whatKey.equals(filterKey);
    }

    @Override
    public boolean canFitInsideCell() {
        return filterKey == null && storedKey == null && unitCount.signum() < 1;
    }
}
