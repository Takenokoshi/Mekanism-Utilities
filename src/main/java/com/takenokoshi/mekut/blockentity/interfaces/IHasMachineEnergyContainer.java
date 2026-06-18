package com.takenokoshi.mekut.blockentity.interfaces;

import mekanism.common.capabilities.energy.MachineEnergyContainer;

public interface IHasMachineEnergyContainer {
    MachineEnergyContainer<?> getEnergyContainer();

    long getEnergyUsed();
}
