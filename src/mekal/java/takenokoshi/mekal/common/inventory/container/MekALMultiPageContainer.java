package takenokoshi.mekal.common.inventory.container;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import mekanism.common.registration.impl.ContainerTypeRegistryObject;
import mekanism.common.tile.base.TileEntityMekanism;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import takenokoshi.mekal.common.inventory.container.slot.PageIndexedInventorySlot;
import takenokoshi.mekal.common.tile.interfaces.IHasGuiSizeOffset;
import takenokoshi.mekal.common.tile.interfaces.IMultiPageMachine;

public class MekALMultiPageContainer<BE extends TileEntityMekanism & IHasGuiSizeOffset & IMultiPageMachine>
        extends MekALDynamicSizedContainer<BE> {

    public List<PageIndexedInventorySlot> pageIndexedInventorySlots;

    public MekALMultiPageContainer(ContainerTypeRegistryObject<?> type, int id, Inventory inv, @NotNull BE tile) {
        super(type, id, inv, tile);
    }

    @Override
    protected void addSlotsAndOpen() {
        pageIndexedInventorySlots = new ArrayList<>();
        super.addSlotsAndOpen();
    }

    @Override
    protected @NotNull Slot addSlot(@NotNull Slot slot) {
        if (slot instanceof PageIndexedInventorySlot pageIndexedInventorySlot) {
            pageIndexedInventorySlots.add(pageIndexedInventorySlot);
        }
        return super.addSlot(slot);
    }

    @Override
    protected void addSlots() {
        // TODO Auto-generated method stub
        super.addSlots();
    }

    @Override
    public boolean canTakeItemForPickAll(@NotNull ItemStack stack, @NotNull Slot slot) {
        if (slot instanceof PageIndexedInventorySlot pageIndexedInventorySlot) {
            return pageIndexedInventorySlot.isPageActive()
                    && super.canTakeItemForPickAll(stack, pageIndexedInventorySlot);
        }
        return super.canTakeItemForPickAll(stack, slot);
    }

    public void setPage(int page) {
        pageIndexedInventorySlots.forEach(slot -> slot.setPage(page));
    }

}
