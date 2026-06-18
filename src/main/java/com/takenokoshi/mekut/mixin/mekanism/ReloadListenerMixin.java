package com.takenokoshi.mekut.mixin.mekanism;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.takenokoshi.mekut.recipe.type.MekUtRecipeType;

import mekanism.common.ReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;

@Mixin(value = { ReloadListener.class }, remap = true)
public class ReloadListenerMixin {
    @Inject(method = { "onResourceManagerReload" }, at = @At("HEAD"))
    void mekanism_utilities$inject(ResourceManager resourceManager, CallbackInfo info) {
        MekUtRecipeType.clearAllCaches();
    }
}
