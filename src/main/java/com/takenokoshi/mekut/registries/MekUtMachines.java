package com.takenokoshi.mekut.registries;

import com.takenokoshi.mekut.blockentity.normalmachine.BEMekStyledCharger;
import com.takenokoshi.mekut.blockentity.normalmachine.BEMekStyledCircuitCutter;
import com.takenokoshi.mekut.blockentity.normalmachine.BESubMaterialConverter;
import com.takenokoshi.mekut.blockentity.normalmachine.BETweakedEnergizedSmelter;
import com.takenokoshi.mekut.blockentity.standardmachine.BEStandardChemicalInjectionChamber;
import com.takenokoshi.mekut.blockentity.standardmachine.BEStandardCrusher;
import com.takenokoshi.mekut.blockentity.standardmachine.BEStandardEnergizedSmelter;
import com.takenokoshi.mekut.blockentity.standardmachine.BEStandardEnrichmentChamber;
import com.takenokoshi.mekut.blockentity.standardmachine.BEStandardMekStyledCharger;
import com.takenokoshi.mekut.blockentity.standardmachine.BEStandardMekStyledCircuitCutter;
import com.takenokoshi.mekut.blockentity.standardmachine.BEStandardPurificationChamber;
import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.core.MekUtMathUtils;
import com.takenokoshi.mekut.lang.MekUtDescription;
import com.takenokoshi.mekut.registration.MachineDeferredRegister;
import com.takenokoshi.mekut.registration.SimpleMachineRegistryObject;

import mekanism.api.Upgrade;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.attachments.containers.chemical.ChemicalTanksBuilder;
import mekanism.common.attachments.containers.item.ItemSlotsBuilder;
import mekanism.common.config.MekanismConfig;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.registries.MekanismSounds;

public class MekUtMachines {
    public static final MachineDeferredRegister MACHINES = new MachineDeferredRegister(MekUtConstants.MODID);

