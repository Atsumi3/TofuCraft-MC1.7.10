package tsuteto.tofu.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import tsuteto.tofu.TofuCraftMod;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = TofuCraftMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class TcDataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        // Block states and models
        generator.addProvider(event.includeClient(),
                new TcBlockStateProvider(output, existingFileHelper));

        // Item models
        generator.addProvider(event.includeClient(),
                new TcItemModelProvider(output, existingFileHelper));

        // Recipes
        generator.addProvider(event.includeServer(),
                new TcRecipeProvider(output, lookupProvider));

        // Loot tables
        generator.addProvider(event.includeServer(),
                new TcLootTableProvider(output, lookupProvider));

        // Block tags
        TcBlockTagProvider blockTagProvider = new TcBlockTagProvider(output, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), blockTagProvider);

        // Item tags
        generator.addProvider(event.includeServer(),
                new TcItemTagProvider(output, lookupProvider, blockTagProvider.contentsGetter(), existingFileHelper));
    }
}
