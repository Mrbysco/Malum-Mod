package com.sammy.malum.data.recipe;

import com.sammy.malum.data.recipe.crafting.*;
import com.sammy.malum.data.recipe.infusion.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.*;
import net.minecraft.data.recipes.*;
import net.minecraft.data.recipes.packs.*;

import java.util.concurrent.CompletableFuture;

public class MalumRecipes extends VanillaRecipeProvider {

    MalumVanillaRecipeReplacements vanillaRecipeReplacements;

    public PackOutput pOutput;

    public MalumRecipes(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(pOutput, registries);
        this.pOutput = pOutput;
        this.vanillaRecipeReplacements = new MalumVanillaRecipeReplacements(pOutput, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput, Provider holderlookup) {
        vanillaRecipeReplacements.buildRecipes(recipeOutput);
        MalumVanillaRecipes.buildRecipes(recipeOutput);
        MalumWoodSetDatagen.buildRecipes(recipeOutput);
        MalumRockSetDatagen.buildRecipes(recipeOutput);

        ArtificeSpiritInfusionRecipes.buildRecipes(recipeOutput);
        CurioSpiritInfusionRecipes.buildRecipes(recipeOutput);
        GearSpiritInfusionRecipes.buildRecipes(recipeOutput);
        MaterialSpiritInfusionRecipes.buildRecipes(recipeOutput);
        TotemicSpiritInfusionRecipes.buildRecipes(recipeOutput);
        MiscSpiritInfusionRecipes.buildRecipes(recipeOutput);

        MalumRuneworkingRecipes.buildRecipes(recipeOutput);
        MalumSpiritFocusingRecipes.buildRecipes(recipeOutput);
        MalumSpiritRepairRecipes.buildRecipes(recipeOutput);
        MalumSpiritTransmutationRecipes.buildRecipes(recipeOutput);
        MalumVoidFavorRecipes.buildRecipes(recipeOutput);
    }
}
