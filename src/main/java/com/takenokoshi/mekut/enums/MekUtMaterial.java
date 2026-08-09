package com.takenokoshi.mekut.enums;

import java.util.List;

import com.glodblock.github.extendedae.ExtendedAE;
import com.glodblock.github.extendedae.common.EAESingletons;
import com.moakiee.ae2lt.AE2LightningTech;
import com.moakiee.ae2lt.registry.ModItems;
import com.takenokoshi.mekut.registries.MekUtBlocks;
import com.takenokoshi.mekut.registries.MekUtChemicals;
import com.takenokoshi.mekut.registries.MekUtItems;

import appeng.core.AppEng;
import appeng.core.definitions.AEItems;
import mekanism.api.chemical.Chemical;
import mekanism.common.registration.impl.BlockRegistryObject;
import mekanism.common.registration.impl.ItemRegistryObject;
import mekanism.common.registries.MekanismItems;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public record MekUtMaterial(String name, ItemRegistryObject<?> raw, BlockRegistryObject<?, ?> rawBlock,
        Holder<Chemical> dirtySlurry, Holder<Chemical> cleanSlurry,
        ItemRegistryObject<?> crystal,
        ItemRegistryObject<?> shard,
        ItemRegistryObject<?> clump,
        ItemLike dust, ItemLike finalItem, int oreNeeded, int produceRate, String[] requiredMods) {

    public TagKey<Item> oreTag() {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ores/" + name));
    }

    public TagKey<Item> rawTag() {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "raw_materials/" + name));
    }

    public TagKey<Item> rawBlockTag() {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "storage_blocks/raw_" + name));
    }

    public TagKey<Item> crystalTag() {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "crystals/" + name));
    }

    public TagKey<Item> shardTag() {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "shards/" + name));
    }

    public TagKey<Item> clumpTag() {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "clumps/" + name));
    }

    public static final MekUtMaterial AMETHYST = new MekUtMaterial("amethyst",
            MekUtItems.RAW_AMETHYST,
            MekUtBlocks.RAW_AMETHYST_BLOCK,
            MekUtChemicals.DIRTY_AMETHYST_SLURRY,
            MekUtChemicals.CLEAN_AMETHYST_SLURRY,
            MekUtItems.AMETHYST_CRYSTAL,
            MekUtItems.AMETHYST_SHARD,
            MekUtItems.AMETHYST_CLUMP,
            MekUtItems.AMETHYST_DUST,
            Items.AMETHYST_SHARD,
            1,
            12,
            new String[] {});

    public static final MekUtMaterial CERTUS_QUARTZ = new MekUtMaterial("certus_quartz",
            MekUtItems.RAW_CERTUS_QUARTZ,
            MekUtBlocks.RAW_CERTUS_QUARTZ_BLOCK,
            MekUtChemicals.DIRTY_CERTUS_QUARTZ_SLURRY,
            MekUtChemicals.CLEAN_CERTUS_QUARTZ_SLURRY,
            MekUtItems.CERTUS_QUARTZ_CRYSTAL,
            MekUtItems.CERTUS_QUARTZ_SHARD,
            MekUtItems.CERTUS_QUARTZ_CLUMP,
            AEItems.CERTUS_QUARTZ_DUST,
            AEItems.CERTUS_QUARTZ_CRYSTAL,
            1,
            12,
            new String[] { AppEng.MOD_ID, });

    public static final MekUtMaterial COAL = new MekUtMaterial("coal",
            MekUtItems.RAW_COAL,
            MekUtBlocks.RAW_COAL_BLOCK,
            MekUtChemicals.DIRTY_COAL_SLURRY,
            MekUtChemicals.CLEAN_COAL_SLURRY,
            MekUtItems.COAL_CRYSTAL,
            MekUtItems.COAL_SHARD,
            MekUtItems.COAL_CLUMP,
            MekanismItems.COAL_DUST,
            Items.COAL,
            1,
            3,
            new String[] {});

    public static final MekUtMaterial DIAMOND = new MekUtMaterial("diamond",
            MekUtItems.RAW_DIAMOND,
            MekUtBlocks.RAW_DIAMOND_BLOCK,
            MekUtChemicals.DIRTY_DIAMOND_SLURRY,
            MekUtChemicals.CLEAN_DIAMOND_SLURRY,
            MekUtItems.DIAMOND_CRYSTAL,
            MekUtItems.DIAMOND_SHARD,
            MekUtItems.DIAMOND_CLUMP,
            MekanismItems.DIAMOND_DUST,
            Items.DIAMOND,
            1,
            3,
            new String[] {});

    public static final MekUtMaterial EMERALD = new MekUtMaterial("emerald",
            MekUtItems.RAW_EMERALD,
            MekUtBlocks.RAW_EMERALD_BLOCK,
            MekUtChemicals.DIRTY_EMERALD_SLURRY,
            MekUtChemicals.CLEAN_EMERALD_SLURRY,
            MekUtItems.EMERALD_CRYSTAL,
            MekUtItems.EMERALD_SHARD,
            MekUtItems.EMERALD_CLUMP,
            MekanismItems.EMERALD_DUST,
            Items.EMERALD,
            1,
            3,
            new String[] {});

    public static final MekUtMaterial ENTRO = new MekUtMaterial("entro",
            MekUtItems.RAW_ENTRO,
            MekUtBlocks.RAW_ENTRO_BLOCK,
            MekUtChemicals.DIRTY_ENTRO_SLURRY,
            MekUtChemicals.CLEAN_ENTRO_SLURRY,
            MekUtItems.ENTRO_CRYSTAL,
            MekUtItems.ENTRO_SHARD,
            MekUtItems.ENTRO_CLUMP,
            EAESingletons.ENTRO_DUST,
            EAESingletons.ENTRO_CRYSTAL,
            1,
            4,
            new String[] { ExtendedAE.MODID });

    public static final MekUtMaterial FLUORITE = new MekUtMaterial("fluorite",
            MekUtItems.RAW_FLUORITE,
            MekUtBlocks.RAW_FLUORITE_BLOCK,
            MekUtChemicals.DIRTY_FLUORITE_SLURRY,
            MekUtChemicals.CLEAN_FLUORITE_SLURRY,
            MekUtItems.FLUORITE_CRYSTAL,
            MekUtItems.FLUORITE_SHARD,
            MekUtItems.FLUORITE_CLUMP,
            MekanismItems.FLUORITE_DUST,
            MekanismItems.FLUORITE_GEM,
            2,
            9,
            new String[] {});

    public static final MekUtMaterial LAPIS_LAZULI = new MekUtMaterial("lapis_lazuli",
            MekUtItems.RAW_LAPIS_LAZULI,
            MekUtBlocks.RAW_LAPIS_LAZULI_BLOCK,
            MekUtChemicals.DIRTY_LAPIS_LAZULI_SLURRY,
            MekUtChemicals.CLEAN_LAPIS_LAZULI_SLURRY,
            MekUtItems.LAPIS_LAZULI_CRYSTAL,
            MekUtItems.LAPIS_LAZULI_SHARD,
            MekUtItems.LAPIS_LAZULI_CLUMP,
            MekanismItems.LAPIS_LAZULI_DUST,
            Items.LAPIS_LAZULI,
            1,
            18,
            new String[] {});

    public static final MekUtMaterial NETHERITE = new MekUtMaterial("netherite",
            MekUtItems.RAW_NETHERITE,
            MekUtBlocks.RAW_NETHERITE_BLOCK,
            MekUtChemicals.DIRTY_NETHERITE_SLURRY,
            MekUtChemicals.CLEAN_NETHERITE_SLURRY,
            MekUtItems.NETHERITE_CRYSTAL,
            MekUtItems.NETHERITE_SHARD,
            MekUtItems.NETHERITE_CLUMP,
            MekanismItems.NETHERITE_DUST,
            MekanismItems.NETHERITE_DUST,
            1,
            3,
            new String[] {});

    public static final MekUtMaterial OVERLOAD = new MekUtMaterial("overload",
            MekUtItems.RAW_OVERLOAD,
            MekUtBlocks.RAW_OVERLOAD_BLOCK,
            MekUtChemicals.DIRTY_OVERLOAD_SLURRY,
            MekUtChemicals.CLEAN_OVERLOAD_SLURRY,
            MekUtItems.OVERLOAD_CRYSTAL,
            MekUtItems.OVERLOAD_SHARD,
            MekUtItems.OVERLOAD_CLUMP,
            ModItems.OVERLOAD_CRYSTAL_DUST,
            ModItems.OVERLOAD_CRYSTAL,
            1,
            4,
            new String[] { AE2LightningTech.MODID });

    public static final MekUtMaterial QUARTZ = new MekUtMaterial("quartz",
            MekUtItems.RAW_QUATRZ,
            MekUtBlocks.RAW_QUARTZ_BLOCK,
            MekUtChemicals.DIRTY_QUARTZ_SLURRY,
            MekUtChemicals.CLEAN_QUARTZ_SLURRY,
            MekUtItems.QUATRZ_CRYSTAL,
            MekUtItems.QUATRZ_SHARD,
            MekUtItems.QUATRZ_CLUMP,
            MekanismItems.QUARTZ_DUST,
            Items.QUARTZ,
            1,
            4,
            new String[] {});

    public static final MekUtMaterial REDSTONE = new MekUtMaterial("redstone",
            MekUtItems.RAW_REDSTONE,
            MekUtBlocks.RAW_REDSTONE_BLOCK,
            MekUtChemicals.DIRTY_REDSTONE_SLURRY,
            MekUtChemicals.CLEAN_REDSTONE_SLURRY,
            MekUtItems.REDSTONE_CRYSTAL,
            MekUtItems.REDSTONE_SHARD,
            MekUtItems.REDSTONE_CLUMP,
            Items.REDSTONE,
            Items.REDSTONE,
            1,
            18,
            new String[] {});

    public static final MekUtMaterial SILICON = new MekUtMaterial("silicon",
            MekUtItems.RAW_SILICON,
            MekUtBlocks.RAW_SILICON_BLOCK,
            MekUtChemicals.DIRTY_SILICON_SLURRY,
            MekUtChemicals.CLEAN_SILICON_SLURRY,
            MekUtItems.SILICON_CRYSTAL,
            MekUtItems.SILICON_SHARD,
            MekUtItems.SILICON_CLUMP,
            MekUtItems.SILICON_DUST,
            AEItems.SILICON,
            1,
            4,
            new String[] { AppEng.MOD_ID });

    public static final List<MekUtMaterial> MATERIALS = List.of(new MekUtMaterial[] {
            AMETHYST, CERTUS_QUARTZ, COAL, DIAMOND, EMERALD, ENTRO, FLUORITE, LAPIS_LAZULI, NETHERITE, OVERLOAD, QUARTZ,
            REDSTONE, SILICON,
    });
}
