package com.sammy.malum.mixin.client;

import com.sammy.malum.common.entity.EntityModelLoader;
import com.sammy.malum.registry.client.ScreenParticleRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Unique
    EntityModelLoader modelLoader;

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/resources/ReloadableResourceManager;registerReloadListener(Lnet/minecraft/server/packs/resources/PreparableReloadListener;)V", ordinal = 17))
    private void lodestone$registerParticleFactories(GameConfig gameConfig, CallbackInfo ci) {
        ScreenParticleRegistry.registerParticleFactory();
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void malum$registerModel(GameConfig gameConfig, CallbackInfo ci) {
        modelLoader = new EntityModelLoader();
    }
}