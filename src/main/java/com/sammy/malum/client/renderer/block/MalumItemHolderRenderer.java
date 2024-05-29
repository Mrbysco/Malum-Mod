package com.sammy.malum.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.sammy.malum.common.block.storage.MalumItemHolderBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntityInventory;

import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;


public class MalumItemHolderRenderer implements BlockEntityRenderer<MalumItemHolderBlockEntity> {
    public MalumItemHolderRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(MalumItemHolderBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Level level = Minecraft.getInstance().level;
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        LodestoneBlockEntityInventory inventory = blockEntityIn.inventory;

        if (!inventory.isEmpty()) {
            ItemStack stack = inventory.getStackInSlot(0);
            poseStack.pushPose();
            Vec3 itemOffset = blockEntityIn.getItemOffset(partialTicks);
            poseStack.translate(itemOffset.x(), itemOffset.y(), itemOffset.z());
            poseStack.mulPose(Axis.YP.rotationDegrees(((level.getGameTime() % 360) + partialTicks) * 3));
            poseStack.scale(0.6f, 0.6f, 0.6f);
            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, combinedLightIn, NO_OVERLAY, poseStack, bufferIn, level, 0);
            poseStack.popPose();
        }
    }
}