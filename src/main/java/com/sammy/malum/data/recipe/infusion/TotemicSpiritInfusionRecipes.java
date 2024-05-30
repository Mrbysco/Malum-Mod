package com.sammy.malum.data.recipe.infusion;

import com.sammy.malum.data.recipe.builder.SpiritInfusionRecipeBuilder;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

import static com.sammy.malum.registry.common.SpiritTypeRegistry.*;

public class TotemicSpiritInfusionRecipes {

    public static void buildRecipes(Consumer<FinishedRecipe> consumer) {
        new SpiritInfusionRecipeBuilder(ItemRegistry.RUNEWOOD_LOG.get(), 4, ItemRegistry.RUNEWOOD_TOTEM_BASE.get(), 4)
                .addExtraItem(ItemRegistry.RUNEWOOD_PLANKS.get(), 6)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 2)
                .addSpirit(AERIAL_SPIRIT, 2)
                .addSpirit(AQUEOUS_SPIRIT, 2)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .addSpirit(INFERNAL_SPIRIT, 2)
                .addSpirit(ELDRITCH_SPIRIT, 1)
                .build(consumer);

        new SpiritInfusionRecipeBuilder(ItemRegistry.SOULWOOD_LOG.get(), 4, ItemRegistry.SOULWOOD_TOTEM_BASE.get(), 4)
                .addExtraItem(ItemRegistry.SOULWOOD_PLANKS.get(), 6)
                .addExtraItem(ItemRegistry.HEX_ASH.get(), 2)
                .addSpirit(AERIAL_SPIRIT, 2)
                .addSpirit(AQUEOUS_SPIRIT, 2)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .addSpirit(INFERNAL_SPIRIT, 2)
                .addSpirit(ELDRITCH_SPIRIT, 1)
                .build(consumer);
    }
}
