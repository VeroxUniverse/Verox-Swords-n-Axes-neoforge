package net.veroxuniverse.verox_swords_axes.registry;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredItem;
import net.veroxuniverse.unbound_lib.curio.StatCurioItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ModCurioItems {

    public record Entry(DeferredItem<StatCurioItem> item, String name, String slot, String placeholderTexture, List<ItemLike> ingredients) {}

    private static final List<Entry> ENTRIES = new ArrayList<>();

    public static final DeferredItem<StatCurioItem> FENCERS_BAND = curio("fencers_band", "Fencer's Band", "ring", "item/gold_nugget",
            Items.GOLD_INGOT, Items.QUARTZ);
    public static final DeferredItem<StatCurioItem> TABARD_OF_VIGILANCE = curio("tabard_of_vigilance", "Tabard of Vigilance", "body", "item/leather_chestplate",
            Items.YELLOW_WOOL, Items.BLUE_WOOL, Items.IRON_INGOT);
    public static final DeferredItem<StatCurioItem> DANCERS_BELT = curio("dancers_belt", "Dancer's Belt", "belt", "item/leather",
            Items.LEATHER, Items.STRING, Items.GOLD_INGOT);
    public static final DeferredItem<StatCurioItem> BUTCHERS_CHAIN = curio("butchers_chain", "Butcher's Chain", "necklace", "item/chain",
            Items.CHAIN, Items.BONE, Items.REDSTONE);
    public static final DeferredItem<StatCurioItem> BELT_OF_PRIMAL_FORCE = curio("belt_of_primal_force", "Belt of Primal Force", "belt", "item/magma_cream",
            Items.LEATHER, Items.NETHERITE_SCRAP, Items.MAGMA_CREAM);
    public static final DeferredItem<StatCurioItem> WRATH_BRACER = curio("wrath_bracer", "Wrath Bracer", "hands", "item/blaze_powder",
            Items.LEATHER, Items.IRON_INGOT, Items.BLAZE_POWDER);

    private ModCurioItems() {}

    /** Loads the class so the items are added to {@link ModItems#ITEMS} before registration. */
    public static void init() {}

    public static List<Entry> entries() {
        return Collections.unmodifiableList(ENTRIES);
    }

    private static DeferredItem<StatCurioItem> curio(String id, String name, String slot, String placeholderTexture, ItemLike... ingredients) {
        DeferredItem<StatCurioItem> item = ModItems.ITEMS.register(id,
                () -> new StatCurioItem(new Item.Properties().rarity(Rarity.UNCOMMON)));
        ENTRIES.add(new Entry(item, name, slot, placeholderTexture, List.of(ingredients)));
        return item;
    }
}
