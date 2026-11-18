package takenokoshi.mekal.client.gui.element.page;

import mekanism.client.gui.IGuiWrapper;
import mekanism.client.gui.element.bar.GuiFluidBar;
import net.neoforged.neoforge.fluids.FluidStack;

public class PageIndexedGuiFluidBar extends GuiFluidBar implements IPageIndexedGuiElement {

    private final int page;

    public PageIndexedGuiFluidBar(IGuiWrapper gui, TankInfoProvider<FluidStack> infoProvider, int x, int y, int width,
            int height, boolean horizontal, int page) {
        super(gui, infoProvider, x, y, width, height, horizontal);
        this.page = page;
        setPage(0);
    }

    @Override
    public void setPage(int page) {
        visible = page == this.page;
    }
}