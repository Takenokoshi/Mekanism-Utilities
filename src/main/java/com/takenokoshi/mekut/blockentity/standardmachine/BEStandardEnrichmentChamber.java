package com.takenokoshi.mekut.blockentity.standardmachine;

import org.jetbrains.annotations.NotNull;

import com.takenokoshi.mekut.blockentity.prefab.BEBasicItemStackToItemStackMachine;
import com.takenokoshi.mekut.core.MekUtMathUtils;
import com.takenokoshi.mekut.recipe.type.IMekUtRecipeTypeProvider;
import com.takenokoshi.mekut.recipe.type.WrappedMekanismRecipeType;

import mekanism.api.recipes.ItemStackToItemStackRecipe;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mekanism.client.recipe_viewer.type.RecipeViewerRecipeType;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.SingleItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BEStandardEnrichmentChamber extends BEBasicItemStackToItemStackMachine {

    public BEStandardEnrichmentChamber(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state,
                MekUtMathUtils.getBaselineAccelerated(200, 6));
    }

    @Override
    public IRecipeViewerRecipeType<ItemStackToItemStackRecipe> recipeViewerType() {
        return RecipeViewerRecipeType.ENRICHING;
    }

    @Override
    public @NotNull IMekUtRecipeTypeProvider<?, ItemStackToItemStackRecipe, SingleItem<ItemStackToItemStackRecipe>> getRecipeType() {
        return WrappedMekanismRecipeType.ENRICHING;
    }

}
