package takenokoshi.mekal.common.inventory.slot;

import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import mekanism.api.AutomationType;
import mekanism.api.IContentsListener;
import mekanism.common.inventory.slot.BasicInventorySlot;
import mekanism.common.inventory.warning.ISupportsWarning;
import net.minecraft.world.item.ItemStack;
import takenokoshi.mekal.common.inventory.container.slot.PageIndexedInventorySlot;

public class PageIndexedBasicInventorySlot extends BasicInventorySlot {
    private final int page;
    private final int changedLimit;
    private final int y;
    private @Nullable Consumer<ISupportsWarning<?>> warningAdder;

    public PageIndexedBasicInventorySlot(int limit, BiPredicate<@NotNull ItemStack, @NotNull AutomationType> canExtract,
            BiPredicate<@NotNull ItemStack, @NotNull AutomationType> canInsert, Predicate<@NotNull ItemStack> validator,
            @Nullable IContentsListener listener, int x, int y, int page, int changedLimit) {
        super(limit, canExtract, canInsert, validator, listener, x, y);
        this.page = page;
        this.changedLimit = changedLimit;
        this.y = y;
    }

    public PageIndexedBasicInventorySlot(BiPredicate<@NotNull ItemStack, @NotNull AutomationType> canExtract,
            BiPredicate<@NotNull ItemStack, @NotNull AutomationType> canInsert, Predicate<@NotNull ItemStack> validator,
            @Nullable IContentsListener listener, int x, int y, int page, int changedLimit) {
        super(canExtract, canInsert, validator, listener, x, y);
        this.page = page;
        this.changedLimit = changedLimit;
        this.y = y;
    }

    public PageIndexedBasicInventorySlot(Predicate<@NotNull ItemStack> canExtract,
            Predicate<@NotNull ItemStack> canInsert, Predicate<@NotNull ItemStack> validator,
            @Nullable IContentsListener listener, int x, int y, int page, int changedLimit) {
        super(canExtract, canInsert, validator, listener, x, y);
        this.page = page;
        this.changedLimit = changedLimit;
        this.y = y;
    }

    @Override
    public int getLimit(ItemStack stack) {
        return changedLimit;
    }

    public void tracksWarnings(@Nullable Consumer<ISupportsWarning<?>> warningAdder) {
        this.warningAdder = warningAdder;
    }

    @Override
    public PageIndexedInventorySlot createContainerSlot() {
        return new PageIndexedInventorySlot(this, getGuiX(), y,
                getSlotType(), getSlotOverlay(),
                warningAdder, this::setStackUnchecked, page);
    }

}
