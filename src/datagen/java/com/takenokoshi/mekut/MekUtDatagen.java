package com.takenokoshi.mekut;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.InMemoryCommentedFormat;
import com.electronwill.nightconfig.core.concurrent.SynchronizedConfig;
import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.lang.MekUtEnglishLangProvider;
import com.takenokoshi.mekut.loottable.MekUtLootTableProvider;
import com.takenokoshi.mekut.model.MekUtBlockModelProvider;
import com.takenokoshi.mekut.model.MekUtItemModelProvider;
import com.takenokoshi.mekut.recipe.MekUtRecipeProvider;
import com.takenokoshi.mekut.tag.MekUtBlockTagProvider;
import com.takenokoshi.mekut.tag.MekUtItemTagProvider;

import mekanism.common.lib.FieldReflectionHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider.TagLookup;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.config.ConfigTracker;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.util.ObfuscationReflectionHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = MekUtConstants.MODID, bus = EventBusSubscriber.Bus.MOD)
public class MekUtDatagen {

    @SuppressWarnings("UnstableApiUsage")
    private static final FieldReflectionHelper<ConfigTracker, EnumMap<ModConfig.Type, Set<ModConfig>>> CONFIG_SETS = new FieldReflectionHelper<>(
            ConfigTracker.class, "configSets", () -> new EnumMap<>(ModConfig.Type.class));
    private static final Constructor<?> LOADED_CONFIG;
    private static final Method SET_CONFIG;

    static {
        Class<?> loadedConfig;
        try {
            loadedConfig = Class.forName("net.neoforged.fml.config.LoadedConfig");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        LOADED_CONFIG = ObfuscationReflectionHelper.findConstructor(loadedConfig, CommentedConfig.class, Path.class,
                ModConfig.class);
        SET_CONFIG = ObfuscationReflectionHelper.findMethod(ModConfig.class, "setConfig", loadedConfig, Function.class);
    }

    private MekUtDatagen() {
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        bootstrapConfigs();
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();

        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        MekUtBlockTagProvider blockTagProvider = generator
                .addProvider(true,
                        new MekUtBlockTagProvider(output, lookupProvider, event.getExistingFileHelper()));
        generator.addProvider(true, new MekUtItemTagProvider(output, lookupProvider,
                CompletableFuture.completedFuture(TagLookup.empty()), blockTagProvider.contentsGetter(),
                event.getExistingFileHelper()));
        generator.addProvider(true, new MekUtRecipeProvider(output, lookupProvider));
        generator.addProvider(true, MekUtLootTableProvider.createBlockLoot(output, lookupProvider));

        generator.addProvider(true, new MekUtEnglishLangProvider(output));
        generator.addProvider(true, new MekUtBlockModelProvider(output, event.getExistingFileHelper()));
        generator.addProvider(true, new MekUtItemModelProvider(output, event.getExistingFileHelper()));
    }

    @SuppressWarnings("UnstableApiUsage")
    public static void bootstrapConfigs() {
        for (Set<ModConfig> configs : CONFIG_SETS.getValue(ConfigTracker.INSTANCE).values()) {
            for (ModConfig config : configs) {
                CommentedConfig commentedConfig = new SynchronizedConfig(InMemoryCommentedFormat.defaultInstance(),
                        LinkedHashMap::new);
                config.getSpec().correct(commentedConfig);
                try {
                    SET_CONFIG.invoke(config, LOADED_CONFIG.newInstance(commentedConfig, null, config),
                            (Function<ModConfig, ModConfigEvent>) ModConfigEvent.Loading::new);
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
