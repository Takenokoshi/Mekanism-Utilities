package com.takenokoshi.mekut.core;

import com.takenokoshi.mekut.item.cell.rainbow.InfinityRainbowCellHandler;

import appeng.api.storage.StorageCells;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

public class AppMekModule {
    static void registerCellHandler(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            StorageCells.addCellHandler(new InfinityRainbowCellHandler());
        });
    }
}
