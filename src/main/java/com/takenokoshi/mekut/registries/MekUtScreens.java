package com.takenokoshi.mekut.registries;

import com.takenokoshi.mekut.blockentity.normalmachine.*;
import com.takenokoshi.mekut.gui.machine.*;
import com.takenokoshi.mekut.registration.MachineRegistryObject;

import mekanism.client.ClientRegistrationUtil;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.tile.base.TileEntityMekanism;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.MenuScreens.ScreenConstructor;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

public class MekUtScreens {
    public static void registerScreens(RegisterMenuScreensEvent event) {
        registerMachineGui(event, MekUtMachines.CHEMICAL_CUTTER, GuiChemicalCutter<BEChemicalCutter>::new);
        registerMachineGui(event, MekUtMachines.COMPACT_SUPERCRITICAL_PHASE_SHIFTER, GuiCompactSPS<BECompactSPS>::new);
        registerMachineGui(event, MekUtMachines.ICE_MAKER, GuiFluidToObjectMachine<BEIceMaker>::new);
        registerMachineGui(event, MekUtMachines.LAZER_COMPRESS_NUCLEO_SYNTHESIZER,
                GuiBiChemicalToChemicalMachine<BELazerCompressNucleoSynthesizer>::new);
        registerMachineGui(event, MekUtMachines.MEKSTYLED_CHARGER, GuiMekStyledCharger<BEMekStyledCharger>::new);
        registerMachineGui(event, MekUtMachines.SMALL_DIGITAL_ASSEMBLER,
                GuiSmallDigitalAssembler<BESmallDigitalAssembler>::new);
        registerMachineGui(event, MekUtMachines.SMALL_DIGITAL_REACTION_CHAMBER,
                GuiSmallDigitalReactionChamber<BESmallDigitalReactionChamber>::new);
        registerMachineGui(event, MekUtMachines.STELLAR_GENESIS_CHAMBER,
                GuiBiChemicalToObjectMachine<BEStellarGenesisChamber>::new);
        registerMachineGui(event, MekUtMachines.SUBMATERIAL_CONVERTER, GuiSubMaterialConverter::new);
        registerMachineGui(event, MekUtMachines.TWEAKED_ENERGIZED_SMELTER,
                GuiTweakedEnergizedSmelter<BETweakedEnergizedSmelter>::new);
    }

    public static <BE extends TileEntityMekanism, CONTAINER extends MekanismTileContainer<BE>, GUI extends Screen & MenuAccess<CONTAINER>> void registerMachineGui(
            RegisterMenuScreensEvent event, MachineRegistryObject<BE, ?, CONTAINER, ?> registryObject,
            ScreenConstructor<CONTAINER, GUI> constructor) {
        ClientRegistrationUtil.registerScreen(event, registryObject.getContainer(), constructor);
    }
}
