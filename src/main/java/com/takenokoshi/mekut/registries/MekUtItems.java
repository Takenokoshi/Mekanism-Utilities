package com.takenokoshi.mekut.registries;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.enums.MUMaterial;
import com.takenokoshi.mekut.item.XpCrystalItem;
import com.takenokoshi.mekut.item.cell.rainbow.InfinityRainbowCellItem;

import mekanism.common.registration.impl.ItemDeferredRegister;
import mekanism.common.registration.impl.ItemRegistryObject;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class MekUtItems {
    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(MekUtConstants.MODID);

    public static final ItemRegistryObject<Item> ELASTIC_ALLOY = ITEMS.register("elastic_alloy");
    public static final ItemRegistryObject<Item> CONVERGENT_ALLOY = ITEMS.register("convergent_alloy");
    public static final ItemRegistryObject<Item> XP_ALLOY = ITEMS.register("xp_alloy");

    public static final ItemRegistryObject<Item> DIGITAL_CONTROL_CIRCUIT = ITEMS.register("digital_control_circuit");
    public static final ItemRegistryObject<Item> STANDARD_CONTROL_CIRCUIT = ITEMS.register("standard_control_circuit");
    public static final ItemRegistryObject<Item> ACCELERATION_CONTROL_CIRCUIT = ITEMS
            .register("acceleration_control_circuit");
    public static final ItemRegistryObject<Item> CHEMICAL_CONTROL_CIRCUIT = ITEMS
            .register("chemical_control_circuit");
    public static final ItemRegistryObject<Item> KNOWLEDGE_CONTROL_CIRCUIT = ITEMS
            .register("knowladge_control_circuit");

    public static final ItemRegistryObject<Item> ENRICHED_LAPIS_LAZULI = ITEMS.register("enriched_lapis_lazuli");
    public static final ItemRegistryObject<Item> ENRICHED_SINGULARITY = ITEMS.register("enriched_singularity");

    public static final ItemRegistryObject<Item> GOLDEN_REDSTONE = ITEMS.register("golden_redstone");
    public static final ItemRegistryObject<Item> AMETHYST_DUST = ITEMS.register("amethyst_dust");
    public static final ItemRegistryObject<Item> REFINED_AMETHYST_INGOT = ITEMS.register("refined_amethyst_ingot");
    public static final ItemRegistryObject<XpCrystalItem> XP_CRYSTAL = ITEMS.registerItem("xp_crystal",
            XpCrystalItem::new);
    public static final ItemRegistryObject<Item> ACTIVATED_LAPIS_LAZULI = ITEMS.registerItem("activated_lapis_lazuli",
            p -> new Item(p) {
                @Override
                public boolean isFoil(ItemStack stack) {
                    return true;
                }
            });

    public static final ItemRegistryObject<Item> DARK_RED_DYE = ITEMS.register("dark_red_dye");
    public static final ItemRegistryObject<Item> AQUA_DYE = ITEMS.register("aqua_dye");

    public static final ItemRegistryObject<InfinityRainbowCellItem> ME_INFINITY_RAINBOW_CELL = ITEMS
            .registerItem("me_infinity_rainbow_cell", InfinityRainbowCellItem::new);

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
