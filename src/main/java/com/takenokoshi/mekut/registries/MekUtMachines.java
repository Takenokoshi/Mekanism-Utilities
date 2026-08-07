package com.takenokoshi.mekut.registries;

import com.takenokoshi.mekaddonlib.registration.GuiSizedMachineRegistryObject;
import com.takenokoshi.mekaddonlib.registration.MachineDeferredRegister;
import com.takenokoshi.mekaddonlib.registration.SimpleMachineRegistryObject;
import com.takenokoshi.mekut.blockentity.abs.BEAbstractCompactBoiler;
import com.takenokoshi.mekut.blockentity.abs.BEAbstractCompactFissionReactor;
import com.takenokoshi.mekut.blockentity.abs.BEAbstractCompactFusionReactor;
import com.takenokoshi.mekut.blockentity.abs.BEAbstractCompactIndustrialTurbine;
import com.takenokoshi.mekut.blockentity.abs.BEAbstractCompactSPS;
import com.takenokoshi.mekut.blockentity.abs.BEAbstractCompactThermalEvaporationPlant;
import com.takenokoshi.mekut.blockentity.abs.BEAbstractEnergizedSmelter;
import com.takenokoshi.mekut.blockentity.interfaces.machine.IBiChemicalToObjectRecipeMachine;
import com.takenokoshi.mekut.blockentity.interfaces.machine.IFluidToObjectMachine;
import com.takenokoshi.mekut.blockentity.interfaces.machine.IItemStackChemicalToItemStackMachine;
import com.takenokoshi.mekut.blockentity.interfaces.machine.IItemStackListFluidChemicalToItemFluidChemicalRecipeMachine;
import com.takenokoshi.mekut.blockentity.interfaces.machine.IItemStackListFluidChemicalToItemRecipeMachine;
import com.takenokoshi.mekut.blockentity.misc.BEChemicalRatioSplitter;
import com.takenokoshi.mekut.blockentity.misc.BEFluidRatioSplitter;
import com.takenokoshi.mekut.blockentity.misc.BEItemRatioSplitter;
import com.takenokoshi.mekut.blockentity.normalmachine.BEChemicalCutter;
import com.takenokoshi.mekut.blockentity.normalmachine.BECompactThermalEvaporationPlant;
import com.takenokoshi.mekut.blockentity.normalmachine.BECompactBoiler;
import com.takenokoshi.mekut.blockentity.normalmachine.BECompactFissionReactor;
import com.takenokoshi.mekut.blockentity.normalmachine.BECompactFusionReactor;
import com.takenokoshi.mekut.blockentity.normalmachine.BECompactIndustrialTurbine;
import com.takenokoshi.mekut.blockentity.normalmachine.BECompactSPS;
import com.takenokoshi.mekut.blockentity.normalmachine.BEIceMaker;
import com.takenokoshi.mekut.blockentity.normalmachine.BELazerCompressNucleoSynthesizer;
import com.takenokoshi.mekut.blockentity.normalmachine.BEMekStyledCharger;
import com.takenokoshi.mekut.blockentity.normalmachine.BESmallDigitalAssembler;
import com.takenokoshi.mekut.blockentity.normalmachine.BESmallDigitalReactionChamber;
import com.takenokoshi.mekut.blockentity.normalmachine.BEStellarGenesisChamber;
import com.takenokoshi.mekut.blockentity.normalmachine.BESubMaterialConverter;
import com.takenokoshi.mekut.blockentity.normalmachine.BETweakedEnergizedSmelter;
import com.takenokoshi.mekut.blockentity.normalmachine.BlockEntityXpTank;
import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.core.MekUtMathUtils;
import mekanism.api.Upgrade;
import mekanism.common.attachments.component.AttachedSideConfig;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.attachments.containers.chemical.ChemicalTanksBuilder;
import mekanism.common.attachments.containers.item.ItemSlotsBuilder;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.config.MekanismConfig;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.registries.MekanismSounds;
import mekanism.generators.common.registries.GeneratorsSounds;

public class MekUtMachines {
    public static final MachineDeferredRegister MACHINES = new MachineDeferredRegister(MekUtConstants.MODID);

    public static final SimpleMachineRegistryObject<BEChemicalCutter> CHEMICAL_CUTTER = MACHINES
            .registerSimple("chemical_cutter",
                    AttachedSideConfig.ADVANCED_MACHINE,
                    IItemStackChemicalToItemStackMachine.getContainerAdder(200000)::accept,
                    BEChemicalCutter::new,
                    BEChemicalCutter.class,
                    builder -> builder
                            .withSideConfig(TransmissionType.ITEM, TransmissionType.CHEMICAL, TransmissionType.ENERGY)
                            .withEnergyConfig(
                                    MekanismConfig.usage.chemicalCrystallizer,
                                    MekanismConfig.storage.chemicalCrystallizer)
                            .withSupportedUpgrades(Upgrade.SPEED, Upgrade.ENERGY, Upgrade.CHEMICAL));

