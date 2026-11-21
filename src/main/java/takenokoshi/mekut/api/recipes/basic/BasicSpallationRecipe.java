package takenokoshi.mekut.api.recipes.basic;

import java.util.Collections;
import java.util.List;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import takenokoshi.mekut.api.recipes.MekUtRecipeSerializers;
import takenokoshi.mekut.api.recipes.MekUtRecipeTypes;
import takenokoshi.mekut.api.recipes.SpallationRecipe;

public final class BasicSpallationRecipe extends SpallationRecipe {

    private final ChemicalStackIngredient input;
    private final List<ChemicalStack> output;
    private final double protonRequired, neutronProduction;

    public BasicSpallationRecipe(ChemicalStackIngredient input,
            List<ChemicalStack> output,
            double protonRequired,
            double neutronProduction) {
        this.input = input;
        this.output = output;
        this.protonRequired = protonRequired;
        this.neutronProduction = neutronProduction;
    }

    @Override
    public RecipeSerializer<BasicSpallationRecipe> getSerializer() {
        return MekUtRecipeSerializers.SPALLATION.value();
    }

    @Override
    public RecipeType<SpallationRecipe> getType() {
        return MekUtRecipeTypes.TYPE_SPALLATION.value();
    }

    @Override
    public double getNeutronProduction() {
        return neutronProduction;
    }

    @Override
    public double getProtonRequired() {
        return protonRequired;
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
    public List<List<ChemicalStack>> getOutputDefinition() {
        return Collections.singletonList(output);
    }

    @Override
    public List<ChemicalStack> getOutput(ChemicalStack input) {
        return output.stream().map(ChemicalStack::copy).toList();
    }

    public List<ChemicalStack> getOutputRaw() {
        return output;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BasicSpallationRecipe other = (BasicSpallationRecipe) o;
        return protonRequired == other.protonRequired
                && neutronProduction == other.neutronProduction
                && output.equals(other.output)
                && input.equals(other.input);
    }

    @Override
    public int hashCode() {
        int hash = input.hashCode();
        hash = hash * 31 + output.hashCode();
        hash = hash * 31 + Double.hashCode(protonRequired);
        return hash * 31 + Double.hashCode(neutronProduction);
    }
}
