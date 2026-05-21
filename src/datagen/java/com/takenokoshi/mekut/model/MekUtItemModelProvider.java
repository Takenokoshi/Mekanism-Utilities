package com.takenokoshi.mekut.model;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.enums.MUMaterial;
import com.takenokoshi.mekut.registries.MekUtItems;

import appeng.core.AppEng;
import mekanism.common.Mekanism;
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
        simpleItem(MekUtItems.RAW_MU_MATERIALS.get(MUMaterial.DIAMOND).get())
                .texture("layer0", MekUtConstants.rl("item/raw/diamond"));
        simpleItem(MekUtItems.RAW_MU_MATERIALS.get(MUMaterial.REDSTONE).get())
                .texture("layer0", MekUtConstants.rl("item/raw/redstone"));
        simpleItem(MekUtItems.RAW_MU_MATERIALS.get(MUMaterial.NETHERITE).get())
                .texture("layer0", MekUtConstants.rl("item/raw/netherite"));

        simpleItem(MekUtItems.RAW_MU_MATERIALS.get(MUMaterial.COAL).get())
                .texture("layer0", mcLoc("item/coal"))
                .texture("layer1", MekUtConstants.rl("item/raw/stone_layer"));
        simpleItem(MekUtItems.RAW_MU_MATERIALS.get(MUMaterial.AMETHYST).get())
                .texture("layer0", mcLoc("item/amethyst_shard"))
                .texture("layer1", MekUtConstants.rl("item/raw/stone_layer"));
        simpleItem(MekUtItems.RAW_MU_MATERIALS.get(MUMaterial.EMERALD).get())
                .texture("layer0", mcLoc("item/emerald"))
                .texture("layer1", MekUtConstants.rl("item/raw/stone_layer"));
        simpleItem(MekUtItems.RAW_MU_MATERIALS.get(MUMaterial.LAPIS_LAZULI).get())
                .texture("layer0", mcLoc("item/lapis_lazuli"))
                .texture("layer1", MekUtConstants.rl("item/raw/stone_layer"));
        simpleItem(MekUtItems.RAW_MU_MATERIALS.get(MUMaterial.QUARTZ).get())
                .texture("layer0", mcLoc("item/quartz"))
                .texture("layer1", MekUtConstants.rl("item/raw/netherrack_layer"));

        getBuilder(MekUtItems.RAW_MU_MATERIALS.get(MUMaterial.FLUORITE).getId().getPath())
                .parent(new ModelFile.UncheckedModelFile(Mekanism.rl("item/fluorite_gem")))
                .texture("layer1", MekUtConstants.rl("item/raw/stone_layer"));
        getBuilder(MekUtItems.RAW_MU_MATERIALS.get(MUMaterial.CERTUS_QUARTZ).getId().getPath())
                .parent(new ModelFile.UncheckedModelFile(AppEng.makeId("item/certus_quartz_crystal")))
                .texture("layer1", MekUtConstants.rl("item/raw/stone_layer"));

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
        simpleItem(MekUtItems.REFINED_AMETHYST_INGOT.get())
                .texture("layer0", MekUtConstants.rl("item/ingot/refined_amethyst"));
        simpleItem(MekUtItems.GOLDEN_REDSTONE.get()).texture("layer0", MekUtConstants.rl("item/dust/golden_redstone"));

        simpleItem(MekUtItems.ELASTIC_ALLOY.get()).texture("layer0", MekUtConstants.rl("item/alloy/elastic"));
        simpleItem(MekUtItems.CONVERGENT_ALLOY.get()).texture("layer0", MekUtConstants.rl("item/alloy/convergent"));
        simpleItem(MekUtItems.XP_ALLOY.get()).texture("layer0", MekUtConstants.rl("item/alloy/xp"));

        simpleItem(MekUtItems.DIGITAL_CONTROL_CIRCUIT.get())
                .texture("layer0", MekUtConstants.rl("item/control_circuit/digital"));
        simpleItem(MekUtItems.STANDARD_CONTROL_CIRCUIT.get())
                .texture("layer0", MekUtConstants.rl("item/control_circuit/standard"));
        simpleItem(MekUtItems.ACCELERATION_CONTROL_CIRCUIT.get())
                .texture("layer0", MekUtConstants.rl("item/control_circuit/standard"))
                .texture("layer1", MekUtConstants.rl("item/control_circuit/acceleration"));
        simpleItem(MekUtItems.CHEMICAL_CONTROL_CIRCUIT.get())
                .texture("layer0", MekUtConstants.rl("item/control_circuit/standard"))
                .texture("layer1", MekUtConstants.rl("item/control_circuit/chemical"));
        simpleItem(MekUtItems.KNOWLEDGE_CONTROL_CIRCUIT.get())
                .texture("layer0", MekUtConstants.rl("item/control_circuit/knowledge"));

        simpleItem(MekUtItems.ACTIVATED_LAPIS_LAZULI.get())
                .texture("layer0", mcLoc("item/lapis_lazuli"))
                .texture("layer1", MekUtConstants.rl("item/gem/activated_lapis_lazuli"));

        getBuilder(MekUtItems.ENRICHED_LAPIS_LAZULI.getId().getPath())
                .parent(new ModelFile.UncheckedModelFile(Mekanism.rl("item/enriched_redstone")))
                .texture("layer1", MekUtConstants.rl("item/enriched/lapis_lazuli"));
        simpleItem(MekUtItems.ENRICHED_SINGULARITY.get())
                .texture("layer0", MekUtConstants.rl("item/enriched/singularity"));

        simpleItem(MekUtItems.DARK_RED_DYE.get())
                .texture("layer0", MekUtConstants.rl("item/dye/dark_red"));
        simpleItem(MekUtItems.AQUA_DYE.get())
                .texture("layer0", MekUtConstants.rl("item/dye/aqua"));

        simpleItem(MekUtItems.ME_INFINITY_RAINBOW_CELL.get())
                .texture("layer0", MekUtConstants.rl("item/cell/infinity_rainbow"));
    }

    public ItemModelBuilder simpleItem(Item item) {
        return getBuilder(BuiltInRegistries.ITEM.getKey(item).toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"));
    }

}
