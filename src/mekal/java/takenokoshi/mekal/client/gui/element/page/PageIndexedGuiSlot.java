package takenokoshi.mekal.client.gui.element.page;

import mekanism.client.gui.IGuiWrapper;
import mekanism.client.gui.element.slot.GuiSlot;
import mekanism.client.gui.element.slot.SlotType;

public class PageIndexedGuiSlot extends GuiSlot implements IPageIndexedGuiElement {

    private final int page;

    public PageIndexedGuiSlot(SlotType type, IGuiWrapper gui, int x, int y, int page) {
        super(type, gui, x, y);
        this.page = page;
        setPage(0);
    }

    @Override
    public void setPage(int page) {
        visible = page == this.page;
    }
}