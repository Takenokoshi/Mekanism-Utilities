package com.takenokoshi.mekut.gui.machine;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.takenokoshi.mekaddonlib.blockentity.interfaces.IHasGuiSizeOffset;
import com.takenokoshi.mekaddonlib.blockentity.interfaces.IWarningSupporter;
import com.takenokoshi.mekaddonlib.inventory.container.MekALDynamicSizedContainer;
import com.takenokoshi.mekut.blockentity.interfaces.IHasInputChemicalTank;
import com.takenokoshi.mekut.blockentity.interfaces.IHasMachineEnergyContainer;
import com.takenokoshi.mekut.blockentity.interfaces.IRecipeViewerTypeProvider;
import com.takenokoshi.mekut.blockentity.interfaces.IScaledProgressProvider;

import fr.iglee42.evolvedmekanism.client.bars.GuiCustomDynamicHorizontalRateBar;
import mekanism.client.gui.GuiConfigurableTile;
import mekanism.client.gui.element.GuiInnerScreen;
import mekanism.client.gui.element.bar.GuiBar.IBarInfoHandler;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiChemicalGauge;
import mekanism.client.gui.element.gauge.GuiEnergyGauge;
import mekanism.client.gui.element.tab.GuiEnergyTab;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mekanism.common.MekanismLang;
import mekanism.common.lib.Color;
import mekanism.common.tile.base.TileEntityMekanism;
import mekanism.common.tile.interfaces.ISideConfiguration;
import mekanism.common.util.text.EnergyDisplay;
import mekanism.common.util.text.TextUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class GuiCompactAPT<BE extends TileEntityMekanism & ISideConfiguration & IHasGuiSizeOffset & IWarningSupporter & IHasMachineEnergyContainer & IHasInputChemicalTank & IScaledProgressProvider & IRecipeViewerTypeProvider>
        extends GuiConfigurableTile<BE, MekALDynamicSizedContainer<BE>> {

    public GuiCompactAPT(MekALDynamicSizedContainer<BE> container, Inventory inv, Component title) {
        super(container, inv, title);
        dynamicSlots = true;
        imageHeight += tile.getExtraHeight();
        inventoryLabelY += tile.getExtraHeight();
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addRenderableWidget(new GuiEnergyTab(this, tile.getEnergyContainer(), tile::getEnergyUsed));
        addRenderableWidget(new GuiChemicalGauge(() -> tile.getInputTank(), () -> tile.getChemicalTanks(null),
                GaugeType.SMALL, this, 7, 27));
        addRenderableWidget(new GuiCustomDynamicHorizontalRateBar(this, new IBarInfoHandler() {
            @Override
            public Component getTooltip() {
                return MekanismLang.PROGRESS.translate(TextUtils.getPercent(Math.max(0, tile.getScaledProgress())));
            }

            @Override
            public double getLevel() {
                return Math.max(0.0D, tile.getScaledProgress());
            }
        }, 7, 79, 160, progress -> Color.argb(tile.getInputTank().getStack().getChemicalTint())));

        addRenderableWidget(new GuiEnergyGauge(tile.getEnergyContainer(), GaugeType.SMALL, this, 151, 27));
        var screen = addRenderableWidget(new GuiInnerScreen(this, 47, 17, 82, 60, () -> {
            List<Component> list = new ArrayList<>();
            boolean active = tile.getActive();
            list.add(MekanismLang.STATUS.translate(active ? MekanismLang.ACTIVE : MekanismLang.IDLE));
            if (active) {
                list.add(MekanismLang.USING.translate(EnergyDisplay.of(tile.getEnergyUsed())));
            }
            return list;
        }));
        var type = tile.recipeViewerType();
        if (type != null) {
            screen.recipeViewerCategories(new IRecipeViewerRecipeType[] { type });
        }
    }

    @Override
    protected void drawForegroundText(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY) {
        renderTitleText(guiGraphics);
        renderInventoryText(guiGraphics);
        super.drawForegroundText(guiGraphics, mouseX, mouseY);
    }

}
