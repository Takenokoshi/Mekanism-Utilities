package com.takenokoshi.mekut.recipe_viewer.type;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.recipe.MekUtRecipeConstants;
import com.takenokoshi.mekut.recipe.recipe.prefab.BiChemicalToItemRecipe;
import com.takenokoshi.mekut.recipe.recipe.prefab.ChemicalToChemicalHeatRecipe;
import com.takenokoshi.mekut.recipe.recipe.prefab.FluidToItemRecipe;
import com.takenokoshi.mekut.recipe.recipe.prefab.ItemStackListFluidChemicalToItemFluidChemicalRecipe;
import com.takenokoshi.mekut.recipe.recipe.prefab.ItemStackListFluidChemicalToItemRecipe;
import com.takenokoshi.mekut.recipe.type.WrappedRecipeType;
import com.takenokoshi.mekut.registries.MekUtMachines;
import com.takenokoshi.mekut.registries.MekUtRecipeTypes;

import mekanism.api.recipes.ChemicalChemicalToChemicalRecipe;
import mekanism.api.recipes.ChemicalToChemicalRecipe;
import mekanism.api.recipes.ItemStackChemicalToItemStackRecipe;
import net.minecraft.world.item.crafting.SmeltingRecipe;

public class MekUtRecipeViewerRecipeType {

    public static final RVMekUtRecipeTypeWrapper<?, ItemStackChemicalToItemStackRecipe, ?> CHEMICAL_CUT = new RVMekUtRecipeTypeWrapper<>(
            MekUtConstants.rl(MekUtRecipeConstants.CHEMICAL_CUT),
            ItemStackChemicalToItemStackRecipe.class,
            MekUtRecipeTypes.CHEMICAL_CUT, -28, -16, 144, 54,
            MekUtMachines.CHEMICAL_CUTTER);

    public static final RVMekUtRecipeTypeWrapper<?, FluidToItemRecipe, ?> ICE_MAKING = new RVMekUtRecipeTypeWrapper<>(
            MekUtConstants.rl(MekUtRecipeConstants.ICE_MAKING),
            FluidToItemRecipe.class,
            MekUtRecipeTypes.ICE_MAKING, -5, -3, 147, 79,
            MekUtMachines.ICE_MAKER);

    public static final RVMekUtRecipeTypeWrapper<?, ChemicalChemicalToChemicalRecipe, ?> LAZER_COMPRESS = new RVMekUtRecipeTypeWrapper<>(
            MekUtConstants.rl(MekUtRecipeConstants.LAZER_COMPRESS),
            ChemicalChemicalToChemicalRecipe.class,
            MekUtRecipeTypes.LAZER_COMPRESS, -3, -3, 170, 80,
            MekUtMachines.LAZER_COMPRESS_NUCLEO_SYNTHESIZER);

    public static final RVMekUtRecipeTypeWrapper<?, ItemStackListFluidChemicalToItemRecipe, ?> SMALL_DIGITAL_ASSEMBLER = new RVMekUtRecipeTypeWrapper<>(
            MekUtConstants.rl(MekUtRecipeConstants.SMALL_DIGITAL_ASSEMBLER),
            ItemStackListFluidChemicalToItemRecipe.class,
            MekUtRecipeTypes.SMALL_DIGITAL_ASSEMBLER, 0, -16, 208, 59,
            MekUtMachines.SMALL_DIGITAL_ASSEMBLER);

    public static final RVMekUtRecipeTypeWrapper<?, ItemStackListFluidChemicalToItemFluidChemicalRecipe, ?> SMALL_DIGITAL_REACTION_CHAMBER = new RVMekUtRecipeTypeWrapper<>(
            MekUtConstants.rl(MekUtRecipeConstants.SMALL_DIGITAL_REACTION_CHAMBER),
            ItemStackListFluidChemicalToItemFluidChemicalRecipe.class,
            MekUtRecipeTypes.SMALL_DIGITAL_REACTION_CHAMBER, 0, -16, 232, 59,
            MekUtMachines.SMALL_DIGITAL_REACTION_CHAMBER);

    public static final RVMekUtRecipeTypeWrapper<?, ChemicalToChemicalRecipe, ?> SPS = new RVMekUtRecipeTypeWrapper<>(
            MekUtConstants.rl(MekUtRecipeConstants.SPS),
            ChemicalToChemicalRecipe.class,
            MekUtRecipeTypes.SPS, -4, -13, 168, 60,
            MekUtMachines.COMPACT_SUPERCRITICAL_PHASE_SHIFTER);

    public static final RVMekUtRecipeTypeWrapper<?, BiChemicalToItemRecipe, ?> STELLAR_GENESIS = new RVMekUtRecipeTypeWrapper<>(
            MekUtConstants.rl(MekUtRecipeConstants.STELLAR_GENESIS),
            BiChemicalToItemRecipe.class,
            MekUtRecipeTypes.STELLAR_GENESIS, -3, -3, 170, 80,
            MekUtMachines.STELLAR_GENESIS_CHAMBER);

    public static final RVMekUtRecipeTypeWrapper<?, ChemicalToChemicalHeatRecipe, ?> FISSION_REACTOR = new RVMekUtRecipeTypeWrapper<>(
            MekUtConstants.rl(MekUtRecipeConstants.FISSION_REACTOR),
            ChemicalToChemicalHeatRecipe.class,
            MekUtRecipeTypes.FISSION_REACTOR, -4, -13, 168, 60,
            MekUtMachines.COMPACT_FISSION_REACTOR);

    public static final RVMekUtRecipeTypeWrapper<?, SmeltingRecipe, ?> TWEAKED_SMELLTING = new RVMekUtRecipeTypeWrapper<>(
            MekUtConstants.rl("smelting"),
            SmeltingRecipe.class,
            WrappedRecipeType.VANILLA_SMELTING, -28, -16, 152, 54,
            MekUtMachines.TWEAKED_ENERGIZED_SMELTER);
}
