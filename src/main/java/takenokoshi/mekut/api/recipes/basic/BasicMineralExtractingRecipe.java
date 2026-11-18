package takenokoshi.mekut.api.recipes.basic;

import mekanism.api.recipes.basic.BasicItemStackToItemStackRecipe;
import mekanism.api.recipes.basic.IBasicItemStackOutput;
import mekanism.api.recipes.ingredients.ItemStackIngredient;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import takenokoshi.mekut.api.recipes.MekUtRecipeSerializers;
import takenokoshi.mekut.api.recipes.MekUtRecipeTypes;
import takenokoshi.mekut.common.MekUt;

public class BasicMineralExtractingRecipe extends BasicItemStackToItemStackRecipe implements IBasicItemStackOutput {

    private static final Holder<Item> MINERAL_EXTRACTOR = DeferredHolder.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MekUt.MODID, "mineral_extractor"));
    
    public BasicMineralExtractingRecipe(ItemStackIngredient input, ItemStack output) {
        super(input, output, MekUtRecipeTypes.TYPE_MINERAL_EXTRACTING.value());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return MekUtRecipeSerializers.MINERAL_EXTRACTING.value();
    }

    @Override
    public String getGroup() {
        return "mineral_extractor";
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(MINERAL_EXTRACTOR);
    }

}
