package com.takenokoshi.mekut.blockentity.normalmachine;

import com.takenokoshi.mekut.blockentity.abs.BEAbstractSmallDigitalReactionChamber;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BESmallDigitalReactionChamber extends BEAbstractSmallDigitalReactionChamber {

    public BESmallDigitalReactionChamber(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state, 2);
    }

    @Override
    protected int initItemSlotCapacity() {
        return 4096;
    }

    @Override
    protected int initFluidTankCapacity() {
        return 20000;
    }

    @Override
    protected long initChemicalTankCapacity() {
        return 200000;
    }
}