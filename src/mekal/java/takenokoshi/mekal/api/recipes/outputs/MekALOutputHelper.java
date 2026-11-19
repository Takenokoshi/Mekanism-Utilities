package takenokoshi.mekal.api.recipes.outputs;

import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.api.inventory.IInventorySlot;
import mekanism.api.math.MathUtils;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.outputs.IOutputHandler;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;

public class MekALOutputHelper {

    public static IOutputHandler<DoubleChemicalRecipeOutput> getOutputHandler(
            IChemicalTank mainTank, RecipeError mainNotEnoughSpaceError,
            IChemicalTank secondaryTank, RecipeError secondaryNotEnoughSpaceError) {
        return new IOutputHandler<DoubleChemicalRecipeOutput>() {

            @Override
            public void handleOutput(DoubleChemicalRecipeOutput output, int operations) {
                MekALOutputHelper.handleOutput(mainTank, output.main(), operations);
                MekALOutputHelper.handleOutput(secondaryTank, output.secondary(), operations);
            }

            @Override
            public void calculateOperationsCanSupport(OperationTracker tracker, DoubleChemicalRecipeOutput output) {
                MekALOutputHelper.calculateOperationsCanSupport(tracker, mainNotEnoughSpaceError, mainTank,
                        output.main());
                if (tracker.shouldContinueChecking()) {
                    MekALOutputHelper.calculateOperationsCanSupport(tracker, secondaryNotEnoughSpaceError,
                            secondaryTank,
                            output.secondary());
                }
            }

        };
    }

    /**
     * Mathods under this memo were copied from
     * https://github.com/mekanism/Mekanism/blob/1.21.x/src/api/java/mekanism/api/recipes/outputs/OutputHelper.java
     */

    /**
     * Adds {@code operations} operations worth of {@code toOutput} to the output.
     *
     * @param tank       Output.
     * @param toOutput   Output result.
     * @param operations Operations to perform.
     */
    public static void handleOutput(IChemicalTank tank, ChemicalStack toOutput, int operations) {
        if (operations == 0) {
            // This should not happen
            return;
        }
        ChemicalStack output = toOutput.copyWithAmount(toOutput.getAmount() * operations);
        tank.insert(output, Action.EXECUTE, AutomationType.INTERNAL);
    }

    public static void handleOutput(IExtendedFluidTank fluidTank, FluidStack toOutput, int operations) {
        if (operations == 0) {
            // This should not happen
            return;
        }
        fluidTank.insert(toOutput.copyWithAmount(toOutput.getAmount() * operations), Action.EXECUTE,
                AutomationType.INTERNAL);
    }

    public static void handleOutput(IInventorySlot inventorySlot, ItemStack toOutput, int operations) {
        if (operations == 0 || toOutput.isEmpty()) {
            return;
        }
        ItemStack output = toOutput.copy();
        if (operations > 1) {
            // If we are doing more than one operation we need to make a copy of our stack
            // and change the amount
            // that we are using the fill the tank with
            output.setCount(output.getCount() * operations);
        }
        inventorySlot.insertItem(output, Action.EXECUTE, AutomationType.INTERNAL);
    }

    /**
     * Calculates how many operations the output has room for and updates the given
     * operation tracker.
     *
     * @param tracker        Tracker of current errors and max operations.
     * @param tank           Output.
     * @param toOutput       Output result.
     * @param notEnoughSpace The error to apply if the output causes the recipe to
     *                       not be able to perform any operations.
     */
    public static void calculateOperationsCanSupport(OperationTracker tracker, RecipeError notEnoughSpace,
            IChemicalTank tank,
            ChemicalStack toOutput) {
        // If our output is empty, we have nothing to add, so we treat it as being able
        // to fit all
        if (!toOutput.isEmpty()) {
            // Copy the stack and make it be max size
            ChemicalStack maxOutput = toOutput.copyWithAmount(Long.MAX_VALUE);
            // Divide the amount we can actually use by the amount one output operation is
            // equal to, capping it at the max we were told about
            ChemicalStack remainder = tank.insert(maxOutput, Action.SIMULATE, AutomationType.INTERNAL);
            long amountUsed = maxOutput.getAmount() - remainder.getAmount();
            // Divide the amount we can actually use by the amount one output operation is
            // equal to, capping it at the max we were told about
            int operations = MathUtils.clampToInt(amountUsed / toOutput.getAmount());
            tracker.updateOperations(operations);
            if (operations == 0) {
                if (amountUsed == 0 && tank.getNeeded() > 0) {
                    tracker.addError(RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT);
                } else {
                    tracker.addError(notEnoughSpace);
                }
            }
        }
    }

    public static void calculateOperationsCanSupport(OperationTracker tracker, RecipeError notEnoughSpace,
            IExtendedFluidTank tank, FluidStack toOutput) {
        // If our output is empty, we have nothing to add, so we treat it as being able
        // to fit all
        if (!toOutput.isEmpty()) {
            // Copy the stack and make it be max size
            FluidStack maxOutput = toOutput.copyWithAmount(Integer.MAX_VALUE);
            // Then simulate filling the fluid tank, so we can see how much actually can fit
            FluidStack remainder = tank.insert(maxOutput, Action.SIMULATE, AutomationType.INTERNAL);
            int amountUsed = maxOutput.getAmount() - remainder.getAmount();
            // Divide the amount we can actually use by the amount one output operation is
            // equal to, capping it at the max we were told about
            int operations = amountUsed / toOutput.getAmount();
            tracker.updateOperations(operations);
            if (operations == 0) {
                if (amountUsed == 0 && tank.getNeeded() > 0) {
                    tracker.addError(RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT);
                } else {
                    tracker.addError(notEnoughSpace);
                }
            }
        }
    }

    public static void calculateOperationsCanSupport(OperationTracker tracker, RecipeError notEnoughSpace,
            IInventorySlot slot, ItemStack toOutput) {
        // If our output is empty, we have nothing to add, so we treat it as being able
        // to fit all
        if (!toOutput.isEmpty()) {
            // Make a copy of the stack we are outputting with its maximum size
            ItemStack output = toOutput.copyWithCount(toOutput.getMaxStackSize());
            ItemStack remainder = slot.insertItem(output, Action.SIMULATE, AutomationType.INTERNAL);
            int amountUsed = output.getCount() - remainder.getCount();
            // Divide the amount we can actually use by the amount one output operation is
            // equal to, capping it at the max we were told about
            int operations = amountUsed / toOutput.getCount();
            tracker.updateOperations(operations);
            if (operations == 0) {
                if (amountUsed == 0 && slot.getLimit(slot.getStack()) - slot.getCount() > 0) {
                    tracker.addError(RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT);
                } else {
                    tracker.addError(notEnoughSpace);
                }
            }
        }
    }
}
