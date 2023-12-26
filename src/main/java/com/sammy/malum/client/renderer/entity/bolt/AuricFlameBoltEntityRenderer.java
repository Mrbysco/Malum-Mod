package com.sammy.malum.client.renderer.entity.bolt;

import com.sammy.malum.common.entity.bolt.*;
import com.sammy.malum.common.item.curiosities.weapons.staff.*;
import net.minecraft.client.renderer.entity.*;

public class AuricFlameBoltEntityRenderer extends AbstractBoltEntityRenderer<AuricFlameBoltEntity> {
    public AuricFlameBoltEntityRenderer(EntityRendererProvider.Context context) {
        super(context, AuricFlameStaffItem.AURIC_YELLOW, AuricFlameStaffItem.AURIC_PURPLE);
    }
}