package com.sammy.malum.client.screen.codex.screens;

import com.sammy.malum.client.screen.codex.ArcanaCodexHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

import java.util.function.Supplier;

public abstract class AbstractMalumScreen extends Screen {

    protected final Supplier<SoundEvent> sweetenerSound;

    protected AbstractMalumScreen(Component pTitle, Supplier<SoundEvent> sweetenerSound) {
        super(pTitle);
        this.sweetenerSound = sweetenerSound;
    }

    public boolean isHovering(double mouseX, double mouseY, float posX, float posY, int width, int height) {
        return ArcanaCodexHelper.isHovering(mouseX, mouseY, posX, posY, width, height);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (minecraft.options.keyInventory.matches(keyCode, scanCode)) {
            onClose();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    public void openScreen(boolean silentMouseInput) {
        Minecraft.getInstance().setScreen(this);
    }

    public void playPageFlipSound(Supplier<SoundEvent> soundEvent, float pitch) {
        playSound(soundEvent, 1f, Math.max(1, pitch * 0.8f));
        playSound(sweetenerSound, 1f, pitch);
    }

    public void playSweetenedSound(Supplier<SoundEvent> soundEvent, float sweetenerPitch) {
        playSound(soundEvent, 1f, 1);
        playSound(sweetenerSound, 1f, sweetenerPitch);
    }

    public void playSound(Supplier<SoundEvent> soundEvent, float volume, float pitch) {
        Minecraft.getInstance().player.playNotifySound(soundEvent.get(), SoundSource.PLAYERS, volume, pitch);
    }
}