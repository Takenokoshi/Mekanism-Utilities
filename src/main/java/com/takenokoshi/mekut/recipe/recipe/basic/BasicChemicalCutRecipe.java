package com.takenokoshi.mekut.recipe.recipe.basic;

import com.takenokoshi.mekut.registries.MekUtRecipeSerializers;
import com.takenokoshi.mekut.registries.MekUtRecipeTypes;

import mekanism.api.recipes.basic.BasicItemStackChemicalToItemStackRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class BasicChemicalCutRecipe extends BasicItemStackChemicalToItemStackRecipe {

    public BasicChemicalCutRecipe(ItemStackIngredient itemInput, ChemicalStackIngredient chemicalInput,
            ItemStack output, boolean perTickUsage) {
        super(itemInput, chemicalInput, output, perTickUsage, MekUtRecipeTypes.CHEMICAL_CUT.get());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MekUtRecipeSerializers.CHEMICAL_CUT.get();
    }

}
