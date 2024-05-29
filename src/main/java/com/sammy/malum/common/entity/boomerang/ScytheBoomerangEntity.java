package com.sammy.malum.common.entity.boomerang;

import com.sammy.malum.common.item.curiosities.weapons.scythe.MalumScytheItem;
import com.sammy.malum.registry.common.DamageTypeRegistry;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.entity.EntityRegistry;
import com.sammy.malum.registry.common.item.EnchantmentRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemHandlerHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.helpers.ItemHelper;

import java.util.Random;

public class ScytheBoomerangEntity extends ThrowableItemProjectile {

    protected int slot;
    protected float damage;
    protected float magicDamage;
    public int age;
    protected int returnTimer = 8;

    public int enemiesHit;

    public ScytheBoomerangEntity(Level level) {
        super(EntityRegistry.SCYTHE_BOOMERANG.get(), level);
        noPhysics = false;
    }

    public ScytheBoomerangEntity(Level level, double pX, double pY, double pZ) {
        super(EntityRegistry.SCYTHE_BOOMERANG.get(), pX, pY, pZ, level);
        noPhysics = false;
    }

    public void setData(Entity owner, float damage, float magicDamage, int slot) {
        setOwner(owner);
        this.damage = damage;
        this.magicDamage = magicDamage;
        this.slot = slot;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (slot != 0) {
            compound.putInt("slot", slot);
        }
        if (damage != 0) {
            compound.putFloat("damage", damage);
        }
        if (magicDamage != 0) {
            compound.putFloat("magicDamage", magicDamage);
        }
        if (age != 0) {
            compound.putInt("age", age);
        }
        if (returnTimer != 0) {
            compound.putInt("returnTimer", returnTimer);
        }
        if (enemiesHit != 0) {
            compound.putInt("enemiesHit", enemiesHit);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        slot = compound.getInt("slot");
        damage = compound.getFloat("damage");
        magicDamage = compound.getFloat("magicDamage");
        age = compound.getInt("age");
        returnTimer = compound.getInt("returnTimer");
        enemiesHit = compound.getInt("enemiesHit");
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        returnTimer = 0;
    }

    @Override
    protected boolean canHitEntity(Entity pTarget) {
        return !pTarget.equals(getOwner());
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (getOwner() instanceof LivingEntity scytheOwner && !level().isClientSide) {
            Entity target = result.getEntity();
            DamageSource source = target.damageSources().mobProjectile(this, scytheOwner);
            boolean success = target.hurt(source, damage);
            if (success && target instanceof LivingEntity livingentity) {
                ItemStack scythe = getItem();
                scythe.hurtAndBreak(1, scytheOwner, (e) -> remove(RemovalReason.KILLED));
                ItemHelper.applyEnchantments(scytheOwner, livingentity, scythe);
                int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, scythe);
                if (i > 0) {
                    livingentity.setSecondsOnFire(i * 4);
                }
                if (magicDamage > 0) {
                    if (!livingentity.isDeadOrDying()) {
                        livingentity.invulnerableTime = 0;
                        livingentity.hurt(DamageTypeRegistry.create(level(), DamageTypeRegistry.VOODOO, this, scytheOwner), magicDamage);
                    }
                }

            }
            returnTimer += 4;
            target.level().playSound(null, target.getX(), target.getY(), target.getZ(), SoundRegistry.SCYTHE_CUT.get(), target.getSoundSource(), 1.0F, 0.9f + target.level().random.nextFloat() * 0.2f);
        }
        super.onHitEntity(result);
    }

