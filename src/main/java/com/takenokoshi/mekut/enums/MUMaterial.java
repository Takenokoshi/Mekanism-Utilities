package com.takenokoshi.mekut.enums;

public enum MUMaterial {
    REDSTONE("redstone", 0xC81E1E, false),
    DIAMOND("diamond", 0x5CDBD5, true),
    EMERALD("emerald", 0x11C95A, true),
    LAPIS_LAZULI("lapis_lazuli", 0x2661DB, true),
    QUARTZ("quartz", 0xF5E6DC, true),
    AMETHYST("amethyst", 0xA361FF, true),
    CERTUS_QUARTZ("certus_quartz", 0xC9F2FF, true),
    FLUORITE("fluorite", 0x78FFBE, true),
    COAL("coal", 0x2D2D2D, true),
    NETHERITE("netherite", 0x433D47, false),
    ;

    public final String name;
    public final int rgbColor;
    public final boolean isGem;

    private MUMaterial(String name, int rgbColor, boolean isGem) {
        this.name = name;
        this.rgbColor = rgbColor;
        this.isGem = isGem;
    }
}
