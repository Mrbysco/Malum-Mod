package com.sammy.malum.common.item.nitrate;

import com.mojang.datafixers.util.Pair;
import com.sammy.malum.common.entity.nitrate.AbstractNitrateEntity;
import com.sammy.malum.common.entity.nitrate.EthericNitrateEntity;
import com.sammy.malum.common.entity.nitrate.VividNitrateEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import team.lodestar.lodestone.helpers.ColorHelper;
import team.lodestar.lodestone.setup.LodestoneScreenParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.rendering.particle.ParticleBuilders;
import team.lodestar.lodestone.systems.rendering.particle.screen.base.ScreenParticle;

import java.awt.*;
import java.util.function.Function;

import static com.sammy.malum.common.entity.nitrate.VividNitrateEntity.COLOR_FUNCTION;

public class VividNitrateItem extends AbstractNitrateItem {

    public VividNitrateItem(Properties pProperties) {
        super(pProperties, p -> new VividNitrateEntity(p, p.level));
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return true;
    }

    @OnlyIn(value = Dist.CLIENT)
    @Override
    public void particleTick(ItemStack stack, float x, float y, ScreenParticle.RenderOrder renderOrder) {
        Level level = Minecraft.getInstance().level;
        float partialTick = Minecraft.getInstance().timer.partialTick;
        float gameTime = (float) (level.getGameTime() + partialTick + Math.sin(((level.getGameTime() + partialTick) * 0.1f)));
        Color firstColor = COLOR_FUNCTION.apply(new VividNitrateEntity.ColorFunctionData(level, 40f, 0, partialTick)).brighter();
        Color secondColor = COLOR_FUNCTION.apply(new VividNitrateEntity.ColorFunctionData(level, 40f, 0.125f, partialTick)).darker();
        ParticleBuilders.create(LodestoneScreenParticleRegistry.STAR)
                .setAlpha(0.04f, 0f)
                .setLifetime(6)
                .setScale((float) (1.5f + Math.sin(gameTime * 0.1f) * 0.125f), 0)
                .setColor(firstColor, secondColor)
                .setColorCoefficient(1.25f)
                .randomOffset(0.05f)
                .setSpinOffset(0.025f * gameTime % 6.28f)
                .setSpin(0, 1)
                .setSpinEasing(Easing.EXPO_IN_OUT)
                .setAlphaEasing(Easing.QUINTIC_IN)
                .overwriteRenderOrder(renderOrder)
                .centerOnStack(stack, -1, 4)
                .repeat(x, y, 1)
                .setScale((float) (1.4f - Math.sin(gameTime * 0.075f) * 0.125f), 0)
                .setColor(secondColor, firstColor)
                .setSpinOffset(0.785f-0.01f * gameTime % 6.28f)
                .repeat(x, y, 1);
    }
}