package com.takenokoshi.mekut.blockentity.normalmachine;

import com.takenokoshi.mekut.blockentity.abs.BEAbstractCompactThermalEvaporationPlant;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BECompactThermalEvaporationPlant extends BEAbstractCompactThermalEvaporationPlant {

    public BECompactThermalEvaporationPlant(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state, 3_000.0d);
    }

    @Override
    protected int initFluidTankCapacity() {
        return 4_608_000;
    }
    
}
