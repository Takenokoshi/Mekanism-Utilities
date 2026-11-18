package takenokoshi.mekal.client.gui.element.page;

import mekanism.client.SpecialColors;
import mekanism.client.gui.IGuiWrapper;
import mekanism.client.render.MekanismRenderer;
import net.minecraft.client.gui.GuiGraphics;
import takenokoshi.mekal.client.gui.element.GuiTopHolder;

public class GuiMekALPageViewer extends GuiTopHolder {

    public GuiMekALPageViewer(IGuiWrapper gui) {
        super(gui, 8, -26, 152, false);
    }

    @Override
    protected void colorTab(GuiGraphics guiGraphics) {
        MekanismRenderer.color(guiGraphics, SpecialColors.TAB_UPGRADE);
    }

}