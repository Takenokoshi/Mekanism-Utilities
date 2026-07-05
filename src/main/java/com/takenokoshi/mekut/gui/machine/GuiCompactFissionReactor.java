package com.takenokoshi.mekut.gui.machine;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekut.blockentity.abs.BEAbstractCompactFissionReactor;
import com.takenokoshi.mekut.inventory.container.MekUtDynamicSizedContainer;
import com.takenokoshi.mekut.network.to_server.PacketGuiSetBurnRate;
import com.takenokoshi.mekut.recipe_viewer.type.MekUtRecipeViewerRecipeType;

import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.text.EnumColor;
import mekanism.client.gui.GuiConfigurableTile;
import mekanism.client.gui.element.GuiBigLight;
import mekanism.client.gui.element.GuiInnerScreen;
import mekanism.client.gui.element.bar.GuiBar.IBarInfoHandler;
import mekanism.client.gui.element.bar.GuiDynamicHorizontalRateBar;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiChemicalGauge;
import mekanism.client.gui.element.gauge.GuiMergedTankGauge;
import mekanism.client.gui.element.graph.GuiDoubleGraph;
import mekanism.client.gui.element.tab.GuiHeatTab;
import mekanism.client.gui.element.tab.GuiWarningTab;
import mekanism.client.gui.element.text.GuiTextField;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mekanism.common.MekanismLang;
import mekanism.common.inventory.warning.IWarningTracker;
import mekanism.common.inventory.warning.WarningTracker.WarningType;
import mekanism.common.network.PacketUtils;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.UnitDisplayUtils.TemperatureUnit;
import mekanism.common.util.text.TextUtils;
import mekanism.common.util.text.BooleanStateDisplay.ActiveDisabled;
import mekanism.common.util.text.InputValidator;
import mekanism.generators.client.recipe_viewer.GeneratorsRVRecipeType;
import mekanism.generators.common.GeneratorsLang;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GuiCompactFissionReactor<BE extends BEAbstractCompactFissionReactor>
        extends GuiConfigurableTile<BE, MekUtDynamicSizedContainer<BE>> {
    private GuiDoubleGraph heatGraph;
    private GuiTextField rateLimitField;

    public GuiCompactFissionReactor(MekUtDynamicSizedContainer<BE> container, Inventory inv, Component title) {
        super(container, inv, title);
        dynamicSlots = true;
        imageWidth += tile.getExtraWidth();
        imageHeight += tile.getExtraHeight();
        inventoryLabelX += (tile.getExtraWidth() >> 1);
        inventoryLabelY += tile.getExtraHeight();
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addRenderableWidget(new GuiInnerScreen(this, 45, 17, 105, 56, () -> {
            return List.of(
                    MekanismLang.STATUS.translate(tile.getActive() ? EnumColor.BRIGHT_GREEN : EnumColor.RED,
                            ActiveDisabled.of(tile.getActive())),
                    GeneratorsLang.GAS_BURN_RATE.translate(tile.getBurnRate()),
                    GeneratorsLang.FISSION_RATE_LIMIT.translate(tile.getOperationsPerTick()),
                    GeneratorsLang.FISSION_HEATING_RATE
                            .translate(TextUtils.format(tile.coolantHeatingLookupMonitor.getLastBoilRate())),
                    MekanismLang.TEMPERATURE.translate(EnumColor.ORANGE,
                            MekanismUtils.getTemperatureDisplay(tile.getHeatCapacitor().getTemperature(),
                                    TemperatureUnit.KELVIN, true)));
        })).spacing(1).recipeViewerCategories(new IRecipeViewerRecipeType[] {
                MekUtRecipeViewerRecipeType.FISSION_REACTOR,
                GeneratorsRVRecipeType.FISSION, });
        addRenderableWidget(new GuiMergedTankGauge<>(tile::getCooledCoolantTank, () -> tile,
                GaugeType.STANDARD, this, 6, 13))
                .setLabel(GeneratorsLang.FISSION_COOLANT_TANK.translateColored(EnumColor.AQUA))
                .warning(WarningType.NO_SPACE_IN_OUTPUT, tile.getWarningCheck(BEAbstractCompactFissionReactor.TOO_HOT));
        addRenderableWidget(new GuiChemicalGauge(tile::getInputTank, () -> tile.getChemicalTanks(null),
                GaugeType.STANDARD, this, 25, 13))
                .setLabel(GeneratorsLang.FISSION_FUEL_TANK.translateColored(EnumColor.DARK_GREEN))
                .warning(WarningType.NO_MATCHING_RECIPE, tile.getWarningCheck(RecipeError.NOT_ENOUGH_INPUT));
        addRenderableWidget(new GuiChemicalGauge(tile::getHeatedCoolantTank, () -> tile.getChemicalTanks(null),
                GaugeType.STANDARD, this, 152, 13))
                .setLabel(GeneratorsLang.FISSION_HEATED_COOLANT_TANK.translateColored(EnumColor.ORANGE))
                .warning(WarningType.NO_SPACE_IN_OUTPUT, tile.getWarningCheck(BEAbstractCompactFissionReactor.TOO_HOT));
        addRenderableWidget(new GuiChemicalGauge(tile::getOutputTank, () -> tile.getChemicalTanks(null),
                GaugeType.STANDARD, this, 171, 13))
                .setLabel(GeneratorsLang.FISSION_WASTE_TANK.translateColored(EnumColor.BROWN))
                .warning(WarningType.NO_SPACE_IN_OUTPUT, tile.getWarningCheck(RecipeError.NOT_ENOUGH_OUTPUT_SPACE));
        addRenderableWidget(new GuiHeatTab(this, () -> {
            return List.of(
                    MekanismLang.DISSIPATED_RATE.translate(MekanismUtils.getTemperatureDisplay(tile.lastEnvironmentLoss,
                            TemperatureUnit.KELVIN, false)),
                    MekanismLang.TRANSFERRED_RATE.translate(MekanismUtils.getTemperatureDisplay(tile.lastAdjacentLoss,
                            TemperatureUnit.KELVIN, false)));
        }));

        rateLimitField = addRenderableWidget(new GuiTextField(this, 77, 76, 54, 12));
        rateLimitField.setEnterHandler(this::setRateLimit);
        rateLimitField.setInputValidator(InputValidator.DIGIT);
        rateLimitField.setMaxLength(21);
        rateLimitField.addCheckmarkButton(this::setRateLimit);

        addRenderableWidget(new GuiBigLight(this, 173, 76, tile::getActive));
        addRenderableWidget(new GuiDynamicHorizontalRateBar(this, new IBarInfoHandler() {

            @Override
            public @Nullable Component getTooltip() {
                return MekanismUtils.getTemperatureDisplay(tile.getHeatCapacitor().getTemperature(),
                        TemperatureUnit.KELVIN, true);
            }

            @Override
            public double getLevel() {
                return Math.min(1.0d, tile.getHeatCapacitor().getTemperature() / tile.tempLimit);
            }

        }, 5, 102, imageWidth - 12));

        heatGraph = addRenderableWidget(new GuiDoubleGraph(this, 5, 123, imageWidth - 10, 38,
                temp -> MekanismUtils.getTemperatureDisplay(temp, TemperatureUnit.KELVIN, true)));
        heatGraph.setMinScale(1_600);
    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        renderTitleText(guiGraphics);
        renderInventoryText(guiGraphics);
        drawScrollingString(guiGraphics, MekanismLang.TEMPERATURE_LONG.translate(""), 0, 93, TextAlignment.LEFT,
                titleTextColor(), 5, false);
        drawScrollingString(guiGraphics, GeneratorsLang.FISSION_HEAT_GRAPH.translate(), 0, 114, TextAlignment.LEFT,
                titleTextColor(), 5, false);
        drawScrollingString(guiGraphics, GeneratorsLang.FISSION_SET_RATE_LIMIT.translate(), 3, 76, TextAlignment.RIGHT,
                titleTextColor(), rateLimitField.getRelativeX() - 2, 3, false);
        super.drawForegroundText(guiGraphics, mouseX, mouseY);
    }

    @Override
    public void containerTick() {
        super.containerTick();
        heatGraph.addData(tile.getHeatCapacitor().getTemperature());
    }

    @Override
    protected void addWarningTab(IWarningTracker warningTracker) {
        // Move the tab to the right side of the gui so it doesn't intersect the heat
        // tab
        addRenderableWidget(new GuiWarningTab(this, warningTracker, false));
    }

    private void setRateLimit() {
        if (!rateLimitField.getText().isEmpty()) {
            try {
                long value = Long.parseLong(rateLimitField.getText());
                PacketUtils.sendToServer(new PacketGuiSetBurnRate(tile.getBlockPos(), value));
                rateLimitField.setText("");
            } catch (Exception e) {
            }
        }
    }
}
