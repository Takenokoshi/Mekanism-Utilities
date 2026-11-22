package takenokoshi.mekal.common.upgrade;

import java.util.List;

import mekanism.api.energy.IEnergyContainer;
import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.api.inventory.IInventorySlot;
import mekanism.common.inventory.slot.EnergyInventorySlot;
import mekanism.common.inventory.slot.FluidInventorySlot;
import mekanism.common.inventory.slot.InputInventorySlot;
import mekanism.common.inventory.slot.OutputInventorySlot;
import mekanism.common.tile.component.ITileComponent;
import mekanism.common.tile.interfaces.IRedstoneControl.RedstoneControl;
import mekanism.common.upgrade.MachineUpgradeData;
import net.minecraft.core.HolderLookup;

public class ItemStackFluidToItemStackMachineUpgradeData extends MachineUpgradeData {

    public final IExtendedFluidTank stored;
    public final FluidInventorySlot fluidSlot;
    public final IInventorySlot bucketSlot;
    public final int[] usedSoFar;

    public ItemStackFluidToItemStackMachineUpgradeData(HolderLookup.Provider provider,
            boolean redstone,
            RedstoneControl controlType,
            IEnergyContainer energyContainer,
            int operatingTicks,
            int usedSoFar,
            IExtendedFluidTank stored,
            FluidInventorySlot fluidSlot,
            IInventorySlot bucketSlot,
            EnergyInventorySlot energySlot,
            InputInventorySlot inputSlot,
            OutputInventorySlot outputSlot,
            List<ITileComponent> components) {
        super(provider, redstone, controlType, energyContainer, operatingTicks, energySlot, inputSlot, outputSlot,
                components);
        this.stored = stored;
        this.fluidSlot = fluidSlot;
        this.bucketSlot = bucketSlot;
        this.usedSoFar = new int[] { usedSoFar, };
    }

    public ItemStackFluidToItemStackMachineUpgradeData(HolderLookup.Provider provider,
            boolean redstone,
            RedstoneControl controlType,
            IEnergyContainer energyContainer,
            int[] progress,
            int[] usedSoFar,
            IExtendedFluidTank stored,
            FluidInventorySlot fluidSlot,
            IInventorySlot bucketSlot,
            EnergyInventorySlot energySlot,
            List<IInventorySlot> inputSlots,
            List<IInventorySlot> outputSlots,
            boolean sorting,
            List<ITileComponent> components) {
        super(provider, redstone, controlType, energyContainer, progress, energySlot, inputSlots, outputSlots, sorting,
                components);
        this.stored = stored;
        this.fluidSlot = fluidSlot;
        this.bucketSlot = bucketSlot;
        this.usedSoFar = usedSoFar;
    }

}
