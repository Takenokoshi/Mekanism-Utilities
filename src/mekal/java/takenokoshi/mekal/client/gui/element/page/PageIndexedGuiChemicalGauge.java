package takenokoshi.mekal.client.gui.element.page;

import java.util.List;
import java.util.function.Supplier;

import mekanism.api.chemical.IChemicalTank;
import mekanism.client.gui.IGuiWrapper;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiChemicalGauge;

public class PageIndexedGuiChemicalGauge extends GuiChemicalGauge implements IPageIndexedGuiElement {

    private final int page;

    public PageIndexedGuiChemicalGauge(Supplier<IChemicalTank> tankSupplier,
            Supplier<List<IChemicalTank>> tanksSupplier,
            GaugeType type, IGuiWrapper gui, int x, int y, int sizeX, int sizeY, int page) {
        super(tankSupplier, tanksSupplier, type, gui, x, y, sizeX, sizeY);
        this.page = page;
        setPage(0);
    }

    public PageIndexedGuiChemicalGauge(Supplier<IChemicalTank> tankSupplier,
            Supplier<List<IChemicalTank>> tanksSupplier,
            GaugeType type, IGuiWrapper gui, int x, int y, int page) {
        this(tankSupplier, tanksSupplier, type, gui, x, y, type.getGaugeOverlay().getWidth() + 2,
                type.getGaugeOverlay().getHeight() + 2, page);
    }

    @Override
    public void setPage(int page) {
        visible = this.page == page;
    }

}