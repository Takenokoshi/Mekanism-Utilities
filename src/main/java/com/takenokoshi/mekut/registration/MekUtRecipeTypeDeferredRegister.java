package com.takenokoshi.mekut.registration;

import java.util.function.Function;

import com.takenokoshi.mekut.recipe.MekUtRecipeType;

import mekanism.common.recipe.lookup.cache.IInputRecipeCache;
import mekanism.common.registration.MekanismDeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeType;

public class MekUtRecipeTypeDeferredRegister extends MekanismDeferredRegister<RecipeType<?>> {

    public MekUtRecipeTypeDeferredRegister(String modid) {
        super(Registries.RECIPE_TYPE, modid, MekUtRecipeTypeRegistryObject::new);
    }

    @SuppressWarnings("unchecked")
    // Safe: holderCreator always creates MekUtRecipeTypeRegistryObject
    public <VANILLA_INPUT extends RecipeInput, RECIPE extends Recipe<VANILLA_INPUT>, INPUT_CACHE extends IInputRecipeCache> MekUtRecipeTypeRegistryObject<VANILLA_INPUT, RECIPE, INPUT_CACHE> registerMekUt(
            String name,
            Function<ResourceLocation, ? extends MekUtRecipeType<VANILLA_INPUT, RECIPE, INPUT_CACHE>> function) {
        return (MekUtRecipeTypeRegistryObject<VANILLA_INPUT, RECIPE, INPUT_CACHE>) super.register(name, function);
    }
}
