package com.takenokoshi.mekut.core;

import com.takenokoshi.mekut.blockentity.normalmachine.*;
import com.takenokoshi.mekut.blockentity.standardmachine.*;
import com.takenokoshi.mekut.gui.machine.*;
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
        ClientRegistrationUtil.registerScreen(event, MekUtMachines.SUBMATERIAL_CONVERTER.getContainer(),
                GuiSubMaterialConverter::new);
        ClientRegistrationUtil.registerScreen(event, MekUtMachines.MEKSTYLED_CHARGER.getContainer(),
                GuiMekStyledCharger<BEMekStyledCharger>::new);
        ClientRegistrationUtil.registerScreen(event, MekUtMachines.TWEAKED_ENERGIZED_SMELTER.getContainer(),
                GuiTweakedEnergizedSmelter<BETweakedEnergizedSmelter>::new);
        ClientRegistrationUtil.registerScreen(event, MekUtMachines.STANDARD_CHEMICAL_INJECTION_CHAMBER.getContainer(),
                GuiTweakedItemChemicalToItemMachine<BEStandardChemicalInjectionChamber>::new);
        ClientRegistrationUtil.registerScreen(event, MekUtMachines.STANDARD_CRUSHER.getContainer(),
                GuiBasicItemStackToItemStackMachine<BEStandardCrusher>::new);
        ClientRegistrationUtil.registerScreen(event, MekUtMachines.STANDARD_ENERGIZED_SMELTER.getContainer(),
                GuiTweakedEnergizedSmelter<BEStandardEnergizedSmelter>::new);
        ClientRegistrationUtil.registerScreen(event, MekUtMachines.STANDARD_ENRICHMENR_CHAMBER.getContainer(),
                GuiBasicItemStackToItemStackMachine<BEStandardEnrichmentChamber>::new);
        ClientRegistrationUtil.registerScreen(event, MekUtMachines.STANDARD_MEKSTYLED_CHARGER.getContainer(),
                GuiMekStyledCharger<BEStandardMekStyledCharger>::new);
        ClientRegistrationUtil.registerScreen(event, MekUtMachines.STANDARD_PURIFICATION_CHAMBER.getContainer(),
                GuiTweakedItemChemicalToItemMachine<BEStandardPurificationChamber>::new);
    }

}
