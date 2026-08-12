package com.takenokoshi.mekut.model;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.enums.MekUtMaterial;
import com.takenokoshi.mekut.registries.MekUtItems;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class MekUtItemModelProvider extends ItemModelProvider {

    public MekUtItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MekUtConstants.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        simpleItem(MekUtItems.ARTIFICIAL_STAR.get())
                .texture("layer0", MekUtConstants.rl("item/artificial_star"));
        simpleItem(MekUtItems.SILICON.get())
                .texture("layer0", MekUtConstants.rl("item/silicon"));

        simpleItem(MekUtItems.XP_CRYSTAL.get()).texture("layer0", MekUtConstants.rl("item/crystal/xp"));

        simpleItem(MekUtItems.AMETHYST_DUST.get()).texture("layer0", MekUtConstants.rl("item/dust/amethyst"));
        simpleItem(MekUtItems.GOLDEN_REDSTONE.get()).texture("layer0", MekUtConstants.rl("item/dust/golden_redstone"));
        simpleItem(MekUtItems.IRIDIUM_DUST.get()).texture("layer0", MekUtConstants.rl("item/dust/iridium"));
        simpleItem(MekUtItems.SILICON_DUST.get()).texture("layer0", MekUtConstants.rl("item/dust/silicon"));
        simpleItem(MekUtItems.REFINED_LAPIS_LAZULI_DUST.get()).texture("layer0",
                MekUtConstants.rl("item/dust/refined_lapis_lazuli"));

        simpleItem(MekUtItems.IRIDIUM_INGOT.get())
                .texture("layer0", MekUtConstants.rl("item/ingot/iridium"));

        simpleItem(MekUtItems.ELASTIC_ALLOY.get()).texture("layer0", MekUtConstants.rl("item/alloy/elastic"));
        simpleItem(MekUtItems.CONVERGENT_ALLOY.get()).texture("layer0", MekUtConstants.rl("item/alloy/convergent"));
        simpleItem(MekUtItems.COMPISITE_ALLOY.get()).texture("layer0", MekUtConstants.rl("item/alloy/composite"));
        simpleItem(MekUtItems.STARDUST_ALLOY.get()).texture("layer0", MekUtConstants.rl("item/alloy/stardust"));

        simpleItem(MekUtItems.DIGITAL_CONTROL_CIRCUIT.get())
                .texture("layer0", MekUtConstants.rl("item/control_circuit/digital"));
        simpleItem(MekUtItems.STANDARD_CONTROL_CIRCUIT.get())
                .texture("layer0", MekUtConstants.rl("item/control_circuit/standard"));
        simpleItem(MekUtItems.AUGMENT_CONTROL_CIRCUIT.get())
                .texture("layer0", MekUtConstants.rl("item/control_circuit/augment"));
        simpleItem(MekUtItems.COMET_CONTROL_CIRCUIT.get())
                .texture("layer0", MekUtConstants.rl("item/control_circuit/comet"));

        simpleItem(MekUtItems.ENRICHED_LAPIS_LAZULI.get())
                .texture("layer0", MekUtConstants.rl("item/enriched/lapis_lazuli"));
        simpleItem(MekUtItems.ENRICHED_AMETHYST.get())
                .texture("layer0", MekUtConstants.rl("item/enriched/amethyst"));
        simpleItem(MekUtItems.ENRICHED_GLOWSTONE.get())
                .texture("layer0", MekUtConstants.rl("item/enriched/glowstone"));

        simpleItem(MekUtItems.DARK_RED_DYE.get())
                .texture("layer0", MekUtConstants.rl("item/dye/dark_red"));
        simpleItem(MekUtItems.AQUA_DYE.get())
                .texture("layer0", MekUtConstants.rl("item/dye/aqua"));

        simpleItem(MekUtItems.ME_INFINITY_RAINBOW_CELL.get())
                .texture("layer0", MekUtConstants.rl("item/cell/infinity_rainbow"));

        MekUtMaterial.MATERIALS.forEach(material -> {
            simpleItem(material.raw().get())
                    .texture("layer0", MekUtConstants.rl("item/raw/" + material.name()));
            simpleItem(material.crystal().get())
                    .texture("layer0", MekUtConstants.rl("item/crystal/" + material.name()));
            simpleItem(material.shard().get())
                    .texture("layer0", MekUtConstants.rl("item/shard/" + material.name()));
            simpleItem(material.clump().get())
                    .texture("layer0", MekUtConstants.rl("item/clump/" + material.name()));
        });

        simpleItem(MekUtItems.SUPPLIER_BASE.get())
                .texture("layer0", MekUtConstants.rl("item/supplier/base"));
        simpleItem(MekUtItems.COBBLESTONE_SUPPLIER.get())
                .texture("layer0", MekUtConstants.rl("item/supplier/cobblestone"));
    }

    public ItemModelBuilder simpleItem(Item item) {
        return getBuilder(BuiltInRegistries.ITEM.getKey(item).toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"));
    }

}
