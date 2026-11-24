package takenokoshi.mekal.common.inventory.container.slot;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.common.inventory.container.slot.ContainerSlotType;
import mekanism.common.inventory.container.slot.InventoryContainerSlot;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.inventory.slot.BasicInventorySlot;
import mekanism.common.inventory.warning.ISupportsWarning;
import net.minecraft.world.item.ItemStack;

public class PagingInventoryContainerSlot extends InventoryContainerSlot {

    protected int page;

    protected final Int2ObjectMap<BasicInventorySlot> pageToSlotMap;

    public PagingInventoryContainerSlot(BasicInventorySlot firstPageSlot, int slotPage, int x, int y,
            ContainerSlotType slotType,
            @Nullable SlotOverlay slotOverlay, 
            @Nullable Consumer<ISupportsWarning<?>> warningAdder,
            Consumer<ItemStack> uncheckedSetter) {
        super(firstPageSlot, x, y, slotType, slotOverlay, warningAdder, uncheckedSetter);
        this.pageToSlotMap = new Int2ObjectOpenHashMap<>();
        this.pageToSlotMap.put(slotPage, firstPageSlot);
        page = 0;
    }

    @Override
    public void setChanged() {
        container.setChanged();
    }

    public void setSlotOnPage(int page, @NotNull BasicInventorySlot slot) {
        pageToSlotMap.put(page, slot);
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public boolean isActive() {
        return super.isActive() && pageToSlotMap.containsKey(page);
    }

    protected BasicInventorySlot getCurrentSlot() {
        return pageToSlotMap.get(page);
    }

    @Override
    public @NotNull ItemStack insertItem(@NotNull ItemStack stack, Action action) {
        if (!isActive()) {
            return stack;
        }
        BasicInventorySlot currentSlot = getCurrentSlot();
        ItemStack remainder = currentSlot.insertItem(stack, action, AutomationType.MANUAL);
        if (action.execute() && stack.getCount() != remainder.getCount()) {
            this.setChanged();
            currentSlot.onContentsChanged();
        }
        return remainder;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        if (stack.isEmpty() || !isActive()) {
            return false;
        }
        BasicInventorySlot currentSlot = getCurrentSlot();
        if (currentSlot.isEmpty()) {
            return this.insertItem(stack, Action.SIMULATE).getCount() < stack.getCount();
        } else {
            return currentSlot.extractItem(1, Action.SIMULATE, AutomationType.MANUAL).isEmpty()
                    ? false
                    : currentSlot.isItemValidForInsertion(stack, AutomationType.MANUAL);
        }
    }

    @Override
    public @NotNull ItemStack getItem() {
        return isActive() ? getCurrentSlot().getStack() : ItemStack.EMPTY;
    }

    @Override
    public boolean hasItem() {
        return isActive() ? !getCurrentSlot().isEmpty() : false;
    }

    // 以下、作成途中

    @Override
    public void set(@NotNull ItemStack stack) {
        super.set(stack);
    }

}
