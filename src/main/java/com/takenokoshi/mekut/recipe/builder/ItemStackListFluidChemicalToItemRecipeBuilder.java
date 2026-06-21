package com.takenokoshi.mekut.recipe.builder;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekut.recipe.recipe.basic.BasicSmallDigitalAssemblerRecipe;
import com.takenokoshi.mekut.recipe.recipe.prefab.ItemStackListFluidChemicalToItemRecipe;

import mekanism.api.chemical.ChemicalStack;
import mekanism.api.datagen.recipe.MekanismRecipeBuilder;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.FluidStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;

public class ItemStackListFluidChemicalToItemRecipeBuilder
        extends MekanismRecipeBuilder<ItemStackListFluidChemicalToItemRecipeBuilder> {

    protected final Factory factory;
    protected List<ItemStackIngredient> itemInputs = new ArrayList<>();
    protected FluidStackIngredient fluidInput;
    protected ChemicalStackIngredient chemicalInput;
    protected final ItemStack outputItem;
    protected long energyRequired = 0;

    protected ItemStackListFluidChemicalToItemRecipeBuilder(Factory factory, ItemStack outputItem) {
        this.factory = factory;
        this.outputItem = outputItem;
    }

    public static ItemStackListFluidChemicalToItemRecipeBuilder smallDigitalAssembler(ItemStack outputItem) {
        return new ItemStackListFluidChemicalToItemRecipeBuilder(BasicSmallDigitalAssemblerRecipe::new, outputItem);
    }

    public ItemStackListFluidChemicalToItemRecipeBuilder addItemInput(ItemStackIngredient ingredient) {
        itemInputs.add(ingredient);
        return this;
    }

    public ItemStackListFluidChemicalToItemRecipeBuilder addItemInput(ItemStack stack) {
        return addItemInput(IngredientCreatorAccess.item().from(stack));
    }

    public ItemStackListFluidChemicalToItemRecipeBuilder addItemInput(ItemLike item, int size) {
        return addItemInput(IngredientCreatorAccess.item().from(item, size));
    }

    public ItemStackListFluidChemicalToItemRecipeBuilder addItemInput(TagKey<Item> tagKey, int size) {
        return addItemInput(IngredientCreatorAccess.item().from(tagKey, size));
    }

    public ItemStackListFluidChemicalToItemRecipeBuilder setFluidInput(FluidStackIngredient ingredient) {
        this.fluidInput = ingredient;
        return this;
    }

    public ItemStackListFluidChemicalToItemRecipeBuilder setFluidInput(FluidStack stack) {
        return setFluidInput(IngredientCreatorAccess.fluid().from(stack));
    }

    public  ItemStackListFluidChemicalToItemRecipeBuilder setFluidInput(TagKey<Fluid> tagKey, int size) {
        return setFluidInput(IngredientCreatorAccess.fluid().from(tagKey, size));
    }

    public ItemStackListFluidChemicalToItemRecipeBuilder setChemicalInput(ChemicalStackIngredient ingredient) {
        this.chemicalInput = ingredient;
        return this;
    }

    public ItemStackListFluidChemicalToItemRecipeBuilder setChemicalInput(ChemicalStack stack) {
        return setChemicalInput(IngredientCreatorAccess.chemicalStack().from(stack));
    }

    public ItemStackListFluidChemicalToItemRecipeBuilder setEnergyRequired(long value) {
        this.energyRequired = value;
        return this;
    }

    @Override
    protected Recipe<?> asRecipe() {
        return factory.create(List.copyOf(itemInputs), fluidInput, chemicalInput, outputItem, energyRequired);
    }

    @FunctionalInterface
    protected static interface Factory {
        ItemStackListFluidChemicalToItemRecipe create(List<ItemStackIngredient> itemInputs,
                @Nullable FluidStackIngredient fluidInput,
                @Nullable ChemicalStackIngredient chemicalInput,
                ItemStack outputItem, long energyRequired);
    }

}
