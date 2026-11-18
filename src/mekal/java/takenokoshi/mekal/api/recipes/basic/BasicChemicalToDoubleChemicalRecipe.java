package takenokoshi.mekal.api.recipes.basic;

import java.util.Collections;
import java.util.List;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import net.minecraft.world.item.crafting.RecipeType;
import takenokoshi.mekal.api.recipes.ChemicalToDoubleChemicalRecipe;
import takenokoshi.mekal.api.recipes.outputs.DoubleChemicalRecipeOutput;

public abstract class BasicChemicalToDoubleChemicalRecipe extends ChemicalToDoubleChemicalRecipe {

    private final RecipeType<ChemicalToDoubleChemicalRecipe> recipeType;
    protected final ChemicalStack mainOutput, secondaryOutput;
    private final ChemicalStackIngredient input;

    public BasicChemicalToDoubleChemicalRecipe(RecipeType<ChemicalToDoubleChemicalRecipe> recipeType,
            ChemicalStack mainOutput, ChemicalStack secondaryOutput,
            ChemicalStackIngredient input) {
        this.recipeType = recipeType;
        this.mainOutput = mainOutput;
        this.secondaryOutput = secondaryOutput;
        this.input = input;
    }

    @Override
    public final RecipeType<ChemicalToDoubleChemicalRecipe> getType() {
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
    public List<DoubleChemicalRecipeOutput> getOutputDefinition() {
        return Collections.singletonList(new DoubleChemicalRecipeOutput(mainOutput.copy(), secondaryOutput.copy()));
    }

    public ChemicalStack getMainOutputRaw() {
        return mainOutput;
    }

    public ChemicalStack getSecondaryOutputRaw() {
        return secondaryOutput;
    }

    @Override
    public DoubleChemicalRecipeOutput getOutput(ChemicalStack input) {
        return new DoubleChemicalRecipeOutput(mainOutput, secondaryOutput);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BasicChemicalToDoubleChemicalRecipe other = (BasicChemicalToDoubleChemicalRecipe) o;
        return mainOutput.equals(other.mainOutput)
                && secondaryOutput.equals(other.secondaryOutput)
                && input.equals(other.input);
    }

    @Override
    public int hashCode() {
        return mainOutput.hashCode() * 961 + secondaryOutput.hashCode() * 31 + input.hashCode();
    }
}
