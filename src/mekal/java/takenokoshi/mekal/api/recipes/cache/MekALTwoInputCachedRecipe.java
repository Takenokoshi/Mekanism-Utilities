package takenokoshi.mekal.api.recipes.cache;

import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

import mekanism.api.functions.ConstantPredicates;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.cache.TwoInputCachedRecipe;
import mekanism.api.recipes.ingredients.InputIngredient;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.outputs.IOutputHandler;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import takenokoshi.mekal.api.recipes.ItemStackFluidToItemStackRecipe;

public class MekALTwoInputCachedRecipe<INPUT_A, INPUT_B, OUTPUT, RECIPE extends MekanismRecipe<?> & BiPredicate<INPUT_A, INPUT_B>>
        extends TwoInputCachedRecipe<INPUT_A, INPUT_B, OUTPUT, RECIPE> {

    protected MekALTwoInputCachedRecipe(RECIPE recipe, BooleanSupplier recheckAllErrors,
            IInputHandler<INPUT_A> inputHandler, IInputHandler<INPUT_B> secondaryInputHandler,
            IOutputHandler<OUTPUT> outputHandler, Supplier<InputIngredient<INPUT_A>> inputSupplier,
            Supplier<InputIngredient<INPUT_B>> secondaryInputSupplier,
            BiFunction<INPUT_A, INPUT_B, OUTPUT> outputGetter, Predicate<INPUT_A> inputEmptyCheck,
            Predicate<INPUT_B> secondaryInputEmptyCheck, Predicate<OUTPUT> outputEmptyCheck) {
        super(recipe, recheckAllErrors, inputHandler, secondaryInputHandler, outputHandler, inputSupplier,
                secondaryInputSupplier, outputGetter, inputEmptyCheck, secondaryInputEmptyCheck, outputEmptyCheck);
    }

    public static <RECIPE extends ItemStackFluidToItemStackRecipe> TwoInputCachedRecipe<ItemStack, FluidStack, ItemStack, ItemStackFluidToItemStackRecipe> itemFluidToItem(
            RECIPE recipe, BooleanSupplier recheckAllErrors, IInputHandler<@NotNull ItemStack> itemInputHandler,
            IInputHandler<@NotNull FluidStack> fluidInputHandler, IOutputHandler<@NotNull ItemStack> outputHandler) {
        return new MekALTwoInputCachedRecipe<ItemStack, FluidStack, ItemStack, ItemStackFluidToItemStackRecipe>(recipe,
                recheckAllErrors, itemInputHandler, fluidInputHandler, outputHandler,
                recipe::getItemInput, recipe::getFluidInput, recipe::getOutput, ConstantPredicates.ITEM_EMPTY,
                ConstantPredicates.FLUID_EMPTY, ConstantPredicates.ITEM_EMPTY);
    }

}
