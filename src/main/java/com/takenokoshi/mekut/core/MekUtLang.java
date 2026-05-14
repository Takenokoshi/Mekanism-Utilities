package com.takenokoshi.mekut.core;

import mekanism.api.text.ILangEntry;
import net.minecraft.Util;

public class MekUtLang implements ILangEntry {

    private final String key;

    public MekUtLang(String key) {
        this.key = key;
    }

    public MekUtLang(String type, String path) {
        this(Util.makeDescriptionId(type, MekUtConstants.rl(path)));
    }

    @Override
    public String getTranslationKey() {
        return key;
    }
}
