package com.takenokoshi.mekut.core;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(value = MekUtConstants.MODID, dist = Dist.CLIENT)
public class MekUtClient extends MekUt {

    public MekUtClient(IEventBus modEventBus, ModContainer modContainer) {
        super(modEventBus, modContainer);
    }

}
