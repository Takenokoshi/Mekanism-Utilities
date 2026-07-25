package com.takenokoshi.mekut.gui.machine;

import java.util.List;
import org.jetbrains.annotations.NotNull;

import com.takenokoshi.mekaddonlib.inventory.container.MekALDynamicSizedContainer;
import com.takenokoshi.mekut.blockentity.abs.BEAbstractCompactFusionReactor;
import com.takenokoshi.mekut.network.to_server.PacketGuiSetBurnRate;

import mekanism.client.gui.GuiConfigurableTile;
import mekanism.client.gui.element.gauge.GaugeInfo;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiChemicalGauge;
import mekanism.client.gui.element.gauge.GuiEnergyGauge;
import mekanism.client.gui.element.gauge.GuiFluidGauge;
import mekanism.client.gui.element.gauge.GuiNumberGauge;
import mekanism.client.gui.element.gauge.GuiNumberGauge.INumberInfoHandler;
import mekanism.client.gui.element.progress.GuiProgress;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.tab.GuiEnergyTab;
import mekanism.client.gui.element.tab.GuiHeatTab;
import mekanism.client.gui.element.text.GuiTextField;
import mekanism.client.render.MekanismRenderer;
import mekanism.client.render.MekanismRenderer.FluidTextureType;
import mekanism.common.MekanismLang;
import mekanism.common.network.PacketUtils;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.UnitDisplayUtils.TemperatureUnit;
import mekanism.common.util.text.EnergyDisplay;
import mekanism.common.util.text.InputValidator;
import mekanism.generators.common.GeneratorsLang;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.material.Fluids;

