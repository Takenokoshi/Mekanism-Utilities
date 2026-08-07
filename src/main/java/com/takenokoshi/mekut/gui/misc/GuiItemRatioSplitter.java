package com.takenokoshi.mekut.gui.misc;

import org.jetbrains.annotations.NotNull;

import com.takenokoshi.mekut.blockentity.misc.BEItemRatioSplitter;
import com.takenokoshi.mekut.network.to_server.PacketGuiRatioSplitter;

import mekanism.client.gui.GuiConfigurableTile;
import mekanism.client.gui.element.progress.GuiProgress;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.text.GuiTextField;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.network.PacketUtils;
import mekanism.common.util.text.InputValidator;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GuiItemRatioSplitter
        extends GuiConfigurableTile<BEItemRatioSplitter, MekanismTileContainer<BEItemRatioSplitter>> {

    private GuiTextField ratio1Field;
    private GuiTextField ratio2Field;

    public GuiItemRatioSplitter(MekanismTileContainer<BEItemRatioSplitter> container, Inventory inv,
            Component title) {
        super(container, inv, title);
        dynamicSlots = true;
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
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

        addRenderableWidget(new GuiProgress(tile::getActive, ProgressType.BI, this, 80, 40));
    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        renderTitleText(guiGraphics);
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
