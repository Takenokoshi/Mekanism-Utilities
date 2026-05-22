package com.takenokoshi.mekut.recipe_viewer.type;

import java.util.List;
import java.util.stream.Stream;

import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekut.recipe.IMekUtRecipeTypeProvider;
import com.takenokoshi.mekut.recipe.MekUtRecipeType;

import mekanism.api.text.TextComponentUtil;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mekanism.common.recipe.lookup.cache.IInputRecipeCache;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.ItemLike;

public record RVMekUtRecipeTypeWrapper<VANILLA_INPUT extends RecipeInput, RECIPE extends Recipe<VANILLA_INPUT>, INPUT_CACHE extends IInputRecipeCache>(
        ResourceLocation id,
        ItemLike item,
        Class<? extends RECIPE> recipeClass,
        IMekUtRecipeTypeProvider<VANILLA_INPUT, RECIPE, INPUT_CACHE> vanillaProvider,
        int xOffset, int yOffset, int width, int height,
        List<ItemLike> workstations)
        implements IRecipeViewerRecipeType<RECIPE>, IMekUtRecipeTypeProvider<VANILLA_INPUT, RECIPE, INPUT_CACHE> {

    public RVMekUtRecipeTypeWrapper(
            ResourceLocation id,
            ItemLike item,
            Class<? extends RECIPE> recipeClass,
            IMekUtRecipeTypeProvider<VANILLA_INPUT, RECIPE, INPUT_CACHE> vanillaProvider,
            int xOffset, int yOffset, int width, int height,
            ItemLike... workstations) {
        this(id, item, recipeClass, vanillaProvider, xOffset, yOffset, width, height, List.of(workstations));
    }

    public RVMekUtRecipeTypeWrapper {
        if (workstations.isEmpty()) {
            workstations = List.of(item);
        } else {
            workstations = Stream.concat(Stream.of(item), workstations.stream()).toList();
        }
    }

    @Override
    public Component getTextComponent() {
        return TextComponentUtil.build(item);
    }

    @Override
    public boolean requiresHolder() {
        return true;
    }

    @Override
    public ItemStack iconStack() {
        return new ItemStack(item);
    }

    @Nullable
    @Override
    public ResourceLocation icon() {
        return null;
    }

    @Override
    public MekUtRecipeType<VANILLA_INPUT, RECIPE, INPUT_CACHE> getRecipeType() {
        return vanillaProvider.getRecipeType();
    }

}
