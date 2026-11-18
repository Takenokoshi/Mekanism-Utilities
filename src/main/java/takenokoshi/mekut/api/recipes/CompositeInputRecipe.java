package takenokoshi.mekut.api.recipes;

import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.InputIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.TriPredicate;
import net.neoforged.neoforge.fluids.FluidStack;

public abstract class CompositeInputRecipe extends MekanismRecipe<RecipeInput>
        implements TriPredicate<List<ItemStack>, FluidStack, ChemicalStack> {

    public abstract List<ItemStackIngredient> getItemInputs();

    @Nullable
    public abstract FluidStackIngredient getFluidInput();

    public Optional<FluidStackIngredient> getFluidInputAsOptional() {
        return Optional.ofNullable(getFluidInput());
    }

    @Nullable
    public abstract ChemicalStackIngredient getChemicalInput();

    public Optional<ChemicalStackIngredient> getChemicalInputAsOptional() {
        return Optional.ofNullable(getChemicalInput());
    }

    /** Vanilla system check is not supported. */
    @Override
    public boolean matches(RecipeInput input, Level level) {
        return false;
    }

    @Override
    public boolean isIncomplete() {
        for (ItemStackIngredient itemInput : getItemInputs()) {
            if (itemInput.hasNoMatchingInstances()) {
                return true;
            }
        }
        FluidStackIngredient fluidInput = getFluidInput();
        if (fluidInput != null && fluidInput.hasNoMatchingInstances()) {
            return true;
        }
        ChemicalStackIngredient chemicalInput = getChemicalInput();
        if (chemicalInput != null && chemicalInput.hasNoMatchingInstances()) {
            return true;
        }
        return false;
    }

    @Override
    public void logMissingTags() {
        getItemInputs().forEach(InputIngredient::logMissingTags);
        getFluidInputAsOptional().ifPresent(InputIngredient::logMissingTags);
        getChemicalInputAsOptional().ifPresent(InputIngredient::logMissingTags);
    }

}
