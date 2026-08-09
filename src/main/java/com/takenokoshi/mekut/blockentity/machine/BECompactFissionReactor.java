package com.takenokoshi.mekut.blockentity.machine;

import com.takenokoshi.mekut.blockentity.abs.BEAbstractCompactFissionReactor;

import mekanism.generators.common.config.MekanismGeneratorsConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BECompactFissionReactor extends BEAbstractCompactFissionReactor {

    public static final double avgSurfaceArea = 62.0d / 15.0d;

    public BECompactFissionReactor(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state, 1920, 1200.0d, Math.min(
                avgSurfaceArea / MekanismGeneratorsConfig.generators.fissionSurfaceAreaTarget.getAsDouble(),
                1.0d));
    }

    @Override
    protected int initFluidCoolantTankCapacity() {
        return 583_200_000;
    }

    @Override
    protected long initChemicalCoolantTankCapacity() {
        return 583_200_000l;
    }

    @Override
    protected long initFuelTankCapacity() {
        return 15_360_000l;
    }

    @Override
    protected double initHeatCapacity() {
        return 1736 * MekanismGeneratorsConfig.generators.fissionCasingHeatCapacity.getAsDouble();
    }

    @Override
    protected long initHeatedCoolantTankCapacity() {
        return 5_832_000_000l;
    }

}
