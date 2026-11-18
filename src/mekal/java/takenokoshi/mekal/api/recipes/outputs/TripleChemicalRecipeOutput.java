package takenokoshi.mekal.api.recipes.outputs;

import org.jetbrains.annotations.NotNull;

import mekanism.api.chemical.ChemicalStack;

public record TripleChemicalRecipeOutput(@NotNull ChemicalStack main, @NotNull ChemicalStack secondary,
        ChemicalStack tertiary) {
}
