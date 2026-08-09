package com.takenokoshi.mekut.lang;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.enums.MekUtDataType;
import com.takenokoshi.mekut.registries.MekUtBlocks;
import com.takenokoshi.mekut.registries.MekUtChemicals;
import com.takenokoshi.mekut.registries.MekUtFluids;
import com.takenokoshi.mekut.registries.MekUtItems;
import com.takenokoshi.mekut.registries.MekUtMachines;

import mekanism.api.text.IHasTranslationKey;
import mekanism.common.registration.impl.DeferredChemical;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class MekUtEnglishLangProvider extends LanguageProvider {

    public MekUtEnglishLangProvider(PackOutput output) {
        super(output, MekUtConstants.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        MekUtItems.ITEMS.getEntries().forEach(holder -> {
            add(holder.get(), format(holder.getId().getPath()));
        });
        MekUtBlocks.BLOCKS.getPrimaryEntries().forEach(holder -> {
            add(holder.get(), format(holder.getId().getPath()));
        });
        MekUtMachines.MACHINES.getMachines().forEach(machine -> {
            add(machine.getBlock().get(), format(machine.getBlock().getId().getPath()));
            add("container.mekanism_utilities." + machine.getBlock().getId().getPath(),
                    format(machine.getBlock().getId().getPath()));
        });
        MekUtFluids.FLUIDS.getBlockEntries().forEach(holder -> {
            add(holder.get(), format(holder.getId().getPath()));
        });
        MekUtFluids.FLUIDS.getBucketEntries().forEach(holder -> {
            add(holder.get(), format(holder.getId().getPath()));
        });
        MekUtFluids.FLUIDS.getFluidEntries().forEach(holder -> {
            add("fluid.mekanism_utilities." + holder.getId().getPath(), format(holder.getId().getPath()));
        });
        addChemical(MekUtChemicals.SINGULARITY);
        addChemical(MekUtChemicals.ENRICHED_XP);
        addChemical(MekUtChemicals.XP);
        addChemical(MekUtChemicals.FLUIX);
        addChemical(MekUtChemicals.ASTRAL_ETHER);
        addChemical(MekUtChemicals.IRIDIUM);
        addChemical(MekUtChemicals.NETHERITE);
        addLang(MekUtLang.MOD_NAME, "Mekanism:Utilities");
        addLang(MekUtLang.CREATIVE_TAB, "Mekanism:Utilities");
        addLang(MekUtDescription.AMETHYST_ORE,
                "Unrecorded ore that should only form in places outside the laws of this world.\\nNot found in nature.");
        addLang(MekUtDescription.CERTUS_QUARTZ_ORE,
                "Ore that might exist on a planet somewhere in the distant universe.\\nNot found in nature.");
        addLang(MekUtDescription.ENTRO_ORE,
                "Ore that might exist on a planet somewhere in the distant universe.\\nNot found in nature.");
        addLang(MekUtDescription.NETHERITE_ORE,
                "Ore that was likely mined by the former Piglin civilization.\\nNot found in nature.");

        addLang(MekUtMachines.COMPACT_BOILER.descriptionEntry, "Thermoelectric boiler in a single block size");
        addLang(MekUtMachines.COMPACT_FISSION_REACTOR.descriptionEntry, "Fission Reactor in a single block size");
        addLang(MekUtMachines.COMPACT_FUSION_REACTOR.descriptionEntry, "Fusion Reactor in a single block size");
        addLang(MekUtMachines.COMPACT_INDUSTRIAL_TURBINE.descriptionEntry, "Industrial Turbine in a single block size");
        addLang(MekUtMachines.COMPACT_SUPERCRITICAL_PHASE_SHIFTER.descriptionEntry, "SPS in a single block size");
        addLang(MekUtMachines.COMPACT_THERMAL_EVAPOLATION_PLANT.descriptionEntry,
                "Thermal Evaporation Plant in a single block size");
        addLang(MekUtMachines.CHEMICAL_CUTTER.descriptionEntry,
                "This is a machine that creates AE2 printed processor from Mekanism crystals.");
        addLang(MekUtMachines.ICE_MAKER.descriptionEntry,
                "A machine that lives up to its name: one that cools water to produce ice.");
        addLang(MekUtMachines.LAZER_COMPRESS_NUCLEO_SYNTHESIZER.descriptionEntry,
                "A machine that triggers a special nuclear reaction through precise laser control.");
        addLang(MekUtMachines.STELLAR_GENESIS_CHAMBER.descriptionEntry,
                "A machine that simulates the birth of a star originating from an accumulation of interstellar matter.");
        addLang(MekUtMachines.SMALL_DIGITAL_ASSEMBLER.descriptionEntry,
                "An advanced machine that automatically performs specialized crafting tasks.\\nIt can also handle assembring in ExtendedAE's Crystal Assembler.");
        addLang(MekUtMachines.SMALL_DIGITAL_REACTION_CHAMBER.descriptionEntry,
                "An advanced machine for efficient material synthesis.\\nIt can also handle material synthesis in Mekanism's Pressurized Reaction Chamber and AdvancedAE's reaction chamber.");
        addLang(MekUtMachines.SUBMATERIAL_CONVERTER.descriptionEntry,
                "This Machine can Convert Submaterial for Metallurgic Infuser & Osmium Compressor.");
        addLang(MekUtMachines.TWEAKED_ENERGIZED_SMELTER.descriptionEntry,
                "This is an energized smelter machine that allows you to gain xp through smelting.");
        addLang(MekUtMachines.XP_TANK.descriptionEntry, "Tank for xp. You can convert chemical xp to player xp.");

        
        addLang(MekUtMachines.ITEM_RATIO_SPLITTER.descriptionEntry, "Splits input items into two outputs according to a configurable ratio.");
        addLang(MekUtMachines.FLUID_RATIO_SPLITTER.descriptionEntry, "Splits input fluid into two outputs according to a configurable ratio.");
        addLang(MekUtMachines.CHEMICAL_RATIO_SPLITTER.descriptionEntry, "Splits input chemical into two outputs according to a configurable ratio.");

        addLang(MekUtDataType.INPUT1_OUTPUT1, "Input(1)・Output(1)");
        addLang(MekUtDataType.INPUT2_OUTPUT2, "Input(2)・Output(2)");
        addLang(MekUtDataType.INPUT_OUTPUT1, "Input・Output(1)");
        addLang(MekUtDataType.INPUT_OUTPUT2, "Input・Output(2)");
    }

    private String format(String name) {

        String[] split = name.split("_");

        return Arrays.stream(split)
                .map(s -> Character.toUpperCase(s.charAt(0)) + s.substring(1))
                .collect(Collectors.joining(" "));
    }

    private void addChemical(DeferredChemical<?> chemical) {
        add(chemical.getTranslationKey(), format(chemical.getId().getPath()));
    }

    private void addLang(IHasTranslationKey langEntry, String translation) {
        add(langEntry.getTranslationKey(), translation);
    }

}
