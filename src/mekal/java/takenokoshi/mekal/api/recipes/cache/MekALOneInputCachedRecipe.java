package takenokoshi.mekal.api.recipes.cache;

import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import mekanism.api.functions.ConstantPredicates;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.cache.OneInputCachedRecipe;
import mekanism.api.recipes.ingredients.InputIngredient;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.outputs.IOutputHandler;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import takenokoshi.mekal.api.recipes.FluidToItemStackRecipe;

public class MekALOneInputCachedRecipe<INPUT, OUTPUT, RECIPE extends MekanismRecipe<?> & Predicate<INPUT>>
        extends OneInputCachedRecipe<INPUT, OUTPUT, RECIPE> {

    protected MekALOneInputCachedRecipe(RECIPE recipe, BooleanSupplier recheckAllErrors,
            IInputHandler<INPUT> inputHandler, IOutputHandler<OUTPUT> outputHandler,
            Supplier<? extends InputIngredient<INPUT>> inputSupplier, Function<INPUT, OUTPUT> outputGetter,
            Predicate<INPUT> inputEmptyCheck, Predicate<OUTPUT> outputEmptyCheck) {
        super(recipe, recheckAllErrors, inputHandler, outputHandler, inputSupplier, outputGetter, inputEmptyCheck,
                outputEmptyCheck);
    }

    public static <RECIPE extends FluidToItemStackRecipe> MekALOneInputCachedRecipe<FluidStack, ItemStack, RECIPE> fluidToItem(
            RECIPE recipe, BooleanSupplier recheckAllErrors, IInputHandler<FluidStack> inputHandler,
            IOutputHandler<ItemStack> outputHandler) {
        return new MekALOneInputCachedRecipe<>(recipe, recheckAllErrors, inputHandler, outputHandler,
                recipe::getInput, recipe::getOutput, ConstantPredicates.FLUID_EMPTY, ConstantPredicates.ITEM_EMPTY);
    }

}
