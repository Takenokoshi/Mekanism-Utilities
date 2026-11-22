package takenokoshi.mekut.client.recipe_viewer.type;

import mekanism.api.recipes.ItemStackToFluidRecipe;
import mekanism.api.recipes.ItemStackToItemStackRecipe;
import mekanism.client.recipe_viewer.type.RVRecipeTypeWrapper;
import takenokoshi.mekal.api.recipes.FluidToItemStackRecipe;
import takenokoshi.mekal.api.recipes.ItemStackFluidToItemStackRecipe;
import takenokoshi.mekut.common.recipe.MekUtRecipeType;
import takenokoshi.mekut.common.registries.MekUtMachines;

public class MekUtRVRecipeType {

    public static final RVRecipeTypeWrapper<?, FluidToItemStackRecipe, ?> FREEZING = new RVRecipeTypeWrapper<>(
            MekUtRecipeType.FREEZING, FluidToItemStackRecipe.class,
            -28, -16, 144, 54,
            MekUtMachines.FREEZING_MACHINE);

    public static final RVRecipeTypeWrapper<?, ItemStackFluidToItemStackRecipe, ?> HYDRAULIC_EXTRACTING = new RVRecipeTypeWrapper<>(
            MekUtRecipeType.HYDRAULIC_EXTRACTING, ItemStackFluidToItemStackRecipe.class,
            -28, -16, 144, 54,
            MekUtMachines.HYDRAULIC_EXTRACTOR);

    public static final RVRecipeTypeWrapper<?, ItemStackToItemStackRecipe, ?> MINERAL_EXTRACTING = new RVRecipeTypeWrapper<>(
            MekUtRecipeType.MINERAL_EXTRACTING, ItemStackToItemStackRecipe.class,
            -28, -16, 144, 54,
            MekUtMachines.MINERAL_EXTRACTOR);

    public static final RVRecipeTypeWrapper<?, ItemStackToFluidRecipe, ?> SQUEEZING = new RVRecipeTypeWrapper<>(
            MekUtRecipeType.SQUEEZING, ItemStackToFluidRecipe.class,
            -28, -16, 144, 54,
            MekUtMachines.SQUEEZER);
}
