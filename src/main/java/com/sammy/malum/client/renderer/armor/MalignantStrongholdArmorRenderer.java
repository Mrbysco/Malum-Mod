package com.sammy.malum.client.renderer.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.client.cosmetic.ArmorSkinRenderingData;
import com.sammy.malum.common.item.cosmetic.skins.ArmorSkin;
import com.sammy.malum.registry.client.ModelRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import team.lodestar.lodestone.systems.model.LodestoneArmorModel;

public class MalignantStrongholdArmorRenderer implements ArmorRenderer {

    public MalignantStrongholdArmorRenderer() {}

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, ItemStack stack, LivingEntity entity, EquipmentSlot slot, int light, HumanoidModel<LivingEntity> contextModel) {
        float pticks = Minecraft.getInstance().getFrameTime();
        float f = Mth.rotLerp(pticks, entity.yBodyRotO, entity.yBodyRot);
        float f1 = Mth.rotLerp(pticks, entity.yHeadRotO, entity.yHeadRot);
        float netHeadYaw = f1 - f;
        float netHeadPitch = Mth.lerp(pticks, entity.xRotO, entity.getXRot());
        ArmorSkin skin = ArmorSkin.getAppliedItemSkin(stack);
        LodestoneArmorModel model = ModelRegistry.MALIGNANT_LEAD_ARMOR;
        if (skin != null) {
            model = ArmorSkinRenderingData.RENDERING_DATA.apply(skin).getModel(entity);
        }
        model.slot = slot;
        model.copyFromDefault(contextModel);
        model.setupAnim(entity, entity.walkAnimation.position(), entity.walkAnimation.speed(), entity.tickCount + pticks, netHeadYaw, netHeadPitch);
    }
}