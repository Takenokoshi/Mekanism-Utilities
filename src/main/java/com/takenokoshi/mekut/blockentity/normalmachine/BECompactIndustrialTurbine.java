package com.takenokoshi.mekut.blockentity.normalmachine;

import com.takenokoshi.mekut.blockentity.abs.BEAbstractCompactIndustrialTurbine;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BECompactIndustrialTurbine extends BEAbstractCompactIndustrialTurbine {

    public BECompactIndustrialTurbine(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state, 2_912L, 405L);
    }

    @Override
    protected long initChemicalTankCapacity() {
        return 240_448_000L;
    }

    @Override
    protected long initEnergyContainerCapacity() {
        return 83_232_000_000L;
    }

    @Override
    protected int initFluidTankCapacity() {
        return 12_992_000;
    }

}
