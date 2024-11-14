package com.sammy.malum.common.item.curiosities.curios.sets.weeping;

import com.mojang.blaze3d.systems.*;
import com.sammy.malum.*;
import com.sammy.malum.common.capability.*;
import com.sammy.malum.common.entity.hidden_blade.*;
import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.curiosities.curios.MalumCurioItem;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.core.helpers.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.player.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.*;
import org.lwjgl.opengl.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.registry.common.*;
import team.lodestar.lodestone.systems.rendering.*;
import team.lodestar.lodestone.systems.rendering.shader.*;

import java.util.function.Consumer;

public class CurioHiddenBladeNecklace extends MalumCurioItem implements IMalumEventResponderItem, IVoidItem {

    public static final int COOLDOWN_DURATION = 200;

    public CurioHiddenBladeNecklace(Properties builder) {
        super(builder, MalumTrinketType.VOID);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("scythe_counterattack"));
        consumer.accept(positiveEffect("enhanced_maneuvers"));
        consumer.accept(negativeEffect("pacifist_recharge"));
        consumer.accept(negativeEffect("no_sweep"));
    }


    @Override
    public void incomingDamageEvent(LivingDamageEvent.Pre event, LivingEntity attacker, LivingEntity attacked, ItemStack stack) {
        if (attacked.level().isClientSide()) {
            return;
        }
        MalumLivingEntityDataCapability.getCapabilityOptional(attacked).ifPresent(c -> {
            if (c.hiddenBladeNecklaceCooldown == 0) {
                float damage = event.getOriginalDamage();
                int amplifier = 1 + Mth.ceil(damage * 0.6f);
                attacked.addEffect(new MobEffectInstance(MobEffectRegistry.WICKED_INTENT, 60, amplifier));
                SoundHelper.playSound(attacked, SoundRegistry.HIDDEN_BLADE_PRIMED.get(), 1f, RandomHelper.randomBetween(attacked.level().getRandom(), 1.4f, 1.6f));
            }
        });
    }

    @Override
    public void outgoingDamageEvent(LivingDamageEvent.Pre event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        var source = event.getSource();
        var level = attacker.level();
        if (level.isClientSide()) {
            return;
        }
        if (!source.is(DamageTypeTagRegistry.IS_SCYTHE_MELEE)) {
            return;
        }
        if (CurioHelper.hasCurioEquipped(attacker, ItemRegistry.NECKLACE_OF_THE_HIDDEN_BLADE.get())) {
            MalumLivingEntityDataCapability.getCapabilityOptional(attacker).ifPresent(c -> {
                var random = level.getRandom();
                if (c.hiddenBladeNecklaceCooldown != 0) {
                    if (c.hiddenBladeNecklaceCooldown <= COOLDOWN_DURATION) {
                        SoundHelper.playSound(attacker, SoundRegistry.HIDDEN_BLADE_DISRUPTED.get(), 1f, RandomHelper.randomBetween(random, 0.7f, 0.8f));
                    }
                    c.hiddenBladeNecklaceCooldown = (int) (COOLDOWN_DURATION * 1.5);
                    MalumLivingEntityDataCapability.sync(attacker);
                    return;
                }
                var effect = attacker.getEffect(MobEffectRegistry.WICKED_INTENT);
                if (effect == null) {
                    return;
                }
                var scytheWeapon = SoulDataHandler.getScytheWeapon(source, attacker);

                var damageDealer = source.getDirectEntity() != null ? source.getDirectEntity() : attacker;
                var damageCenter = damageDealer.position().add(attacker.getLookAngle().scale(4));
                int duration = 25;
                var attributes = attacker.getAttributes();
                float multiplier = (float) Mth.clamp(attributes.getValue(Attributes.ATTACK_SPEED), 0, 1) * 2;
                float baseDamage = (float) (attributes.getValue(Attributes.ATTACK_DAMAGE) / duration) * multiplier * effect.getAmplifier();
                float magicDamage = (float) (attributes.getValue(LodestoneAttributes.MAGIC_DAMAGE) / duration) * multiplier;
                var entity = new HiddenBladeDelayedImpactEntity(level, damageCenter.x, damageCenter.y - 3f + attacker.getBbHeight() / 2f, damageCenter.z);
                entity.setData(attacker, baseDamage, magicDamage, duration);
                entity.setItem(stack);
                level.addFreshEntity(entity);
                attacker.removeEffect(effect.getEffect());
                c.hiddenBladeNecklaceCooldown = 200;
                for (int i = 0; i < 3; i++) {
                    SoundHelper.playSound(attacker, SoundRegistry.HIDDEN_BLADE_UNLEASHED.get(), 3f, RandomHelper.randomBetween(random, 0.75f, 1.25f));
                }
                var particle = ParticleHelper.createSlashingEffect(ParticleEffectTypeRegistry.HIDDEN_BLADE_COUNTER_FLURRY);
                if (scytheWeapon.getItem() instanceof ISpiritAffiliatedItem spiritAffiliatedItem) {
                    particle.setSpiritType(spiritAffiliatedItem);
                }
                particle.setRandomSlashAngle(random)
                        .mirrorRandomly(random)
                        .spawnForwardSlashingParticle(attacker);
                MalumLivingEntityDataCapability.sync(attacker);
            });
        }
    }
    public static void entityTick(EntityTickEvent event) {
        if (event.getEntity() instanceof LivingEntity entity) {
            var level = entity.level();
            MalumLivingEntityDataCapability.getCapabilityOptional(entity).ifPresent(c -> {
                if (c.hiddenBladeNecklaceCooldown > 0) {
                    c.hiddenBladeNecklaceCooldown--;
                    if (!level.isClientSide()) {
                        if (c.hiddenBladeNecklaceCooldown == 0) {
                            SoundHelper.playSound(entity, SoundRegistry.HIDDEN_BLADE_CHARGED.get(), 1f, RandomHelper.randomBetween(level.getRandom(), 1.0f, 1.2f));
                        }
                    }
                }
            });
        }
    }

    public static class ClientOnly {

        public static int fadeOut;

        public static void tick(ClientTickEvent event) {
            final LocalPlayer player = Minecraft.getInstance().player;
            if (player != null) {
                var cooldown = MalumLivingEntityDataCapability.getCapability(player).hiddenBladeNecklaceCooldown;
                if (cooldown == 0) {
                    if (player.hasEffect(MobEffectRegistry.WICKED_INTENT)) {
                        if (fadeOut > 30) {
                            fadeOut = 30;
                        }
                        fadeOut -= 2;
                        return;
                    }
                    else if (fadeOut < 0) {
                        fadeOut = 20;
                    }
                    if (fadeOut < 80) {
                        fadeOut++;
                    }
                } else {
                    fadeOut = 0;
                }
            }
        }

        public static void renderHiddenBladeCooldown(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
            var minecraft = Minecraft.getInstance();
            var poseStack = guiGraphics.pose();
            if (!minecraft.options.hideGui) {
                var player = minecraft.player;
                if (!player.isCreative() && !player.isSpectator()) {
                    var cooldown = MalumLivingEntityDataCapability.getCapability(player).hiddenBladeNecklaceCooldown;
                    if (cooldown > 0 || fadeOut <= 80) {
                        int left = guiGraphics.guiWidth() / 2 - 8;
                        int top = guiGraphics.guiHeight() - 52;
                        poseStack.pushPose();
//                        gui.setupOverlayRenderState(true, false);
                        RenderSystem.setShaderTexture(0, getTexture());
                        RenderSystem.depthMask(true);
                        RenderSystem.enableBlend();
                        RenderSystem.defaultBlendFunc();
                        ExtendedShaderInstance shaderInstance = (ExtendedShaderInstance) LodestoneShaders.DISTORTED_TEXTURE.getInstance().get();
                        shaderInstance.safeGetUniform("YFrequency").set(15f);
                        shaderInstance.safeGetUniform("XFrequency").set(15f);
                        shaderInstance.safeGetUniform("Speed").set(550f);
                        shaderInstance.safeGetUniform("Intensity").set(120f);
                        var builder = VFXBuilders.createScreen()
                                .setPosColorTexLightmapDefaultFormat()
                                .setShader(() -> shaderInstance);

                        float size = 16;
                        double delta = Mth.clamp((COOLDOWN_DURATION-cooldown) / (float)COOLDOWN_DURATION, 0, 1);
                        delta -= 0.125f;
                        final boolean secondRow = delta >= 0.5f;
                        int xOffset = 16*(Mth.floor(delta*8)) - (secondRow ? 64 : 0);
                        int yOffset = secondRow ? 16 : 0;

                        if (fadeOut > 20) {
                            final boolean hasEffect = player.hasEffect(MobEffectRegistry.WICKED_INTENT);
                            builder.setAlpha((80 - fadeOut) / (hasEffect ? 10f : 60f));
                        }
                        builder.setPosColorTexLightmapDefaultFormat()
                                .setPositionWithWidth(left, top, size, size)
                                .setUVWithWidth(xOffset, yOffset, 16, 16, 64)
                                .draw(poseStack);
                        if (fadeOut > 0 && fadeOut < 20) {
                            float glow = (10 - Math.abs(10 - fadeOut)) / 10f;
                            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

                            builder.setAlpha(glow).draw(poseStack);
                        }

                        shaderInstance.setUniformDefaults();
                        RenderSystem.disableBlend();
                        poseStack.popPose();
                    }
                }
            }
        }

        public static ResourceLocation getTexture() {
            return MalumMod.malumPath("textures/gui/hud/hidden_blade.png");
        }
    }
}
