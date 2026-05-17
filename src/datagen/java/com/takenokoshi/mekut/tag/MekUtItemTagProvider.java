package com.takenokoshi.mekut.tag;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.enums.MUMaterial;
import com.takenokoshi.mekut.registries.MekUtBlocks;
import com.takenokoshi.mekut.registries.MekUtItems;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class MekUtItemTagProvider extends ItemTagsProvider {

    public MekUtItemTagProvider(PackOutput output,
            CompletableFuture<Provider> lookupProvider,
            CompletableFuture<TagLookup<Item>> parentProvider,
            CompletableFuture<TagLookup<Block>> blockTags,
            @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, parentProvider, blockTags, MekUtConstants.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(Provider provider) {
        for (MUMaterial material : MUMaterial.values()) {
            tag(MekUtItemTags.RAW_MU_MATERIALS_BLOCK.get(material))
                    .add(MekUtBlocks.RAW_MU_MATERIALS_BLOCK.get(material).asItem());
            tag(MekUtItemTags.RAW_MU_MATERIALS.get(material)).add(MekUtItems.RAW_MU_MATERIALS.get(material).asItem());
            tag(MekUtItemTags.MU_MATERIALS_CLUMP.get(material))
                    .add(MekUtItems.MU_MATERIALS_CLUMP.get(material).asItem());
            tag(MekUtItemTags.MU_MATERIALS_CRYSTAL.get(material))
                    .add(MekUtItems.MU_MATERIALS_CRYSTAL.get(material).asItem());
            tag(MekUtItemTags.MU_MATERIALS_SHARD.get(material))
                    .add(MekUtItems.MU_MATERIALS_SHARD.get(material).asItem());
            if (!material.isGem) {
                tag(MekUtItemTags.MU_MATERIALS_DIRTY_DUST.get(material))
                        .add(MekUtItems.MU_MATERIALS_DIRTY_DUST.get(material).asItem());
            }
        }
        tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ores/amethyst")))
                .add(MekUtBlocks.AMETHYST_ORE.asItem());
        tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ores/certus_quartz")))
                .add(MekUtBlocks.CERTUS_QUARTZ_ORE.asItem());
        tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ores/netherite")))
                .add(MekUtBlocks.NETHERITE_ORE.asItem());

        tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "dusts/amethyst")))
                .add(MekUtItems.AMETHYST_DUST.asItem());

        tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ores/lapis_lazuli")))
                .addTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ores/lapis")));
    }

}
