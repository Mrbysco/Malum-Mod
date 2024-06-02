package com.sammy.malum.registry.common.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class FoodPropertyRegistry {

    public static final FoodProperties RUNIC_SAP = new FoodProperties.Builder().nutrition(6).saturationMod(0.1F).effect(new MobEffectInstance(MobEffects.HEAL, 1, 0), 1).effect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0), 1).build();
    public static final FoodProperties CURSED_SAP = new FoodProperties.Builder().nutrition(6).saturationMod(0.1F).effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 400, 0), 1).effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 400, 0), 1).build();
}
