package com.sammy.malum.common.item.impetus;

import com.sammy.malum.common.recipe.SpiritRepairRecipe;
import io.github.fabricators_of_create.porting_lib.enchant.CustomEnchantingBehaviorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.function.Supplier;

public class ImpetusItem extends Item implements SpiritRepairRecipe.IRepairOutputOverride, CustomEnchantingBehaviorItem {
    private Supplier<CrackedImpetusItem> cracked;

    public ImpetusItem(Properties properties) {
        super(properties);
    }

    public ImpetusItem setCrackedVariant(Supplier<CrackedImpetusItem> cracked) {
        this.cracked = cracked;
        cracked.get().setRepairedVariant(this);
        return this;
    }

    public CrackedImpetusItem getCrackedVariant() {
        return cracked.get();
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean isEnchantable(ItemStack p_41456_) {
        return false;
    }

    @Override
    public boolean ignoreDuringLookup() {
        return true;
    }
}