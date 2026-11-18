package takenokoshi.mekut.datagen.recipe;

import mekanism.api.datagen.recipe.builder.ItemStackChemicalToItemStackRecipeBuilder;
import mekanism.api.recipes.ingredients.creator.IngredientCreatorAccess;
import mekanism.common.resource.PrimaryResource;
import mekanism.common.resource.ResourceType;
import mekanism.common.tags.MekanismTags;
import net.minecraft.data.recipes.RecipeOutput;
import takenokoshi.mekut.common.MekUt;
import takenokoshi.mekut.common.registries.MekUtChemicals;
import takenokoshi.mekut.common.registries.MekUtItems;

public class ControlCircuitRecipeProvider {
    static void buildRecipes(RecipeOutput recipeOutput) {
        ItemStackChemicalToItemStackRecipeBuilder
                .metallurgicInfusing(
                        IngredientCreatorAccess.item().from(
                                MekanismTags.Items.PROCESSED_RESOURCES.get(ResourceType.INGOT, PrimaryResource.OSMIUM),
                                1),
                        IngredientCreatorAccess.chemicalStack().fromHolder(MekUtChemicals.GLOWSTONE, 20),
                        MekUtItems.STANDARD_CONTROL_CIRCUIT.asStack(1),
                        false)
                .build(recipeOutput, MekUt.rl("control_circuit/standard"));
    }
}
