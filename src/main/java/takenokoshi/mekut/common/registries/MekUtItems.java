package takenokoshi.mekut.common.registries;

import java.util.EnumMap;

import mekanism.common.registration.impl.ItemDeferredRegister;
import mekanism.common.registration.impl.ItemRegistryObject;
import net.minecraft.world.item.Item;
import takenokoshi.mekut.common.MekUt;

public class MekUtItems {
    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(MekUt.MODID);

    public static final ItemRegistryObject<Item> ENRICHED_GLOWSTONE = ITEMS.register("enriched_glowstone");

    public static final ItemRegistryObject<Item> CALCIUM_CARBIDE_DUST = ITEMS.register("calcium_carbide_dust");
    public static final ItemRegistryObject<Item> CALCIUM_HYDROXIDE_DUST = ITEMS.register("calcium_hydroxide_dust");

    public static final ItemRegistryObject<Item> NONWOVEN_FABRIC = ITEMS.register("nonwoven_fabric");

    public static final ItemRegistryObject<Item> RAW_DIAMOND = ITEMS.register("raw_daimond");

    /** 原石は鉱石抽出機での加工拡張の成果物枠 */
    public static final EnumMap<MekUtMetalType, ItemRegistryObject<Item>> METAL_RAW_MATERIALS = new EnumMap<>(
            MekUtMetalType.class);
    public static final EnumMap<MekUtMetalType, ItemRegistryObject<Item>> METAL_CRYSTALS = new EnumMap<>(
            MekUtMetalType.class);
    public static final EnumMap<MekUtMetalType, ItemRegistryObject<Item>> METAL_SHARDS = new EnumMap<>(
            MekUtMetalType.class);
    public static final EnumMap<MekUtMetalType, ItemRegistryObject<Item>> METAL_CLUMPS = new EnumMap<>(
            MekUtMetalType.class);
    public static final EnumMap<MekUtMetalType, ItemRegistryObject<Item>> METAL_DIRTY_DUSTS = new EnumMap<>(
            MekUtMetalType.class);
    public static final EnumMap<MekUtMetalType, ItemRegistryObject<Item>> METAL_DUSTS = new EnumMap<>(
            MekUtMetalType.class);
    public static final EnumMap<MekUtMetalType, ItemRegistryObject<Item>> METAL_INGOTS = new EnumMap<>(
            MekUtMetalType.class);

    static {
        for (final MekUtMetalType metalType : MekUtMetalType.values()) {
            METAL_RAW_MATERIALS.put(metalType, ITEMS.register("raw_" + metalType.name));
            METAL_CRYSTALS.put(metalType, ITEMS.register(metalType.name + "_crystal"));
            METAL_SHARDS.put(metalType, ITEMS.register(metalType.name + "_shard"));
            METAL_CLUMPS.put(metalType, ITEMS.register(metalType.name + "_clump"));
            METAL_DIRTY_DUSTS.put(metalType, ITEMS.register("dirty_" + metalType.name + "_dust"));
            // レッドストーンは粉とインゴットの登録をスキップする
            if (metalType == MekUtMetalType.REDSTONE) {
                continue;
            }
            METAL_DUSTS.put(metalType, ITEMS.register(metalType.name + "_dust"));
            METAL_INGOTS.put(metalType, ITEMS.register(metalType.name + "_ingot"));
        }
    }

}
