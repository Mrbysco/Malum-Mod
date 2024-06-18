package com.sammy.malum.visual_effects.networked.pylon;

import com.sammy.malum.common.block.curiosities.repair_pylon.*;
import com.sammy.malum.common.block.storage.*;
import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import team.lodestar.lodestone.helpers.*;

import java.util.function.*;

public class PylonRepairParticleEffect extends ParticleEffectType {

    public PylonRepairParticleEffect(String id) {
        super(id);
    }

    public static NBTEffectData createData(BlockPos holderPos) {
        NBTEffectData effectData = new NBTEffectData(new CompoundTag());
        BlockHelper.saveBlockPos(effectData.compoundTag, holderPos);
        return effectData;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public Supplier<ParticleEffectActor> get() {
        return () -> (level, random, positionData, colorData, nbtData) -> {
            if (!(level.getBlockEntity(positionData.getAsBlockPos()) instanceof RepairPylonCoreBlockEntity pylon)) {
                return;
            }
            if (!(level.getBlockEntity(BlockHelper.loadBlockPos(nbtData.compoundTag)) instanceof IMalumSpecialItemAccessPoint holder)) {
                return;
            }
            RepairPylonParticleEffects.repairItemParticles(pylon, holder, colorData);
        };
    }
}