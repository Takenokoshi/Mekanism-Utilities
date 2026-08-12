package com.takenokoshi.mekut.registries;

import com.takenokoshi.mekut.core.MekUtConstants;
import mekanism.api.chemical.Chemical;
import mekanism.api.chemical.ChemicalBuilder;
import mekanism.common.registration.impl.ChemicalDeferredRegister;
import mekanism.common.registration.impl.DeferredChemical;

public class MekUtChemicals {
    public static final ChemicalDeferredRegister CHEMICALS = new ChemicalDeferredRegister(MekUtConstants.MODID);

    public static final DeferredChemical<?> REFINED_LAPIS_LAZULI = CHEMICALS.registerInfuse("refined_lapis_lazuli", 0x1800A8);
    public static final DeferredChemical<?> XP = CHEMICALS.register("xp", 0x7f53ff00);
    public static final DeferredChemical<?> ENRICHED_XP = CHEMICALS.register("enriched_xp", 0x7f29ff00);
    public static final DeferredChemical<?> ASTRAL_ETHER = CHEMICALS.register("astral_ether", 0xD4A1FF);
    public static final DeferredChemical<?> AMETHYST = CHEMICALS.registerInfuse("amethyst", 0x7A73B8);
    public static final DeferredChemical<?> GLOWSTONE = CHEMICALS.registerInfuse("glowstone", 0xFFBC5E);
    public static final DeferredChemical<?> IRIDIUM = CHEMICALS.register("iridium", 0xC4CCD8);
    public static final DeferredChemical<?> NETHERITE = CHEMICALS.register("netherite", 0x5A4E52);

    public static final DeferredChemical<?> CLEAN_AMETHYST_SLURRY = CHEMICALS.register(
            "clean_amethyst_slurry", () -> new Chemical(ChemicalBuilder.cleanSlurry().tint(0x7FA361FF)));
    public static final DeferredChemical<?> CLEAN_CERTUS_QUARTZ_SLURRY = CHEMICALS.register(
            "clean_certus_quartz_slurry", () -> new Chemical(ChemicalBuilder.cleanSlurry().tint(0x7FC9F2FF)));
    public static final DeferredChemical<?> CLEAN_COAL_SLURRY = CHEMICALS.register(
            "clean_coal_slurry", () -> new Chemical(ChemicalBuilder.cleanSlurry().tint(0x7FD2D2D)));
    public static final DeferredChemical<?> CLEAN_DIAMOND_SLURRY = CHEMICALS.register(
            "clean_diamond_slurry", () -> new Chemical(ChemicalBuilder.cleanSlurry().tint(0x7F5CDBD5)));
    public static final DeferredChemical<?> CLEAN_EMERALD_SLURRY = CHEMICALS.register(
            "clean_emerald_slurry", () -> new Chemical(ChemicalBuilder.cleanSlurry().tint(0x7F11C95A)));
    public static final DeferredChemical<?> CLEAN_ENTRO_SLURRY = CHEMICALS.register(
            "clean_entro_slurry", () -> new Chemical(ChemicalBuilder.cleanSlurry().tint(0x7F03B99A)));
    public static final DeferredChemical<?> CLEAN_FLUORITE_SLURRY = CHEMICALS.register(
            "clean_fluorite_slurry", () -> new Chemical(ChemicalBuilder.cleanSlurry().tint(0x7F78FFBE)));
    public static final DeferredChemical<?> CLEAN_LAPIS_LAZULI_SLURRY = CHEMICALS.register(
            "clean_lapis_lazuli_slurry", () -> new Chemical(ChemicalBuilder.cleanSlurry().tint(0x7F2661DB)));
    public static final DeferredChemical<?> CLEAN_NETHERITE_SLURRY = CHEMICALS.register(
            "clean_netherite_slurry", () -> new Chemical(ChemicalBuilder.cleanSlurry().tint(0x7F433D47)));
    public static final DeferredChemical<?> CLEAN_OVERLOAD_SLURRY = CHEMICALS.register(
            "clean_overload_slurry", () -> new Chemical(ChemicalBuilder.cleanSlurry().tint(0x7FFFA8FD)));
    public static final DeferredChemical<?> CLEAN_QUARTZ_SLURRY = CHEMICALS.register(
            "clean_quartz_slurry", () -> new Chemical(ChemicalBuilder.cleanSlurry().tint(0x7FF5E6DC)));
    public static final DeferredChemical<?> CLEAN_REDSTONE_SLURRY = CHEMICALS.register(
            "clean_redstone_slurry", () -> new Chemical(ChemicalBuilder.cleanSlurry().tint(0x7FC81E1E)));
    public static final DeferredChemical<?> CLEAN_SILICON_SLURRY = CHEMICALS.register(
            "clean_silicon_slurry", () -> new Chemical(ChemicalBuilder.cleanSlurry().tint(0x7F858585)));

