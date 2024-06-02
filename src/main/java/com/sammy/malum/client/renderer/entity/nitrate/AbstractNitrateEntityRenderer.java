package com.sammy.malum.client.renderer.entity.nitrate;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.client.RenderUtils;
import com.sammy.malum.common.entity.nitrate.AbstractNitrateEntity;
import com.sammy.malum.registry.client.MalumRenderTypeTokens;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;

import java.awt.*;
import java.util.function.Function;

import static com.sammy.malum.client.renderer.entity.FloatingItemEntityRenderer.renderSpiritGlimmer;

public class AbstractNitrateEntityRenderer<T extends AbstractNitrateEntity> extends EntityRenderer<T> {
    public final Function<Float, Color> primaryColor;
    public final Function<Float, Color> secondaryColor;

    public AbstractNitrateEntityRenderer(EntityRendererProvider.Context context, Function<Float, Color> primaryColor, Function<Float, Color> secondaryColor) {
        super(context);
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.shadowRadius = 0;
        this.shadowStrength = 0;
    }

    public AbstractNitrateEntityRenderer(EntityRendererProvider.Context context, Color primaryColor, Color secondaryColor) {
        this(context, f -> primaryColor, f -> secondaryColor);
    }

    private static final RenderType TRAIL_TYPE = LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE_TRIANGLE.apply(MalumRenderTypeTokens.CONCENTRATED_TRAIL);

    @Override
    public void render(T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        float effectScalar = entity.getVisualEffectScalar();
        VFXBuilders.WorldVFXBuilder builder = VFXBuilders.createWorld().setRenderType(TRAIL_TYPE);
        RenderUtils.renderEntityTrail(poseStack, builder, entity.trailPointBuilder, entity, primaryColor, secondaryColor, effectScalar, partialTicks);
        RenderUtils.renderEntityTrail(poseStack, builder, entity.spinningTrailPointBuilder, entity, primaryColor, secondaryColor, effectScalar, partialTicks);
        if (entity.age > 1 && !entity.fadingAway) {
            poseStack.pushPose();
            float glimmerScale = 3f * Math.min(1f, (entity.age - 2) / 5f);
            poseStack.scale(glimmerScale, glimmerScale, glimmerScale);
            renderSpiritGlimmer(poseStack, primaryColor.apply(0f), secondaryColor.apply(0.125f), partialTicks);
            poseStack.popPose();
        }
        super.render(entity, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
