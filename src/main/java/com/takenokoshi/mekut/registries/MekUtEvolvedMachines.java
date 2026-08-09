package com.takenokoshi.mekut.registries;

import com.takenokoshi.mekaddonlib.registration.GuiSizedMachineRegistryObject;
import com.takenokoshi.mekaddonlib.registration.MachineDeferredRegister;
import com.takenokoshi.mekut.blockentity.abs.BEAbstractCompactAPT;
import com.takenokoshi.mekut.blockentity.machine.BECompactAPT;
import com.takenokoshi.mekut.core.MekUtConstants;

import fr.iglee42.evolvedmekanism.config.EMConfig;
import mekanism.api.Upgrade;
import mekanism.common.attachments.component.AttachedSideConfig;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.registries.MekanismSounds;

public class MekUtEvolvedMachines {
    public static final MachineDeferredRegister MACHINES = new MachineDeferredRegister(MekUtConstants.MODID);


    public static final GuiSizedMachineRegistryObject<BECompactAPT> COMPACT_ANTIMATTER_PROTOMOLECULAR_TRANSMUTATOR = MACHINES
            .registerGuiSized("compact_antimatter_protomolecular_transmutator",
                    AttachedSideConfig.ADVANCED_MACHINE_INPUT_ONLY,
                    BEAbstractCompactAPT.getContainerAddar(10000L)::accept,
                    BECompactAPT::new,
                    BECompactAPT.class,
                    builder -> builder
                            .withEnergyConfig(
                                    EMConfig.general.aptEnergyConsumption,
                                    EMConfig.general.aptEnergyStorage)
                            .withSideConfig(TransmissionType.ITEM, TransmissionType.CHEMICAL, TransmissionType.ENERGY)
                            .withSupportedUpgrades(Upgrade.MUFFLING)
                            .withSound(MekanismSounds.SPS));
}
