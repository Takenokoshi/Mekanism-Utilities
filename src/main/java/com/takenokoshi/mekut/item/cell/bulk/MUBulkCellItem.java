package com.takenokoshi.mekut.item.cell.bulk;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.ParametersAreNonnullByDefault;

import org.jetbrains.annotations.NotNull;

import appeng.api.config.FuzzyMode;
import appeng.api.stacks.AEFluidKey;
import appeng.api.stacks.AEKey;
import appeng.api.stacks.AEKeyType;
import appeng.api.stacks.GenericStack;
import appeng.api.storage.cells.ICellWorkbenchItem;
import appeng.core.AEConfig;
import appeng.core.localization.Tooltips;
import appeng.items.AEBaseItem;
import appeng.items.contents.CellConfig;
import appeng.items.storage.StorageCellTooltipComponent;
import appeng.util.ConfigInventory;
import gripe._90.megacells.definition.MEGATranslations;
import me.ramidzkh.mekae2.ae2.MekanismKey;
import me.ramidzkh.mekae2.ae2.MekanismKeyType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class MUBulkCellItem<AEKEY extends AEKey> extends AEBaseItem implements ICellWorkbenchItem {

    private final Set<AEKeyType> suportedKeys;
    private final Class<AEKEY> clazz;
    private final MUBulkCellHandler<AEKEY> handler;

    public MUBulkCellItem(Properties properties, Set<AEKeyType> suportedKeys, Class<AEKEY> clazz,
            MUBulkCellHandler<AEKEY> handler) {
        super(properties.stacksTo(1));
        this.suportedKeys = suportedKeys;
        this.clazz = clazz;
        this.handler = handler;
    }

    public static final MUBulkCellItem<AEFluidKey> fluid(Properties properties) {
        return new MUBulkCellItem<>(properties, Set.of(AEKeyType.fluids()), AEFluidKey.class,
                MUBulkCellHandler.FLUID_HANDLER);
    }

    public static final MUBulkCellItem<MekanismKey> chemical(Properties properties) {
        return new MUBulkCellItem<>(properties, Set.of(MekanismKeyType.TYPE), MekanismKey.class,
                MUBulkCellHandler.CHEMICAL_HANDLER);
    }

    public boolean checkClass(Class<?> clazz) {
        return this.clazz.equals(clazz);
    }

    @Override
    public ConfigInventory getConfigInventory(ItemStack is) {
        return CellConfig.create(suportedKeys, is, 1);
    }

    @Override
    public FuzzyMode getFuzzyMode(ItemStack itemStack) {
        return null;
    }

    @Override
    public void setFuzzyMode(ItemStack itemStack, FuzzyMode fuzzyMode) {
    }

    @ParametersAreNonnullByDefault
    @Override
    public void appendHoverText(ItemStack is, TooltipContext context, List<Component> lines, TooltipFlag flag) {
        MUBulkCellInventory<AEKEY> inv = handler.getCellInventory(is, null);
        if (inv != null) {
            AEKEY storedKey = inv.getStoredKey();
            AEKEY filterKey = inv.getFilterKey();
            if (storedKey != null) {
                lines.add(Tooltips.of(MEGATranslations.Contains.text(storedKey.getDisplayName())));
                if (inv.isAlot()) {
                    lines.add(MEGATranslations.ALot.text().withStyle(Tooltips.NUMBER_TEXT));
                } else {
                    lines.add(Tooltips.ofNumber(inv.getStoredQuantity()));
                }
            } else {
                lines.add(Tooltips.of(MEGATranslations.Empty.text()));
            }
            if (filterKey != null) {
                if (storedKey == null) {
                    lines.add(Tooltips.of(MEGATranslations.PartitionedFor.text(filterKey.getDisplayName())));
                } else if (!storedKey.equals(filterKey)) {
                    lines.add(MEGATranslations.MismatchedFilter.text(filterKey.getDisplayName())
                            .withStyle(ChatFormatting.DARK_RED));
                }
            } else {
                lines.add(
                        storedKey != null
                                ? MEGATranslations.MismatchedFilter.text(MEGATranslations.Empty.text())
                                        .withStyle(ChatFormatting.DARK_RED)
                                : Tooltips.of(MEGATranslations.NotPartitioned.text()));
            }
        }
    }

    @NotNull
    @Override
    public Optional<TooltipComponent> getTooltipImage(@NotNull ItemStack is) {
        MUBulkCellInventory<AEKEY> inv = handler.getCellInventory(is, null);
        if (inv == null || !AEConfig.instance().isTooltipShowCellContent()) {
            return Optional.empty();
        }
        List<GenericStack> content = new ArrayList<>();
        if (inv.getStoredKey() != null) {
            content.add(new GenericStack(inv.getStoredKey(), inv.getStoredQuantity()));
        } else if (inv.getFilterKey() != null) {
            content.add(new GenericStack(inv.getFilterKey(), 0));
        }
        return Optional.of(new StorageCellTooltipComponent(List.of(), content, false, true));
    }

}
