package com.sammy.malum.common.item.codex;

import com.sammy.malum.client.screen.codex.screens.ArcanaProgressionScreen;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EncyclopediaArcanaItem extends Item {

    public EncyclopediaArcanaItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide) {
            final ItemStack stack = player.getItemInHand(hand);
            ArcanaProgressionScreen.openCodexViaItem(false);
            player.swing(hand);
            return InteractionResultHolder.success(stack);
        }
        return super.use(level, player, hand);
    }
}