package takenokoshi.mekal.common.network.to_server;

import io.netty.buffer.ByteBuf;
import mekanism.common.network.IMekanismPacket;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import takenokoshi.mekal.common.MekAL;
import takenokoshi.mekal.common.inventory.container.MekALMultiPageContainer;

public record PacketGuiMultiPageSync(int page) implements IMekanismPacket{

    public static final CustomPacketPayload.Type<PacketGuiMultiPageSync> TYPE = new CustomPacketPayload.Type<>(
            ResourceLocation.fromNamespaceAndPath(MekAL.MODID, "multi_page_sync"));

    public static final StreamCodec<ByteBuf, PacketGuiMultiPageSync> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, PacketGuiMultiPageSync::page, PacketGuiMultiPageSync::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    @Override
    public void handle(IPayloadContext context) {
        if (context.player().containerMenu instanceof MekALMultiPageContainer container) {
            container.setPage(page);
        }
    }
}
