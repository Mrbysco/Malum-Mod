package com.sammy.malum.common.item.augment;

import com.sammy.malum.registry.common.*;

public class AcceleratingInlayItem extends AbstractAugmentItem{
    public AcceleratingInlayItem(Properties pProperties) {
        super(pProperties, SpiritTypeRegistry.AERIAL_SPIRIT);
    }

    @Override
    public float getSpeedIncrease() {
        return 1f;
    }
}
