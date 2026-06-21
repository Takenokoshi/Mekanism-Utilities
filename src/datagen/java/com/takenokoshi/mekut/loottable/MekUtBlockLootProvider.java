package com.takenokoshi.mekut.loottable;

import com.glodblock.github.extendedae.common.EAESingletons;
import com.takenokoshi.mekut.enums.MUMaterial;
import com.takenokoshi.mekut.registries.MekUtBlocks;
import com.takenokoshi.mekut.registries.MekUtItems;
import com.takenokoshi.mekut.registries.MekUtMachines;

import appeng.core.definitions.AEBlocks;
import mekanism.common.registries.MekanismBlocks;
import mekanism.common.resource.ore.OreType;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

//BaseBlockLootTables is copy of https://github.com/mekanism/Mekanism/blob/1.21.x/src/datagen/main/java/mekanism/common/loot/table/BaseBlockLootTables.java
public class MekUtBlockLootProvider extends BaseBlockLootTables {

    protected MekUtBlockLootProvider(Provider registries) {
        super(registries);
    }

    @Override
    protected void generate() {

        dropSelfWithContents(MekUtMachines.MACHINES.blockRegister.getPrimaryEntries());

        MekUtBlocks.RAW_MU_MATERIALS_BLOCK.forEach((material, registry) -> {
            dropSelf(registry.get());
        });

        createOreDrop(MekUtBlocks.AMETHYST_ORE.get(), MekUtItems.RAW_MU_MATERIALS.get(MUMaterial.AMETHYST).asItem(), 4);
        createOreDrop(MekUtBlocks.CERTUS_QUARTZ_ORE.get(),
                MekUtItems.RAW_MU_MATERIALS.get(MUMaterial.CERTUS_QUARTZ).asItem(), 4);
        createOreDrop(MekUtBlocks.NETHERITE_ORE.get(), MekUtItems.RAW_MU_MATERIALS.get(MUMaterial.NETHERITE).asItem(),1);

        createOreDrop(Blocks.AMETHYST_CLUSTER, MekUtItems.RAW_MU_MATERIALS.get(MUMaterial.AMETHYST).asItem(), 4);
        createOreDrop(Blocks.COAL_ORE, MekUtItems.RAW_MU_MATERIALS.get(MUMaterial.COAL).asItem(),1);
        createOreDrop(Blocks.DEEPSLATE_COAL_ORE, MekUtItems.RAW_MU_MATERIALS.get(MUMaterial.COAL).asItem(),1);
        createOreDrop(Blocks.DIAMOND_ORE, MekUtItems.RAW_MU_MATERIALS.get(MUMaterial.DIAMOND).asItem(),1);
        createOreDrop(Blocks.DEEPSLATE_DIAMOND_ORE, MekUtItems.RAW_MU_MATERIALS.get(MUMaterial.DIAMOND).asItem(),1);
        createOreDrop(Blocks.EMERALD_ORE, MekUtItems.RAW_MU_MATERIALS.get(MUMaterial.EMERALD).asItem(),1);
        createOreDrop(Blocks.DEEPSLATE_EMERALD_ORE, MekUtItems.RAW_MU_MATERIALS.get(MUMaterial.EMERALD).asItem(),1);
        createOreDrop(Blocks.LAPIS_ORE, MekUtItems.RAW_MU_MATERIALS.get(MUMaterial.LAPIS_LAZULI).asItem(), 4, 9);
        createOreDrop(Blocks.DEEPSLATE_LAPIS_ORE, MekUtItems.RAW_MU_MATERIALS.get(MUMaterial.LAPIS_LAZULI).asItem(),
                4, 9);
        createOreDrop(Blocks.NETHER_QUARTZ_ORE, MekUtItems.RAW_MU_MATERIALS.get(MUMaterial.QUARTZ).asItem(),1);

        createRedstoneOreDrops(Blocks.REDSTONE_ORE, MekUtItems.RAW_MU_MATERIALS.get(MUMaterial.REDSTONE).asItem());
        createRedstoneOreDrops(Blocks.DEEPSLATE_REDSTONE_ORE,
                MekUtItems.RAW_MU_MATERIALS.get(MUMaterial.REDSTONE).asItem());

        createOreDrop(MekanismBlocks.ORES.get(OreType.FLUORITE).stone().get(),
                MekUtItems.RAW_MU_MATERIALS.get(MUMaterial.FLUORITE).asItem(), 2, 4);
        createOreDrop(MekanismBlocks.ORES.get(OreType.FLUORITE).deepslate().get(),
                MekUtItems.RAW_MU_MATERIALS.get(MUMaterial.FLUORITE).asItem(), 2, 4);

        createOreDrop(AEBlocks.QUARTZ_CLUSTER.block(),
                MekUtItems.RAW_MU_MATERIALS.get(MUMaterial.CERTUS_QUARTZ).asItem(), 4);
        createOreDrop(EAESingletons.ENTRO_CLUSTER,
                MekUtItems.RAW_MU_MATERIALS.get(MUMaterial.ENTRO).asItem(), 1);
    }

    protected void createOreDrop(Block block, Item item, int amount) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        add(block, this.createSilkTouchDispatchTable(block,
                this.applyExplosionDecay(block, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(ConstantValue.exactly(amount)))
                        .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE))))));
    }

    protected void createOreDrop(Block block, Item item, int min, int max) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        add(block, this.createSilkTouchDispatchTable(block,
                this.applyExplosionDecay(block, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)))
                        .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE))))));
    }

    protected void createRedstoneOreDrops(Block block, Item item) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        add(block, this.createSilkTouchDispatchTable(block,
                this.applyExplosionDecay(block, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 5.0F)))
                        .apply(ApplyBonusCount
                                .addUniformBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE))))));
    }

}
