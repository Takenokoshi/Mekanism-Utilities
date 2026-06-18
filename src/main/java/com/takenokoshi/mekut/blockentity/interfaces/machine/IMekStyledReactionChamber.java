package com.takenokoshi.mekut.blockentity.interfaces.machine;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.takenokoshi.mekut.blockentity.interfaces.IHasMachineEnergyContainer;
import com.takenokoshi.mekut.recipe.inputcache.ItemStackListFluidInputRecipeCache;
import com.takenokoshi.mekut.recipe.lookup.recipe.IItemStackListFluidRecipeLookupHandler;
import com.takenokoshi.mekut.recipe.type.IMekUtRecipeTypeProvider;
import com.takenokoshi.mekut.recipe.type.WrappedRecipeType;

import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import net.minecraft.world.item.crafting.RecipeInput;
import net.pedroksl.advanced_ae.recipes.ReactionChamberRecipe;

public interface IMekStyledReactionChamber
        extends IItemStackListFluidRecipeLookupHandler<ReactionChamberRecipe>, IHasMachineEnergyContainer {

    public static final List<RecipeError> TRACKED_ERROR_TYPES = List.of(
            RecipeError.NOT_ENOUGH_ENERGY,
            RecipeError.NOT_ENOUGH_INPUT,
            RecipeError.NOT_ENOUGH_OUTPUT_SPACE,
            RecipeError.INPUT_DOESNT_PRODUCE_OUTPUT);

    IExtendedFluidTank getInputTank();

    IExtendedFluidTank getOutputTank();

    double getScaledProgress();

    @Override
    default @NotNull IMekUtRecipeTypeProvider<RecipeInput, ReactionChamberRecipe, ItemStackListFluidInputRecipeCache<ReactionChamberRecipe>> getRecipeType() {
        return WrappedRecipeType.ADVANCEDAE_REACTION_CHAMBER;
    }
}
