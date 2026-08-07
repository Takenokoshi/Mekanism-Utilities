package com.takenokoshi.mekut.network.to_server;

import com.takenokoshi.mekut.blockentity.interfaces.IRatioSplitter;
import com.takenokoshi.mekut.core.MekUtConstants;

import io.netty.buffer.ByteBuf;
import mekanism.common.network.IMekanismPacket;
import mekanism.common.network.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record PacketGuiRatioSplitter(BlockPos pos, int value, boolean is2) implements IMekanismPacket {

    public static final CustomPacketPayload.Type<PacketGuiRatioSplitter> TYPE = new CustomPacketPayload.Type<>(
            MekUtConstants.rl("ratio_splitter"));

    public static final StreamCodec<ByteBuf, PacketGuiRatioSplitter> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, PacketGuiRatioSplitter::pos,
            ByteBufCodecs.INT, PacketGuiRatioSplitter::value,
            ByteBufCodecs.BOOL, PacketGuiRatioSplitter::is2,
            PacketGuiRatioSplitter::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    @Override
    public void handle(IPayloadContext context) {
        if (PacketUtils.blockEntity(context, pos) instanceof IRatioSplitter splitter) {
            if (is2) {
                splitter.setRatio2(value);
            } else {
                splitter.setRatio1(value);
            }
        }
    }

}
