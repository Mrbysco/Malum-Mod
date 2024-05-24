package com.sammy.malum.common.item.misc;

import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.*;
import org.jetbrains.annotations.Nullable;


public class BlazingQuartzItem extends ItemNameBlockItem {
    public final int fuel;
    public BlazingQuartzItem(Block pBlock, int fuel, Properties pProperties) {
        super(pBlock, pProperties);
        this.fuel = fuel;
    }
    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return fuel;
    }
}
