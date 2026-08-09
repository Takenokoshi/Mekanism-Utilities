package com.takenokoshi.mekut.loottable;

import com.takenokoshi.mekut.enums.MekUtMaterial;
import com.takenokoshi.mekut.registries.MekUtEvolvedMachines;
import com.takenokoshi.mekut.registries.MekUtExtrasMachines;
import com.takenokoshi.mekut.registries.MekUtMachines;

import net.minecraft.core.HolderLookup.Provider;

//BaseBlockLootTables is copy of https://github.com/mekanism/Mekanism/blob/1.21.x/src/datagen/main/java/mekanism/common/loot/table/BaseBlockLootTables.java
public class MekUtBlockLootProvider extends BaseBlockLootTables {

    protected MekUtBlockLootProvider(Provider registries) {
        super(registries);
    }

    @Override
    protected void generate() {

        dropSelfWithContents(MekUtMachines.MACHINES.blockRegister.getPrimaryEntries());
        dropSelfWithContents(MekUtEvolvedMachines.MACHINES.blockRegister.getPrimaryEntries());
        dropSelfWithContents(MekUtExtrasMachines.MACHINES.blockRegister.getPrimaryEntries());

        MekUtMaterial.MATERIALS.forEach(material -> {
            dropSelf(material.rawBlock().get());
        });
    }

}
