package takenokoshi.mekal.datagen.recipe.builder;

import com.mojang.datafixers.util.Function4;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.datagen.recipe.MekanismRecipeBuilder;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import net.minecraft.world.item.crafting.Recipe;
import takenokoshi.mekal.api.recipes.ChemicalToTripleChemicalRecipe;

public class ChemicalToTripleChemicalRecipeBuilder
        extends MekanismRecipeBuilder<ChemicalToTripleChemicalRecipeBuilder> {

    protected final Function4<ChemicalStackIngredient, ChemicalStack, ChemicalStack, ChemicalStack, ChemicalToTripleChemicalRecipe> factory;
    protected final ChemicalStackIngredient input;
    protected final ChemicalStack mainOutput, secondaryOutput, tertiaryOutput;

    protected ChemicalToTripleChemicalRecipeBuilder(
            Function4<ChemicalStackIngredient, ChemicalStack, ChemicalStack, ChemicalStack, ChemicalToTripleChemicalRecipe> factory,
            ChemicalStackIngredient input, ChemicalStack mainOutput, ChemicalStack secondaryOutput,
            ChemicalStack tertiaryOutput) {
        this.factory = factory;
        this.input = input;
        this.mainOutput = mainOutput;
        this.secondaryOutput = secondaryOutput;
        this.tertiaryOutput = tertiaryOutput;
    }

    @Override
    protected Recipe<?> asRecipe() {
        return factory.apply(input, mainOutput, secondaryOutput, tertiaryOutput);
    }

}
