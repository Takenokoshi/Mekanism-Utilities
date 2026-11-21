package takenokoshi.mekal.common.inventory.container.slot;

import java.util.function.Predicate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import mekanism.api.IContentsListener;
import mekanism.api.functions.ConstantPredicates;
import mekanism.common.inventory.slot.BasicInventorySlot;
import net.minecraft.world.item.ItemStack;

public class CustomLimitInputInventorySlot extends BasicInventorySlot {

    public static CustomLimitInputInventorySlot at(int limit, @Nullable IContentsListener listener, int x, int y){
        return at(limit, ConstantPredicates.alwaysTrue(), listener, x, y);
    }

    public static CustomLimitInputInventorySlot at(int limit, Predicate<@NotNull ItemStack> isItemValid,
            @Nullable IContentsListener listener, int x, int y) {
        return at(limit, ConstantPredicates.alwaysTrue(), isItemValid, listener, x, y);
    }

    public static CustomLimitInputInventorySlot at(int limit, Predicate<@NotNull ItemStack> insertPredicate,
            Predicate<@NotNull ItemStack> isItemValid, @Nullable IContentsListener listener, int x, int y) {
        return new CustomLimitInputInventorySlot(limit, insertPredicate, isItemValid, listener, x, y);
    }

    protected CustomLimitInputInventorySlot(int limit,
            Predicate<@NotNull ItemStack> insertPredicate, Predicate<@NotNull ItemStack> isItemValid,
            @Nullable IContentsListener listener, int x, int y) {
        super(limit, ConstantPredicates.notExternal(), (stack, automationType) -> insertPredicate.test(stack),
                isItemValid, listener, x, y);
    }

}
