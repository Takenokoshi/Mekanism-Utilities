package com.takenokoshi.mekut.recipe.building;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.fxd927.mekanismelements.common.registries.MSItems;
import com.glodblock.github.extendedae.common.EAESingletons;
import com.jerry.mekaf.common.content.blocktype.AdvancedFactoryType;
import com.jerry.mekaf.common.registries.AdvancedFactoryBlocks;
import com.jerry.mekmm.Mekmm;
import com.jerry.mekmm.common.content.blocktype.MoreMachineFactoryType;
import com.jerry.mekmm.common.registries.MoreMachineBlocks;
import com.jerry.mekmm.common.util.MoreMachineEnumUtils;
import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.registration.MachineRegistryObject;
import com.takenokoshi.mekut.registries.MekUtItems;
import com.takenokoshi.mekut.registries.MekUtMachines;

import appeng.core.definitions.AEBlocks;
import appeng.core.definitions.AEItems;
import fr.iglee42.emgenerators.registries.EMGenBlocks;
import fr.iglee42.evolvedmekanism.EvolvedMekanism;
import fr.iglee42.evolvedmekanism.registries.EMBlocks;
import fr.iglee42.evolvedmekanism.registries.EMItems;
import fr.iglee42.evolvedmekanism.tiers.EMFactoryTier;
import mekanism.common.content.blocktype.FactoryType;
import mekanism.common.registries.MekanismBlocks;
import mekanism.common.registries.MekanismItems;
import mekanism.common.util.EnumUtils;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.pedroksl.advanced_ae.common.definitions.AAEBlocks;
import net.pedroksl.advanced_ae.common.definitions.AAEItems;

public class BuildCraftingRecipe {

    private static final List<SimpleMachineRecipeData> NORMAL_MACHINES = new ArrayList<>();
    private static final List<SimpleMachineRecipeData> TWEAKED_MACHINES = new ArrayList<>();
    private static final List<SimpleMachineRecipeData> EVOMEK_CREATIVE_FACTORIES = new ArrayList<>();
    private static final List<SimpleMachineRecipeData> MEKMM_CREATIVE_FACTORIES = new ArrayList<>();

