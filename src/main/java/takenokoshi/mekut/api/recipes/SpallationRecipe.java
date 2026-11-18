package takenokoshi.mekut.api.recipes;

import takenokoshi.mekal.api.recipes.ChemicalToTripleChemicalRecipe;

public abstract class SpallationRecipe extends ChemicalToTripleChemicalRecipe {
    public abstract double getProducedNeutron();

    public abstract double getRequiredProton();
}
