package com.takenokoshi.mekut.model;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.registries.MekUtItems;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class MekUtItemModelProvider extends ItemModelProvider {

    public MekUtItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MekUtConstants.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        MekUtItems.RAW_MU_MATERIALS.forEach((material, registry) -> {
            basicItem(registry.get()).texture("layer_0", MekUtConstants.rl("raw/" + material.name));
        });
        MekUtItems.MU_MATERIALS_CLUMP.forEach((material, registry) -> {
            basicItem(registry.get()).texture("layer_0", MekUtConstants.rl("clump/" + material.name));
        });
        MekUtItems.MU_MATERIALS_CRYSTAL.forEach((material, registry) -> {
            basicItem(registry.get()).texture("layer_0", MekUtConstants.rl("crystal/" + material.name));
        });
        MekUtItems.MU_MATERIALS_DIRTY_DUST.forEach((material, registry) -> {
            basicItem(registry.get()).texture("layer_0", MekUtConstants.rl("dirty_dust/" + material.name));
        });
        MekUtItems.MU_MATERIALS_SHARD.forEach((material, registry) -> {
            basicItem(registry.get()).texture("layer_0", MekUtConstants.rl("shard/" + material.name));
        });

        basicItem(MekUtItems.XP_CRYSTAL.get()).texture("layer_0", MekUtConstants.rl("crystal/xp"));

        basicItem(MekUtItems.AMETHYST_DUST.get()).texture("layer_0", MekUtConstants.rl("dust/amethyst_dust"));
        basicItem(MekUtItems.GOLDEN_REDSTONE.get()).texture("layer_0", MekUtConstants.rl("dust/golden_redstone"));

        basicItem(MekUtItems.ELASTIC_ALLOY.get()).texture("layer_0", MekUtConstants.rl("alloy/elastic"));
        basicItem(MekUtItems.CONVERGENT_ALLOY.get()).texture("layer_0", MekUtConstants.rl("alloy/convergent"));
        basicItem(MekUtItems.XP_ALLOY.get()).texture("layer_0", MekUtConstants.rl("alloy/xp"));

        basicItem(MekUtItems.DIGITAL_CONTROL_CIRCUIT.get()).texture("layer_0",
                MekUtConstants.rl("control_circuit/digital"));
        basicItem(MekUtItems.STANDARD_CONTROL_CIRCUIT.get()).texture("layer_0",
                MekUtConstants.rl("control_circuit/standard"));
        basicItem(MekUtItems.KNOWLEDGE_CONTROL_CIRCUIT.get()).texture("layer_0",
                MekUtConstants.rl("control_circuit/knowledge"));
    }

}
