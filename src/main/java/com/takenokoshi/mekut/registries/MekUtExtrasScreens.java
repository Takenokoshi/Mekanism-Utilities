package com.takenokoshi.mekut.registries;

import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

import static com.takenokoshi.mekut.registries.MekUtScreens.registerMachineGui;

import com.takenokoshi.mekut.blockentity.machine.BECompactNaquadahReactor;
import com.takenokoshi.mekut.gui.machine.GuiCompactFusionReactor;

public class MekUtExtrasScreens {

    public static void registerScreens(RegisterMenuScreensEvent event) {
        registerMachineGui(event, MekUtExtrasMachines.COMPACT_NAQUADAH_REACTOR,
                GuiCompactFusionReactor<BECompactNaquadahReactor>::new);
    }
}
