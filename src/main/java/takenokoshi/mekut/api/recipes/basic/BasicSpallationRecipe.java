package takenokoshi.mekut.api.recipes.basic;

import java.util.Collections;
import java.util.List;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import takenokoshi.mekal.api.recipes.outputs.TripleChemicalRecipeOutput;
import takenokoshi.mekut.api.recipes.SpallationRecipe;

public class BasicSpallationRecipe extends SpallationRecipe {

    protected final ChemicalStackIngredient input;
    protected final ChemicalStack mainOutput, secondaryOutput, tertiaryOutput;
    protected final double requiredProton, producedNeutron;

    public BasicSpallationRecipe(ChemicalStackIngredient input,
            ChemicalStack mainOutput,
            ChemicalStack secondaryOutput,
            ChemicalStack tertiaryOutput,
            double requiredProton,
            double producedNeutron) {
        this.input = input;
        this.mainOutput = mainOutput;
        this.secondaryOutput = secondaryOutput;
        this.tertiaryOutput = tertiaryOutput;
        this.requiredProton = requiredProton;
        this.producedNeutron = producedNeutron;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSerializer'");
    }

    @Override
    public RecipeType<?> getType() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getType'");
    }

    @Override
    public double getProducedNeutron() {
        return producedNeutron;
    }

    @Override
    public double getRequiredProton() {
        return requiredProton;
    }

    @Override
    public boolean test(ChemicalStack chemicalStack) {
        return input.test(chemicalStack);
    }

    @Override
    public ChemicalStackIngredient getInput() {
        return input;
    }

    @Override
    public List<TripleChemicalRecipeOutput> getOutputDefinition() {
        return Collections.singletonList(new TripleChemicalRecipeOutput(mainOutput, secondaryOutput, tertiaryOutput));
    }

    @Override
    public TripleChemicalRecipeOutput getOutput(ChemicalStack input) {
        return new TripleChemicalRecipeOutput(mainOutput.copy(), secondaryOutput.copy(), tertiaryOutput.copy());
    }

    public ChemicalStack getMainOutputRaw() {
        return mainOutput;
    }

    public ChemicalStack getSecondaryOutputRaw() {
        return secondaryOutput;
    }

    public ChemicalStack getTertiaryOutputRaw() {
        return tertiaryOutput;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BasicSpallationRecipe other = (BasicSpallationRecipe) o;
        return requiredProton == other.requiredProton
                && producedNeutron == other.producedNeutron
                && mainOutput.equals(other.mainOutput)
                && secondaryOutput.equals(other.secondaryOutput)
                && tertiaryOutput.equals(other.tertiaryOutput)
                && input.equals(other.input);
    }

    @Override
    public int hashCode() {
        int hash = input.hashCode();
        hash = hash * 31 + mainOutput.hashCode();
        hash = hash * 31 + secondaryOutput.hashCode();
        hash = hash * 31 + tertiaryOutput.hashCode();
        hash = hash * 31 + Double.hashCode(requiredProton);
        return hash * 31 + Double.hashCode(producedNeutron);
    }
}
