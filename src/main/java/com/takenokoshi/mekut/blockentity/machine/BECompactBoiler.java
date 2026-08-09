package com.takenokoshi.mekut.blockentity.machine;

import com.takenokoshi.mekut.blockentity.abs.BEAbstractCompactBoiler;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BECompactBoiler extends BEAbstractCompactBoiler {

    public BECompactBoiler(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state, 128);
    }

    @Override
    protected long initSteamTankCapacity() {
        return 259_200_000L;
    }

    @Override
    protected int initFluidTankCapacity() {
        return 60_160_000;
    }

    @Override
    protected long initHeatedCoolantTankCapacity() {
        return 962_560_000L;
    }

    @Override
    protected long initCooledCoolantTankCapacity() {
        return 414_720_000L;
    }

}
