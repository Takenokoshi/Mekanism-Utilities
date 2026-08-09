package com.takenokoshi.mekut.recipe.recipe.basic;

import java.util.List;
import java.util.Optional;

import com.takenokoshi.mekut.recipe.recipe.prefab.ItemStackListFluidChemicalToItemFluidChemicalRecipe;
import com.takenokoshi.mekut.registries.MekUtRecipeSerializers;
import com.takenokoshi.mekut.registries.MekUtRecipeTypes;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.PressurizedReactionRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.fluids.FluidStack;

public class BasicSmallDigitalReactionChamberRecipe extends ItemStackListFluidChemicalToItemFluidChemicalRecipe {

    public BasicSmallDigitalReactionChamberRecipe(
            List<ItemStackIngredient> itemInputs, FluidStackIngredient fluidInput,
            ChemicalStackIngredient chemicalInput, ItemStack outputItem, FluidStack outputFluid,
            ChemicalStack outputChemical, long energyRequired, int duration) {
        super(MekUtRecipeTypes.SMALL_DIGITAL_REACTION_CHAMBER.get(), itemInputs, fluidInput, chemicalInput, outputItem, outputFluid,
                outputChemical, energyRequired,
                duration);
        if (itemInputs.size() > 9) {
            throw new IllegalStateException("itemInputs size can't be larger then 9");
        }
    }

    public BasicSmallDigitalReactionChamberRecipe(
            List<ItemStackIngredient> itemInputs, Optional<FluidStackIngredient> fluidInput,
            Optional<ChemicalStackIngredient> chemicalInput, ItemStack outputItem, FluidStack outputFluid,
            ChemicalStack outputChemical, long energyRequired, int duration) {
        this(itemInputs, fluidInput.orElse(null), chemicalInput.orElse(null), outputItem, outputFluid, outputChemical,
                energyRequired, duration);
    }

    public static BasicSmallDigitalReactionChamberRecipe convertPRC(PressurizedReactionRecipe recipe) {
        return new BasicSmallDigitalReactionChamberRecipe(List.of(recipe.getInputSolid()), recipe.getInputFluid(),
                recipe.getInputChemical(), recipe.getOutputDefinition().get(0).item(), FluidStack.EMPTY,
                recipe.getOutputDefinition().get(0).chemical(), recipe.getEnergyRequired(), recipe.getDuration());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MekUtRecipeSerializers.SMALL_DIGITAL_REACTION_CHAMBER.get();
    }

}
