package takenokoshi.mekal.api.recipes;

import java.util.List;
import java.util.function.Predicate;

import org.jetbrains.annotations.Contract;

import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.vanilla_input.SingleFluidRecipeInput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;

public abstract class FluidToItemStackRecipe extends MekanismRecipe<SingleFluidRecipeInput>
        implements Predicate<FluidStack> {

    @Override
    public abstract boolean test(FluidStack fluidStack);

    @Override
    public boolean matches(SingleFluidRecipeInput input, Level level) {
        return !isIncomplete() && test(input.fluid());
    }

    public abstract FluidStackIngredient getInput();

    public abstract List<ItemStack> getOutputDefinition();

    @Contract(value = "_ ->new", pure = true)
    public abstract ItemStack getOutput(FluidStack input);

    @Override
    public boolean isIncomplete() {
        return getInput().hasNoMatchingInstances();
    }

    @Override
    public void logMissingTags() {
        getInput().logMissingTags();
    }
}
