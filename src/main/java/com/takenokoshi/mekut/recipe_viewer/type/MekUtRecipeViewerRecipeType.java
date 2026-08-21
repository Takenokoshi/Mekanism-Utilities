package com.takenokoshi.mekut.recipe_viewer.type;

import com.takenokoshi.mekaddonlib.recipe_viewer.type.RVMekALRecipeTypeWrapper;
import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.recipe.MekUtRecipeConstants;
import com.takenokoshi.mekut.recipe.recipe.prefab.BiChemicalToItemRecipe;
import com.takenokoshi.mekut.recipe.recipe.prefab.ChemicalToChemicalHeatRecipe;
import com.takenokoshi.mekut.recipe.recipe.prefab.FluidToItemRecipe;
import com.takenokoshi.mekut.recipe.recipe.prefab.GreenHouseRecipe;
import com.takenokoshi.mekut.recipe.recipe.prefab.ItemStackListFluidChemicalToItemFluidChemicalRecipe;
import com.takenokoshi.mekut.recipe.recipe.prefab.ItemStackListFluidChemicalToItemRecipe;
import com.takenokoshi.mekut.recipe.type.WrappedRecipeType;
import com.takenokoshi.mekut.registries.MekUtMachines;
import com.takenokoshi.mekut.registries.MekUtRecipeTypes;

import mekanism.api.recipes.ChemicalChemicalToChemicalRecipe;
import mekanism.api.recipes.ChemicalToChemicalRecipe;
import mekanism.api.recipes.ItemStackChemicalToItemStackRecipe;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.ItemLike;

public class MekUtRecipeViewerRecipeType {

    public static final RVMekALRecipeTypeWrapper<?, ItemStackChemicalToItemStackRecipe, ?> CHEMICAL_CUT = new RVMekALRecipeTypeWrapper<>(
            MekUtConstants.rl(MekUtRecipeConstants.CHEMICAL_CUT),
            ItemStackChemicalToItemStackRecipe.class,
            MekUtRecipeTypes.CHEMICAL_CUT, -28, -16, 144, 54,
            MekUtMachines.CHEMICAL_CUTTER);

    public static final RVMekALRecipeTypeWrapper<?, GreenHouseRecipe, ?> GREEN_HOUSE = new RVMekALRecipeTypeWrapper<>(
            MekUtConstants.rl(MekUtRecipeConstants.GREEN_HOUSE),
            GreenHouseRecipe.class,
            MekUtRecipeTypes.GREEN_HOUSE, -28, -10, 198, 60,
            MekUtMachines.GREEN_HOUSE);

    public static final RVMekALRecipeTypeWrapper<?, FluidToItemRecipe, ?> ICE_MAKING = new RVMekALRecipeTypeWrapper<>(
            MekUtConstants.rl(MekUtRecipeConstants.ICE_MAKING),
            FluidToItemRecipe.class,
            MekUtRecipeTypes.ICE_MAKING, -5, -3, 147, 79,
            MekUtMachines.ICE_MAKER);

    public static final RVMekALRecipeTypeWrapper<?, ChemicalChemicalToChemicalRecipe, ?> LAZER_COMPRESS = new RVMekALRecipeTypeWrapper<>(
            MekUtConstants.rl(MekUtRecipeConstants.LAZER_COMPRESS),
            ChemicalChemicalToChemicalRecipe.class,
            MekUtRecipeTypes.LAZER_COMPRESS, -3, -3, 170, 80,
            MekUtMachines.LAZER_COMPRESS_NUCLEO_SYNTHESIZER);

    public static final RVMekALRecipeTypeWrapper<?, ItemStackListFluidChemicalToItemRecipe, ?> SMALL_DIGITAL_ASSEMBLER = new RVMekALRecipeTypeWrapper<>(
            MekUtConstants.rl(MekUtRecipeConstants.SMALL_DIGITAL_ASSEMBLER),
            ItemStackListFluidChemicalToItemRecipe.class,
            MekUtRecipeTypes.SMALL_DIGITAL_ASSEMBLER, 0, -16, 208, 59,
            MekUtMachines.SMALL_DIGITAL_ASSEMBLER);

    public static final RVMekALRecipeTypeWrapper<?, ItemStackListFluidChemicalToItemFluidChemicalRecipe, ?> SMALL_DIGITAL_REACTION_CHAMBER = new RVMekALRecipeTypeWrapper<>(
            MekUtConstants.rl(MekUtRecipeConstants.SMALL_DIGITAL_REACTION_CHAMBER),
            ItemStackListFluidChemicalToItemFluidChemicalRecipe.class,
            MekUtRecipeTypes.SMALL_DIGITAL_REACTION_CHAMBER, 0, -16, 232, 59,
            MekUtMachines.SMALL_DIGITAL_REACTION_CHAMBER);

    public static final RVMekALRecipeTypeWrapper<?, ChemicalToChemicalRecipe, ?> SPS = new RVMekALRecipeTypeWrapper<>(
            MekUtConstants.rl(MekUtRecipeConstants.SPS),
            ChemicalToChemicalRecipe.class,
            MekUtRecipeTypes.SPS, -4, -13, 168, 60,
            MekUtMachines.COMPACT_SUPERCRITICAL_PHASE_SHIFTER);

    public static final RVMekALRecipeTypeWrapper<?, BiChemicalToItemRecipe, ?> STELLAR_GENESIS = new RVMekALRecipeTypeWrapper<>(
            MekUtConstants.rl(MekUtRecipeConstants.STELLAR_GENESIS),
            BiChemicalToItemRecipe.class,
            MekUtRecipeTypes.STELLAR_GENESIS, -3, -3, 170, 80,
            MekUtMachines.STELLAR_GENESIS_CHAMBER);

    public static final RVMekALRecipeTypeWrapper<?, ChemicalToChemicalHeatRecipe, ?> FISSION_REACTOR = new RVMekALRecipeTypeWrapper<>(
            MekUtConstants.rl(MekUtRecipeConstants.FISSION_REACTOR),
            ChemicalToChemicalHeatRecipe.class,
            MekUtRecipeTypes.FISSION_REACTOR, -4, -13, 168, 60,
            MekUtMachines.COMPACT_FISSION_REACTOR);

    public static final RVMekALRecipeTypeWrapper<?, SmeltingRecipe, ?> TWEAKED_SMELLTING = new RVMekALRecipeTypeWrapper<>(
            MekUtConstants.rl("smelting"),
            SmeltingRecipe.class,
            WrappedRecipeType.VANILLA_SMELTING, -28, -16, 152, 54,
            MekUtMachines.TWEAKED_ENERGIZED_SMELTER,
            MekUtMachines.ENERGIZED_SMELTING_FACTORIES.values().toArray(ItemLike[]::new));
}
