package com.takenokoshi.mekut.blockentity.interfaces.machine;

import mekanism.api.chemical.IChemicalTank;
import mekanism.api.recipes.ChemicalChemicalToChemicalRecipe;

public interface IBiChemicalToChemicalRecipeMachine
        extends IBiChemicalToObjectRecipeMachine<ChemicalChemicalToChemicalRecipe> {

    IChemicalTank getOutputTank();
}
