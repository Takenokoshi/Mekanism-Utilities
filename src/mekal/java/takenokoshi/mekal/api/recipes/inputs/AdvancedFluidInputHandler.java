package takenokoshi.mekal.api.recipes.inputs;

import org.jetbrains.annotations.NotNull;

import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.ingredients.InputIngredient;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.inputs.InputHelper;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import takenokoshi.mekal.common.util.MekALFluidUtils;

public class AdvancedFluidInputHandler implements IInputHandler<FluidStack> {

    @NotNull
    protected final IInputHandler<FluidStack> delegate;

    @NotNull
    protected FluidStack suppliedByItem = FluidStack.EMPTY;

    public static AdvancedFluidInputHandler create(IExtendedFluidTank fluidTank, RecipeError notEnoughError) {
        return new AdvancedFluidInputHandler(InputHelper.getInputHandler(fluidTank, notEnoughError));
    }

    public AdvancedFluidInputHandler(@NotNull IInputHandler<FluidStack> delegate) {
        this.delegate = delegate;
    }

    public void setSuppliedByItem(@NotNull FluidStack suppliedByItem) {
        this.suppliedByItem = suppliedByItem;
    }

    public void setSuppliedByItem(@NotNull ItemStack itemStack) {
        this.suppliedByItem = itemStack.isEmpty()
                ? FluidStack.EMPTY
                : MekALFluidUtils.getSuppliedFluid(itemStack.getItem());
    }

    @Override
    public FluidStack getInput() {
        final FluidStack delegateFluid = delegate.getInput();
        return delegateFluid.isEmpty() ? suppliedByItem : delegateFluid;
    }

    @Override
    public FluidStack getRecipeInput(InputIngredient<FluidStack> ingredient) {
        final FluidStack delegateFluid = delegate.getRecipeInput(ingredient);
        return delegateFluid.isEmpty() ? ingredient.getMatchingInstance(suppliedByItem) : delegateFluid;
    }

    @Override
    public void calculateOperationsCanSupport(OperationTracker tracker, FluidStack stack, int usageMultiplier) {
        if (stack.isEmpty()) {
            return;
        }
        if (!suppliedByItem.isEmpty() && FluidStack.isSameFluidSameComponents(stack, suppliedByItem)) {
            return;
        }
        delegate.calculateOperationsCanSupport(tracker, stack, usageMultiplier);
    }

    @Override
    public void use(FluidStack stack, int operations) {
        if (stack.isEmpty()) {
            return;
        }
        if (!suppliedByItem.isEmpty() && FluidStack.isSameFluidSameComponents(stack, suppliedByItem)) {
            return;
        }
        delegate.use(stack, operations);
    }
}
