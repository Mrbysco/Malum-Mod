package com.sammy.malum.common.item.curiosities.trinkets.runes;

import com.sammy.malum.common.item.curiosities.trinkets.MalumTinketsItem;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.visual_effects.ScreenParticleEffects;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import team.lodestar.lodestone.handlers.screenparticle.ParticleEmitterHandler;
import team.lodestar.lodestone.systems.particle.screen.ScreenParticleHolder;

public abstract class AbstractRuneTrinketsItem extends MalumTinketsItem implements ParticleEmitterHandler.ItemParticleSupplier {

    public final MalumSpiritType spiritType;

    public AbstractRuneTrinketsItem(Properties builder, MalumSpiritType spiritType) {
        super(builder, MalumTrinketType.RUNE);
        this.spiritType = spiritType;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void spawnLateParticles(ScreenParticleHolder target, Level level, float partialTick, ItemStack stack, float x, float y) {
        ScreenParticleEffects.spawnRuneParticles(target, spiritType);
    }
}