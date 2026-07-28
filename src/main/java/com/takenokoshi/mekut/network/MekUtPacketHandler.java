package com.takenokoshi.mekut.network;

import com.takenokoshi.mekut.network.to_server.PacketGuiSetBurnRate;
import com.takenokoshi.mekut.network.to_server.PacketGuiXpTank;

import mekanism.common.lib.Version;
import mekanism.common.network.BasePacketHandler;
import net.neoforged.bus.api.IEventBus;

public class MekUtPacketHandler extends BasePacketHandler {

    public MekUtPacketHandler(IEventBus modEventBus, Version version) {
        super(modEventBus, version);
    }

    @Override
    protected void registerClientToServer(PacketRegistrar registrar) {
        registrar.play(PacketGuiSetBurnRate.TYPE, PacketGuiSetBurnRate.STREAM_CODEC);
        registrar.play(PacketGuiXpTank.TYPE, PacketGuiXpTank.STREAM_CODEC);
    }

    @Override
    protected void registerServerToClient(PacketRegistrar registrar) {
    }
    
}
