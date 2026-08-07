package com.takenokoshi.mekut.blockentity.misc;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekaddonlib.blockentity.component.EjectorComponentUtils;
import com.takenokoshi.mekut.blockentity.interfaces.IRatioSplitter;
import com.takenokoshi.mekut.enums.MekUtDataType;

import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.IContentsListener;
import mekanism.api.RelativeSide;
import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.common.attachments.component.AttachedSideConfig;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.attachments.containers.fluid.FluidTanksBuilder;
import mekanism.common.capabilities.fluid.BasicFluidTank;
import mekanism.common.capabilities.holder.fluid.FluidTankHelper;
import mekanism.common.capabilities.holder.fluid.IFluidTankHolder;
import mekanism.common.inventory.container.MekanismContainer;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.registration.impl.ItemRegistryObject;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.tile.component.config.DataType;
import mekanism.common.tile.component.config.slot.FluidSlotInfo;
import mekanism.common.tile.prefab.TileEntityConfigurableMachine;
import mekanism.common.util.NBTUtils;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.fluids.FluidStack;

public class BEFluidRatioSplitter extends TileEntityConfigurableMachine implements IRatioSplitter {

    public static final AttachedSideConfig SIDE_CONFIG = Util.make(() -> {
        Map<TransmissionType, AttachedSideConfig.LightConfigInfo> configInfo = new EnumMap<>(TransmissionType.class);
        configInfo.put(TransmissionType.FLUID, AttachedSideConfig.LightConfigInfo.TWO_OUTPUT);
        return new AttachedSideConfig(configInfo);
    });

    public static void addContainersToItem(ItemRegistryObject<?> value) {
        value.addAttachmentOnlyContainers(ContainerType.FLUID, () -> FluidTanksBuilder.builder()
                .addBasic(0x7fffffff)
                .addBasic(0x7fffffff)
                .addBasic(0x7fffffff)
                .build());
    }

    private IExtendedFluidTank inputTank;
    private IExtendedFluidTank outputTank1;
    private IExtendedFluidTank outputTank2;

    private int ratio1;
    private int ratio2;

    public BEFluidRatioSplitter(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state);
        var fluidConfig = configComponent.getConfig(TransmissionType.FLUID);
        fluidConfig.addSlotInfo(DataType.INPUT, new FluidSlotInfo(true, false, List.of(inputTank)));
        fluidConfig.addSlotInfo(DataType.OUTPUT_1, new FluidSlotInfo(false, true, List.of(outputTank1)));
        fluidConfig.addSlotInfo(DataType.OUTPUT_2, new FluidSlotInfo(false, true, List.of(outputTank2)));
        fluidConfig.addSlotInfo(MekUtDataType.INPUT_OUTPUT1.getValue(),
                new FluidSlotInfo(true, true, List.of(inputTank, outputTank1)));
        fluidConfig.addSlotInfo(MekUtDataType.INPUT_OUTPUT2.getValue(),
                new FluidSlotInfo(true, true, List.of(inputTank, outputTank2)));
        fluidConfig.setDataType(DataType.INPUT, RelativeSide.FRONT);
        fluidConfig.setDataType(DataType.OUTPUT_1, RelativeSide.LEFT);
        fluidConfig.setDataType(DataType.OUTPUT_2, RelativeSide.RIGHT);
        fluidConfig.setCanEject(true);
        ejectorComponent = new TileComponentEjector(this, () -> 0, () -> 0x7fffffff)
                .setOutputData(configComponent, TransmissionType.FLUID);
        EjectorComponentUtils.setCanFluidTankEject(ejectorComponent,
                (type, tank) -> type.canOutput() && tank != inputTank);
    }

    @Override
    protected @Nullable IFluidTankHolder getInitialFluidTanks(IContentsListener listener) {
        FluidTankHelper builder = FluidTankHelper.forSideWithConfig(this);
        builder.addTank(inputTank = BasicFluidTank.create(0x7fffffff, listener));
        builder.addTank(outputTank1 = BasicFluidTank.output(0x7fffffff, listener));
        builder.addTank(outputTank2 = BasicFluidTank.output(0x7fffffff, listener));
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
        if (total < 1 || inputTank.getFluidAmount() < total) {
            setActive(false);
            return sendUpdatePacket;
        }
        FluidStack toSplitte = inputTank.getFluid().copyWithAmount(1);
        if (!outputTank1.insert(toSplitte, Action.SIMULATE, AutomationType.INTERNAL).isEmpty()
                || !outputTank2.insert(toSplitte, Action.SIMULATE, AutomationType.INTERNAL).isEmpty()) {
            setActive(false);
            return sendUpdatePacket;
        }
        int splitteTimes = inputTank.getFluidAmount() / total;
        if (ratio1 > 0) {
            splitteTimes = Math.min(outputTank1.getNeeded() / ratio1, splitteTimes);
        }
        if (ratio2 > 0) {
            splitteTimes = Math.min(outputTank2.getNeeded() / ratio2, splitteTimes);
        }
        if (splitteTimes < 1) {
            setActive(false);
            return sendUpdatePacket;
        }
        inputTank.shrinkStack(splitteTimes * total, Action.EXECUTE);
        if (ratio1 > 0) {
            outputTank1.insert(toSplitte.copyWithAmount(ratio1 * splitteTimes), Action.EXECUTE,
                    AutomationType.INTERNAL);
        }
        if (ratio2 > 0) {
            outputTank2.insert(toSplitte.copyWithAmount(ratio2 * splitteTimes), Action.EXECUTE,
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

    public IExtendedFluidTank getInputTank() {
        return inputTank;
    }

    public IExtendedFluidTank getOutputTank1() {
        return outputTank1;
    }

    public IExtendedFluidTank getOutputTank2() {
        return outputTank2;
    }

}
