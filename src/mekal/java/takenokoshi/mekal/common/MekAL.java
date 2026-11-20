package takenokoshi.mekal.common;

import mekanism.common.lib.Version;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import takenokoshi.mekal.common.network.MekALPacketHandler;
import takenokoshi.mekal.common.registries.MekALRecipeSerializersInternal;

@Mod(value = MekAL.MODID)
public class MekAL {
    public static final String MODID = "mekal";
    private static MekAL instance;

    public final Version versionNumber;
    private final MekALPacketHandler packetHandler;

    public MekAL(IEventBus modEventBus, ModContainer modContainer) {
        versionNumber = new Version(modContainer);
        MekALRecipeSerializersInternal.RECIPE_SERIALIZERS.register(modEventBus);
        this.packetHandler = new MekALPacketHandler(modEventBus, versionNumber);
        instance = this;
    }

    public static MekALPacketHandler packetHandler() {
        return instance.packetHandler;
    }
}
