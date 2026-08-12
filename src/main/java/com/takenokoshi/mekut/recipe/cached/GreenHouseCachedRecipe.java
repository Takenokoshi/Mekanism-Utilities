package com.takenokoshi.mekut.recipe.cached;

import java.util.List;
import java.util.function.BooleanSupplier;

import com.takenokoshi.mekut.recipe.output.BasicChanceOutputHandler;
import com.takenokoshi.mekut.recipe.output.MekUtChanceOutput;
import com.takenokoshi.mekut.recipe.recipe.prefab.GreenHouseRecipe;

import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.api.recipes.inputs.IInputHandler;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;

public class GreenHouseCachedRecipe extends BasicCachedRecipe<GreenHouseRecipe> {

    private final IInputHandler<ItemStack> cropHandler;
    private final IInputHandler<ItemStack> soilHandler;
    private final IInputHandler<FluidStack> fertilizerHandler;
    private final BasicChanceOutputHandler[] outputHandlers;

    private ItemStack crop = ItemStack.EMPTY;
    private ItemStack soil = ItemStack.EMPTY;
    private FluidStack fertilizer = FluidStack.EMPTY;
    private List<MekUtChanceOutput> outputs = List.of();

    public GreenHouseCachedRecipe(GreenHouseRecipe recipe, BooleanSupplier recheckAllErrors,
            IInputHandler<ItemStack> cropHandler, IInputHandler<ItemStack> soilHandler,
            IInputHandler<FluidStack> fertilizerHandler, BasicChanceOutputHandler[] outputHandlers) {
        super(recipe, recheckAllErrors);
        this.cropHandler = cropHandler;
        this.soilHandler = soilHandler;
        this.fertilizerHandler = fertilizerHandler;
        this.outputHandlers = outputHandlers;
    }

    @Override
    protected void calculateOperationsThisTick(OperationTracker tracker) {
        super.calculateOperationsThisTick(tracker);
        if (!tracker.shouldContinueChecking()) {
            return;
        }
        crop = cropHandler.getRecipeInput(recipe.cropIngredient);
        soil = soilHandler.getRecipeInput(recipe.soilIngredient);
        fertilizer = fertilizerHandler.getRecipeInput(recipe.fertilizerIngredient);
        if (crop.isEmpty() || soil.isEmpty() || fertilizer.isEmpty()) {
            tracker.mismatchedRecipe();
            return;
        }
        outputs = recipe.getOutputs(crop, soil);
        if (outputs.size() > outputHandlers.length) {
            tracker.resetProgress(RecipeError.NOT_ENOUGH_OUTPUT_SPACE);
            return;
        }
        cropHandler.calculateOperationsCanSupport(tracker, crop);
        soilHandler.calculateOperationsCanSupport(tracker, soil);
        fertilizerHandler.calculateOperationsCanSupport(tracker, fertilizer);
        for (int i = 0; i < outputs.size(); i++) {
            outputHandlers[i].calculateOperationsCanSupport(tracker, outputs.get(i));
        }
    }

    @Override
    public boolean isInputValid() {
        return recipe.test(cropHandler.getInput(), soilHandler.getInput(), fertilizerHandler.getInput());
    }

    @Override
    protected void finishProcessing(int operations) {
        if (crop.isEmpty() || soil.isEmpty() || fertilizer.isEmpty() || outputs.isEmpty()
                || outputs.size() > outputHandlers.length) {
            return;
        }
        fertilizerHandler.use(fertilizer, operations);
        for (int i = 0; i < outputs.size(); i++) {
            outputHandlers[i].handleOutput(outputs.get(i), operations);
        }
    }

}
