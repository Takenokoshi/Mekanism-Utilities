package takenokoshi.mekal.api.recipes.basic;

import java.util.Collections;
import java.util.List;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.fluids.FluidStack;
import takenokoshi.mekal.api.recipes.FluidChemicalToDoubleChemicalRecipe;
import takenokoshi.mekal.api.recipes.outputs.DoubleChemicalRecipeOutput;

public abstract class BasicFluidChemicalToDoubleChemicalRecipe extends FluidChemicalToDoubleChemicalRecipe {

    private final RecipeType<? extends FluidChemicalToDoubleChemicalRecipe> recipeType;
    private final FluidStackIngredient fluidInput;
    private final ChemicalStackIngredient chemicalInput;
    protected final ChemicalStack mainOutput, secondaryOutput;

    public BasicFluidChemicalToDoubleChemicalRecipe(
            RecipeType<? extends FluidChemicalToDoubleChemicalRecipe> recipeType,
            FluidStackIngredient fluidInput,
            ChemicalStackIngredient chemicalInput, ChemicalStack mainOutput, ChemicalStack secondaryOutput) {
        this.recipeType = recipeType;
        this.fluidInput = fluidInput;
        this.chemicalInput = chemicalInput;
        this.mainOutput = mainOutput;
        this.secondaryOutput = secondaryOutput;
    }

    @Override
    public final RecipeType<? extends FluidChemicalToDoubleChemicalRecipe> getType() {
        return recipeType;
    }

    @Override
    public boolean test(FluidStack fluidStack, ChemicalStack chemicalStack) {
        return fluidInput.test(fluidStack) && chemicalInput.test(chemicalStack);
    }

    @Override
    public FluidStackIngredient getFluidInput() {
        return fluidInput;
    }

    @Override
    public ChemicalStackIngredient getChemicalInput() {
        return chemicalInput;
    }

    @Override
    public List<DoubleChemicalRecipeOutput> getOutputDefinition() {
        return Collections.singletonList(new DoubleChemicalRecipeOutput(mainOutput, secondaryOutput));
    }

    @Override
    public DoubleChemicalRecipeOutput getOutput(FluidStack fluidStack, ChemicalStack chemicalStack) {
        return new DoubleChemicalRecipeOutput(mainOutput.copy(), secondaryOutput.copy());
    }

    public ChemicalStack getMainOutputRaw() {
        return mainOutput;
    }

    public ChemicalStack getSecondaryOutputRaw() {
        return secondaryOutput;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BasicFluidChemicalToDoubleChemicalRecipe other = (BasicFluidChemicalToDoubleChemicalRecipe) o;
        return fluidInput.equals(other.fluidInput)
                && chemicalInput.equals(other.chemicalInput)
                && mainOutput.equals(other.mainOutput)
                && secondaryOutput.equals(other.secondaryOutput);
    }

    @Override
    public int hashCode() {
        int result = fluidInput.hashCode();
        result = 31 * result + chemicalInput.hashCode();
        result = 31 * result + mainOutput.hashCode();
        result = 31 * result + secondaryOutput.hashCode();
        return result;
    }
}
