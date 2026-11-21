package takenokoshi.mekal.api.recipes.basic;

import mekanism.api.MekanismAPI;
import mekanism.api.recipes.MekanismRecipeTypes;
import mekanism.api.recipes.ingredients.ChemicalStackIngredient;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import takenokoshi.mekal.api.recipes.MekALRecipeSerializers;

public class BasicMekALMetallurgicInfuserRecipe extends BasicItemStackChemicalToItemStackIngredientRecipe {

    private static final Holder<Item> METALLURGIC_INFUSER = DeferredHolder.create(Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(MekanismAPI.MEKANISM_MODID, "metallurgic_infuser"));

    public BasicMekALMetallurgicInfuserRecipe(ItemStackIngredient itemInput, ChemicalStackIngredient chemicalInput,
            ItemStackIngredient output, boolean perTickUsage) {
        super(itemInput, chemicalInput, output, perTickUsage, MekanismRecipeTypes.TYPE_METALLURGIC_INFUSING.value());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MekALRecipeSerializers.MEKAL_METALLURGIC_INFUSING.value();
    }

    @Override
    public String getGroup() {
        return "metallurgic_infuser";
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(METALLURGIC_INFUSER);
    }

}