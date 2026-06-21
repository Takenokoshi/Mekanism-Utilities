package com.takenokoshi.mekut.recipe_viewer.jei.category;

import java.util.List;

import com.takenokoshi.mekut.recipe.recipe.prefab.ItemStackListFluidChemicalToItemFluidChemicalRecipe;

import mekanism.client.gui.element.bar.GuiVerticalPowerBar;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiChemicalGauge;
import mekanism.client.gui.element.gauge.GuiFluidGauge;
import mekanism.client.gui.element.gauge.GuiGauge;
import mekanism.client.gui.element.progress.ProgressType;
import mekanism.client.gui.element.slot.GuiSlot;
import mekanism.client.gui.element.slot.SlotType;
import mekanism.client.recipe_viewer.RecipeViewerUtils;
import mekanism.client.recipe_viewer.jei.HolderRecipeCategory;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.common.tile.component.config.DataType;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.crafting.RecipeHolder;

public class SmallDigitalReactionChamberRecipeCategory
        extends HolderRecipeCategory<ItemStackListFluidChemicalToItemFluidChemicalRecipe> {

    private static final String INPUT_CHEMICAL = "input_chemical";
    private static final String INPUT_FLUID = "input_fluid";
    private static final String OUTPUT_CHEMICAL = "output_chemical";
    private static final String OUTPUT_FLUID = "output_fluid";

    private final GuiGauge<?> inputChemical;
    private final GuiGauge<?> inputFluid;
    private final GuiSlot[] inputItems;
    private final GuiSlot outputItem;
    private final GuiGauge<?> outputChemical;
    private final GuiGauge<?> ouputFluid;

    public SmallDigitalReactionChamberRecipeCategory(IGuiHelper helper,
            IRecipeViewerRecipeType<ItemStackListFluidChemicalToItemFluidChemicalRecipe> recipeType) {
        super(helper, recipeType);
        GaugeType type1 = GaugeType.SMALL.with(DataType.INPUT);
        inputChemical = addElement(GuiChemicalGauge.getDummy(type1, this, 28, 27));
        inputFluid = addElement(GuiFluidGauge.getDummy(type1, this, 5, 27));
        inputItems = new GuiSlot[9];
        for (int index = 0; index < inputItems.length; index++) {
            inputItems[index] = addSlot(SlotType.INPUT, 54 + index % 3 * 18, 22 + index / 3 * 18);
        }
        outputItem = addSlot(SlotType.OUTPUT, 152, 40);
        GaugeType type2 = GaugeType.SMALL.with(DataType.OUTPUT);
        outputChemical = addElement(GuiChemicalGauge.getDummy(type2, this, 176, 27));
        ouputFluid = addElement(GuiFluidGauge.getDummy(type2, this, 199, 27));
        addElement(new GuiVerticalPowerBar(this, RecipeViewerUtils.FULL_BAR, 222, 21));
        addSimpleProgress(ProgressType.RIGHT, 113, 43);
        addSlot(SlotType.EXTRA, 6, 58).with(SlotOverlay.MINUS);
        addSlot(SlotType.EXTRA, 29, 58).with(SlotOverlay.MINUS);
        addSlot(SlotType.EXTRA, 177, 58).with(SlotOverlay.PLUS);
        addSlot(SlotType.EXTRA, 200, 58).with(SlotOverlay.PLUS);
    }

    @Override
    protected void renderElements(RecipeHolder<ItemStackListFluidChemicalToItemFluidChemicalRecipe> recipe,
            IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, int x, int y) {
        super.renderElements(recipe, recipeSlotsView, guiGraphics, x, y);
        if (recipeSlotsView.findSlotByName(INPUT_CHEMICAL).isEmpty()) {
            inputChemical.drawBarOverlay(guiGraphics);
        }
        if (recipeSlotsView.findSlotByName(INPUT_FLUID).isEmpty()) {
            inputFluid.drawBarOverlay(guiGraphics);
        }
        if (recipeSlotsView.findSlotByName(OUTPUT_CHEMICAL).isEmpty()) {
            outputChemical.drawBarOverlay(guiGraphics);
        }
        if (recipeSlotsView.findSlotByName(OUTPUT_FLUID).isEmpty()) {
            ouputFluid.drawBarOverlay(guiGraphics);
        }
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder,
            RecipeHolder<ItemStackListFluidChemicalToItemFluidChemicalRecipe> holder, IFocusGroup focusGroup) {
        ItemStackListFluidChemicalToItemFluidChemicalRecipe recipe = holder.value();
        recipe.getChemicalInputAsOptional().ifPresent(ingredient -> initChemical(
                builder, RecipeIngredientRole.INPUT, inputChemical, ingredient.getRepresentations())
                .setSlotName(INPUT_CHEMICAL));
        recipe.getFluidInputAsOptional().ifPresent(ingredient -> initFluid(
                builder, RecipeIngredientRole.INPUT, inputFluid, ingredient.getRepresentations())
                .setSlotName(INPUT_FLUID));
        final var itemIngredients = recipe.getItemInputs();
        for (int i = 0; i < itemIngredients.size(); i++) {
            initItem(builder, RecipeIngredientRole.INPUT, inputItems[i], itemIngredients.get(i).getRepresentations());
        }
        if (!recipe.outputChemical.isEmpty()) {
            initChemical(builder, RecipeIngredientRole.OUTPUT, outputChemical, List.of(recipe.outputChemical.copy()))
                    .setSlotName(OUTPUT_CHEMICAL);
        }
        if (!recipe.outputFluid.isEmpty()) {
            initFluid(builder, RecipeIngredientRole.OUTPUT, ouputFluid, List.of(recipe.outputFluid.copy()))
                    .setSlotName(OUTPUT_FLUID);
        }
        if (!recipe.outputItem.isEmpty()) {
            initItem(builder, RecipeIngredientRole.OUTPUT, outputItem, List.of(recipe.outputItem.copy()));
        }
    }

}
