package com.takenokoshi.mekut.core;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.takenokoshi.mekut.config.MekUtConfig;
import com.takenokoshi.mekut.network.MekUtPacketHandler;
import com.takenokoshi.mekut.registries.*;

import mekanism.common.lib.Version;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(value = MekUtConstants.MODID, dist = Dist.DEDICATED_SERVER)
public class MekUt {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static MekUt instance;
    public final Version versionNumber;
    private final MekUtPacketHandler packetHandler;

    public MekUt(IEventBus modEventBus, ModContainer modContainer) {
        instance = this;
        versionNumber = new Version(modContainer);
        MekUtConfig.registerConfigs(modContainer);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(MekUtConfig::onConfigLoad);
        addRegistrationListeners(modEventBus);
        if (ModList.get().isLoaded("appmek")) {
            modEventBus.addListener(AppMekModule::registerCellHandler);
        }
        packetHandler = new MekUtPacketHandler(modEventBus, versionNumber);
    }

    public static MekUtPacketHandler packetHandler() {
        return instance.packetHandler;
    }

    private void addRegistrationListeners(IEventBus modEventBus) {
        MekUtBlocks.BLOCKS.register(modEventBus);
        MekUtChemicals.CHEMICALS.register(modEventBus);
        MekUtFluids.FLUIDS.register(modEventBus);
        MekUtItems.ITEMS.register(modEventBus);
        MekUtMachines.MACHINES.register(modEventBus);
        if (ModList.get().isLoaded("evolvedmekanism")) {
            MekUtEvolvedMachines.MACHINES.register(modEventBus);
        }
        if (ModList.get().isLoaded("mekanism_extras")) {
            MekUtExtrasMachines.MACHINES.register(modEventBus);
        }
        MekUtRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        MekUtRecipeTypes.RECIPE_TYPES.register(modEventBus);
        MekUtCreativeTabs.CREATIVE_TABS.register(modEventBus);
        MekUtLootModifiers.LOOT_MODIFIERS.register(modEventBus);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM MekUt SETUP");
    }
}
