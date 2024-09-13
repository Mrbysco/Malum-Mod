package com.sammy.malum.registry.common.worldgen;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.worldgen.WeepingWellStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class StructureRegistry {
    public static final DeferredRegister<StructureType<?>> STRUCTURES = DeferredRegister.create(Registries.STRUCTURE_TYPE, MalumMod.MALUM);

    public static final DeferredHolder<StructureType<?>, StructureType<WeepingWellStructure>> WEEPING_WELL = STRUCTURES.register("weeping_well", () -> () -> (WeepingWellStructure.CODEC));

}
