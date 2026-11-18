package takenokoshi.mekut.client.recipe_viewer.emi;

import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import mekanism.client.recipe_viewer.emi.MekanismEmi;
import mekanism.client.recipe_viewer.emi.recipe.ItemStackToItemStackEmiRecipe;
import takenokoshi.mekut.client.recipe_viewer.type.MekUtRVRecipeType;

@EmiEntrypoint
public class MekUtEmi implements EmiPlugin {

    @Override
    public void register(EmiRegistry registry) {
        addCategories(registry);
    }

    private void addCategories(EmiRegistry registry) {
        MekanismEmi.addCategoryAndRecipes(registry, MekUtRVRecipeType.MINERAL_EXTRACTING,
                ItemStackToItemStackEmiRecipe::new);
    }

}
