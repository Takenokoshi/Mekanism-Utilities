package com.takenokoshi.mekut.blockentity.normalmachine;

import com.takenokoshi.mekut.blockentity.abs.BEAbstractLazerCompressNucleoSynthesizer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BELazerCompressNucleoSynthesizer extends BEAbstractLazerCompressNucleoSynthesizer {

    public BELazerCompressNucleoSynthesizer(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state, 3);
    }

    @Override
    protected long initChemicalTankCapacity() {
        return 20000000l;
    }
}