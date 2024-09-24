package com.sammy.malum.common.item;

import net.minecraft.core.component.*;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BrillianceChunkItem extends Item {
    public BrillianceChunkItem(Properties properties) {
        super(properties.component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
        int i = 3 + level.random.nextInt(5) + level.random.nextInt(5);

        while (i > 0) {
            int j = ExperienceOrb.getExperienceValue(i);
            i -= j;
            level.addFreshEntity(new ExperienceOrb(level, entityLiving.getX(), entityLiving.getY(), entityLiving.getZ(), j));
        }
        return super.finishUsingItem(stack, level, entityLiving);
    }


    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 5;
    }
}
