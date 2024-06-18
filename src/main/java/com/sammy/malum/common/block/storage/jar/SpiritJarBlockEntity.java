package com.sammy.malum.common.block.storage.jar;

import com.sammy.malum.common.item.curiosities.SpiritPouchItem;
import com.sammy.malum.common.item.spirit.SpiritShardItem;
import com.sammy.malum.core.handlers.SpiritHarvestHandler;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import com.sammy.malum.visual_effects.SpiritLightSpecs;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import io.github.fabricators_of_create.porting_lib.util.LazyOptional;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.registry.common.particle.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;
import team.lodestar.lodestone.systems.container.ItemInventory;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;

import java.awt.*;
import java.util.UUID;

public class SpiritJarBlockEntity extends LodestoneBlockEntity {

    public SpiritJarBlockEntity(BlockEntityType<? extends SpiritJarBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public SpiritJarBlockEntity(BlockPos pos, BlockState state) {
        this(BlockEntityRegistry.SPIRIT_JAR.get(), pos, state);
    }

    public MalumSpiritType type;
    public int count;

    // Storage Drawers moment
    private long lastClickTime;
    private UUID lastClickUUID;

    public final LazyOptional<ItemStackHandler> inventory = LazyOptional.of(() -> new ItemStackHandler() {
        @Override
        public int getSlotCount() {
            return 2;
        }

        @NotNull
        @Override
        public ItemStack getStackInSlot(int slot) {
            if (slot == 0 && type != null) {
                return new ItemStack(type.spiritShard.get(), count); // Yes, this can create a stack bigger than 64. It's fine.
            } else
                return ItemStack.EMPTY;
        }

        @Override
        public long insertSlot(int slot, ItemVariant resource, long maxAmount, TransactionContext transaction) {
            if (slot == 1 && resource.getItem() instanceof SpiritShardItem spiritItem && (type == null || spiritItem.type == type)) {
                if (type == null)
                    type = spiritItem.type;
                count += resource.toStack().getCount();
                if (!level.isClientSide) {
                    BlockHelper.updateAndNotifyState(level, worldPosition);
                }
            }

            return super.insertSlot(slot, resource, maxAmount, transaction);
        }


        @Override
        public int getSlotLimit(int slot) {
            if (slot == 0)
                return Math.min(64, count);
            return 64;
        }

        @Override
        public boolean isItemValid(int slot, ItemVariant resource, int count) {
            return resource.getItem() instanceof SpiritShardItem spiritItem && spiritItem.type == type;
        }
    });


    public ItemStack extractItem(int slot, int amount) {
        if (slot != 0 || count <= 0)
            return ItemStack.EMPTY;
        MalumSpiritType extractedType = type;
        if (extractedType == null)
            return ItemStack.EMPTY;

        int amountToExtract = Math.min(count, amount);
        count -= amountToExtract;
        if (count == 0) {
            type = null;
        }
        if (!level.isClientSide) {
            BlockHelper.updateAndNotifyState(level, worldPosition);
        }

        return new ItemStack(extractedType.spiritShard.get(), amountToExtract);
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        if (getLevel() == null)
            return InteractionResult.PASS;

        int count;
        if (getLevel().getGameTime() - lastClickTime < 10 && player.getUUID().equals(lastClickUUID)) {
            count = insertAllSpirits(player);
        } else {
            count = insertHeldItem(player);
        }

        lastClickTime = getLevel().getGameTime();
        lastClickUUID = player.getUUID();

        if (count != 0) {
            if (player.level().isClientSide) {
                spawnUseParticles(level, worldPosition, type);
            } else {
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        }

        return InteractionResult.sidedSuccess(player.level().isClientSide);
    }

    public int insertHeldItem(Player player) {
        int count = 0;
        ItemStack playerStack = player.getInventory().getSelected();
        if (!playerStack.isEmpty())
            count = insertFromStack(playerStack);

        return count;
    }

    public int insertAllSpirits(Player player) {
        if (type == null)
            return 0;

        int count = 0;
        for (int i = 0, n = player.getInventory().getContainerSize(); i < n; i++) {
            ItemStack subStack = player.getInventory().getItem(i);
            if (!subStack.isEmpty()) {
                int subCount = insertFromStack(subStack);
                if (subCount > 0 && subStack.getCount() == 0)
                    player.getInventory().setItem(i, ItemStack.EMPTY);

                count += subCount;
            }
        }

        return count;
    }

    public int insertFromStack(ItemStack stack) {
        int inserted = 0;
        if (stack.getItem() instanceof SpiritPouchItem) {
            if (type != null) {
                ItemInventory inventory = SpiritPouchItem.getInventory(stack);
                for (int i = 0; i < inventory.getContainerSize(); i++) {
                    ItemStack spiritStack = inventory.getItem(i);
                    if (spiritStack.getItem() instanceof SpiritShardItem spiritItem) {
                        MalumSpiritType type = spiritItem.type;
                        if (type.identifier.equals(this.type.identifier)) {
                            inventory.setItem(i, ItemStack.EMPTY);
                            inserted += spiritStack.getCount();
                            count += spiritStack.getCount();
                        }
                    }
                }
            }
        } else if (stack.getItem() instanceof SpiritShardItem spiritSplinterItem) {
            if (type == null || type.equals(spiritSplinterItem.type)) {
                type = spiritSplinterItem.type;
                inserted += stack.getCount();
                count += stack.getCount();
                stack.shrink(stack.getCount());
            }
        }
        return inserted;

    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onPlace(LivingEntity placer, ItemStack stack) {
        if (stack.hasTag()) {
            load(stack.getTag());
        }
        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        if (type != null) {
            compound.putString("spirit", type.identifier);
        }
        compound.putInt("count", count);
    }

    @Override
    public void load(@NotNull CompoundTag compound) {
        if (compound.contains("spirit")) {
            type = SpiritHarvestHandler.getSpiritType(compound.getString("spirit"));
        } else {
            type = null;
        }
        count = compound.getInt("count");
        super.load(compound);
    }

    @Override
    public void tick() {
        if (level.isClientSide) {
            if (type != null) {
                SpiritLightSpecs.rotatingLightSpecs(level, getItemPos(), type, 0.4f, 3);
            }
        }
    }

    public Vec3 getItemPos() {
        double time = (level.getGameTime() * 0.05f) % 6.28f;
        double x = getBlockPos().getX() + 0.5f;
        double y = getBlockPos().getY() + 0.5f + (float) Math.sin(time) * 0.2f;
        double z = getBlockPos().getZ() + 0.5f;
        return new Vec3(x, y, z);
    }

    @Environment(EnvType.CLIENT)
    public void spawnUseParticles(Level level, BlockPos pos, MalumSpiritType type) {
        Color color = type.getPrimaryColor();
        WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                .setTransparencyData(GenericParticleData.create(0.15f, 0f).build())
                .setScaleData(GenericParticleData.create(0.3f, 0).build())
                .setSpinData(SpinParticleData.create(0.2f).build())
                .setColorData(ColorParticleData.create(color, color.darker()).build())
                .setLifetime(20)
                .setRandomMotion(0.02f)
                .setRandomOffset(0.1f, 0.1f)
                .enableNoClip()
                .repeat(level, pos.getX() + 0.5f, pos.getY() + 0.5f + Math.sin(level.getGameTime() / 20f) * 0.2f, pos.getZ() + 0.5f, 10);
    }
}
