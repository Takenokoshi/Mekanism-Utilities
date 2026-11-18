package takenokoshi.mekal.client.gui.element.page;

import mekanism.api.energy.IEnergyContainer;
import mekanism.client.gui.IGuiWrapper;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiEnergyGauge;

public class PageIndexedGuiEnergyGauge extends GuiEnergyGauge implements IPageIndexedGuiElement {

    private final int page;

    public PageIndexedGuiEnergyGauge(IEnergyContainer container, GaugeType type, IGuiWrapper gui, int x, int y,
            int page) {
        super(container, type, gui, x, y);
        this.page = page;
        setPage(0);
    }

    @Override
    public void setPage(int page) {
        visible = this.page == page;
    }

}