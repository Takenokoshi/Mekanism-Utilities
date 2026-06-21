package com.takenokoshi.mekut.blockentity.base;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.takenokoshi.mekut.core.MekUtUpgradeUtils;

import mekanism.api.Upgrade;
import mekanism.api.math.MathUtils;
import mekanism.api.recipes.cache.CachedRecipe.OperationTracker.RecipeError;
import mekanism.common.util.UpgradeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BEExpScaledRecipeMachine<RECIPE extends Recipe<?>> extends BlockEntityMekUtRecipeMachine<RECIPE> {

    public BEExpScaledRecipeMachine(Holder<Block> blockProvider, BlockPos pos, BlockState state,
            List<RecipeError> errorTypes, int baselineMaxOperations) {
        super(blockProvider, pos, state, errorTypes, baselineMaxOperations);
    }

    public @NotNull List<Component> getInfo(@NotNull Upgrade upgrade) {
        return UpgradeUtils.getExpScaledInfo(this, upgrade);
    }

    protected void recaluculateProcessingSpeed() {
        operationsPerTick = MathUtils.clampToInt(
                ((1l << upgradeComponent.getUpgrades(Upgrade.SPEED))
                        + (2l << MekUtUpgradeUtils.getEmpoweredSpeed(upgradeComponent)))
                        * baselineMaxOperations);
    }

    @Override
    public void recalculateUpgrades(Upgrade upgrade) {
        if (upgrade == Upgrade.SPEED || MekUtUpgradeUtils.isEmpoweredSpeed(upgrade)) {
            recaluculateProcessingSpeed();
        }
        super.recalculateUpgrades(upgrade);
    }

}
