package com.takenokoshi.mekut.registries;

import com.takenokoshi.mekut.blockentity.abs.BEAbstractCompactSPS;
import com.takenokoshi.mekut.blockentity.interfaces.machine.IBiChemicalToObjectRecipeMachine;
import com.takenokoshi.mekut.blockentity.interfaces.machine.IFluidToObjectMachine;
import com.takenokoshi.mekut.blockentity.interfaces.machine.IItemStackChemicalToItemStackMachine;
import com.takenokoshi.mekut.blockentity.interfaces.machine.IItemStackListFluidChemicalToItemFluidChemicalRecipeMachine;
import com.takenokoshi.mekut.blockentity.interfaces.machine.IItemStackListFluidChemicalToItemRecipeMachine;
import com.takenokoshi.mekut.blockentity.normalmachine.BEChemicalCutter;
import com.takenokoshi.mekut.blockentity.normalmachine.BECompactSPS;
import com.takenokoshi.mekut.blockentity.normalmachine.BEIceMaker;
import com.takenokoshi.mekut.blockentity.normalmachine.BELazerCompressNucleoSynthesizer;
import com.takenokoshi.mekut.blockentity.normalmachine.BEMekStyledCharger;
import com.takenokoshi.mekut.blockentity.normalmachine.BESmallDigitalAssembler;
import com.takenokoshi.mekut.blockentity.normalmachine.BESmallDigitalReactionChamber;
import com.takenokoshi.mekut.blockentity.normalmachine.BEStellarGenesisChamber;
import com.takenokoshi.mekut.blockentity.normalmachine.BESubMaterialConverter;
import com.takenokoshi.mekut.blockentity.normalmachine.BETweakedEnergizedSmelter;
import com.takenokoshi.mekut.blockentity.standardmachine.BEStandardChemicalInjectionChamber;
import com.takenokoshi.mekut.blockentity.standardmachine.BEStandardCrusher;
import com.takenokoshi.mekut.blockentity.standardmachine.BEStandardEnergizedSmelter;
import com.takenokoshi.mekut.blockentity.standardmachine.BEStandardEnrichmentChamber;
import com.takenokoshi.mekut.blockentity.standardmachine.BEStandardMekStyledCharger;
import com.takenokoshi.mekut.blockentity.standardmachine.BEStandardPurificationChamber;
import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.core.MekUtMathUtils;
import com.takenokoshi.mekut.lang.MekUtDescription;
import com.takenokoshi.mekut.registration.GuiSizedMachineRegistryObject;
import com.takenokoshi.mekut.registration.MachineDeferredRegister;
import com.takenokoshi.mekut.registration.SimpleMachineRegistryObject;

import mekanism.api.Upgrade;
import mekanism.common.attachments.component.AttachedSideConfig;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.attachments.containers.chemical.ChemicalTanksBuilder;
import mekanism.common.attachments.containers.item.ItemSlotsBuilder;
import mekanism.common.config.MekanismConfig;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.registries.MekanismSounds;

public class MekUtMachines {
    public static final MachineDeferredRegister MACHINES = new MachineDeferredRegister(MekUtConstants.MODID);

    public static final SimpleMachineRegistryObject<BEChemicalCutter> CHEMICAL_CUTTER = MACHINES
            .registerSimple("chemical_cutter",
                    AttachedSideConfig.ADVANCED_MACHINE,
                    IItemStackChemicalToItemStackMachine::addContainersToItem,
                    BEChemicalCutter::new,
                    BEChemicalCutter.class,
                    MekUtDescription.CHEMICAL_CUTTER,
                    builder -> builder
                            .withSideConfig(TransmissionType.ITEM, TransmissionType.CHEMICAL, TransmissionType.ENERGY)
                            .withEnergyConfig(
                                    MekanismConfig.usage.chemicalCrystallizer,
                                    MekanismConfig.storage.chemicalCrystallizer)
                            .withSupportedUpgrades(Upgrade.SPEED, Upgrade.ENERGY, Upgrade.CHEMICAL));

