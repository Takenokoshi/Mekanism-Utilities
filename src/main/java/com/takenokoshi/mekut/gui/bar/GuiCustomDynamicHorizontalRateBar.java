package com.takenokoshi.mekut.gui.bar;

import mekanism.client.gui.IGuiWrapper;
import mekanism.client.gui.element.bar.GuiBar;
import mekanism.client.render.MekanismRenderer;
import mekanism.common.lib.Color;
import mekanism.common.lib.Color.ColorFunction;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.MekanismUtils.ResourceType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

/*
copied from  https://github.com/iglee42/EvolvedMekanism/blob/1.21.1/src/main/java/fr/iglee42/evolvedmekanism/client/bars/GuiCustomDynamicHorizontalRateBar.java
 */
public class GuiCustomDynamicHorizontalRateBar extends GuiBar<GuiBar.IBarInfoHandler> {
    private static final ResourceLocation RATE_BAR;
    private static final int texWidth = 3;
    private static final int texHeight = 8;
    private Color.ColorFunction colorFunction;

    public GuiCustomDynamicHorizontalRateBar(IGuiWrapper gui, GuiBar.IBarInfoHandler handler, int x, int y, int width) {
        this(gui, handler, x, y, width, ColorFunction.HEAT);
    }

    public GuiCustomDynamicHorizontalRateBar(IGuiWrapper gui, GuiBar.IBarInfoHandler handler, int x, int y, int width,
            Color.ColorFunction colorFunction) {
        super(RATE_BAR, gui, handler, x, y, width, texHeight, true);
        this.colorFunction = colorFunction;
    }

    protected void renderBarOverlay(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks,
            double handlerLevel) {
        int displayInt = (int) (handlerLevel * (double) (this.width - 2));
        if (displayInt > 0) {
            for (int i = 0; i < displayInt; ++i) {
                float level = (float) i / (float) (this.width - 2);
                MekanismRenderer.color(guiGraphics, this.colorFunction.getColor(level));
                if (i == 0) {
                    guiGraphics.blit(this.getResource(), this.relativeX + 1, this.relativeY + 1, 0.0F, 0.0F, 1, 8,
                            texWidth, texHeight);
                } else if (i == displayInt - 1) {
                    guiGraphics.blit(this.getResource(), this.relativeX + 1 + i, this.relativeY + 1, 2.0F, 0.0F, 1, 8,
                            texWidth, texHeight);
                } else {
                    guiGraphics.blit(this.getResource(), this.relativeX + 1 + i, this.relativeY + 1, 1.0F, 0.0F, 1, 8,
                            texWidth, texHeight);
                }
            }

            MekanismRenderer.resetColor(guiGraphics);
        }

    }

    public void setColorFunction(Color.ColorFunction colorFunction) {
        this.colorFunction = colorFunction;
    }

    static {
        RATE_BAR = MekanismUtils.getResource(ResourceType.GUI_BAR, "dynamic_rate.png");
    }
}