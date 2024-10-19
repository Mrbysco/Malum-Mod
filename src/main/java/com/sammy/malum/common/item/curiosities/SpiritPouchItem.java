package com.sammy.malum.common.item.curiosities;

import com.sammy.malum.common.container.SpiritPouchContainer;
import com.sammy.malum.common.item.spirit.SpiritShardItem;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.network.NetworkHooks;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.container.ItemInventory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.stream.Stream;

public class SpiritPouchItem extends Item {

    public SpiritPouchItem(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new InventoryCapability(stack);
    }

    @Override
    public void onDestroyed(ItemEntity pItemEntity) {
        Iterator<ItemStack> iter = new Iterator<>() {
            private int i = 0;
            private final ItemInventory inventory = getInventory(pItemEntity.getItem());

            @Override
            public boolean hasNext() {
                return i < inventory.getContainerSize();
            }

            @Override
            public ItemStack next() {
                return inventory.getItem(i++);
            }
        };

        ItemUtils.onContainerDestroyed(pItemEntity, Stream.iterate(iter.next(), t -> iter.hasNext(), t -> iter.next()));
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack pStack, Slot pSlot, ClickAction pAction, Player pPlayer) {
        ItemStack stack = pSlot.getItem();

        if (pAction != ClickAction.SECONDARY || !(stack.getItem() instanceof SpiritShardItem)) {
            return false;
        } else {
            ItemInventory inventory = getInventory(pStack);

            if (!stack.isEmpty() && stack.getItem().canFitInsideContainerItems()) {
                ItemStack toInsert = pSlot.safeTake(stack.getCount(), Integer.MAX_VALUE, pPlayer);
                ItemStack remainder = inventory.addItem(toInsert);
                pSlot.set(remainder);
                if (remainder.getCount() != toInsert.getCount())
                    pPlayer.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + pPlayer.level().getRandom().nextFloat() * 0.4F);
            }

            return true;
        }
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack pStack, ItemStack pOther, Slot pSlot, ClickAction pAction, Player pPlayer, SlotAccess pAccess) {
        if (pAction != ClickAction.SECONDARY || !pSlot.allowModification(pPlayer) || !(pOther.getItem() instanceof SpiritShardItem)) {
            return false;
        } else {
            ItemInventory inventory = getInventory(pStack);

            if (!pOther.isEmpty() && pOther.getItem().canFitInsideContainerItems()) {
                ItemStack remainder = inventory.addItem(pOther.copy());
                if (pOther.getCount() != remainder.getCount())
                    pPlayer.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + pPlayer.level().getRandom().nextFloat() * 0.4F);

                pOther.shrink(pOther.getCount() - remainder.getCount());
            }

            return true;
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player playerIn, InteractionHand handIn) {
        if (!level.isClientSide) {
            ItemStack stack = playerIn.getItemInHand(handIn);
            MenuProvider container = new SimpleMenuProvider((w, p, pl) -> new SpiritPouchContainer(w, p, stack), stack.getHoverName());
            NetworkHooks.openScreen((ServerPlayer) playerIn, container, b -> b.writeItem(stack));
            SoundHelper.playSound(playerIn, SoundEvents.ARMOR_EQUIP_LEATHER, 1, 1);
        }
        return InteractionResultHolder.success(playerIn.getItemInHand(handIn));
    }

    private static class InventoryCapability implements ICapabilityProvider {
        private final LazyOptional<IItemHandler> opt;

        public InventoryCapability(ItemStack stack) {
            opt = LazyOptional.of(() -> new InvWrapper(getInventory(stack)));
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
            return ForgeCapabilities.ITEM_HANDLER.orEmpty(capability, opt);
        }
    }

    public static ItemInventory getInventory(ItemStack stack) {
        return new ItemInventory(stack, 27) {
            @Override
            public boolean canPlaceItem(int pIndex, ItemStack pStack) {
                return pStack.getItem() instanceof SpiritShardItem;
            }
        };
    }
}
