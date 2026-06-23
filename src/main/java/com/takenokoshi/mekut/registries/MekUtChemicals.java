package com.takenokoshi.mekut.registries;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.enums.MUMaterial;

import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalBuilder;
import mekanism.common.registration.impl.ChemicalDeferredRegister;
import mekanism.common.registration.impl.DeferredChemical;

public class MekUtChemicals {
    public static final ChemicalDeferredRegister CHEMICALS = new ChemicalDeferredRegister(MekUtConstants.MODID);

    public static final DeferredChemical<?> SINGULARITY = CHEMICALS.registerInfuse("singularity", 0x1800a8);
    public static final DeferredChemical<?> XP = CHEMICALS.register("xp", 0x7f53ff00);
    public static final DeferredChemical<?> ENRICHED_XP = CHEMICALS.register("enriched_xp", 0x7f29ff00);
    public static final DeferredChemical<?> ASTRAL_ETHER = CHEMICALS.register("astral_ether", 0xD4A1FF);
    public static final DeferredChemical<?> FLUIX = CHEMICALS.registerInfuse("fluix", 0x7A73B8);
    public static final DeferredChemical<?> IRIDIUM = CHEMICALS.register("iridium", 0xC4CCD8);
    public static final DeferredChemical<?> NETHERITE = CHEMICALS.register("netherite", 0x5A4E52);

    public static final DeferredChemical<?> HEAVY_WATER_STEAM = CHEMICALS.register("heavy_water_steam", 0xFF0D1455);

    public static final Map<MUMaterial, DeferredChemical<?>> MU_MATERIALS_CLEAN_SLURRY = registerMaterials(
            m -> "clean_" + m.name + "_slurry",
            m -> new Chemical(ChemicalBuilder.cleanSlurry().tint(0x7f000000 + m.rgbColor)));

    public static final Map<MUMaterial, DeferredChemical<?>> MU_MATERIALS_DIRTY_SLURRY = registerMaterials(
            m -> "dirty_" + m.name + "_slurry",
            m -> new Chemical(ChemicalBuilder.dirtySlurry().tint(0x7f000000 + m.rgbColor)));

    private static Map<MUMaterial, DeferredChemical<?>> registerMaterials(
            Function<MUMaterial, String> nameBulder,
            Function<MUMaterial, Chemical> creator) {
        EnumMap<MUMaterial, DeferredChemical<?>> result = new EnumMap<>(MUMaterial.class);
        for (MUMaterial material : MUMaterial.values()) {
            result.put(material, CHEMICALS.register(nameBulder.apply(material), () -> creator.apply(material)));
        }
        return Collections.unmodifiableMap(result);
    }
}
