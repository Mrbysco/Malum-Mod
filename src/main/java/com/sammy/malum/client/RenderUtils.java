package com.sammy.malum.client;

import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.rendering.*;
import team.lodestar.lodestone.systems.rendering.trail.*;

import java.awt.*;
import java.util.List;
import java.util.function.*;

public class RenderUtils {


    public static void renderEntityTrail(PoseStack poseStack, VFXBuilders.WorldVFXBuilder builder, TrailPointBuilder trailPointBuilder, Entity entity, Color primaryColor, Color secondaryColor, float effectScalar, float partialTicks) {
        renderEntityTrail(poseStack, builder, trailPointBuilder, entity, t -> primaryColor, t -> secondaryColor, effectScalar, effectScalar, partialTicks);
    }

    public static void renderEntityTrail(PoseStack poseStack, VFXBuilders.WorldVFXBuilder builder, TrailPointBuilder trailPointBuilder, Entity entity, Function<Float, Color> primaryColor, Function<Float, Color> secondaryColor, float effectScalar, float partialTicks) {
        renderEntityTrail(poseStack, builder, trailPointBuilder, entity, primaryColor, secondaryColor, effectScalar, effectScalar, partialTicks);
    }

    public static void renderEntityTrail(PoseStack poseStack, VFXBuilders.WorldVFXBuilder builder, TrailPointBuilder trailPointBuilder, Entity entity, Color primaryColor, Color secondaryColor, float scaleScalar, float alphaScalar, float partialTicks) {
        renderEntityTrail(poseStack, builder, trailPointBuilder, entity, t-> primaryColor, t-> secondaryColor, scaleScalar, alphaScalar, partialTicks);
    }
    public static void renderEntityTrail(PoseStack poseStack, VFXBuilders.WorldVFXBuilder builder, TrailPointBuilder trailPointBuilder, Entity entity, Function<Float, Color> primaryColor, Function<Float, Color> secondaryColor, float scaleScalar, float alphaScalar, float partialTicks) {
        List<TrailPoint> spinningTrailPoints = trailPointBuilder.getTrailPoints();
        poseStack.pushPose();
        final Vec3 motionOffset = entity.getDeltaMovement().scale(0.5f);
        float trailOffsetX = (float) (Mth.lerp(partialTicks, entity.xOld, entity.getX()) - motionOffset.x);
        float trailOffsetY = (float) (Mth.lerp(partialTicks, entity.yOld, entity.getY()) - motionOffset.y);
        float trailOffsetZ = (float) (Mth.lerp(partialTicks, entity.zOld, entity.getZ()) - motionOffset.z);
        if (spinningTrailPoints.size() > 3) {
            poseStack.translate(-trailOffsetX, -trailOffsetY, -trailOffsetZ);
            for (int i = 0; i < 2; i++) {
                float size = (0.2f + i * 0.2f) * scaleScalar;
                float alpha = (0.7f - i * 0.35f) * alphaScalar;
                builder.setAlpha(alpha)
                        .renderTrail(poseStack, spinningTrailPoints, f -> size, f -> builder.setAlpha(alpha * f).setColor(ColorHelper.colorLerp(Easing.SINE_IN, f * 2f, secondaryColor.apply(f), primaryColor.apply(f))))
                        .renderTrail(poseStack, spinningTrailPoints, f -> 1.5f * size, f -> builder.setAlpha(alpha * f / 2f).setColor(ColorHelper.colorLerp(Easing.SINE_IN, f * 1.5f, secondaryColor.apply(f), primaryColor.apply(f))))
                        .renderTrail(poseStack, spinningTrailPoints, f -> size * 2.5f, f -> builder.setAlpha(alpha * f / 4f).setColor(ColorHelper.colorLerp(Easing.SINE_IN, f * 1.5f, secondaryColor.apply(f), primaryColor.apply(f))));
            }
            poseStack.translate(trailOffsetX, trailOffsetY, trailOffsetZ);
        }
        poseStack.popPose();
    }
}