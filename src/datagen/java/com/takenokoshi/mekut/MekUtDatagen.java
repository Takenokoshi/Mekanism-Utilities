package com.takenokoshi.mekut;

import java.util.concurrent.CompletableFuture;

import com.takenokoshi.mekut.core.MekUtConstants;
import com.takenokoshi.mekut.lang.MekUtEnglishLangProvider;
import com.takenokoshi.mekut.model.MekUtBlockModelProvider;
import com.takenokoshi.mekut.model.MekUtItemModelProvider;
import com.takenokoshi.mekut.recipe.MekUtRecipeProvider;
import com.takenokoshi.mekut.tag.MekUtBlockTagProvider;
import com.takenokoshi.mekut.tag.MekUtItemTagProvider;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider.TagLookup;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = MekUtConstants.MODID, bus = EventBusSubscriber.Bus.MOD)
public class MekUtDatagen {

    private MekUtDatagen() {
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {

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

        generator.addProvider(true, new MekUtEnglishLangProvider(output));
        generator.addProvider(true, new MekUtBlockModelProvider(output, event.getExistingFileHelper()));
        generator.addProvider(true, new MekUtItemModelProvider(output, event.getExistingFileHelper()));
    }
}
