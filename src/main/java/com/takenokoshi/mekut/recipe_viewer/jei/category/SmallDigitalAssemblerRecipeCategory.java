package com.takenokoshi.mekut.recipe_viewer.jei.category;

import java.util.List;

import com.takenokoshi.mekut.recipe.recipe.prefab.ItemStackListFluidChemicalToItemRecipe;

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

public class SmallDigitalAssemblerRecipeCategory extends HolderRecipeCategory<ItemStackListFluidChemicalToItemRecipe> {

    private static final String INPUT_CHEMICAL = "input_chemical";
    private static final String INPUT_FLUID = "input_fluid";

    private final GuiGauge<?> inputChemical;
    private final GuiGauge<?> inputFluid;
    private final GuiSlot[] inputItems;
    private final GuiSlot outputItem;

    public SmallDigitalAssemblerRecipeCategory(IGuiHelper helper,
            IRecipeViewerRecipeType<ItemStackListFluidChemicalToItemRecipe> recipeType) {
        super(helper, recipeType);
        GaugeType type1 = GaugeType.SMALL.with(DataType.INPUT);
        inputChemical = addElement(GuiChemicalGauge.getDummy(type1, this, 28, 27));
        inputFluid = addElement(GuiFluidGauge.getDummy(type1, this, 5, 27));
        inputItems = new GuiSlot[9];
        for (int index = 0; index < inputItems.length; index++) {
            inputItems[index] = addSlot(SlotType.INPUT, 54 + index % 3 * 18, 22 + index / 3 * 18);
        }
        outputItem = addSlot(SlotType.OUTPUT, 152, 40);
        addElement(new GuiVerticalPowerBar(this, RecipeViewerUtils.FULL_BAR, 199, 21));
        addSimpleProgress(ProgressType.RIGHT, 113, 43);
        addSlot(SlotType.POWER, 177, 22).with(SlotOverlay.POWER);
        addSlot(SlotType.EXTRA, 6, 58).with(SlotOverlay.MINUS);
        addSlot(SlotType.EXTRA, 29, 58).with(SlotOverlay.MINUS);
    }

    @Override
    protected void renderElements(RecipeHolder<ItemStackListFluidChemicalToItemRecipe> recipe,
            IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, int x, int y) {
        super.renderElements(recipe, recipeSlotsView, guiGraphics, x, y);
        if (recipeSlotsView.findSlotByName(INPUT_CHEMICAL).isEmpty()) {
            inputChemical.drawBarOverlay(guiGraphics);
        }
        if (recipeSlotsView.findSlotByName(INPUT_FLUID).isEmpty()) {
            inputFluid.drawBarOverlay(guiGraphics);
        }
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<ItemStackListFluidChemicalToItemRecipe> holder,
            IFocusGroup group) {
        ItemStackListFluidChemicalToItemRecipe recipe = holder.value();
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
        initItem(builder, RecipeIngredientRole.OUTPUT, outputItem, List.of(recipe.outputItem.copy()));
    }

}
