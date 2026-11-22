package takenokoshi.mekut.client;

import mekanism.client.ClientRegistrationUtil;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import takenokoshi.mekal.client.gui.machine.GuiItemStackFluidToItemStackMachine;
import takenokoshi.mekal.client.gui.machine.GuiItemStackToFluidMachine;
import takenokoshi.mekut.common.MekUt;
import takenokoshi.mekut.common.registries.MekUtMachines;

@EventBusSubscriber(modid = MekUt.MODID, value = Dist.CLIENT)
public class MekUtClientRegistration {

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        ClientRegistrationUtil.registerScreen(event, MekUtMachines.HYDRAULIC_EXTRACTOR.getContainer(),
                GuiItemStackFluidToItemStackMachine::new);
        ClientRegistrationUtil.registerElectricScreen(event, MekUtMachines.MINERAL_EXTRACTOR.getContainer());
        ClientRegistrationUtil.registerScreen(event, MekUtMachines.SQUEEZER.getContainer(),
                GuiItemStackToFluidMachine::new);
    }
}
