package com.takenokoshi.mekut.blockentity.machine;

import com.takenokoshi.mekut.blockentity.abs.BEAbstractIceMaker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BEIceMaker extends BEAbstractIceMaker {

    public BEIceMaker(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state, 1);
    }

    @Override
    protected int initItemSlotCapacity() {
        return 128;
    }

    @Override
    protected int initFluidTankCapacity() {
        return 20000;
    }
}