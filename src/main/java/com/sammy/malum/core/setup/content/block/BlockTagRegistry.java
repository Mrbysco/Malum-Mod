package com.sammy.malum.core.setup.content.block;

import com.sammy.malum.core.helper.DataHelper;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.function.Supplier;

public class BlockTagRegistry {

    public static final TagKey<Block> TRAY_HEAT_SOURCES = modTag("farmersdelight:tray_heat_sources");
    public static final TagKey<Block> HEAT_SOURCES = modTag("farmersdelight:heat_sources");


    private static TagKey<Block> modTag(String path) {
        return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(path));
    }

    private static TagKey<Block> malumTag(String path) {
        return TagKey.create(Registry.BLOCK_REGISTRY, DataHelper.prefix(path));
    }

    private static TagKey<Block> forgeTag(String name) {
        return BlockTags.create(new ResourceLocation("forge", name));
    }
}