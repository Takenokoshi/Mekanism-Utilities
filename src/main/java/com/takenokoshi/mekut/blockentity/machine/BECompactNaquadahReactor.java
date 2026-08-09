package com.takenokoshi.mekut.blockentity.machine;

import java.util.function.Consumer;
import java.util.function.LongSupplier;

import com.jerry.genextras.common.GeneratorExtraTags;
import com.jerry.genextras.common.config.GeneratorsExtraConfig;
import com.jerry.genextras.common.registries.GenExtraChemicals;
import com.jerry.mekextras.common.registries.ExtraChemicals;
import com.takenokoshi.mekut.blockentity.abs.BEAbstractCompactFusionReactor;

import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.attachments.containers.chemical.ChemicalTanksBuilder;
import mekanism.common.attachments.containers.fluid.FluidTanksBuilder;
import mekanism.common.attachments.containers.item.ItemSlotsBuilder;
import mekanism.common.registration.impl.ItemRegistryObject;
import mekanism.common.registries.MekanismChemicals;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BECompactNaquadahReactor extends BEAbstractCompactFusionReactor {

    public static Consumer<ItemRegistryObject<?>> getContainerAdder(LongSupplier fuelTankCapacity) {
        return (value) -> {
            value.addAttachmentOnlyContainers(ContainerType.ITEM,
                    () -> ItemSlotsBuilder.builder()
                            .addChemicalFillSlot(0)
                            .addChemicalFillSlot(1)
                            .addChemicalFillSlot(2)
                            .addBasic(1)
                            .addChemicalDrainSlot(3)
                            .addEnergy()
                            .build());
            value.addAttachmentOnlyContainers(ContainerType.FLUID,
                    () -> FluidTanksBuilder.builder()
                            .addBasic(Integer.MAX_VALUE)
                            .build());
            value.addAttachmentOnlyContainers(ContainerType.CHEMICAL,
                    () -> ChemicalTanksBuilder.builder()
                            .addBasic(fuelTankCapacity)
                            .addBasic(fuelTankCapacity)
                            .addBasic(fuelTankCapacity)
                            .addBasic(Long.MAX_VALUE)
                            .build());
        };
    }

    public BECompactNaquadahReactor(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state,
                GeneratorExtraTags.Chemicals.RICH_NAQUADAH_FUEL,
                GeneratorExtraTags.Chemicals.NAQUADAH_URANIUM_FUEL,
                GeneratorExtraTags.Chemicals.RICH_URANIUM_FUEL,
                ExtraChemicals.NAQUADAH_URANIUM_FUEL,
                GenExtraChemicals.POLONIUM_CONTAINING_STEAM,
                MekanismChemicals.STEAM,
                400_000_000.0d,
                GeneratorsExtraConfig.extraGenerators.reactorWaterPerInjection,
                GeneratorsExtraConfig.extraGenerators.reactorSteamPerInjection,
                GeneratorsExtraConfig.extraGenerators.energyPerReactorFuel,
                GeneratorsExtraConfig.extraGenerators.reactorWaterHeatingRatio,
                GeneratorsExtraConfig.extraGenerators.reactorCasingThermalConductivity,
                GeneratorsExtraConfig.extraGenerators.reactorThermocoupleEfficiency);
    }

    @Override
    protected long initEnergyContainerCapacity() {
        return GeneratorsExtraConfig.extraGenerators.reactorEnergyCapacity.getAsLong();
    }

    @Override
    protected long initFuelTankCapacity() {
        return GeneratorsExtraConfig.extraGenerators.reactorFuelCapacity.getAsLong();
    }

    @Override
    protected double initInverseConductionCoefficient() {
        return 1 / GeneratorsExtraConfig.extraGenerators.reactorCasingThermalConductivity.get();
    }

}
