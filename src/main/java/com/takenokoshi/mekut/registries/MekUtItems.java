package com.takenokoshi.mekut.registries;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.item.ItemSupplierItem;
import com.takenokoshi.mekut.item.XpCrystalItem;
import mekanism.api.text.EnumColor;
import mekanism.common.registration.impl.ItemDeferredRegister;
import mekanism.common.registration.impl.ItemRegistryObject;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class MekUtItems {
    public static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(MekUtConstants.MODID);

    public static final ItemRegistryObject<Item> ARTIFICIAL_STAR = registerFoiling("artificial_star");

    public static final ItemRegistryObject<Item> ELASTIC_ALLOY = ITEMS
            .register("elastic_alloy", EnumColor.PINK);
    public static final ItemRegistryObject<Item> CONVERGENT_ALLOY = ITEMS
            .register("convergent_alloy", EnumColor.DARK_BLUE);
    public static final ItemRegistryObject<Item> COMPISITE_ALLOY = ITEMS
            .register("composite_alloy", EnumColor.DARK_GREEN);
    public static final ItemRegistryObject<Item> STARDUST_ALLOY = registerFoiling("stardust_alloy");

    public static final ItemRegistryObject<Item> DIGITAL_CONTROL_CIRCUIT = ITEMS
            .register("digital_control_circuit", EnumColor.PINK);
    public static final ItemRegistryObject<Item> STANDARD_CONTROL_CIRCUIT = ITEMS
            .register("standard_control_circuit", EnumColor.DARK_BLUE);
    public static final ItemRegistryObject<Item> AUGMENT_CONTROL_CIRCUIT = ITEMS
            .register("augment_control_circuit", EnumColor.DARK_GREEN);
    public static final ItemRegistryObject<Item> COMET_CONTROL_CIRCUIT = registerFoiling(
            "comet_control_circuit");

    public static final ItemRegistryObject<Item> ENRICHED_FLUIX = ITEMS.register("enriched_fluix");
    public static final ItemRegistryObject<Item> ENRICHED_SINGULARITY = ITEMS.register("enriched_singularity");

    public static final ItemRegistryObject<Item> GOLDEN_REDSTONE = ITEMS.register("golden_redstone");
    public static final ItemRegistryObject<Item> AMETHYST_DUST = ITEMS.register("amethyst_dust");
    public static final ItemRegistryObject<Item> IRIDIUM_DUST = ITEMS.register("iridium_dust");
    public static final ItemRegistryObject<Item> SILICON_DUST = ITEMS.register("silicon_dust");

    public static final ItemRegistryObject<Item> REFINED_AMETHYST_INGOT = ITEMS.register("refined_amethyst_ingot");
    public static final ItemRegistryObject<Item> IRIDIUM_INGOT = ITEMS.register("iridium_ingot");

    public static final ItemRegistryObject<XpCrystalItem> XP_CRYSTAL = ITEMS.registerItem("xp_crystal",
            XpCrystalItem::new);

    public static final ItemRegistryObject<Item> DARK_RED_DYE = ITEMS.register("dark_red_dye");
    public static final ItemRegistryObject<Item> AQUA_DYE = ITEMS.register("aqua_dye");

    public static final ItemRegistryObject<Item> ME_INFINITY_RAINBOW_CELL = ITEMS.register("me_infinity_rainbow_cell");

    public static final ItemRegistryObject<Item> SUPPLIER_BASE = ITEMS.register("supplier_base");
    public static final ItemRegistryObject<ItemSupplierItem> COBBLESTONE_SUPPLIER = ITEMS.registerItem(
            "cobblestone_supplier",
            ItemSupplierItem.getCreator(Items.COBBLESTONE));

    public static final ItemRegistryObject<Item> RAW_AMETHYST = ITEMS.register("raw_amethyst");
    public static final ItemRegistryObject<Item> RAW_CERTUS_QUARTZ = ITEMS.register("raw_certus_quartz");
    public static final ItemRegistryObject<Item> RAW_COAL = ITEMS.register("raw_coal");
    public static final ItemRegistryObject<Item> RAW_DIAMOND = ITEMS.register("raw_diamond");
    public static final ItemRegistryObject<Item> RAW_EMERALD = ITEMS.register("raw_emerald");
    public static final ItemRegistryObject<Item> RAW_ENTRO = ITEMS.register("raw_entro");
    public static final ItemRegistryObject<Item> RAW_FLUORITE = ITEMS.register("raw_fluorite");
    public static final ItemRegistryObject<Item> RAW_LAPIS_LAZULI = ITEMS.register("raw_lapis_lazuli");
    public static final ItemRegistryObject<Item> RAW_NETHERITE = ITEMS.register("raw_netherite");
    public static final ItemRegistryObject<Item> RAW_OVERLOAD = ITEMS.register("raw_overload");
    public static final ItemRegistryObject<Item> RAW_QUATRZ = ITEMS.register("raw_quartz");
    public static final ItemRegistryObject<Item> RAW_REDSTONE = ITEMS.register("raw_redstone");
    public static final ItemRegistryObject<Item> RAW_SILICON = ITEMS.register("raw_silicon");

    public static final ItemRegistryObject<Item> AMETHYST_CRYSTAL = ITEMS.register("amethyst_crystal");
    public static final ItemRegistryObject<Item> CERTUS_QUARTZ_CRYSTAL = ITEMS.register("certus_quartz_crystal");
    public static final ItemRegistryObject<Item> COAL_CRYSTAL = ITEMS.register("coal_crystal");
    public static final ItemRegistryObject<Item> DIAMOND_CRYSTAL = ITEMS.register("diamond_crystal");
    public static final ItemRegistryObject<Item> EMERALD_CRYSTAL = ITEMS.register("emerald_crystal");
    public static final ItemRegistryObject<Item> ENTRO_CRYSTAL = ITEMS.register("entro_crystal");
    public static final ItemRegistryObject<Item> FLUORITE_CRYSTAL = ITEMS.register("fluorite_crystal");
    public static final ItemRegistryObject<Item> LAPIS_LAZULI_CRYSTAL = ITEMS.register("lapis_lazuli_crystal");
    public static final ItemRegistryObject<Item> NETHERITE_CRYSTAL = ITEMS.register("netherite_crystal");
    public static final ItemRegistryObject<Item> OVERLOAD_CRYSTAL = ITEMS.register("overload_crystal");
    public static final ItemRegistryObject<Item> QUATRZ_CRYSTAL = ITEMS.register("quartz_crystal");
    public static final ItemRegistryObject<Item> REDSTONE_CRYSTAL = ITEMS.register("redstone_crystal");
    public static final ItemRegistryObject<Item> SILICON_CRYSTAL = ITEMS.register("silicon_crystal");

    public static final ItemRegistryObject<Item> AMETHYST_SHARD = ITEMS.register("amethyst_shard");
    public static final ItemRegistryObject<Item> CERTUS_QUARTZ_SHARD = ITEMS.register("certus_quartz_shard");
    public static final ItemRegistryObject<Item> COAL_SHARD = ITEMS.register("coal_shard");
    public static final ItemRegistryObject<Item> DIAMOND_SHARD = ITEMS.register("diamond_shard");
    public static final ItemRegistryObject<Item> EMERALD_SHARD = ITEMS.register("emerald_shard");
    public static final ItemRegistryObject<Item> ENTRO_SHARD = ITEMS.register("entro_shard");
    public static final ItemRegistryObject<Item> FLUORITE_SHARD = ITEMS.register("fluorite_shard");
    public static final ItemRegistryObject<Item> LAPIS_LAZULI_SHARD = ITEMS.register("lapis_lazuli_shard");
    public static final ItemRegistryObject<Item> NETHERITE_SHARD = ITEMS.register("netherite_shard");
    public static final ItemRegistryObject<Item> OVERLOAD_SHARD = ITEMS.register("overload_shard");
    public static final ItemRegistryObject<Item> QUATRZ_SHARD = ITEMS.register("quartz_shard");
    public static final ItemRegistryObject<Item> REDSTONE_SHARD = ITEMS.register("redstone_shard");
    public static final ItemRegistryObject<Item> SILICON_SHARD = ITEMS.register("silicon_shard");

    public static final ItemRegistryObject<Item> AMETHYST_CLUMP = ITEMS.register("amethyst_clump");
    public static final ItemRegistryObject<Item> CERTUS_QUARTZ_CLUMP = ITEMS.register("certus_quartz_clump");
    public static final ItemRegistryObject<Item> COAL_CLUMP = ITEMS.register("coal_clump");
    public static final ItemRegistryObject<Item> DIAMOND_CLUMP = ITEMS.register("diamond_clump");
    public static final ItemRegistryObject<Item> EMERALD_CLUMP = ITEMS.register("emerald_clump");
    public static final ItemRegistryObject<Item> ENTRO_CLUMP = ITEMS.register("entro_clump");
    public static final ItemRegistryObject<Item> FLUORITE_CLUMP = ITEMS.register("fluorite_clump");
    public static final ItemRegistryObject<Item> LAPIS_LAZULI_CLUMP = ITEMS.register("lapis_lazuli_clump");
    public static final ItemRegistryObject<Item> NETHERITE_CLUMP = ITEMS.register("netherite_clump");
    public static final ItemRegistryObject<Item> OVERLOAD_CLUMP = ITEMS.register("overload_clump");
    public static final ItemRegistryObject<Item> QUATRZ_CLUMP = ITEMS.register("quartz_clump");
    public static final ItemRegistryObject<Item> REDSTONE_CLUMP = ITEMS.register("redstone_clump");
    public static final ItemRegistryObject<Item> SILICON_CLUMP = ITEMS.register("silicon_clump");

    private static ItemRegistryObject<Item> registerFoiling(String name) {
        return ITEMS.registerItem(name, props -> new Item(props) {
            public boolean isFoil(ItemStack stack) {
                return true;
            };
        });
    }
}
