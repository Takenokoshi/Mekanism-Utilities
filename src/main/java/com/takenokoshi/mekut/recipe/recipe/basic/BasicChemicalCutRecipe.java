package com.takenokoshi.mekut.recipe.recipe.basic;

import com.fxd927.mekanismelements.common.registries.MSGases;
import com.glodblock.github.extendedae.recipe.CircuitCutterRecipe;
import com.takenokoshi.mekut.registries.MekUtRecipeSerializers;
import com.takenokoshi.mekut.registries.MekUtRecipeTypes;

import mekanism.api.recipes.basic.BasicItemStackChemicalToItemStackRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class BasicChemicalCutRecipe extends BasicItemStackChemicalToItemStackRecipe {

    public BasicChemicalCutRecipe(ItemStackIngredient itemInput, ChemicalStackIngredient chemicalInput,
            ItemStack output, boolean perTickUsage) {
        super(itemInput, chemicalInput, output, perTickUsage, MekUtRecipeTypes.CHEMICAL_CUT.get());
    }

    public static BasicChemicalCutRecipe convertFromCircuitCutter(CircuitCutterRecipe recipe) {
        var input = recipe.getInput();
        return new BasicChemicalCutRecipe(
                IngredientCreatorAccess.item().from(input.getIngredient(), input.getAmount()),
                IngredientCreatorAccess.chemicalStack().from(MSGases.COMPRESSED_AIR.asStack(1)),
                recipe.output.copy(),
                true);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MekUtRecipeSerializers.CHEMICAL_CUT.get();
    }

}
