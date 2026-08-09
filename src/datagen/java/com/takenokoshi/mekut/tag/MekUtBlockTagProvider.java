package com.takenokoshi.mekut.tag;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.registries.MekUtBlocks;
import com.takenokoshi.mekut.registries.MekUtMachines;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

public class MekUtBlockTagProvider extends BlockTagsProvider {

    public MekUtBlockTagProvider(PackOutput output,
            CompletableFuture<Provider> lookupProvider,
            @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MekUtConstants.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(MekUtMachines.MACHINES.blockRegister.getPrimaryEntries().stream().map(DeferredHolder::get)
                        .toArray(Block[]::new))
                .add(new Block[] { MekUtBlocks.AMETHYST_ORE.get(),
                        MekUtBlocks.CERTUS_QUARTZ_ORE.get(),
                        MekUtBlocks.NETHERITE_ORE.get(),
                        MekUtBlocks.RAW_AMETHYST_BLOCK.get(),
                        MekUtBlocks.RAW_CERTUS_QUARTZ_BLOCK.get(),
                        MekUtBlocks.RAW_COAL_BLOCK.get(),
                        MekUtBlocks.RAW_DIAMOND_BLOCK.get(),
                        MekUtBlocks.RAW_EMERALD_BLOCK.get(),
                        MekUtBlocks.RAW_ENTRO_BLOCK.get(),
                        MekUtBlocks.RAW_FLUORITE_BLOCK.get(),
                        MekUtBlocks.RAW_LAPIS_LAZULI_BLOCK.get(),
                        MekUtBlocks.RAW_NETHERITE_BLOCK.get(),
                        MekUtBlocks.RAW_OVERLOAD_BLOCK.get(),
                        MekUtBlocks.RAW_QUARTZ_BLOCK.get(),
                        MekUtBlocks.RAW_REDSTONE_BLOCK.get(),
                        MekUtBlocks.RAW_SILICON_BLOCK.get(),
                });
    }

}
