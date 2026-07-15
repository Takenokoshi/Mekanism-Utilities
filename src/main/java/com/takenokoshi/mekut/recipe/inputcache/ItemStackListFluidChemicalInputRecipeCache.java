package com.takenokoshi.mekut.recipe.inputcache;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekaddonlib.recipe.type.MekALRecipeType;
import com.takenokoshi.mekut.recipe.recipe.prefab.ItemStackListFluidChemicalToObjectsRecipe;
import com.takenokoshi.mekut.utils.CacheTable;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;

public class ItemStackListFluidChemicalInputRecipeCache<RECIPE extends ItemStackListFluidChemicalToObjectsRecipe>
        extends MUAbstractInputRecipeCache<RECIPE> {

    protected final Map<Item, Set<RECIPE>> itemToRecipeCache = new HashMap<>();
    protected final Object2IntMap<Item> itemToMaxListSizeCache = new Object2IntOpenHashMap<>();
    protected final Map<Fluid, Set<RECIPE>> fluidToRecipeCache = new HashMap<>();
    protected final Map<Chemical, Set<RECIPE>> chemicalToRecipeCache = new HashMap<>();
    protected final CacheTable<Fluid, Chemical, Set<RECIPE>> fluidChemicalToRecipeCache = new CacheTable<>();

    public ItemStackListFluidChemicalInputRecipeCache(MekALRecipeType<?, RECIPE, ?> recipeType) {
        super(recipeType);
    }

    @Override
    public void clear() {
        super.clear();
        itemToRecipeCache.clear();
        itemToMaxListSizeCache.clear();
        fluidChemicalToRecipeCache.clear();
        fluidToRecipeCache.clear();
        chemicalToRecipeCache.clear();
    }

    @Override
    protected void initCache(List<RecipeHolder<RECIPE>> recipes) {
        for (RecipeHolder<RECIPE> recipeHolder : recipes) {
            RECIPE recipe = recipeHolder.value();
            int listSize = recipe.itemInputs.size();
            recipe.getIngredientItems().forEach(item -> {
                itemToRecipeCache.merge(item, singleSet(recipe),
                        ItemStackListFluidChemicalInputRecipeCache::addAll);
                itemToMaxListSizeCache.mergeInt(item, listSize, Math::max);
            });
            final List<Fluid> fluids = recipe.getIngredientFluids();
            final List<Chemical> chemicals = recipe.getIngredientChemicals();
            fluids.forEach(fluid -> {
                fluidToRecipeCache.merge(fluid, singleSet(recipe),
                        ItemStackListFluidChemicalInputRecipeCache::addAll);
                chemicals.forEach(chemical -> {
                    fluidChemicalToRecipeCache.merge(fluid, chemical, singleSet(recipe),
                            ItemStackListFluidChemicalInputRecipeCache::addAll);
                });
            });
            chemicals.forEach(chemical -> {
                chemicalToRecipeCache.merge(chemical, singleSet(recipe),
                        ItemStackListFluidChemicalInputRecipeCache::addAll);
            });
        }
    }

    private static <T> Set<T> singleSet(T elem) {
        Set<T> result = new HashSet<>();
        result.add(elem);
        return result;
    }

    private static <T> Set<T> addAll(Set<T> a, Set<T> b) {
        Set<T> result = new HashSet<>();
        a.forEach(result::add);
        b.forEach(result::add);
        return result;
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
        return fluidChemicalToRecipeCache.containsKey1(input.getFluid());
    }

    public boolean containsChemical(Level world, ChemicalStack input) {
        if (input.isEmpty()) {
            return false;
        }
        initCacheIfNeeded(world);
        return fluidChemicalToRecipeCache.containsKey2(input.getChemical());
    }

    public boolean containsItemOther(Level world, ItemStack itemInput, int slotIndex, List<ItemStack> otherItemInputs,
            FluidStack fluidInput, ChemicalStack chemicalInput) {
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
        if (otherItemInputs.isEmpty()) {
            if (fluidInput.isEmpty()) {
                if (chemicalInput.isEmpty()) {
                    return true;
                } else {
                    return !Collections.disjoint(itemToRecipeCache.get(itemInput.getItem()),
                            chemicalToRecipeCache.getOrDefault(chemicalInput.getChemical(), Set.of()));
                }
            } else {
                if (chemicalInput.isEmpty()) {
                    return !Collections.disjoint(itemToRecipeCache.get(itemInput.getItem()),
                            fluidToRecipeCache.getOrDefault(fluidInput.getFluid(), Set.of()));
                } else {
                    return !Collections.disjoint(itemToRecipeCache.get(itemInput.getItem()),
                            fluidChemicalToRecipeCache.getOrDefault(fluidInput.getFluid(), chemicalInput.getChemical(),
                                    Set.of()));
                }
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
            if (!chemicalInput.isEmpty()) {
                common.retainAll(chemicalToRecipeCache.getOrDefault(chemicalInput.getChemical(), Set.of()));
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
            FluidStack fluidInput, ChemicalStack chemicalInput) {
        if (itemInputs.isEmpty() && chemicalInput.isEmpty()) {
            return containsFluid(world, fluidInput);
        }
        if (fluidInput.isEmpty()) {
            return false;
        }
        initCacheIfNeeded(world);
        Fluid fluid = fluidInput.getFluid();
        if (itemInputs.isEmpty()) {
            return fluidChemicalToRecipeCache.containsKeys(fluid, chemicalInput.getChemical());
        }
        Set<RECIPE> common = new HashSet<>(fluidToRecipeCache.get(fluid));
        for (int i = 0; i < itemInputs.size() && !common.isEmpty(); i++) {
            common.retainAll(itemToRecipeCache.getOrDefault(itemInputs.get(i).getItem(), Set.of()));
        }
        if (!chemicalInput.isEmpty()) {
            common.retainAll(chemicalToRecipeCache.getOrDefault(chemicalInput.getChemical(), Set.of()));
        }
        return !common.isEmpty();
    }

    public boolean containsChemicalOther(Level world, List<ItemStack> itemInputs,
            FluidStack fluidInput, ChemicalStack chemicalInput) {
        if (itemInputs.isEmpty() && fluidInput.isEmpty()) {
            return containsChemical(world, chemicalInput);
        }
        if (chemicalInput.isEmpty()) {
            return false;
        }
        initCacheIfNeeded(world);
        Chemical chemical = chemicalInput.getChemical();
        if (itemInputs.isEmpty()) {
            return fluidChemicalToRecipeCache.containsKeys(fluidInput.getFluid(), chemical);
        }
        Set<RECIPE> common = new HashSet<>(chemicalToRecipeCache.getOrDefault(chemical, Set.of()));
        for (int i = 0; i < itemInputs.size() && !common.isEmpty(); i++) {
            common.retainAll(itemToRecipeCache.getOrDefault(itemInputs.get(i).getItem(), Set.of()));
        }
        if (!fluidInput.isEmpty()) {
            common.retainAll(fluidToRecipeCache.getOrDefault(fluidInput.getFluid(), Set.of()));
        }
        return !common.isEmpty();
    }

    public @Nullable RECIPE findFirstRecipe(Level world, List<ItemStack> itemInputs,
            FluidStack fluidInput, ChemicalStack chemicalInput) {
        if (itemInputs.isEmpty() && fluidInput.isEmpty() && chemicalInput.isEmpty()) {
            return null;
        }
        initCacheIfNeeded(world);
        return fluidChemicalToRecipeCache.getOrDefault(fluidInput.getFluid(), chemicalInput.getChemical(), Set.of())
                .stream().filter(recipe -> recipe.testItem(itemInputs)).findFirst().orElse(null);
    }

}
