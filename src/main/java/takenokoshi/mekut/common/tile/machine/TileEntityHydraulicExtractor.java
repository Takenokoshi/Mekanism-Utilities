package takenokoshi.mekut.common.tile.machine;

import org.jetbrains.annotations.NotNull;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mekanism.common.recipe.IMekanismRecipeTypeProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import takenokoshi.mekal.api.recipes.ItemStackFluidToItemStackRecipe;
import takenokoshi.mekal.common.recipe.lookup.cache.MekALInputRecipeCache.ItemFluid;
import takenokoshi.mekal.common.tile.prefab.TileEntityItemStackFluidToItemStackMachine;
import takenokoshi.mekut.client.recipe_viewer.type.MekUtRVRecipeType;
import takenokoshi.mekut.common.recipe.MekUtRecipeType;

public class TileEntityHydraulicExtractor extends TileEntityItemStackFluidToItemStackMachine {

    public TileEntityHydraulicExtractor(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state, BASE_TICKS_REQUIRED);
    }

    @Override
    public @NotNull IMekanismRecipeTypeProvider<?, ItemStackFluidToItemStackRecipe, ItemFluid<ItemStackFluidToItemStackRecipe>> getRecipeType() {
        return MekUtRecipeType.HYDRAULIC_EXTRACTING;
    }

    @Override
    public IRecipeViewerRecipeType<ItemStackFluidToItemStackRecipe> recipeViewerType() {
        return MekUtRVRecipeType.HYDRAULIC_EXTRACTING;
    }

}