    public static final DeferredChemical<?> DIRTY_AMETHYST_SLURRY = CHEMICALS.register(
            "dirty_amethyst_slurry", () -> new Chemical(ChemicalBuilder.dirtySlurry().tint(0x7FA361FF)));
    public static final DeferredChemical<?> DIRTY_CERTUS_QUARTZ_SLURRY = CHEMICALS.register(
            "dirty_certus_quartz_slurry", () -> new Chemical(ChemicalBuilder.dirtySlurry().tint(0x7FC9F2FF)));
    public static final DeferredChemical<?> DIRTY_COAL_SLURRY = CHEMICALS.register(
            "dirty_coal_slurry", () -> new Chemical(ChemicalBuilder.dirtySlurry().tint(0x7FD2D2D)));
    public static final DeferredChemical<?> DIRTY_DIAMOND_SLURRY = CHEMICALS.register(
            "dirty_diamond_slurry", () -> new Chemical(ChemicalBuilder.dirtySlurry().tint(0x7F5CDBD5)));
    public static final DeferredChemical<?> DIRTY_EMERALD_SLURRY = CHEMICALS.register(
            "dirty_emerald_slurry", () -> new Chemical(ChemicalBuilder.dirtySlurry().tint(0x7F11C95A)));
    public static final DeferredChemical<?> DIRTY_ENTRO_SLURRY = CHEMICALS.register(
            "dirty_entro_slurry", () -> new Chemical(ChemicalBuilder.dirtySlurry().tint(0x7F03B99A)));
    public static final DeferredChemical<?> DIRTY_FLUORITE_SLURRY = CHEMICALS.register(
            "dirty_fluorite_slurry", () -> new Chemical(ChemicalBuilder.dirtySlurry().tint(0x7F78FFBE)));
    public static final DeferredChemical<?> DIRTY_LAPIS_LAZULI_SLURRY = CHEMICALS.register(
            "dirty_lapis_lazuli_slurry", () -> new Chemical(ChemicalBuilder.dirtySlurry().tint(0x7F2661DB)));
    public static final DeferredChemical<?> DIRTY_NETHERITE_SLURRY = CHEMICALS.register(
            "dirty_netherite_slurry", () -> new Chemical(ChemicalBuilder.dirtySlurry().tint(0x7F433D47)));
    public static final DeferredChemical<?> DIRTY_OVERLOAD_SLURRY = CHEMICALS.register(
            "dirty_overload_slurry", () -> new Chemical(ChemicalBuilder.dirtySlurry().tint(0x7FFFA8FD)));
    public static final DeferredChemical<?> DIRTY_QUARTZ_SLURRY = CHEMICALS.register(
            "dirty_quartz_slurry", () -> new Chemical(ChemicalBuilder.dirtySlurry().tint(0x7FF5E6DC)));
    public static final DeferredChemical<?> DIRTY_REDSTONE_SLURRY = CHEMICALS.register(
            "dirty_redstone_slurry", () -> new Chemical(ChemicalBuilder.dirtySlurry().tint(0x7FC81E1E)));
    public static final DeferredChemical<?> DIRTY_SILICON_SLURRY = CHEMICALS.register(
            "dirty_silicon_slurry", () -> new Chemical(ChemicalBuilder.dirtySlurry().tint(0x7F858585)));
}
