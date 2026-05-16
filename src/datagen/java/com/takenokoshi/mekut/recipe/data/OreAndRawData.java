package com.takenokoshi.mekut.recipe.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.takenokoshi.mekut.enums.MUMaterial;
import com.takenokoshi.mekut.registries.MekUtItems;

import mekanism.common.registries.MekanismItems;
import mekanism.common.resource.PrimaryResource;
import mekanism.common.resource.ResourceType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public record OreAndRawData(String name, ItemStack raw, int oreAmount) {

    private static OreAndRawData material(MUMaterial material, int rawAmount, int oreAmount) {
        return new OreAndRawData(material.name, MekUtItems.RAW_MU_MATERIALS.get(material).asStack(rawAmount),
                oreAmount);
    }

    public static final List<OreAndRawData> LIST;
    static {
        List<OreAndRawData> list = new ArrayList<>();
        list.add(material(MUMaterial.AMETHYST, 12, 1));
        list.add(material(MUMaterial.CERTUS_QUARTZ, 12, 1));
        list.add(material(MUMaterial.COAL, 3, 1));
        list.add(material(MUMaterial.DIAMOND, 3, 1));
        list.add(material(MUMaterial.EMERALD, 3, 1));
        list.add(material(MUMaterial.FLUORITE, 9, 2));
        list.add(material(MUMaterial.LAPIS_LAZULI, 18, 1));
        list.add(material(MUMaterial.NETHERITE, 3, 1));
        list.add(material(MUMaterial.QUARTZ, 3, 1));
        list.add(material(MUMaterial.REDSTONE, 18, 1));
        list.add(new OreAndRawData("iron", new ItemStack(Items.RAW_IRON, 3), 1));
        list.add(new OreAndRawData("gold", new ItemStack(Items.RAW_GOLD, 3), 1));
        list.add(new OreAndRawData("copper", new ItemStack(Items.RAW_COPPER, 21), 2));
        list.add(new OreAndRawData("osmium",
                MekanismItems.PROCESSED_RESOURCES.get(ResourceType.RAW, PrimaryResource.OSMIUM).asStack(3), 1));
        list.add(new OreAndRawData("tin",
                MekanismItems.PROCESSED_RESOURCES.get(ResourceType.RAW, PrimaryResource.TIN).asStack(3), 1));
        list.add(new OreAndRawData("uranium",
                MekanismItems.PROCESSED_RESOURCES.get(ResourceType.RAW, PrimaryResource.URANIUM).asStack(3), 1));
        list.add(new OreAndRawData("lead",
                MekanismItems.PROCESSED_RESOURCES.get(ResourceType.RAW, PrimaryResource.LEAD).asStack(3), 1));
        LIST = Collections.unmodifiableList(list);
    }
}
