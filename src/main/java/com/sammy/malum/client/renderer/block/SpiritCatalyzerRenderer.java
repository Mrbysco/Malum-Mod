package com.sammy.malum.client.renderer.block;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import com.sammy.malum.client.SpiritBasedWorldVFXBuilder;
import com.sammy.malum.client.renderer.entity.*;
import com.sammy.malum.common.block.curiosities.spirit_crucible.catalyzer.*;
import com.sammy.malum.core.systems.spirit.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.handlers.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.rendering.*;
import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeToken;

import java.util.*;

import static com.sammy.malum.MalumMod.*;
import static net.minecraft.client.renderer.texture.OverlayTexture.*;


public class SpiritCatalyzerRenderer implements BlockEntityRenderer<SpiritCatalyzerCoreBlockEntity> {

    private static final ResourceLocation LIGHT_TRAIL = malumPath("textures/vfx/concentrated_trail.png");
    private static final RenderType TRAIL_TYPE = LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE.apply(new RenderTypeToken("malum", LIGHT_TRAIL));

    public SpiritCatalyzerRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(SpiritCatalyzerCoreBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Level level = Minecraft.getInstance().level;
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack stack = blockEntityIn.inventory.getStackInSlot(0);
        if (!stack.isEmpty()) {
            poseStack.pushPose();
            Vec3 offset = blockEntityIn.getItemOffset();
            poseStack.translate(offset.x, offset.y, offset.z);
            poseStack.mulPose(Axis.YP.rotationDegrees(((level.getGameTime() % 360) + partialTicks) * 3));
            poseStack.scale(0.45f, 0.45f, 0.45f);
            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, combinedLightIn, NO_OVERLAY, poseStack, bufferIn, level, 0);
            poseStack.popPose();
        }
        stack = blockEntityIn.augmentInventory.getStackInSlot(0);
        if (!stack.isEmpty()) {
            poseStack.pushPose();
            Vec3 offset = blockEntityIn.getAugmentOffset();
            poseStack.translate(offset.x, offset.y, offset.z);
            poseStack.mulPose(Axis.YP.rotationDegrees(((-level.getGameTime() % 360) - partialTicks) * 3));
            poseStack.scale(0.45f, 0.45f, 0.45f);
            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, combinedLightIn, NO_OVERLAY, poseStack, bufferIn, level, 0);
            poseStack.popPose();
        }
        if (blockEntityIn.getTarget() != null && blockEntityIn.intensity != null) {
            poseStack.pushPose();
            final BlockPos blockPos = blockEntityIn.getBlockPos();
            Vec3 offset = blockEntityIn.getItemOffset();
            for (Map.Entry<MalumSpiritType, Integer> entry : blockEntityIn.intensity.entrySet()) {
                if (entry.getValue() > 0) {
                    final MalumSpiritType spirit = entry.getKey();
                    poseStack.translate(-blockPos.getX(), -blockPos.getY(), -blockPos.getZ());
                    renderBeam(blockEntityIn, poseStack, spirit, entry.getValue());
                    poseStack.translate(blockPos.getX()+offset.x, blockPos.getY()+offset.y, blockPos.getZ()+offset.z);
                    FloatingItemEntityRenderer.renderSpiritGlimmer(poseStack, spirit, entry.getValue() / 60f, partialTicks);
                    poseStack.translate(-offset.x, -offset.y, -offset.z);
                }
            }
            poseStack.popPose();
        }
    }

    public void renderBeam(SpiritCatalyzerCoreBlockEntity catalyzer, PoseStack poseStack, MalumSpiritType spiritType, int intensity) {
        BlockPos catalyzerPos = catalyzer.getBlockPos();
        Vec3 startPos = catalyzer.getItemOffset().add(catalyzerPos.getX(), catalyzerPos.getY(), catalyzerPos.getZ());
        Vec3 targetPos = catalyzer.getTarget().getAccelerationPoint();
        Vec3 difference = targetPos.subtract(startPos);
        float distance = 0.35f + Easing.SINE_OUT.ease(intensity / 60f, 0, 0.35f, 1);
        float alpha = intensity / 60f;
        Vec3 midPoint = startPos.add(difference.scale(distance));
        SpiritBasedWorldVFXBuilder.create(spiritType)
                
                .setColor(spiritType.getPrimaryColor())
                .setRenderType(TRAIL_TYPE)
                .setAlpha(alpha)
                .renderBeam(poseStack.last().pose(), startPos, midPoint, 0.4f, b -> b.setColor(spiritType.getSecondaryColor()).setAlpha(0f));
    }
}