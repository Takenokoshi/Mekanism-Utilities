package com.takenokoshi.mekut.gui.machine;

import org.jetbrains.annotations.NotNull;

import com.takenokoshi.mekut.blockentity.interfaces.IHasInputChemicalTank;
import com.takenokoshi.mekut.blockentity.interfaces.IHasMachineEnergyContainer;
import com.takenokoshi.mekut.blockentity.interfaces.IRecipeViewerTypeProvider;
import com.takenokoshi.mekut.blockentity.interfaces.IScaledProgressProvider;
import com.takenokoshi.mekut.blockentity.interfaces.IWarningSupporter;

import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.client.gui.GuiConfigurableTile;
import mekanism.client.gui.element.bar.GuiChemicalBar;
import mekanism.client.gui.element.bar.GuiVerticalPowerBar;
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

public class GuiChemicalCutter<BE extends TileEntityMekanism & ISideConfiguration & IHasInputChemicalTank & IScaledProgressProvider & IHasMachineEnergyContainer & IWarningSupporter & IRecipeViewerTypeProvider>
        extends GuiConfigurableTile<BE, MekanismTileContainer<BE>> {

    public GuiChemicalCutter(MekanismTileContainer<BE> container, Inventory inv, Component title) {
        super(container, inv, title);
        dynamicSlots = true;
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addRenderableWidget(new GuiVerticalPowerBar(this, tile.getEnergyContainer(), 164, 16))
                .warning(WarningType.NOT_ENOUGH_ENERGY, tile.getWarningCheck(RecipeError.NOT_ENOUGH_ENERGY));
        addRenderableWidget(new GuiEnergyTab(this, tile.getEnergyContainer(), tile::getActive));
        addRenderableWidget(new GuiProgress(tile::getScaledProgress, ProgressType.BAR, this, 86, 38))
                .recipeViewerCategories(new IRecipeViewerRecipeType[] { tile.recipeViewerType() })
                .warning(WarningType.INPUT_DOESNT_PRODUCE_OUTPUT,
                        tile.getWarningCheck(RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT));
        addRenderableWidget(new GuiChemicalBar(this,
                GuiChemicalBar.getProvider(tile.getInputTank(), tile.getChemicalTanks(null)), 68, 36, 6, 12, false))
                .warning(WarningType.NO_MATCHING_RECIPE, tile.getWarningCheck(RecipeError.NOT_ENOUGH_SECONDARY_INPUT));
    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        renderTitleText(guiGraphics);
        renderInventoryText(guiGraphics);
        super.drawForegroundText(guiGraphics, mouseX, mouseY);
    }

}
