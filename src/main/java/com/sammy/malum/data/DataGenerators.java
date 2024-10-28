package com.sammy.malum.data;

import com.sammy.malum.MalumMod;
import com.sammy.malum.data.block.*;
import com.sammy.malum.data.item.MalumItemModels;
import com.sammy.malum.data.item.MalumItemTags;
import com.sammy.malum.data.recipe.*;
import com.sammy.malum.data.worldgen.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = MalumMod.MALUM, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var output = generator.getPackOutput();
        var provider = event.getLookupProvider();
        var helper = event.getExistingFileHelper();

        var itemModelsProvider = new MalumItemModels(output, helper);
        var blockStatesProvider = new MalumBlockStates(output, helper, itemModelsProvider);

        var blockTagsProvider = new MalumBlockTags(output, provider, helper);
        var itemTagsProvider = new MalumItemTags(output, provider, blockTagsProvider.contentsGetter(), helper);

//        generator.addProvider(event.includeClient(), new MalumFusionBlockModels(output));

        generator.addProvider(event.includeClient(), itemModelsProvider);
        generator.addProvider(event.includeClient(), blockStatesProvider);

        generator.addProvider(event.includeServer(), blockTagsProvider);
        generator.addProvider(event.includeServer(), itemTagsProvider);

        generator.addProvider(event.includeServer(), new MalumBlockLootTables(output));

        generator.addProvider(event.includeServer(), new MalumRecipes(output));

        generator.addProvider(event.includeServer(), new MalumBiomeTags(output, provider, helper));
        generator.addProvider(event.includeServer(), new MalumDamageTypeTags(output, provider, helper));

        generator.addProvider(event.includeServer(), new WorldgenRegistryDataGenerator(output, provider));
        generator.addProvider(event.includeClient(), new MalumLang(output));

        generator.addProvider(event.includeServer(), new MalumCuriosThings(output, helper, provider));

    }
}
