package net.veroxuniverse.verox_swords_axes.registry;

import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.veroxuniverse.verox_swords_axes.SwordsNAxes;

public final class ModTags {

    private ModTags() {}

    public static final class Items {
        /** Everything the whetstone, inlays & co. can go into. Other mods can add their weapons here. */
        public static final TagKey<Item> MELEE_WEAPONS = ItemTags.create(SwordsNAxes.id("melee_weapons"));
        /** Armor that accepts armor upgrades (plating, padding …). */
        public static final TagKey<Item> UPGRADEABLE_ARMOR = ItemTags.create(SwordsNAxes.id("upgradeable_armor"));
        /** Items that can be laid onto the forge anvil even without a forging recipe or upgrade slots. */
        public static final TagKey<Item> FORGEABLE = ItemTags.create(SwordsNAxes.id("forgeable"));
        public static final TagKey<Item> SMITHING_HAMMERS = ItemTags.create(SwordsNAxes.id("smithing_hammers"));

        private Items() {}
    }
}
