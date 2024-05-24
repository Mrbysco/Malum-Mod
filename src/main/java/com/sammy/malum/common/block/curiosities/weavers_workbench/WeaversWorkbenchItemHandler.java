package com.sammy.malum.common.block.curiosities.weavers_workbench;

import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class WeaversWorkbenchItemHandler extends ItemStackHandler {

    public final WeaversWorkbenchBlockEntity entity;
    public final int outputs;
    public boolean isCrafting;

    public WeaversWorkbenchItemHandler(int size, int outputs, WeaversWorkbenchBlockEntity blockEntity) {
        super(size + outputs);
        this.entity = blockEntity;
        this.outputs = outputs;
    }

    @Override
    public void onContentsChanged(int slot) {
        if (!isCrafting) {
            this.entity.setChanged();
            if (slot < 2) {
                this.entity.tryCraft();
            }
        }
    }
}