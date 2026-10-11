package net.veroxuniverse.verox_swords_axes.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider.TagLookup;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.veroxuniverse.verox_swords_axes.SwordsNAxes;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = SwordsNAxes.MOD_ID)
public final class DataGenerators {

    private DataGenerators() {}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper files = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookup = event.getLookupProvider();

        generator.addProvider(event.includeClient(), new ModLangProvider(output));
        generator.addProvider(event.includeClient(), new ModItemModelProvider(output, files));

        generator.addProvider(event.includeServer(), new ModRecipeProvider(output, lookup));

        generator.addProvider(event.includeServer(), new ModItemTagsProvider(output, lookup,
                CompletableFuture.completedFuture(TagLookup.empty()), files));
    }
}