public class GuiCompactFusionReactor<BE extends BEAbstractCompactFusionReactor>
        extends GuiConfigurableTile<BE, MekALDynamicSizedContainer<BE>> {

    private static final double MAX_LEVEL = 500_000_000;
    private GuiTextField injectionRateField;

    public GuiCompactFusionReactor(MekALDynamicSizedContainer<BE> container, Inventory inv, Component title) {
        super(container, inv, title);
        dynamicSlots = true;
        imageWidth += tile.getExtraWidth();
        inventoryLabelX += (tile.getExtraWidth() >> 1);
        imageHeight += tile.getExtraHeight();
        inventoryLabelY += tile.getExtraHeight();
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addRenderableWidget(new GuiEnergyTab(this, () -> {
            return List.of(MekanismLang.STORING.translate(EnergyDisplay.of(tile.getEnergyContainer())),
                    GeneratorsLang.PRODUCING_AMOUNT.translate(EnergyDisplay.of(tile.getLastEnergyGenerated())));
        }));
        addRenderableWidget(new GuiHeatTab(this, () -> {
            Component transfer = MekanismUtils.getTemperatureDisplay(tile.lastTransferLoss,
                    TemperatureUnit.KELVIN, false);
            Component environment = MekanismUtils.getTemperatureDisplay(tile.lastEnvironmentLoss,
                    TemperatureUnit.KELVIN, false);
            return List.of(MekanismLang.TRANSFERRED_RATE.translate(transfer),
                    MekanismLang.DISSIPATED_RATE.translate(environment));
        }));
        addRenderableWidget(new GuiChemicalGauge(tile::getLeftFuelTank, () -> tile.getChemicalTanks(null),
                GaugeType.SMALL, this, 30, 64));
        addRenderableWidget(new GuiChemicalGauge(tile::getMixedFuelTank, () -> tile.getChemicalTanks(null),
                GaugeType.STANDARD, this, 84, 50) {
            @Override
            protected GaugeInfo getGaugeColor() {
                return GaugeInfo.YELLOW;
            }
        });
        addRenderableWidget(new GuiChemicalGauge(tile::getRightFuelTank, () -> tile.getChemicalTanks(null),
                GaugeType.SMALL, this, 138, 64) {
            @Override
            protected GaugeInfo getGaugeColor() {
                return GaugeInfo.ORANGE;
            }
        });
        addRenderableWidget(new GuiProgress(tile::getActive, ProgressType.SMALL_RIGHT, this, 52, 76));
        addRenderableWidget(new GuiProgress(tile::getActive, ProgressType.SMALL_LEFT, this, 106, 76));
        injectionRateField = addRenderableWidget(new GuiTextField(this, 103, 115, 150, 11));
        injectionRateField.setInputValidator(InputValidator.DIGIT)
                .setEnterHandler(this::setInjection)
                .setMaxLength(19);
        setInitialFocus(injectionRateField);

        addRenderableWidget(new GuiNumberGauge(new INumberInfoHandler() {
            @Override
            public TextureAtlasSprite getIcon() {
                return MekanismRenderer.getBaseFluidTexture(Fluids.LAVA, FluidTextureType.STILL);
            }

            @Override
            public double getLevel() {
                return tile.getLastPlasmaTemp();
            }

            @Override
            public double getScaledLevel() {
                return Math.min(1, getLevel() / MAX_LEVEL);
            }

            @Override
            public Component getText() {
                return GeneratorsLang.REACTOR_PLASMA
                        .translate(MekanismUtils.getTemperatureDisplay(getLevel(), TemperatureUnit.KELVIN, true));
            }
        }, GaugeType.STANDARD, this, 176 + 12, 50));
        addRenderableWidget(new GuiProgress(() -> {
            return tile.getLastPlasmaTemp() > tile.getLastCaseTemp();
        }, ProgressType.SMALL_RIGHT, this, 176 + 34, 76));
        addRenderableWidget(new GuiNumberGauge(new INumberInfoHandler() {
            @Override
            public TextureAtlasSprite getIcon() {
                return MekanismRenderer.getBaseFluidTexture(Fluids.LAVA, FluidTextureType.STILL);
            }

            @Override
            public double getLevel() {
                return tile.getLastCaseTemp();
            }

            @Override
            public double getScaledLevel() {
                return Math.min(1, getLevel() / MAX_LEVEL);
            }

            @Override
            public Component getText() {
                return GeneratorsLang.REACTOR_CASE
                        .translate(MekanismUtils.getTemperatureDisplay(getLevel(), TemperatureUnit.KELVIN, true));
            }
        }, GaugeType.STANDARD, this, 176 + 66, 50));
        addRenderableWidget(new GuiProgress(() -> tile.getCaseTemp() > 0,
                ProgressType.SMALL_RIGHT, this, 176 + 88, 61));
        addRenderableWidget(new GuiProgress(() -> {
            return tile.getCaseTemp() > 0 && !tile.getWaterTank().isEmpty()
                    && tile.getSteamTank().getStored() < tile.getSteamTank().getCapacity();
        }, ProgressType.SMALL_RIGHT, this, 176 + 88, 91));
        addRenderableWidget(new GuiFluidGauge(tile::getWaterTank, () -> tile.getFluidTanks(null),
                GaugeType.SMALL, this, 176 + 120, 84));
        addRenderableWidget(new GuiChemicalGauge(tile::getSteamTank, () -> tile.getChemicalTanks(null),
                GaugeType.SMALL, this, 176 + 146, 84));
        addRenderableWidget(new GuiEnergyGauge(tile.getEnergyContainer(), GaugeType.SMALL, this, 176 + 120, 46));
    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        renderTitleText(guiGraphics);
        renderInventoryText(guiGraphics);
        drawScrollingString(guiGraphics, GeneratorsLang.REACTOR_INJECTION_RATE.translate(tile.getInjectionRate()),
                0, 35, TextAlignment.CENTER, titleTextColor(), 16, false);
        drawScrollingString(guiGraphics, GeneratorsLang.REACTOR_EDIT_RATE.translate(),
                4, 117, TextAlignment.RIGHT, titleTextColor(), 89, 2, false);
        drawScrollingString(guiGraphics, MekanismLang.MULTIBLOCK_FORMED.translate(),
                0, 16, TextAlignment.LEFT, titleTextColor(), 13, false);
        super.drawForegroundText(guiGraphics, mouseX, mouseY);
    }

    private void setInjection() {
        if (!injectionRateField.getText().isEmpty()) {
            PacketUtils.sendToServer(
                    new PacketGuiSetBurnRate(tile.getBlockPos(), Long.parseLong(injectionRateField.getText())));
            injectionRateField.setText("");
        }
    }
}
