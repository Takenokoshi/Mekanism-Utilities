package com.takenokoshi.mekut.registration;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import mekanism.api.text.ILangEntry;
import mekanism.common.block.prefab.BlockTile.BlockTileModel;
import mekanism.common.content.blocktype.BlockTypeTile;
import mekanism.common.content.blocktype.Machine;
import mekanism.common.content.blocktype.Machine.MachineBuilder;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.registration.impl.BlockDeferredRegister;
import mekanism.common.registration.impl.BlockRegistryObject;
import mekanism.common.registration.impl.ContainerTypeDeferredRegister;
import mekanism.common.registration.impl.ContainerTypeRegistryObject;
import mekanism.common.registration.impl.TileEntityTypeDeferredRegister;
import mekanism.common.registration.impl.TileEntityTypeRegistryObject;
import mekanism.common.tile.base.TileEntityMekanism;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

public class MachineRegistryObject<BE extends TileEntityMekanism, BLOCK extends BlockTileModel<BE, Machine<BE>>, CONTAINER extends MekanismTileContainer<BE>, ITEM extends BlockItem> {

    private final BlockRegistryObject<BLOCK, ITEM> blockRegistryObject;
    private final TileEntityTypeRegistryObject<BE> tileRegistryObject;
    private final ContainerTypeRegistryObject<CONTAINER> containerRegistryObject;
    private final Machine<BE> blockType;

    public MachineRegistryObject(
            String name,
            BlockDeferredRegister blockRegister,
            TileEntityTypeDeferredRegister tileRegister,
            ContainerTypeDeferredRegister containerRegister,
            Function<Machine<BE>, BLOCK> blockCreator,
            BiFunction<BLOCK, Item.Properties, ITEM> itemCreator,
            BlockEntityConstructor<BE, Machine<BE>, BLOCK> beConstructor,
            Class<BE> beClass,
            ContainerConstructor<BE, CONTAINER> contConstructor,
            ILangEntry entry,
            UnaryOperator<MachineBuilder<Machine<BE>, BE, ?>> operator) {

        blockType = operator.apply(MachineBuilder.createMachine(this::getTile, entry))
                .withGui(this::getContainer)
                .build();
        blockRegistryObject = blockRegister.register(name, () -> blockCreator.apply(blockType), itemCreator);
        tileRegistryObject = tileRegister.mekBuilder(blockRegistryObject,
                (p, s) -> beConstructor.create(blockRegistryObject, p, s))
                .clientTicker(BE::tickClient)
                .serverTicker(BE::tickServer)
                .build();
        containerRegistryObject = containerRegister.register(name, beClass,
                (id, inv, be) -> contConstructor.create(getContainer(), id, inv, be));

    }

    public BlockRegistryObject<BLOCK, ITEM> getBlockObject() {
        return blockRegistryObject;
    }

    public TileEntityTypeRegistryObject<BE> getTile() {
        return tileRegistryObject;
    }

    public ContainerTypeRegistryObject<CONTAINER> getContainer() {
        return containerRegistryObject;
    }

    public BlockTypeTile<BE> getBlockType() {
        return blockType;
    }
}
