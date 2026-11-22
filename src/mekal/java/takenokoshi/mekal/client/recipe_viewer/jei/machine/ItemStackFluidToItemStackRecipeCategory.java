package takenokoshi.mekal.client.recipe_viewer.jei.machine;

import java.util.List;

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
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.neoforge.fluids.FluidStack;
import takenokoshi.mekal.api.recipes.ItemStackFluidToItemStackRecipe;
import takenokoshi.mekal.common.tile.prefab.TileEntityItemStackFluidToItemStackMachine;

public class ItemStackFluidToItemStackRecipeCategory extends HolderRecipeCategory<ItemStackFluidToItemStackRecipe> {

    private final GuiBar<?> fluidInput;
    private final GuiSlot input;
    private final GuiSlot output;

    public ItemStackFluidToItemStackRecipeCategory(IGuiHelper helper,
            IRecipeViewerRecipeType<ItemStackFluidToItemStackRecipe> recipeType) {
        super(helper, recipeType);
        this.input = addSlot(SlotType.INPUT, 64, 17);
        addSlot(SlotType.EXTRA, 64, 53);
        this.output = addSlot(SlotType.OUTPUT, 116, 35);
        addSlot(SlotType.NORMAL, 90, 53);
        addSlot(SlotType.POWER, 39, 35).with(SlotOverlay.POWER);
        addElement(new GuiVerticalPowerBar(this, RecipeViewerUtils.FULL_BAR, 164, 16));
        this.fluidInput = addElement(new GuiEmptyBar(this, 68, 36, 6, 12));
        addSimpleProgress(ProgressType.BAR, 86, 38);

    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<ItemStackFluidToItemStackRecipe> recipeHolder,
            IFocusGroup focusGroup) {
        ItemStackFluidToItemStackRecipe recipe = recipeHolder.value();
        initItem(builder, RecipeIngredientRole.INPUT, input, recipe.getItemInput().getRepresentations());
        List<FluidStack> scaledFluids = recipe.getFluidInput().getRepresentations();
        if (recipe.perTickUsage()) {
            scaledFluids = scaledFluids.stream().map(fluid -> fluid.copyWithAmount(fluid.getAmount() * getBaseTicks()))
                    .toList();
        }
        SetRecipeHelper.initFluid(builder, RecipeIngredientRole.INPUT, fluidInput, scaledFluids);
        initItem(builder, RecipeIngredientRole.OUTPUT, output, recipe.getOutputDefinition());
    }

    protected int getBaseTicks() {
        return TileEntityItemStackFluidToItemStackMachine.BASE_TICKS_REQUIRED;
    }

}
