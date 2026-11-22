package takenokoshi.mekut.common.tile.machine;

import org.jetbrains.annotations.NotNull;
import mekanism.api.recipes.ItemStackToFluidRecipe;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mekanism.common.recipe.IMekanismRecipeTypeProvider;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.SingleItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import takenokoshi.mekal.common.tile.prefab.TileEntityItemStackToFluidMachine;
import takenokoshi.mekut.client.recipe_viewer.type.MekUtRVRecipeType;
import takenokoshi.mekut.common.recipe.MekUtRecipeType;

public class TileEntitySqueezer extends TileEntityItemStackToFluidMachine {

    public  TileEntitySqueezer(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state, BASE_TICKS_REQUIRED);
    }

    @Override
    public @NotNull IMekanismRecipeTypeProvider<?, ItemStackToFluidRecipe, SingleItem<ItemStackToFluidRecipe>> getRecipeType() {
        return MekUtRecipeType.SQUEEZING;
    }

    @Override
    public IRecipeViewerRecipeType<ItemStackToFluidRecipe> recipeViewerType() {
        return MekUtRVRecipeType.SQUEEZING;
    }
    
}
