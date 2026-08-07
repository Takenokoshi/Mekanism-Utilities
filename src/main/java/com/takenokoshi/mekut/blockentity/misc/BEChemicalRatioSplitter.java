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
import mekanism.api.chemical.BasicChemicalTank;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.chemical.IChemicalTank;
import mekanism.common.attachments.component.AttachedSideConfig;
import mekanism.common.attachments.containers.ContainerType;
import mekanism.common.attachments.containers.chemical.ChemicalTanksBuilder;
import mekanism.common.capabilities.holder.chemical.ChemicalTankHelper;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import mekanism.common.inventory.container.MekanismContainer;
import mekanism.common.lib.transmitter.TransmissionType;
import mekanism.common.registration.impl.ItemRegistryObject;
import mekanism.common.tile.component.TileComponentEjector;
import mekanism.common.tile.component.config.DataType;
import mekanism.common.tile.component.config.slot.ChemicalSlotInfo;
import mekanism.common.tile.prefab.TileEntityConfigurableMachine;
import mekanism.common.util.NBTUtils;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BEChemicalRatioSplitter extends TileEntityConfigurableMachine implements IRatioSplitter {

    public static final AttachedSideConfig SIDE_CONFIG = Util.make(() -> {
        Map<TransmissionType, AttachedSideConfig.LightConfigInfo> configInfo = new EnumMap<>(TransmissionType.class);
        configInfo.put(TransmissionType.CHEMICAL, AttachedSideConfig.LightConfigInfo.TWO_OUTPUT);
        return new AttachedSideConfig(configInfo);
    });

    public static void addContainersToItem(ItemRegistryObject<?> value) {
        value.addAttachmentOnlyContainers(ContainerType.CHEMICAL, () -> ChemicalTanksBuilder.builder()
                .addBasic(Long.MAX_VALUE)
                .addBasic(Long.MAX_VALUE)
                .addBasic(Long.MAX_VALUE)
                .build());
    }

    private IChemicalTank inputTank;
    private IChemicalTank outputTank1;
    private IChemicalTank outputTank2;

    private int ratio1;
    private int ratio2;

    public BEChemicalRatioSplitter(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state);
        var fluidConfig = configComponent.getConfig(TransmissionType.CHEMICAL);
        fluidConfig.addSlotInfo(DataType.INPUT, new ChemicalSlotInfo(true, false, List.of(inputTank)));
        fluidConfig.addSlotInfo(DataType.OUTPUT_1, new ChemicalSlotInfo(false, true, List.of(outputTank1)));
        fluidConfig.addSlotInfo(DataType.OUTPUT_2, new ChemicalSlotInfo(false, true, List.of(outputTank2)));
        fluidConfig.addSlotInfo(MekUtDataType.INPUT_OUTPUT1.getValue(),
                new ChemicalSlotInfo(true, true, List.of(inputTank, outputTank1)));
        fluidConfig.addSlotInfo(MekUtDataType.INPUT_OUTPUT2.getValue(),
                new ChemicalSlotInfo(true, true, List.of(inputTank, outputTank2)));
        fluidConfig.setDataType(DataType.INPUT, RelativeSide.FRONT);
        fluidConfig.setDataType(DataType.OUTPUT_1, RelativeSide.LEFT);
        fluidConfig.setDataType(DataType.OUTPUT_2, RelativeSide.RIGHT);
        fluidConfig.setCanEject(true);
        ejectorComponent = new TileComponentEjector(this, () -> Long.MAX_VALUE)
                .setOutputData(configComponent, TransmissionType.CHEMICAL);
        EjectorComponentUtils.setCanChemicalTankEject(ejectorComponent,
                (type, tank) -> type.canOutput() && tank != inputTank);
    }

    @Override
    public @Nullable IChemicalTankHolder getInitialChemicalTanks(IContentsListener listener) {
        ChemicalTankHelper builder = ChemicalTankHelper.forSideWithConfig(this);
        builder.addTank(inputTank = BasicChemicalTank.createAllValid(Long.MAX_VALUE, listener));
        builder.addTank(outputTank1 = BasicChemicalTank.output(Long.MAX_VALUE, listener));
        builder.addTank(outputTank2 = BasicChemicalTank.output(Long.MAX_VALUE, listener));
        return builder.build();
    }

    @Override
    protected boolean onUpdateServer() {
        boolean sendUpdatePacket = super.onUpdateServer();
        long total = 0L + ratio1 + ratio2;
        if (total < 1L || inputTank.getStored() < total) {
            setActive(false);
            return sendUpdatePacket;
        }
        ChemicalStack toSplitte = inputTank.getStack().copyWithAmount(1L);
        if (!outputTank1.insert(toSplitte, Action.SIMULATE, AutomationType.INTERNAL).isEmpty()
                || !outputTank2.insert(toSplitte, Action.SIMULATE, AutomationType.INTERNAL).isEmpty()) {
            setActive(false);
            return sendUpdatePacket;
        }
        long splitteTimes = inputTank.getStored() / total;

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

    public IChemicalTank getInputTank() {
        return inputTank;
    }

    public IChemicalTank getOutputTank1() {
        return outputTank1;
    }

    public IChemicalTank getOutputTank2() {
        return outputTank2;
    }

}
