package com.takenokoshi.mekut.model;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.registration.MachineRegistryObject;
import com.takenokoshi.mekut.registries.MekUtBlocks;
import com.takenokoshi.mekut.registries.MekUtFluids;
import com.takenokoshi.mekut.registries.MekUtMachines;

import mekanism.common.Mekanism;
import mekanism.common.block.attribute.AttributeStateActive;
import mekanism.common.block.attribute.Attributes;
import mekanism.common.registration.impl.BlockRegistryObject;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
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

        MekUtFluids.FLUIDS.getBlockEntries().forEach(holder -> this.simpleFluid(holder.get()));

        simpleBlockWithItem(MekUtBlocks.AMETHYST_ORE.get(),
                models().cubeAll("block/ore/amethyst", modLoc("block/ore/amethyst")));
        simpleBlockWithItem(MekUtBlocks.CERTUS_QUARTZ_ORE.get(),
                models().cubeAll("block/ore/certus_quartz", modLoc("block/ore/certus_quartz")));
        simpleBlockWithItem(MekUtBlocks.ENTRO_ORE.get(),
                models().cubeAll("block/ore/entro", modLoc("block/ore/entro")));
        simpleBlockWithItem(MekUtBlocks.NETHERITE_ORE.get(),
                models().cubeAll("block/ore/netherite", modLoc("block/ore/netherite")));

        var wipModel = models().cubeAll("wip_block", modLoc("block/wip"));
        MekUtBlocks.RAW_MU_MATERIALS_BLOCK.forEach((material, def) -> {
            simpleBlockWithItem(def.get(), wipModel);
        });

        mekutNormalMachine(MekUtMachines.MEKSTYLED_CHARGER.getBlockObject(),
                MekUtConstants.rl("block/normal_machine/mekstyled_charger_front"),
                MekUtConstants.rl("block/normal_machine/mekstyled_charger_front_active"));

        mekutNormalMachine(MekUtMachines.CHEMICAL_CUTTER,
                "chemical_cutter");
        mekutNormalMachine(MekUtMachines.COMPACT_SUPERCRITICAL_PHASE_SHIFTER,
                "sps");
        mekutNormalMachine(MekUtMachines.ICE_MAKER,
                "ice_maker");
        mekutNormalMachine(MekUtMachines.LAZER_COMPRESS_NUCLEO_SYNTHESIZER,
                "lazer_compress_nucleo_synthesizer");
        mekutNormalMachine(MekUtMachines.SMALL_DIGITAL_ASSEMBLER,
                "small_digital_assembler");
        mekutNormalMachine(MekUtMachines.SMALL_DIGITAL_REACTION_CHAMBER,
                "small_digital_reaction_chamber");
        mekutNormalMachine(MekUtMachines.STELLAR_GENESIS_CHAMBER,
                "stellar_genesis_chamber");
        mekutNormalMachine(MekUtMachines.SUBMATERIAL_CONVERTER,
                "submaterial_converter");

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

    protected void mekutNormalMachine(
            MachineRegistryObject<?, ?, ?, ?> machine,
            String basePath) {
        mekutNormalMachine(machine.getBlockObject(),
                MekUtConstants.rl("block/normal_machine/" + basePath + "_front"),
                MekUtConstants.rl("block/normal_machine/" + basePath + "_front_active"));
    }

    protected void mekutNormalMachine(
            BlockRegistryObject<?, ?> block,
            ResourceLocation inactiveModel,
            ResourceLocation activeModel) {

        ModelFile inactive = models().cube("block/machine/" + block.getId().getPath(),
                MekUtConstants.rl("block/machine_base/down"),
                MekUtConstants.rl("block/machine_base/up"),
                inactiveModel,
                MekUtConstants.rl("block/machine_base/back"),
                MekUtConstants.rl("block/machine_base/side"),
                MekUtConstants.rl("block/machine_base/side"));

        ModelFile active = models().cube("block/machine/" + block.getId().getPath() + "_active",
                MekUtConstants.rl("block/machine_base/down"),
                MekUtConstants.rl("block/machine_base/up"),
                activeModel,
                MekUtConstants.rl("block/machine_base/back"),
                MekUtConstants.rl("block/machine_base/side"),
                MekUtConstants.rl("block/machine_base/side"));

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

    protected void mekutStandardMachine(
            BlockRegistryObject<?, ?> block,
            ResourceLocation inactiveModel,
            ResourceLocation activeModel) {

        ModelFile inactive = models().cube("block/machine/" + block.getId().getPath(),
                MekUtConstants.rl("block/machine_base/down"),
                MekUtConstants.rl("block/machine_base/up"),
                inactiveModel,
                MekUtConstants.rl("block/machine_base/standard_back"),
                MekUtConstants.rl("block/machine_base/standard_side"),
                MekUtConstants.rl("block/machine_base/standard_side"));

        ModelFile active = models().cube("block/machine/" + block.getId().getPath() + "_active",
                MekUtConstants.rl("block/machine_base/down"),
                MekUtConstants.rl("block/machine_base/up"),
                activeModel,
                MekUtConstants.rl("block/machine_base/standard_back"),
                MekUtConstants.rl("block/machine_base/standard_side"),
                MekUtConstants.rl("block/machine_base/standard_side"));

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

    protected void simpleFluid(Block liquidBlock) {
        getVariantBuilder(liquidBlock)
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(new ModelFile.UncheckedModelFile(Mekanism.rl("block/brine"))).build());
    }

}
