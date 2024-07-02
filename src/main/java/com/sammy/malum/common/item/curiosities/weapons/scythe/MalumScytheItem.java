package com.sammy.malum.common.item.curiosities.weapons.scythe;

import com.sammy.malum.common.entity.boomerang.ScytheBoomerangEntity;
import com.sammy.malum.common.item.IMalumEventResponderItem;
import com.sammy.malum.registry.client.ParticleRegistry;
import com.sammy.malum.registry.common.DamageTypeRegistry;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import io.github.fabricators_of_create.porting_lib.enchant.CustomEnchantingBehaviorItem;
import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import team.lodestar.lodestone.helpers.TrinketsHelper;
import team.lodestar.lodestone.registry.common.tag.LodestoneDamageTypeTags;
import team.lodestar.lodestone.systems.item.ModCombatItem;

public class MalumScytheItem extends ModCombatItem implements IMalumEventResponderItem, CustomEnchantingBehaviorItem {

    public MalumScytheItem(Tier tier, float attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn + 3 + tier.getAttackDamageBonus(), attackSpeedIn - 3.2f, builderIn);
    }

    @Override
    public void hurtEvent(LivingHurtEvent event, LivingEntity attacker, LivingEntity target, ItemStack stack) {
        //TODO: convert this to a ToolAction, or something alike
        boolean canSweep = !TrinketsHelper.hasTrinketEquipped(attacker, ItemRegistry.NECKLACE_OF_THE_NARROW_EDGE.get()) && !TrinketsHelper.hasTrinketEquipped(attacker, ItemRegistry.NECKLACE_OF_THE_HIDDEN_BLADE.get());
        if (attacker instanceof Player player) {
            SoundEvent sound;
            if (canSweep) {
                spawnSweepParticles(player, ParticleRegistry.SCYTHE_SWEEP_PARTICLE.get());
                sound = SoundEvents.PLAYER_ATTACK_SWEEP;
            } else {
                spawnSweepParticles(player, ParticleRegistry.SCYTHE_CUT_PARTICLE.get());
                sound = SoundRegistry.SCYTHE_CUT.get();
            }
            attacker.level().playSound(null, target.getX(), target.getY(), target.getZ(), sound, attacker.getSoundSource(), 1, 1);
        }

        if (!canSweep || event.getSource().is(LodestoneDamageTypeTags.IS_MAGIC) || event.getSource().getMsgId().equals(DamageTypeRegistry.SCYTHE_SWEEP_IDENTIFIER)) {
            return;
        }
        int sweeping = EnchantmentHelper.getEnchantmentLevel(Enchantments.SWEEPING_EDGE, attacker);
        float damage = event.getAmount() * (0.5f + EnchantmentHelper.getSweepingDamageRatio(attacker));
        attacker.level().getEntities(attacker, target.getBoundingBox().inflate(1 + sweeping * 0.25f)).forEach(e -> {
            if (e instanceof LivingEntity livingEntity) {
                if (livingEntity.isAlive()) {
                    livingEntity.hurt((DamageTypeRegistry.create(attacker.level(), DamageTypeRegistry.SCYTHE_SWEEP, attacker)), damage);
                    livingEntity.knockback(0.4F, Mth.sin(attacker.getYRot() * ((float) Math.PI / 180F)), (-Mth.cos(attacker.getYRot() * ((float) Math.PI / 180F))));
                }
            }
        });
    }

    public static void spawnSweepParticles(Player player, SimpleParticleType type) {
        double d0 = (-Mth.sin(player.getYRot() * ((float) Math.PI / 180F)));
        double d1 = Mth.cos(player.getYRot() * ((float) Math.PI / 180F));
        if (player.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(type, player.getX() + d0, player.getY(0.5D), player.getZ() + d1, 0, d0, 0.0D, d1, 0.0D);
        }
    }

    public static ItemStack getScytheItemStack(DamageSource source, LivingEntity attacker) {
        ItemStack stack = attacker.getMainHandItem();

        if (source.getDirectEntity() instanceof ScytheBoomerangEntity scytheBoomerang) {
            stack = scytheBoomerang.getItem();
        }
        return stack.getItem() instanceof MalumScytheItem ? stack : ItemStack.EMPTY;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment.equals(Enchantments.SWEEPING_EDGE)) {
            return true;
        }
        return CustomEnchantingBehaviorItem.super.canApplyAtEnchantingTable(stack, enchantment);
    }
}