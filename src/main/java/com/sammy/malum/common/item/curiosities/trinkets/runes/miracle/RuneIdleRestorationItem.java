package com.sammy.malum.common.item.curiosities.trinkets.runes.miracle;

import com.sammy.malum.common.item.curiosities.trinkets.runes.AbstractRuneTrinketsItem;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import dev.emi.trinkets.api.SlotReference;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public class RuneIdleRestorationItem extends AbstractRuneTrinketsItem {

    public RuneIdleRestorationItem(Properties builder) {
        super(builder, SpiritTypeRegistry.SACRED_SPIRIT);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("passive_healing"));
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        final LivingEntity livingEntity = slot.inventory().getComponent().getEntity();
        if (livingEntity.level().getGameTime() % 40L == 0) {
            livingEntity.heal(1);
        }
        super.tick(stack, slot, entity);
    }
}