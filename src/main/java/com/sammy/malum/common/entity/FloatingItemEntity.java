package com.sammy.malum.common.entity;

import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FloatingItemEntity extends FloatingEntity {

    private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(FloatingItemEntity.class, EntityDataSerializers.ITEM_STACK);

    public ItemStack itemStack = ItemStack.EMPTY;

    public FloatingItemEntity(EntityType<? extends FloatingEntity> type, Level level) {
        super(type, level);
    }

    public void setItem(ItemStack pStack) {
        if (!pStack.is(this.getDefaultItem()) || pStack.hasTag()) {
            this.getEntityData().set(DATA_ITEM_STACK, pStack);
        }
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_ITEM_STACK, ItemStack.EMPTY);
        super.defineSynchedData();
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_ITEM_STACK.equals(pKey)) {
            itemStack = getEntityData().get(DATA_ITEM_STACK);
        }
        super.onSyncedDataUpdated(pKey);
    }

    protected ItemStack getItemRaw() {
        return this.getEntityData().get(DATA_ITEM_STACK);
    }

    protected Item getDefaultItem() {
        return ItemRegistry.ARCANE_SPIRIT.get();
    }

    public ItemStack getItem() {
        ItemStack itemstack = this.getItemRaw();
        return itemstack.isEmpty() ? new ItemStack(this.getDefaultItem()) : itemstack;
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        ItemStack itemstack = this.getItemRaw();
        if (!itemstack.isEmpty()) {
            pCompound.put("Item", itemstack.save(new CompoundTag()));
        }

    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        ItemStack itemstack = ItemStack.of(pCompound.getCompound("Item"));
        this.setItem(itemstack);
    }
}