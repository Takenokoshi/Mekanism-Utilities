package takenokoshi.mekut.client.recipe_viewer.jei;

import mekanism.client.recipe_viewer.jei.CatalystRegistryHelper;
import mekanism.client.recipe_viewer.jei.RecipeRegistryHelper;
import mekanism.client.recipe_viewer.jei.machine.ItemStackToItemStackRecipeCategory;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import takenokoshi.mekut.client.recipe_viewer.type.MekUtRVRecipeType;
import takenokoshi.mekut.common.MekUt;
import takenokoshi.mekut.common.recipe.MekUtRecipeType;

@JeiPlugin
public class MekUtJEI implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(MekUt.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();

        registry.addRecipeCategories(new IRecipeCategory[] {
                new ItemStackToItemStackRecipeCategory(guiHelper, MekUtRVRecipeType.MINERAL_EXTRACTING),
        });
    }

    @Override
    public void registerRecipes(IRecipeRegistration registry) {
        RecipeRegistryHelper.register(registry,
                MekUtRVRecipeType.MINERAL_EXTRACTING, MekUtRecipeType.MINERAL_EXTRACTING);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
        CatalystRegistryHelper.register(registry, new IRecipeViewerRecipeType[] {
                MekUtRVRecipeType.MINERAL_EXTRACTING,
        });
    }

}
