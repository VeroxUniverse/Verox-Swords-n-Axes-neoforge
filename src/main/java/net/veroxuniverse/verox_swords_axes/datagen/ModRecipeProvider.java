package net.veroxuniverse.verox_swords_axes.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.veroxuniverse.verox_swords_axes.SwordsNAxes;
import net.veroxuniverse.verox_swords_axes.item.ArmorTier;
import net.veroxuniverse.verox_swords_axes.recipe.ForgingRecipe;
import net.veroxuniverse.verox_swords_axes.registry.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {

    /** Diamonds / netherite needed per piece: helmet, chestplate, leggings, boots. */
    private static final int[] DIAMONDS = {2, 4, 3, 2};
    private static final int[] NETHERITE = {1, 1, 1, 1};

    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.DUELLIST_HELMET.get())
                .pattern("IFI").pattern("I I")
                .define('I', Items.IRON_INGOT).define('F', Items.FEATHER)
                .unlockedBy("has_iron", has(Items.IRON_INGOT)).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.DUELLIST_CHESTPLATE.get())
                .pattern("I I").pattern("IWI").pattern("IWI")
                .define('I', Items.IRON_INGOT).define('W', Items.WHITE_WOOL)
                .unlockedBy("has_iron", has(Items.IRON_INGOT)).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.DUELLIST_LEGGINGS.get())
                .pattern("III").pattern("W W").pattern("I I")
                .define('I', Items.IRON_INGOT).define('W', Items.WHITE_WOOL)
                .unlockedBy("has_iron", has(Items.IRON_INGOT)).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.DUELLIST_BOOTS.get())
                .pattern("I I").pattern("L L")
                .define('I', Items.IRON_INGOT).define('L', Items.LEATHER)
                .unlockedBy("has_iron", has(Items.IRON_INGOT)).save(output);

        for (int i = 0; i < 4; i++) {
            forging(output, ModItems.duellistSet(ArmorTier.BASE).get(i).get(), Items.DIAMOND, DIAMONDS[i],
                    ModItems.duellistSet(ArmorTier.DIAMOND).get(i).get());
            forging(output, ModItems.duellistSet(ArmorTier.DIAMOND).get(i).get(), Items.NETHERITE_INGOT, NETHERITE[i],
                    ModItems.duellistSet(ArmorTier.NETHERITE).get(i).get());
        }

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.SHORT_SWORD.get())
                .pattern("I").pattern("S")
                .define('I', Items.IRON_INGOT).define('S', Items.STICK)
                .unlockedBy("has_iron", has(Items.IRON_INGOT)).save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.SMITHING_HAMMER.get())
                .pattern("IBI").pattern(" S ").pattern(" S ")
                .define('I', Items.IRON_INGOT).define('B', Items.IRON_BLOCK).define('S', Items.STICK)
                .unlockedBy("has_iron", has(Items.IRON_INGOT)).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModItems.FORGE_ANVIL.get())
                .pattern("III").pattern(" A ").pattern("SSS")
                .define('I', Items.IRON_INGOT).define('A', Items.ANVIL).define('S', Items.SMOOTH_STONE)
                .unlockedBy("has_anvil", has(Items.ANVIL)).save(output);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.WHETSTONE.get())
                .requires(Items.FLINT).requires(Items.SMOOTH_STONE)
                .unlockedBy("has_flint", has(Items.FLINT)).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FIRE_INLAY.get())
                .pattern(" B ").pattern("BGB").pattern(" B ")
                .define('B', Items.BLAZE_POWDER).define('G', Items.GOLD_INGOT)
                .unlockedBy("has_blaze_powder", has(Items.BLAZE_POWDER)).save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.IRON_PLATING.get())
                .pattern("ILI")
                .define('I', Items.IRON_INGOT).define('L', Items.LEATHER)
                .unlockedBy("has_iron", has(Items.IRON_INGOT)).save(output);
    }

    private static void forging(RecipeOutput output, ItemLike base, ItemLike addition, int count, ItemLike result) {
        String name = "forging/" + net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(result.asItem()).getPath();
        output.accept(SwordsNAxes.id(name),
                new ForgingRecipe(Ingredient.of(base), Ingredient.of(addition), count, new ItemStack(result)), null);
    }
}
