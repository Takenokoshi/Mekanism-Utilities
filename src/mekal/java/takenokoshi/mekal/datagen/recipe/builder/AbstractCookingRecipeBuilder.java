package takenokoshi.mekal.datagen.recipe.builder;

import mekanism.api.datagen.recipe.MekanismRecipeBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.item.crafting.SmokingRecipe;

public class AbstractCookingRecipeBuilder extends MekanismRecipeBuilder<AbstractCookingRecipeBuilder> {

    protected final AbstractCookingRecipe.Factory<?> factory;
    protected final CookingBookCategory bookCategory;
    protected final Ingredient ingredient;
    protected final ItemStack output;
    protected final int cookingTime;
    protected float experience;
    protected String group;

    protected AbstractCookingRecipeBuilder(AbstractCookingRecipe.Factory<?> factory,
            CookingBookCategory bookCategory, Ingredient ingredient, ItemStack output, int cookingTime) {
        this.factory = factory;
        this.bookCategory = bookCategory;
        this.ingredient = ingredient;
        this.output = output;
        this.cookingTime = cookingTime;

    }

    public static AbstractCookingRecipeBuilder smelting(CookingBookCategory bookCategory, Ingredient ingredient,
            ItemStack output, int cookingTime) {
        return new AbstractCookingRecipeBuilder(SmeltingRecipe::new, bookCategory, ingredient, output, cookingTime);
    }

    public static AbstractCookingRecipeBuilder smelting(Ingredient ingredient, ItemStack output) {
        return new AbstractCookingRecipeBuilder(SmeltingRecipe::new, CookingBookCategory.MISC, ingredient, output, 200);
    }

    public static AbstractCookingRecipeBuilder blasting(CookingBookCategory bookCategory, Ingredient ingredient,
            ItemStack output, int cookingTime) {
        return new AbstractCookingRecipeBuilder(BlastingRecipe::new, bookCategory, ingredient, output, cookingTime);
    }

    public static AbstractCookingRecipeBuilder blasting(Ingredient ingredient, ItemStack output) {
        return new AbstractCookingRecipeBuilder(BlastingRecipe::new, CookingBookCategory.MISC, ingredient, output, 100);
    }

    public static AbstractCookingRecipeBuilder smoking(CookingBookCategory bookCategory, Ingredient ingredient,
            ItemStack output, int cookingTime) {
        return new AbstractCookingRecipeBuilder(SmokingRecipe::new, bookCategory, ingredient, output, cookingTime);
    }

    public static AbstractCookingRecipeBuilder smoking(Ingredient ingredient, ItemStack output) {
        return new AbstractCookingRecipeBuilder(SmokingRecipe::new, CookingBookCategory.FOOD, ingredient, output, 100);
    }

    public static AbstractCookingRecipeBuilder campfire(CookingBookCategory bookCategory, Ingredient ingredient,
            ItemStack output, int cookingTime) {
        return new AbstractCookingRecipeBuilder(CampfireCookingRecipe::new, bookCategory, ingredient, output,
                cookingTime);
    }

    public static AbstractCookingRecipeBuilder campfire(Ingredient ingredient, ItemStack output) {
        return new AbstractCookingRecipeBuilder(CampfireCookingRecipe::new, CookingBookCategory.FOOD, ingredient,
                output, 600);
    }

    public AbstractCookingRecipeBuilder experience(float experience) {
        if (experience < 0) {
            throw new IllegalArgumentException("Experience cannot be negative.");
        }
        this.experience = experience;
        return this;
    }

    public AbstractCookingRecipeBuilder group(String group) {
        this.group = group;
        return this;
    }

    @Override
    protected Recipe<?> asRecipe() {
        if (group == null) {
            group = "";
        }
        return factory.create(group, bookCategory, ingredient, output, experience, cookingTime);
    }

}