    @Override
    public void tick() {
        super.tick();
        age++;
        ItemStack scythe = getItem();
        final Level level = level();
        if (level.isClientSide) {
            if (!isInWaterRainOrBubble()) {
                if (getItem().getEnchantmentLevel(Enchantments.FIRE_ASPECT) > 0) {
                    Vec3 vector = new Vec3(getRandomX(0.7), getRandomY(), getRandomZ(0.7));
                    if (scythe.getItem() instanceof MalumScytheItem) {
                        Random random = new Random();
                        float rotation = random.nextFloat();
                        vector = new Vec3(Math.cos(this.age) * 0.8f + this.getX(), getY(0.1), Math.sin(this.age) * 0.8f + this.getZ());
                        level.addParticle(ParticleTypes.FLAME, Math.cos(this.age + rotation * 2 - 1) * 0.8f + this.getX(), vector.y, Math.sin(this.age + rotation * 2 - 1) * 0.8f + this.getZ(), 0, 0, 0);
                        level.addParticle(ParticleTypes.FLAME, Math.cos(this.age + rotation * 2 - 1) * 0.8f + this.getX(), vector.y, Math.sin(this.age + rotation * 2 - 1) * 0.8f + this.getZ(), 0, 0, 0);
                    }
                    level.addParticle(ParticleTypes.FLAME, vector.x, vector.y, vector.z, 0, 0, 0);
                }
            }
        } else {
            Entity owner = getOwner();
            if (owner == null || !owner.isAlive() || !owner.level().equals(level()) || distanceTo(owner) > 1000f) {
                if (age > 3600) {
                    ItemEntity itemEntity = new ItemEntity(level, getX(), getY() + 0.5, getZ(), scythe);
                    itemEntity.setPickUpDelay(40);
                    level.addFreshEntity(itemEntity);
                    remove(RemovalReason.DISCARDED);
                }
                setDeltaMovement(Vec3.ZERO);
                return;
            }
            if (owner instanceof LivingEntity scytheOwner) {
                if (age % 3 == 0) {
                    level.playSound(null, blockPosition(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 0.75f, 1.25f);
                }
                if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
                    Vec3 motion = getDeltaMovement();
                    setYRot((float) (Mth.atan2(motion.x, motion.z) * (double) (180F / (float) Math.PI)));
                    yRotO = getYRot();
                    xRotO = getXRot();
                }
                if (returnTimer <= 0) {
                    noPhysics = true;
                    Vec3 ownerPos = scytheOwner.position().add(0, 1, 0);
                    Vec3 motion = ownerPos.subtract(position());
                    setDeltaMovement(motion.normalize().scale(0.75f));
                    float distance = distanceTo(scytheOwner);

                    if (isAlive() && distance < 3f) {
                        if (scytheOwner instanceof Player player) {
                            ItemHandlerHelper.giveItemToPlayer(player, scythe, slot);
                            if (!player.isCreative()) {
                                int enchantmentLevel = EnchantmentHelper.getItemEnchantmentLevel(EnchantmentRegistry.REBOUND.get(), scythe);
                                if (enchantmentLevel < 4) {
                                    int cooldown = 100 - 25 * (enchantmentLevel - 1);
                                    if (cooldown > 0) {
                                        player.getCooldowns().addCooldown(scythe.getItem(), cooldown);
                                    }
                                }
                            }
                            remove(RemovalReason.DISCARDED);
                        }
                    }
                }
                returnTimer--;
            }
        }
    }

    public void shootFromRotation(Entity shooter, float rotationPitch, float rotationYaw, float pitchOffset, float velocity, float innacuracy) {
        float f = -Mth.sin(rotationYaw * ((float) Math.PI / 180F)) * Mth.cos(rotationPitch * ((float) Math.PI / 180F));
        float f1 = -Mth.sin((rotationPitch + pitchOffset) * ((float) Math.PI / 180F));
        float f2 = Mth.cos(rotationYaw * ((float) Math.PI / 180F)) * Mth.cos(rotationPitch * ((float) Math.PI / 180F));
        this.shoot(f, f1, f2, velocity, innacuracy);
        Vec3 vec3 = shooter.getDeltaMovement();
        this.setDeltaMovement(this.getDeltaMovement().add(vec3.x, 0, vec3.z));
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public float getPickRadius() {
        return 4f;
    }

    @Override
    protected Item getDefaultItem() {
        return ItemRegistry.SOUL_STAINED_STEEL_SCYTHE.get();
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean ignoreExplosion() {
        return true;
    }
}