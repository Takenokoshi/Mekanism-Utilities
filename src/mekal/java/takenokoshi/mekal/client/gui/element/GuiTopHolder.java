package takenokoshi.mekal.client.gui.element;

import org.jetbrains.annotations.NotNull;

import mekanism.client.gui.GuiUtils;
import mekanism.client.gui.IGuiWrapper;
import mekanism.client.gui.element.GuiTexturedElement;
import mekanism.client.render.MekanismRenderer;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.MekanismUtils.ResourceType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

/**
 * Some fields & methods were copied from
 * https://github.com/mekanism/Mekanism/blob/1.21.x/src/main/java/mekanism/client/gui/element/GuiSideHolder.java
 */
public abstract class GuiTopHolder extends GuiTexturedElement {

    private static final ResourceLocation HOLDER_TOP = MekanismUtils.getResource(ResourceType.GUI, "holder_top.png");
    private static final int TEXTURE_WIDTH = 9;
    private static final int TEXTURE_HEIGHT = 26;

    private final boolean slotHolder;

    protected GuiTopHolder(IGuiWrapper gui, int x, int y, int width, boolean slotHolder) {
        super(HOLDER_TOP, gui, x, y, width, TEXTURE_HEIGHT);
        this.slotHolder = slotHolder;
        active = false;
        if (!this.slotHolder) {
            setButtonBackground(ButtonBackground.DEFAULT);
        }
    }

    protected abstract void colorTab(GuiGraphics guiGraphics);

    @Override
    public void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.renderWidget(guiGraphics, mouseX, mouseY, partialTicks);
        if (this.slotHolder) {
            draw(guiGraphics);
        }
    }

    @Override
    public void drawBackground(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.drawBackground(guiGraphics, mouseX, mouseY, partialTicks);
        if (!this.slotHolder) {
            draw(guiGraphics);
        }
    }

    protected void draw(@NotNull GuiGraphics guiGraphics) {
        colorTab(guiGraphics);
        drawUncolored(guiGraphics);
        MekanismRenderer.resetColor(guiGraphics);
    }

    protected void drawUncolored(@NotNull GuiGraphics guiGraphics) {
        GuiUtils.blitNineSlicedSized(guiGraphics, getResource(), relativeX, relativeY, width, height, 4, TEXTURE_WIDTH,
                TEXTURE_HEIGHT, 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT);
    }

}
