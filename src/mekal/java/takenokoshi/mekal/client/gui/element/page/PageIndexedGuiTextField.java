package takenokoshi.mekal.client.gui.element.page;

import mekanism.client.gui.IGuiWrapper;
import mekanism.client.gui.element.text.GuiTextField;
import net.minecraft.client.gui.components.events.ContainerEventHandler;

public class PageIndexedGuiTextField extends GuiTextField implements IPageIndexedGuiElement {

    private final int page;

    public PageIndexedGuiTextField(IGuiWrapper gui, ContainerEventHandler parent, int x, int y, int width, int height,
            int page) {
        super(gui, parent, x, y, width, height);
        this.page = page;
        setPage(0);
    }

    public PageIndexedGuiTextField(IGuiWrapper gui, int x, int y, int width, int height,
            int page) {
        super(gui, x, y, width, height);
        this.page = page;
        setPage(0);
    }

    @Override
    public void setPage(int page) {
        visible = this.page == page;
    }

}