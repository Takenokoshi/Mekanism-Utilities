package com.takenokoshi.mekut.blockentity.standardmachine;

import org.jetbrains.annotations.NotNull;

import com.takenokoshi.mekut.blockentity.prefab.BEBasicItemChemicalToItemMachine;
import com.takenokoshi.mekut.config.MekUtConfig;
import mekanism.api.math.MathUtils;
import mekanism.api.recipes.ItemStackChemicalToItemStackRecipe;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mekanism.client.recipe_viewer.type.RecipeViewerRecipeType;
import mekanism.common.config.MekanismConfig;
import mekanism.common.recipe.IMekanismRecipeTypeProvider;
import mekanism.common.recipe.MekanismRecipeType;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.ItemChemical;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BEStandardPurificationChamber extends BEBasicItemChemicalToItemMachine {

    public BEStandardPurificationChamber(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state,
                v -> MathUtils.clampToLong(v * 200d / MekanismConfig.general.maxUpgradeMultiplier.get()),
                MathUtils.clampToInt(MekUtConfig.general.standardMachinePerformance.get() / 200));
    }

    @Override
    public @NotNull IMekanismRecipeTypeProvider<?, ItemStackChemicalToItemStackRecipe, ItemChemical<ItemStackChemicalToItemStackRecipe>> getRecipeType() {
        return MekanismRecipeType.PURIFYING;
    }

    @Override
    protected long initChemicalTankCapacity() {
        return 4800;
    }

    @Override
    public IRecipeViewerRecipeType<ItemStackChemicalToItemStackRecipe> recipeViewerType() {
        return RecipeViewerRecipeType.PURIFYING;
    }

}
