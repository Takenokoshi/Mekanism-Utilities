package com.takenokoshi.mekut.registries;

import com.takenokoshi.mekut.core.MekUtConstants;

import mekanism.common.registration.impl.FluidDeferredRegister;
import mekanism.common.registration.impl.FluidDeferredRegister.MekanismFluidType;
import mekanism.common.registration.impl.FluidRegistryObject;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.block.LiquidBlock;
import net.neoforged.neoforge.fluids.BaseFlowingFluid.Flowing;
import net.neoforged.neoforge.fluids.BaseFlowingFluid.Source;

public class MekUtFluids {
    public static final FluidDeferredRegister FLUIDS = new FluidDeferredRegister(MekUtConstants.MODID);

    public static final FluidRegistryObject<MekanismFluidType, Source, Flowing, LiquidBlock, BucketItem> XP = FLUIDS
            .register("xp", props -> props.tint(0xFF53FF00));

}
