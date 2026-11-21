package takenokoshi.mekal.common.recipe.lookup.monitor;

import java.util.function.IntSupplier;

import org.jetbrains.annotations.NotNull;

import mekanism.api.recipes.cache.CachedRecipe;
import mekanism.common.recipe.lookup.IRecipeLookupHandler;
import mekanism.common.recipe.lookup.monitor.RecipeCacheLookupMonitor;
import takenokoshi.mekal.api.recipes.ItemStackFluidToObjectRecipe;
import takenokoshi.mekal.api.recipes.cache.ItemStackConstantFluidToObjectCachedRecipe;

public class ItemStackConstantFluidRecipeLookupMonitor<OUTPUT, RECIPE extends ItemStackFluidToObjectRecipe<OUTPUT>>
        extends RecipeCacheLookupMonitor<RECIPE> {

    private final IntSupplier getSavedUsedSoFar;

    public ItemStackConstantFluidRecipeLookupMonitor(IRecipeLookupHandler<RECIPE> handler, int cacheIndex,
            IntSupplier getSavedUsedSoFar) {
        super(handler, cacheIndex);
        this.getSavedUsedSoFar = getSavedUsedSoFar;
    }

    public ItemStackConstantFluidRecipeLookupMonitor(IRecipeLookupHandler<RECIPE> handler,
            IntSupplier getSavedUsedSoFar) {
        this(handler, 0, getSavedUsedSoFar);
    }

    @Override
    public void loadSavedData(@NotNull CachedRecipe<RECIPE> cached, int cacheIndex) {
        super.loadSavedData(cached, cacheIndex);
        if (cached instanceof ItemStackConstantFluidToObjectCachedRecipe<?, ?> c) {
            c.loadSavedUsedSoFar(getSavedUsedSoFar.getAsInt());
        }
    }

}
