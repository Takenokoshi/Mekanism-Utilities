package com.takenokoshi.mekut.core;

import mekanism.api.text.ILangEntry;
import net.minecraft.Util;

public class MekUtLang implements ILangEntry {

    private final String key;

    private MekUtLang(String key) {
        this.key = key;
    }

    private MekUtLang(String type, String path) {
        this(Util.makeDescriptionId(type, MekUtConstants.rl(path)));
    }

    @Override
    public String getTranslationKey() {
        return key;
    }

    public static final MekUtLang MOD_NAME = new MekUtLang("mod_name.mekanism_utilities");
}
