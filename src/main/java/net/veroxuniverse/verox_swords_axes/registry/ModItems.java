package net.veroxuniverse.verox_swords_axes.registry;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.veroxuniverse.verox_swords_axes.SwordsNAxes;
import net.veroxuniverse.verox_swords_axes.item.ArmorTier;
import net.veroxuniverse.verox_swords_axes.item.DuellistArmorItem;

import java.util.List;

public final class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SwordsNAxes.MOD_ID);

    public static final DeferredItem<DuellistArmorItem> DUELLIST_HELMET = duellist("duellist_helmet", ArmorItem.Type.HELMET, ArmorTier.BASE);
    public static final DeferredItem<DuellistArmorItem> DUELLIST_CHESTPLATE = duellist("duellist_chestplate", ArmorItem.Type.CHESTPLATE, ArmorTier.BASE);
    public static final DeferredItem<DuellistArmorItem> DUELLIST_LEGGINGS = duellist("duellist_leggings", ArmorItem.Type.LEGGINGS, ArmorTier.BASE);
    public static final DeferredItem<DuellistArmorItem> DUELLIST_BOOTS = duellist("duellist_boots", ArmorItem.Type.BOOTS, ArmorTier.BASE);

    public static final DeferredItem<DuellistArmorItem> DIAMOND_DUELLIST_HELMET = duellist("diamond_duellist_helmet", ArmorItem.Type.HELMET, ArmorTier.DIAMOND);
    public static final DeferredItem<DuellistArmorItem> DIAMOND_DUELLIST_CHESTPLATE = duellist("diamond_duellist_chestplate", ArmorItem.Type.CHESTPLATE, ArmorTier.DIAMOND);
    public static final DeferredItem<DuellistArmorItem> DIAMOND_DUELLIST_LEGGINGS = duellist("diamond_duellist_leggings", ArmorItem.Type.LEGGINGS, ArmorTier.DIAMOND);
    public static final DeferredItem<DuellistArmorItem> DIAMOND_DUELLIST_BOOTS = duellist("diamond_duellist_boots", ArmorItem.Type.BOOTS, ArmorTier.DIAMOND);

    public static final DeferredItem<DuellistArmorItem> NETHERITE_DUELLIST_HELMET = duellist("netherite_duellist_helmet", ArmorItem.Type.HELMET, ArmorTier.NETHERITE);
    public static final DeferredItem<DuellistArmorItem> NETHERITE_DUELLIST_CHESTPLATE = duellist("netherite_duellist_chestplate", ArmorItem.Type.CHESTPLATE, ArmorTier.NETHERITE);
    public static final DeferredItem<DuellistArmorItem> NETHERITE_DUELLIST_LEGGINGS = duellist("netherite_duellist_leggings", ArmorItem.Type.LEGGINGS, ArmorTier.NETHERITE);
    public static final DeferredItem<DuellistArmorItem> NETHERITE_DUELLIST_BOOTS = duellist("netherite_duellist_boots", ArmorItem.Type.BOOTS, ArmorTier.NETHERITE);

    public static final DeferredItem<SwordItem> SHORT_SWORD = ITEMS.register("short_sword",
            () -> new SwordItem(Tiers.IRON, new Item.Properties()
                    .attributes(SwordItem.createAttributes(Tiers.IRON, 2, -2.0F))));

    private ModItems() {}

    public static List<DeferredItem<DuellistArmorItem>> duellistSet(ArmorTier tier) {
        return switch (tier) {
            case BASE -> List.of(DUELLIST_HELMET, DUELLIST_CHESTPLATE, DUELLIST_LEGGINGS, DUELLIST_BOOTS);
            case DIAMOND -> List.of(DIAMOND_DUELLIST_HELMET, DIAMOND_DUELLIST_CHESTPLATE, DIAMOND_DUELLIST_LEGGINGS, DIAMOND_DUELLIST_BOOTS);
            case NETHERITE -> List.of(NETHERITE_DUELLIST_HELMET, NETHERITE_DUELLIST_CHESTPLATE, NETHERITE_DUELLIST_LEGGINGS, NETHERITE_DUELLIST_BOOTS);
        };
    }

    private static DeferredItem<DuellistArmorItem> duellist(String name, ArmorItem.Type type, ArmorTier tier) {
        return ITEMS.register(name, () -> {
            Item.Properties properties = new Item.Properties().durability(type.getDurability(durabilityFactor(tier)));
            if (tier == ArmorTier.NETHERITE) properties.fireResistant();
            return new DuellistArmorItem(material(tier), type, tier, properties);
        });
    }

    private static Holder<ArmorMaterial> material(ArmorTier tier) {
        return switch (tier) {
            case BASE -> ModArmorMaterials.DUELLIST_BASE;
            case DIAMOND -> ModArmorMaterials.DUELLIST_DIAMOND;
            case NETHERITE -> ModArmorMaterials.DUELLIST_NETHERITE;
        };
    }

    private static int durabilityFactor(ArmorTier tier) {
        return switch (tier) {
            case BASE -> 15;
            case DIAMOND -> 33;
            case NETHERITE -> 37;
        };
    }
}
