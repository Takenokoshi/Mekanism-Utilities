package com.takenokoshi.mekut.recipe_viewer.jei.category;

import java.util.List;

import com.takenokoshi.mekut.recipe.recipe.prefab.GreenHouseRecipe;

import mekanism.client.gui.element.GuiDownArrow;
import mekanism.client.gui.element.bar.GuiVerticalPowerBar;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiFluidGauge;
import mekanism.client.gui.element.gauge.GuiGauge;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.slot.GuiSlot;
import mekanism.client.gui.element.slot.SlotType;
import mekanism.client.recipe_viewer.RecipeViewerUtils;
import mekanism.client.recipe_viewer.jei.HolderRecipeCategory;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mekanism.common.util.text.TextUtils;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.RecipeHolder;

public class GreenHouseRecipeCategory extends HolderRecipeCategory<GreenHouseRecipe> {

    private final GuiSlot crop;
    private final GuiSlot soil;
    private final GuiGauge<?> fertilizer;
    private final GuiSlot[] outputs;

    public GreenHouseRecipeCategory(IGuiHelper helper, IRecipeViewerRecipeType<GreenHouseRecipe> recipeType) {
        super(helper, recipeType);
        this.crop = addSlot(SlotType.INPUT, 64, 17);
        this.soil = addSlot(SlotType.INPUT, 64, 53);
        this.fertilizer = addElement(GuiFluidGauge.getDummy(GaugeType.STANDARD, this, 40, 10));
        this.outputs = new GuiSlot[12];
        for (int index = 0; index < outputs.length; index++) {
            outputs[index] = addSlot(SlotType.OUTPUT, index % 4 * 18 + 116, index / 4 * 18 + 17);
        }
        addSlot(SlotType.POWER, 197, 35);
        addElement(new GuiVerticalPowerBar(this, RecipeViewerUtils.FULL_BAR, 218, 15));
        addElement(new GuiDownArrow(this, 68, 38));
        addSimpleProgress(ProgressType.BAR, 86, 38);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder boulder, RecipeHolder<GreenHouseRecipe> holder, IFocusGroup focusGroup) {
        GreenHouseRecipe recipe = holder.value();
        initItem(boulder, RecipeIngredientRole.CATALYST, crop, recipe.cropIngredient.getRepresentations());
        initItem(boulder, RecipeIngredientRole.CATALYST, soil, recipe.soilIngredient.getRepresentations());
        initFluid(boulder, RecipeIngredientRole.INPUT, fertilizer, recipe.fertilizerIngredient.getRepresentations());
        for (int i = 0; i < Math.min(outputs.length, recipe.outputs.size()); i++) {
            int p = i;
            initItem(boulder, RecipeIngredientRole.OUTPUT, outputs[i], List.of(recipe.outputs.get(i).value()))
                    .addRichTooltipCallback((recipeSlotView, tooltip) -> {
                        tooltip.add(Component
                                .literal("Chance:" + TextUtils.format(recipe.outputs.get(p).chance() * 100) + "%"));
                    });
        }
    }

}
