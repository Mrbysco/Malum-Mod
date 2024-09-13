package com.sammy.malum.registry.common;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.container.SpiritPouchContainerScreen;
import com.sammy.malum.client.screen.container.WeaversWorkbenchContainerScreen;
import com.sammy.malum.common.container.SpiritPouchContainer;
import com.sammy.malum.common.container.WeaversWorkbenchContainer;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.sammy.malum.MalumMod.MALUM;

@EventBusSubscriber(modid = MalumMod.MALUM, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ContainerRegistry {

    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(BuiltInRegistries.MENU, MALUM);

    public static final DeferredHolder<MenuType<SpiritPouchContainer>> SPIRIT_POUCH = CONTAINERS.register("spirit_pouch", () -> IForgeMenuType.create((int id, Inventory inv, FriendlyByteBuf extraData) -> new SpiritPouchContainer(id, inv, extraData.readItem())));
    public static final DeferredHolder<MenuType<WeaversWorkbenchContainer>> WEAVERS_WORKBENCH = CONTAINERS.register("weavers_workbench", () -> IForgeMenuType.create(WeaversWorkbenchContainer::new));


    @SubscribeEvent
    public static void bindContainerRenderers(FMLClientSetupEvent event) {
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () ->
        {
            MenuScreens.register(ContainerRegistry.SPIRIT_POUCH.get(), SpiritPouchContainerScreen::new);
            MenuScreens.register(ContainerRegistry.WEAVERS_WORKBENCH.get(), WeaversWorkbenchContainerScreen::new);
        });
    }
}