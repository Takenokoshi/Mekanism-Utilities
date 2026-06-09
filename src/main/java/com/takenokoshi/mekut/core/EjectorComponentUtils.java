package com.takenokoshi.mekut.core;

import java.util.function.BiPredicate;

import com.takenokoshi.mekut.blockentity.component.IEjectorComponentAccess;
import mekanism.api.chemical.IChemicalTank;
import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.tile.component.config.DataType;

public class EjectorComponentUtils {

    public static void setCanFluidTankEject(TileComponentEjector ejector, BiPredicate<DataType, IExtendedFluidTank> v) {
        ((IEjectorComponentAccess) (Object) ejector).mekanism_utilities$setCanFluidTankEject(v);
    }

    public static void setCanChemicalTankEject(TileComponentEjector ejector, BiPredicate<DataType, IChemicalTank> v) {
        ((IEjectorComponentAccess) (Object) ejector).mekanism_utilities$setCanChemicalTankEject(v);
    }
}
