package com.takenokoshi.mekut.registries;

import com.takenokoshi.mekut.blockentity.machine.BECompactAPT;
import com.takenokoshi.mekut.gui.machine.GuiCompactAPT;

import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import static com.takenokoshi.mekut.registries.MekUtScreens.registerMachineGui;

public class MekUtEvolvedScreens {

    public static void registerScreens(RegisterMenuScreensEvent event) {
        registerMachineGui(event, MekUtEvolvedMachines.COMPACT_ANTIMATTER_PROTOMOLECULAR_TRANSMUTATOR,
                GuiCompactAPT<BECompactAPT>::new);
    }
}
