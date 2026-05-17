package com.takenokoshi.mekut.model;

import com.takenokoshi.mekut.core.MekUtConstants;
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
                models().cubeAll("ore/amethyst", modLoc("block/ore/amethyst")));
        simpleBlockWithItem(MekUtBlocks.CERTUS_QUARTZ_ORE.get(),
                models().cubeAll("ore/certus_quartz", modLoc("block/ore/certus_quartz")));
        simpleBlockWithItem(MekUtBlocks.NETHERITE_ORE.get(),
                models().cubeAll("ore/netherite", modLoc("block/ore/netherite")));

        mekanismMachine(MekUtMachines.TWEAKED_ENERGIZED_SMELTER.getBlockObject(), Mekanism.rl("block/energized_smelter"),
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
