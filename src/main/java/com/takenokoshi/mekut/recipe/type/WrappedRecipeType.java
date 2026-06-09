package com.takenokoshi.mekut.recipe.type;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.glodblock.github.extendedae.recipe.CircuitCutterRecipe;
import com.glodblock.github.extendedae.recipe.CrystalAssemblerRecipe;
import com.takenokoshi.mekut.recipe.inputcache.ItemStackListFluidInputRecipeCache;
import com.takenokoshi.mekut.recipe.inputcache.MUSingleInputRecipeCache;
import appeng.core.AppEng;
import appeng.recipes.AERecipeTypes;
import appeng.recipes.handlers.ChargerRecipe;
import mekanism.common.recipe.lookup.cache.IInputRecipeCache;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.pedroksl.advanced_ae.recipes.ReactionChamberRecipe;

// For already registered recipetype such as smelting(vanilla) or charging(AE2).
public class WrappedRecipeType<VANILLA_INPUT extends RecipeInput, RECIPE extends Recipe<VANILLA_INPUT>, INPUT_CACHE extends IInputRecipeCache>
        extends MekUtRecipeType<VANILLA_INPUT, RECIPE, INPUT_CACHE> {

    protected final RecipeType<RECIPE> wrappedType;

    public WrappedRecipeType(ResourceLocation name,
            Function<MekUtRecipeType<VANILLA_INPUT, RECIPE, INPUT_CACHE>, INPUT_CACHE> inputCacheCreator,
            RecipeType<RECIPE> wrappedType) {
        super(name, inputCacheCreator);
        this.wrappedType = wrappedType;
    }

    @Override
    protected @NotNull List<RecipeHolder<RECIPE>> getRecipesUncached(@NotNull RecipeManager recipeManager,
            @Nullable RegistryAccess registryAccess) {
        return recipeManager.getAllRecipesFor(wrappedType);
    }

    public static final WrappedRecipeType<SingleRecipeInput, SmeltingRecipe, MUSingleInputRecipeCache.MUSingleItem<SmeltingRecipe>> VANILLA_SMELTING = new WrappedRecipeType<>(
            ResourceLocation.withDefaultNamespace("smelting"),
            type -> new MUSingleInputRecipeCache.MUSingleItem<>(type, recipe -> recipe
                    .getIngredients()
                    .stream()
                    .flatMap(ingredient -> Arrays.stream(ingredient.getItems()))
                    .map(ItemStack::getItem)
                    .toList()),
            RecipeType.SMELTING);

    public static final WrappedRecipeType<RecipeInput, ChargerRecipe, MUSingleInputRecipeCache.MUSingleItem<ChargerRecipe>> AE2_CHARGER = new WrappedRecipeType<>(
            AppEng.makeId("charger"),
            type -> new MUSingleInputRecipeCache.MUSingleItem<>(type, recipe -> Arrays
                    .stream(recipe.getIngredient().getItems())
                    .map(ItemStack::getItem)
                    .toList()),
            AERecipeTypes.CHARGER);

    public static final WrappedRecipeType<RecipeInput, CircuitCutterRecipe, MUSingleInputRecipeCache.MUSingleItem<CircuitCutterRecipe>> EXTENDEDAE_CIRCUIT_CUTTER = new WrappedRecipeType<>(
            CircuitCutterRecipe.ID,
            type -> new MUSingleInputRecipeCache.MUSingleItem<>(type, recipe -> Arrays
                    .stream(recipe.getInput().getIngredient().getItems())
                    .map(ItemStack::getItem)
                    .toList()),
            CircuitCutterRecipe.TYPE) {
        @Override
        protected boolean isRecipeComplete(CircuitCutterRecipe recipe) {
            return !recipe.getInput().getIngredient().isEmpty();
        }
    };

    public static final WrappedRecipeType<RecipeInput, ReactionChamberRecipe, ItemStackListFluidInputRecipeCache<ReactionChamberRecipe>> ADVANCEDAE_REACTION_CHAMBER = new WrappedRecipeType<>(
            ReactionChamberRecipe.TYPE_ID,
            type -> new ItemStackListFluidInputRecipeCache<>(type,
                    recipe -> recipe.getInputs().stream()
                            .flatMap(item -> Arrays.stream(item.getIngredient().getItems()))
                            .map(ItemStack::getItem)
                            .toList(),
                    recipe -> {
                        var fluid = recipe.getFluid();
                        return fluid == null
                                ? List.of(Fluids.EMPTY)
                                : Arrays.stream(fluid.getIngredient().getStacks()).map(FluidStack::getFluid).toList();
                    },
                    recipe -> recipe.getInputs().size(),
                    (recipe, list) -> {
                        var recipeInputs = recipe.getInputs();
                        if (recipeInputs.size() != list.size()) {
                            return false;
                        }
                        boolean result = true;
                        for (int i = 0; i < recipeInputs.size() && result; i++) {
                            var input = recipeInputs.get(i);
                            boolean val = false;
                            for (int j = 0; j < list.size() && !val; j++) {
                                val |= input.test(list.get(j));
                            }
                            result &= val;
                        }
                        return result;
                    }),
            ReactionChamberRecipe.TYPE) {
        @Override
        protected boolean isRecipeComplete(ReactionChamberRecipe recipe) {
            return true;
        }
    };

    public static final WrappedRecipeType<RecipeInput, CrystalAssemblerRecipe, ItemStackListFluidInputRecipeCache<CrystalAssemblerRecipe>> EXTENDEDAE_CRYSTAL_ASSEMBLER = new WrappedRecipeType<>(
            CrystalAssemblerRecipe.ID,
            type -> new ItemStackListFluidInputRecipeCache<>(type,
                    recipe -> recipe.getInputs().stream()
                            .flatMap(item -> Arrays.stream(item.getIngredient().getItems()))
                            .map(ItemStack::getItem)
                            .toList(),
                    recipe -> {
                        var fluid = recipe.getFluid();
                        return fluid == null
                                ? List.of(Fluids.EMPTY)
                                : Arrays.stream(fluid.getIngredient().getStacks()).map(FluidStack::getFluid).toList();
                    },
                    recipe -> recipe.getInputs().size(),
                    (recipe, list) -> {
                        var recipeInputs = recipe.getInputs();
                        if (recipeInputs.size() != list.size()) {
                            return false;
                        }
                        boolean result = true;
                        for (int i = 0; i < recipeInputs.size() && result; i++) {
                            var input = recipeInputs.get(i).getIngredient();
                            boolean val = false;
                            for (int j = 0; j < list.size() && !val; j++) {
                                val |= input.test(list.get(j));
                            }
                            result &= val;
                        }
                        return result;
                    }),
            CrystalAssemblerRecipe.TYPE) {
        @Override
        protected boolean isRecipeComplete(CrystalAssemblerRecipe recipe) {
            return true;
        }
    };
}
