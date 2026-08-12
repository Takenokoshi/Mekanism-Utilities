package com.takenokoshi.mekut.recipe.recipe.prefab;

import java.util.Collections;
import java.util.List;

import com.takenokoshi.mekut.recipe.output.MekUtChanceOutput;
import com.takenokoshi.mekut.recipe.recipe.util.TriTypePredicate;

import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.TriPredicate;
import net.neoforged.neoforge.fluids.FluidStack;

public abstract class GreenHouseRecipe extends MekanismRecipe<RecipeInput>
        implements TriPredicate<ItemStack, ItemStack, FluidStack>, TriTypePredicate<ItemStack, ItemStack, FluidStack> {

    protected final RecipeType<? extends GreenHouseRecipe> recipeType;
    public final ItemStackIngredient cropIngredient;
    public final ItemStackIngredient soilIngredient;
    public final FluidStackIngredient fertilizerIngredient;
    public final List<MekUtChanceOutput> outputs;
    public final int duration;

    protected GreenHouseRecipe(RecipeType<? extends GreenHouseRecipe> recipeType,
            ItemStackIngredient cropIngredient,
            ItemStackIngredient soilIngredient,
            FluidStackIngredient fertilizerInredient,
            List<MekUtChanceOutput> outputs,
            int duration) {
        this.recipeType = recipeType;
        this.cropIngredient = cropIngredient;
        this.soilIngredient = soilIngredient;
        this.fertilizerIngredient = fertilizerInredient;
        this.outputs = Collections.unmodifiableList(outputs);
        this.duration = duration;
    }

    @Override
    public final RecipeType<?> getType() {
        return recipeType;
    }

    public ItemStackIngredient getCropIngredient() {
        return cropIngredient;
    }

    public ItemStackIngredient getSoilIngredient() {
        return soilIngredient;
    }

    public FluidStackIngredient getFertilizerIngredient() {
        return fertilizerIngredient;
    }

    public List<MekUtChanceOutput> getOutputs(ItemStack crop, ItemStack soil) {
        return outputs;
    }

    public List<MekUtChanceOutput> getOutputsRaw() {
        return outputs;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public boolean test(ItemStack crop, ItemStack soil, FluidStack fertilizer) {
        return cropIngredient.test(crop) && soilIngredient.test(soil) && fertilizerIngredient.test(fertilizer);
    }

    @Override
    public boolean testType(ItemStack crop, ItemStack soil, FluidStack fertilizer) {
        return (crop.isEmpty() || cropIngredient.testType(crop))
                && (soil.isEmpty() || soilIngredient.testType(soil))
                && (fertilizer.isEmpty() || fertilizerIngredient.testType(fertilizer));
    }

    @Override
    public boolean isIncomplete() {
        return cropIngredient.hasNoMatchingInstances()
                || soilIngredient.hasNoMatchingInstances()
                || fertilizerIngredient.hasNoMatchingInstances();
    }

    @Override
    public boolean matches(RecipeInput arg0, Level arg1) {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o != null && o.getClass() == this.getClass()) {
            GreenHouseRecipe other = (GreenHouseRecipe) o;
            return this.duration == other.duration
                    && this.cropIngredient.equals(other.cropIngredient)
                    && this.soilIngredient.equals(other.soilIngredient)
                    && this.fertilizerIngredient.equals(other.fertilizerIngredient)
                    && this.outputs.equals(other.outputs);
        } else {
            return false;
        }
    }

    @Override
    public void logMissingTags() {
        cropIngredient.logMissingTags();
        soilIngredient.logMissingTags();
        fertilizerIngredient.logMissingTags();
    }

    @Override
    public int hashCode() {
        int result = cropIngredient.hashCode();
        result = result * 31 + soilIngredient.hashCode();
        result = result * 31 + fertilizerIngredient.hashCode();
        result = result * 31 + outputs.hashCode();
        result = result * 31 + duration;
        return result;
    }

}
