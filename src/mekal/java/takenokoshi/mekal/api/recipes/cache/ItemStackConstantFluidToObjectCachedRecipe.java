package takenokoshi.mekal.api.recipes.cache;

import java.util.function.BooleanSupplier;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.Predicate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import mekanism.api.functions.ConstantPredicates;
import mekanism.api.math.MathUtils;
import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.api.recipes.inputs.IInputHandler;
import mekanism.api.recipes.outputs.IOutputHandler;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import takenokoshi.mekal.api.recipes.ItemStackFluidToObjectRecipe;

public class ItemStackConstantFluidToObjectCachedRecipe<OUTPUT, RECIPE extends ItemStackFluidToObjectRecipe<OUTPUT>>
        extends CachedRecipe<RECIPE> {

    private final Predicate<OUTPUT> outputEmptyCheck;
    private final IOutputHandler<@NotNull OUTPUT> outputHandler;
    private final IInputHandler<@NotNull ItemStack> itemInputHandler;
    private final IInputHandler<@NotNull FluidStack> fluidInputHandler;
    private final FluidUsageMultiplier fluidUsage;
    private final IntConsumer fluidUsedSoFarChanged;

    private int fluidUsageMultiplier, fluidUsedSoFar;

    private ItemStack recipeItem = ItemStack.EMPTY;
    @Nullable
    private FluidStack recipeFluid;
    @Nullable
    private OUTPUT output;

    protected ItemStackConstantFluidToObjectCachedRecipe(RECIPE recipe, BooleanSupplier recheckAllErrors,
            IInputHandler<@NotNull ItemStack> itemInputHandler,
            IInputHandler<@NotNull FluidStack> fluidInputHandler,
            FluidUsageMultiplier fluidUsage,
            IntConsumer fluidUsedSoFarChanged,
            IOutputHandler<@NotNull OUTPUT> outputHandler,
            Predicate<OUTPUT> outputEmptyCheck) {
        super(recipe, recheckAllErrors);
        this.outputEmptyCheck = outputEmptyCheck;
        this.outputHandler = outputHandler;
        this.itemInputHandler = itemInputHandler;
        this.fluidInputHandler = fluidInputHandler;
        this.fluidUsage = fluidUsage;
        this.fluidUsedSoFarChanged = fluidUsedSoFarChanged;
    }

    public void loadSavedUsedSoFar(int fluidUsedSoFar) {
        if (fluidUsedSoFar > 0) {
            this.fluidUsedSoFar = 0;
        }
    }

    @Override
    protected void setupVariableValues() {
        fluidUsageMultiplier = Math.max(fluidUsage.getToUse(fluidUsedSoFar, getOperatingTicks()), 0);
    }

    @Override
    protected void calculateOperationsThisTick(OperationTracker tracker) {
        super.calculateOperationsThisTick(tracker);
        if (!tracker.shouldContinueChecking()) {
            return;
        }
        recipeItem = itemInputHandler.getRecipeInput(recipe.getItemInput());
        if (recipeItem.isEmpty()) {
            tracker.mismatchedRecipe();
            return;
        }
        recipeFluid = fluidInputHandler.getRecipeInput(recipe.getFluidInput());
        if (recipeFluid.isEmpty()) {
            tracker.updateOperations(0);
            if (!tracker.shouldContinueChecking()) {
                return;
            }
            itemInputHandler.calculateOperationsCanSupport(tracker, recipeItem);
            if (!recipeFluid.isEmpty() && tracker.shouldContinueChecking()) {
                fluidInputHandler.calculateOperationsCanSupport(tracker, recipeFluid, fluidUsageMultiplier);
            }
            if (tracker.shouldContinueChecking()) {
                output = recipe.getOutput(recipeItem, recipeFluid);
                outputHandler.calculateOperationsCanSupport(tracker, output);
            }
        }

    }

    @Override
    public boolean isInputValid() {
        ItemStack itemInput = itemInputHandler.getInput();
        if (itemInput.isEmpty()) {
            return false;
        }
        FluidStack fluidStack = fluidInputHandler.getInput();
        if (!fluidStack.isEmpty() && recipe.test(itemInput, fluidStack)) {
            FluidStack recipeFluid = fluidInputHandler.getRecipeInput(recipe.getFluidInput());
            return !recipeFluid.isEmpty() && fluidStack.getAmount() >= recipeFluid.getAmount();
        }
        return false;
    }

    @Override
    protected void useResources(int operations) {
        super.useResources(operations);
        if (fluidUsageMultiplier <= 0) {
            return;
        }
        if (recipeFluid == null || recipeFluid.isEmpty()) {
            return;
        }
        int toUse = operations * fluidUsageMultiplier;
        fluidInputHandler.use(recipeFluid, toUse);
        fluidUsedSoFar += toUse;
        fluidUsedSoFarChanged.accept(toUse);
    }

    @Override
    protected void resetCache() {
        super.resetCache();
        fluidUsedSoFar = 0;
        fluidUsedSoFarChanged.accept(fluidUsedSoFar);
    }

    @Override
    protected void finishProcessing(int operations) {
        if (recipeFluid == null || output == null || recipeItem.isEmpty() || recipeFluid.isEmpty()
                || outputEmptyCheck.test(output)) {
            return;
        }
        itemInputHandler.use(recipeItem, operations);
        if (fluidUsageMultiplier > 0) {
            fluidInputHandler.use(recipeFluid, operations * fluidUsageMultiplier);
        }
        outputHandler.handleOutput(output, operations);
    }

    @FunctionalInterface
    public static interface FluidUsageMultiplier {
        int getToUse(int usedSoFar, int operatingTicks);

        static FluidUsageMultiplier constantUse(IntSupplier baseTotalUsage, IntSupplier ticksRequired) {
            return (usedSoFar, operatingTicks) -> {
                int baseRemaining = baseTotalUsage.getAsInt() - usedSoFar;
                int remainingTicks = ticksRequired.getAsInt() - operatingTicks;
                if (baseRemaining < remainingTicks) {
                    return 0;
                } else if (baseRemaining == remainingTicks) {
                    return 1;
                }
                return Math.max(MathUtils.clampToInt(baseRemaining / (double) remainingTicks), 0);
            };
        }
    }

    public static <RECIPE extends ItemStackFluidToObjectRecipe<ItemStack>> ItemStackConstantFluidToObjectCachedRecipe<ItemStack, RECIPE> toItem(
            RECIPE recipe, BooleanSupplier recheckAllErrors,
            IInputHandler<@NotNull ItemStack> itemInputHandler,
            IInputHandler<@NotNull FluidStack> fluidInputHandler,
            FluidUsageMultiplier fluidUsage,
            IntConsumer fluidUsedSoFarChanged,
            IOutputHandler<ItemStack> outputHandler) {
        return new ItemStackConstantFluidToObjectCachedRecipe<>(recipe, recheckAllErrors, itemInputHandler,
                fluidInputHandler, fluidUsage, fluidUsedSoFarChanged, outputHandler, ConstantPredicates.ITEM_EMPTY);
    }

}
