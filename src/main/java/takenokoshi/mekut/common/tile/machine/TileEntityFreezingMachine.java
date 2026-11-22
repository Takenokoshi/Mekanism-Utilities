package takenokoshi.mekut.common.tile.machine;

import org.jetbrains.annotations.NotNull;

import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mekanism.common.recipe.IMekanismRecipeTypeProvider;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.SingleFluid;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import takenokoshi.mekal.api.recipes.FluidToItemStackRecipe;
import takenokoshi.mekal.common.tile.prefab.TileEntityFluidToItemStackMachine;
import takenokoshi.mekut.client.recipe_viewer.type.MekUtRVRecipeType;
import takenokoshi.mekut.common.recipe.MekUtRecipeType;

public class TileEntityFreezingMachine extends TileEntityFluidToItemStackMachine {

    public TileEntityFreezingMachine(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state, BASE_TICKS_REQUIRED);
    }

    @Override
    public @NotNull IMekanismRecipeTypeProvider<?, FluidToItemStackRecipe, SingleFluid<FluidToItemStackRecipe>> getRecipeType() {
        return MekUtRecipeType.FREEZING;
    }

    @Override
    public @NotNull IRecipeViewerRecipeType<FluidToItemStackRecipe> recipeViewerType() {
        return MekUtRVRecipeType.FREEZING;
    }

}
