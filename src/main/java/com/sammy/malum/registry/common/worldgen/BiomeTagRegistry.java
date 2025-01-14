package com.sammy.malum.registry.common.worldgen;

import com.sammy.malum.MalumMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class BiomeTagRegistry {
    public static final TagKey<Biome> HAS_SOULSTONE = TagKey.create(Registries.BIOME, MalumMod.malumPath("has_soulstone"));
    public static final TagKey<Biome> HAS_BRILLIANT = TagKey.create(Registries.BIOME, MalumMod.malumPath("has_brilliant"));
    public static final TagKey<Biome> HAS_BLAZING_QUARTZ = TagKey.create(Registries.BIOME, MalumMod.malumPath("has_blazing_quartz"));
    public static final TagKey<Biome> HAS_QUARTZ = TagKey.create(Registries.BIOME, MalumMod.malumPath("has_quartz"));
    public static final TagKey<Biome> HAS_CTHONIC = TagKey.create(Registries.BIOME, MalumMod.malumPath("has_rare_earths"));

    public static final TagKey<Biome> HAS_RUNEWOOD = TagKey.create(Registries.BIOME, MalumMod.malumPath("has_runewood"));
    public static final TagKey<Biome> HAS_RARE_RUNEWOOD = TagKey.create(Registries.BIOME, MalumMod.malumPath("has_rare_runewood"));
    public static final TagKey<Biome> HAS_AZURE_RUNEWOOD = TagKey.create(Registries.BIOME, MalumMod.malumPath("has_azure_runewood"));
    public static final TagKey<Biome> HAS_RARE_AZURE_RUNEWOOD = TagKey.create(Registries.BIOME, MalumMod.malumPath("has_rare_azure_runewood"));
}
