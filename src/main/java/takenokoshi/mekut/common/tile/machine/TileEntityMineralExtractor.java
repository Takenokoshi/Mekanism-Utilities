package takenokoshi.mekut.common.tile.machine;

import org.jetbrains.annotations.NotNull;

import mekanism.api.recipes.ItemStackToItemStackRecipe;
import mekanism.common.recipe.IMekanismRecipeTypeProvider;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.SingleItem;
import mekanism.common.tile.prefab.TileEntityElectricMachine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import takenokoshi.mekut.common.recipe.MekUtRecipeType;

public class TileEntityMineralExtractor extends TileEntityElectricMachine {

    public TileEntityMineralExtractor(Holder<Block> blockProvider, BlockPos pos, BlockState state) {
        super(blockProvider, pos, state, BASE_TICKS_REQUIRED);
    }

    @Override
    public @NotNull IMekanismRecipeTypeProvider<?, ItemStackToItemStackRecipe, SingleItem<ItemStackToItemStackRecipe>> getRecipeType() {
        return MekUtRecipeType.MINERAL_EXTRACTING;
    }

}
