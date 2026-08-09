package com.takenokoshi.mekut.tag;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.enums.MekUtMaterial;
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

        MekUtMaterial.MATERIALS.forEach(material -> {
            tag(material.rawTag()).add(material.raw().asItem());
            tag(material.rawBlockTag()).add(material.rawBlock().asItem());
            tag(material.crystalTag()).add(material.crystal().asItem());
            tag(material.shardTag()).add(material.shard().asItem());
            tag(material.clumpTag()).add(material.clump().asItem());
        });

        tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ores/amethyst")))
                .add(MekUtBlocks.AMETHYST_ORE.asItem());
        tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ores/certus_quartz")))
                .add(MekUtBlocks.CERTUS_QUARTZ_ORE.asItem());
        tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ores/entro")))
                .add(MekUtBlocks.ENTRO_ORE.asItem());
        tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ores/netherite")))
                .add(MekUtBlocks.NETHERITE_ORE.asItem());

        tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "dusts/amethyst")))
                .add(MekUtItems.AMETHYST_DUST.asItem());
        tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ingots/refined_amethyst")))
                .add(MekUtItems.REFINED_AMETHYST_INGOT.asItem());

        tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ores/lapis_lazuli")))
                .addTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ores/lapis")));

        tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "dyes/dark_red")))
                .add(MekUtItems.DARK_RED_DYE.get());
        tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "dyes/aqua")))
                .add(MekUtItems.AQUA_DYE.get());
    }

}
