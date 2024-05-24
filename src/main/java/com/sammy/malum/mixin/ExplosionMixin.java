package com.sammy.malum.mixin;

import com.sammy.malum.common.item.curiosities.curios.sets.prospector.CurioDemolitionistRing;
import com.sammy.malum.common.item.curiosities.curios.sets.prospector.CurioHoarderRing;
import com.sammy.malum.common.item.curiosities.curios.sets.prospector.CurioProspectorBelt;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Explosion.class)
public abstract class ExplosionMixin {

    @Unique
    boolean malum$hasHoarderRing;
    @Unique
    ItemStack malum$droppedItem;


    @Mutable
    @Shadow
    @Final
    private float radius;

    @Shadow
    @Nullable
    public abstract LivingEntity getIndirectSourceEntity();

    @ModifyArg(method = "finalizeExplosion", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getDrops(Lnet/minecraft/world/level/storage/loot/LootParams$Builder;)Ljava/util/List;"))
    private LootParams.Builder malum$getBlockDrops(LootParams.Builder builder) {
        return CurioProspectorBelt.applyFortune(getIndirectSourceEntity(), builder);
    }

    @Inject(method = "<init>(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/damagesource/DamageSource;Lnet/minecraft/world/level/ExplosionDamageCalculator;DDDFZLnet/minecraft/world/level/Explosion$BlockInteraction;)V", at = @At(value = "RETURN"))
    private void malum$modifyExplosionStats(Level pLevel, Entity pSource, DamageSource pDamageSource, ExplosionDamageCalculator pDamageCalculator, double pToBlowX, double pToBlowY, double pToBlowZ, float pRadius, boolean pFire, Explosion.BlockInteraction pBlockInteraction, CallbackInfo ci) {
        radius = CurioDemolitionistRing.increaseExplosionRadius(getIndirectSourceEntity(), radius);
    }

    @Inject(method = "finalizeExplosion", at = @At(value = "HEAD"))
    private void malum$finalizeExplosion(boolean pSpawnParticles, CallbackInfo ci) {
        malum$hasHoarderRing = CurioHoarderRing.hasHoarderRing(getIndirectSourceEntity());
    }

    @ModifyArg(method = "finalizeExplosion", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;popResource(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/item/ItemStack;)V"), index = 2)
    private ItemStack malum$popResourceCache(ItemStack pStack) {
        return malum$droppedItem = pStack;
    }

    @ModifyArg(method = "finalizeExplosion", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;popResource(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/item/ItemStack;)V"), index = 1)
    private BlockPos malum$popResource(BlockPos value) {
        return CurioHoarderRing.getExplosionPos(malum$hasHoarderRing, value, getIndirectSourceEntity(), malum$droppedItem);
    }
}