package takenokoshi.mekut.datagen.loot.table;

import net.minecraft.core.HolderLookup.Provider;
import takenokoshi.mekal.datagen.loot.table.BaseBlockLootSubProvider;
import takenokoshi.mekut.common.registries.MekUtMachines;

public class MekUtBlockLootTables extends BaseBlockLootSubProvider {

    public MekUtBlockLootTables(Provider registries) {
        super(registries);
    }

    @Override
    protected void generate() {
        dropSelfWithContents(MekUtMachines.MACHINES.getMachines());
    }
    
}
