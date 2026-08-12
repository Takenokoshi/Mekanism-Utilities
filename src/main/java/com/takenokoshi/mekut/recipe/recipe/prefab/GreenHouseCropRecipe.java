package com.takenokoshi.mekut.recipe.recipe.prefab;

import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;

import com.takenokoshi.mekut.recipe.output.MekUtChanceOutput;

import mekanism.api.recipes.MekanismRecipe;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public abstract class GreenHouseCropRecipe extends MekanismRecipe<RecipeInput>
        implements BiPredicate<ItemStack, ItemStack> {

    protected final RecipeType<? extends GreenHouseCropRecipe> recipeType;
    public final ItemStackIngredient cropIngredient;
    public final ItemStackIngredient soilIngredient;
    public final List<MekUtChanceOutput> outputs;
    public final int duration;

    protected GreenHouseCropRecipe(RecipeType<? extends GreenHouseCropRecipe> recipeType,
            ItemStackIngredient cropIngredient, ItemStackIngredient soilIngredient, List<MekUtChanceOutput> outputs,
            int duration) {
        this.recipeType = recipeType;
        this.cropIngredient = cropIngredient;
        this.soilIngredient = soilIngredient;
        this.outputs = Collections.unmodifiableList(outputs);
        this.duration = duration;
    }

    @Override
    public RecipeType<? extends GreenHouseCropRecipe> getType() {
        return recipeType;
    }

    public ItemStackIngredient getCropIngredient() {
        return cropIngredient;
    }

    public ItemStackIngredient getSoilIngredient() {
        return soilIngredient;
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
    public boolean test(ItemStack crop, ItemStack soil) {
        return cropIngredient.test(crop) && soilIngredient.test(soil);
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
            GreenHouseCropRecipe other = (GreenHouseCropRecipe) o;
            return this.duration == other.duration
                    && this.cropIngredient.equals(other.cropIngredient)
                    && this.soilIngredient.equals(other.soilIngredient)
                    && this.outputs.equals(other.outputs);
        } else {
            return false;
        }
    }

    @Override
    public void logMissingTags() {
        cropIngredient.logMissingTags();
        soilIngredient.logMissingTags();
    }

    @Override
    public boolean isIncomplete() {
        return cropIngredient.hasNoMatchingInstances()||soilIngredient.hasNoMatchingInstances();
    }

    @Override
    public int hashCode() {
        int result = cropIngredient.hashCode();
        result = result * 31 + soilIngredient.hashCode();
        result = result * 31 + outputs.hashCode();
        result = result * 31 + duration;
        return result;
    }
}
