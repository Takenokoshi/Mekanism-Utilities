package takenokoshi.mekal.api.recipes;

import java.util.List;
import java.util.function.BiPredicate;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import takenokoshi.mekal.api.recipes.vanilla_input.SingleItemFluidRecipeInput;

public abstract class ItemStackFluidToObjectRecipe<OUTPUT> extends MekanismRecipe<SingleItemFluidRecipeInput>
        implements BiPredicate<ItemStack, FluidStack> {

    public abstract boolean perTickUsage();

    public abstract ItemStackIngredient getItemInput();

    public abstract FluidStackIngredient getFluidInput();

    @Contract(value = "_, _ -> new", pure = true)
    public abstract OUTPUT getOutput(ItemStack inputItem, FluidStack inputFluid);

    @Override
    public abstract boolean test(ItemStack itemStack, FluidStack fluidStack);

    @Override
    public boolean matches(SingleItemFluidRecipeInput input, Level level) {
        return !isIncomplete() && test(input.item(), input.fluid());
    }

    public abstract List<@NotNull OUTPUT> getOutputDefinition();

    @Override
    public boolean isIncomplete() {
        return getItemInput().hasNoMatchingInstances() || getFluidInput().hasNoMatchingInstances();
    }

    @Override
    public void logMissingTags() {
        getItemInput().logMissingTags();
        getFluidInput().logMissingTags();
    }
}
