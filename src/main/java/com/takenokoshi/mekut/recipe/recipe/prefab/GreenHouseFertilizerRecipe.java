package com.takenokoshi.mekut.recipe.recipe.prefab;

import java.util.List;

import com.takenokoshi.mekut.recipe.output.MekUtChanceOutput;

import mekanism.api.math.MathUtils;
import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.vanilla_input.SingleFluidRecipeInput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public abstract class GreenHouseFertilizerRecipe extends MekanismRecipe<SingleFluidRecipeInput> {

    protected final RecipeType<? extends GreenHouseFertilizerRecipe> recipeType;
    public final FluidStackIngredient fertilizerIngredient;
    public final int outputMultiplier;
    public final double durationMultiplier;

    public GreenHouseFertilizerRecipe(RecipeType<? extends GreenHouseFertilizerRecipe> recipeType,
            FluidStackIngredient fertilizerInredient, int outputMultiplier, double durationMultiplier) {
        this.recipeType = recipeType;
        this.fertilizerIngredient = fertilizerInredient;
        this.outputMultiplier = outputMultiplier;
        this.durationMultiplier = durationMultiplier;
    }

    @Override
    public RecipeType<?> getType() {
        return recipeType;
    }

    public FluidStackIngredient getFertilizerIngredient() {
        return fertilizerIngredient;
    }

    public int getOutputMultiplier() {
        return outputMultiplier;
    }

    public double getDurationMultiplier() {
        return durationMultiplier;
    }

    public List<MekUtChanceOutput> multiplyOutputs(List<MekUtChanceOutput> outputs) {
        return outputs.stream().map(this::multiplyOutput).toList();
    }

    public MekUtChanceOutput multiplyOutput(MekUtChanceOutput output) {
        ItemStack value = output.value();
        int p = value.getCount();
        return new MekUtChanceOutput(
                value.copyWithCount(p > 0x7fffffff / outputMultiplier ? 0x7fffffff : p * outputMultiplier),
                output.chance());
    }

    public int multiplyDuration(int duration) {
        return MathUtils.clampToInt(duration * durationMultiplier);
    }

    @Override
    public boolean isIncomplete() {
        return fertilizerIngredient.hasNoMatchingInstances();
    }

    @Override
    public boolean matches(SingleFluidRecipeInput arg0, Level arg1) {
        return !isIncomplete() && fertilizerIngredient.test(arg0.fluid());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o != null && o.getClass() == this.getClass()) {
            GreenHouseFertilizerRecipe other = (GreenHouseFertilizerRecipe) o;
            return this.outputMultiplier == other.outputMultiplier
                    && this.durationMultiplier == other.durationMultiplier
                    && this.fertilizerIngredient.equals(other.fertilizerIngredient);
        } else {
            return false;
        }
    }

    @Override
    public void logMissingTags() {
        fertilizerIngredient.logMissingTags();
    }

    @Override
    public int hashCode() {
        int result = fertilizerIngredient.hashCode();
        result = result * 31 + outputMultiplier;
        result = result * 31 + Double.hashCode(durationMultiplier);
        return result;
    }
}
