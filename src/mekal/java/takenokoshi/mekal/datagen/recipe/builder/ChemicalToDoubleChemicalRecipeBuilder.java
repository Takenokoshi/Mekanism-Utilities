package takenokoshi.mekal.datagen.recipe.builder;

import com.mojang.datafixers.util.Function3;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.datagen.recipe.MekanismRecipeBuilder;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import net.minecraft.world.item.crafting.Recipe;
import takenokoshi.mekal.api.recipes.ChemicalToDoubleChemicalRecipe;

public class ChemicalToDoubleChemicalRecipeBuilder
        extends MekanismRecipeBuilder<ChemicalToDoubleChemicalRecipeBuilder> {

    protected final Function3<ChemicalStackIngredient, ChemicalStack, ChemicalStack, ChemicalToDoubleChemicalRecipe> factory;
    protected final ChemicalStackIngredient input;
    protected final ChemicalStack mainOutput, secondaryOutput;

    protected ChemicalToDoubleChemicalRecipeBuilder(
            Function3<ChemicalStackIngredient, ChemicalStack, ChemicalStack, ChemicalToDoubleChemicalRecipe> factory,
            ChemicalStackIngredient input, ChemicalStack mainOutput, ChemicalStack secondaryOutput) {
        this.factory = factory;
        this.input = input;
        this.mainOutput = mainOutput;
        this.secondaryOutput = secondaryOutput;
    }

    @Override
    protected Recipe<?> asRecipe() {
        return factory.apply(input, mainOutput, secondaryOutput);
    }

}
