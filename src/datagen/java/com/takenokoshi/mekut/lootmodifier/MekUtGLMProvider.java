package com.takenokoshi.mekut.lootmodifier;

import java.util.concurrent.CompletableFuture;

import com.glodblock.github.extendedae.ExtendedAE;
import com.glodblock.github.extendedae.common.EAESingletons;
import com.moakiee.ae2lt.AE2LightningTech;
import com.moakiee.ae2lt.registry.ModItems;
import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.registries.MekUtItems;

import appeng.core.AppEng;
import appeng.core.definitions.AEItems;
import fr.iglee42.evolvedmekanism.EvolvedMekanism;
import mekanism.common.Mekanism;
import mekanism.common.registries.MekanismItems;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

public class MekUtGLMProvider extends GlobalLootModifierProvider {

    public MekUtGLMProvider(PackOutput output, CompletableFuture<Provider> registries) {
        super(output, registries, MekUtConstants.MODID);
    }

    @Override
    protected void start() {
        add("replace_to_raw/amethyst/cluster",
                new ItemReplaceLootModifier(
                        new LootItemCondition[] {
                                LootTableIdCondition
                                        .builder(ResourceLocation.withDefaultNamespace("blocks/amethyst_cluster"))
                                        .build()
                        },
                        Items.AMETHYST_SHARD.builtInRegistryHolder(),
                        MekUtItems.RAW_AMETHYST),
                new ICondition[] {});
        add("replace_to_raw/coal/ore",
                new ItemReplaceLootModifier(
                        new LootItemCondition[] {
                                LootTableIdCondition
                                        .builder(ResourceLocation.withDefaultNamespace("blocks/coal_ore"))
                                        .build()
                        },
                        Items.COAL.builtInRegistryHolder(),
                        MekUtItems.RAW_COAL),
                new ICondition[] {});
        add("replace_to_raw/coal/deepslate_ore",
                new ItemReplaceLootModifier(
                        new LootItemCondition[] {
                                LootTableIdCondition
                                        .builder(ResourceLocation.withDefaultNamespace("blocks/deepslate_coal_ore"))
                                        .build()
                        },
                        Items.COAL.builtInRegistryHolder(),
                        MekUtItems.RAW_COAL),
                new ICondition[] {});
        add("replace_to_raw/certus_quartz/cluster",
                new ItemReplaceLootModifier(
                        new LootItemCondition[] {
                                LootTableIdCondition
                                        .builder(AppEng.makeId("blocks/quartz_cluster"))
                                        .build()
                        },
                        AEItems.CERTUS_QUARTZ_CRYSTAL.holder(),
                        MekUtItems.RAW_CERTUS_QUARTZ),
                new ICondition[] {
                        new ModLoadedCondition(AppEng.MOD_ID),
                });
        add("replace_to_raw/diamond/ore",
                new ItemReplaceLootModifier(
                        new LootItemCondition[] {
                                LootTableIdCondition
                                        .builder(ResourceLocation.withDefaultNamespace("blocks/diamond_ore"))
                                        .build()
                        },
                        Items.DIAMOND.builtInRegistryHolder(),
                        MekUtItems.RAW_DIAMOND),
                new ICondition[] {});
        add("replace_to_raw/diamond/deepslate_ore",
                new ItemReplaceLootModifier(
                        new LootItemCondition[] {
                                LootTableIdCondition
                                        .builder(ResourceLocation.withDefaultNamespace("blocks/deepslate_diamond_ore"))
                                        .build()
                        },
                        Items.DIAMOND.builtInRegistryHolder(),
                        MekUtItems.RAW_DIAMOND),
                new ICondition[] {});
        add("replace_to_raw/emerald/ore",
                new ItemReplaceLootModifier(
                        new LootItemCondition[] {
                                LootTableIdCondition
                                        .builder(ResourceLocation.withDefaultNamespace("blocks/emerald_ore"))
                                        .build()
                        },
                        Items.EMERALD.builtInRegistryHolder(),
                        MekUtItems.RAW_EMERALD),
                new ICondition[] {});
        add("replace_to_raw/emerald/deepslate_ore",
                new ItemReplaceLootModifier(
                        new LootItemCondition[] {
                                LootTableIdCondition
                                        .builder(ResourceLocation.withDefaultNamespace("blocks/deepslate_emerald_ore"))
                                        .build()
                        },
                        Items.EMERALD.builtInRegistryHolder(),
                        MekUtItems.RAW_EMERALD),
                new ICondition[] {});
        add("replace_to_raw/entro/cluster",
                new ItemReplaceLootModifier(
                        new LootItemCondition[] {
                                LootTableIdCondition
                                        .builder(ExtendedAE.id("blocks/entro_cluster"))
                                        .build()
                        },
                        EAESingletons.ENTRO_CRYSTAL.builtInRegistryHolder(),
                        MekUtItems.RAW_ENTRO),
                new ICondition[] {
                        new ModLoadedCondition(ExtendedAE.MODID),
                });
        add("replace_to_raw/fluorite/ore",
                new ItemReplaceLootModifier(
                        new LootItemCondition[] {
                                LootTableIdCondition
                                        .builder(Mekanism.rl("blocks/fluorite_ore"))
                                        .build()
                        },
                        MekanismItems.FLUORITE_GEM,
                        MekUtItems.RAW_FLUORITE),
                new ICondition[] {});
        add("replace_to_raw/fluorite/deepslate_ore",
                new ItemReplaceLootModifier(
                        new LootItemCondition[] {
                                LootTableIdCondition
                                        .builder(Mekanism.rl("blocks/deepslate_fluorite_ore"))
                                        .build()
                        },
                        MekanismItems.FLUORITE_GEM,
                        MekUtItems.RAW_FLUORITE),
                new ICondition[] {});
        add("replace_to_raw/fluorite/depthrock_ore",
                new ItemReplaceLootModifier(
                        new LootItemCondition[] {
                                LootTableIdCondition
                                        .builder(EvolvedMekanism.rl("blocks/depthrock_fluorite_ore"))
                                        .build()
                        },
                        MekanismItems.FLUORITE_GEM,
                        MekUtItems.RAW_FLUORITE),
                new ICondition[] {
                        new ModLoadedCondition(EvolvedMekanism.MODID),
                });
        add("replace_to_raw/fluorite/endstone_ore",
                new ItemReplaceLootModifier(
                        new LootItemCondition[] {
                                LootTableIdCondition
                                        .builder(EvolvedMekanism.rl("blocks/endstone_fluorite_ore"))
                                        .build()
                        },
                        MekanismItems.FLUORITE_GEM,
                        MekUtItems.RAW_FLUORITE),
                new ICondition[] {
                        new ModLoadedCondition(EvolvedMekanism.MODID),
                });
        add("replace_to_raw/fluorite/holystone_ore",
                new ItemReplaceLootModifier(
                        new LootItemCondition[] {
                                LootTableIdCondition
                                        .builder(EvolvedMekanism.rl("blocks/holystone_fluorite_ore"))
                                        .build()
                        },
                        MekanismItems.FLUORITE_GEM,
                        MekUtItems.RAW_FLUORITE),
                new ICondition[] {
                        new ModLoadedCondition(EvolvedMekanism.MODID),
                });
        add("replace_to_raw/fluorite/netherrack_ore",
                new ItemReplaceLootModifier(
                        new LootItemCondition[] {
                                LootTableIdCondition
                                        .builder(EvolvedMekanism.rl("blocks/netherrack_fluorite_ore"))
                                        .build()
                        },
                        MekanismItems.FLUORITE_GEM,
                        MekUtItems.RAW_FLUORITE),
                new ICondition[] {
                        new ModLoadedCondition(EvolvedMekanism.MODID),
                });
        add("replace_to_raw/fluorite/shiverstone_ore",
                new ItemReplaceLootModifier(
                        new LootItemCondition[] {
                                LootTableIdCondition
                                        .builder(EvolvedMekanism.rl("blocks/shiverstone_fluorite_ore"))
                                        .build()
                        },
                        MekanismItems.FLUORITE_GEM,
                        MekUtItems.RAW_FLUORITE),
                new ICondition[] {
                        new ModLoadedCondition(EvolvedMekanism.MODID),
                });
        add("replace_to_raw/lapis_lazuli/ore",
                new ItemReplaceLootModifier(
                        new LootItemCondition[] {
                                LootTableIdCondition
                                        .builder(ResourceLocation.withDefaultNamespace("blocks/lapis_ore")).build()
                        },
                        Items.LAPIS_LAZULI.builtInRegistryHolder(),
                        MekUtItems.RAW_LAPIS_LAZULI),
                new ICondition[] {});
        add("replace_to_raw/lapis_lazuli/deepslate_ore",
                new ItemReplaceLootModifier(
                        new LootItemCondition[] {
                                LootTableIdCondition
                                        .builder(ResourceLocation.withDefaultNamespace("blocks/deepslate_lapis_ore"))
                                        .build()
                        },
                        Items.LAPIS_LAZULI.builtInRegistryHolder(),
                        MekUtItems.RAW_LAPIS_LAZULI),
                new ICondition[] {});
        add("replace_to_raw/quartz/nether_ore",
                new ItemReplaceLootModifier(
                        new LootItemCondition[] {
                                LootTableIdCondition
                                        .builder(ResourceLocation.withDefaultNamespace("blocks/nether_quartz_ore"))
                                        .build()
                        },
                        Items.QUARTZ.builtInRegistryHolder(),
                        MekUtItems.RAW_QUATRZ),
                new ICondition[] {});
        add("replace_to_raw/overload/cluster",
                new ItemReplaceLootModifier(
                        new LootItemCondition[] {
                                LootTableIdCondition
                                        .builder(ResourceLocation.fromNamespaceAndPath(AE2LightningTech.MODID,
                                                "blocks/overload_crystal_cluster"))
                                        .build()
                        },
                        ModItems.OVERLOAD_CRYSTAL,
                        MekUtItems.RAW_OVERLOAD),
                new ICondition[] {
                        new ModLoadedCondition(AE2LightningTech.MODID),
                });
        add("replace_to_raw/redstone/ore",
                new ItemReplaceLootModifier(
                        new LootItemCondition[] {
                                LootTableIdCondition
                                        .builder(ResourceLocation.withDefaultNamespace("blocks/redstone_ore")).build()
                        },
                        Items.REDSTONE.builtInRegistryHolder(),
                        MekUtItems.RAW_REDSTONE),
                new ICondition[] {});
        add("replace_to_raw/redstone/deepslate_ore",
                new ItemReplaceLootModifier(
                        new LootItemCondition[] {
                                LootTableIdCondition
                                        .builder(ResourceLocation.withDefaultNamespace("blocks/deepslate_redstone_ore"))
                                        .build()
                        },
                        Items.REDSTONE.builtInRegistryHolder(),
                        MekUtItems.RAW_REDSTONE),
                new ICondition[] {});
    }

}
