package com.takenokoshi.mekut.enums;

import java.util.EnumMap;

import appeng.core.definitions.AEItems;
import mekanism.common.registries.MekanismItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class MUMaterialDatagen {

    public static final EnumMap<MUMaterial, ItemStack> FINAL_ITEMS_MAP;
    static {
        FINAL_ITEMS_MAP = new EnumMap<>(MUMaterial.class);
        //conut should be 1
        FINAL_ITEMS_MAP.put(MUMaterial.AMETHYST, new ItemStack(Items.AMETHYST_SHARD, 1));
        FINAL_ITEMS_MAP.put(MUMaterial.CERTUS_QUARTZ, new ItemStack(AEItems.CERTUS_QUARTZ_CRYSTAL, 1));
        FINAL_ITEMS_MAP.put(MUMaterial.COAL, new ItemStack(Items.COAL, 1));
        FINAL_ITEMS_MAP.put(MUMaterial.DIAMOND, new ItemStack(Items.DIAMOND, 1));
        FINAL_ITEMS_MAP.put(MUMaterial.EMERALD, new ItemStack(Items.EMERALD, 1));
        FINAL_ITEMS_MAP.put(MUMaterial.FLUORITE, new ItemStack(MekanismItems.FLUORITE_GEM.asItem(), 1));
        FINAL_ITEMS_MAP.put(MUMaterial.LAPIS_LAZULI, new ItemStack(Items.LAPIS_LAZULI, 1));
        FINAL_ITEMS_MAP.put(MUMaterial.NETHERITE, new ItemStack(MekanismItems.NETHERITE_DUST.asItem(), 1));
        FINAL_ITEMS_MAP.put(MUMaterial.QUARTZ, new ItemStack(Items.QUARTZ, 1));
        FINAL_ITEMS_MAP.put(MUMaterial.REDSTONE, new ItemStack(Items.REDSTONE, 1));
    }

}
