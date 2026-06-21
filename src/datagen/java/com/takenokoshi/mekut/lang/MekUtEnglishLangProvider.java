package com.takenokoshi.mekut.lang;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.takenokoshi.mekut.core.MekUtConstants;
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
            add(machine.getBlockObject().get(), format(machine.getBlockObject().getId().getPath()));
            add("container.mekanism_utilities." + machine.getBlockObject().getId().getPath(),
                    format(machine.getBlockObject().getId().getPath()));
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
        MekUtChemicals.MU_MATERIALS_CLEAN_SLURRY.forEach((material, registry) -> {
            add(registry.getTranslationKey(), format(registry.getId().getPath()));
        });
        MekUtChemicals.MU_MATERIALS_DIRTY_SLURRY.forEach((material, registry) -> {
            add(registry.getTranslationKey(), format(registry.getId().getPath()));
        });
        addChemical(MekUtChemicals.SINGULARITY);
        addChemical(MekUtChemicals.XP);
        addChemical(MekUtChemicals.HEAVY_WATER_STEAM);
        addChemical(MekUtChemicals.BLAZE_ETHER);
        addChemical(MekUtChemicals.FLUIX);
        addChemical(MekUtChemicals.ASTRAL_ETHER);
        addChemical(MekUtChemicals.IRIDIUM);
        addChemical(MekUtChemicals.NETHERITE);
        addLang(MekUtLang.MOD_NAME, "Mekanism:Utilities");
        addLang(MekUtDescription.AMETHYST_ORE,
                "Unrecorded ore that should only form in places outside the laws of this world.\\nNot found in nature.");
        addLang(MekUtDescription.CERTUS_QUARTZ_ORE,
                "Ore that might exist on a planet somewhere in the distant universe.\\nNot found in nature.");
        addLang(MekUtDescription.NETHERITE_ORE,
                "Ore that was likely mined by the former Piglin civilization.\\nNot found in nature.");

        addLang(MekUtDescription.MEKSTYLED_CHARGER,
                "This is a machine that reconstructs the AE2 Charger in the style of Mekanism.\\nThe reaction chamber of the AdvancedAE is probably more convenient.");
        addLang(MekUtDescription.MEKSTYLED_CIRCUIT_CUTTER,
                "This is a machine that reconstructs the ExtendedAE Circuit Cutter in the style of Mekanism.");
        addLang(MekUtDescription.SUBMATERIAL_CONVERTER,
                "This Machine can Convert Submaterial for Metallurgic Infuser & Osmium Compressor.");
        addLang(MekUtDescription.TWEAKED_ENERGIZED_SMELTER,
                "This is an energized smelter machine that allows you to gain xp through smelting.");
        addLang(MekUtDescription.STANDARD_MACHINE,
                "This machine has a higher processing speed than conventional machines.");
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
