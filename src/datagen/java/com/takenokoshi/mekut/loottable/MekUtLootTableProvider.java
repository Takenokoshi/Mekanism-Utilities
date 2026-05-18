package com.takenokoshi.mekut.loottable;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public class MekUtLootTableProvider {
    public static LootTableProvider createBlockLoot(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> registries) {
        return new LootTableProvider(output, Set.of(), List
                .of(new LootTableProvider.SubProviderEntry(MekUtBlockLootProvider::new, LootContextParamSets.BLOCK)),
                registries);
    }
}
