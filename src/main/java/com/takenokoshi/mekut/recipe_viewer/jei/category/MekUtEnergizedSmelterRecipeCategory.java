package com.takenokoshi.mekut.recipe_viewer.jei.category;

import java.util.List;

import com.takenokoshi.mekut.registries.MekUtChemicals;

import mekanism.api.math.MathUtils;
import mekanism.client.gui.element.GuiUpArrow;
import mekanism.client.gui.element.bar.GuiBar;
import mekanism.client.gui.element.bar.GuiEmptyBar;
import mekanism.client.gui.element.bar.GuiVerticalPowerBar;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.slot.GuiSlot;
import mekanism.client.gui.element.slot.SlotType;
import mekanism.client.recipe_viewer.RecipeViewerUtils;
import mekanism.client.recipe_viewer.jei.HolderRecipeCategory;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;

public class MekUtEnergizedSmelterRecipeCategory<RECIPE extends AbstractCookingRecipe> extends HolderRecipeCategory<RECIPE> {

    private static final String XP = "xp";

    private final GuiSlot input;
    private final GuiSlot output;
    private final GuiBar<?> xpBar;

    public MekUtEnergizedSmelterRecipeCategory(IGuiHelper helper,
            IRecipeViewerRecipeType<RECIPE> recipeType) {
        super(helper, recipeType);
        addElement(new GuiUpArrow(this, 68, 38));
        input = addSlot(SlotType.INPUT, 64, 17);
        output = addSlot(SlotType.OUTPUT, 116, 35);
        addSlot(SlotType.POWER, 64, 53).with(SlotOverlay.POWER);
        addElement(new GuiVerticalPowerBar(this, RecipeViewerUtils.FULL_BAR, 164, 16));
        addSimpleProgress(ProgressType.BAR, 86, 38);
        xpBar = addElement(new GuiEmptyBar(this, 100, 60, 50, 4));
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<RECIPE> holder,
            IFocusGroup focusGroup) {
        AbstractCookingRecipe recipe = holder.value();
        initItem(builder, RecipeIngredientRole.INPUT, input, List.of(recipe.getIngredients().get(0).getItems()));
        initItem(builder, RecipeIngredientRole.OUTPUT, output, List.of(recipe.getResultItem(null)));
        long xpValue = MathUtils.clampToLong(recipe.getExperience() * 100);
        if (xpValue > 0) {
            initChemical(builder, RecipeIngredientRole.OUTPUT, xpBar, List.of(MekUtChemicals.XP.asStack(xpValue)))
                    .setSlotName(XP);
        }
    }

}
