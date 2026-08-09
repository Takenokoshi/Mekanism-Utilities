package com.takenokoshi.mekut.registries;

import com.jerry.genextras.common.config.GeneratorsExtraConfig;
import com.takenokoshi.mekaddonlib.registration.GuiSizedMachineRegistryObject;
import com.takenokoshi.mekaddonlib.registration.MachineDeferredRegister;
import com.takenokoshi.mekut.blockentity.abs.BEAbstractCompactFusionReactor;
import com.takenokoshi.mekut.blockentity.machine.BECompactNaquadahReactor;
import com.takenokoshi.mekut.core.MekUtConstants;

import mekanism.api.Upgrade;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.generators.common.registries.GeneratorsSounds;

public class MekUtExtrasMachines {
    public static final MachineDeferredRegister MACHINES = new MachineDeferredRegister(MekUtConstants.MODID);

    public static final GuiSizedMachineRegistryObject<BECompactNaquadahReactor> COMPACT_NAQUADAH_REACTOR = MACHINES
            .registerGuiSized("compact_naquadah_reactor",
                    BEAbstractCompactFusionReactor.SIDE_CONFIG,
                    BECompactNaquadahReactor
                            .getContainerAdder(GeneratorsExtraConfig.extraGenerators.reactorFuelCapacity)::accept,
                    BECompactNaquadahReactor::new,
                    builder -> builder
                            .withSimple(Capabilities.LASER_RECEPTOR),
                    BECompactNaquadahReactor.class,
                    builder -> builder
                            .withSideConfig(TransmissionType.values())
                            .withSound(GeneratorsSounds.FUSION_REACTOR)
                            .withSupportedUpgrades(Upgrade.MUFFLING));
}
