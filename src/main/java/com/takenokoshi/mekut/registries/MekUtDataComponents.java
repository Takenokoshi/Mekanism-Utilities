package com.takenokoshi.mekut.registries;

import java.math.BigInteger;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.takenokoshi.mekut.core.MekUtConstants;

import appeng.api.stacks.AEKey;
import io.netty.buffer.ByteBuf;
import mekanism.common.registration.MekanismDeferredHolder;
import mekanism.common.registration.impl.DataComponentDeferredRegister;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class MekUtDataComponents {
    public static final DataComponentDeferredRegister DATA_COMPONENTS = new DataComponentDeferredRegister(
            MekUtConstants.MODID);

    public static final Codec<BigInteger> BIG_INTEGER_CODEC = Codec.STRING.flatXmap(
            str -> {
                try {
                    return DataResult.success(new BigInteger(str));
                } catch (NumberFormatException e) {
                    return DataResult.error(e::getMessage);
                }
            },
            v -> DataResult.success(v.toString()));
    public static final StreamCodec<ByteBuf, BigInteger> BIG_INTEGER_STREAM_CODEC = ByteBufCodecs.STRING_UTF8.map(
            BigInteger::new,
            BigInteger::toString);

    public static final MekanismDeferredHolder<DataComponentType<?>, DataComponentType<AEKey>> BULK_CELL_KEY = DATA_COMPONENTS
            .simple("bulk_cell_key", builder -> builder.persistent(AEKey.CODEC)
                    .networkSynchronized(AEKey.STREAM_CODEC)
                    .cacheEncoding());

    public static final MekanismDeferredHolder<DataComponentType<?>, DataComponentType<BigInteger>> BULK_CELL_UNIT_COUNT = DATA_COMPONENTS
            .simple("bulk_cell_unit_count", builder -> builder.persistent(BIG_INTEGER_CODEC)
                    .networkSynchronized(BIG_INTEGER_STREAM_CODEC)
                    .cacheEncoding());

}
