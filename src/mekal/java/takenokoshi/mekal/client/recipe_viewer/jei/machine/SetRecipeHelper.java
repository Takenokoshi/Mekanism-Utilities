package takenokoshi.mekal.client.recipe_viewer.jei.machine;

import java.util.List;

import mekanism.client.gui.element.GuiElement;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.neoforge.NeoForgeTypes;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.neoforged.neoforge.fluids.FluidStack;

public class SetRecipeHelper {

    public static void initFluid(IRecipeLayoutBuilder builder, RecipeIngredientRole role, GuiElement element,
            List<FluidStack> stacks) {
        int max = stacks.stream().mapToInt(FluidStack::getAmount).filter((stackSize) -> stackSize > 0).max()
                        .orElse(1000);
        builder.addSlot(role, element.getX() + 1, element.getY() + 1).addIngredients(NeoForgeTypes.FLUID_STACK, stacks).setFluidRenderer(max, false,
                element.getWidth() - 2, element.getHeight() - 2);
    }
}
