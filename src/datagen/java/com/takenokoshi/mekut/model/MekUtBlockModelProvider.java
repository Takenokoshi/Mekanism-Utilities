package com.takenokoshi.mekut.model;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.enums.MUMaterial;
import com.takenokoshi.mekut.registries.MekUtBlocks;
import com.takenokoshi.mekut.registries.MekUtMachines;

import mekanism.common.Mekanism;
import mekanism.common.block.attribute.AttributeStateActive;
import mekanism.common.block.attribute.Attributes;
import mekanism.common.registration.impl.BlockRegistryObject;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class MekUtBlockModelProvider extends BlockStateProvider {

    public MekUtBlockModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MekUtConstants.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlockWithItem(MekUtBlocks.AMETHYST_ORE.get(),
                models().cubeAll("block/ore/amethyst", modLoc("block/ore/amethyst")));
        simpleBlockWithItem(MekUtBlocks.CERTUS_QUARTZ_ORE.get(),
                models().cubeAll("block/ore/certus_quartz", modLoc("block/ore/certus_quartz")));
        simpleBlockWithItem(MekUtBlocks.NETHERITE_ORE.get(),
                models().cubeAll("block/ore/netherite", modLoc("block/ore/netherite")));

        simpleBlockWithItem(MekUtBlocks.RAW_MU_MATERIALS_BLOCK.get(MUMaterial.AMETHYST).get(),
                new ModelFile.UncheckedModelFile(modLoc("block/ore/amethyst")));
        simpleBlockWithItem(MekUtBlocks.RAW_MU_MATERIALS_BLOCK.get(MUMaterial.CERTUS_QUARTZ).get(),
                new ModelFile.UncheckedModelFile(modLoc("block/ore/certus_quartz")));
        simpleBlockWithItem(MekUtBlocks.RAW_MU_MATERIALS_BLOCK.get(MUMaterial.COAL).get(),
                new ModelFile.UncheckedModelFile(mcLoc("block/coal_ore")));
        simpleBlockWithItem(MekUtBlocks.RAW_MU_MATERIALS_BLOCK.get(MUMaterial.DIAMOND).get(),
                new ModelFile.UncheckedModelFile(mcLoc("block/diamond_ore")));
        simpleBlockWithItem(MekUtBlocks.RAW_MU_MATERIALS_BLOCK.get(MUMaterial.EMERALD).get(),
                new ModelFile.UncheckedModelFile(mcLoc("block/emerald_ore")));
        simpleBlockWithItem(MekUtBlocks.RAW_MU_MATERIALS_BLOCK.get(MUMaterial.FLUORITE).get(),
                new ModelFile.UncheckedModelFile(Mekanism.rl("block/ore/fluorite")));
        simpleBlockWithItem(MekUtBlocks.RAW_MU_MATERIALS_BLOCK.get(MUMaterial.LAPIS_LAZULI).get(),
                new ModelFile.UncheckedModelFile(mcLoc("block/lapis_ore")));
        simpleBlockWithItem(MekUtBlocks.RAW_MU_MATERIALS_BLOCK.get(MUMaterial.NETHERITE).get(),
                new ModelFile.UncheckedModelFile(modLoc("block/ore/netherite")));
        simpleBlockWithItem(MekUtBlocks.RAW_MU_MATERIALS_BLOCK.get(MUMaterial.QUARTZ).get(),
                new ModelFile.UncheckedModelFile(mcLoc("block/nether_quartz_ore")));
        simpleBlockWithItem(MekUtBlocks.RAW_MU_MATERIALS_BLOCK.get(MUMaterial.REDSTONE).get(),
                new ModelFile.UncheckedModelFile(mcLoc("block/redstone_ore")));

        mekanismMachine(MekUtMachines.TWEAKED_ENERGIZED_SMELTER.getBlockObject(),
                Mekanism.rl("block/energized_smelter"),
                Mekanism.rl("block/energized_smelter_active"));
    }

    protected void mekanismMachine(
            BlockRegistryObject<?, ?> block,
            ResourceLocation inactiveModel,
            ResourceLocation activeModel) {

        ModelFile inactive = new ModelFile.UncheckedModelFile(inactiveModel);

        ModelFile active = new ModelFile.UncheckedModelFile(activeModel);

        getVariantBuilder(block.get())
                .forAllStates(state -> {

                    Direction facing = state.getValue(
                            BlockStateProperties.HORIZONTAL_FACING);

                    boolean lit = ((AttributeStateActive) (Attributes.ACTIVE_LIGHT)).isActive(state);

                    return ConfiguredModel.builder()
                            .modelFile(lit ? active : inactive)
                            .rotationY(((int) facing.toYRot() + 180) % 360)
                            .build();
                });

        simpleBlockItem(
                block.get(),
                inactive);
    }

}
