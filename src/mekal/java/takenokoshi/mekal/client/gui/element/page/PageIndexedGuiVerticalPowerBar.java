package takenokoshi.mekal.client.gui.element.page;

import mekanism.api.energy.IEnergyContainer;
import mekanism.client.gui.IGuiWrapper;
import mekanism.client.gui.element.bar.GuiVerticalPowerBar;

public class PageIndexedGuiVerticalPowerBar extends GuiVerticalPowerBar implements IPageIndexedGuiElement {

    private final int page;

    public PageIndexedGuiVerticalPowerBar(IGuiWrapper gui, IEnergyContainer container, int x, int y, int desiredHeight,
            int page) {
        super(gui, container, x, y, desiredHeight);
        this.page = page;
        setPage(0);
    }

    public PageIndexedGuiVerticalPowerBar(IGuiWrapper gui, IBarInfoHandler handler, int x, int y, int desiredHeight,
            int page) {
        super(gui, handler, x, y, desiredHeight);
        this.page = page;
        setPage(0);
    }

    public PageIndexedGuiVerticalPowerBar(IGuiWrapper gui, IEnergyContainer container, int x, int y, int page) {
        super(gui, container, x, y);
        this.page = page;
        setPage(0);
    }

    public PageIndexedGuiVerticalPowerBar(IGuiWrapper gui, IBarInfoHandler handler, int x, int y, int page) {
        super(gui, handler, x, y);
        this.page = page;
        setPage(0);
    }

    @Override
    public void setPage(int page) {
        visible = this.page == page;
    }

}