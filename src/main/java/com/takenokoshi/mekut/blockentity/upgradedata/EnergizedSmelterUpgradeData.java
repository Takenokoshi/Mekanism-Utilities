package com.takenokoshi.mekut.blockentity.upgradedata;

import java.util.List;

import mekanism.api.chemical.IChemicalTank;
import mekanism.api.energy.IEnergyContainer;
import mekanism.api.inventory.IInventorySlot;
import mekanism.common.inventory.slot.EnergyInventorySlot;
import mekanism.common.tile.component.ITileComponent;
import mekanism.common.tile.interfaces.IRedstoneControl.RedstoneControl;
import mekanism.common.upgrade.IUpgradeData;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;

public class EnergizedSmelterUpgradeData implements IUpgradeData {

    public final boolean redstone;
    public final RedstoneControl controlType;
    public final IEnergyContainer energyContainer;
    public final int[] progress;
    public final boolean sorting;
    public final EnergyInventorySlot energySlot;
    public final List<IInventorySlot> inputSlots;
    public final List<IInventorySlot> outputSlots;
    public final IChemicalTank chemicalTank;
    public final CompoundTag components;

    public EnergizedSmelterUpgradeData(HolderLookup.Provider provider, boolean redstone, RedstoneControl controlType,
            IEnergyContainer energyContainer, int[] progress, EnergyInventorySlot energySlot,
            List<IInventorySlot> inputSlots, List<IInventorySlot> outputSlots, IChemicalTank chemicalTank,
            boolean sorting, List<ITileComponent> components) {
        this.redstone = redstone;
        this.controlType = controlType;
        this.energyContainer = energyContainer;
        this.chemicalTank = chemicalTank;
        this.progress = progress;
        this.energySlot = energySlot;
        this.inputSlots = inputSlots;
        this.outputSlots = outputSlots;
        this.sorting = sorting;
        this.components = new CompoundTag();
        for (ITileComponent component : components) {
            component.write(this.components, provider);
        }
    }
}
