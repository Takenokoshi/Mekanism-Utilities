package com.takenokoshi.mekut.recipe.type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.fxd927.mekanismelements.api.recipes.AdsorptionRecipe;
import com.fxd927.mekanismelements.api.recipes.ChemicalDemolitionRecipe;
import com.fxd927.mekanismelements.api.recipes.RadiationIrradiatingRecipe;
import com.fxd927.mekanismelements.common.recipe.IMSRecipeTypeProvider;
import com.fxd927.mekanismelements.common.recipe.MSRecipeType;
import com.fxd927.mekanismelements.common.recipe.lookup.cache.MSInputRecipeCache;

import mekanism.api.recipes.FluidToFluidRecipe;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.vanilla_input.FluidRecipeInput;
import mekanism.api.recipes.vanilla_input.ItemChemicalRecipeInput;
import mekanism.api.recipes.vanilla_input.SingleFluidRecipeInput;
import mekanism.common.recipe.lookup.cache.IInputRecipeCache;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeManager;

public class WrappedElementsRecipeType<VANILLA_INPUT extends RecipeInput, RECIPE extends MekanismRecipe<VANILLA_INPUT>, INPUT_CACHE extends IInputRecipeCache>
        extends MekUtRecipeType<VANILLA_INPUT, RECIPE, INPUT_CACHE> {

    public final MSRecipeType<RECIPE, INPUT_CACHE> wrappedType;
    public final Class<VANILLA_INPUT> inputClazz;

    public WrappedElementsRecipeType(IMSRecipeTypeProvider<RECIPE, INPUT_CACHE> wrappedType,
            Class<VANILLA_INPUT> inputClazz) {
        super(wrappedType.getRegistryName(), type -> wrappedType.getInputCache());
        this.wrappedType = wrappedType.getMSRecipeType();
        this.inputClazz = inputClazz;
    }

    @Override
    public INPUT_CACHE getInputCache() {
        return wrappedType.getInputCache();
    }

    protected @NotNull List<RecipeHolder<RECIPE>> getRecipesUncached(@NotNull RecipeManager recipeManager,
            @Nullable RegistryAccess registryAccess) {
        List<RecipeHolder<RECIPE>> recipes = recipeManager.getAllRecipesFor(this);
        List<RecipeHolder<RECIPE>> result = new ArrayList<>();
        for (int i = 0; i < recipes.size(); i++) {
            RecipeHolder<RECIPE> holder = recipes.get(i);
            RECIPE recipe = holder.value();
            if (recipe instanceof AdsorptionRecipe r) {
                r.setId(holder.id());
            } else if (recipe instanceof RadiationIrradiatingRecipe r) {
                r.setId(holder.id());
            } else if (recipe instanceof ChemicalDemolitionRecipe r) {
                r.setId(holder.id());
            }
            result.add(new RecipeHolder<RECIPE>(holder.id(), recipe));
        }
        return Collections.unmodifiableList(result);
    }

    public static final WrappedElementsRecipeType<ItemChemicalRecipeInput, RadiationIrradiatingRecipe, MSInputRecipeCache.ItemChemical<RadiationIrradiatingRecipe>> RADIATION_IRRADIATING = new WrappedElementsRecipeType<>(
            MSRecipeType.RADIATION_IRRADIATING, ItemChemicalRecipeInput.class);
    public static final WrappedElementsRecipeType<FluidRecipeInput, AdsorptionRecipe, MSInputRecipeCache.ItemFluid<AdsorptionRecipe>> ADSORPTION = new WrappedElementsRecipeType<>(
            MSRecipeType.ADSORPTION, FluidRecipeInput.class);
    public static final WrappedElementsRecipeType<SingleFluidRecipeInput, FluidToFluidRecipe, MSInputRecipeCache.SingleFluid<FluidToFluidRecipe>> ADVANCED_EVAPORATING = new WrappedElementsRecipeType<>(
            MSRecipeType.ADVANCED_EVAPORATING, SingleFluidRecipeInput.class);
    public static final WrappedElementsRecipeType<ItemChemicalRecipeInput, ChemicalDemolitionRecipe, MSInputRecipeCache.ItemChemical<ChemicalDemolitionRecipe>> CHEMICAL_DEMOLITION = new WrappedElementsRecipeType<>(
            MSRecipeType.CHEMICAL_DEMOLITION, ItemChemicalRecipeInput.class);

}
