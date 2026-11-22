package takenokoshi.mekut.common.registries;

import mekanism.common.attachments.component.AttachedSideConfig;
import mekanism.common.block.attribute.AttributeSideConfig;
import mekanism.common.config.MekanismConfig;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.registries.MekanismSounds;
import takenokoshi.mekal.common.attachments.component.MekALAttachedSideConfig;
import takenokoshi.mekal.common.registration.MachineDeferredRegister;
import takenokoshi.mekal.common.registration.MachineRegistryHelper;
import takenokoshi.mekal.common.registration.SimpleMachineRegistryObject;
import takenokoshi.mekut.common.MekUt;
import takenokoshi.mekut.common.recipe.MekUtRecipeType;
import takenokoshi.mekut.common.tile.machine.TileEntityFreezingMachine;
import takenokoshi.mekut.common.tile.machine.TileEntityHydraulicExtractor;
import takenokoshi.mekut.common.tile.machine.TileEntityMineralExtractor;
import takenokoshi.mekut.common.tile.machine.TileEntitySqueezer;

public class MekUtMachines {
    public static final MachineDeferredRegister MACHINES = new MachineDeferredRegister(MekUt.MODID);

    public static final SimpleMachineRegistryObject<TileEntityFreezingMachine> FREEZING_MACHINE = MACHINES
            .registerSimple("freezing_machine",
                    MekALAttachedSideConfig.FLUID_TO_ITEM_MACHINE,
                    holder -> MachineRegistryHelper.addAttachmentOnlyContainersFluidToItemMachine(
                            holder, MekUtRecipeType.FREEZING),
                    TileEntityFreezingMachine::new,
                    TileEntityFreezingMachine.class,
                    builder -> builder
                            .withSound(MekanismSounds.ROTARY_CONDENSENTRATOR)
                            .withEnergyConfig(MekanismConfig.usage.chemicalOxidizer,
                                    MekanismConfig.storage.chemicalOxidizer)
                            .withSideConfig(new TransmissionType[] {
                                    TransmissionType.ITEM, TransmissionType.FLUID, TransmissionType.ENERGY,
                            }));

    public static final SimpleMachineRegistryObject<TileEntityHydraulicExtractor> HYDRAULIC_EXTRACTOR = MACHINES
            .registerSimple("hydraulic_extractor",
                    MekALAttachedSideConfig.ITEM_FLUID_TO_ITEM_MACHINE,
                    holder -> MachineRegistryHelper.addAttachmentOnlyContainersItemFluidToItemMachine(
                            holder, MekUtRecipeType.HYDRAULIC_EXTRACTING),
                    TileEntityHydraulicExtractor::new,
                    TileEntityHydraulicExtractor.class,
                    builder -> builder
                            .withSound(MekanismSounds.PURIFICATION_CHAMBER)
                            .withEnergyConfig(MekanismConfig.usage.purificationChamber,
                                    MekanismConfig.storage.purificationChamber)
                            .withSideConfig(new TransmissionType[] {
                                    TransmissionType.ITEM, TransmissionType.FLUID, TransmissionType.ENERGY,
                            }));

    public static final SimpleMachineRegistryObject<TileEntityMineralExtractor> MINERAL_EXTRACTOR = MACHINES
            .registerSimple("mineral_extractor",
                    AttachedSideConfig.ELECTRIC_MACHINE,
                    holder -> MachineRegistryHelper.addAttachmentOnlyContainersElectricMachine(
                            holder, MekUtRecipeType.MINERAL_EXTRACTING),
                    TileEntityMineralExtractor::new,
                    TileEntityMineralExtractor.class,
                    builder -> builder
                            .withSound(MekanismSounds.ENRICHMENT_CHAMBER)
                            .withEnergyConfig(MekanismConfig.usage.enrichmentChamber,
                                    MekanismConfig.storage.enrichmentChamber)
                            .with(AttributeSideConfig.ELECTRIC_MACHINE));

    public static final SimpleMachineRegistryObject<TileEntitySqueezer> SQUEEZER = MACHINES
            .registerSimple("squeezer",
                    MekALAttachedSideConfig.ITEM_TO_FLUID_MACHINE,
                    holder -> MachineRegistryHelper.addAttachmentOnlyContainersItemToFluidMachine(holder,
                            MekUtRecipeType.SQUEEZING),
                    TileEntitySqueezer::new,
                    TileEntitySqueezer.class,
                    builder -> builder
                            .withSound(MekanismSounds.CHEMICAL_OXIDIZER)
                            .withEnergyConfig(MekanismConfig.usage.chemicalOxidizer,
                                    MekanismConfig.storage.chemicalOxidizer)
                            .withSideConfig(new TransmissionType[] {
                                    TransmissionType.ITEM, TransmissionType.FLUID, TransmissionType.ENERGY,
                            }));
}
