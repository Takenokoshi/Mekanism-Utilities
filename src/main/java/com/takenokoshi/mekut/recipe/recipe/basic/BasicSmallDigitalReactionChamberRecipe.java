package com.takenokoshi.mekut.recipe.recipe.basic;

import java.util.List;
import java.util.Optional;

import com.takenokoshi.mekut.recipe.recipe.prefab.ItemStackListFluidChemicalToItemFluidChemicalRecipe;
import com.takenokoshi.mekut.registries.MekUtRecipeSerializers;
import com.takenokoshi.mekut.registries.MekUtRecipeTypes;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.math.MathUtils;
import mekanism.api.recipes.PressurizedReactionRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.config.MekanismConfig;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.fluids.FluidStack;
import net.pedroksl.advanced_ae.recipes.ReactionChamberRecipe;

public class BasicSmallDigitalReactionChamberRecipe extends ItemStackListFluidChemicalToItemFluidChemicalRecipe {

    public BasicSmallDigitalReactionChamberRecipe(
            List<ItemStackIngredient> itemInputs, FluidStackIngredient fluidInput,
            ChemicalStackIngredient chemicalInput, ItemStack outputItem, FluidStack outputFluid,
            ChemicalStack outputChemical, long energyRequired, int duration) {
        super(MekUtRecipeTypes.SMALL_DIGITAL_REACTION_CHAMBER.get(), itemInputs, fluidInput, chemicalInput, outputItem, outputFluid,
                outputChemical, energyRequired,
                duration);
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

    public static BasicSmallDigitalReactionChamberRecipe convertAAE(ReactionChamberRecipe recipe) {
        var fluid = recipe.getFluid();
        return new BasicSmallDigitalReactionChamberRecipe(
                recipe.getInputs().stream()
                        .map(value -> IngredientCreatorAccess.item().from(value.getIngredient(), value.getAmount()))
                        .toList(),
                fluid == null ? null
                        : IngredientCreatorAccess.fluid().from(fluid.getIngredient(), fluid.getAmount()),
                null,
                recipe.getResultItem(), recipe.getResultFluid(), ChemicalStack.EMPTY,
                MathUtils.clampToLong(recipe.getEnergy() * MekanismConfig.general.forgeConversionRate.getAsDouble()),
                100);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MekUtRecipeSerializers.SMALL_DIGITAL_REACTION_CHAMBER.get();
    }

}
