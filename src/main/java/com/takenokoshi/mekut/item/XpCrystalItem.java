package com.takenokoshi.mekut.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class XpCrystalItem extends Item {

    public XpCrystalItem(Properties properties) {
        super(properties.food(
                new FoodProperties.Builder().nutrition(12).alwaysEdible().saturationModifier(0.4f).fast().build()));
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

}