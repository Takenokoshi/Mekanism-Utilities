package com.takenokoshi.mekut.gui.machine;

import org.jetbrains.annotations.NotNull;

import com.takenokoshi.mekut.blockentity.interfaces.IHasMachineEnergyContainer;
import com.takenokoshi.mekut.blockentity.interfaces.IWarningSupporter;
import com.takenokoshi.mekut.blockentity.interfaces.machine.IFluidToObjectMachine;

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
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.inventory.warning.WarningTracker.WarningType;
import mekanism.common.tile.base.TileEntityMekanism;
import mekanism.common.tile.interfaces.ISideConfiguration;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GuiFluidToObjectMachine<BE extends TileEntityMekanism & ISideConfiguration & IFluidToObjectMachine<?> & IHasMachineEnergyContainer & IWarningSupporter>
        extends GuiConfigurableTile<BE, MekanismTileContainer<BE>> {

    public GuiFluidToObjectMachine(MekanismTileContainer<BE> container, Inventory inv, Component title) {
        super(container, inv, title);
        dynamicSlots = true;
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addRenderableWidget(new GuiVerticalPowerBar(this, tile.getEnergyContainer(), 157, 23))
                .warning(WarningType.NOT_ENOUGH_ENERGY, tile.getWarningCheck(RecipeError.NOT_ENOUGH_ENERGY));
        addRenderableWidget(new GuiEnergyTab(this, tile.getEnergyContainer(), tile::getEnergyUsed));
        addRenderableWidget(
                new GuiFluidGauge(tile::getInputTank, () -> tile.getFluidTanks(null), GaugeType.STANDARD,
                        this, 25, 22));
        addRenderableWidget(new GuiProgress(tile::getScaledProgress, ProgressType.LARGE_RIGHT, this, 53, 61))
                .warning(WarningType.INPUT_DOESNT_PRODUCE_OUTPUT,
                        tile.getWarningCheck(RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT))
                .recipeViewerCategories(new IRecipeViewerRecipeType[] { tile.recipeViewerType() });
        addRenderableWidget(new GuiDownArrow(this, 12, 53));
    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        renderTitleTextWithOffset(guiGraphics, 25, 150);
        super.drawForegroundText(guiGraphics, mouseX, mouseY);
    }

}
