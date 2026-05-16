package com.takenokoshi.mekut.lang;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.registries.MekUtBlocks;
import com.takenokoshi.mekut.registries.MekUtItems;

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
    }

    private String format(String name) {

        String[] split = name.split("_");

        return Arrays.stream(split)
                .map(s -> Character.toUpperCase(s.charAt(0)) + s.substring(1))
                .collect(Collectors.joining(" "));
    }

}
