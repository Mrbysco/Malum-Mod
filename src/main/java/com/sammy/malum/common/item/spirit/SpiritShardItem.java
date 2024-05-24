package com.sammy.malum.common.item.spirit;

import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.visual_effects.ScreenParticleEffects;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.handlers.screenparticle.ParticleEmitterHandler.ItemParticleSupplier;
import team.lodestar.lodestone.systems.particle.screen.ScreenParticleHolder;

import java.util.List;

public class SpiritShardItem extends Item implements ItemParticleSupplier {
    public final MalumSpiritType type;

    public SpiritShardItem(Properties properties, MalumSpiritType type) {
        super(properties.rarity(type.getItemRarity()));
        this.type = type;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(type.getSpiritShardFlavourTextComponent());
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void spawnLateParticles(ScreenParticleHolder target, Level level, float partialTick, ItemStack stack, float x, float y) {
        ScreenParticleEffects.spawnSpiritShardScreenParticles(target, type);
    }
}
