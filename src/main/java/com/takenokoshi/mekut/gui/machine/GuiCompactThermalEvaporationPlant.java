package com.takenokoshi.mekut.gui.machine;

import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.takenokoshi.mekaddonlib.inventory.container.MekALDynamicSizedContainer;
import com.takenokoshi.mekut.blockentity.abs.BEAbstractCompactThermalEvaporationPlant;

import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.client.gui.GuiConfigurableTile;
import mekanism.client.gui.element.GuiDownArrow;
import mekanism.client.gui.element.GuiElement;
import mekanism.client.gui.element.GuiInnerScreen;
import mekanism.client.gui.element.bar.GuiBar.IBarInfoHandler;
import mekanism.client.gui.element.bar.GuiHorizontalRateBar;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiFluidGauge;
import mekanism.client.gui.element.tab.GuiHeatTab;
import mekanism.client.gui.element.tab.GuiWarningTab;
import mekanism.client.recipe_viewer.type.RecipeViewerRecipeType;
import mekanism.common.MekanismLang;
import mekanism.common.content.evaporation.EvaporationMultiblockData;
import mekanism.common.inventory.warning.IWarningTracker;
import mekanism.common.inventory.warning.WarningTracker.WarningType;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.UnitDisplayUtils.TemperatureUnit;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GuiCompactThermalEvaporationPlant<BE extends BEAbstractCompactThermalEvaporationPlant>
        extends GuiConfigurableTile<BE, MekALDynamicSizedContainer<BE>> {

    private GuiElement inputGauge, outputGauge;

    public GuiCompactThermalEvaporationPlant(MekALDynamicSizedContainer<BE> container, Inventory inv,
            Component title) {
        super(container, inv, title);
        dynamicSlots = true;
        imageWidth += 20;
        inventoryLabelX += 10;
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addRenderableWidget(new GuiInnerScreen(this, 48, 19, 100, 40, () -> {
            return List.of(MekanismLang.MULTIBLOCK_FORMED.translate(), MekanismLang.EVAPORATION_HEIGHT.translate(18),
                    MekanismLang.TEMPERATURE.translate(MekanismUtils.getTemperatureDisplay(
                            tile.getHeatCapacitor().getTemperature(), TemperatureUnit.KELVIN, true)),
                    MekanismLang.FLUID_PRODUCTION.translate(Math.round(tile.getOperationsPerTickD() * 100D) / 100D));
        })).padding(3).clearSpacing().recipeViewerCategories(RecipeViewerRecipeType.EVAPORATING);
        addRenderableWidget(new GuiDownArrow(this, 32, 39));
        addRenderableWidget(new GuiDownArrow(this, 156, 39));
        addRenderableWidget(new GuiHorizontalRateBar(this, new IBarInfoHandler() {
            @Override
            public Component getTooltip() {
                return MekanismUtils.getTemperatureDisplay(tile.getHeatCapacitor().getTemperature(),
                        TemperatureUnit.KELVIN, true);
            }

            @Override
            public double getLevel() {
                return Math.min(1,
                        tile.getHeatCapacitor().getTemperature() / EvaporationMultiblockData.MAX_MULTIPLIER_TEMP);
            }
        }, 58, 62)).warning(WarningType.INPUT_DOESNT_PRODUCE_OUTPUT,
                tile.getWarningCheck(RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT));
        inputGauge = addRenderableWidget(
                new GuiFluidGauge(tile::getInputTank, () -> tile.getFluidTanks(null), GaugeType.STANDARD, this, 6, 13))
                .warning(WarningType.NO_MATCHING_RECIPE, tile.getWarningCheck(RecipeError.NOT_ENOUGH_INPUT));
        outputGauge = addRenderableWidget(new GuiFluidGauge(tile::getOutputTank, () -> tile.getFluidTanks(null),
                GaugeType.STANDARD, this, 172, 13))
                .warning(WarningType.NO_SPACE_IN_OUTPUT, tile.getWarningCheck(RecipeError.NOT_ENOUGH_OUTPUT_SPACE));
        addRenderableWidget(new GuiHeatTab(this, () -> {
            Component environment = MekanismUtils.getTemperatureDisplay(tile.getEnvironmentLoss(),
                    TemperatureUnit.KELVIN, false);
            return Collections.singletonList(MekanismLang.DISSIPATED_RATE.translate(environment));
        }));
    }

    @Override
    protected void addWarningTab(IWarningTracker warningTracker) {
        addRenderableWidget(new GuiWarningTab(this, warningTracker, false));
    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        renderTitleTextWithOffset(guiGraphics, inputGauge.getRelativeRight(), outputGauge.getRelativeX());
        renderInventoryText(guiGraphics);
        super.drawForegroundText(guiGraphics, mouseX, mouseY);
    }

}
