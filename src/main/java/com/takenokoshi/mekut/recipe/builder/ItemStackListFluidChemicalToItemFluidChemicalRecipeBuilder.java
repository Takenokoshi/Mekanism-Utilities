package com.takenokoshi.mekut.recipe.builder;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekut.recipe.recipe.basic.BasicSmallDigitalReactionChamberRecipe;
import com.takenokoshi.mekut.recipe.recipe.prefab.ItemStackListFluidChemicalToItemFluidChemicalRecipe;

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

public class ItemStackListFluidChemicalToItemFluidChemicalRecipeBuilder
        extends MekanismRecipeBuilder<ItemStackListFluidChemicalToItemFluidChemicalRecipeBuilder> {

    protected final Factory factory;
    protected List<ItemStackIngredient> itemInputs = new ArrayList<>();
    protected FluidStackIngredient fluidInput;
    protected ChemicalStackIngredient chemicalInput;
    protected final ItemStack outputItem;
    protected final FluidStack outputFluid;
    protected final ChemicalStack outputChemical;
    protected long energyRequired = 0;
    protected int duration = 100;

    protected ItemStackListFluidChemicalToItemFluidChemicalRecipeBuilder(Factory factory, ItemStack outputItem,
            FluidStack outputFluid, ChemicalStack outputChemical) {
        this.factory = factory;
        this.outputItem = outputItem;
        this.outputFluid = outputFluid;
        this.outputChemical = outputChemical;
    }

    public static ItemStackListFluidChemicalToItemFluidChemicalRecipeBuilder smallDigitalReactionChamber(
            ItemStack outputItem,
            FluidStack outputFluid, ChemicalStack outputChemical) {
        return new ItemStackListFluidChemicalToItemFluidChemicalRecipeBuilder(
                BasicSmallDigitalReactionChamberRecipe::new, outputItem, outputFluid,
                outputChemical);
    }

    public ItemStackListFluidChemicalToItemFluidChemicalRecipeBuilder addItemInput(ItemStackIngredient ingredient) {
        itemInputs.add(ingredient);
        return this;
    }

    public ItemStackListFluidChemicalToItemFluidChemicalRecipeBuilder addItemInput(ItemStack stack) {
        return addItemInput(IngredientCreatorAccess.item().from(stack));
    }

    public ItemStackListFluidChemicalToItemFluidChemicalRecipeBuilder addItemInput(TagKey<Item> tagKey, int size) {
        return addItemInput(IngredientCreatorAccess.item().from(tagKey, size));
    }

    public ItemStackListFluidChemicalToItemFluidChemicalRecipeBuilder addItemInput(ItemLike item, int size) {
        return addItemInput(IngredientCreatorAccess.item().from(item, size));
    }

    public ItemStackListFluidChemicalToItemFluidChemicalRecipeBuilder setFluidInput(FluidStackIngredient ingredient) {
        this.fluidInput = ingredient;
        return this;
    }

    public ItemStackListFluidChemicalToItemFluidChemicalRecipeBuilder setFluidInput(FluidStack stack) {
        return setFluidInput(IngredientCreatorAccess.fluid().from(stack));
    }

    public ItemStackListFluidChemicalToItemFluidChemicalRecipeBuilder setFluidInput(TagKey<Fluid> tagKey, int size) {
        return setFluidInput(IngredientCreatorAccess.fluid().from(tagKey, size));
    }

    public ItemStackListFluidChemicalToItemFluidChemicalRecipeBuilder setChemicalInput(
            ChemicalStackIngredient ingredient) {
        this.chemicalInput = ingredient;
        return this;
    }

    public ItemStackListFluidChemicalToItemFluidChemicalRecipeBuilder setChemicalInput(ChemicalStack stack) {
        return setChemicalInput(IngredientCreatorAccess.chemicalStack().from(stack));
    }

    public ItemStackListFluidChemicalToItemFluidChemicalRecipeBuilder setEnergyRequired(long value) {
        this.energyRequired = value;
        return this;
    }

    public ItemStackListFluidChemicalToItemFluidChemicalRecipeBuilder setDuration(int value) {
        this.duration = Math.max(1, value);
        return this;
    }

    @Override
    protected Recipe<?> asRecipe() {
        return factory.create(List.copyOf(itemInputs), fluidInput, chemicalInput, outputItem, outputFluid,
                outputChemical,
                energyRequired, duration);
    }

    @FunctionalInterface
    protected static interface Factory {
        ItemStackListFluidChemicalToItemFluidChemicalRecipe create(
                List<ItemStackIngredient> itemInputs,
                @Nullable FluidStackIngredient fluidInput,
                @Nullable ChemicalStackIngredient chemicalInput,
                ItemStack outputItem,
                FluidStack outputFluid,
                ChemicalStack outputChemical,
                long energyRequired,
                int duration);
    }

}
