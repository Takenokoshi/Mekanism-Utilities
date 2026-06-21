package com.takenokoshi.mekut.recipe_viewer.jei.category;

import com.takenokoshi.mekut.recipe.recipe.prefab.FluidToItemRecipe;

import mekanism.client.gui.element.GuiDownArrow;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiFluidGauge;
import mekanism.client.gui.element.gauge.GuiGauge;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.slot.GuiSlot;
import mekanism.client.gui.element.slot.SlotType;
import mekanism.client.recipe_viewer.jei.HolderRecipeCategory;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.tile.component.config.DataType;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.world.item.crafting.RecipeHolder;

public class FluidToItemRecipeCategory extends HolderRecipeCategory<FluidToItemRecipe> {
    private final GuiGauge<?> inputFluid;
    private final GuiSlot outputItem;

    public FluidToItemRecipeCategory(IGuiHelper helper, IRecipeViewerRecipeType<FluidToItemRecipe> recipeType) {
        super(helper, recipeType);
        inputFluid = addElement(GuiFluidGauge.getDummy(GaugeType.STANDARD.with(DataType.INPUT), this, 25, 22));
        outputItem = this.addSlot(SlotType.OUTPUT, 129, 57);
        addSlot(SlotType.INPUT, 8, 34).with(SlotOverlay.MINUS);
        addSlot(SlotType.EXTRA, 8, 65);
        addElement(new GuiDownArrow(this, 12, 53));
        addSimpleProgress(ProgressType.LARGE_RIGHT, 53, 61);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<FluidToItemRecipe> holder,
            IFocusGroup focusGroup) {
        var recipe = holder.value();
        initFluid(builder, RecipeIngredientRole.INPUT, inputFluid, recipe.getInput().getRepresentations());
        initItem(builder, RecipeIngredientRole.OUTPUT, outputItem, recipe.getOutputDefinition());
    }

}
