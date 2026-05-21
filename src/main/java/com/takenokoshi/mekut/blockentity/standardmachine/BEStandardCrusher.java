package com.takenokoshi.mekut.blockentity.standardmachine;

import org.jetbrains.annotations.NotNull;

import com.takenokoshi.mekut.blockentity.prefab.BEBasicItemStackToItemStackMachine;
import com.takenokoshi.mekut.core.MekUtMathUtils;

import mekanism.api.recipes.ItemStackToItemStackRecipe;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mekanism.client.recipe_viewer.type.RecipeViewerRecipeType;
import mekanism.common.recipe.IMekanismRecipeTypeProvider;
import mekanism.common.recipe.MekanismRecipeType;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.SingleItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BEStandardCrusher extends BEBasicItemStackToItemStackMachine {

    public BEStandardCrusher(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state,
                MekUtMathUtils.getBaselineAccelerated(200, 6));
    }

    @Override
    public @NotNull IMekanismRecipeTypeProvider<?, ItemStackToItemStackRecipe, SingleItem<ItemStackToItemStackRecipe>> getRecipeType() {
        return MekanismRecipeType.CRUSHING;
    }

    @Override
    public IRecipeViewerRecipeType<ItemStackToItemStackRecipe> recipeViewerType() {
        return RecipeViewerRecipeType.CRUSHING;
    }

}
