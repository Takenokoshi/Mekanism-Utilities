package com.takenokoshi.mekut.core;

import net.minecraft.resources.ResourceLocation;

public class MekUtConstants {
    public static final String MODID = "mekanism_utilities";

    public static ResourceLocation rl(String path){
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
