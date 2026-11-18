package takenokoshi.mekal.api.recipes;

import java.util.List;
import java.util.function.Predicate;

import org.jetbrains.annotations.Contract;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.vanilla_input.SingleChemicalRecipeInput;
import net.minecraft.world.level.Level;
import takenokoshi.mekal.api.recipes.outputs.TripleChemicalRecipeOutput;

public abstract class ChemicalToTripleChemicalRecipe extends MekanismRecipe<SingleChemicalRecipeInput>
        implements Predicate<ChemicalStack> {

    @Override
    public abstract boolean test(ChemicalStack chemicalStack);

    @Override
    public boolean matches(SingleChemicalRecipeInput input, Level level) {
        return !isIncomplete() && test(input.chemical());
    }

    public abstract ChemicalStackIngredient getInput();

    public abstract List<TripleChemicalRecipeOutput> getOutputDefinition();

    @Contract(value = "_ -> new", pure = true)
    public abstract TripleChemicalRecipeOutput getOutput(ChemicalStack input);

    @Override
    public boolean isIncomplete() {
        return getInput().hasNoMatchingInstances();
    }

    @Override
    public void logMissingTags() {
        getInput().logMissingTags();
    }
}
