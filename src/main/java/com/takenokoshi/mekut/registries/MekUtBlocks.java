package com.takenokoshi.mekut.registries;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

import com.takenokoshi.mekut.block.BlockSimpleDiscription;
import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.enums.MUMaterial;
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

    public static final Map<MUMaterial, BlockRegistryObject<?, ?>> RAW_MU_MATERIALS_BLOCK = registerMaterials(
            m -> "raw_" + m.name + "_block",
            m -> new Block(Block.Properties.of().mapColor(MapColor.COLOR_GRAY)));

    private static Map<MUMaterial, BlockRegistryObject<?, ?>> registerMaterials(
            Function<MUMaterial, String> nameBuilder,
            Function<MUMaterial, Block> blockCreator) {
        EnumMap<MUMaterial, BlockRegistryObject<?, ?>> result = new EnumMap<>(MUMaterial.class);
        for (MUMaterial material : MUMaterial.values()) {
            result.put(material, BLOCKS.register(nameBuilder.apply(material), () -> blockCreator.apply(material)));
        }
        return Collections.unmodifiableMap(result);
    }
}
