package com.takenokoshi.mekut.registries;

import com.takenokoshi.mekut.block.BlockSimpleDiscription;
import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.lang.MekUtDescription;

import mekanism.common.item.block.ItemBlockTooltip;
import mekanism.common.registration.impl.BlockDeferredRegister;
import mekanism.common.registration.impl.BlockRegistryObject;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class MekUtBlocks {
    public static final BlockDeferredRegister BLOCKS = new BlockDeferredRegister(MekUtConstants.MODID);

    public static final BlockRegistryObject<?, ?> AMETHYST_ORE = BLOCKS
            .register("amethyst_ore",
                    () -> new BlockSimpleDiscription(
                            BlockBehaviour.Properties.of()
                                    .strength(1.5f, 18000000.0f)
                                    .sound(SoundType.AMETHYST)
                                    .mapColor(MapColor.STONE),
                            MekUtDescription.AMETHYST_ORE),
                    ItemBlockTooltip::new);
    public static final BlockRegistryObject<?, ?> CERTUS_QUARTZ_ORE = BLOCKS
            .register("certus_quartz_ore",
                    () -> new BlockSimpleDiscription(
                            BlockBehaviour.Properties.of()
                                    .strength(1.5f, 18000000.0f)
                                    .sound(SoundType.AMETHYST)
                                    .mapColor(MapColor.STONE),
                            MekUtDescription.CERTUS_QUARTZ_ORE),
                    ItemBlockTooltip::new);
    public static final BlockRegistryObject<?, ?> ENTRO_ORE = BLOCKS
            .register("entro_ore",
                    () -> new BlockSimpleDiscription(
                            BlockBehaviour.Properties.of()
                                    .strength(1.5f, 18000000.0f)
                                    .sound(SoundType.AMETHYST)
                                    .mapColor(MapColor.STONE),
                            MekUtDescription.ENTRO_ORE),
                    ItemBlockTooltip::new);
    public static final BlockRegistryObject<?, ?> NETHERITE_ORE = BLOCKS
            .register("netherite_ore",
                    () -> new BlockSimpleDiscription(
                            BlockBehaviour.Properties.of()
                                    .strength(1.5f, 18000000.0f)
                                    .sound(SoundType.ANCIENT_DEBRIS)
                                    .mapColor(MapColor.NETHER),
                            MekUtDescription.NETHERITE_ORE),
                    ItemBlockTooltip::new);

    public static final BlockRegistryObject<?, ?> RAW_AMETHYST_BLOCK = BLOCKS
            .register("raw_amethyst_block", () -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_GRAY)));

    public static final BlockRegistryObject<?, ?> RAW_CERTUS_QUARTZ_BLOCK = BLOCKS
            .register("raw_certus_quartz_block", () -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_GRAY)));

    public static final BlockRegistryObject<?, ?> RAW_COAL_BLOCK = BLOCKS
            .register("raw_coal_block", () -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_GRAY)));

    public static final BlockRegistryObject<?, ?> RAW_DIAMOND_BLOCK = BLOCKS
            .register("raw_diamond_block", () -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_GRAY)));

    public static final BlockRegistryObject<?, ?> RAW_EMERALD_BLOCK = BLOCKS
            .register("raw_emerald_block", () -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_GRAY)));

    public static final BlockRegistryObject<?, ?> RAW_ENTRO_BLOCK = BLOCKS
            .register("raw_entro_block", () -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_GRAY)));

    public static final BlockRegistryObject<?, ?> RAW_FLUORITE_BLOCK = BLOCKS
            .register("raw_fluorite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_GRAY)));

    public static final BlockRegistryObject<?, ?> RAW_LAPIS_LAZULI_BLOCK = BLOCKS
            .register("raw_lapis_lazuli_block", () -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_GRAY)));

    public static final BlockRegistryObject<?, ?> RAW_NETHERITE_BLOCK = BLOCKS
            .register("raw_netherite_block", () -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_GRAY)));

    public static final BlockRegistryObject<?, ?> RAW_OVERLOAD_BLOCK = BLOCKS
            .register("raw_overload_block", () -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_GRAY)));

    public static final BlockRegistryObject<?, ?> RAW_QUARTZ_BLOCK = BLOCKS
            .register("raw_quartz_block", () -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_GRAY)));

    public static final BlockRegistryObject<?, ?> RAW_REDSTONE_BLOCK = BLOCKS
            .register("raw_redstone_block", () -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_GRAY)));

    public static final BlockRegistryObject<?, ?> RAW_SILICON_BLOCK = BLOCKS
            .register("raw_silicon_block", () -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_GRAY)));
}
