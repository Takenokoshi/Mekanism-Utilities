package com.takenokoshi.mekut.gui.misc;

import org.jetbrains.annotations.NotNull;

import com.takenokoshi.mekut.blockentity.misc.BEFluidRatioSplitter;
import com.takenokoshi.mekut.network.to_server.PacketGuiRatioSplitter;

import mekanism.client.gui.GuiConfigurableTile;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiFluidGauge;
import mekanism.client.gui.element.gauge.GuiGauge;
import mekanism.client.gui.element.progress.GuiProgress;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.text.GuiTextField;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.network.PacketUtils;
import mekanism.common.util.text.InputValidator;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GuiFluidRatioSplitter
        extends GuiConfigurableTile<BEFluidRatioSplitter, MekanismTileContainer<BEFluidRatioSplitter>> {
    private GuiGauge<?> centerGauge;
    private GuiTextField ratio1Field;
    private GuiTextField ratio2Field;

    public GuiFluidRatioSplitter(MekanismTileContainer<BEFluidRatioSplitter> container, Inventory inv,
            Component title) {
        super(container, inv, title);
        inventoryLabelY += 2;
        titleLabelY = 5;
        dynamicSlots = true;
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addRenderableWidget(new GuiProgress(tile::getActive, ProgressType.SMALL_LEFT, this, 47, 29));
        addRenderableWidget(new GuiProgress(tile::getActive, ProgressType.SMALL_RIGHT, this, 101, 29));

        addRenderableWidget(centerGauge = new GuiFluidGauge(tile::getInputTank, () -> tile.getFluidTanks(null),
                GaugeType.SMALL_MED, this, 79, 4));

        addRenderableWidget(new GuiFluidGauge(tile::getOutputTank1, () -> tile.getFluidTanks(null),
                GaugeType.SMALL_MED, this, 25, 13));
        addRenderableWidget(new GuiFluidGauge(tile::getOutputTank2, () -> tile.getFluidTanks(null),
                GaugeType.SMALL_MED, this, 133, 13));

        addRenderableWidget(ratio1Field = new GuiTextField(this, 8,
                inventoryLabelY - font.lineHeight - 4,
                imageWidth / 4, font.lineHeight + 2))
                .setEnterHandler(this::sendPacket1)
                .setInputValidator(InputValidator.DIGIT)
                .addCheckmarkButton(this::sendPacket1)
                .setMaxLength(10);
        addRenderableWidget(ratio2Field = new GuiTextField(this, imageWidth - 8 - imageWidth / 4,
                inventoryLabelY - font.lineHeight - 4,
                imageWidth / 4, font.lineHeight + 2))
                .setEnterHandler(this::sendPacket2)
                .setInputValidator(InputValidator.DIGIT)
                .addCheckmarkButton(this::sendPacket2)
                .setMaxLength(10);
    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        renderTitleTextWithOffset(guiGraphics, 1, centerGauge.getRelativeX(), 4, TextAlignment.LEFT);
        renderInventoryText(guiGraphics);
        drawScrollingString(guiGraphics, Component.literal(tile.getRatio1() + ":" + tile.getRatio2()),
                0, inventoryLabelY - font.lineHeight - 4, TextAlignment.CENTER,
                titleTextColor(), 20, false);
        super.drawForegroundText(guiGraphics, mouseX, mouseY);
    }

    private void sendPacket1() {
        if (ratio1Field.getText().isEmpty()) {
            return;
        }
        try {
            int v = Integer.parseInt(ratio1Field.getText());
            PacketUtils.sendToServer(new PacketGuiRatioSplitter(tile.getBlockPos(), v, false));
        } catch (Exception e) {
        }
    }

    private void sendPacket2() {
        if (ratio2Field.getText().isEmpty()) {
            return;
        }
        try {
            int v = Integer.parseInt(ratio2Field.getText());
            PacketUtils.sendToServer(new PacketGuiRatioSplitter(tile.getBlockPos(), v, true));
        } catch (Exception e) {
        }
    }

}
