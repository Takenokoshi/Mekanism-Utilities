package com.takenokoshi.mekut.blockentity.normalmachine;

import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekut.blockentity.abs.BEAbstractCompactSPS;
import com.takenokoshi.mekut.recipe_viewer.type.MekUtRecipeViewerRecipeType;

import mekanism.api.recipes.ChemicalToChemicalRecipe;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BECompactSPS extends BEAbstractCompactSPS {

    public BECompactSPS(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state, 2, 1d);
    }

    @Override
    protected long initTankCapacity() {
        return 2000;
    }

    @Override
    public @Nullable IRecipeViewerRecipeType<ChemicalToChemicalRecipe> recipeViewerType() {
        return MekUtRecipeViewerRecipeType.SPS;
    }
    
}
