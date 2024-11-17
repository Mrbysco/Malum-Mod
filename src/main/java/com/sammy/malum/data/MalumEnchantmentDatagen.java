package com.sammy.malum.data;

import com.sammy.malum.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.data.worldgen.*;
import net.minecraft.resources.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.item.enchantment.effects.*;
import team.lodestar.lodestone.registry.common.*;

import static net.minecraft.world.item.enchantment.Enchantment.enchantment;

public class MalumEnchantmentDatagen {

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        HolderGetter<Enchantment> enchantments = context.lookup(Registries.ENCHANTMENT);
        HolderGetter<Item> items = context.lookup(Registries.ITEM);

        register(context, EnchantmentRegistry.ANIMATED,
                enchantment(Enchantment.definition(items.getOrThrow(ItemTagRegistry.ANIMATED_ENCHANTABLE), 2, 2,
                        Enchantment.constantCost(55), Enchantment.constantCost(200), 10, EquipmentSlotGroup.MAINHAND))
                        .withEffect(EnchantmentEffectComponents.ATTRIBUTES,
                                new EnchantmentAttributeEffect(
                                        MalumMod.malumPath("enchantment.attack_speed"),
                                        Attributes.ATTACK_SPEED,
                                        LevelBasedValue.perLevel(0.15F),
                                        AttributeModifier.Operation.ADD_VALUE
                                )
                        ));
        register(context, EnchantmentRegistry.REBOUND,
                enchantment(Enchantment.definition(items.getOrThrow(ItemTagRegistry.REBOUND_ENCHANTABLE), 1, 3,
                        Enchantment.constantCost(55), Enchantment.constantCost(200), 10, EquipmentSlotGroup.MAINHAND)));
        register(context, EnchantmentRegistry.ASCENSION,
                enchantment(Enchantment.definition(items.getOrThrow(ItemTagRegistry.ASCENSION_ENCHANTABLE), 1, 3,
                        Enchantment.constantCost(55), Enchantment.constantCost(200), 10, EquipmentSlotGroup.MAINHAND)));

        register(context, EnchantmentRegistry.REPLENISHING,
                enchantment(Enchantment.definition(items.getOrThrow(ItemTagRegistry.REPLENISHING_ENCHANTABLE), 1, 2,
                        Enchantment.constantCost(55), Enchantment.constantCost(200), 10, EquipmentSlotGroup.MAINHAND)));


        register(context, EnchantmentRegistry.HAUNTED,
                enchantment(Enchantment.definition(items.getOrThrow(ItemTagRegistry.HAUNTED_ENCHANTABLE), 2, 2,
                        Enchantment.constantCost(55), Enchantment.constantCost(200), 10, EquipmentSlotGroup.MAINHAND))
                        .withEffect(EnchantmentEffectComponents.ATTRIBUTES,
                                new EnchantmentAttributeEffect(
                                        MalumMod.malumPath("enchantment.magic_damage"),
                                        LodestoneAttributes.MAGIC_DAMAGE,
                                        LevelBasedValue.perLevel(1F),
                                        AttributeModifier.Operation.ADD_VALUE
                                )
                        ));
        register(context, EnchantmentRegistry.SPIRIT_PLUNDER,
                enchantment(Enchantment.definition(items.getOrThrow(ItemTagRegistry.SPIRIT_SPOILS_ENCHANTABLE), 1, 2,
                        Enchantment.constantCost(55), Enchantment.constantCost(200), 10, EquipmentSlotGroup.MAINHAND))
                        .withEffect(EnchantmentEffectComponents.ATTRIBUTES,
                                new EnchantmentAttributeEffect(
                                        MalumMod.malumPath("enchantment.spirit_spoils"),
                                        AttributeRegistry.SPIRIT_SPOILS,
                                        LevelBasedValue.perLevel(1),
                                        AttributeModifier.Operation.ADD_VALUE
                                )
                        ));
    }

    private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        context.register(key, builder.build(key.location()));
    }
}