package com.takenokoshi.mekut.loottable;

import com.takenokoshi.mekut.registries.MekUtBlocks;
import com.takenokoshi.mekut.registries.MekUtMachines;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
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
