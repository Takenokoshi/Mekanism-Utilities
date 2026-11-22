package takenokoshi.mekut.common.recipe;

import java.util.function.Function;

import mekanism.api.recipes.ItemStackToFluidRecipe;
import mekanism.api.recipes.ItemStackToItemStackRecipe;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.vanilla_input.SingleChemicalRecipeInput;
import mekanism.api.recipes.vanilla_input.SingleFluidRecipeInput;
import mekanism.common.recipe.MekanismRecipeType;
import mekanism.common.recipe.lookup.cache.IInputRecipeCache;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.SingleChemical;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.SingleFluid;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.SingleItem;
import mekanism.common.registration.impl.RecipeTypeDeferredRegister;
import mekanism.common.registration.impl.RecipeTypeRegistryObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import takenokoshi.mekal.api.recipes.FluidToItemStackRecipe;
import takenokoshi.mekal.api.recipes.ItemStackFluidToItemStackRecipe;
import takenokoshi.mekal.api.recipes.vanilla_input.SingleItemFluidRecipeInput;
import takenokoshi.mekal.common.recipe.lookup.cache.MekALInputRecipeCache.ItemFluid;
import takenokoshi.mekut.api.recipes.MekUtRecipeTypes;
import takenokoshi.mekut.api.recipes.SpallationRecipe;
import takenokoshi.mekut.common.MekUt;

public class MekUtRecipeType {

    public static final RecipeTypeDeferredRegister RECIPE_TYPES = new RecipeTypeDeferredRegister(MekUt.MODID);

    public static final RecipeTypeRegistryObject<SingleFluidRecipeInput, FluidToItemStackRecipe, SingleFluid<FluidToItemStackRecipe>> FREEZING = register(
            MekUtRecipeTypes.NAME_FREEZING,
            recipeType -> new SingleFluid<>(recipeType, FluidToItemStackRecipe::getInput));

    public static final RecipeTypeRegistryObject<SingleItemFluidRecipeInput, ItemStackFluidToItemStackRecipe, ItemFluid<ItemStackFluidToItemStackRecipe>> HYDRAULIC_EXTRACTING = register(
            MekUtRecipeTypes.NAME_HYDRAULIC_EXTRACTING,
            ItemFluid::createSimple);

    public static final RecipeTypeRegistryObject<SingleRecipeInput, ItemStackToItemStackRecipe, SingleItem<ItemStackToItemStackRecipe>> MINERAL_EXTRACTING = register(
            MekUtRecipeTypes.NAME_MINERAL_EXTRACTING,
            recipeType -> new SingleItem<>(recipeType, ItemStackToItemStackRecipe::getInput));

    public static final RecipeTypeRegistryObject<SingleChemicalRecipeInput, SpallationRecipe, SingleChemical<SpallationRecipe>> SPALLATION = register(
            MekUtRecipeTypes.NAME_SPALLATION,
            recipeType -> new SingleChemical<>(recipeType, SpallationRecipe::getInput));

    public static final RecipeTypeRegistryObject<SingleRecipeInput, ItemStackToFluidRecipe, SingleItem<ItemStackToFluidRecipe>> SQUEEZING = register(
            MekUtRecipeTypes.NAME_SQUEEZING,
            recipeType -> new SingleItem<>(recipeType, ItemStackToFluidRecipe::getInput));

    private static <VANILLA_INPUT extends RecipeInput, RECIPE extends MekanismRecipe<VANILLA_INPUT>, INPUT_CACHE extends IInputRecipeCache> RecipeTypeRegistryObject<VANILLA_INPUT, RECIPE, INPUT_CACHE> register(
            ResourceLocation name,
            Function<MekanismRecipeType<VANILLA_INPUT, RECIPE, INPUT_CACHE>, INPUT_CACHE> inputCacheCreator) {
        if (!MekUt.MODID.equals(name.getNamespace())) {
            throw new IllegalStateException("Name must be in " + MekUt.MODID + " namespace");
        }
        return RECIPE_TYPES.registerMek(name.getPath(),
                registryName -> new MekanismRecipeType<>(registryName, inputCacheCreator));
    }
}