    public static void build(RecipeOutput output,
            Function<ItemLike, Criterion<InventoryChangeTrigger.TriggerInstance>> has) {
        ShapedRecipeBuilder
                .shaped(RecipeCategory.REDSTONE, MekUtItems.DIGITAL_CONTROL_CIRCUIT)
                .define('A', MekUtItems.ELASTIC_ALLOY)
                .define('B', MekanismItems.BASIC_CONTROL_CIRCUIT)
                .pattern("ABA")
                .unlockedBy("unlock", has.apply(MekUtItems.ELASTIC_ALLOY))
                .save(output, MekUtConstants.rl("crafting/digital_control_circuit"));
        ShapedRecipeBuilder
                .shaped(RecipeCategory.REDSTONE, MekUtItems.STANDARD_CONTROL_CIRCUIT)
                .define('A', MekUtItems.CONVERGENT_ALLOY)
                .define('B', MekUtItems.DIGITAL_CONTROL_CIRCUIT)
                .pattern("ABA")
                .unlockedBy("unlock", has.apply(MekUtItems.CONVERGENT_ALLOY))
                .save(output, MekUtConstants.rl("crafting/standard_control_circuit"));
        ShapedRecipeBuilder
                .shaped(RecipeCategory.REDSTONE, MekUtItems.KNOWLEDGE_CONTROL_CIRCUIT)
                .define('A', MekUtItems.XP_ALLOY)
                .define('B', MekUtItems.STANDARD_CONTROL_CIRCUIT)
                .pattern("ABA")
                .unlockedBy("unlock", has.apply(MekUtItems.XP_ALLOY))
                .save(output, MekUtConstants.rl("crafting/knowledge_control_circuit"));
        ShapedRecipeBuilder
                .shaped(RecipeCategory.REDSTONE, MekUtMachines.CHEMICAL_CUTTER)
                .define('C', MekanismBlocks.STEEL_CASING)
                .define('A', MekanismItems.REINFORCED_ALLOY)
                .define('B', MekUtItems.STANDARD_CONTROL_CIRCUIT)
                .define('D', EAESingletons.CIRCUIT_CUTTER)
                .pattern("ABA")
                .pattern("DCD")
                .pattern("ABA")
                .unlockedBy("unlock", has.apply(EAESingletons.CIRCUIT_CUTTER))
                .save(output, MekUtConstants.rl("crafting/machine/chemical_cutter"));
        ShapedRecipeBuilder
                .shaped(RecipeCategory.REDSTONE, MekUtMachines.ICE_MAKER)
                .define('C', MekanismBlocks.STEEL_CASING)
                .define('A', ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ingots/copper")))
                .define('B', MekUtItems.DIGITAL_CONTROL_CIRCUIT)
                .define('D', AEItems.ENTROPY_MANIPULATOR)
                .pattern("ABA")
                .pattern("DCD")
                .pattern("ABA")
                .unlockedBy("unlock", has.apply(Items.ICE))
                .save(output, MekUtConstants.rl("crafting/machine/ice_maker"));
        ShapedRecipeBuilder
                .shaped(RecipeCategory.REDSTONE, MekUtMachines.LAZER_COMPRESS_NUCLEO_SYNTHESIZER)
                .define('C', MekanismBlocks.STEEL_CASING)
                .define('A', ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ingots/netherite")))
                .define('B', MekUtItems.KNOWLEDGE_CONTROL_CIRCUIT)
                .define('D', MekanismBlocks.LASER)
                .pattern("ABA")
                .pattern("DCD")
                .pattern("ABA")
                .unlockedBy("unlock", has.apply(MekanismBlocks.LASER))
                .save(output, MekUtConstants.rl("crafting/machine/lazer_compress_nucleo_synthesizer"));
        ShapedRecipeBuilder
                .shaped(RecipeCategory.REDSTONE, MekUtMachines.SMALL_DIGITAL_ASSEMBLER)
                .define('C', MekanismBlocks.STEEL_CASING)
                .define('A', ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ingots/bronze")))
                .define('B', MekUtItems.DIGITAL_CONTROL_CIRCUIT)
                .define('D', EAESingletons.CRYSTAL_ASSEMBLER)
                .pattern("ABA")
                .pattern("DCD")
                .pattern("ABA")
                .unlockedBy("unlock", has.apply(EAESingletons.CRYSTAL_ASSEMBLER))
                .save(output, MekUtConstants.rl("crafting/machine/small_digital_assembler"));
        ShapedRecipeBuilder
                .shaped(RecipeCategory.REDSTONE, MekUtMachines.SMALL_DIGITAL_REACTION_CHAMBER)
                .define('C', MekanismBlocks.STEEL_CASING)
                .define('A', ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ingots/steel")))
                .define('B', MekUtItems.STANDARD_CONTROL_CIRCUIT)
                .define('D', AAEBlocks.REACTION_CHAMBER)
                .define('E', MekanismBlocks.PRESSURIZED_REACTION_CHAMBER)
                .pattern("ABA")
                .pattern("DCE")
                .pattern("ABA")
                .unlockedBy("unlock", has.apply(AAEBlocks.REACTION_CHAMBER))
                .save(output, MekUtConstants.rl("crafting/machine/small_digital_reaction_chamber"));
        ShapedRecipeBuilder
                .shaped(RecipeCategory.REDSTONE, MekUtMachines.STELLAR_GENESIS_CHAMBER)
                .define('C', MekanismBlocks.STEEL_CASING)
                .define('A', MekUtItems.STARDUST_ALLOY)
                .define('B', MekUtItems.COMET_CONTROL_CIRCUIT)
                .define('D', MekanismItems.ANTIMATTER_PELLET)
                .pattern("ABA")
                .pattern("DCD")
                .pattern("ABA")
                .unlockedBy("unlock", has.apply(MekUtItems.COMET_CONTROL_CIRCUIT))
                .save(output, MekUtConstants.rl("crafting/machine/stellar_genesis_chamber"));
        ShapedRecipeBuilder
                .shaped(RecipeCategory.MISC, Items.BEACON)
                .define('G', Items.GLASS)
                .define('S', MekUtItems.ARTIFICIAL_STAR)
                .define('O', Items.OBSIDIAN)
                .pattern("GGG")
                .pattern("GSG")
                .pattern("OOO")
                .unlockedBy("unlock", has.apply(MekUtItems.COMET_CONTROL_CIRCUIT))
                .save(output, MekUtConstants.rl("crafting/artificial_star/beacon"));
        ShapedRecipeBuilder
                .shaped(RecipeCategory.MISC, MekanismItems.MODULE_GRAVITATIONAL_MODULATING)
                .define('A', MekanismItems.ATOMIC_ALLOY)
                .define('S', MekUtItems.ARTIFICIAL_STAR)
                .define('I', MekanismBlocks.ULTIMATE_INDUCTION_PROVIDER)
                .define('B', MekanismItems.MODULE_BASE)
                .define('P', MekanismItems.ANTIMATTER_PELLET)
                .pattern("ASA")
                .pattern("IBI")
                .pattern("PPP")
                .unlockedBy("unlock", has.apply(MekUtItems.COMET_CONTROL_CIRCUIT))
                .save(output, MekUtConstants.rl("crafting/artificial_star/gravitational_modulating_unit"));
        ShapedRecipeBuilder
                .shaped(RecipeCategory.MISC, AAEItems.LUCK_CARD)
                .define('A', Items.AMETHYST_BLOCK)
                .define('F', Items.RABBIT_FOOT)
                .define('S', MekUtItems.ARTIFICIAL_STAR)
                .define('B', AAEItems.QUANTUM_UPGRADE_BASE)
                .pattern("AFA")
                .pattern("SBS")
                .pattern("AFA")
                .unlockedBy("unlock", has.apply(MekUtItems.COMET_CONTROL_CIRCUIT))
                .save(output, MekUtConstants.rl("crafting/artificial_star/luck_card"));
        ShapelessRecipeBuilder
                .shapeless(RecipeCategory.DECORATIONS, MSItems.HIGH_QUALITY_CONCRETE_POWDER_AQUA.asStack(8))
                .requires(MSItems.HIGH_QUALITY_CONCRETE_POWDER, 8)
                .requires(MekUtItems.AQUA_DYE, 1)
                .unlockedBy("unlock", has.apply(MekUtItems.AQUA_DYE))
                .save(output, MekUtConstants.rl("crafting/hq_concreate/aqua_powder"));
        ShapelessRecipeBuilder
                .shapeless(RecipeCategory.DECORATIONS, MSItems.HIGH_QUALITY_CONCRETE_POWDER_DARK_RED.asStack(8))
                .requires(MSItems.HIGH_QUALITY_CONCRETE_POWDER, 8)
                .requires(MekUtItems.DARK_RED_DYE, 1)
                .unlockedBy("unlock", has.apply(MekUtItems.DARK_RED_DYE))
                .save(output, MekUtConstants.rl("crafting/hq_concreate/dark_red_powder"));
        NORMAL_MACHINES.forEach(data -> {
            ShapedRecipeBuilder
                    .shaped(RecipeCategory.REDSTONE, data.output)
                    .define('C', MekanismBlocks.STEEL_CASING)
                    .define('A', MekUtItems.ELASTIC_ALLOY)
                    .define('B', MekUtItems.DIGITAL_CONTROL_CIRCUIT)
                    .define('D', data.input)
                    .pattern("ABA")
                    .pattern("DCD")
                    .pattern("ABA")
                    .unlockedBy("unlock", has.apply(data.input))
                    .save(output, MekUtConstants.rl("crafting/machine/" + data.name));
        });
        TWEAKED_MACHINES.forEach(data -> {
            ShapedRecipeBuilder
                    .shaped(RecipeCategory.REDSTONE, data.output)
                    .define('C', data.input)
                    .define('A', MekUtItems.ELASTIC_ALLOY)
                    .define('B', MekUtItems.DIGITAL_CONTROL_CIRCUIT)
                    .define('D', ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "ingots/copper")))
                    .pattern("ABA")
                    .pattern("DCD")
                    .pattern("ABA")
                    .unlockedBy("unlock", has.apply(data.input))
                    .save(output, MekUtConstants.rl("crafting/machine/" + data.name));
        });
        MEKMM_CREATIVE_FACTORIES.forEach(data -> {
            ShapedRecipeBuilder
                    .shaped(RecipeCategory.REDSTONE, data.output)
                    .define('A', EMItems.CREATIVE_ALLOY)
                    .define('C', EMItems.CREATIVE_CONTROL_CIRCUIT)
                    .define('S', MekUtItems.ARTIFICIAL_STAR)
                    .define('F', data.input)
                    .pattern("ACA")
                    .pattern("SFS")
                    .pattern("ACA")
                    .unlockedBy("unlock", has.apply(EMItems.CREATIVE_ALLOY))
                    .save(output.withConditions(new ModLoadedCondition(EvolvedMekanism.MODID),
                            new ModLoadedCondition(Mekmm.MOD_ID)),
                            MekUtConstants.rl("crafting/artificial_star/factory/" + data.name));
        });
        EVOMEK_CREATIVE_FACTORIES.forEach(data -> {
            ShapedRecipeBuilder
                    .shaped(RecipeCategory.REDSTONE, data.output)
                    .define('A', EMItems.CREATIVE_ALLOY)
                    .define('C', EMItems.CREATIVE_CONTROL_CIRCUIT)
                    .define('S', MekUtItems.ARTIFICIAL_STAR)
                    .define('F', data.input)
                    .pattern("ACA")
                    .pattern("SFS")
                    .pattern("ACA")
                    .unlockedBy("unlock", has.apply(EMItems.CREATIVE_ALLOY))
                    .save(output.withConditions(new ModLoadedCondition(EvolvedMekanism.MODID)),
                            MekUtConstants.rl("crafting/artificial_star/factory/" + data.name));
        });
    }

    private static record SimpleMachineRecipeData(String name, ItemLike output, ItemLike input) {
        private SimpleMachineRecipeData(MachineRegistryObject<?, ?, ?, ?> output, ItemLike input) {
            this(output.getId().getPath(), output, input);
        }

        static SimpleMachineRecipeData mekAFCreative(AdvancedFactoryType factoryType) {
            return new SimpleMachineRecipeData(factoryType.getRegistryNameComponent(),
                    AdvancedFactoryBlocks.getAdvancedFactory(EMFactoryTier.CREATIVE, factoryType),
                    AdvancedFactoryBlocks.getAdvancedFactory(EMFactoryTier.MULTIVERSAL, factoryType));
        }

        static SimpleMachineRecipeData mekMMCreative(MoreMachineFactoryType factoryType) {
            return new SimpleMachineRecipeData(factoryType.getRegistryNameComponent(),
                    MoreMachineBlocks.getMoreMachineFactory(EMFactoryTier.CREATIVE, factoryType),
                    MoreMachineBlocks.getMoreMachineFactory(EMFactoryTier.MULTIVERSAL, factoryType));
        }

        static SimpleMachineRecipeData evoMekCreative(FactoryType factoryType) {
            return new SimpleMachineRecipeData(factoryType.getRegistryNameComponent(),
                    EMBlocks.getFactory(EMFactoryTier.CREATIVE, factoryType),
                    EMBlocks.getFactory(EMFactoryTier.MULTIVERSAL, factoryType));
        }
    }

    static {
        NORMAL_MACHINES
                .add(new SimpleMachineRecipeData(MekUtMachines.SUBMATERIAL_CONVERTER, MekanismItems.ENRICHED_GOLD));
        TWEAKED_MACHINES.add(new SimpleMachineRecipeData(MekUtMachines.TWEAKED_ENERGIZED_SMELTER,
                MekanismBlocks.ENERGIZED_SMELTER));
        TWEAKED_MACHINES.add(new SimpleMachineRecipeData(MekUtMachines.MEKSTYLED_CHARGER, AEBlocks.CHARGER));
        for (AdvancedFactoryType factoryType : MoreMachineEnumUtils.ADVANCED_FACTORY_TYPES) {
            MEKMM_CREATIVE_FACTORIES.add(SimpleMachineRecipeData.mekAFCreative(factoryType));
        }
        for (MoreMachineFactoryType factoryType : MoreMachineEnumUtils.MM_FACTORY_TYPES) {
            MEKMM_CREATIVE_FACTORIES.add(SimpleMachineRecipeData.mekMMCreative(factoryType));
        }
        for (FactoryType factoryType : EnumUtils.FACTORY_TYPES) {
            EVOMEK_CREATIVE_FACTORIES.add(SimpleMachineRecipeData.evoMekCreative(factoryType));
        }
        EVOMEK_CREATIVE_FACTORIES.add(new SimpleMachineRecipeData("solar_generator",
                EMGenBlocks.CREATIVE_SOLAR_GENERATOR, EMGenBlocks.MULTIVERSAL_SOLAR_GENERATOR));
    }
}
