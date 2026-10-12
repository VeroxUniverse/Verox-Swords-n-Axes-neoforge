package net.veroxuniverse.verox_swords_axes.registry;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ModUpgradeItems {

    public record Entry(DeferredItem<Item> item, String name, String placeholderTexture, List<ItemLike> ingredients) {}

    private static final List<Entry> ENTRIES = new ArrayList<>();

    public static final DeferredItem<Item> SERRATED_WHETSTONE = upgrade("serrated_whetstone", "Serrated Whetstone", "item/flint", Rarity.COMMON,
            Items.FLINT, Items.IRON_INGOT);
    public static final DeferredItem<Item> WINDRUNNER_GRIP_WRAP = upgrade("windrunner_grip_wrap", "Windrunner Grip Wrap", "item/leather", Rarity.COMMON,
            Items.LEATHER, Items.PHANTOM_MEMBRANE);
    public static final DeferredItem<Item> BLADEDANCE_OIL = upgrade("bladedance_oil", "Bladedance Oil", "item/honey_bottle", Rarity.UNCOMMON,
            Items.GLASS_BOTTLE, Items.HONEYCOMB, Items.GOLD_NUGGET);
    public static final DeferredItem<Item> DUELISTS_PLUME = upgrade("duelists_plume", "Duelist's Plume", "item/feather", Rarity.COMMON,
            Items.FEATHER, Items.GOLD_INGOT, Items.WHITE_WOOL);
    public static final DeferredItem<Item> PARRY_PLAQUE = upgrade("parry_plaque", "Parry Plaque", "item/iron_ingot", Rarity.COMMON,
            Items.IRON_INGOT, Items.SHIELD, Items.LAPIS_LAZULI);
    public static final DeferredItem<Item> FRENZY_BLADE_EDGE = upgrade("frenzy_blade_edge", "Frenzy Blade Edge", "item/prismarine_shard", Rarity.COMMON,
            Items.PRISMARINE_SHARD, Items.IRON_INGOT, Items.REDSTONE);
    public static final DeferredItem<Item> FENCERS_STEP_SOLES = upgrade("fencers_step_soles", "Fencer's Step Soles", "item/rabbit_hide", Rarity.COMMON,
            Items.RABBIT_HIDE, Items.LEATHER, Items.GOLD_NUGGET);

    public static final DeferredItem<Item> BLOODFORGED_RIVET = upgrade("bloodforged_rivet", "Bloodforged Rivet", "item/quartz", Rarity.COMMON,
            Items.QUARTZ, Items.REDSTONE, Items.IRON_INGOT);
    public static final DeferredItem<Item> WRATHEMBER_CORE = upgrade("wrathember_core", "Wrathember Core", "item/magma_cream", Rarity.UNCOMMON,
            Items.MAGMA_CREAM, Items.NETHERRACK, Items.BLAZE_POWDER);
    public static final DeferredItem<Item> BONEBREAKER_INLAY = upgrade("bonebreaker_inlay", "Bonebreaker Inlay", "item/bone", Rarity.UNCOMMON,
            Items.BONE_BLOCK, Items.COPPER_INGOT);
    public static final DeferredItem<Item> WOUND_SEAL_PLATE = upgrade("wound_seal_plate", "Wound-Seal Plate", "item/glistering_melon_slice", Rarity.UNCOMMON,
            Items.GLISTERING_MELON_SLICE, Items.IRON_INGOT, Items.LEATHER);
    public static final DeferredItem<Item> HEAVY_IRON_WEDGE = upgrade("heavy_iron_wedge", "Heavy Iron Wedge", "item/iron_ingot", Rarity.COMMON,
            Items.IRON_BLOCK, Items.FLINT);
    public static final DeferredItem<Item> BUTCHERS_EDGE = upgrade("butchers_edge", "Butcher's Edge", "block/wither_rose", Rarity.COMMON,
            Items.WITHER_ROSE, Items.OBSIDIAN, Items.IRON_INGOT);
    public static final DeferredItem<Item> BERSERKER_SPIKE = upgrade("berserker_spike", "Berserker Spike", "item/pointed_dripstone", Rarity.COMMON,
            Items.POINTED_DRIPSTONE, Items.IRON_INGOT, Items.LEATHER);

    private ModUpgradeItems() {}

    /** Loads the class so the items are added to {@link ModItems#ITEMS} before registration. */
    public static void init() {}

    public static List<Entry> entries() {
        return Collections.unmodifiableList(ENTRIES);
    }

    private static DeferredItem<Item> upgrade(String id, String name, String placeholderTexture, Rarity rarity, ItemLike... ingredients) {
        DeferredItem<Item> item = ModItems.ITEMS.registerSimpleItem(id, new Item.Properties().rarity(rarity));
        ENTRIES.add(new Entry(item, name, placeholderTexture, List.of(ingredients)));
        return item;
    }
}