package com.takenokoshi.mekut.gui.machine;

import org.jetbrains.annotations.NotNull;

import com.takenokoshi.mekaddonlib.inventory.container.MekALDynamicSizedContainer;
import com.takenokoshi.mekut.blockentity.abs.BEAbstractGreenHouse;
import com.takenokoshi.mekut.recipe_viewer.type.MekUtRecipeViewerRecipeType;

import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.client.gui.GuiConfigurableTile;
import mekanism.client.gui.element.GuiDownArrow;
import mekanism.client.gui.element.bar.GuiVerticalPowerBar;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiFluidGauge;
import mekanism.client.gui.element.progress.GuiProgress;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.tab.GuiEnergyTab;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mekanism.common.inventory.warning.WarningTracker.WarningType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GuiGreenHouse<BE extends BEAbstractGreenHouse>
        extends GuiConfigurableTile<BE, MekALDynamicSizedContainer<BE>> {

    public GuiGreenHouse(MekALDynamicSizedContainer<BE> container, Inventory inv, Component title) {
        super(container, inv, title);
        dynamicSlots = true;
        imageWidth += tile.getExtraWidth();
        imageHeight += tile.getExtraHeight();
        inventoryLabelX += tile.getExtraWidth() / 2;
        inventoryLabelY += tile.getExtraHeight();
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addRenderableWidget(new GuiDownArrow(this, 68, 38));
        addRenderableWidget(new GuiDownArrow(this, 25, 41));
        addRenderableWidget(new GuiFluidGauge(tile::getFertilizerTank, () -> tile.getFluidTanks(null),
                GaugeType.STANDARD, this, 40, 10))
                .warning(WarningType.NO_MATCHING_RECIPE, tile.getWarningCheck(RecipeError.NOT_ENOUGH_INPUT));
        addRenderableWidget(new GuiEnergyTab(this, tile.getEnergyContainer(), tile::getEnergyUsed));
        addRenderableWidget(new GuiVerticalPowerBar(this, tile.getEnergyContainer(), 218, 15))
                .warning(WarningType.NOT_ENOUGH_ENERGY, tile.getWarningCheck(RecipeError.NOT_ENOUGH_ENERGY))
                .warning(WarningType.NOT_ENOUGH_ENERGY_REDUCED_RATE,
                        tile.getWarningCheck(RecipeError.NOT_ENOUGH_ENERGY_REDUCED_RATE));
        addRenderableWidget(new GuiProgress(tile::getScaledProgress, ProgressType.BAR, this, 86, 38))
                .warning(WarningType.INPUT_DOESNT_PRODUCE_OUTPUT,
                        tile.getWarningCheck(RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT))
                .recipeViewerCategories(new IRecipeViewerRecipeType[] { MekUtRecipeViewerRecipeType.GREEN_HOUSE, });
    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        renderTitleText(guiGraphics);
        renderInventoryText(guiGraphics);
        super.drawForegroundText(guiGraphics, mouseX, mouseY);
    }

}
