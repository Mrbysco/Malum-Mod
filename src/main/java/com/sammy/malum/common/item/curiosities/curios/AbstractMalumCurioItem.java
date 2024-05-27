package com.sammy.malum.common.item.curiosities.curios;

import com.google.common.collect.*;
import com.sammy.malum.registry.common.*;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import net.minecraft.*;
import net.minecraft.sounds.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import team.lodestar.lodestone.helpers.*;

import java.util.*;
import java.util.function.*;

public abstract class AbstractMalumCurioItem extends Item implements Trinket {

    public enum MalumTrinketType {
        CLOTH(SoundRegistry.CLOTH_TRINKET_EQUIP),
        ORNATE(SoundRegistry.ORNATE_TRINKET_EQUIP),
        GILDED(SoundRegistry.GILDED_TRINKET_EQUIP),
        ALCHEMICAL(SoundRegistry.ALCHEMICAL_TRINKET_EQUIP),
        ROTTEN(SoundRegistry.ROTTEN_TRINKET_EQUIP),
        METALLIC(SoundRegistry.METALLIC_TRINKET_EQUIP),
        RUNE(SoundRegistry.RUNE_TRINKET_EQUIP),
        VOID(SoundRegistry.VOID_TRINKET_EQUIP);
        final Supplier<SoundEvent> sound;

        MalumTrinketType(Supplier<SoundEvent> sound) {
            this.sound = sound;
        }
    }

    private final Function<Attribute, UUID> uuids = Util.memoize(a -> UUID.randomUUID());
    public final MalumTrinketType type;

    public AbstractMalumCurioItem(Properties properties, MalumTrinketType type) {
        super(properties);
        this.type = type;
    }

    public void addAttributeModifiers(Multimap<Attribute, AttributeModifier> map, SlotReference slot, ItemStack stack) {
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
        Multimap<Attribute, AttributeModifier> map = LinkedHashMultimap.create();
        addAttributeModifiers(map, slot, stack);
        return map;
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        entity.level().playSound(null, entity.blockPosition(), type.sound.get(), SoundSource.PLAYERS, 1.0f, RandomHelper.randomBetween(entity.getRandom(), 0.9f, 1.1f));
        Trinket.super.onEquip(stack, slot, entity);
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        return true;
    }

    public void addAttributeModifier(Multimap<Attribute, AttributeModifier> map, Attribute attribute, Function<UUID, AttributeModifier> attributeModifier) {
        map.put(attribute, attributeModifier.apply(uuids.apply(attribute)));
    }
}