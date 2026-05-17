package com.takenokoshi.mekut.core;

import com.takenokoshi.mekut.blockentity.normalmachine.BETweakedEnergizedSmelter;
import com.takenokoshi.mekut.gui.machine.GuiMekUtEnergizedSmelter;
import com.takenokoshi.mekut.registries.MekUtMachines;

import mekanism.client.ClientRegistrationUtil;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@Mod(value = MekUtConstants.MODID, dist = Dist.CLIENT)
public class MekUtClient extends MekUt {

    public MekUtClient(IEventBus modEventBus, ModContainer modContainer) {
        super(modEventBus, modContainer);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::initScreens);
    }

    private void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            @SuppressWarnings("unused")
            Minecraft minecraft = Minecraft.getInstance();
        });
    }

    private void initScreens(RegisterMenuScreensEvent event) {
        ClientRegistrationUtil.registerScreen(event, MekUtMachines.TWEAKED_ENERGIZED_SMELTER.getContainer(),
                GuiMekUtEnergizedSmelter<BETweakedEnergizedSmelter>::new);
    }

}
