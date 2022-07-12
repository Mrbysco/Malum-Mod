package com.sammy.malum.compability.jei.categories;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.recipe.SpiritTransmutationRecipe;
import com.sammy.malum.compability.jei.JEIHandler;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

import static com.sammy.malum.MalumMod.malumPath;

public class BlockTransmutationRecipeCategory implements IRecipeCategory<SpiritTransmutationRecipe> {
    public static final ResourceLocation UID = malumPath("block_transmutation");
    private final IDrawable background;
    private final IDrawable overlay;
    private final IDrawable icon;

    public BlockTransmutationRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(142, 83);
        overlay = guiHelper.createDrawable(new ResourceLocation(MalumMod.MALUM, "textures/gui/block_transmutation_jei.png"), 0, 0, 140, 81);
        icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ItemRegistry.SOULWOOD_TOTEM_BASE.get()));
    }

    @Override
    public void draw(SpiritTransmutationRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
        overlay.draw(poseStack);
    }

    @Override
    public RecipeType<SpiritTransmutationRecipe> getRecipeType() {
        return JEIHandler.TRANSMUTATION;
    }

    @Nonnull
    @Override
    @SuppressWarnings("removal")
    public ResourceLocation getUid() {
        return UID;
    }

    @Nonnull
    @Override
    @SuppressWarnings("removal")
    public Class<? extends SpiritTransmutationRecipe> getRecipeClass() {
        return SpiritTransmutationRecipe.class;
    }

    @Override
    public Component getTitle() {
        return new TranslatableComponent("malum.jei." + UID.getPath());
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Nonnull
    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SpiritTransmutationRecipe recipe, IFocusGroup focuses) {
       builder.addSlot(RecipeIngredientRole.INPUT, 28, 27)
            .addItemStack(recipe.input.getStack().getItem().getDefaultInstance());
       builder.addSlot(RecipeIngredientRole.OUTPUT, 93, 26)
            .addItemStack(recipe.output.getStack().getItem().getDefaultInstance());
    }
}