    public static final GuiSizedMachineRegistryObject<BECompactBoiler> COMPACT_BOILER = MACHINES
            .registerGuiSized("compact_boiler",
                    BEAbstractCompactBoiler.SIDE_CONFIG,
                    BEAbstractCompactBoiler.getContainerAdder(962_560_000L, 259_200_000L, 414_720_000L,
                            60_160_000)::accept,
                    BECompactBoiler::new,
                    BECompactBoiler.class,
                    builder -> builder
                            .withSideConfig(TransmissionType.CHEMICAL, TransmissionType.FLUID, TransmissionType.HEAT)
                            .withSound(MekanismSounds.CHARGEPAD)
                            .withSupportedUpgrades(Upgrade.MUFFLING));

    public static final GuiSizedMachineRegistryObject<BECompactFissionReactor> COMPACT_FISSION_REACTOR = MACHINES
            .registerGuiSized("compact_fission_reactor",
                    BEAbstractCompactFissionReactor.SIDE_CONFIG,
                    item -> BEAbstractCompactFissionReactor.addContainers(item,
                            15_360_000l,
                            1736000.0d,
                            583_200_000,
                            583_200_000l,
                            5_832_000_000l),
                    BECompactFissionReactor::new,
                    BECompactFissionReactor.class,
                    builder -> builder
                            .withSideConfig(TransmissionType.CHEMICAL, TransmissionType.FLUID, TransmissionType.HEAT)
                            .withSound(GeneratorsSounds.FISSION_REACTOR)
                            .withSupportedUpgrades(Upgrade.MUFFLING));

    public static final GuiSizedMachineRegistryObject<BECompactFusionReactor> COMPACT_FUSION_REACTOR = MACHINES
            .registerGuiSized("compact_fusion_reactor",
                    BEAbstractCompactFusionReactor.SIDE_CONFIG,
                    BEAbstractCompactFusionReactor.getContainerAdder(1000L)::accept,
                    BECompactFusionReactor::new,
                    builder -> builder
                            .withSimple(Capabilities.LASER_RECEPTOR),
                    BECompactFusionReactor.class,
                    builder -> builder
                            .withSideConfig(TransmissionType.values())
                            .withSound(GeneratorsSounds.FUSION_REACTOR)
                            .withSupportedUpgrades(Upgrade.MUFFLING));

    public static final SimpleMachineRegistryObject<BECompactIndustrialTurbine> COMPACT_INDUSTRIAL_TURBINE = MACHINES
            .registerSimple("compact_industrial_turbine",
                    BEAbstractCompactIndustrialTurbine.SIDE_CONFIG,
                    BEAbstractCompactIndustrialTurbine.getContainerAdder(186_368_000L, 12_992_000)::accept,
                    BECompactIndustrialTurbine::new,
                    BECompactIndustrialTurbine.class,
                    builder -> builder
                            .withSideConfig(TransmissionType.CHEMICAL, TransmissionType.ENERGY, TransmissionType.FLUID,
                                    TransmissionType.ITEM)
                            .withSupportedUpgrades(Upgrade.FILTER));

