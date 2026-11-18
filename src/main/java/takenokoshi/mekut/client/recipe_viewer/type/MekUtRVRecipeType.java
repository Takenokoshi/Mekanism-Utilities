package takenokoshi.mekut.client.recipe_viewer.type;

import mekanism.api.recipes.ItemStackToItemStackRecipe;
import mekanism.client.recipe_viewer.type.RVRecipeTypeWrapper;
import takenokoshi.mekut.common.recipe.MekUtRecipeType;
import takenokoshi.mekut.common.registries.MekUtMachines;

public class MekUtRVRecipeType {

    public static final RVRecipeTypeWrapper<?, ItemStackToItemStackRecipe, ?> MINERAL_EXTRACTING = new RVRecipeTypeWrapper<>(
            MekUtRecipeType.MINERAL_EXTRACTING, ItemStackToItemStackRecipe.class,
            -28, -16, 144, 54,
            MekUtMachines.MINERAL_EXTRACTOR);
}
