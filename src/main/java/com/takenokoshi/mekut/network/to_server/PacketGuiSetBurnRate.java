package com.takenokoshi.mekut.network.to_server;

import org.jetbrains.annotations.NotNull;

import com.takenokoshi.mekut.blockentity.packet.IBurnRatePacketAcceptor;
import com.takenokoshi.mekut.core.MekUtConstants;

import io.netty.buffer.ByteBuf;
import mekanism.common.network.IMekanismPacket;
import mekanism.common.network.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record PacketGuiSetBurnRate(BlockPos pos, long value) implements IMekanismPacket {

    public static final CustomPacketPayload.Type<PacketGuiSetBurnRate> TYPE = new CustomPacketPayload.Type<>(
            MekUtConstants.rl("set_burn_rate"));
    public static final StreamCodec<ByteBuf, PacketGuiSetBurnRate> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, PacketGuiSetBurnRate::pos,
            ByteBufCodecs.VAR_LONG, PacketGuiSetBurnRate::value,
            PacketGuiSetBurnRate::new);

    @NotNull
    @Override
    public CustomPacketPayload.Type<PacketGuiSetBurnRate> type() {
        return TYPE;
    }

    @Override
    public void handle(IPayloadContext context) {
        if (PacketUtils.blockEntity(context, pos) instanceof IBurnRatePacketAcceptor acceptor) {
            acceptor.setBurnRate(value);
        }
    }

}
