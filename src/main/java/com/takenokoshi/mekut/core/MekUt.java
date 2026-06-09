package com.takenokoshi.mekut.core;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.takenokoshi.mekut.config.MekUtConfig;
import com.takenokoshi.mekut.item.cell.bulk.MUBulkCellHandler;
import com.takenokoshi.mekut.item.cell.rainbow.InfinityRainbowCellHandler;
import com.takenokoshi.mekut.item.cell.stone.InfinityStoneCellHandler;
import com.takenokoshi.mekut.registries.*;

import appeng.api.storage.StorageCells;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(value = MekUtConstants.MODID, dist = Dist.DEDICATED_SERVER)
public class MekUt {
    public static final Logger LOGGER = LogUtils.getLogger();

    public MekUt(IEventBus modEventBus, ModContainer modContainer) {
        MekUtConfig.registerConfigs(modContainer);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(MekUtConfig::onConfigLoad);
        addRegistrationListeners(modEventBus);
        modEventBus.addListener(this::registerCellHandler);
    }

    private void addRegistrationListeners(IEventBus modEventBus) {
        MekUtBlocks.BLOCKS.register(modEventBus);
        MekUtChemicals.CHEMICALS.register(modEventBus);
        MekUtDataComponents.DATA_COMPONENTS.register(modEventBus);
        MekUtFluids.FLUIDS.register(modEventBus);
        MekUtItems.ITEMS.register(modEventBus);
        MekUtMachines.MACHINES.register(modEventBus);
        MekUtRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        MekUtRecipeTypes.RECIPE_TYPES.register(modEventBus);
        MekUtCreativeTabs.CREATIVE_TABS.register(modEventBus);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM MekUt SETUP");
    }

    private void registerCellHandler(final FMLCommonSetupEvent event){
        StorageCells.addCellHandler(new InfinityRainbowCellHandler());
        StorageCells.addCellHandler(new InfinityStoneCellHandler());
        StorageCells.addCellHandler(MUBulkCellHandler.FLUID_HANDLER);
        StorageCells.addCellHandler(MUBulkCellHandler.CHEMICAL_HANDLER);
    }
}
