package com.takenokoshi.mekut.item;

import java.util.function.Function;

import mekanism.api.text.TextComponentUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class MekUtBasicItem extends Item {

    private final int color;
    private final boolean isFoil;

    public static Function<Item.Properties, MekUtBasicItem> getBuilder(int color, boolean isFoil) {
        return (props) -> {
            return new MekUtBasicItem(props, color, isFoil);
        };
    }

    public MekUtBasicItem(Properties properties, int color, boolean isFoil) {
        super(properties);
        this.color = color;
        this.isFoil = isFoil;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return isFoil;
    }

    @Override
    public Component getName(ItemStack stack) {
        return TextComponentUtil.color(super.getName(stack).copy(), color);
    }

}
