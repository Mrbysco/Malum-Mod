package com.sammy.malum.client.particles.spiritflame;

import com.mojang.serialization.Codec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleType;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.particle.options.WorldParticleOptions;


public class SpiritFlameParticleType extends ParticleType<WorldParticleOptions> {
    public SpiritFlameParticleType() {
        super(false, WorldParticleOptions.DESERIALIZER);
    }

    @Override
    public Codec<WorldParticleOptions> codec() {
        return WorldParticleOptions.worldCodec(this);
    }

    public static class Factory implements ParticleProvider<WorldParticleOptions> {
        private final SpriteSet sprite;

        public Factory(SpriteSet sprite) {
            this.sprite = sprite;
        }

        @Nullable
        @Override
        public Particle createParticle(WorldParticleOptions data, ClientLevel world, double x, double y, double z, double mx, double my, double mz) {
            return new SpiritFlameParticle(world, data, (ParticleEngine.MutableSpriteSet) sprite, x, y, z, mx, my, mz);
        }
    }
}