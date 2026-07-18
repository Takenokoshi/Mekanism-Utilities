package com.takenokoshi.mekut.gui.machine;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.takenokoshi.mekut.blockentity.abs.BEAbstractCompactIndustrialTurbine;

import mekanism.client.gui.GuiConfigurableTile;
import mekanism.client.gui.element.GuiDownArrow;
import mekanism.client.gui.element.bar.GuiHorizontalPowerBar;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiChemicalGauge;
import mekanism.client.gui.element.gauge.GuiFluidGauge;
import mekanism.client.gui.element.progress.GuiProgress;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.tab.GuiEnergyTab;
import mekanism.common.MekanismLang;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.util.text.EnergyDisplay;
import mekanism.generators.common.GeneratorsLang;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GuiCompactIndustrialTurbine<BE extends BEAbstractCompactIndustrialTurbine>
        extends GuiConfigurableTile<BE, MekanismTileContainer<BE>> {
    public GuiCompactIndustrialTurbine(MekanismTileContainer<BE> container, Inventory inv, Component title) {
        super(container, inv, title);
        dynamicSlots = true;
        titleLabelY = 4;
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addRenderableWidget(new GuiDownArrow(this, 159, 44));
        addRenderableWidget(new GuiHorizontalPowerBar(this, tile.getEnergyContainer(), 115, 75));
        addRenderableWidget(new GuiFluidGauge(tile::getFluidTank, () -> tile.getFluidTanks(null),
                GaugeType.STANDARD, this, 133, 13));
        addRenderableWidget(new GuiChemicalGauge(tile::getChemicalTank, () -> tile.getChemicalTanks(null),
                GaugeType.STANDARD, this, 25, 13));
        addRenderableWidget(new GuiProgress(tile::getActive, ProgressType.LARGE_RIGHT, this, 64, 39));
        addRenderableWidget(new GuiEnergyTab(this, () -> List.of(
                new Component[] {
                        MekanismLang.STORING.translate(EnergyDisplay.of(tile.getEnergyContainer())),
                        GeneratorsLang.PRODUCING_AMOUNT.translate(EnergyDisplay.of(tile.getEnergyGenerated())),
                })));
    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        renderTitleText(guiGraphics);
        super.drawForegroundText(guiGraphics, mouseX, mouseY);
    }

}
