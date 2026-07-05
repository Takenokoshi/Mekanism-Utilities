package com.takenokoshi.mekut.blockentity.normalmachine;

import com.takenokoshi.mekut.blockentity.abs.BEAbstractChemicalCutter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BEChemicalCutter extends BEAbstractChemicalCutter {

    public BEChemicalCutter(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state, 2);
    }

    @Override
    protected long initChemicalTankCapacity() {
        return 200000;
    }
}