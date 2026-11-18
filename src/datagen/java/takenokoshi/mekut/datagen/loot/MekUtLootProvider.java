package takenokoshi.mekut.datagen.loot;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import takenokoshi.mekut.datagen.loot.table.MekUtBlockLootTables;

public class MekUtLootProvider extends LootTableProvider {

    public MekUtLootProvider(PackOutput output, CompletableFuture<Provider> registries) {
        super(output, Collections.emptySet(), List.of(
                new SubProviderEntry[] {
                        new SubProviderEntry(MekUtBlockLootTables::new, LootContextParamSets.BLOCK),
                }), registries);
    }

}
