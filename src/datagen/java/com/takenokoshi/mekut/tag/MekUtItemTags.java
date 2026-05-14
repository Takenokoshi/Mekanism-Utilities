package com.takenokoshi.mekut.tag;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import com.takenokoshi.mekut.enums.MUMaterial;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class MekUtItemTags {

    public static final Map<MUMaterial, TagKey<Item>> RAW_MU_MATERIALS_BLOCK = createMUMaterialTags(
            m -> true,
            m -> ResourceLocation.fromNamespaceAndPath("c", "storage_blocks/raw_" + m.name));

    public static final Map<MUMaterial, TagKey<Item>> RAW_MU_MATERIALS = createMUMaterialTags(
            m -> true,
            m -> ResourceLocation.fromNamespaceAndPath("c", "raw_materials/" + m.name));

    public static final Map<MUMaterial, TagKey<Item>> MU_MATERIALS_CRYSTAL = createMUMaterialTags(
            m -> true,
            m -> ResourceLocation.fromNamespaceAndPath("c", "crystals/" + m.name));

    public static final Map<MUMaterial, TagKey<Item>> MU_MATERIALS_SHARD = createMUMaterialTags(
            m -> true,
            m -> ResourceLocation.fromNamespaceAndPath("c", "shards/" + m.name));

    public static final Map<MUMaterial, TagKey<Item>> MU_MATERIALS_CLUMP = createMUMaterialTags(
            m -> true,
            m -> ResourceLocation.fromNamespaceAndPath("c", "clumps/" + m.name));

    public static final Map<MUMaterial, TagKey<Item>> MU_MATERIALS_DIRTY_DUST = createMUMaterialTags(
            m -> !m.isGem,
            m -> ResourceLocation.fromNamespaceAndPath("c", "dirty_dusts/" + m.name));

    private static Map<MUMaterial, TagKey<Item>> createMUMaterialTags(
            Predicate<MUMaterial> shouldCreate,
            Function<MUMaterial, ResourceLocation> rlBulder) {
        EnumMap<MUMaterial, TagKey<Item>> result = new EnumMap<>(MUMaterial.class);
        for (MUMaterial material : MUMaterial.values()) {
            if (shouldCreate.test(material)) {
                result.put(material, ItemTags.create(rlBulder.apply(material)));
            }
        }
        return Collections.unmodifiableMap(result);
    }
}
