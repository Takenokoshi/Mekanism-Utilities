package takenokoshi.mekut.common.registries;

import mekanism.common.attachments.component.AttachedSideConfig;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.attachments.containers.item.ItemSlotsBuilder;
import mekanism.common.block.attribute.AttributeSideConfig;
import mekanism.common.config.MekanismConfig;
import mekanism.common.recipe.lookup.cache.SingleInputRecipeCache;
import mekanism.common.registries.MekanismSounds;
import takenokoshi.mekal.common.registration.MachineDeferredRegister;
import takenokoshi.mekal.common.registration.SimpleMachineRegistryObject;
import takenokoshi.mekut.common.MekUt;
import takenokoshi.mekut.common.recipe.MekUtRecipeType;
import takenokoshi.mekut.common.tile.machine.TileEntityMineralExtractor;

public class MekUtMachines {
    public static final MachineDeferredRegister MACHINES = new MachineDeferredRegister(MekUt.MODID);

    public static final SimpleMachineRegistryObject<TileEntityMineralExtractor> MINERAL_EXTRACTOR = MACHINES
            .registerSimple("mineral_extractor",
                    AttachedSideConfig.ELECTRIC_MACHINE,
                    holder -> holder.addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                            .addInput(MekUtRecipeType.MINERAL_EXTRACTING, SingleInputRecipeCache::containsInput)
                            .addOutput()
                            .addEnergy()
                            .build()),
                    TileEntityMineralExtractor::new,
                    TileEntityMineralExtractor.class,
                    builder -> builder
                            .withSound(MekanismSounds.ENRICHMENT_CHAMBER)
                            .withEnergyConfig(MekanismConfig.usage.enrichmentChamber,
                                    MekanismConfig.storage.enrichmentChamber)
                            .with(AttributeSideConfig.ELECTRIC_MACHINE));
}
