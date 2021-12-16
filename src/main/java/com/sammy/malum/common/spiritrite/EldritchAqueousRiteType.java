package com.sammy.malum.common.spiritrite;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.registry.misc.ParticleRegistry;
import com.sammy.malum.core.systems.particle.ParticleManager;
import com.sammy.malum.core.systems.rites.MalumRiteType;
import net.minecraft.world.level.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.Level.Level;

import java.awt.*;
import java.util.ArrayList;

import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.*;

public class EldritchAqueousRiteType extends MalumRiteType {
    public EldritchAqueousRiteType() {
        super("eldritch_aqueous_rite", false, ELDRITCH_SPIRIT, ARCANE_SPIRIT, AQUEOUS_SPIRIT, AQUEOUS_SPIRIT);
    }

    @Override
    public void riteEffect(Level Level, BlockPos pos) {
        ArrayList<BlockPos> positions = getNearbyBlocksUnderBase(Block.class, Level, pos, false);
        positions.removeIf(p -> p.getX() == pos.getX() && p.getZ() == pos.getZ() || !Level.getBlockState(p).isAir(Level, p));
        positions.forEach(p -> {
            BlockState water = Blocks.WATER.defaultBlockState();
            if (MalumHelper.areWeOnServer(Level)) {
                Level.setBlockAndUpdate(p, water);
            } else {
                particles(Level, p);
            }
        });
    }

    @Override
    public void corruptedRiteEffect(Level Level, BlockPos pos) {
    }

    @Override
    public int interval(boolean corrupted) {
        return defaultInterval() * 5;
    }

    @Override
    public int range(boolean corrupted) {
        return defaultRange() / 2;
    }

    public void particles(Level Level, BlockPos pos) {
        Color color = AQUEOUS_SPIRIT_COLOR;
        ParticleManager.create(ParticleRegistry.WISP_PARTICLE)
                .setAlpha(0.2f, 0f)
                .setLifetime(20)
                .setSpin(0.2f)
                .setScale(0.4f, 0)
                .setColor(color, color)
                .enableNoClip()
                .randomOffset(0.1f, 0.1f)
                .randomVelocity(0.001f, 0.001f)
                .evenlyRepeatEdges(Level, pos, 6, Direction.UP);
        ParticleManager.create(ParticleRegistry.SMOKE_PARTICLE)
                .setAlpha(0.1f, 0f)
                .setLifetime(40)
                .setSpin(0.1f)
                .setScale(0.6f, 0)
                .setColor(color, color)
                .randomOffset(0.2f)
                .enableNoClip()
                .randomVelocity(0.001f, 0.001f)
                .evenlyRepeatEdges(Level, pos, 8, Direction.UP);
    }
}