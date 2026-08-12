package com.takenokoshi.mekut.model;

import com.takenokoshi.mekaddonlib.registration.MachineRegistryObject;
import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.enums.MekUtMaterial;
import com.takenokoshi.mekut.registries.MekUtBlocks;
import com.takenokoshi.mekut.registries.MekUtEvolvedMachines;
import com.takenokoshi.mekut.registries.MekUtExtrasMachines;
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
        MekUtMaterial.MATERIALS.forEach(material -> {
            simpleBlockWithItem(material.rawBlock().get(), wipModel);
        });

        mekUtSimpleMachine(MekUtMachines.CHEMICAL_CUTTER,
                true,
                "standard",
                "chemical_cutter");
        mekUtSimpleMachine(MekUtMachines.COMPACT_BOILER,
                false,
                "digital",
                "boiler");
        mekUtSimpleMachine(MekUtMachines.COMPACT_FISSION_REACTOR,
                false,
                "standard",
                "fission_reactor");
        mekUtSimpleMachine(MekUtMachines.COMPACT_FUSION_REACTOR,
                true,
                "augment",
                "fusion_reactor");
        mekUtSimpleMachine(MekUtMachines.COMPACT_INDUSTRIAL_TURBINE,
                true,
                "digital",
                "industrial_turbine");
        mekUtSimpleMachine(MekUtMachines.COMPACT_SUPERCRITICAL_PHASE_SHIFTER,
                true,
                "augment",
                "sps");
        mekUtSimpleMachine(MekUtMachines.COMPACT_THERMAL_EVAPOLATION_PLANT,
                false,
                "digital",
                "tep");
        mekUtSimpleMachine(MekUtMachines.ICE_MAKER,
                true,
                "digital",
                "ice_maker");
        mekUtSimpleMachine(MekUtMachines.LAZER_COMPRESS_NUCLEO_SYNTHESIZER,
                true,
                "augment",
                "lazer_compress_nucleo_synthesizer");
        mekUtSimpleMachine(MekUtMachines.SMALL_DIGITAL_ASSEMBLER,
                true,
                "digital",
                "small_digital_assembler");
        mekUtSimpleMachine(MekUtMachines.SMALL_DIGITAL_REACTION_CHAMBER,
                true,
                "standard",
                "small_digital_reaction_chamber");
        mekUtSimpleMachine(MekUtMachines.STELLAR_GENESIS_CHAMBER,
                true,
                "comet",
                "stellar_genesis_chamber");
        mekUtSimpleMachine(MekUtMachines.SUBMATERIAL_CONVERTER,
                false,
                "digital",
                "submaterial_converter");
        mekUtSimpleMachine(MekUtMachines.XP_TANK,
                false,
                "digital",
                "xp_tank");
        mekUtSimpleMachine(MekUtEvolvedMachines.COMPACT_ANTIMATTER_PROTOMOLECULAR_TRANSMUTATOR,
                true,
                "quantum",
                "apt");
        mekUtSimpleMachine(MekUtExtrasMachines.COMPACT_NAQUADAH_REACTOR,
                true,
                "cosmic",
                "naquadah_reactor");

        mekUtSimpleMachine(MekUtMachines.ITEM_RATIO_SPLITTER,
                false,
                "digital",
                "item_ratio_splitter");
        mekUtSimpleMachine(MekUtMachines.FLUID_RATIO_SPLITTER,
                false,
                "digital",
                "fluid_ratio_splitter");
        mekUtSimpleMachine(MekUtMachines.CHEMICAL_RATIO_SPLITTER,
                false,
                "digital",
                "chemical_ratio_splitter");

        mekanismMachine(MekUtMachines.TWEAKED_ENERGIZED_SMELTER.getBlock(),
                Mekanism.rl("block/energized_smelter"),
                Mekanism.rl("block/energized_smelter_active"));

        greenHouse(MekUtMachines.GREEN_HOUSE, "augment", "green_house");
    }

    protected void mekUtSimpleMachine(
            MachineRegistryObject<?, ?, ?, ?> registryObject,
            boolean energy,
            String tierDecoration,
            String baseName) {
        mekUtSimpleMachine(registryObject.getBlock(), energy, tierDecoration, baseName);
    }

    protected void mekUtSimpleMachine(
            BlockRegistryObject<?, ?> registryObject,
            boolean energy,
            String tierDecoration,
            String baseName) {

        ModelFile inactive = models().withExistingParent(baseName, MekUtConstants.rl(energy
                ? "block/base/machine_base_energy"
                : "block/base/machine_base"))
                .texture("front", MekUtConstants.rl("block/machine_front/" + baseName))
                .texture("decoration", MekUtConstants.rl("block/tier_decoration/" + tierDecoration));

        ModelFile active = models().withExistingParent(baseName + "_active", MekUtConstants.rl(energy
                ? "block/base/machine_base_energy"
                : "block/base/machine_base"))
                .texture("front", MekUtConstants.rl("block/machine_front_active/" + baseName))
                .texture("decoration", MekUtConstants.rl("block/tier_decoration/" + tierDecoration));

        getVariantBuilder(registryObject.get())
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
                registryObject.get(),
                inactive);

    }

    private void greenHouse(
            MachineRegistryObject<?, ?, ?, ?> machine,
            String tierDecoration,
            String baseName) {

        ModelFile inactive = models().withExistingParent(baseName, MekUtConstants.rl("block/base/green_house"))
                .texture("front", MekUtConstants.rl("block/machine_front/" + baseName))
                .texture("decoration", MekUtConstants.rl("block/tier_decoration/" + tierDecoration));

        ModelFile active = models()
                .withExistingParent(baseName + "_active", MekUtConstants.rl("block/base/green_house"))
                .texture("front", MekUtConstants.rl("block/machine_front_active/" + baseName))
                .texture("decoration", MekUtConstants.rl("block/tier_decoration/" + tierDecoration));

        getVariantBuilder(machine.getBlock().get())
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
                machine.getBlock().get(),
                inactive);

    }

    private void mekanismMachine(
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

    private void simpleFluid(Block liquidBlock) {
        getVariantBuilder(liquidBlock)
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(new ModelFile.UncheckedModelFile(Mekanism.rl("block/brine"))).build());
    }

}
