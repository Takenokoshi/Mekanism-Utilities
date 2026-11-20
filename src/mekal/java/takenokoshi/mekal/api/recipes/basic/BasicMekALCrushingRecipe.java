package takenokoshi.mekal.api.recipes.basic;

import mekanism.api.MekanismAPI;
import mekanism.api.recipes.MekanismRecipeTypes;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import takenokoshi.mekal.api.recipes.MekALRecipeSerializers;

public class BasicMekALCrushingRecipe extends BasicItemStackToItemStackIngredientRecipe {

    private static final Holder<Item> CRUSHER = DeferredHolder.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MekanismAPI.MEKANISM_MODID, "crusher"));

    public BasicMekALCrushingRecipe(ItemStackIngredient input, ItemStackIngredient output) {
        super(input, output, MekanismRecipeTypes.TYPE_CRUSHING.value());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MekALRecipeSerializers.MEKAL_CRUSHING.value();
    }

    @Override
    public String getGroup() {
        return "crusher";
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(CRUSHER);
    }
    
}
