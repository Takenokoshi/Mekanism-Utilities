package com.takenokoshi.mekut.recipe_viewer.jei.category;

import com.takenokoshi.mekut.recipe.recipe.prefab.ChemicalToChemicalHeatRecipe;

import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiChemicalGauge;
import mekanism.client.gui.element.gauge.GuiGauge;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.recipe_viewer.jei.HolderRecipeCategory;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mekanism.common.tile.component.config.DataType;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.world.item.crafting.RecipeHolder;

public class FissionReactorRecipeCategory extends HolderRecipeCategory<ChemicalToChemicalHeatRecipe> {

    private final GuiGauge<?> input;
    private final GuiGauge<?> output;

    public FissionReactorRecipeCategory(IGuiHelper helper,
            IRecipeViewerRecipeType<ChemicalToChemicalHeatRecipe> recipeType) {
        super(helper, recipeType);
        GaugeType type1 = GaugeType.STANDARD.with(DataType.INPUT);
        this.input = this.addElement(GuiChemicalGauge.getDummy(type1, this, 25, 13));
        GaugeType type = GaugeType.STANDARD.with(DataType.OUTPUT);
        this.output = this.addElement(GuiChemicalGauge.getDummy(type, this, 133, 13));
        this.addConstantProgress(ProgressType.LARGE_RIGHT, 64, 39);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<ChemicalToChemicalHeatRecipe> recipeHolder,
            IFocusGroup focusGroup) {
        ChemicalToChemicalHeatRecipe recipe = recipeHolder.value();
        this.initChemical(builder, RecipeIngredientRole.INPUT, this.input, recipe.getInput().getRepresentations());
        this.initChemical(builder, RecipeIngredientRole.OUTPUT, this.output, recipe.getOutputDefinition());
    }

}
