package com.sammy.malum.common.item.curiosities.trinkets.sets.alchemical;

import com.sammy.malum.common.item.IMalumEventResponderItem;
import com.sammy.malum.common.item.curiosities.trinkets.MalumTinketsItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import team.lodestar.lodestone.helpers.RandomHelper;

import java.util.function.Consumer;

public class TrinketsProwessRing extends MalumTinketsItem implements IMalumEventResponderItem {
    public TrinketsProwessRing(Properties builder) {
        super(builder, MalumTrinketType.ALCHEMICAL);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("spirits_xp"));
    }

    @Override
    public void pickupSpirit(LivingEntity collector, double arcaneResonance) {
        var level = collector.level();
        int generatedExperience = (int) Math.round(RandomHelper.randomBetween(level.random, 1, 4) * arcaneResonance);

        while (generatedExperience > 0) {
            int value = ExperienceOrb.getExperienceValue(generatedExperience);
            generatedExperience -= value;
            level.addFreshEntity(new ExperienceOrb(level, collector.getX(), collector.getY(), collector.getZ(), value));
        }
    }
}