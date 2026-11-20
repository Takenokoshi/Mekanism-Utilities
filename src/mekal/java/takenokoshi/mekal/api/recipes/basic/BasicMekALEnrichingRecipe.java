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

public class BasicMekALEnrichingRecipe extends BasicItemStackToItemStackIngredientRecipe {

    private static final Holder<Item> ENRICHMENT_CHAMBER = DeferredHolder.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MekanismAPI.MEKANISM_MODID, "enrichment_chamber"));

    public BasicMekALEnrichingRecipe(ItemStackIngredient input, ItemStackIngredient output) {
        super(input, output, MekanismRecipeTypes.TYPE_ENRICHING.value());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MekALRecipeSerializers.MEKAL_ENRICHING.value();
    }

    @Override
    public String getGroup() {
        return "enrichment_chamber";
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(ENRICHMENT_CHAMBER);
    }
    
}
