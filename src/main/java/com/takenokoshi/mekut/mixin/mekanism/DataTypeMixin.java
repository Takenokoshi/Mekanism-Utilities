package com.takenokoshi.mekut.mixin.mekanism;

import java.util.Arrays;
import java.util.function.IntFunction;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.serialization.Codec;
import com.takenokoshi.mekut.enums.MekUtDataType;

import io.netty.buffer.ByteBuf;
import mekanism.api.text.EnumColor;
import mekanism.api.text.ILangEntry;
import mekanism.common.tile.component.config.DataType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.ByIdMap.OutOfBoundsStrategy;
import net.minecraft.util.StringRepresentable;

@Mixin(value = { DataType.class }, remap = false)
public class DataTypeMixin {

    @Shadow
    @Final
    @Mutable
    private static Codec<DataType> CODEC;
    @Shadow
    @Final
    @Mutable
    private static IntFunction<DataType> BY_ID;
    @Shadow
    @Final
    @Mutable
    private static StreamCodec<ByteBuf, DataType> STREAM_CODEC;

    @Shadow
    @Final
    @Mutable
    @SuppressWarnings("target")
    static DataType[] $VALUES;

    @Invoker("<init>")
    private static DataType mekanism_utilities$invokeNew(String name, int num, ILangEntry langEntry,
            EnumColor enumColor) {
        return null;
    };

    @Unique
    private static DataType mekanism_utilities$createNew(MekUtDataType holder, EnumColor enumColor) {
        int index = $VALUES.length;
        DataType result = mekanism_utilities$invokeNew("MEKANISM_UTILITIES$" + holder.name().toUpperCase(), index,
                holder.descKey, enumColor);
        DataType[] newValues = Arrays.copyOf($VALUES, index + 1);
        newValues[index] = result;
        $VALUES = newValues;
        holder.setValue(result);
        return result;
    }

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void mekanism_utilities$clinitInject(CallbackInfo ci) {
        mekanism_utilities$createNew(MekUtDataType.INPUT1_OUTPUT1, EnumColor.PURPLE);
        mekanism_utilities$createNew(MekUtDataType.INPUT2_OUTPUT2, EnumColor.BRIGHT_GREEN);
        mekanism_utilities$createNew(MekUtDataType.INPUT_OUTPUT1, EnumColor.PURPLE);
        mekanism_utilities$createNew(MekUtDataType.INPUT_OUTPUT2, EnumColor.BROWN);
        CODEC = StringRepresentable.fromEnum(DataType::values);
        BY_ID = ByIdMap.continuous(Enum::ordinal, DataType.values(), OutOfBoundsStrategy.WRAP);
        STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, Enum::ordinal);
    }

    @ModifyReturnValue(at = { @At("RETURN") }, method = { "canOutput" })
    private boolean mekanism_utilities$modifyCanOutput(boolean original) {
        DataType self = (DataType) (Object) this;
        return original || MekUtDataType.INPUT1_OUTPUT1.is(self) || MekUtDataType.INPUT2_OUTPUT2.is(self)
                || MekUtDataType.INPUT_OUTPUT1.is(self) || MekUtDataType.INPUT_OUTPUT2.is(self);
    }

}
