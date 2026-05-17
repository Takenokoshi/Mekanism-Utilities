package com.takenokoshi.mekut.blockentity.base;

import java.util.List;
import java.util.function.ToIntFunction;

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
            List<RecipeError> errorTypes, int baseTicksRequired, ToIntFunction<RECIPE> recipeTicksGetter,
            int baselineMaxOperations) {
        super(blockProvider, pos, state, errorTypes, baseTicksRequired, recipeTicksGetter, baselineMaxOperations);
    }

    public @NotNull List<Component> getInfo(@NotNull Upgrade upgrade) {
        return UpgradeUtils.getExpScaledInfo(this, upgrade);
    }

    protected void recaluculateProcessingSpeed() {
        int speedFactor = 1 << upgradeComponent.getUpgrades(Upgrade.SPEED)
                + 2 << MekUtUpgradeUtils.getEmpoweredSpeed(upgradeComponent);
        if (speedFactor > recipeTicksRequired) {
            operationsPerTick = MathUtils.clampToInt(speedFactor / recipeTicksRequired);
            ticksRequired = 1;
        } else {
            operationsPerTick = 1;
            ticksRequired = MathUtils.clampToInt(recipeTicksRequired / speedFactor);
        }
    }

}
