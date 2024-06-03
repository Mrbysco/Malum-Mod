package com.sammy.malum.registry.common.item;

import com.chocohead.mm.api.ClassTinkerers;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.enchantment.HauntedEnchantment;
import com.sammy.malum.common.enchantment.ReboundEnchantment;
import com.sammy.malum.common.enchantment.ReplenishingEnchantment;
import com.sammy.malum.common.enchantment.SpiritPlunderEnchantment;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class EnchantmentRegistry {
    public static final LazyRegistrar<Enchantment> ENCHANTMENTS = LazyRegistrar.create(BuiltInRegistries.ENCHANTMENT, MalumMod.MALUM);


    public static final EnchantmentCategory SOUL_HUNTER_WEAPON = ClassTinkerers.getEnum(EnchantmentCategory.class, "SOUL_HUNTER");
    public static final EnchantmentCategory SCYTHE = ClassTinkerers.getEnum(EnchantmentCategory.class, "SCYTHE");
    public static final EnchantmentCategory REBOUND_SCYTHE = ClassTinkerers.getEnum(EnchantmentCategory.class, "REBOUND_SCYTHE");
    public static final EnchantmentCategory STAFF = ClassTinkerers.getEnum(EnchantmentCategory.class, "STAFF");
    public static final EnchantmentCategory SCYTHE_OR_STAFF = ClassTinkerers.getEnum(EnchantmentCategory.class, "SCYTHE_OR_STAFF");

    public static final RegistryObject<Enchantment> REBOUND = ENCHANTMENTS.register("rebound", ReboundEnchantment::new);
    public static final RegistryObject<Enchantment> HAUNTED = ENCHANTMENTS.register("haunted", HauntedEnchantment::new);
    public static final RegistryObject<Enchantment> SPIRIT_PLUNDER = ENCHANTMENTS.register("spirit_plunder", SpiritPlunderEnchantment::new);
    public static final RegistryObject<Enchantment> REPLENISHING = ENCHANTMENTS.register("replenishing", ReplenishingEnchantment::new);

    public static void init() {
    }
}
