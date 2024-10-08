package com.sammy.malum.data;

import com.sammy.malum.*;
import com.sammy.malum.compability.irons_spellbooks.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.HolderLookup.*;
import net.minecraft.data.*;
import net.minecraft.data.tags.*;
import net.minecraft.resources.*;
import net.neoforged.neoforge.common.data.*;
import team.lodestar.lodestone.registry.common.tag.*;

import java.util.concurrent.*;

public class MalumDamageTypeTags extends DamageTypeTagsProvider {

    public MalumDamageTypeTags(PackOutput pOutput, CompletableFuture<Provider> pProvider, ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider, MalumMod.MALUM, existingFileHelper);
    }

    @Override
    protected void addTags(Provider pProvider) {
        tag(LodestoneDamageTypeTags.CAN_TRIGGER_MAGIC).add(DamageTypeRegistry.SCYTHE_MELEE, DamageTypeRegistry.SCYTHE_SWEEP);
        tag(LodestoneDamageTypeTags.IS_MAGIC).add(DamageTypeRegistry.VOODOO);
        tag(DamageTypeTagRegistry.SOUL_SHATTER_DAMAGE).add(DamageTypeRegistry.SCYTHE_MELEE, DamageTypeRegistry.VOODOO, DamageTypeRegistry.SCYTHE_SWEEP, DamageTypeRegistry.HIDDEN_BLADE_COUNTER);
        tag(DamageTypeTagRegistry.IS_SCYTHE).add(DamageTypeRegistry.SCYTHE_MELEE, DamageTypeRegistry.SCYTHE_SWEEP, DamageTypeRegistry.HIDDEN_BLADE_COUNTER);
        tag(DamageTypeTagRegistry.IS_SCYTHE_MELEE).add(DamageTypeRegistry.SCYTHE_MELEE, DamageTypeRegistry.SCYTHE_SWEEP);

        tag(DamageTypeTagRegistry.SOUL_SHATTER_DAMAGE)
                .addOptional(ResourceLocation.parse("irons_spellbooks:blood_magic"))
                .addOptional(ResourceLocation.parse("irons_spellbooks:eldritch_magic"))
                .addOptional(ResourceLocation.parse("irons_spellbooks:ender_magic"))
                .addOptional(ResourceLocation.parse("irons_spellbooks:evocation_magic"))
                .addOptional(ResourceLocation.parse("irons_spellbooks:fire_magic"))
                .addOptional(ResourceLocation.parse("irons_spellbooks:holy_magic"))
                .addOptional(ResourceLocation.parse("irons_spellbooks:ice_magic"))
                .addOptional(ResourceLocation.parse("irons_spellbooks:lightning_magic"))
                .addOptional(ResourceLocation.parse("irons_spellbooks:nature_magic"));

    }
}
