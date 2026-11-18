package takenokoshi.mekal.common.network.to_server;

import io.netty.buffer.ByteBuf;
import mekanism.common.network.IMekanismPacket;
import mekanism.common.util.WorldUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import takenokoshi.mekal.common.MekAL;
import takenokoshi.mekal.common.tile.interfaces.ISortableFactory;

public record PacketGuiMekALFactoryToggleSort(BlockPos pos) implements IMekanismPacket {

    public static final CustomPacketPayload.Type<PacketGuiMekALFactoryToggleSort> TYPE = new CustomPacketPayload.Type<>(
            ResourceLocation.fromNamespaceAndPath(MekAL.MODID, "factory_toggle_sort"));

    public static final StreamCodec<ByteBuf, PacketGuiMekALFactoryToggleSort> STREAM_CODEC = StreamCodec
            .composite(BlockPos.STREAM_CODEC, PacketGuiMekALFactoryToggleSort::pos,
                    PacketGuiMekALFactoryToggleSort::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    @Override
    public void handle(IPayloadContext context) {
        if (WorldUtils.getTileEntity(context.player().level(), pos) instanceof ISortableFactory factory) {
            factory.toggleSorting();
        }
    }
}
