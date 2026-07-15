package com.takenokoshi.mekut.inventory.slot;

import java.util.function.Consumer;
import java.util.function.Predicate;

import org.jetbrains.annotations.NotNull;

import com.takenokoshi.mekaddonlib.inventory.slot.LimitChangedInputInventorySlot;
import com.takenokoshi.mekut.item.ItemSupplierItem;

import mekanism.api.IContentsListener;
import net.minecraft.world.item.ItemStack;

public class InputOrSupplyingSlot extends LimitChangedInputInventorySlot {

    private static Predicate<ItemStack> getModifiedPredicate(Predicate<ItemStack> predicate) {
        return (stack) -> {
            if (stack.isEmpty()) {
                return false;
            } else if (stack.getItem() instanceof ItemSupplierItem supplierItem) {
                return predicate.test(supplierItem.getSupplyingStack());
            } else {
                return predicate.test(stack);
            }
        };
    }

    public static InputOrSupplyingSlot at(Predicate<@NotNull ItemStack> insertPredicate,
            Predicate<@NotNull ItemStack> isItemValid, @NotNull IContentsListener recipeCacheListener, int x, int y,
            int limit) {
        return new InputOrSupplyingSlot(insertPredicate, isItemValid, recipeCacheListener, x, y, limit);
    }

    private Consumer<ItemStack> supplyingStackSetter = (stack) -> {
    };

    protected InputOrSupplyingSlot(Predicate<@NotNull ItemStack> insertPredicate,
            Predicate<@NotNull ItemStack> isItemValid, @NotNull IContentsListener recipeCacheListener, int x, int y,
            int limit) {
        super(getModifiedPredicate(insertPredicate), getModifiedPredicate(isItemValid), recipeCacheListener, x, y,
                limit);
    }

    @Override
    public void onContentsChanged() {
        super.onContentsChanged();
        updateSupplyingStack();
    }

    private void updateSupplyingStack() {
        if (isEmpty()) {
            supplyingStackSetter.accept(ItemStack.EMPTY);
        } else if (getStack().getItem() instanceof ItemSupplierItem supplierItem) {
            supplyingStackSetter.accept(supplierItem.getSupplyingStack());
        } else {
            supplyingStackSetter.accept(ItemStack.EMPTY);
        }

    }

    public void setSupplyingStackSetter(Consumer<ItemStack> value) {
        supplyingStackSetter = value;
    }

}
