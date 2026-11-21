package takenokoshi.mekal.api.recipes.basic;

import java.util.List;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.recipes.ItemStackChemicalToItemStackRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;

public abstract class BasicItemStackChemicalToItemStackIngredientRecipe extends ItemStackChemicalToItemStackRecipe {

    private final RecipeType<? extends ItemStackChemicalToItemStackRecipe> recipeType;
    protected final ItemStackIngredient itemInput, output;
    protected final ChemicalStackIngredient chemicalInput;
    private final boolean perTickUsage;

    public BasicItemStackChemicalToItemStackIngredientRecipe(
            ItemStackIngredient itemInput,
            ChemicalStackIngredient chemicalInput,
            ItemStackIngredient output,
            boolean perTickUsage,
            RecipeType<? extends ItemStackChemicalToItemStackRecipe> recipeType) {
        this.recipeType = recipeType;
        this.itemInput = itemInput;
        this.output = output;
        this.chemicalInput = chemicalInput;
        this.perTickUsage = perTickUsage;
    }

    @Override
    public RecipeType<? extends ItemStackChemicalToItemStackRecipe> getType() {
        return recipeType;
    }

    @Override
    public ItemStackIngredient getItemInput() {
        return itemInput;
    }

    @Override
    public ChemicalStackIngredient getChemicalInput() {
        return chemicalInput;
    }

    @Override
    @Contract(value = "_, _ -> new", pure = true)
    public ItemStack getOutput(ItemStack inputItem, ChemicalStack inputChemical) {
        List<ItemStack> outputRepresentations = output.getRepresentations();
        return outputRepresentations.isEmpty() ? ItemStack.EMPTY : outputRepresentations.getFirst();
    }

    @Override
    public final boolean perTickUsage() {
        return perTickUsage;
    }

    @Override
    public boolean test(ItemStack itemStack, ChemicalStack chemicalStack) {
        return itemInput.test(itemStack) && chemicalInput.test(chemicalStack);
    }

    @NotNull
    @Override
    public ItemStack getResultItem(@NotNull HolderLookup.Provider provider) {
        List<ItemStack> outputRepresentations = output.getRepresentations();
        return outputRepresentations.isEmpty() ? ItemStack.EMPTY : outputRepresentations.getFirst();
    }

    @Override
    public List<ItemStack> getOutputDefinition() {
        return output.getRepresentations();
    }

    public ItemStackIngredient getOutputRaw() {
        return output;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BasicItemStackChemicalToItemStackIngredientRecipe other = (BasicItemStackChemicalToItemStackIngredientRecipe) o;
        return itemInput.equals(other.itemInput) && chemicalInput.equals(other.chemicalInput)
                && output.equals(other.output);
    }

    @Override
    public int hashCode() {
        return itemInput.hashCode() * 961 * chemicalInput.hashCode() * 31 + output.hashCode();
    }

    @Override
    public boolean isIncomplete() {
        return super.isIncomplete() || output.hasNoMatchingInstances();
    }

    @Override
    public void logMissingTags() {
        super.logMissingTags();
        output.logMissingTags();
    }
}
