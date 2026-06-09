package com.takenokoshi.mekut.recipe.type;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import mekanism.api.recipes.ChemicalChemicalToChemicalRecipe;
import mekanism.api.recipes.ChemicalCrystallizerRecipe;
import mekanism.api.recipes.ChemicalDissolutionRecipe;
import mekanism.api.recipes.ChemicalToChemicalRecipe;
import mekanism.api.recipes.CombinerRecipe;
import mekanism.api.recipes.ElectrolysisRecipe;
import mekanism.api.recipes.FluidChemicalToChemicalRecipe;
import mekanism.api.recipes.FluidToFluidRecipe;
import mekanism.api.recipes.ItemStackChemicalToItemStackRecipe;
import mekanism.api.recipes.ItemStackToChemicalRecipe;
import mekanism.api.recipes.ItemStackToEnergyRecipe;
import mekanism.api.recipes.ItemStackToItemStackRecipe;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.NucleosynthesizingRecipe;
import mekanism.api.recipes.PressurizedReactionRecipe;
import mekanism.api.recipes.RotaryRecipe;
import mekanism.api.recipes.SawmillRecipe;
import mekanism.api.recipes.vanilla_input.BiChemicalRecipeInput;
import mekanism.api.recipes.vanilla_input.ReactionRecipeInput;
import mekanism.api.recipes.vanilla_input.RotaryRecipeInput;
import mekanism.api.recipes.vanilla_input.SingleChemicalRecipeInput;
import mekanism.api.recipes.vanilla_input.SingleFluidChemicalRecipeInput;
import mekanism.api.recipes.vanilla_input.SingleFluidRecipeInput;
import mekanism.api.recipes.vanilla_input.SingleItemChemicalRecipeInput;
import mekanism.common.recipe.IMekanismRecipeTypeProvider;
import mekanism.common.recipe.MekanismRecipeType;
import mekanism.common.recipe.lookup.cache.IInputRecipeCache;
import mekanism.common.recipe.lookup.cache.InputRecipeCache;
import mekanism.common.recipe.lookup.cache.InputRecipeCache.SingleItem;
import mekanism.common.recipe.lookup.cache.RotaryInputRecipeCache;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;

