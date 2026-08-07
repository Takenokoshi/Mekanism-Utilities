package com.takenokoshi.mekut.blockentity.misc;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekaddonlib.inventory.slot.LimitChangedInputInventorySlot;
import com.takenokoshi.mekaddonlib.inventory.slot.LimitChangedOutputInventorySlot;
import com.takenokoshi.mekut.blockentity.interfaces.IRatioSplitter;
import com.takenokoshi.mekut.enums.MekUtDataType;

import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.IContentsListener;
import mekanism.api.RelativeSide;
import mekanism.api.inventory.IInventorySlot;
import mekanism.common.attachments.component.AttachedSideConfig;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.attachments.containers.item.ItemSlotsBuilder;
import mekanism.common.capabilities.holder.slot.IInventorySlotHolder;
import mekanism.common.capabilities.holder.slot.InventorySlotHelper;
import mekanism.common.inventory.container.MekanismContainer;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.registration.impl.ItemRegistryObject;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.tile.component.config.DataType;
import mekanism.common.tile.component.config.slot.InventorySlotInfo;
import mekanism.common.tile.prefab.TileEntityConfigurableMachine;
import mekanism.common.util.NBTUtils;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BEItemRatioSplitter extends TileEntityConfigurableMachine implements IRatioSplitter {

    public static final AttachedSideConfig SIDE_CONFIG = Util.make(() -> {
        Map<TransmissionType, AttachedSideConfig.LightConfigInfo> configInfo = new EnumMap<>(TransmissionType.class);
        configInfo.put(TransmissionType.ITEM, AttachedSideConfig.LightConfigInfo.TWO_OUTPUT);
        return new AttachedSideConfig(configInfo);
    });

    public static void addContainersToItem(ItemRegistryObject<?> value) {
        value.addAttachmentOnlyContainers(ContainerType.ITEM, () -> ItemSlotsBuilder.builder()
                .addBasic(3)
                .build());
    }

    private IInventorySlot inputSlot;
    private IInventorySlot outputSlot1;
    private IInventorySlot outputSlot2;

    private int ratio1;
    private int ratio2;

    public BEItemRatioSplitter(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state);
        var itemConfig = configComponent.getConfig(TransmissionType.ITEM);
        itemConfig.addSlotInfo(DataType.INPUT, new InventorySlotInfo(true, false, List.of(inputSlot)));
        itemConfig.addSlotInfo(DataType.OUTPUT_1, new InventorySlotInfo(false, true, List.of(outputSlot1)));
        itemConfig.addSlotInfo(DataType.OUTPUT_2, new InventorySlotInfo(false, true, List.of(outputSlot2)));
        itemConfig.addSlotInfo(MekUtDataType.INPUT_OUTPUT1.getValue(),
                new InventorySlotInfo(true, true, List.of(inputSlot, outputSlot1)));
        itemConfig.addSlotInfo(MekUtDataType.INPUT_OUTPUT2.getValue(),
                new InventorySlotInfo(true, true, List.of(inputSlot, outputSlot2)));
        itemConfig.setDataType(DataType.INPUT, RelativeSide.FRONT);
        itemConfig.setDataType(DataType.OUTPUT_1, RelativeSide.LEFT);
        itemConfig.setDataType(DataType.OUTPUT_2, RelativeSide.RIGHT);
        itemConfig.setCanEject(true);
        ejectorComponent = new TileComponentEjector(this).setOutputData(configComponent, TransmissionType.ITEM);
    }

    @Override
    protected @Nullable IInventorySlotHolder getInitialInventory(IContentsListener listener) {
        InventorySlotHelper builder = InventorySlotHelper.forSideWithConfig(this);
        builder.addSlot(inputSlot = LimitChangedInputInventorySlot.at(
                stack -> stack.getMaxStackSize() >= ratio1 + ratio2,
                stack -> stack.getMaxStackSize() >= ratio1 + ratio2,
                listener, 80, 17, 1_000_000_000));
        builder.addSlot(outputSlot1 = LimitChangedOutputInventorySlot.at(
                listener, 59, 35, 1_000_000_000));
        builder.addSlot(outputSlot2 = LimitChangedOutputInventorySlot.at(
                listener, 101, 35, 1_000_000_000));
        return builder.build();
    }

    @Override
    public void setRatio1(int v) {
        ratio1 = v;
    }

    @Override
    public void setRatio2(int v) {
        ratio2 = v;
    }

    @Override
    public int getRatio1() {
        return ratio1;
    }

    @Override
    public int getRatio2() {
        return ratio2;
    }

    @Override
    protected boolean onUpdateServer() {
        boolean sendUpdatePacket = super.onUpdateServer();
        int total = ratio1 + ratio2;
        if (total < 1 || inputSlot.getCount() < total) {
            setActive(false);
            return sendUpdatePacket;
        }
        ItemStack toSplitte = inputSlot.getStack().copyWithCount(1);
        if (!outputSlot1.insertItem(toSplitte, Action.SIMULATE, AutomationType.INTERNAL).isEmpty()
                || !outputSlot2.insertItem(toSplitte, Action.SIMULATE, AutomationType.INTERNAL).isEmpty()) {
            setActive(false);
            return sendUpdatePacket;
        }
        int splitteTimes = inputSlot.getCount() / total;
        if (ratio1 > 0) {
            splitteTimes = Math.min((outputSlot1.getLimit(toSplitte) - outputSlot1.getCount()) / ratio1, splitteTimes);
        }
        if (ratio2 > 0) {
            splitteTimes = Math.min((outputSlot2.getLimit(toSplitte) - outputSlot2.getCount()) / ratio2, splitteTimes);
        }
        if (splitteTimes < 1) {
            setActive(false);
            return sendUpdatePacket;
        }
        inputSlot.shrinkStack(splitteTimes * total, Action.EXECUTE);
        if (ratio1 > 0) {
            outputSlot1.insertItem(toSplitte.copyWithCount(splitteTimes * ratio1), Action.EXECUTE,
                    AutomationType.INTERNAL);
        }
        if (ratio2 > 0) {
            outputSlot2.insertItem(toSplitte.copyWithCount(splitteTimes * ratio2), Action.EXECUTE,
                    AutomationType.INTERNAL);
        }
        setActive(true);
        return sendUpdatePacket;
    }

    @Override
    public void addContainerTrackers(MekanismContainer container) {
        super.addContainerTrackers(container);
        trackRatio(container);
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag nbtTags, @NotNull Provider provider) {
        super.saveAdditional(nbtTags, provider);
        nbtTags.putInt("ratio1", ratio1);
        nbtTags.putInt("ratio2", ratio2);
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag nbt, @NotNull Provider provider) {
        super.loadAdditional(nbt, provider);
        NBTUtils.setIntIfPresent(nbt, "ratio1", this::setRatio1);
        NBTUtils.setIntIfPresent(nbt, "ratio2", this::setRatio2);
    }

}
