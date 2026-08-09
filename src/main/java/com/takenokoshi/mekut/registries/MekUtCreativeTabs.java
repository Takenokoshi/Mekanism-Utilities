package com.takenokoshi.mekut.registries;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.lang.MekUtLang;

import mekanism.common.registration.MekanismDeferredHolder;
import mekanism.common.registration.impl.CreativeTabDeferredRegister;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.fml.ModList;

public class MekUtCreativeTabs {
    public static final CreativeTabDeferredRegister CREATIVE_TABS = new CreativeTabDeferredRegister(
            MekUtConstants.MODID);

    public static final MekanismDeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = CREATIVE_TABS
            .register("creative_tab", MekUtLang.CREATIVE_TAB, MekUtItems.COMPISITE_ALLOY,
                    builder -> builder.displayItems(
                            (displayParameters, output) -> {
                                CreativeTabDeferredRegister.addToDisplay(MekUtItems.ITEMS, output);
                                CreativeTabDeferredRegister.addToDisplay(MekUtBlocks.BLOCKS, output);
                                CreativeTabDeferredRegister.addToDisplay(MekUtMachines.MACHINES.blockRegister, output);
                                if (ModList.get().isLoaded("evolvedmekanism")) {
                                    CreativeTabDeferredRegister
                                            .addToDisplay(MekUtEvolvedMachines.MACHINES.blockRegister, output);
                                }
                                if (ModList.get().isLoaded("mekanism_extras")) {
                                    CreativeTabDeferredRegister.addToDisplay(MekUtExtrasMachines.MACHINES.blockRegister,
                                            output);
                                }
                            }));
}
