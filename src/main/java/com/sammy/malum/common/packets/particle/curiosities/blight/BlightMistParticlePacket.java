package com.sammy.malum.common.packets.particle.curiosities.blight;

import com.sammy.malum.common.packets.particle.base.BlockBasedParticleEffectPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;
import team.lodestar.lodestone.registry.common.particle.*;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;

import java.awt.*;
import java.util.function.Supplier;

public class BlightMistParticlePacket extends BlockBasedParticleEffectPacket {
    public BlightMistParticlePacket(BlockPos pos) {
        super(pos);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = Minecraft.getInstance().level;
        for (int i = 0; i < 3; i++) {
            float multiplier = Mth.nextFloat(level.random, 0.4f, 1f);
            float timeMultiplier = Mth.nextFloat(level.random, 0.9f, 1.4f);
            Color color = new Color((int) (31 * multiplier), (int) (19 * multiplier), (int) (31 * multiplier));
            boolean spinDirection = level.random.nextBoolean();
            WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                    .setTransparencyData(GenericParticleData.create(0.15f, 1f, 0).build())
                    .setSpinData(SpinParticleData.create(0.2f * (spinDirection ? 1 : -1)).build())
                    .setScaleData(GenericParticleData.create(0.15f, 0.2f, 0).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                    .setLifetime((int) (45 * timeMultiplier))
                    .setColorData(ColorParticleData.create(color, color).build())
                    .enableNoClip()
                    .setRandomOffset(0.1f, 0f)
                    .setRandomMotion(0.005f, 0.01f)
                    .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                    .repeatSurroundBlock(level, pos, 2, Direction.UP);

            WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                    .setTransparencyData(GenericParticleData.create(0.25f, 0.55f, 0).build())
                    .setLifetime((int) (50 * timeMultiplier))
                    .setSpinData(SpinParticleData.create(0.1f * (spinDirection ? 1 : -1)).build())
                    .setScaleData(GenericParticleData.create(0.35f, 0.4f, 0).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                    .setColorData(ColorParticleData.create(color, color).build())
                    .setRandomOffset(0.2f, 0)
                    .enableNoClip()
                    .setRandomMotion(0.005f, 0.005f)
                    .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                    .repeatSurroundBlock(level, pos, 2, Direction.UP);

            color = new Color((int) (80 * multiplier), (int) (40 * multiplier), (int) (80 * multiplier));
            WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                    .setTransparencyData(GenericParticleData.create(0.02f, 0.15f, 0).build())
                    .setSpinData(SpinParticleData.create(0.1f * (spinDirection ? 1 : -1)).build())
                    .setScaleData(GenericParticleData.create(0.35f, 0.4f, 0).setEasing(Easing.QUINTIC_OUT, Easing.SINE_IN).build())
                    .setColorData(ColorParticleData.create(color, color).build())
                    .setLifetime((int) (50 * timeMultiplier))
                    .setRandomOffset(0.2f, 0)
                    .enableNoClip()
                    .setRandomMotion(0.01f, 0.005f)
                    .repeatSurroundBlock(level, pos, 2, Direction.UP);
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, BlightMistParticlePacket.class, BlightMistParticlePacket::encode, BlightMistParticlePacket::decode, BlightMistParticlePacket::handle);
    }

    public static BlightMistParticlePacket decode(FriendlyByteBuf buf) {
        return new BlightMistParticlePacket(new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()));
    }
}