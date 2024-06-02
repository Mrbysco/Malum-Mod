package com.sammy.malum.core.handlers;

import com.sammy.malum.common.components.MalumComponents;
import com.sammy.malum.registry.common.AttributeRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;

public class ReserveStaffChargeHandler {
    public int chargeCount;
    public float chargeProgress;

    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("chargeCount", chargeCount);
        tag.putFloat("chargeProgress", chargeProgress);
        return tag;
    }

    public void deserializeNBT(CompoundTag tag) {
        chargeCount = tag.getInt("chargeCount");
        chargeProgress = tag.getFloat("chargeProgress");
    }

    public static void recoverStaffCharges(Player player) {
        if (!player.level().isClientSide) {
            AttributeInstance reserveStaffCharges = player.getAttribute(AttributeRegistry.RESERVE_STAFF_CHARGES.get());
            if (reserveStaffCharges != null) {
                ReserveStaffChargeHandler chargeHandler = MalumComponents.MALUM_PLAYER_COMPONENT.get(player).reserveStaffChargeHandler;
                if (chargeHandler.chargeCount < reserveStaffCharges.getValue()) {
                    chargeHandler.chargeProgress++;
                    if (chargeHandler.chargeProgress >= 600) {
                        chargeHandler.chargeProgress = 0;
                        chargeHandler.chargeCount++;
                        MalumComponents.MALUM_PLAYER_COMPONENT.sync(player);
                    }
                }
            }
        }
    }
}