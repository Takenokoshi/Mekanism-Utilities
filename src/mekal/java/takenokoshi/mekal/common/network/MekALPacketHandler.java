package takenokoshi.mekal.common.network;

import mekanism.common.lib.Version;
import mekanism.common.network.BasePacketHandler;
import net.neoforged.bus.api.IEventBus;
import takenokoshi.mekal.common.network.to_server.PacketGuiMekALFactoryToggleSort;
import takenokoshi.mekal.common.network.to_server.PacketGuiMultiPageSync;

public class MekALPacketHandler extends BasePacketHandler {

    public MekALPacketHandler(IEventBus modEventBus, Version version) {
        super(modEventBus, version);
    }

    @Override
    protected void registerClientToServer(PacketRegistrar registrar) {
        registrar.play(PacketGuiMekALFactoryToggleSort.TYPE, PacketGuiMekALFactoryToggleSort.STREAM_CODEC);
        registrar.play(PacketGuiMultiPageSync.TYPE, PacketGuiMultiPageSync.STREAM_CODEC);
    }

    @Override
    protected void registerServerToClient(PacketRegistrar registrar) {
    }
    
}
