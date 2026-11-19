package takenokoshi.mekal.api.recipes;

import java.util.List;
import java.util.function.BiPredicate;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.vanilla_input.SingleFluidChemicalRecipeInput;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import takenokoshi.mekal.api.recipes.outputs.DoubleChemicalRecipeOutput;

public abstract class FluidChemicalToDoubleChemicalRecipe extends MekanismRecipe<SingleFluidChemicalRecipeInput>
        implements BiPredicate<@NotNull FluidStack, ChemicalStack> {

    @Override
    public abstract boolean test(FluidStack fluidStack, ChemicalStack chemicalStack);

    @Override
    public boolean matches(SingleFluidChemicalRecipeInput input, Level level) {
        return !isIncomplete() && test(input.fluid(), input.chemical());
    }
    public abstract FluidStackIngredient getFluidInput();

    public abstract ChemicalStackIngredient getChemicalInput();

    public abstract List<DoubleChemicalRecipeOutput> getOutputDefinition();

    @Contract(value = "_, _ -> new", pure = true)
    public abstract DoubleChemicalRecipeOutput getOutput(FluidStack fluidStack, ChemicalStack chemicalStack);

    @Override
    public boolean isIncomplete() {
        return getFluidInput().hasNoMatchingInstances() || getChemicalInput().hasNoMatchingInstances();
    }

    @Override
    public void logMissingTags() {
        getFluidInput().logMissingTags();
        getChemicalInput().logMissingTags();
    }
}
