package com.takenokoshi.mekut.recipe_viewer.jei;

import java.util.List;

import com.takenokoshi.mekaddonlib.recipe_viewer.jei.MekALRecipeRegistryHelper;
import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.recipe.type.WrappedRecipeType;
import com.takenokoshi.mekut.recipe_viewer.jei.category.BiChemicalToItemRecipeCategory;
import com.takenokoshi.mekut.recipe_viewer.jei.category.FissionReactorRecipeCategory;
import com.takenokoshi.mekut.recipe_viewer.jei.category.FluidToItemRecipeCategory;
import com.takenokoshi.mekut.recipe_viewer.jei.category.MekUtEnergizedSmelterRecipeCategory;
import com.takenokoshi.mekut.recipe_viewer.jei.category.SmallDigitalAssemblerRecipeCategory;
import com.takenokoshi.mekut.recipe_viewer.jei.category.SmallDigitalReactionChamberRecipeCategory;
import com.takenokoshi.mekut.recipe_viewer.type.MekUtRecipeViewerRecipeType;
import com.takenokoshi.mekut.registries.MekUtBlocks;
import com.takenokoshi.mekut.registries.MekUtItems;
import com.takenokoshi.mekut.registries.MekUtMachines;
import com.takenokoshi.mekut.registries.MekUtRecipeTypes;

import mekanism.client.recipe_viewer.jei.CatalystRegistryHelper;
import mekanism.client.recipe_viewer.jei.MekanismJEI;
import mekanism.client.recipe_viewer.jei.machine.ChemicalChemicalToChemicalRecipeCategory;
import mekanism.client.recipe_viewer.jei.machine.ChemicalToChemicalRecipeCategory;
import mekanism.client.recipe_viewer.jei.machine.ItemStackChemicalToItemStackRecipeCategory;
import mekanism.client.recipe_viewer.type.IRecipeViewerRecipeType;
import mekanism.client.recipe_viewer.type.RecipeViewerRecipeType;
import mekanism.generators.client.recipe_viewer.GeneratorsRVRecipeType;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class MekUtJEIPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return MekUtConstants.rl("jei_plugin");
    }

    public static boolean shouldLoad() {
        // Skip handling if both EMI and JEI are loaded as otherwise some things behave
        // strangely
        // MekUt doesn't have emi integration now, so this method should return true.
        // return !Mekanism.hooks.emi.isLoaded();
        return true;
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        if (shouldLoad()) {
            MekanismJEI.registerItemSubtypes(registration, MekUtItems.ITEMS.getEntries());
            MekanismJEI.registerItemSubtypes(registration, MekUtBlocks.BLOCKS.getSecondaryEntries());
            MekanismJEI.registerItemSubtypes(registration, MekUtMachines.MACHINES.blockRegister.getSecondaryEntries());
        }
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        if (!shouldLoad()) {
            return;
        }
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
        registry.addRecipeCategories(new IRecipeCategory[] {
                new ItemStackChemicalToItemStackRecipeCategory(guiHelper, MekUtRecipeViewerRecipeType.CHEMICAL_CUT),
                new FluidToItemRecipeCategory(guiHelper, MekUtRecipeViewerRecipeType.ICE_MAKING),
                new ChemicalChemicalToChemicalRecipeCategory(guiHelper, MekUtRecipeViewerRecipeType.LAZER_COMPRESS),
                new SmallDigitalAssemblerRecipeCategory(guiHelper, MekUtRecipeViewerRecipeType.SMALL_DIGITAL_ASSEMBLER),
                new SmallDigitalReactionChamberRecipeCategory(guiHelper,
                        MekUtRecipeViewerRecipeType.SMALL_DIGITAL_REACTION_CHAMBER),
                new ChemicalToChemicalRecipeCategory(guiHelper, MekUtRecipeViewerRecipeType.SPS),
                new BiChemicalToItemRecipeCategory(guiHelper, MekUtRecipeViewerRecipeType.STELLAR_GENESIS),
                new MekUtEnergizedSmelterRecipeCategory<>(guiHelper, MekUtRecipeViewerRecipeType.TWEAKED_SMELLTING),
                new FissionReactorRecipeCategory(guiHelper, MekUtRecipeViewerRecipeType.FISSION_REACTOR),
        });
    }

    @Override
    public void registerRecipes(IRecipeRegistration registry) {
        if (!shouldLoad()) {
            return;
        }
        MekALRecipeRegistryHelper.register(registry, MekUtRecipeViewerRecipeType.CHEMICAL_CUT,
                MekUtRecipeTypes.CHEMICAL_CUT);
        MekALRecipeRegistryHelper.register(registry, MekUtRecipeViewerRecipeType.ICE_MAKING,
                MekUtRecipeTypes.ICE_MAKING);
        MekALRecipeRegistryHelper.register(registry, MekUtRecipeViewerRecipeType.LAZER_COMPRESS,
                MekUtRecipeTypes.LAZER_COMPRESS);
        MekALRecipeRegistryHelper.register(registry, MekUtRecipeViewerRecipeType.SMALL_DIGITAL_ASSEMBLER,
                MekUtRecipeTypes.SMALL_DIGITAL_ASSEMBLER);
        MekALRecipeRegistryHelper.register(registry, MekUtRecipeViewerRecipeType.SMALL_DIGITAL_REACTION_CHAMBER,
                MekUtRecipeTypes.SMALL_DIGITAL_REACTION_CHAMBER);
        MekALRecipeRegistryHelper.register(registry, MekUtRecipeViewerRecipeType.SPS,
                MekUtRecipeTypes.SPS);
        MekALRecipeRegistryHelper.register(registry, MekUtRecipeViewerRecipeType.STELLAR_GENESIS,
                MekUtRecipeTypes.STELLAR_GENESIS);
        MekALRecipeRegistryHelper.register(registry, MekUtRecipeViewerRecipeType.FISSION_REACTOR,
                MekUtRecipeTypes.FISSION_REACTOR);
        MekALRecipeRegistryHelper.register(registry, MekUtRecipeViewerRecipeType.TWEAKED_SMELLTING,
                WrappedRecipeType.VANILLA_SMELTING);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
        if (!shouldLoad()) {
            return;
        }
        CatalystRegistryHelper.register(registry, new IRecipeViewerRecipeType[] {
                MekUtRecipeViewerRecipeType.CHEMICAL_CUT,
                MekUtRecipeViewerRecipeType.ICE_MAKING,
                MekUtRecipeViewerRecipeType.LAZER_COMPRESS,
                MekUtRecipeViewerRecipeType.SMALL_DIGITAL_ASSEMBLER,
                MekUtRecipeViewerRecipeType.SMALL_DIGITAL_REACTION_CHAMBER,
                MekUtRecipeViewerRecipeType.SPS,
                MekUtRecipeViewerRecipeType.STELLAR_GENESIS,
                MekUtRecipeViewerRecipeType.FISSION_REACTOR,
                MekUtRecipeViewerRecipeType.TWEAKED_SMELLTING,
        });
        CatalystRegistryHelper.register(registry, RecipeTypes.SMELTING,
                List.of(MekUtMachines.TWEAKED_ENERGIZED_SMELTER));
        CatalystRegistryHelper.register(registry,
                MekanismJEI.genericRecipeType(RecipeViewerRecipeType.CHEMICAL_CONVERSION),
                List.of(MekUtMachines.SUBMATERIAL_CONVERTER));
        CatalystRegistryHelper.register(registry,
                MekanismJEI.genericRecipeType(GeneratorsRVRecipeType.FISSION),
                List.of(MekUtMachines.COMPACT_FISSION_REACTOR));
    }

}
