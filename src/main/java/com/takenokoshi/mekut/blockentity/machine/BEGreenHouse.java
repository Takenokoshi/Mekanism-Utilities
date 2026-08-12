package com.takenokoshi.mekut.blockentity.machine;

import com.takenokoshi.mekut.blockentity.abs.BEAbstractGreenHouse;
import com.takenokoshi.mekut.recipe.output.BasicChanceOutputHandler;
import com.takenokoshi.mekut.recipe.output.SimpleChanceOutputHandler;

import mekanism.api.inventory.IInventorySlot;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BEGreenHouse extends BEAbstractGreenHouse {

    public BEGreenHouse(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state, 1);
    }

    @Override
    protected BasicChanceOutputHandler initOutputHandler(IInventorySlot slot, RecipeError notEnoughSpaceError) {
        return SimpleChanceOutputHandler.create(slot, notEnoughSpaceError);
    }

    @Override
    protected int initFluidTankCapacity() {
        return 10_000;
    }

    @Override
    protected int initItemSlotCapacity() {
        return 128;
    }

}
