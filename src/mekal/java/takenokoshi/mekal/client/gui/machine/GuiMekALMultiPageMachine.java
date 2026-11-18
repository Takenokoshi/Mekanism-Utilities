package takenokoshi.mekal.client.gui.machine;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import mekanism.client.gui.GuiConfigurableTile;
import mekanism.client.gui.element.GuiElement;
import mekanism.client.gui.element.button.MekanismButton;
import mekanism.client.render.IFancyFontRenderer;
import mekanism.common.network.PacketUtils;
import mekanism.common.tile.base.TileEntityMekanism;
import mekanism.common.tile.interfaces.ISideConfiguration;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import takenokoshi.mekal.client.gui.element.page.GuiMekALPageViewer;
import takenokoshi.mekal.client.gui.element.page.IPageIndexedGuiElement;
import takenokoshi.mekal.common.inventory.container.MekALMultiPageContainer;
import takenokoshi.mekal.common.network.to_server.PacketGuiMultiPageSync;
import takenokoshi.mekal.common.tile.interfaces.IHasGuiSizeOffset;
import takenokoshi.mekal.common.tile.interfaces.IMultiPageMachine;

public class GuiMekALMultiPageMachine<BE extends TileEntityMekanism & IHasGuiSizeOffset & IMultiPageMachine & ISideConfiguration>
        extends GuiConfigurableTile<BE, MekALMultiPageContainer<BE>> {

    protected int page;
    protected final int totalPages;
    protected final List<IPageIndexedGuiElement> pagedElements;

    protected GuiMekALMultiPageMachine(MekALMultiPageContainer<BE> container, Inventory inv, Component title) {
        super(container, inv, title);
        dynamicSlots = true;
        imageWidth += tile.getExtraWidth();
        imageHeight += tile.getExtraHeight();
        inventoryLabelX += tile.getExtraWidth() / 2;
        inventoryLabelY += tile.getExtraHeight();
        this.totalPages = tile.getTotalPages();
        this.pagedElements = new ArrayList<>();
    }

    protected void changePage(int i) {
        page = Math.floorMod(page + i, totalPages);
        menu.setPage(page);
        PacketUtils.sendToServer(new PacketGuiMultiPageSync(page));
        pagedElements.forEach(p -> p.setPage(page));
    }

    protected boolean nextPage(GuiElement element, double mouseX, double mouseY) {
        changePage(1);
        return true;
    }

    protected boolean previousPage(GuiElement element, double mouseX, double mouseY) {
        changePage(-1);
        return true;
    }

    @Override
    protected <T extends GuiElement> T addRenderableWidget(T element) {
        if (element instanceof IPageIndexedGuiElement paged) {
            pagedElements.add(paged);
        }
        return super.addRenderableWidget(element);
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addRenderableWidget(new GuiMekALPageViewer(this));
        addRenderableWidget(new MekanismButton(this, 12, -22, 18, 18,
                Component.literal("<"), this::previousPage));
        addRenderableWidget(new MekanismButton(this, 138, -22, 18, 18,
                Component.literal(">"), this::nextPage));
    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.drawForegroundText(guiGraphics, mouseX, mouseY);
        drawScrollingString(guiGraphics, Component.literal("Page:" + (page + 1) + "/" + totalPages), 34, -18,
                IFancyFontRenderer.TextAlignment.CENTER, titleTextColor(), 100, 0, false);
    }

}
