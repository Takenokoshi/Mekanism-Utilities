package takenokoshi.mekal.api.recipes.basic;

import java.util.Collections;
import java.util.List;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import net.minecraft.world.item.crafting.RecipeType;
import takenokoshi.mekal.api.recipes.ChemicalToTripleChemicalRecipe;
import takenokoshi.mekal.api.recipes.outputs.TripleChemicalRecipeOutput;

public abstract class BasicChemicalToTripleChemicalRecipe extends ChemicalToTripleChemicalRecipe {

    private final RecipeType<ChemicalToTripleChemicalRecipe> recipeType;
    protected final ChemicalStack mainOutput, secondaryOutput, tertiaryOutput;
    private final ChemicalStackIngredient input;

    public BasicChemicalToTripleChemicalRecipe(
            RecipeType<ChemicalToTripleChemicalRecipe> recipeType,
            ChemicalStack mainOutput,
            ChemicalStack secondaryOutput,
            ChemicalStack tertiaryOutput,
            ChemicalStackIngredient input) {
        this.recipeType = recipeType;
        this.mainOutput = mainOutput;
        this.secondaryOutput = secondaryOutput;
        this.tertiaryOutput = tertiaryOutput;
        this.input = input;
    }

    @Override
    public final RecipeType<ChemicalToTripleChemicalRecipe> getType() {
        return recipeType;
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
        BasicChemicalToTripleChemicalRecipe other = (BasicChemicalToTripleChemicalRecipe) o;
        return mainOutput.equals(other.mainOutput)
                && secondaryOutput.equals(other.secondaryOutput)
                && tertiaryOutput.equals(other.tertiaryOutput)
                && input.equals(other.input);
    }

    @Override
    public int hashCode() {
        return mainOutput.hashCode() * 29791 + secondaryOutput.hashCode() * 961 + tertiaryOutput.hashCode() * 31
                + input.hashCode();
    }
}
