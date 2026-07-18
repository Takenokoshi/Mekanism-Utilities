package com.takenokoshi.mekut.gui.machine;

import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.takenokoshi.mekaddonlib.inventory.container.MekALDynamicSizedContainer;
import com.takenokoshi.mekut.blockentity.abs.BEAbstractCompactBoiler;

import mekanism.api.text.EnumColor;
import mekanism.client.gui.GuiConfigurableTile;
import mekanism.client.gui.element.GuiInnerScreen;
import mekanism.client.gui.element.bar.GuiBar.IBarInfoHandler;
import mekanism.client.gui.element.bar.GuiVerticalRateBar;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiChemicalGauge;
import mekanism.client.gui.element.gauge.GuiFluidGauge;
import mekanism.client.gui.element.tab.GuiHeatTab;
import mekanism.client.gui.element.tab.GuiWarningTab;
import mekanism.client.recipe_viewer.type.RecipeViewerRecipeType;
import mekanism.common.MekanismLang;
import mekanism.common.config.MekanismConfig;
import mekanism.common.inventory.warning.IWarningTracker;
import mekanism.common.util.HeatUtils;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.UnitDisplayUtils.TemperatureUnit;
import mekanism.common.util.text.TextUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GuiCompactBoiler<BE extends BEAbstractCompactBoiler>
        extends GuiConfigurableTile<BE, MekALDynamicSizedContainer<BE>> {

    public GuiCompactBoiler(MekALDynamicSizedContainer<BE> container, Inventory inv, Component title) {
        super(container, inv, title);
        dynamicSlots = true;
        imageWidth += tile.getExtraWidth();
        inventoryLabelX += tile.getExtraWidth() / 2;
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addRenderableWidget(new GuiInnerScreen(this, 54, 13, 110, 40, () -> {
            return List.of(
                    MekanismLang.TEMPERATURE.translate(MekanismUtils.getTemperatureDisplay(
                            tile.getTotalTemperature(), TemperatureUnit.KELVIN, true)),
                    MekanismLang.BOIL_RATE.translate(TextUtils.format(tile.getBoilRate())),
                    MekanismLang.MAX_BOIL_RATE.translate(TextUtils.format(tile.getMaxBoil())));
        })).padding(3).recipeViewerCategories(RecipeViewerRecipeType.BOILER);
        addRenderableWidget(new GuiVerticalRateBar(this, new IBarInfoHandler() {
            @Override
            public Component getTooltip() {
                return MekanismLang.BOIL_RATE.translate(TextUtils.format(tile.getBoilRate()));
            }

            @Override
            public double getLevel() {
                return Math.min(1, tile.getBoilRate() / (double) tile.getMaxBoil());
            }
        }, 44, 13));
        addRenderableWidget(new GuiVerticalRateBar(this, new IBarInfoHandler() {
            @Override
            public Component getTooltip() {
                return MekanismLang.MAX_BOIL_RATE.translate(TextUtils.format(tile.getMaxBoil()));
            }

            @Override
            public double getLevel() {
                return Math.min(1, tile.getMaxBoil() * HeatUtils.getWaterThermalEnthalpy() /
                        (tile.superheatingElements * MekanismConfig.general.superheatingHeatTransfer.get()));
            }
        }, 166, 13));
        addRenderableWidget(new GuiChemicalGauge(tile::getHeatedCoolantTank, () -> tile.getChemicalTanks(null),
                GaugeType.SMALL, this, 6, 25))
                .setLabel(MekanismLang.BOILER_HEATED_COOLANT_TANK.translateColored(EnumColor.ORANGE));
        addRenderableWidget(new GuiFluidGauge(tile::getWaterTank, () -> tile.getFluidTanks(null),
                GaugeType.SMALL, this, 26, 25))
                .setLabel(MekanismLang.BOILER_WATER_TANK.translateColored(EnumColor.INDIGO));
        addRenderableWidget(new GuiChemicalGauge(tile::getSteamTank, () -> tile.getChemicalTanks(null),
                GaugeType.SMALL, this, 174, 25))
                .setLabel(MekanismLang.BOILER_STEAM_TANK.translateColored(EnumColor.GRAY));
        addRenderableWidget(new GuiChemicalGauge(tile::getCooledCoolantTank, () -> tile.getChemicalTanks(null),
                GaugeType.SMALL, this, 194, 25))
                .setLabel(MekanismLang.BOILER_COOLANT_TANK.translateColored(EnumColor.AQUA));
        addRenderableWidget(new GuiHeatTab(this, () -> {
            Component environment = MekanismUtils.getTemperatureDisplay(tile.getEnvironmentLoss(), TemperatureUnit.KELVIN, false);
            return Collections.singletonList(MekanismLang.DISSIPATED_RATE.translate(environment));
        }));

    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        renderTitleText(guiGraphics);
        renderInventoryText(guiGraphics);
        super.drawForegroundText(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void addWarningTab(IWarningTracker warningTracker) {
        addRenderableWidget(new GuiWarningTab(this, warningTracker, false));
    }

}
