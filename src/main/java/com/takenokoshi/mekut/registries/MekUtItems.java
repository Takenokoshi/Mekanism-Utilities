package com.takenokoshi.mekut.registries;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.enums.MUMaterial;

import mekanism.common.registration.impl.ItemDeferredRegister;
import mekanism.common.registration.impl.ItemRegistryObject;
import net.minecraft.world.item.Item;

public class MekUtItems {
    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(MekUtConstants.MODID);

    public static final Map<MUMaterial, ItemRegistryObject<?>> RAW_MU_MATERIALS = registerMaterials(
            m -> true,
            m -> "raw_" + m.name,
            (m, p) -> new Item(p));

    public static final Map<MUMaterial, ItemRegistryObject<?>> MU_MATERIALS_CRYSTAL = registerMaterials(
            m -> true,
            m -> m.name + "_crystal",
            (m, p) -> new Item(p));

    public static final Map<MUMaterial, ItemRegistryObject<?>> MU_MATERIALS_SHARD = registerMaterials(
            m -> true,
            m -> m.name + "_shard",
            (m, p) -> new Item(p));

    public static final Map<MUMaterial, ItemRegistryObject<?>> MU_MATERIALS_CLUMP = registerMaterials(
            m -> true,
            m -> m.name + "_clump",
            (m, p) -> new Item(p));

    public static final Map<MUMaterial, ItemRegistryObject<?>> MU_MATERIALS_DIRTY_DUST = registerMaterials(
            m -> !m.isGem,
            m -> "dirty_" + m.name + "_dust",
            (m, p) -> new Item(p));

    private static Map<MUMaterial, ItemRegistryObject<?>> registerMaterials(
            Predicate<MUMaterial> shouldRegister,
            Function<MUMaterial, String> nameBulder,
            BiFunction<MUMaterial, Item.Properties, Item> creator) {
        EnumMap<MUMaterial, ItemRegistryObject<?>> result = new EnumMap<>(MUMaterial.class);
        for (MUMaterial material : MUMaterial.values()) {
            if (shouldRegister.test(material)) {
                result.put(material, ITEMS.registerItem(nameBulder.apply(material), p -> creator.apply(material, p)));
            }
        }
        return Collections.unmodifiableMap(result);
    }
}
