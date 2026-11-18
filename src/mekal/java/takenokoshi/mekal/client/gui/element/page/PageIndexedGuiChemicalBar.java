package takenokoshi.mekal.client.gui.element.page;

import mekanism.api.chemical.ChemicalStack;
import mekanism.client.gui.IGuiWrapper;
import mekanism.client.gui.element.bar.GuiChemicalBar;

public class PageIndexedGuiChemicalBar extends GuiChemicalBar implements IPageIndexedGuiElement {

    private final int page;

    public PageIndexedGuiChemicalBar(IGuiWrapper gui, TankInfoProvider<ChemicalStack> infoProvider, int x, int y,
            int width, int height, boolean horizontal, int page) {
        super(gui, infoProvider, x, y, width, height, horizontal);
        this.page = page;
        setPage(0);
    }

    @Override
    public void setPage(int page) {
        visible = page == this.page;
    }

}