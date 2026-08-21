package com.takenokoshi.mekut.blockentity.machine;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekut.blockentity.abs.BEAbstractEnergizedSmelter;
import com.takenokoshi.mekut.blockentity.upgradedata.EnergizedSmelterUpgradeData;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BETweakedEnergizedSmelter extends BEAbstractEnergizedSmelter {

    public BETweakedEnergizedSmelter(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state, 1);
    }

    @Override
    protected int initItemSlotCapacity() {
        return 128;
    }

    @Override
    public @Nullable EnergizedSmelterUpgradeData getUpgradeData(Provider provider) {
        return new EnergizedSmelterUpgradeData(provider, redstone, getControlType(), getEnergyContainer(), 
        new int[]{getOperatingTicks()}, energySlot, List.of(inputSlot), List.of(outputSlot), getXpTank(), false, getComponents());
    }
}