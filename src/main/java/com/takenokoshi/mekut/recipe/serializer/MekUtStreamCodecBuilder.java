package com.takenokoshi.mekut.recipe.serializer;

import java.util.function.Function;

import com.mojang.datafixers.util.Function8;

import net.minecraft.network.codec.StreamCodec;

public final class MekUtStreamCodecBuilder {
    public static <B, C, T1, T2, T3, T4, T5, T6, T7, T8> StreamCodec<B, C> composite08(
            StreamCodec<? super B, T1> codec1, Function<C, T1> getter1,
            StreamCodec<? super B, T2> codec2, Function<C, T2> getter2,
            StreamCodec<? super B, T3> codec3, Function<C, T3> getter3,
            StreamCodec<? super B, T4> codec4, Function<C, T4> getter4,
            StreamCodec<? super B, T5> codec5, Function<C, T5> getter5,
            StreamCodec<? super B, T6> codec6, Function<C, T6> getter6,
            StreamCodec<? super B, T7> codec7, Function<C, T7> getter7,
            StreamCodec<? super B, T8> codec8, Function<C, T8> getter8,
            Function8<T1, T2, T3, T4, T5, T6, T7, T8, C> factory) {
        return new StreamCodec<>() {

            @Override
            public C decode(B buf) {
                return factory.apply(
                        codec1.decode(buf),
                        codec2.decode(buf),
                        codec3.decode(buf),
                        codec4.decode(buf),
                        codec5.decode(buf),
                        codec6.decode(buf),
                        codec7.decode(buf),
                        codec8.decode(buf));
            }

            @Override
            public void encode(B buf, C value) {
                codec1.encode(buf, getter1.apply(value));
                codec2.encode(buf, getter2.apply(value));
                codec3.encode(buf, getter3.apply(value));
                codec4.encode(buf, getter4.apply(value));
                codec5.encode(buf, getter5.apply(value));
                codec6.encode(buf, getter6.apply(value));
                codec7.encode(buf, getter7.apply(value));
                codec8.encode(buf, getter8.apply(value));
            }

        };
    }
}
