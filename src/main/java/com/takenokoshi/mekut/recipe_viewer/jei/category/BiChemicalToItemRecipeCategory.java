package com.takenokoshi.mekut.recipe_viewer.jei.category;

import com.takenokoshi.mekut.recipe.recipe.prefab.BiChemicalToItemRecipe;

import mekanism.client.gui.element.bar.GuiHorizontalPowerBar;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiChemicalGauge;
import mekanism.client.gui.element.gauge.GuiGauge;
import mekanism.client.gui.element.progress.GuiProgress;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.slot.GuiSlot;
import mekanism.client.gui.element.slot.SlotType;
import mekanism.client.recipe_viewer.RecipeViewerUtils;
import mekanism.client.recipe_viewer.jei.HolderRecipeCategory;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.tile.component.config.DataType;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.world.item.crafting.RecipeHolder;

public class BiChemicalToItemRecipeCategory extends HolderRecipeCategory<BiChemicalToItemRecipe> {

    protected static final String LEFT_INPUT = "leftInput";
    protected static final String RIGHT_INPUT = "rightInput";
    protected static final String OUTPUT = "output";

    private final GuiSlot outputItem;
    private final GuiGauge<?> leftInputGauge;
    private final GuiGauge<?> rightInputGauge;
    protected final GuiProgress rightArrow;
    protected final GuiProgress leftArrow;

    public BiChemicalToItemRecipeCategory(IGuiHelper helper,
            IRecipeViewerRecipeType<BiChemicalToItemRecipe> recipeType) {
        super(helper, recipeType);
        this.leftInputGauge = this
                .addElement(GuiChemicalGauge.getDummy(GaugeType.STANDARD.with(DataType.INPUT_1), this, 25, 13));
        this.rightInputGauge = this
                .addElement(GuiChemicalGauge.getDummy(GaugeType.STANDARD.with(DataType.INPUT_2), this, 133, 13));
        this.addSlot(SlotType.INPUT, 6, 56).with(SlotOverlay.MINUS);
        this.addSlot(SlotType.INPUT_2, 154, 56).with(SlotOverlay.MINUS);
        outputItem = this.addSlot(SlotType.OUTPUT, 80, 36);
        this.addSlot(SlotType.POWER, 154, 14).with(SlotOverlay.POWER);
        this.rightArrow = this.addSimpleProgress(ProgressType.SMALL_RIGHT, 47, 39);
        this.leftArrow = this.addSimpleProgress(ProgressType.SMALL_LEFT, 101, 39);
        this.addElement(new GuiHorizontalPowerBar(this, RecipeViewerUtils.FULL_BAR, 115, 75));
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<BiChemicalToItemRecipe> holder,
            IFocusGroup group) {
        BiChemicalToItemRecipe recipe = holder.value();
        initChemical(builder, RecipeIngredientRole.INPUT, leftInputGauge, recipe.getLeftInput().getRepresentations());
        initChemical(builder, RecipeIngredientRole.INPUT, rightInputGauge, recipe.getRightInput().getRepresentations());
        initItem(builder, RecipeIngredientRole.OUTPUT, outputItem, recipe.getOutputDefinition());
        builder.setShapeless();
    }

}
