package net.veroxuniverse.verox_swords_axes.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider.TagLookup;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;
import net.veroxuniverse.unbound_lib.tag.LibItemTags;
import net.veroxuniverse.verox_swords_axes.SwordsNAxes;
import net.veroxuniverse.verox_swords_axes.item.ArmorTier;
import net.veroxuniverse.verox_swords_axes.item.DuellistArmorItem;
import net.veroxuniverse.verox_swords_axes.registry.ModItems;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                               CompletableFuture<TagLookup<Block>> blockTags,
                               @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, SwordsNAxes.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ItemTags.SWORDS).add(ModItems.SHORT_SWORD.get());
        tag(LibItemTags.MELEE_WEAPONS).add(ModItems.SHORT_SWORD.get());

        for (ArmorTier tier : ArmorTier.values()) {
            for (DeferredItem<DuellistArmorItem> piece : ModItems.duellistSet(tier)) {
                DuellistArmorItem item = piece.get();
                tag(LibItemTags.UPGRADEABLE_ARMOR).add(item);
                tag(vanillaArmorTag(item.getType())).add(item);
                tag(ItemTags.TRIMMABLE_ARMOR).add(item);
            }
        }
    }

    private static TagKey<Item> vanillaArmorTag(ArmorItem.Type type) {
        return switch (type) {
            case HELMET -> ItemTags.HEAD_ARMOR;
            case CHESTPLATE, BODY -> ItemTags.CHEST_ARMOR;
            case LEGGINGS -> ItemTags.LEG_ARMOR;
            case BOOTS -> ItemTags.FOOT_ARMOR;
        };
    }
}
