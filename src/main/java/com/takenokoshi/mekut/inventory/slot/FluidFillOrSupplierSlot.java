package com.takenokoshi.mekut.inventory.slot;

import java.util.function.Consumer;
import java.util.function.Predicate;

import org.jetbrains.annotations.NotNull;
import com.takenokoshi.mekut.item.FluidSupplierItem;

import mekanism.api.Action;
import mekanism.api.AutomationType;
import mekanism.api.IContentsListener;
import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.api.functions.ConstantPredicates;
import mekanism.api.inventory.IInventorySlot;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.inventory.container.slot.ContainerSlotType;
import mekanism.common.inventory.slot.BasicInventorySlot;
import mekanism.common.inventory.slot.IFluidHandlerSlot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;

public class FluidFillOrSupplierSlot extends BasicInventorySlot
        implements IFluidHandlerSlot {

    public static Predicate<ItemStack> getPredicate(IExtendedFluidTank fluidTank) {
        return stack -> {
            if (stack.getItem() instanceof FluidSupplierItem supplierItem) {
                return fluidTank
                        .insert(supplierItem.getStack().copyWithAmount(1), Action.SIMULATE, AutomationType.INTERNAL)
                        .isEmpty();
            }
            IFluidHandlerItem fluidHandlerItem = (IFluidHandlerItem) Capabilities.FLUID.getCapability(stack);
            if (fluidHandlerItem != null) {
                int tank = 0;
                for (int tanks = fluidHandlerItem.getTanks(); tank < tanks; ++tank) {
                    FluidStack fluidInTank = fluidHandlerItem.getFluidInTank(tank);
                    if (!fluidInTank.isEmpty()
                            && fluidTank.insert(fluidInTank, Action.SIMULATE, AutomationType.INTERNAL)
                                    .getAmount() < fluidInTank.getAmount()) {
                        return true;
                    }
                }
            }
            return false;
        };
    }

    public static FluidFillOrSupplierSlot create(IExtendedFluidTank fluidTank,
            @NotNull IContentsListener recipeCacheListener, int x, int y) {
        return new FluidFillOrSupplierSlot(fluidTank,
                recipeCacheListener, x, y);
    }

    @NotNull
    protected final IExtendedFluidTank fluidTank;

    private Consumer<FluidStack> supplyingStackSetter = (stack) -> {
    };
    private boolean isSupplying = false;
    private boolean isDraining;
    private boolean isFilling;

    public FluidFillOrSupplierSlot(IExtendedFluidTank fluidTank,
            @NotNull IContentsListener recipeCacheListener, int x, int y) {
        super(ConstantPredicates.alwaysFalse(),
                getPredicate(fluidTank),
                getPredicate(fluidTank),
                recipeCacheListener, x, y);
        this.fluidTank = fluidTank;
        this.setSlotType(ContainerSlotType.EXTRA);
    }

    @Override
    public void onContentsChanged() {
        super.onContentsChanged();
        updateSupplyingStack();
    }

    private void updateSupplyingStack() {
        if (isEmpty()) {
            supplyingStackSetter.accept(FluidStack.EMPTY);
            isSupplying = false;
        } else if (getStack().getItem() instanceof FluidSupplierItem supplierItem) {
            supplyingStackSetter.accept(supplierItem.getStack());
            isSupplying = true;
        } else {
            supplyingStackSetter.accept(FluidStack.EMPTY);
            isSupplying = false;
        }

    }

    public void setSupplyingStackSetter(Consumer<FluidStack> value) {
        supplyingStackSetter = value;
    }

    public IExtendedFluidTank getFluidTank() {
        return this.fluidTank;
    }

    public boolean isDraining() {
        return this.isDraining;
    }

    public boolean isFilling() {
        return this.isFilling;
    }

    public void setDraining(boolean draining) {
        this.isDraining = draining;
    }

    public void setFilling(boolean filling) {
        this.isFilling = filling;
    }

    @Override
    public int getLimit(ItemStack stack) {
        return 1;
    }

    @Override
    public boolean fillTank() {
        return false;
    }

    @Override
    public void fillTank(IInventorySlot outputSlot) {
        if (!isSupplying) {
            IFluidHandlerSlot.super.fillTank(outputSlot);
        }
    }

}
