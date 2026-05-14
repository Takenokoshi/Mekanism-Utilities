package com.takenokoshi.mekut.core;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.takenokoshi.mekut.registries.MekUtBlocks;
import com.takenokoshi.mekut.registries.MekUtChemicals;
import com.takenokoshi.mekut.registries.MekUtCreativeTabs;
import com.takenokoshi.mekut.registries.MekUtItems;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(value = MekUtConstants.MODID, dist = Dist.DEDICATED_SERVER)
public class MekUt {
    public static final Logger LOGGER = LogUtils.getLogger();

    public MekUt(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        addRegistrationListeners(modEventBus);
    }

    private void addRegistrationListeners(IEventBus modEventBus) {
        MekUtBlocks.BLOCKS.register(modEventBus);
        MekUtChemicals.CHEMICALS.register(modEventBus);
        MekUtItems.ITEMS.register(modEventBus);
        MekUtCreativeTabs.CREATIVE_TABS.register(modEventBus);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM MekUt SETUP");
    }
}
