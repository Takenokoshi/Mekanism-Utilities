package com.takenokoshi.mekut.registries;

import com.mojang.serialization.MapCodec;
import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.lootmodifier.ItemReplaceLootModifier;

import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class MekUtLootModifiers {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister
            .create(
                    NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS,
                    MekUtConstants.MODID);

    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<ItemReplaceLootModifier>> ITEM_REPLACE = LOOT_MODIFIERS
            .register("item_replace", () -> ItemReplaceLootModifier.MAP_CODEC);
}
