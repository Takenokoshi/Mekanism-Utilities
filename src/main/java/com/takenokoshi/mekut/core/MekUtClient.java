package com.takenokoshi.mekut.core;

import com.takenokoshi.mekut.registries.MekUtEvolvedScreens;
import com.takenokoshi.mekut.registries.MekUtExtrasScreens;
import com.takenokoshi.mekut.registries.MekUtFluids;
import com.takenokoshi.mekut.registries.MekUtScreens;

import mekanism.client.ClientRegistrationUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Holder;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@Mod(value = MekUtConstants.MODID, dist = Dist.CLIENT)
public class MekUtClient extends MekUt {

    public MekUtClient(IEventBus modEventBus, ModContainer modContainer) {
        super(modEventBus, modContainer);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(MekUtScreens::registerScreens);
        if (ModList.get().isLoaded("evolvedmekanism")) {
            modEventBus.addListener(MekUtEvolvedScreens::registerScreens);
        }
        if (ModList.get().isLoaded("mekanism_extras")) {
            modEventBus.addListener(MekUtExtrasScreens::registerScreens);
        }
        modEventBus.addListener(this::registerItemColorHandlers);
        modEventBus.addListener(this::registerClientExtensions);
    }

    private void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            @SuppressWarnings("unused")
            Minecraft minecraft = Minecraft.getInstance();
            for (Holder<Fluid> fluid : MekUtFluids.FLUIDS.getFluidEntries()) {
                ItemBlockRenderTypes.setRenderLayer(fluid.value(), RenderType.translucent());
            }
        });
    }

    private void registerItemColorHandlers(RegisterColorHandlersEvent.Item event){
        ClientRegistrationUtil.registerBucketColorHandler(event, MekUtFluids.FLUIDS);
    }

    private void registerClientExtensions(RegisterClientExtensionsEvent event){
        ClientRegistrationUtil.registerFluidExtensions(event, MekUtFluids.FLUIDS);
    }

}
