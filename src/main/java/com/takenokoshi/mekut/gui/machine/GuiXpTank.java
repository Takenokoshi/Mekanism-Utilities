package com.takenokoshi.mekut.gui.machine;

import org.jetbrains.annotations.NotNull;

import com.takenokoshi.mekut.blockentity.normalmachine.BlockEntityXpTank;
import com.takenokoshi.mekut.network.to_server.PacketGuiXpTank;

import mekanism.client.gui.GuiConfigurableTile;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiChemicalGauge;
import mekanism.client.gui.element.text.GuiTextField;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.network.PacketUtils;
import mekanism.common.util.text.InputValidator;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GuiXpTank extends GuiConfigurableTile<BlockEntityXpTank, MekanismTileContainer<BlockEntityXpTank>> {

    private GuiTextField xpExtractField;

    public GuiXpTank(MekanismTileContainer<BlockEntityXpTank> container, Inventory inv, Component title) {
        super(container, inv, title);
        dynamicSlots = true;
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addRenderableWidget(new GuiChemicalGauge(tile::getXpTank, () -> tile.getChemicalTanks(null),
                GaugeType.STANDARD, this, 154, 13));
        xpExtractField = addRenderableWidget(new GuiTextField(this, 30, 46, 54, 12));
        xpExtractField.setEnterHandler(this::setRateLimit);
        xpExtractField.setInputValidator(InputValidator.DIGIT);
        xpExtractField.setMaxLength(10);
        xpExtractField.addCheckmarkButton(this::setRateLimit);
    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        renderTitleText(guiGraphics);
        renderInventoryText(guiGraphics);
        super.drawForegroundText(guiGraphics, mouseX, mouseY);
    }

    private void setRateLimit() {
        if (xpExtractField.getText().isEmpty()) {
            return;
        }
        try {
            int value = Integer.parseInt(xpExtractField.getText());
            PacketUtils.sendToServer(new PacketGuiXpTank(tile.getBlockPos(), value));
        } catch (Exception e) {
        }
    }

}
