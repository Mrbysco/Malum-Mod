package com.sammy.malum.common.effect;

import com.sammy.malum.common.components.MalumComponents;
import com.sammy.malum.core.handlers.TouchOfDarknessHandler;
import com.sammy.malum.registry.common.AttributeRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import team.lodestar.lodestone.helpers.ColorHelper;
import team.lodestar.lodestone.registry.common.LodestoneAttributeRegistry;

public class SilencedEffect extends MobEffect {
    public SilencedEffect() {
        super(MobEffectCategory.HARMFUL, ColorHelper.getColor(20, 14, 22));
        addAttributeModifier(LodestoneAttributeRegistry.MAGIC_DAMAGE.get(), "16886dd3-5b79-4191-88a5-4597125b90be", -0.1f, AttributeModifier.Operation.MULTIPLY_TOTAL);
        addAttributeModifier(LodestoneAttributeRegistry.MAGIC_PROFICIENCY.get(), "c7a4eea8-38ee-4b56-8623-02c9f4a8b363", -0.1f, AttributeModifier.Operation.MULTIPLY_TOTAL);
        addAttributeModifier(LodestoneAttributeRegistry.MAGIC_RESISTANCE.get(), "90d9ba7f-4023-4b6a-88f0-d50ec5ee1040", -0.1f, AttributeModifier.Operation.MULTIPLY_TOTAL);

        addAttributeModifier(AttributeRegistry.SOUL_WARD_STRENGTH.get(), "c804e494-0f03-49e3-b9e1-c382ddd98927", -0.1f, AttributeModifier.Operation.MULTIPLY_TOTAL);
        addAttributeModifier(AttributeRegistry.SOUL_WARD_CAP.get(), "e0e391f4-ca5c-4237-a261-fbcb85d2a491", -0.1f, AttributeModifier.Operation.MULTIPLY_TOTAL);
        addAttributeModifier(AttributeRegistry.SOUL_WARD_RECOVERY_RATE.get(), "c460be81-7c50-499d-b66f-ec7b78f917e4", -0.1f, AttributeModifier.Operation.MULTIPLY_TOTAL);

        addAttributeModifier(AttributeRegistry.ARCANE_RESONANCE.get(), "3c0373c4-179d-4c38-b54b-442e69c1fb1c", -0.1f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        TouchOfDarknessHandler handler = MalumComponents.MALUM_LIVING_ENTITY_COMPONENT.get(pLivingEntity).touchOfDarknessHandler;
        handler.afflict(20);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}