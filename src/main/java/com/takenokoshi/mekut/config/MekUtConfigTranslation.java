package com.takenokoshi.mekut.config;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.takenokoshi.mekut.core.MekUtConstants;

import mekanism.common.config.IConfigTranslation;
import mekanism.common.config.TranslationPreset;
import net.minecraft.Util;

public class MekUtConfigTranslation implements IConfigTranslation {

    public static final MekUtConfigTranslation STANDARD_MACHINE_PERFORMANCE = new MekUtConfigTranslation(
            "standard_machine_performance", "Standard Machine Performance",
            "Factor of Standard Machines' processing Speed");

    private final String key;
    private final String title;
    private final String tooltip;
    @Nullable
    private final String button;

    private MekUtConfigTranslation(String path, String title, String tooltip, @Nullable String button) {
        this.key = Util.makeDescriptionId("configuration", MekUtConstants.rl(path));
        this.title = title;
        this.tooltip = tooltip;
        this.button = button;
    }

    private MekUtConfigTranslation(TranslationPreset preset, String type) {
        this(preset.path(type), preset.title(type), preset.tooltip(type));
    }

    private MekUtConfigTranslation(TranslationPreset preset, String type, String tooltipSuffix) {
        this(preset.path(type), preset.title(type), preset.tooltip(type) + tooltipSuffix);
    }

    private MekUtConfigTranslation(String path, String title, String tooltip) {
        this(path, title, tooltip, false);
    }

    private MekUtConfigTranslation(String path, String title, String tooltip, boolean isSection) {
        this(path, title, tooltip, IConfigTranslation.getSectionTitle(title, isSection));
    }

    @NotNull
    @Override
    public String getTranslationKey() {
        return key;
    }

    @Override
    public String title() {
        return title;
    }

    @Override
    public String tooltip() {
        return tooltip;
    }

    @Nullable
    @Override
    public String button() {
        return button;
    }
}
