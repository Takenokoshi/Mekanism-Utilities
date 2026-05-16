package com.takenokoshi.mekut.registration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import mekanism.api.text.ILangEntry;
import mekanism.common.block.prefab.BlockTile.BlockTileModel;
import mekanism.common.content.blocktype.BlockTypeTile;
import mekanism.common.content.blocktype.BlockTypeTile.BlockTileBuilder;
import mekanism.common.inventory.container.tile.MekanismTileContainer;
import mekanism.common.registration.impl.BlockDeferredRegister;
import mekanism.common.registration.impl.ContainerTypeDeferredRegister;
import mekanism.common.registration.impl.TileEntityTypeDeferredRegister;
import mekanism.common.tile.base.TileEntityMekanism;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;

public class MachineDeferredRegister {

    private final String modId;
    public final BlockDeferredRegister blockRegister;
    private final TileEntityTypeDeferredRegister tileRegister;
    private final ContainerTypeDeferredRegister containerRegister;
    private final List<MachineRegistryObject<?, ?, ?, ?>> machines;

    public MachineDeferredRegister(String modId) {
        this.modId = modId;
        this.blockRegister = new BlockDeferredRegister(this.modId);
        this.tileRegister = new TileEntityTypeDeferredRegister(this.modId);
        this.containerRegister = new ContainerTypeDeferredRegister(this.modId);
        this.machines = new ArrayList<>();
    }

    public void register(IEventBus modEventBus) {
        blockRegister.register(modEventBus);
        tileRegister.register(modEventBus);
        containerRegister.register(modEventBus);
    }

    public List<MachineRegistryObject<?, ?, ?, ?>> getMachines() {
        return Collections.unmodifiableList(machines);
    }

    public <BE extends TileEntityMekanism, BLOCK extends BlockTileModel<BE, BlockTypeTile<BE>>, CONTAINER extends MekanismTileContainer<BE>, ITEM extends BlockItem> MachineRegistryObject<BE, BLOCK, CONTAINER, ITEM> registerFull(
            String name, Function<BlockTypeTile<BE>, BLOCK> blockCreator,
            BiFunction<BLOCK, Item.Properties, ITEM> itemCreator,
            BlockEntityConstructor<BE, BlockTypeTile<BE>, BLOCK> beConstructor, Class<BE> beClass,
            ContainerConstructor<BE, CONTAINER> contConstructor, ILangEntry entry,
            UnaryOperator<BlockTileBuilder<BlockTypeTile<BE>, BE, ?>> operator) {
        MachineRegistryObject<BE, BLOCK, CONTAINER, ITEM> result = new MachineRegistryObject<>(name, blockRegister,
                tileRegister, containerRegister, blockCreator,
                itemCreator, beConstructor, beClass, contConstructor, entry, operator);
        machines.add(result);
        return result;
    }

    public <BE extends TileEntityMekanism> SimpleMachineRegistryObject<BE> registerSimple(String name,
            Function<BlockTypeTile<BE>, BlockTileModel<BE, BlockTypeTile<BE>>> blockCreator,
            BlockEntityConstructor<BE, BlockTypeTile<BE>, BlockTileModel<BE, BlockTypeTile<BE>>> beConstructor,
            Class<BE> beClass, ILangEntry entry,
            UnaryOperator<BlockTileBuilder<BlockTypeTile<BE>, BE, ?>> operator) {
        SimpleMachineRegistryObject<BE> result = new SimpleMachineRegistryObject<>(name, blockRegister, tileRegister,
                containerRegister, blockCreator,
                beConstructor, beClass, entry, operator);
        machines.add(result);
        return result;
    }
}
