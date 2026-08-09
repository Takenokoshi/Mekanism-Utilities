package com.takenokoshi.mekut.recipe.recipe.basic;

import java.util.List;
import java.util.Optional;

import com.takenokoshi.mekut.recipe.recipe.prefab.ItemStackListFluidChemicalToItemRecipe;
import com.takenokoshi.mekut.registries.MekUtRecipeSerializers;
import com.takenokoshi.mekut.registries.MekUtRecipeTypes;

import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class BasicSmallDigitalAssemblerRecipe extends ItemStackListFluidChemicalToItemRecipe {

    public BasicSmallDigitalAssemblerRecipe(
            List<ItemStackIngredient> itemInputs, FluidStackIngredient fluidInput,
            ChemicalStackIngredient chemicalInput, ItemStack outputItem, long energyRequired) {
        super(MekUtRecipeTypes.SMALL_DIGITAL_ASSEMBLER.get(), itemInputs, fluidInput, chemicalInput, outputItem,
                energyRequired);
        if (itemInputs.size() > 9) {
            throw new IllegalStateException("itemInputs size can't be larger then 9");
        }
    }

    public BasicSmallDigitalAssemblerRecipe(
            List<ItemStackIngredient> itemInputs, Optional<FluidStackIngredient> fluidInput,
            Optional<ChemicalStackIngredient> chemicalInput, ItemStack outputItem, long energyRequired) {
        this(itemInputs, fluidInput.orElse(null), chemicalInput.orElse(null), outputItem, energyRequired);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MekUtRecipeSerializers.SMALL_DIGITAL_ASSEMBLER.get();
    }

}
