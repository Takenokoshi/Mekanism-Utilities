package com.takenokoshi.mekut.item.cell.bulk;

import java.util.function.Function;

import org.jetbrains.annotations.Nullable;

import appeng.api.stacks.AEFluidKey;
import appeng.api.stacks.AEKey;
import appeng.api.storage.cells.ICellHandler;
import appeng.api.storage.cells.ISaveProvider;
import me.ramidzkh.mekae2.ae2.MekanismKey;
import net.minecraft.world.item.ItemStack;

public class MUBulkCellHandler<AEKEY extends AEKey> implements ICellHandler {

    private final Class<AEKEY> clazz;
    private final Function<AEKey, AEKEY> castFunction;

    public MUBulkCellHandler(Class<AEKEY> clazz, Function<AEKey, AEKEY> castFunction) {
        this.clazz = clazz;
        this.castFunction = castFunction;
    }

    @Override
    public @Nullable MUBulkCellInventory<AEKEY> getCellInventory(ItemStack arg0, @Nullable ISaveProvider arg1) {
        return isCell(arg0) ? new MUBulkCellInventory<>(arg0, arg1, castFunction) : null;
    }

    @Override
    public boolean isCell(ItemStack stack) {
        return stack != null ? stack.getItem() instanceof MUBulkCellItem<?> cell && cell.checkClass(clazz) : false;
    }

    public static final MUBulkCellHandler<AEFluidKey> FLUID_HANDLER = new MUBulkCellHandler<>(
            AEFluidKey.class,
            key -> key instanceof AEFluidKey fluidKey ? fluidKey : null);

    public static final MUBulkCellHandler<MekanismKey> CHEMICAL_HANDLER = new MUBulkCellHandler<>(
            MekanismKey.class,
            key -> key instanceof MekanismKey mekanismKey ? mekanismKey : null);

}
