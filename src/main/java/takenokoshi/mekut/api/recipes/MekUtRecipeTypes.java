package takenokoshi.mekut.api.recipes;

import mekanism.api.recipes.ItemStackToFluidRecipe;
import mekanism.api.recipes.ItemStackToItemStackRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import takenokoshi.mekal.api.recipes.FluidToItemStackRecipe;
import takenokoshi.mekal.api.recipes.ItemStackFluidToItemStackRecipe;
import takenokoshi.mekut.common.MekUt;

public class MekUtRecipeTypes {
    public static final ResourceLocation NAME_FREEZING = ResourceLocation
            .fromNamespaceAndPath(MekUt.MODID, "freezing");
    public static final ResourceLocation NAME_HYDRAULIC_EXTRACTING = ResourceLocation
            .fromNamespaceAndPath(MekUt.MODID, "hydraulic_extracting");
    public static final ResourceLocation NAME_MINERAL_EXTRACTING = ResourceLocation
            .fromNamespaceAndPath(MekUt.MODID, "mineral_extracting");
    public static final ResourceLocation NAME_SPALLATION = ResourceLocation
            .fromNamespaceAndPath(MekUt.MODID, "spallation");
    public static final ResourceLocation NAME_SQUEEZING = ResourceLocation
            .fromNamespaceAndPath(MekUt.MODID, "squeezing");

    public static final DeferredHolder<RecipeType<?>, RecipeType<FluidToItemStackRecipe>> TYPE_FREEZING = DeferredHolder
            .create(Registries.RECIPE_TYPE, NAME_FREEZING);
    public static final DeferredHolder<RecipeType<?>, RecipeType<ItemStackFluidToItemStackRecipe>> TYPE_HYDRAULIC_EXTRACTING = DeferredHolder
            .create(Registries.RECIPE_TYPE, NAME_HYDRAULIC_EXTRACTING);
    public static final DeferredHolder<RecipeType<?>, RecipeType<ItemStackToItemStackRecipe>> TYPE_MINERAL_EXTRACTING = DeferredHolder
            .create(Registries.RECIPE_TYPE, NAME_MINERAL_EXTRACTING);
    public static final DeferredHolder<RecipeType<?>, RecipeType<SpallationRecipe>> TYPE_SPALLATION = DeferredHolder
            .create(Registries.RECIPE_TYPE, NAME_SPALLATION);
    public static final DeferredHolder<RecipeType<?>, RecipeType<ItemStackToFluidRecipe>> TYPE_SQUEEZING = DeferredHolder
            .create(Registries.RECIPE_TYPE, NAME_SQUEEZING);
}
