package com.takenokoshi.mekut.gui.factory;

import org.jetbrains.annotations.NotNull;

import com.takenokoshi.mekaddonlib.inventory.container.MekALDynamicSizedContainer;
import com.takenokoshi.mekut.blockentity.factory.BEEnergizedSmeltingFactory;
import com.takenokoshi.mekut.gui.tab.MekUtFactoryGuiSortingTab;
import com.takenokoshi.mekut.recipe_viewer.type.MekUtRecipeViewerRecipeType;

import mekanism.client.gui.GuiConfigurableTile;
import mekanism.client.gui.element.bar.GuiChemicalBar;
import mekanism.client.gui.element.bar.GuiVerticalPowerBar;
import mekanism.client.gui.element.progress.GuiProgress;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GuiEnergizedSmeltingFactory extends
        GuiConfigurableTile<BEEnergizedSmeltingFactory, MekALDynamicSizedContainer<BEEnergizedSmeltingFactory>> {

    public GuiEnergizedSmeltingFactory(MekALDynamicSizedContainer<BEEnergizedSmeltingFactory> container,
            Inventory inv, Component title) {
        super(container, inv, title);
        dynamicSlots = true;
        imageWidth += tile.getExtraWidth();
        imageHeight += tile.getExtraHeight();
        inventoryLabelX += tile.getExtraWidth() / 2;
        inventoryLabelY += tile.getExtraHeight();
        titleLabelY -= 2;
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addRenderableWidget(new MekUtFactoryGuiSortingTab(this, tile));
        addRenderableWidget(new GuiVerticalPowerBar(this, tile.getEnergyContainer(), imageWidth - 12, 16));
        addRenderableWidget(new GuiChemicalBar(this,
                GuiChemicalBar.getProvider(tile.getXpTank(), tile.getChemicalTanks(null)), 7,
                76, imageWidth - 14, 4, true));
        for (int i = 0; i < tile.tier.processes; i++) {
            int p = i;
            addRenderableWidget(new GuiProgress(() -> tile.getScaledProgress(p), ProgressType.DOWN, this,
                    20 * i + 4 + tile.slotsLeftPos, 33))
                    .recipeViewerCategories(
                            new IRecipeViewerRecipeType[] { MekUtRecipeViewerRecipeType.TWEAKED_SMELLTING, });
        }
    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        renderTitleText(guiGraphics);
        renderInventoryText(guiGraphics);
        super.drawForegroundText(guiGraphics, mouseX, mouseY);
    }

}
