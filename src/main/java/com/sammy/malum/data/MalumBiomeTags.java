package com.sammy.malum.data;

import com.sammy.malum.registry.common.worldgen.BiomeTagRegistry;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import io.github.fabricators_of_create.porting_lib.tags.Tags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class MalumBiomeTags extends FabricTagProvider<Biome> {

    public MalumBiomeTags(FabricDataOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, Registries.BIOME, pProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        //super.addTags(pProvider);
        getOrCreateTagBuilder(BiomeTags.IS_OVERWORLD);
        getOrCreateTagBuilder(BiomeTags.IS_NETHER);
        getOrCreateTagBuilder(Tags.Biomes.IS_PLAINS);
        getOrCreateTagBuilder(BiomeTags.IS_FOREST);
        getOrCreateTagBuilder(Tags.Biomes.IS_MOUNTAIN);
        getOrCreateTagBuilder(BiomeTags.IS_HILL);

        getOrCreateTagBuilder(BiomeTagRegistry.HAS_SOULSTONE).addTag(BiomeTags.IS_OVERWORLD);
        getOrCreateTagBuilder(BiomeTagRegistry.HAS_BRILLIANT).addTag(BiomeTags.IS_OVERWORLD);
        getOrCreateTagBuilder(BiomeTagRegistry.HAS_BLAZING_QUARTZ).addTag(BiomeTags.IS_NETHER);
        getOrCreateTagBuilder(BiomeTagRegistry.HAS_QUARTZ).addTag(BiomeTags.IS_OVERWORLD);
        getOrCreateTagBuilder(BiomeTagRegistry.HAS_CTHONIC).addTag(BiomeTags.IS_OVERWORLD);

        FabricTagProvider<Biome>.FabricTagBuilder tag = getOrCreateTagBuilder(BiomeTagRegistry.HAS_RUNEWOOD);
        tag.builder.entries.remove(Tags.Biomes.IS_SNOWY);

        tag.addTag(Tags.Biomes.IS_PLAINS).addTag(Tags.Biomes.IS_MOUNTAIN).addTag(BiomeTags.IS_HILL)

        ;//TODO.remove(Tags.Biomes.IS_SNOWY);

        getOrCreateTagBuilder(BiomeTagRegistry.HAS_RARE_RUNEWOOD).addTag(BiomeTags.IS_FOREST);//TODO.remove(Tags.Biomes.IS_SNOWY);

        getOrCreateTagBuilder(BiomeTagRegistry.HAS_AZURE_RUNEWOOD)
                .add(Biomes.SNOWY_PLAINS)
                .add(Biomes.SNOWY_TAIGA)
                .add(Biomes.FROZEN_RIVER)
                .add(Biomes.SNOWY_BEACH);

        getOrCreateTagBuilder(BiomeTagRegistry.HAS_RARE_AZURE_RUNEWOOD)
                .add(Biomes.FROZEN_PEAKS)
                .add(Biomes.JAGGED_PEAKS)
                .add(Biomes.SNOWY_SLOPES)
                .add(Biomes.GROVE);
    }
}
