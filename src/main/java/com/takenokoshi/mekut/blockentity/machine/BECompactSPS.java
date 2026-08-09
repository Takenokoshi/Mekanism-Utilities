package com.takenokoshi.mekut.blockentity.machine;

import com.takenokoshi.mekut.blockentity.abs.BEAbstractCompactSPS;
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
    
}
