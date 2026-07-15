package com.takenokoshi.mekut.recipe.inputcache;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.ToIntFunction;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekaddonlib.recipe.type.MekALRecipeType;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;

public class ItemStackListFluidInputRecipeCache<RECIPE extends Recipe<?>> extends MUAbstractInputRecipeCache<RECIPE> {

    protected final Function<RECIPE, @NotNull List<Item>> itemInputExtractor;
    //should return List.of(Fluids.EMPTY) when recipe won't require fluid.
    protected final Function<RECIPE, @NotNull List<Fluid>> fluidInputExtractor;
    protected final ToIntFunction<RECIPE> listSizeExtractor;
    protected final BiPredicate<RECIPE, List<ItemStack>> testPredicate;
    protected final Map<Item, Set<RECIPE>> itemToRecipeCache = new HashMap<>();
    protected final Object2IntMap<Item> itemToMaxListSizeCache = new Object2IntOpenHashMap<>();
    protected final Map<Fluid, Set<RECIPE>> fluidToRecipeCache = new HashMap<>();

    public ItemStackListFluidInputRecipeCache(MekALRecipeType<?, RECIPE, ?> recipeType,
            Function<RECIPE, List<Item>> itemInputExtractor,
            Function<RECIPE, List<Fluid>> fluidInputExtractor,
            ToIntFunction<RECIPE> listSizeExtractor,
            BiPredicate<RECIPE, List<ItemStack>> testPredicate) {
        super(recipeType);
        this.itemInputExtractor = itemInputExtractor;
        this.fluidInputExtractor = fluidInputExtractor;
        this.listSizeExtractor = listSizeExtractor;
        this.testPredicate = testPredicate;
        this.itemToMaxListSizeCache.defaultReturnValue(0);
    }

    public void clear() {
        super.clear();
        itemToRecipeCache.clear();
        itemToMaxListSizeCache.clear();
        fluidToRecipeCache.clear();
    }

    @Override
    protected void initCache(List<RecipeHolder<RECIPE>> recipes) {
        for (RecipeHolder<RECIPE> recipeHolder : recipes) {
            RECIPE recipe = recipeHolder.value();
            int listSize = listSizeExtractor.applyAsInt(recipe);
            itemInputExtractor.apply(recipe).forEach(item -> {
                itemToRecipeCache.merge(item, new HashSet<>(List.of(recipe)),
                        ItemStackListFluidInputRecipeCache::addAll);
                itemToMaxListSizeCache.mergeInt(item, listSize, Math::max);
            });
            fluidInputExtractor.apply(recipe).forEach(fluid -> {
                fluidToRecipeCache.merge(fluid, new HashSet<>(List.of(recipe)),
                        ItemStackListFluidInputRecipeCache::addAll);
            });
        }
    }

    private static <T> Set<T> addAll(Set<T> a, Set<T> b) {
        a.addAll(b);
        return a;
    }

    // slotIndex >= 0
    public boolean containsItem(Level world, ItemStack input, int slotIndex) {
        if (input.isEmpty()) {
            return false;
        }
        initCacheIfNeeded(world);
        return itemToMaxListSizeCache.getInt(input.getItem()) > slotIndex;
    }

    public boolean containsFluid(Level world, FluidStack input) {
        if (input.isEmpty()) {
            return false;
        }
        initCacheIfNeeded(world);
        return fluidToRecipeCache.containsKey(input.getFluid());
    }

    public boolean containsItemOther(Level world, ItemStack itemInput, int slotIndex, List<ItemStack> otherItemInputs,
            FluidStack fluidInput) {
        if (itemInput.isEmpty()) {
            return false;
        }
        for (ItemStack itemStack : otherItemInputs) {
            if (ItemStack.isSameItem(itemStack, itemInput)) {
                return false;
            }
        }
        initCacheIfNeeded(world);
        if (itemToMaxListSizeCache.getInt(itemInput.getItem()) <= slotIndex) {
            return false;
        }
        if (otherItemInputs.isEmpty()) {//fast check for empty item inputs
            if (fluidInput.isEmpty()) {
                return true;
            } else {
                return !Collections.disjoint(itemToRecipeCache.get(itemInput.getItem()),
                        fluidToRecipeCache.getOrDefault(fluidInput.getFluid(), Set.of()));
            }
        } else {
            Item item = itemInput.getItem();
            Set<RECIPE> common = new HashSet<>(itemToRecipeCache.get(item));
            if (!fluidInput.isEmpty()) {
                common.retainAll(fluidToRecipeCache.getOrDefault(fluidInput.getFluid(), Set.of()));
                if (common.isEmpty()) {
                    return false;
                }
            }
            for (int i = 0; i < otherItemInputs.size() && !common.isEmpty(); i++) {
                common.retainAll(itemToRecipeCache.getOrDefault(otherItemInputs.get(i).getItem(), Set.of()));
            }
            return !common.isEmpty();
        }
    }

    public boolean containsFluidOther(Level world, List<ItemStack> itemInputs,
            FluidStack fluidInput) {
        if (itemInputs.isEmpty()) {
            // containsFluid will check fluidInput.isEmpty()
            return containsFluid(world, fluidInput);
        }
        if (fluidInput.isEmpty()) {
            return false;
        }
        initCacheIfNeeded(world);
        Fluid fluid = fluidInput.getFluid();
        if (!fluidToRecipeCache.containsKey(fluid)) {
            return false;
        }
        Set<RECIPE> common = new HashSet<>(fluidToRecipeCache.get(fluid));
        for (int i = 0; i < itemInputs.size() && !common.isEmpty(); i++) {
            common.retainAll(itemToRecipeCache.getOrDefault(itemInputs.get(i).getItem(), Set.of()));
        }
        return !common.isEmpty();
    }

    @Nullable
    public RECIPE findFirstRecipe(Level world, List<ItemStack> itemInputs,
            FluidStack fluidInput) {
        if (itemInputs.isEmpty() && fluidInput.isEmpty()) {
            return null;
        }
        initCacheIfNeeded(world);
        Fluid fluid = fluidInput.isEmpty() ? Fluids.EMPTY : fluidInput.getFluid();
        return fluidToRecipeCache.containsKey(fluid)
                ? fluidToRecipeCache.get(fluid).stream()
                        .filter(recipe -> testPredicate.test(recipe, itemInputs))
                        .findFirst().orElse(null)
                : null;
    }

}
