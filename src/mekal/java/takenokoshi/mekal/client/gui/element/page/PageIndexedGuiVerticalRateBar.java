package takenokoshi.mekal.client.gui.element.page;

import mekanism.client.gui.IGuiWrapper;
import mekanism.client.gui.element.bar.GuiVerticalRateBar;

public class PageIndexedGuiVerticalRateBar extends GuiVerticalRateBar implements IPageIndexedGuiElement {

    private final int page;

    public PageIndexedGuiVerticalRateBar(IGuiWrapper gui, IBarInfoHandler handler, int x, int y, int page) {
        super(gui, handler, x, y);
        this.page = page;
        setPage(0);
    }

    @Override
    public void setPage(int page) {
        visible = this.page == page;
    }
}