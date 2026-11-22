package takenokoshi.mekal.client.recipe_viewer.emi.recipe;

import mekanism.api.recipes.ItemStackToFluidRecipe;
import mekanism.client.recipe_viewer.emi.MekanismEmiRecipeCategory;
import mekanism.client.recipe_viewer.emi.recipe.ItemStackToFluidEmiRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import takenokoshi.mekal.common.tile.prefab.TileEntityItemStackToFluidMachine;

public class MekALItemStackToFluidEmiRecipe extends ItemStackToFluidEmiRecipe {

    public MekALItemStackToFluidEmiRecipe(MekanismEmiRecipeCategory category,
            RecipeHolder<ItemStackToFluidRecipe> recipeHolder) {
        super(category, recipeHolder.id(), recipeHolder.value(), TileEntityItemStackToFluidMachine.BASE_TICKS_REQUIRED);
    }

}