    public static final SimpleMachineRegistryObject<BEMekStyledCharger> MEKSTYLED_CHARGER = MACHINES
            .registerSimple("mekstyled_charger", holder -> holder
                    .addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                            .addInput(1)
                            .addOutput()
                            .addEnergy()
                            .build()),
                    BEMekStyledCharger::new,
                    BEMekStyledCharger.class,
                    MekUtDescription.MEKSTYLED_CHARGER,
                    builder -> builder
                            .withSideConfig(TransmissionType.ITEM, TransmissionType.ENERGY)
                            .withEnergyConfig(MekanismConfig.usage.enrichmentChamber,
                                    MekanismConfig.storage.enrichmentChamber)
                            .withSound(MekanismSounds.ENRICHMENT_CHAMBER)
                            .withSupportedUpgrades(Upgrade.ENERGY, Upgrade.SPEED));

    public static final SimpleMachineRegistryObject<BEMekStyledCircuitCutter> MEKSTYLED_CIRCUIT_CUTTER = MACHINES
            .registerSimple("mekstyled_circuit_cutter", holder -> holder
                    .addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                            .addInput(1)
                            .addOutput()
                            .addEnergy()
                            .build()),
                    BEMekStyledCircuitCutter::new,
                    BEMekStyledCircuitCutter.class,
                    MekUtDescription.MEKSTYLED_CIRCUIT_CUTTER,
                    builder -> builder
                            .withSideConfig(TransmissionType.ITEM, TransmissionType.ENERGY)
                            .withEnergyConfig(MekanismConfig.usage.precisionSawmill,
                                    MekanismConfig.storage.precisionSawmill)
                            .withSound(MekanismSounds.PRECISION_SAWMILL)
                            .withSupportedUpgrades(Upgrade.ENERGY, Upgrade.SPEED));

    public static final SimpleMachineRegistryObject<BESubMaterialConverter> SUBMATERIAL_CONVERTER = MACHINES
            .registerSimple("submaterial_converter",
                    holder -> holder
                            .addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                                    .addInput(1)
                                    .build())
                            .addAttachmentOnlyContainers(ContainerType.CHEMICAL, () -> ChemicalTanksBuilder.builder()
                                    .addBasic(Long.MAX_VALUE)
                                    .build()),
                    BESubMaterialConverter::new,
                    BESubMaterialConverter.class,
                    MekUtDescription.SUBMATERIAL_CONVERTER,
                    builder -> builder
                            .withSideConfig(TransmissionType.ITEM, TransmissionType.CHEMICAL)
                            .withSupportedUpgrades(Upgrade.MUFFLING));

    public static final SimpleMachineRegistryObject<BETweakedEnergizedSmelter> TWEAKED_ENERGIZED_SMELTER = MACHINES
            .registerSimple("tweaked_energized_smelter",
                    holder -> holder
                            .addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                                    .addInput(1)
                                    .addOutput()
                                    .addEnergy()
                                    .build())
                            .addAttachmentOnlyContainers(ContainerType.CHEMICAL, () -> ChemicalTanksBuilder.builder()
                                    .addBasic(Long.MAX_VALUE)
                                    .build()),
                    BETweakedEnergizedSmelter::new,
                    BETweakedEnergizedSmelter.class,
                    MekUtDescription.TWEAKED_ENERGIZED_SMELTER,
                    builder -> builder
                            .withSideConfig(TransmissionType.ITEM, TransmissionType.ENERGY, TransmissionType.CHEMICAL)
                            .withEnergyConfig(MekanismConfig.usage.energizedSmelter,
                                    MekanismConfig.storage.energizedSmelter)
                            .withSound(MekanismSounds.ENERGIZED_SMELTER)
                            .withSupportedUpgrades(Upgrade.ENERGY, Upgrade.SPEED, Upgrade.MUFFLING));

    public static final SimpleMachineRegistryObject<BEStandardChemicalInjectionChamber> STANDARD_CHEMICAL_INJECTION_CHAMBER = MACHINES
            .registerSimple("standard_chemical_injection_chamber",
                    holder -> holder
                            .addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                                    .addInput(1)
                                    .addOutput()
                                    .addEnergy()
                                    .build())
                            .addAttachmentOnlyContainers(ContainerType.CHEMICAL, () -> ChemicalTanksBuilder.builder()
                                    .addBasic(4800)
                                    .build()),
                    BEStandardChemicalInjectionChamber::new,
                    BEStandardChemicalInjectionChamber.class,
                    MekUtDescription.STANDARD_MACHINE,
                    builder -> builder
                            .withSideConfig(TransmissionType.ITEM, TransmissionType.ENERGY, TransmissionType.CHEMICAL)
                            .withEnergyConfig(
                                    MekUtMathUtils.getUsageAccelerated(MekanismConfig.usage.chemicalInjectionChamber,
                                            1),
                                    MekUtMathUtils
                                            .getStorageAccelerated(MekanismConfig.storage.chemicalInjectionChamber, 1))
                            .withSound(MekanismSounds.CHEMICAL_INJECTION_CHAMBER)
                            .withSupportedUpgrades(Upgrade.MUFFLING));

    public static final SimpleMachineRegistryObject<BEStandardCrusher> STANDARD_CRUSHER = MACHINES
            .registerSimple("standard_crusher", holder -> holder
                    .addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                            .addInput(1)
                            .addOutput()
                            .addEnergy()
                            .build()),
                    BEStandardCrusher::new,
                    BEStandardCrusher.class,
                    MekUtDescription.STANDARD_MACHINE,
                    builder -> builder
                            .withSideConfig(TransmissionType.ITEM, TransmissionType.ENERGY)
                            .withEnergyConfig(
                                    MekUtMathUtils.getUsageAccelerated(MekanismConfig.usage.crusher, 1),
                                    MekUtMathUtils.getStorageAccelerated(MekanismConfig.storage.crusher, 1))
                            .withSupportedUpgrades(Upgrade.MUFFLING));

    public static final SimpleMachineRegistryObject<BEStandardEnergizedSmelter> STANDARD_ENERGIZED_SMELTER = MACHINES
            .registerSimple("standard_energized_smelter",
                    holder -> holder
                            .addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                                    .addInput(1)
                                    .addOutput()
                                    .addEnergy()
                                    .build())
                            .addAttachmentOnlyContainers(ContainerType.CHEMICAL, () -> ChemicalTanksBuilder.builder()
                                    .addBasic(Long.MAX_VALUE)
                                    .build()),
                    BEStandardEnergizedSmelter::new,
                    BEStandardEnergizedSmelter.class,
                    MekUtDescription.STANDARD_MACHINE,
                    builder -> builder
                            .withSideConfig(TransmissionType.ITEM, TransmissionType.ENERGY, TransmissionType.CHEMICAL)
                            .withEnergyConfig(
                                    MekUtMathUtils.getUsageAccelerated(MekanismConfig.usage.energizedSmelter, 1),
                                    MekUtMathUtils.getStorageAccelerated(MekanismConfig.storage.energizedSmelter, 1))
                            .withSound(MekanismSounds.ENERGIZED_SMELTER)
                            .withSupportedUpgrades(Upgrade.MUFFLING));

    public static final SimpleMachineRegistryObject<BEStandardEnrichmentChamber> STANDARD_ENRICHMENR_CHAMBER = MACHINES
            .registerSimple("standard_enrichment_chamber", holder -> holder
                    .addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                            .addInput(1)
                            .addOutput()
                            .addEnergy()
                            .build()),
                    BEStandardEnrichmentChamber::new,
                    BEStandardEnrichmentChamber.class,
                    MekUtDescription.STANDARD_MACHINE,
                    builder -> builder
                            .withSideConfig(TransmissionType.ITEM, TransmissionType.ENERGY)
                            .withEnergyConfig(
                                    MekUtMathUtils.getUsageAccelerated(MekanismConfig.usage.enrichmentChamber, 1),
                                    MekUtMathUtils.getStorageAccelerated(MekanismConfig.storage.enrichmentChamber, 1))
                            .withSupportedUpgrades(Upgrade.MUFFLING));

    public static final SimpleMachineRegistryObject<BEStandardMekStyledCharger> STANDARD_MEKSTYLED_CHARGER = MACHINES
            .registerSimple("standard_mekstyled_charger", holder -> holder
                    .addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                            .addInput(1)
                            .addOutput()
                            .addEnergy()
                            .build()),
                    BEStandardMekStyledCharger::new,
                    BEStandardMekStyledCharger.class,
                    MekUtDescription.STANDARD_MACHINE,
                    builder -> builder
                            .withSideConfig(TransmissionType.ITEM, TransmissionType.ENERGY)
                            .withEnergyConfig(
                                    MekUtMathUtils.getUsageAccelerated(MekanismConfig.usage.enrichmentChamber, 1),
                                    MekUtMathUtils.getStorageAccelerated(MekanismConfig.storage.enrichmentChamber, 1))
                            .withSound(MekanismSounds.ENRICHMENT_CHAMBER)
                            .withSupportedUpgrades(Upgrade.MUFFLING));

    public static final SimpleMachineRegistryObject<BEStandardMekStyledCircuitCutter> STANDARD_MEKSTYLED_CIRCUIT_CUTTER = MACHINES
            .registerSimple("standard_mekstyled_circuit_cutter", holder -> holder
                    .addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                            .addInput(1)
                            .addOutput()
                            .addEnergy()
                            .build()),
                    BEStandardMekStyledCircuitCutter::new,
                    BEStandardMekStyledCircuitCutter.class,
                    MekUtDescription.STANDARD_MACHINE,
                    builder -> builder
                            .withSideConfig(TransmissionType.ITEM, TransmissionType.ENERGY)
                            .withEnergyConfig(
                                    MekUtMathUtils.getUsageAccelerated(MekanismConfig.usage.precisionSawmill, 1),
                                    MekUtMathUtils.getStorageAccelerated(MekanismConfig.storage.precisionSawmill, 1))
                            .withSound(MekanismSounds.PRECISION_SAWMILL)
                            .withSupportedUpgrades(Upgrade.MUFFLING));

    public static final SimpleMachineRegistryObject<BEStandardPurificationChamber> STANDARD_PURIFICATION_CHAMBER = MACHINES
            .registerSimple("standard_purification_chamber",
                    holder -> holder
                            .addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                                    .addInput(1)
                                    .addOutput()
                                    .addEnergy()
                                    .build())
                            .addAttachmentOnlyContainers(ContainerType.CHEMICAL, () -> ChemicalTanksBuilder.builder()
                                    .addBasic(4800)
                                    .build()),
                    BEStandardPurificationChamber::new,
                    BEStandardPurificationChamber.class,
                    MekUtDescription.STANDARD_MACHINE,
                    builder -> builder
                            .withSideConfig(TransmissionType.ITEM, TransmissionType.ENERGY, TransmissionType.CHEMICAL)
                            .withEnergyConfig(
                                    MekUtMathUtils.getUsageAccelerated(MekanismConfig.usage.purificationChamber,
                                            1),
                                    MekUtMathUtils
                                            .getStorageAccelerated(MekanismConfig.storage.purificationChamber, 1))
                            .withSound(MekanismSounds.PURIFICATION_CHAMBER)
                            .withSupportedUpgrades(Upgrade.MUFFLING));
}
