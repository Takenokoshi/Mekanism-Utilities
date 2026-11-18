package takenokoshi.mekut.common;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import takenokoshi.mekut.common.registries.MekUtBlocks;
import takenokoshi.mekut.common.registries.MekUtChemicals;
import takenokoshi.mekut.common.registries.MekUtItems;
import takenokoshi.mekut.common.registries.MekUtMachines;
import takenokoshi.mekut.common.registries.MekUtRecipeSerializersInternal;

@Mod(value = MekUt.MODID)
public class MekUt {
    public static final String MODID = "mekut";

    public static  ResourceLocation rl(String path){
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    public MekUt(IEventBus modEventBus, ModContainer modContainer) {
        addRegistrationListeners(modEventBus);
    }

    
    private void addRegistrationListeners(IEventBus modEventBus){
        MekUtChemicals.CHEMICALS.register(modEventBus);
        MekUtMachines.MACHINES.register(modEventBus);
        MekUtBlocks.BLOCKS.register(modEventBus);
        MekUtItems.ITEMS.register(modEventBus);
        MekUtRecipeSerializersInternal.RECIPE_SERIALIZERS.register(modEventBus);
    }
}
