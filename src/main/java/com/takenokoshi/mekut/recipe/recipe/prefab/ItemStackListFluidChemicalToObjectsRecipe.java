package com.takenokoshi.mekut.recipe.recipe.prefab;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import mekanism.api.MekanismAPI;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.util.TriPredicate;
import net.neoforged.neoforge.fluids.FluidStack;

public abstract class ItemStackListFluidChemicalToObjectsRecipe extends MekanismRecipe<RecipeInput>
        implements TriPredicate<List<ItemStack>, FluidStack, ChemicalStack> {
    protected final RecipeType<? extends ItemStackListFluidChemicalToObjectsRecipe> recipeType;
    public final List<ItemStackIngredient> itemInputs;
    @Nullable
    public final FluidStackIngredient fluidInput;
    @Nullable
    public final ChemicalStackIngredient chemicalInput;

    protected ItemStackListFluidChemicalToObjectsRecipe(
            RecipeType<? extends ItemStackListFluidChemicalToObjectsRecipe> recipeType,
            List<ItemStackIngredient> itemInputs, FluidStackIngredient fluidInput,
            ChemicalStackIngredient chemicalInput) {
        this.recipeType = recipeType;
        this.itemInputs = List.copyOf(itemInputs);
        this.fluidInput = fluidInput;
        this.chemicalInput = chemicalInput;
    }

    public List<ItemStackIngredient> getItemInputs() {
        return itemInputs;
    }

    @Nullable
    public FluidStackIngredient getFluidInput() {
        return fluidInput;
    }

    public Optional<FluidStackIngredient> getFluidInputAsOptional() {
        return Optional.ofNullable(fluidInput);
    }

    @Nullable
    public ChemicalStackIngredient getChemicalInput() {
        return chemicalInput;
    }

    public Optional<ChemicalStackIngredient> getChemicalInputAsOptional() {
        return Optional.ofNullable(chemicalInput);
    }

    public boolean testFluid(FluidStack fluid) {
        if (fluidInput == null) {
            return fluid.isEmpty();
        } else {
            return fluidInput.testType(fluid);
        }
    }

    public boolean testChemical(ChemicalStack chemical) {
        if (chemicalInput == null) {
            return chemical.isEmpty();
        } else {
            return chemicalInput.testType(chemical);
        }
    }

    @Override
    public RecipeType<?> getType() {
        return recipeType;
    }

    @Override
    public boolean test(List<ItemStack> items, FluidStack fluid, ChemicalStack chemical) {
        if (!testFluid(fluid) || !testChemical(chemical)) {
            return false;
        }
        return testItem(items);
    }

    public boolean testItem(List<ItemStack> items) {
        if (itemInputs.size() != items.size()) {
            return false;
        }
        boolean[] listUsedCache = new boolean[itemInputs.size()];
        for (int i = 0; i < listUsedCache.length; i++) {
            ItemStackIngredient testing = itemInputs.get(i);
            boolean found = false;
            for (int j = 0; j < listUsedCache.length; j++) {
                if (listUsedCache[j]) {
                    continue;
                }
                if (testing.test(items.get(j))) {
                    found = true;
                    listUsedCache[j] = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isIncomplete() {
        if ((fluidInput != null && fluidInput.hasNoMatchingInstances())
                || (chemicalInput != null && chemicalInput.hasNoMatchingInstances())) {
            return true;
        }
        for (ItemStackIngredient itemStackIngredient : itemInputs) {
            if (itemStackIngredient.hasNoMatchingInstances()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean matches(RecipeInput arg0, Level arg1) {
        return false;
    }

    public List<Item> getIngredientItems() {
        return itemInputs.stream().flatMap(ing -> ing.getRepresentations().stream()).map(ItemStack::getItem).toList();
    }

    public List<Fluid> getIngredientFluids() {
        return fluidInput == null
                ? List.of(Fluids.EMPTY)
                : fluidInput.getRepresentations().stream().map(FluidStack::getFluid).toList();
    }

    public List<Chemical> getIngredientChemicals() {
        return chemicalInput == null
                ? List.of(MekanismAPI.EMPTY_CHEMICAL_HOLDER.value())
                : chemicalInput.getRepresentations().stream().map(ChemicalStack::getChemical).toList();
    }

}
