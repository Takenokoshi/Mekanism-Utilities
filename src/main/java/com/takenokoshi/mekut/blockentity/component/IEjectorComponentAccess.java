package com.takenokoshi.mekut.blockentity.component;

import java.util.function.BiPredicate;

import mekanism.api.chemical.IChemicalTank;
import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.common.tile.component.config.DataType;

public interface IEjectorComponentAccess {

    void mekanism_utilities$setCanFluidTankEject(
            BiPredicate<DataType, IExtendedFluidTank> v);

    void mekanism_utilities$setCanChemicalTankEject(
            BiPredicate<DataType, IChemicalTank> v);
}