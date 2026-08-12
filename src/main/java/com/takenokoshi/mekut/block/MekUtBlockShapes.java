package com.takenokoshi.mekut.block;

import mekanism.common.util.VoxelShapeUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MekUtBlockShapes {
    public static final VoxelShape[] GREEN_HOUSE = new VoxelShape[4];
    static {
        VoxelShapeUtils.setShape(Block.box(-16.0d, -16.0d, -16.0d, 32.0d, 32.0d, 32.0d).move(0, 1, 0), GREEN_HOUSE);
    }
}
