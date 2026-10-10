package net.veroxuniverse.verox_swords_axes.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.veroxuniverse.verox_swords_axes.SwordsNAxes;
import net.veroxuniverse.verox_swords_axes.recipe.ForgingRecipe;

public final class ModRecipes {

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, SwordsNAxes.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, SwordsNAxes.MOD_ID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<ForgingRecipe>> FORGING_TYPE =
            RECIPE_TYPES.register("forging", () -> RecipeType.simple(SwordsNAxes.id("forging")));
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ForgingRecipe>> FORGING_SERIALIZER =
            RECIPE_SERIALIZERS.register("forging", ForgingRecipe.Serializer::new);

    private ModRecipes() {}
}
