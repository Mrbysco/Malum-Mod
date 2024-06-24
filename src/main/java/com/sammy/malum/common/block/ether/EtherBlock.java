package com.sammy.malum.common.block.ether;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import team.lodestar.lodestone.systems.block.WaterLoggedEntityBlock;

public class EtherBlock<T extends EtherBlockEntity> extends WaterLoggedEntityBlock<T> {
    public static final VoxelShape SHAPE = Block.box(6, 6, 6, 10, 10, 10);

    public EtherBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

}