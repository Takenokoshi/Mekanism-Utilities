package com.takenokoshi.mekut.blockentity.interfaces;

import mekanism.common.capabilities.energy.MachineEnergyContainer;

public interface IHasMachineEnergyCntainer {
    MachineEnergyContainer<?> getEnergyContainer();

    long getEnergyUsed();
}
