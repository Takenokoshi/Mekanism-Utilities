package takenokoshi.mekut.datagen.recipe.builder;

import java.util.ArrayList;
import java.util.List;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.datagen.recipe.MekanismRecipeBuilder;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import net.minecraft.world.item.crafting.Recipe;
import takenokoshi.mekut.api.recipes.basic.BasicSpallationRecipe;

public class SpallationRecipeBuilder extends MekanismRecipeBuilder<SpallationRecipeBuilder> {

    protected final ChemicalStackIngredient input;
    private final List<ChemicalStack> output;
    protected double requiredProton, producedNeutron;

    private SpallationRecipeBuilder(ChemicalStackIngredient input) {
        this.input = input;
        this.output = new ArrayList<>();
        this.requiredProton = 1.0D;
        this.producedNeutron = 1.0D;
    }

    public static SpallationRecipeBuilder spallation(ChemicalStackIngredient input) {
        return new SpallationRecipeBuilder(input);
    }

    public SpallationRecipeBuilder addOutput(ChemicalStack stack) {
        output.add(stack);
        return this;
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
        return new BasicSpallationRecipe(input, output, requiredProton,
                producedNeutron);
    }

}
