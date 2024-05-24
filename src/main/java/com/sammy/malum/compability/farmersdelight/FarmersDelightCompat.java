package com.sammy.malum.compability.farmersdelight;

import com.sammy.malum.common.item.curiosities.*;
import com.sammy.malum.registry.common.item.ItemRegistry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import vectorwing.farmersdelight.common.utility.TextUtils;

import static com.sammy.malum.registry.common.item.ItemTiers.ItemTierEnum.SOUL_STAINED_STEEL;

public class FarmersDelightCompat {
    public static boolean LOADED;

    public static void init() {
        LOADED = FabricLoader.getInstance().isModLoaded("farmersdelight");
    }

    public static class LoadedOnly {

        public static Item makeMagicKnife(Item.Properties properties) {
            return new MagicKnifeItem(SOUL_STAINED_STEEL, -1.5f, 0, 2, properties);
        }

        public static void addInfo(IRecipeRegistration registration) {
            registration.addIngredientInfo(new ItemStack(ItemRegistry.SOUL_STAINED_STEEL_KNIFE.get()), VanillaTypes.ITEM_STACK, TextUtils.getTranslation("jei.info.knife"));
        }
    }
}
