package com.sammy.malum.common.item.curiosities.curios.sets.alchemical;

import com.sammy.malum.common.capability.*;
import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.compability.irons_spellbooks.*;
import com.sammy.malum.core.systems.events.*;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;

import java.util.function.*;

public class CurioManaweavingRing extends MalumCurioItem implements IMalumEventResponderItem {
    public CurioManaweavingRing(Properties builder) {
        super(builder, MalumTrinketType.ALCHEMICAL);
    }

    @Override
    public void addExtraTooltipLines(Consumer<Component> consumer) {
        consumer.accept(positiveEffect("spirits_weave_mana"));
        if (IronsSpellsCompat.LOADED) {
            consumer.accept(positiveEffect("spirits_weave_mana_irons_spellbooks"));
        }
    }

    @Override
    public void spiritCollectionEvent(CollectSpiritEvent event, LivingEntity collector, double arcaneResonance) {
        if (collector instanceof ServerPlayer player) {
            var handler = MalumPlayerDataCapability.getCapability(player).soulWardHandler;
            handler.recoverSoulWard(player, arcaneResonance);
            IronsSpellsCompat.generateMana(player, 10 * arcaneResonance);
        }
    }
}
