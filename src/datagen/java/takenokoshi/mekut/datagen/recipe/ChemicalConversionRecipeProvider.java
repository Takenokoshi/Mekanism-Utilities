package takenokoshi.mekut.datagen.recipe;

import mekanism.api.datagen.recipe.builder.ItemStackToChemicalRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
import takenokoshi.mekut.common.MekUt;
import takenokoshi.mekut.common.registries.MekUtChemicals;
import takenokoshi.mekut.common.registries.MekUtItems;

public class ChemicalConversionRecipeProvider {
    static void buildRecipes(RecipeOutput recipeOutput) {
        ItemStackToChemicalRecipeBuilder
                .chemicalConversion(
                        IngredientCreatorAccess.item().from(Tags.Items.DUSTS_GLOWSTONE, 1),
                        MekUtChemicals.GLOWSTONE.asStack(20L))
                .build(recipeOutput, MekUt.rl("chemical_conversion/glowstone/from_dust"));
        ItemStackToChemicalRecipeBuilder
                .chemicalConversion(
                        IngredientCreatorAccess.item().from(Items.GLOWSTONE, 1),
                        MekUtChemicals.GLOWSTONE.asStack(80L))
                .build(recipeOutput, MekUt.rl("chemical_conversion/glowstone/from_block"));
        ItemStackToChemicalRecipeBuilder
                .chemicalConversion(
                        IngredientCreatorAccess.item().from(MekUtItems.ENRICHED_GLOWSTONE, 1),
                        MekUtChemicals.GLOWSTONE.asStack(80L))
                .build(recipeOutput, MekUt.rl("chemical_conversion/glowstone/from_enriched"));
    }
}
