package takenokoshi.mekut.api.recipes;

import mekanism.api.recipes.ItemStackToItemStackRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import takenokoshi.mekut.common.MekUt;

public class MekUtRecipeTypes {
    public static final ResourceLocation NAME_MINERAL_EXTRACTING = ResourceLocation
            .fromNamespaceAndPath(MekUt.MODID, "mineral_extracting");

    public static final DeferredHolder<RecipeType<?>, RecipeType<ItemStackToItemStackRecipe>> TYPE_MINERAL_EXTRACTING = DeferredHolder
            .create(Registries.RECIPE_TYPE, NAME_MINERAL_EXTRACTING);
}
