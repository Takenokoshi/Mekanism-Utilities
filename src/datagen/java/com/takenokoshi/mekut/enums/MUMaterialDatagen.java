package com.takenokoshi.mekut.enums;

import java.util.EnumMap;

import com.takenokoshi.mekut.registries.MekUtBlocks;

import appeng.core.definitions.AEItems;
import mekanism.common.registries.MekanismBlocks;
import mekanism.common.registries.MekanismItems;
import mekanism.common.resource.ore.OreType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class MUMaterialDatagen {

    public static final EnumMap<MUMaterial, ItemStack> FINAL_ITEMS_MAP;
    public static final EnumMap<MUMaterial, OreData> ORE_MAP;
    static {
        FINAL_ITEMS_MAP = new EnumMap<>(MUMaterial.class);
        // conut should be 1
        FINAL_ITEMS_MAP.put(MUMaterial.AMETHYST, new ItemStack(Items.AMETHYST_SHARD));
        FINAL_ITEMS_MAP.put(MUMaterial.CERTUS_QUARTZ, new ItemStack(AEItems.CERTUS_QUARTZ_CRYSTAL));
        FINAL_ITEMS_MAP.put(MUMaterial.COAL, new ItemStack(Items.COAL));
        FINAL_ITEMS_MAP.put(MUMaterial.DIAMOND, new ItemStack(Items.DIAMOND));
        FINAL_ITEMS_MAP.put(MUMaterial.EMERALD, new ItemStack(Items.EMERALD));
        FINAL_ITEMS_MAP.put(MUMaterial.FLUORITE, new ItemStack(MekanismItems.FLUORITE_GEM.asItem()));
        FINAL_ITEMS_MAP.put(MUMaterial.LAPIS_LAZULI, new ItemStack(Items.LAPIS_LAZULI));
        FINAL_ITEMS_MAP.put(MUMaterial.NETHERITE, new ItemStack(MekanismItems.NETHERITE_DUST.asItem()));
        FINAL_ITEMS_MAP.put(MUMaterial.QUARTZ, new ItemStack(Items.QUARTZ));
        FINAL_ITEMS_MAP.put(MUMaterial.REDSTONE, new ItemStack(Items.REDSTONE));

        ORE_MAP = new EnumMap<>(MUMaterial.class);
        ORE_MAP.put(MUMaterial.AMETHYST, new OreData(
                new ItemStack(MekUtBlocks.AMETHYST_ORE), null, null));
        ORE_MAP.put(MUMaterial.CERTUS_QUARTZ, new OreData(
                new ItemStack(MekUtBlocks.CERTUS_QUARTZ_ORE), null, null));
        ORE_MAP.put(MUMaterial.COAL, new OreData(
                new ItemStack(Items.COAL_ORE),
                new ItemStack(Items.DEEPSLATE_COAL_ORE), null));
        ORE_MAP.put(MUMaterial.DIAMOND, new OreData(
                new ItemStack(Items.DIAMOND_ORE),
                new ItemStack(Items.DEEPSLATE_DIAMOND_ORE), null));
        ORE_MAP.put(MUMaterial.EMERALD, new OreData(
                new ItemStack(Items.EMERALD_ORE),
                new ItemStack(Items.DEEPSLATE_EMERALD_ORE), null));
        ORE_MAP.put(MUMaterial.FLUORITE, new OreData(
                new ItemStack(MekanismBlocks.ORES.get(OreType.FLUORITE).stone()),
                new ItemStack(MekanismBlocks.ORES.get(OreType.FLUORITE).deepslate()), null));
        ORE_MAP.put(MUMaterial.LAPIS_LAZULI, new OreData(
                new ItemStack(Items.LAPIS_ORE),
                new ItemStack(Items.DEEPSLATE_LAPIS_ORE), null));
        ORE_MAP.put(MUMaterial.NETHERITE, new OreData(
                null, null, new ItemStack(MekUtBlocks.NETHERITE_ORE)));
        ORE_MAP.put(MUMaterial.QUARTZ, new OreData(
                null, null, new ItemStack(Items.NETHER_QUARTZ_ORE)));
        ORE_MAP.put(MUMaterial.REDSTONE, new OreData(
                new ItemStack(Items.REDSTONE_ORE),
                new ItemStack(Items.DEEPSLATE_REDSTONE_ORE), null));
    }

    public static record OreData(ItemStack stone, ItemStack deepslate, ItemStack netherrack) {
    }

}
