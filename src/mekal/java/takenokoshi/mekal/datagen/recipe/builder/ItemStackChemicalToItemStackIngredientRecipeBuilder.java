package takenokoshi.mekal.datagen.recipe.builder;

import com.mojang.datafixers.util.Function4;

import mekanism.api.datagen.recipe.MekanismRecipeBuilder;
import mekanism.api.recipes.ItemStackChemicalToItemStackRecipe;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.world.item.crafting.Recipe;
import takenokoshi.mekal.api.recipes.basic.BasicMekALCompressingRecipe;
import takenokoshi.mekal.api.recipes.basic.BasicMekALInjectingRecipe;
import takenokoshi.mekal.api.recipes.basic.BasicMekALMetallurgicInfuserRecipe;
import takenokoshi.mekal.api.recipes.basic.BasicMekALPaintingRecipe;
import takenokoshi.mekal.api.recipes.basic.BasicMekALPurifyingRecipe;

public class ItemStackChemicalToItemStackIngredientRecipeBuilder
        extends MekanismRecipeBuilder<ItemStackChemicalToItemStackIngredientRecipeBuilder> {

    protected final Function4<ItemStackIngredient, ChemicalStackIngredient, ItemStackIngredient, Boolean, ItemStackChemicalToItemStackRecipe> factory;
    protected final ItemStackIngredient itemInput, output;
    protected final ChemicalStackIngredient chemicalInput;
    protected final boolean perTickUsage;

    protected ItemStackChemicalToItemStackIngredientRecipeBuilder(
            Function4<ItemStackIngredient, ChemicalStackIngredient, ItemStackIngredient, Boolean, ItemStackChemicalToItemStackRecipe> factory,
            ItemStackIngredient itemInput, ChemicalStackIngredient chemicalInput, ItemStackIngredient output,
            boolean perTickUsage) {
        this.factory = factory;
        this.itemInput = itemInput;
        this.output = output;
        this.chemicalInput = chemicalInput;
        this.perTickUsage = perTickUsage;
    }

    public static ItemStackChemicalToItemStackIngredientRecipeBuilder compressing(ItemStackIngredient itemInput,
            ChemicalStackIngredient chemicalInput, ItemStackIngredient output,
            boolean perTickUsage) {
        return new ItemStackChemicalToItemStackIngredientRecipeBuilder(BasicMekALCompressingRecipe::new, itemInput,
                chemicalInput, output, perTickUsage);
    }

    public static ItemStackChemicalToItemStackIngredientRecipeBuilder injecting(ItemStackIngredient itemInput,
            ChemicalStackIngredient chemicalInput, ItemStackIngredient output,
            boolean perTickUsage) {
        return new ItemStackChemicalToItemStackIngredientRecipeBuilder(BasicMekALInjectingRecipe::new, itemInput,
                chemicalInput, output, perTickUsage);
    }

    public static ItemStackChemicalToItemStackIngredientRecipeBuilder metallurgicInfusing(ItemStackIngredient itemInput,
            ChemicalStackIngredient chemicalInput, ItemStackIngredient output,
            boolean perTickUsage) {
        return new ItemStackChemicalToItemStackIngredientRecipeBuilder(BasicMekALMetallurgicInfuserRecipe::new,
                itemInput,
                chemicalInput, output, perTickUsage);
    }

    public static ItemStackChemicalToItemStackIngredientRecipeBuilder painting(ItemStackIngredient itemInput,
            ChemicalStackIngredient chemicalInput, ItemStackIngredient output,
            boolean perTickUsage) {
        return new ItemStackChemicalToItemStackIngredientRecipeBuilder(BasicMekALPaintingRecipe::new, itemInput,
                chemicalInput, output, perTickUsage);
    }

    public static ItemStackChemicalToItemStackIngredientRecipeBuilder purifying(ItemStackIngredient itemInput,
            ChemicalStackIngredient chemicalInput, ItemStackIngredient output,
            boolean perTickUsage) {
        return new ItemStackChemicalToItemStackIngredientRecipeBuilder(BasicMekALPurifyingRecipe::new, itemInput,
                chemicalInput, output, perTickUsage);
    }

    @Override
    protected Recipe<?> asRecipe() {
        return factory.apply(itemInput, chemicalInput, output, perTickUsage);
    }

}
