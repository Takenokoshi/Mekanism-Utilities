package com.takenokoshi.mekut.network.to_server;

import com.takenokoshi.mekut.blockentity.normalmachine.BlockEntityXpTank;
import com.takenokoshi.mekut.core.MekUtConstants;

import io.netty.buffer.ByteBuf;
import mekanism.common.network.IMekanismPacket;
import mekanism.common.network.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record PacketGuiXpTank(BlockPos pos, int toGive) implements IMekanismPacket {

    public static final CustomPacketPayload.Type<PacketGuiXpTank> TYPE = new CustomPacketPayload.Type<>(
            MekUtConstants.rl("xp_tank"));

    public static final StreamCodec<ByteBuf, PacketGuiXpTank> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, PacketGuiXpTank::pos,
            ByteBufCodecs.INT, PacketGuiXpTank::toGive,
            PacketGuiXpTank::new);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    @Override
    public void handle(IPayloadContext context) {
        if (PacketUtils.blockEntity(context, pos) instanceof BlockEntityXpTank xpTank) {
            xpTank.giveXpToPlayer(context.player(), toGive);
        }
    }
}
