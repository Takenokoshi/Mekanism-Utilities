package takenokoshi.mekut.datagen.recipe.builder;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.datagen.recipe.MekanismRecipeBuilder;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import net.minecraft.world.item.crafting.Recipe;
import takenokoshi.mekut.api.recipes.basic.BasicSpallationRecipe;

public class SpallationRecipeBuilder extends MekanismRecipeBuilder<SpallationRecipeBuilder> {

    protected final ChemicalStackIngredient input;
    protected final ChemicalStack mainOutput, secondaryOutput, tertiaryOutput;
    protected double requiredProton, producedNeutron;

    private SpallationRecipeBuilder(ChemicalStackIngredient input,
            ChemicalStack mainOutput,
            ChemicalStack secondaryOutput,
            ChemicalStack tertiaryOutput) {
        this.input = input;
        this.mainOutput = mainOutput;
        this.secondaryOutput = secondaryOutput;
        this.tertiaryOutput = tertiaryOutput;
        this.requiredProton = 1.0D;
        this.producedNeutron = 1.0D;
    }

    public static SpallationRecipeBuilder spallation(ChemicalStackIngredient input,
            ChemicalStack mainOutput,
            ChemicalStack secondaryOutput,
            ChemicalStack tertiaryOutput) {
        return new SpallationRecipeBuilder(input, mainOutput, secondaryOutput, tertiaryOutput);
    }

    public SpallationRecipeBuilder proton(double requiredProton) {
        this.requiredProton = requiredProton;
        return this;
    }

    public SpallationRecipeBuilder neutron(double producedNeutron) {
        this.producedNeutron = producedNeutron;
        return this;
    }

    @Override
    protected Recipe<?> asRecipe() {
        return new BasicSpallationRecipe(input, mainOutput, secondaryOutput, tertiaryOutput, requiredProton,
                producedNeutron);
    }

}