public class WrappedMekanismRecipeType<VANILLA_INPUT extends RecipeInput, RECIPE extends MekanismRecipe<VANILLA_INPUT>, INPUT_CACHE extends IInputRecipeCache>
        extends MekUtRecipeType<VANILLA_INPUT, RECIPE, INPUT_CACHE> {

    public final MekanismRecipeType<VANILLA_INPUT, RECIPE, INPUT_CACHE> wrappedType;

    public WrappedMekanismRecipeType(
            IMekanismRecipeTypeProvider<VANILLA_INPUT, RECIPE, INPUT_CACHE> wrappedType) {
        super(wrappedType.getRegistryName(), type -> wrappedType.getInputCache());
        this.wrappedType = wrappedType.getRecipeType();
    }

    @Override
    public INPUT_CACHE getInputCache() {
        return wrappedType.getInputCache();
    }

    @Override
    public @NotNull List<RecipeHolder<RECIPE>> getRecipes(@NotNull RecipeManager recipeManager,
            @Nullable RegistryAccess registryAccess) {
        return wrappedType.getRecipes(recipeManager, registryAccess);
    }

    // other methods won't be called.

    public static final WrappedMekanismRecipeType<SingleRecipeInput, ItemStackToItemStackRecipe, InputRecipeCache.SingleItem<ItemStackToItemStackRecipe>> CRUSHING = new WrappedMekanismRecipeType<>(
            MekanismRecipeType.CRUSHING);
    public static final WrappedMekanismRecipeType<SingleRecipeInput, ItemStackToItemStackRecipe, InputRecipeCache.SingleItem<ItemStackToItemStackRecipe>> ENRICHING = new WrappedMekanismRecipeType<>(
            MekanismRecipeType.ENRICHING);
    public static final WrappedMekanismRecipeType<SingleRecipeInput, ItemStackToItemStackRecipe, InputRecipeCache.SingleItem<ItemStackToItemStackRecipe>> SMELTING = new WrappedMekanismRecipeType<>(
            MekanismRecipeType.SMELTING);
    public static final WrappedMekanismRecipeType<BiChemicalRecipeInput, ChemicalChemicalToChemicalRecipe, InputRecipeCache.EitherSideChemical<ChemicalChemicalToChemicalRecipe>> CHEMICAL_INFUSING = new WrappedMekanismRecipeType<>(
            MekanismRecipeType.CHEMICAL_INFUSING);
    public static final WrappedMekanismRecipeType<RecipeInput, CombinerRecipe, InputRecipeCache.DoubleItem<CombinerRecipe>> COMBINING = new WrappedMekanismRecipeType<>(
            MekanismRecipeType.COMBINING);
    public static final WrappedMekanismRecipeType<SingleFluidRecipeInput, ElectrolysisRecipe, InputRecipeCache.SingleFluid<ElectrolysisRecipe>> SEPARATING = new WrappedMekanismRecipeType<>(
            MekanismRecipeType.SEPARATING);
    public static final WrappedMekanismRecipeType<SingleFluidChemicalRecipeInput, FluidChemicalToChemicalRecipe, InputRecipeCache.FluidChemical<FluidChemicalToChemicalRecipe>> WASHING = new WrappedMekanismRecipeType<>(
            MekanismRecipeType.WASHING);
    public static final WrappedMekanismRecipeType<SingleFluidRecipeInput, FluidToFluidRecipe, InputRecipeCache.SingleFluid<FluidToFluidRecipe>> EVAPORATING = new WrappedMekanismRecipeType<>(
            MekanismRecipeType.EVAPORATING);
    public static final WrappedMekanismRecipeType<SingleChemicalRecipeInput, ChemicalToChemicalRecipe, InputRecipeCache.SingleChemical<ChemicalToChemicalRecipe>> ACTIVATING = new WrappedMekanismRecipeType<>(
            MekanismRecipeType.ACTIVATING);
    public static final WrappedMekanismRecipeType<SingleChemicalRecipeInput, ChemicalToChemicalRecipe, InputRecipeCache.SingleChemical<ChemicalToChemicalRecipe>> CENTRIFUGING = new WrappedMekanismRecipeType<>(
            MekanismRecipeType.CENTRIFUGING);
    public static final WrappedMekanismRecipeType<SingleChemicalRecipeInput, ChemicalCrystallizerRecipe, InputRecipeCache.SingleChemical<ChemicalCrystallizerRecipe>> CRYSTALLIZING = new WrappedMekanismRecipeType<>(
            MekanismRecipeType.CRYSTALLIZING);
    public static final WrappedMekanismRecipeType<SingleItemChemicalRecipeInput, ChemicalDissolutionRecipe, InputRecipeCache.ItemChemical<ChemicalDissolutionRecipe>> DISSOLUTION = new WrappedMekanismRecipeType<>(
            MekanismRecipeType.DISSOLUTION);
    public static final WrappedMekanismRecipeType<SingleItemChemicalRecipeInput, ItemStackChemicalToItemStackRecipe, InputRecipeCache.ItemChemical<ItemStackChemicalToItemStackRecipe>> COMPRESSING = new WrappedMekanismRecipeType<>(
            MekanismRecipeType.COMPRESSING);
    public static final WrappedMekanismRecipeType<SingleItemChemicalRecipeInput, ItemStackChemicalToItemStackRecipe, InputRecipeCache.ItemChemical<ItemStackChemicalToItemStackRecipe>> PURIFYING = new WrappedMekanismRecipeType<>(
            MekanismRecipeType.PURIFYING);
    public static final WrappedMekanismRecipeType<SingleItemChemicalRecipeInput, ItemStackChemicalToItemStackRecipe, InputRecipeCache.ItemChemical<ItemStackChemicalToItemStackRecipe>> INJECTING = new WrappedMekanismRecipeType<>(
            MekanismRecipeType.INJECTING);
    public static final WrappedMekanismRecipeType<SingleItemChemicalRecipeInput, NucleosynthesizingRecipe, InputRecipeCache.ItemChemical<NucleosynthesizingRecipe>> NUCLEOSYNTHESIZING = new WrappedMekanismRecipeType<>(
            MekanismRecipeType.NUCLEOSYNTHESIZING);
    public static final WrappedMekanismRecipeType<SingleRecipeInput, ItemStackToEnergyRecipe, InputRecipeCache.SingleItem<ItemStackToEnergyRecipe>> ENERGY_CONVERSION = new WrappedMekanismRecipeType<>(
            MekanismRecipeType.ENERGY_CONVERSION);
    public static final WrappedMekanismRecipeType<SingleRecipeInput, ItemStackToChemicalRecipe, SingleItem<ItemStackToChemicalRecipe>> CHEMICAL_CONVERSION = new WrappedMekanismRecipeType<>(
            MekanismRecipeType.CHEMICAL_CONVERSION);
    public static final WrappedMekanismRecipeType<SingleRecipeInput, ItemStackToChemicalRecipe, InputRecipeCache.SingleItem<ItemStackToChemicalRecipe>> OXIDIZING = new WrappedMekanismRecipeType<>(
            MekanismRecipeType.OXIDIZING);
    public static final WrappedMekanismRecipeType<SingleRecipeInput, ItemStackToChemicalRecipe, InputRecipeCache.SingleItem<ItemStackToChemicalRecipe>> PIGMENT_EXTRACTING = new WrappedMekanismRecipeType<>(
            MekanismRecipeType.PIGMENT_EXTRACTING);
    public static final WrappedMekanismRecipeType<BiChemicalRecipeInput, ChemicalChemicalToChemicalRecipe, InputRecipeCache.EitherSideChemical<ChemicalChemicalToChemicalRecipe>> PIGMENT_MIXING = new WrappedMekanismRecipeType<>(
            MekanismRecipeType.PIGMENT_MIXING);
    public static final WrappedMekanismRecipeType<SingleItemChemicalRecipeInput, ItemStackChemicalToItemStackRecipe, InputRecipeCache.ItemChemical<ItemStackChemicalToItemStackRecipe>> METALLURGIC_INFUSING = new WrappedMekanismRecipeType<>(
            MekanismRecipeType.METALLURGIC_INFUSING);
    public static final WrappedMekanismRecipeType<SingleItemChemicalRecipeInput, ItemStackChemicalToItemStackRecipe, InputRecipeCache.ItemChemical<ItemStackChemicalToItemStackRecipe>> PAINTING = new WrappedMekanismRecipeType<>(
            MekanismRecipeType.PAINTING);
    public static final WrappedMekanismRecipeType<ReactionRecipeInput, PressurizedReactionRecipe, InputRecipeCache.ItemFluidChemical<PressurizedReactionRecipe>> REACTION = new WrappedMekanismRecipeType<>(
            MekanismRecipeType.REACTION);
    public static final WrappedMekanismRecipeType<RotaryRecipeInput, RotaryRecipe, RotaryInputRecipeCache> ROTARY = new WrappedMekanismRecipeType<>(
            MekanismRecipeType.ROTARY);
    public static final WrappedMekanismRecipeType<SingleRecipeInput, SawmillRecipe, InputRecipeCache.SingleItem<SawmillRecipe>> SAWING = new WrappedMekanismRecipeType<>(
            MekanismRecipeType.SAWING);

}
