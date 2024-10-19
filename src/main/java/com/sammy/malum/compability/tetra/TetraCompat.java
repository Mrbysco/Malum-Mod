package com.sammy.malum.compability.tetra;

import com.sammy.malum.core.handlers.*;
import com.sammy.malum.registry.common.item.EnchantmentRegistry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.*;
import net.minecraftforge.fml.ModList;
import se.mickelus.tetra.aspect.ItemAspect;
import se.mickelus.tetra.aspect.TetraEnchantmentHelper;
import se.mickelus.tetra.effect.ItemEffect;
import se.mickelus.tetra.items.modular.ModularItem;

public class TetraCompat {
    public static boolean LOADED;

    public static void init() {
        LOADED = ModList.get().isLoaded("tetra");
    }

    public static boolean hasSoulStrikeModifier(ItemStack stack) {
        return LOADED && LoadedOnly.hasSoulStrikeModifier(stack);
    }

    public static void onEntityJoin(EntityJoinLevelEvent event) {
        if (LOADED) {
            LoadedOnly.fireArrow(event);
        }
    }

    public static class LoadedOnly {

        private static final ItemEffect SHATTERS_SOULS = ItemEffect.get("malum.shatters_souls");

        public static final ItemAspect SOUL_HUNTER_TOOL = ItemAspect.get("soulStained");

        static {
            TetraEnchantmentHelper.registerMapping(SOUL_HUNTER_TOOL, new TetraEnchantmentHelper.EnchantmentRules("additions/malum_something", "exclusions/malum_something", EnchantmentRegistry.SOUL_HUNTER_WEAPON));
        }

        public static boolean hasSoulStrikeModifier(ItemStack stack) {
            if (stack.getItem() instanceof ModularItem modularItem) {
                return modularItem.getEffectLevel(stack, SHATTERS_SOULS) > 0;
            }
            return false;
        }

        public static void fireArrow(EntityJoinLevelEvent event) {
            if (event.getEntity() instanceof Arrow arrow) {
                if (arrow.getOwner() instanceof Player player) {
                    if (hasSoulStrikeModifier(player.getUseItem())) {
                        arrow.addTag(SoulDataHandler.SOUL_SHATTER_ENTITY_TAG);
                    }
                }
            }
        }
    }
}