package net.veroxuniverse.verox_swords_axes.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.veroxuniverse.verox_swords_axes.registry.ModBlocks;
import net.veroxuniverse.verox_swords_axes.registry.ModRecipes;

/**
 * Forge anvil recipe: base item + addition (count) → result.
 * The result keeps every component of the base (damage, upgrades, dye colors, enchantments, name …).
 */
public record ForgingRecipe(Ingredient base, Ingredient addition, int additionCount, ItemStack result) implements Recipe<ForgingRecipeInput> {

    @Override
    public boolean matches(ForgingRecipeInput input, Level level) {
        return this.base.test(input.base())
                && this.addition.test(input.addition())
                && input.addition().getCount() >= this.additionCount;
    }

    @Override
    public ItemStack assemble(ForgingRecipeInput input, HolderLookup.Provider registries) {
        ItemStack forged = input.base().transmuteCopy(this.result.getItem(), this.result.getCount());
        forged.applyComponents(this.result.getComponentsPatch());
        return forged;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return this.result;
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(ModBlocks.FORGE_ANVIL.get());
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.FORGING_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.FORGING_TYPE.get();
    }

    public static final class Serializer implements RecipeSerializer<ForgingRecipe> {

        private static final MapCodec<ForgingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("base").forGetter(ForgingRecipe::base),
                Ingredient.CODEC_NONEMPTY.fieldOf("addition").forGetter(ForgingRecipe::addition),
                Codec.intRange(1, 64).optionalFieldOf("addition_count", 1).forGetter(ForgingRecipe::additionCount),
                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(ForgingRecipe::result)
        ).apply(instance, ForgingRecipe::new));

        private static final StreamCodec<RegistryFriendlyByteBuf, ForgingRecipe> STREAM_CODEC = StreamCodec.composite(
                Ingredient.CONTENTS_STREAM_CODEC, ForgingRecipe::base,
                Ingredient.CONTENTS_STREAM_CODEC, ForgingRecipe::addition,
                ByteBufCodecs.VAR_INT, ForgingRecipe::additionCount,
                ItemStack.STREAM_CODEC, ForgingRecipe::result,
                ForgingRecipe::new);

        @Override
        public MapCodec<ForgingRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ForgingRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
