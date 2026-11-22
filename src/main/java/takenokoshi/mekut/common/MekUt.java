package takenokoshi.mekut.common;

import mekanism.common.Mekanism;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import takenokoshi.mekal.common.RecipeTypeReloadListener;
import takenokoshi.mekut.common.recipe.MekUtRecipeType;
import takenokoshi.mekut.common.registries.MekUtBlocks;
import takenokoshi.mekut.common.registries.MekUtChemicals;
import takenokoshi.mekut.common.registries.MekUtItems;
import takenokoshi.mekut.common.registries.MekUtMachines;
import takenokoshi.mekut.common.registries.MekUtRecipeSerializersInternal;

@SuppressWarnings("unused")
@Mod(value = MekUt.MODID)
public class MekUt {
    public static final String MODID = "mekut";

    private RecipeTypeReloadListener recipeCacheManager;

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    public MekUt(IEventBus modEventBus, ModContainer modContainer) {
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, this::addReloadListenersLowest);
        addRegistrationListeners(modEventBus);
    }

    private void addRegistrationListeners(IEventBus modEventBus) {
        MekUtChemicals.CHEMICALS.register(modEventBus);
        MekUtMachines.MACHINES.register(modEventBus);
        MekUtBlocks.BLOCKS.register(modEventBus);
        MekUtItems.ITEMS.register(modEventBus);
        MekUtRecipeType.RECIPE_TYPES.register(modEventBus);
        MekUtRecipeSerializersInternal.RECIPE_SERIALIZERS.register(modEventBus);
    }

    public void setRecipeCacheManager(RecipeTypeReloadListener recipeCacheManager) {
        if (this.recipeCacheManager != null) {
            return;
        }
        this.recipeCacheManager = recipeCacheManager;
    }

    public RecipeTypeReloadListener getRecipeCacheManager() {
        return recipeCacheManager;
    }

    private void addReloadListenersLowest(AddReloadListenerEvent event) {
        event.addListener(getRecipeCacheManager());
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        setRecipeCacheManager(new RecipeTypeReloadListener(MekUtRecipeType.RECIPE_TYPES));
    }
}