    public static final SimpleMachineRegistryObject<BECompactSPS> COMPACT_SUPERCRITICAL_PHASE_SHIFTER = MACHINES
            .registerSimple("compact_supercritical_phase_shifter",
                    BEAbstractCompactSPS.SIDE_CONFIG,
                    holder -> holder
                            .addAttachmentOnlyContainers(ContainerType.CHEMICAL,
                                    () -> ChemicalTanksBuilder.builder()
                                            .addBasic(2000)
                                            .addBasic(2000)
                                            .build()),
                    BECompactSPS::new,
                    BECompactSPS.class,
                    MekUtDescription.COMPACT_SPS,
                    builder -> builder
                            .withSideConfig(TransmissionType.CHEMICAL, TransmissionType.ENERGY)
                            .withEnergyConfig(
                                    MekUtMathUtils.getMultiplied(
                                            MekanismConfig.general.spsEnergyPerInput,
                                            MekanismConfig.general.spsInputPerAntimatter),
                                    MekUtMathUtils.getMultiplied(
                                            MekanismConfig.general.spsEnergyPerInput,
                                            MekanismConfig.general.spsInputPerAntimatter,
                                            MekanismConfig.general.spsOutputTankCapacity))
                            .withSound(MekanismSounds.SPS)
                            .withSupportedUpgrades(Upgrade.MUFFLING));

    public static final SimpleMachineRegistryObject<BEIceMaker> ICE_MAKER = MACHINES
            .registerSimple("ice_maker",
                    IFluidToObjectMachine.SIDE_CONFIG_TO_ITEM,
                    IFluidToObjectMachine::addContainersFluidToItem,
                    BEIceMaker::new,
                    BEIceMaker.class,
                    MekUtDescription.ICE_MAKER,
                    builder -> builder
                            .withSideConfig(TransmissionType.ITEM, TransmissionType.FLUID, TransmissionType.ENERGY)
                            .withEnergyConfig(
                                    MekanismConfig.usage.chemicalCrystallizer,
                                    MekanismConfig.storage.chemicalCrystallizer)
                            .withSupportedUpgrades(Upgrade.SPEED, Upgrade.ENERGY));

    public static final SimpleMachineRegistryObject<BELazerCompressNucleoSynthesizer> LAZER_COMPRESS_NUCLEO_SYNTHESIZER = MACHINES
            .registerSimple("lazer_compress_nucleo_synthesizer",
                    AttachedSideConfig.CHEMICAL_INFUSING,
                    IBiChemicalToObjectRecipeMachine::addContainersBiChemicalToChemical,
                    BELazerCompressNucleoSynthesizer::new,
                    BELazerCompressNucleoSynthesizer.class,
                    MekUtDescription.LAZER_COMPRESS_NUCLEO_SYNTHESIZER,
                    builder -> builder
                            .withSideConfig(TransmissionType.ITEM, TransmissionType.CHEMICAL, TransmissionType.ENERGY)
                            .withEnergyConfig(
                                    MekanismConfig.usage.antiprotonicNucleosynthesizer,
                                    MekanismConfig.storage.antiprotonicNucleosynthesizer)
                            .withSound(MekanismSounds.ANTIPROTONIC_NUCLEOSYNTHESIZER)
                            .withSupportedUpgrades(Upgrade.SPEED, Upgrade.ENERGY, Upgrade.MUFFLING));

    public static final SimpleMachineRegistryObject<BEMekStyledCharger> MEKSTYLED_CHARGER = MACHINES
            .registerSimple("mekstyled_charger",
                    AttachedSideConfig.ELECTRIC_MACHINE,
                    holder -> holder
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
                            .withSupportedUpgrades(Upgrade.ENERGY, Upgrade.SPEED, Upgrade.MUFFLING));

