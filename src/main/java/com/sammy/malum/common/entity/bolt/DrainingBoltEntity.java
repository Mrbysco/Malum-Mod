package com.sammy.malum.common.entity.bolt;

import com.sammy.malum.common.item.curiosities.weapons.staff.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.entity.*;
import com.sammy.malum.registry.common.item.*;
import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.*;
import net.minecraft.sounds.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.render_types.*;

import java.util.function.*;

public class DrainingBoltEntity extends AbstractBoltProjectileEntity {

    public DrainingBoltEntity(Level level) {
        super(EntityRegistry.DRAINING_BOLT.get(), level);
        noPhysics = false;
    }

    public DrainingBoltEntity(Level level, double pX, double pY, double pZ) {
        this(level);
        setPos(pX, pY, pZ);
        noPhysics = false;
    }

    @Override
    public void onDealDamage(LivingEntity target) {
        MobEffect silenced = MobEffectRegistry.SILENCED.get();
        MobEffectInstance effect = target.getEffect(silenced);
        if (effect == null) {
            target.addEffect(new MobEffectInstance(silenced, 300, 0, true, true, true));
        } else {
            EntityHelper.amplifyEffect(effect, target, 1, 9);
            EntityHelper.extendEffect(effect, target, 30, 600);
        }
    }

    @Override
    public void playSound(SoundEvent pSound, float pVolume, float pPitch) {
        super.playSound(pSound, pVolume, pPitch-0.2f);
        super.playSound(SoundRegistry.DRAINING_MOTIF.get(), pVolume, pPitch-0.1f);
    }

    @Override
    public int getMaxAge() {
        return 20;
    }

    @Override
    public ParticleEffectType getImpactParticleEffect() {
        return ParticleEffectTypeRegistry.DRAINING_BOLT_IMPACT;
    }

    @Override
    protected Item getDefaultItem() {
        return ItemRegistry.EROSION_SCEPTER.get();
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void spawnParticles() {
        Level level = level();
        Vec3 position = position();
        float scalar = getVisualEffectScalar();
        Vec3 norm = getDeltaMovement().normalize().scale(0.05f);
        var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level, position, ErosionScepterItem.MALIGNANT_COLOR_DATA);
        lightSpecs.getBuilder().multiplyLifetime(1.5f).setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT).setMotion(norm);
        lightSpecs.getBloomBuilder().multiplyLifetime(1.5f).setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT).setMotion(norm);
        lightSpecs.spawnParticles();
        final SpinParticleData spinData = SpinParticleData.createRandomDirection(random, RandomHelper.randomBetween(random, 0.25f, 0.5f)).randomSpinOffset(random).build();
        final Consumer<LodestoneWorldParticleActor> behavior = p -> p.setParticleMotion(p.getParticleSpeed().scale(0.95f));
        DirectionalParticleBuilder.create(ParticleRegistry.SAW)
                .setTransparencyData(GenericParticleData.create(0.4f * scalar, 0.2f * scalar, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                .setSpinData(spinData)
                .setScaleData(GenericParticleData.create(0.3f * scalar, 0).setEasing(Easing.SINE_IN_OUT).build())
                .setColorData(ErosionScepterItem.MALIGNANT_COLOR_DATA)
                .setLifetime(Math.min(6 + age * 3, 24))
                .setDirection(getDeltaMovement().normalize())
                .enableNoClip()
                .enableForcedSpawn()
                .addTickActor(behavior)
                .spawn(level, position.x, position.y, position.z)
                .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                .setTransparencyData(GenericParticleData.create(0.9f * scalar, 0.4f * scalar, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                .spawn(level, position.x, position.y, position.z);
    }
}