    public static final SimpleMachineRegistryObject<BECompactSPS> COMPACT_SUPERCRITICAL_PHASE_SHIFTER = MACHINES
            .registerSimple("compact_supercritical_phase_shifter",
                    AttachedSideConfig.CENTRIFUGE,
                    BEAbstractCompactSPS.getContainerAdder(2000)::accept,
                    BECompactSPS::new,
                    BECompactSPS.class,
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

    public static final GuiSizedMachineRegistryObject<BECompactThermalEvaporationPlant> COMPACT_THERMAL_EVAPOLATION_PLANT = MACHINES
            .registerGuiSized("compact_thermal_evaporation_plant",
                    BEAbstractCompactThermalEvaporationPlant.SIDE_CONFIG,
                    BEAbstractCompactThermalEvaporationPlant.getContainerAdder(4_608_000)::accept,
                    BECompactThermalEvaporationPlant::new,
                    BECompactThermalEvaporationPlant.class,
                    builder -> builder
                            .withSideConfig(TransmissionType.FLUID, TransmissionType.ITEM, TransmissionType.HEAT)
                            .withSupportedUpgrades(Upgrade.MUFFLING));

    public static final SimpleMachineRegistryObject<BEIceMaker> ICE_MAKER = MACHINES
            .registerSimple("ice_maker",
                    IFluidToObjectMachine.SIDE_CONFIG_TO_ITEM,
                    IFluidToObjectMachine.getToItemContainerAdder(20000)::accept,
                    BEIceMaker::new,
                    BEIceMaker.class,
                    builder -> builder
                            .withSideConfig(TransmissionType.ITEM, TransmissionType.FLUID, TransmissionType.ENERGY)
                            .withEnergyConfig(
                                    MekanismConfig.usage.chemicalCrystallizer,
                                    MekanismConfig.storage.chemicalCrystallizer)
                            .withSupportedUpgrades(Upgrade.SPEED, Upgrade.ENERGY));

    public static final SimpleMachineRegistryObject<BELazerCompressNucleoSynthesizer> LAZER_COMPRESS_NUCLEO_SYNTHESIZER = MACHINES
            .registerSimple("lazer_compress_nucleo_synthesizer",
                    AttachedSideConfig.CHEMICAL_INFUSING,
                    IBiChemicalToObjectRecipeMachine.getToChemicalContainerAdder(20000000l)::accept,
                    BELazerCompressNucleoSynthesizer::new,
                    BELazerCompressNucleoSynthesizer.class,
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
                    IBiChemicalToObjectRecipeMachine.getToItemContainerAdder(Long.MAX_VALUE)::accept,
                    BEStellarGenesisChamber::new,
                    BEStellarGenesisChamber.class,
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
                    builder -> builder
                            .withSideConfig(TransmissionType.ITEM, TransmissionType.CHEMICAL)
                            .withSupportedUpgrades(Upgrade.MUFFLING));

    public static final SimpleMachineRegistryObject<BETweakedEnergizedSmelter> TWEAKED_ENERGIZED_SMELTER = MACHINES
            .registerSimple("tweaked_energized_smelter",
                    AttachedSideConfig.CHEMICAL_OUT_MACHINE,
                    BEAbstractEnergizedSmelter::addContainersToItem,
                    BETweakedEnergizedSmelter::new,
                    BETweakedEnergizedSmelter.class,
                    builder -> builder
                            .withSideConfig(TransmissionType.ITEM, TransmissionType.ENERGY, TransmissionType.CHEMICAL)
                            .withEnergyConfig(MekanismConfig.usage.energizedSmelter,
                                    MekanismConfig.storage.energizedSmelter)
                            .withSound(MekanismSounds.ENERGIZED_SMELTER)
                            .withSupportedUpgrades(Upgrade.ENERGY, Upgrade.SPEED, Upgrade.MUFFLING));

    public static final SimpleMachineRegistryObject<BlockEntityXpTank> XP_TANK = MACHINES
            .registerSimple("xp_tank",
                    BlockEntityXpTank.SIDE_CONFIG,
                    BlockEntityXpTank::addContainersToItem,
                    BlockEntityXpTank::new,
                    BlockEntityXpTank.class,
                    builder -> builder.withSideConfig(TransmissionType.CHEMICAL));

    public static final SimpleMachineRegistryObject<BEItemRatioSplitter> ITEM_RATIO_SPLITTER = MACHINES
            .registerSimple("item_ratio_splitter",
                    BEItemRatioSplitter.SIDE_CONFIG,
                    BEItemRatioSplitter::addContainersToItem,
                    BEItemRatioSplitter::new,
                    BEItemRatioSplitter.class,
                    builder -> builder.withSideConfig(TransmissionType.ITEM));

    public static final SimpleMachineRegistryObject<BEFluidRatioSplitter> FLUID_RATIO_SPLITTER = MACHINES
            .registerSimple("fluid_ratio_splitter",
                    BEFluidRatioSplitter.SIDE_CONFIG,
                    BEFluidRatioSplitter::addContainersToItem,
                    BEFluidRatioSplitter::new,
                    BEFluidRatioSplitter.class,
                    builder -> builder.withSideConfig(TransmissionType.FLUID));

    public static final SimpleMachineRegistryObject<BEChemicalRatioSplitter> CHEMICAL_RATIO_SPLITTER = MACHINES
            .registerSimple("chemical_ratio_splitter",
                    BEChemicalRatioSplitter.SIDE_CONFIG,
                    BEChemicalRatioSplitter::addContainersToItem,
                    BEChemicalRatioSplitter::new,
                    BEChemicalRatioSplitter.class,
                    builder -> builder.withSideConfig(TransmissionType.CHEMICAL));
}
