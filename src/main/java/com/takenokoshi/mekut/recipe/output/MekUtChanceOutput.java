package com.takenokoshi.mekut.recipe.output;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

public record MekUtChanceOutput(ItemStack value, double chance) {

    public static final MekUtChanceOutput EMPTY = new MekUtChanceOutput(ItemStack.EMPTY, 0.0d);

    public boolean isEmpty() {
        return value.isEmpty() || chance <= 0.0d;
    }

    public static final Codec<MekUtChanceOutput> CODEC = RecordCodecBuilder
            .create(instance -> instance
                    .group(
                            ItemStack.CODEC.fieldOf("value").forGetter(MekUtChanceOutput::value),
                            Codec.DOUBLE.fieldOf("chance").forGetter(MekUtChanceOutput::chance))
                    .apply(instance, MekUtChanceOutput::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, MekUtChanceOutput> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC,
            MekUtChanceOutput::value,
            ByteBufCodecs.DOUBLE,
            MekUtChanceOutput::chance,
            MekUtChanceOutput::new);

    @Override
    public final boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o != null && o instanceof MekUtChanceOutput other) {
            return this.chance == other.chance && this.value.equals(other.value);
        } else {
            return false;
        }
    }

    @Override
    public final int hashCode() {
        int result = value.getCount();
        result = result * 31 + ItemStack.hashItemAndComponents(value);
        result = result * 31 + Double.hashCode(chance);
        return result;
    }
}
