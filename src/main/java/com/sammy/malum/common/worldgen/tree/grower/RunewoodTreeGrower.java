package com.sammy.malum.common.worldgen.tree.grower;

import com.sammy.malum.registry.common.worldgen.*;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.level.block.BambooSaplingBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.*;
import net.minecraft.world.level.levelgen.feature.*;

public class RunewoodTreeGrower extends AbstractTreeGrower {

    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource pRandom, boolean pHasFlowers) {
        return ConfiguredFeatureRegistry.CONFIGURED_RUNEWOOD_TREE;
    }
}
