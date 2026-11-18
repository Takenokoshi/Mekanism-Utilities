package takenokoshi.mekal.client.gui.element.page;

import mekanism.client.gui.IGuiWrapper;
import mekanism.client.gui.element.progress.GuiProgress;
import mekanism.client.gui.element.progress.IProgressInfoHandler;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.progress.IProgressInfoHandler.IBooleanProgressInfoHandler;

public class PageIndexedGuiProgress extends GuiProgress implements IPageIndexedGuiElement {

    private final int page;

    public PageIndexedGuiProgress(IProgressInfoHandler handler, ProgressType type, IGuiWrapper gui, int x, int y,
            int page) {
        super(handler, type, gui, x, y);
        this.page = page;
        setPage(0);
    }

    public PageIndexedGuiProgress(IBooleanProgressInfoHandler handler, ProgressType type, IGuiWrapper gui, int x, int y,
            int page) {
        super(handler, type, gui, x, y);
        this.page = page;
        setPage(0);
    }

    @Override
    public void setPage(int page) {
        visible = this.page == page;
    }

}