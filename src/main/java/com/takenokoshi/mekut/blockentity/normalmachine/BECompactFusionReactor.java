package com.takenokoshi.mekut.blockentity.normalmachine;

import com.takenokoshi.mekut.blockentity.abs.BEAbstractCompactFusionReactor;

import mekanism.generators.common.config.MekanismGeneratorsConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BECompactFusionReactor extends BEAbstractCompactFusionReactor {

    public BECompactFusionReactor(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state, false);
    }

    @Override
    protected long initFuelTankCapacity() {
        return 1000L;
    }

    @Override
    protected long initEnergyContainerCapacity() {
        return MekanismGeneratorsConfig.generators.fusionEnergyCapacity.getAsLong();
    }
    
}
