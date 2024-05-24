package com.sammy.malum.registry.client;

import com.sammy.malum.*;
import com.sammy.malum.common.recipe.*;
import net.minecraft.client.*;
import net.minecraft.client.player.*;
import net.minecraft.world.item.*;
import team.lodestar.lodestone.handlers.screenparticle.*;

import java.util.*;

import static com.sammy.malum.visual_effects.ScreenParticleEffects.VoidTransmutableParticleEffect.*;

public class ParticleEmitterRegistry {

    public static boolean registeredVoidParticleEmitters = false;

    @SubscribeEvent
    public static void addParticleEmitters(EntityJoinLevelEvent event) {
        if (!registeredVoidParticleEmitters) {
            if (event.getEntity() instanceof AbstractClientPlayer player && player.equals(Minecraft.getInstance().player)) {
                final List<FavorOfTheVoidRecipe> recipes = FavorOfTheVoidRecipe.getRecipes(player.level());
                for (FavorOfTheVoidRecipe recipe : recipes) {
                    for (ItemStack item : recipe.input.getItems()) {
                        ParticleEmitterHandler.registerItemParticleEmitter(item.getItem(), INSTANCE);
                    }
                }
                registeredVoidParticleEmitters = true;
            }
        }
    }
}
