package com.sammy.malum.client.screen.codex.pages.recipe;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.screen.codex.screens.*;
import com.sammy.malum.client.screen.codex.pages.*;
import com.sammy.malum.common.recipe.SpiritInfusionRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.Item;

import java.util.function.Predicate;

import static com.sammy.malum.client.screen.codex.ArcanaCodexHelper.*;

public class SpiritInfusionPage extends BookPage {
    private final SpiritInfusionRecipe recipe;

    public SpiritInfusionPage(Predicate<SpiritInfusionRecipe> predicate) {
        super(MalumMod.malumPath("textures/gui/book/pages/spirit_infusion_page.png"));
        this.recipe = SpiritInfusionRecipe.getRecipe(Minecraft.getInstance().level, predicate);
    }

    public SpiritInfusionPage(SpiritInfusionRecipe recipe) {
        super(MalumMod.malumPath("textures/gui/book/pages/spirit_infusion_page.png"));
        this.recipe = recipe;
    }

    public static SpiritInfusionPage fromInput(Item inputItem) {
        return new SpiritInfusionPage(s -> s.doesInputMatch(inputItem.getDefaultInstance()));
    }

    public static SpiritInfusionPage fromOutput(Item outputItem) {
        return new SpiritInfusionPage(s -> s.doesOutputMatch(outputItem.getDefaultInstance()));
    }

    @Override
    public boolean isValid() {
        return recipe != null;
    }

    @Override
    public void render(EntryScreen screen, GuiGraphics guiGraphics, int left, int top, int mouseX, int mouseY, float partialTicks, boolean isRepeat) {
        Runnable renderSpirits = renderBufferedComponents(screen, guiGraphics, recipe.spirits, left + 15, top + 59, mouseX, mouseY, true);
        if (!recipe.extraItems.isEmpty()) {
            renderComponents(screen, guiGraphics, recipe.extraItems, left + 107, top + 59, mouseX, mouseY, true);
        }
        renderSpirits.run();
        renderComponent(screen, guiGraphics, recipe.input, left + 63, top + 59, mouseX, mouseY);
        renderItem(screen, guiGraphics, recipe.output, left + 63, top + 126, mouseX, mouseY);
    }
}
