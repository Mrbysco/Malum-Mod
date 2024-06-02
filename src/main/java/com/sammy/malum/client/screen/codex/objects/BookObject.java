package com.sammy.malum.client.screen.codex.objects;

import com.sammy.malum.client.screen.codex.screens.AbstractMalumScreen;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

import static com.sammy.malum.MalumMod.malumPath;

public class BookObject<T extends AbstractMalumScreen> {

    public static final ResourceLocation WIDGET_FADE_TEXTURE = malumPath("textures/gui/book/widget_fade.png");

    public final int posX;
    public final int posY;
    public final int width;
    public final int height;

    public boolean isHoveredOver;
    public float xOffset;
    public float yOffset;

    public BookObject(int posX, int posY, int width, int height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    public boolean isValid(T screen) {
        return true;
    }

    public void render(T screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {

    }

    public void renderLate(T screen, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {

    }

    public void click(T screen, double mouseX, double mouseY) {

    }

    public void exit(T screen) {

    }

    public boolean isHovering(T screen, float offsetX, float offsetY, double mouseX, double mouseY) {
        return screen.isHovering(mouseX, mouseY, posX + offsetX, posY + offsetY, width, height);
    }

    public int getOffsetXPosition() {
        return (int) (posX + xOffset);
    }

    public int getOffsetYPosition() {
        return (int) (posY + yOffset);
    }
}