package takenokoshi.mekal.common.inventory.container.slot;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import mekanism.api.Action;
import mekanism.common.inventory.container.slot.ContainerSlotType;
import mekanism.common.inventory.container.slot.InventoryContainerSlot;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.inventory.slot.BasicInventorySlot;
import mekanism.common.inventory.warning.ISupportsWarning;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class PageIndexedInventorySlot extends InventoryContainerSlot {

    public final int page;
    private boolean active;

    public PageIndexedInventorySlot(BasicInventorySlot slot, int x, int y, ContainerSlotType slotType,
            @Nullable SlotOverlay slotOverlay, @Nullable Consumer<ISupportsWarning<?>> warningAdder,
            Consumer<ItemStack> uncheckedSetter, int page) {
        super(slot, x, y, slotType, slotOverlay, warningAdder, uncheckedSetter);
        this.page = page;
        this.active = false;
        setPage(0);
    }

    public void setPage(int page) {
        active = this.page == page;
    }

    @Override
    public boolean isActive() {
        return super.isActive() && active;
    }

    public boolean isPageActive(){
        return active;
    }

    @Override
    public @NotNull ItemStack insertItem(@NotNull ItemStack stack, Action action) {
        return active ? super.insertItem(stack, action) : stack;
    }

    @Override
    public boolean mayPickup(@NotNull Player player) {
        return active && super.mayPickup(player);
    }

}
