package com.takenokoshi.mekut.gui.machine;

import com.takenokoshi.mekut.blockentity.interfaces.IHasMachineEnergyContainer;
import com.takenokoshi.mekut.blockentity.interfaces.IWarningSupporter;
import com.takenokoshi.mekut.blockentity.interfaces.machine.IBiChemicalToChemicalRecipeMachine;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiChemicalGauge;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.inventory.warning.WarningTracker.WarningType;
import mekanism.common.tile.base.TileEntityMekanism;
import mekanism.common.tile.interfaces.ISideConfiguration;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GuiBiChemicalToChemicalMachine<BE extends TileEntityMekanism & ISideConfiguration & IBiChemicalToChemicalRecipeMachine & IHasMachineEnergyContainer & IWarningSupporter>
        extends GuiBiChemicalToObjectMachine<BE> {

    public GuiBiChemicalToChemicalMachine(MekanismTileContainer<BE> container, Inventory inv, Component title) {
        super(container, inv, title);
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addRenderableWidget(new GuiChemicalGauge(tile::getOutputTank, () -> tile.getChemicalTanks(null),
                GaugeType.STANDARD, this, 79, 4))
                .warning(WarningType.NO_SPACE_IN_OUTPUT, tile.getWarningCheck(RecipeError.NOT_ENOUGH_OUTPUT_SPACE));
    }

    @Override
    protected void renderTitleText(GuiGraphics guiGraphics) {
        renderTitleTextWithOffset(guiGraphics, 1, 78, 4, TextAlignment.LEFT);
    }

    @Override
    protected void renderInventoryText(GuiGraphics guiGraphics) {
        renderInventoryText(guiGraphics, 78);
    }

}
