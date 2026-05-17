package com.takenokoshi.mekut.model;

import com.takenokoshi.mekut.core.MekUtConstants;
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
        MekUtItems.RAW_MU_MATERIALS.forEach((material, registry) -> {
            simpleItem(registry.get()).texture("layer0", MekUtConstants.rl("item/raw/" + material.name));

        });
        MekUtItems.MU_MATERIALS_CLUMP.forEach((material, registry) -> {
            simpleItem(registry.get()).texture("layer0", MekUtConstants.rl("item/clump/" + material.name));
        });
        MekUtItems.MU_MATERIALS_CRYSTAL.forEach((material, registry) -> {
            simpleItem(registry.get()).texture("layer0", MekUtConstants.rl("item/crystal/" + material.name));
        });
        MekUtItems.MU_MATERIALS_DIRTY_DUST.forEach((material, registry) -> {
            simpleItem(registry.get()).texture("layer0", MekUtConstants.rl("item/dirty_dust/" + material.name));
        });
        MekUtItems.MU_MATERIALS_SHARD.forEach((material, registry) -> {
            simpleItem(registry.get()).texture("layer0", MekUtConstants.rl("item/shard/" + material.name));
        });

        simpleItem(MekUtItems.XP_CRYSTAL.get()).texture("layer0", MekUtConstants.rl("item/crystal/xp"));

        simpleItem(MekUtItems.AMETHYST_DUST.get()).texture("layer0", MekUtConstants.rl("item/dust/amethyst_dust"));
        simpleItem(MekUtItems.GOLDEN_REDSTONE.get()).texture("layer0", MekUtConstants.rl("item/dust/golden_redstone"));

        simpleItem(MekUtItems.ELASTIC_ALLOY.get()).texture("layer0", MekUtConstants.rl("item/alloy/elastic"));
        simpleItem(MekUtItems.CONVERGENT_ALLOY.get()).texture("layer0", MekUtConstants.rl("item/alloy/convergent"));
        simpleItem(MekUtItems.XP_ALLOY.get()).texture("layer0", MekUtConstants.rl("item/alloy/xp"));

        simpleItem(MekUtItems.DIGITAL_CONTROL_CIRCUIT.get()).texture("layer0",
                MekUtConstants.rl("item/control_circuit/digital"));
        simpleItem(MekUtItems.STANDARD_CONTROL_CIRCUIT.get()).texture("layer0",
                MekUtConstants.rl("item/control_circuit/standard"));
        simpleItem(MekUtItems.KNOWLEDGE_CONTROL_CIRCUIT.get()).texture("layer0",
                MekUtConstants.rl("item/control_circuit/knowledge"));
    }

    public ItemModelBuilder simpleItem(Item item) {
        return getBuilder(BuiltInRegistries.ITEM.getKey(item).toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"));
    }

}
