package takenokoshi.mekut.client;

import mekanism.client.ClientRegistrationUtil;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import takenokoshi.mekut.common.MekUt;
import takenokoshi.mekut.common.registries.MekUtMachines;

@EventBusSubscriber(modid = MekUt.MODID, value = Dist.CLIENT)
public class MekUtClientRegistration {
    
    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event){
        ClientRegistrationUtil.registerElectricScreen(event, MekUtMachines.MINERAL_EXTRACTOR.getContainer());
    }
}
