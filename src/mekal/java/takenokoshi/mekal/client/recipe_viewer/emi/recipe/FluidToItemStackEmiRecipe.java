package takenokoshi.mekal.client.recipe_viewer.emi.recipe;

import dev.emi.emi.api.widget.WidgetHolder;
import mekanism.client.gui.element.gauge.GaugeType;
import mekanism.client.gui.element.gauge.GuiFluidGauge;
import mekanism.client.gui.element.slot.SlotType;
import mekanism.client.recipe_viewer.emi.MekanismEmiRecipeCategory;
import mekanism.client.recipe_viewer.emi.recipe.MekanismEmiHolderRecipe;
import mekanism.common.tile.component.config.DataType;
import net.minecraft.world.item.crafting.RecipeHolder;
import takenokoshi.mekal.api.recipes.FluidToItemStackRecipe;

public class FluidToItemStackEmiRecipe extends MekanismEmiHolderRecipe<FluidToItemStackRecipe> {

    public FluidToItemStackEmiRecipe(MekanismEmiRecipeCategory category,
            RecipeHolder<FluidToItemStackRecipe> recipeHolder) {
        super(category, recipeHolder);
        addInputDefinition(recipe.getInput());
        addItemOutputDefinition(recipe.getOutputDefinition());
    }

    @Override
    public void addWidgets(WidgetHolder widgetHolder) {
        initTank(widgetHolder, GuiFluidGauge.getDummy(GaugeType.STANDARD.with(DataType.INPUT), this, 25, 22), input(0));
        addSlot(widgetHolder, SlotType.OUTPUT,  129, 57, output(0));
    }
    
}
