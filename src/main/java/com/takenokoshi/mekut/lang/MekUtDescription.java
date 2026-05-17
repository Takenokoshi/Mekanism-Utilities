package com.takenokoshi.mekut.lang;

public class MekUtDescription extends MekUtLang {

    protected MekUtDescription(String path) {
        super("description", path);
    }

    public static final MekUtDescription AMETHYST_ORE = new MekUtDescription("amethyst_ore");
    public static final MekUtDescription CERTUS_QUARTZ_ORE = new MekUtDescription("certus_quartz_ore");
    public static final MekUtDescription NETHERITE_ORE = new MekUtDescription("netherite_ore");

    public static final MekUtDescription TWEAKED_ENERGIZED_SMELTER = new MekUtDescription("tweaked_energized_smelter");

}
