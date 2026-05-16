package com.takenokoshi.mekut.registration;

import java.util.function.Function;
import java.util.function.UnaryOperator;

import mekanism.api.text.ILangEntry;
import mekanism.common.block.prefab.BlockTile.BlockTileModel;
import mekanism.common.content.blocktype.BlockTypeTile;
import mekanism.common.content.blocktype.BlockTypeTile.BlockTileBuilder;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.item.block.ItemBlockMekanism;
import mekanism.common.registration.impl.BlockDeferredRegister;
import mekanism.common.registration.impl.ContainerTypeDeferredRegister;
import mekanism.common.registration.impl.TileEntityTypeDeferredRegister;
import mekanism.common.tile.base.TileEntityMekanism;

public class SimpleMachineRegistryObject<BE extends TileEntityMekanism> extends
        MachineRegistryObject<BE, BlockTileModel<BE, BlockTypeTile<BE>>, MekanismTileContainer<BE>, ItemBlockMekanism<BlockTileModel<BE, BlockTypeTile<BE>>>> {

    public SimpleMachineRegistryObject(String name, BlockDeferredRegister blockRegister,
            TileEntityTypeDeferredRegister tileRegister, ContainerTypeDeferredRegister containerRegister,
            Function<BlockTypeTile<BE>, BlockTileModel<BE, BlockTypeTile<BE>>> blockCreator,
            BlockEntityConstructor<BE, BlockTypeTile<BE>, BlockTileModel<BE, BlockTypeTile<BE>>> beConstructor,
            Class<BE> beClass, ILangEntry entry,
            UnaryOperator<BlockTileBuilder<BlockTypeTile<BE>, BE, ?>> operator) {
        super(name, blockRegister, tileRegister, containerRegister, blockCreator,
                ItemBlockMekanism::new, beConstructor, beClass,
                MekanismTileContainer::new, entry, operator);
    }

}
