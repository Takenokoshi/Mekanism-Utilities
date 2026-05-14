package com.takenokoshi.mekut.tag;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekut.core.MekUtConstants;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class MekUtBlockTagProvider extends BlockTagsProvider {

    public MekUtBlockTagProvider(PackOutput output,
            CompletableFuture<Provider> lookupProvider,
            @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MekUtConstants.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(Provider provider) {
    }

}