    public static final GuiSizedMachineRegistryObject<BESmallDigitalAssembler> SMALL_DIGITAL_ASSEMBLER = MACHINES
            .registerGuiSized("small_digital_assembler",
                    IItemStackListFluidChemicalToItemRecipeMachine.SIDE_CONFIG,
                    IItemStackListFluidChemicalToItemRecipeMachine::addContainersToItem,
                    BESmallDigitalAssembler::new,
                    BESmallDigitalAssembler.class,
                    MekUtDescription.SMALL_DIGITAL_ASSEMBLER,
                    builder -> builder
                            .withSideConfig(TransmissionType.ITEM, TransmissionType.FLUID, TransmissionType.CHEMICAL,
                                    TransmissionType.ENERGY)
                            .withEnergyConfig(MekanismConfig.usage.formulaicAssemblicator,
                                    MekanismConfig.storage.formulaicAssemblicator)
                            .withSound(MekanismSounds.CHEMICAL_CRYSTALLIZER)
                            .withSupportedUpgrades(Upgrade.ENERGY, Upgrade.SPEED, Upgrade.MUFFLING));

    public static final GuiSizedMachineRegistryObject<BESmallDigitalReactionChamber> SMALL_DIGITAL_REACTION_CHAMBER = MACHINES
            .registerGuiSized("small_digital_reaction_chamber",
                    IItemStackListFluidChemicalToItemFluidChemicalRecipeMachine.SIDE_CONFIG,
                    IItemStackListFluidChemicalToItemFluidChemicalRecipeMachine::addContainersToItem,
                    BESmallDigitalReactionChamber::new,
                    BESmallDigitalReactionChamber.class,
                    MekUtDescription.SMALL_DIGITAL_REACTION_CHAMBER,
                    builder -> builder
                            .withSideConfig(TransmissionType.ITEM, TransmissionType.FLUID, TransmissionType.CHEMICAL,
                                    TransmissionType.ENERGY)
                            .withEnergyConfig(MekanismConfig.usage.pressurizedReactionBase,
                                    MekanismConfig.storage.chemicalCrystallizer)
                            .withSound(MekanismSounds.PRECISION_SAWMILL)
                            .withSupportedUpgrades(Upgrade.ENERGY, Upgrade.SPEED, Upgrade.MUFFLING));

    public static final SimpleMachineRegistryObject<BEStellarGenesisChamber> STELLAR_GENESIS_CHAMBER = MACHINES
            .registerSimple("stellar_genesis_chamber",
                    IBiChemicalToObjectRecipeMachine.SIDE_CONFIG_TO_ITEM,
                    IBiChemicalToObjectRecipeMachine::addContainersBiChemicalToItem,
                    BEStellarGenesisChamber::new,
                    BEStellarGenesisChamber.class,
                    MekUtDescription.STELLAR_GENESIS_CHAMBER,
                    builder -> builder
                            .withSideConfig(TransmissionType.ITEM, TransmissionType.CHEMICAL,
                                    TransmissionType.ENERGY)
                            .withEnergyConfig(
                                    MekUtMathUtils.getMultiplied(
                                            MekanismConfig.general.spsEnergyPerInput,
                                            MekanismConfig.general.spsInputPerAntimatter),
                                    MekUtMathUtils.getMultiplied(
                                            MekanismConfig.general.spsEnergyPerInput,
                                            MekanismConfig.general.spsInputPerAntimatter,
                                            MekanismConfig.general.spsOutputTankCapacity))
                            .withSound(MekanismSounds.SPS)
                            .withSupportedUpgrades(Upgrade.ENERGY, Upgrade.SPEED, Upgrade.MUFFLING));

    public static final SimpleMachineRegistryObject<BESubMaterialConverter> SUBMATERIAL_CONVERTER = MACHINES
            .registerSimple("submaterial_converter",
                    BESubMaterialConverter.SIDE_CONFIG,
                    holder -> holder
                            .addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                                    .addInput(1)
                                    .addChemicalDrainSlot(0)
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
                                    .addChemicalFillSlot(0)
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

    public static final SimpleMachineRegistryObject<BEStandardPurificationChamber> STANDARD_PURIFICATION_CHAMBER = MACHINES
            .registerSimple("standard_purification_chamber",
                    holder -> holder
                            .addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                                    .addInput(1)
                                    .addOutput()
                                    .addEnergy()
                                    .addChemicalFillSlot(0)
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
