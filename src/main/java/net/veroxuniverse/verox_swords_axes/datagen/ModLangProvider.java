package net.veroxuniverse.verox_swords_axes.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.veroxuniverse.verox_swords_axes.SwordsNAxes;
import net.veroxuniverse.verox_swords_axes.registry.ModBlocks;
import net.veroxuniverse.verox_swords_axes.registry.ModItems;

public class ModLangProvider extends LanguageProvider {

    public ModLangProvider(PackOutput output) {
        super(output, SwordsNAxes.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        String mod = SwordsNAxes.MOD_ID;
        add("itemGroup." + mod, "Verox' Swords n' Axes");

        addItem(ModItems.DUELLIST_HELMET, "Duellist Helmet");
        addItem(ModItems.DUELLIST_CHESTPLATE, "Duellist Chestplate");
        addItem(ModItems.DUELLIST_LEGGINGS, "Duellist Leggings");
        addItem(ModItems.DUELLIST_BOOTS, "Duellist Boots");
        addItem(ModItems.DIAMOND_DUELLIST_HELMET, "Diamond Duellist Helmet");
        addItem(ModItems.DIAMOND_DUELLIST_CHESTPLATE, "Diamond Duellist Chestplate");
        addItem(ModItems.DIAMOND_DUELLIST_LEGGINGS, "Diamond Duellist Leggings");
        addItem(ModItems.DIAMOND_DUELLIST_BOOTS, "Diamond Duellist Boots");
        addItem(ModItems.NETHERITE_DUELLIST_HELMET, "Netherite Duellist Helmet");
        addItem(ModItems.NETHERITE_DUELLIST_CHESTPLATE, "Netherite Duellist Chestplate");
        addItem(ModItems.NETHERITE_DUELLIST_LEGGINGS, "Netherite Duellist Leggings");
        addItem(ModItems.NETHERITE_DUELLIST_BOOTS, "Netherite Duellist Boots");

        addItem(ModItems.SHORT_SWORD, "Short Sword");
        addItem(ModItems.SMITHING_HAMMER, "Smithing Hammer");
        addItem(ModItems.WHETSTONE, "Whetstone");
        addItem(ModItems.FIRE_INLAY, "Fire Inlay");
        addItem(ModItems.IRON_PLATING, "Iron Plating");
        addBlock(ModBlocks.FORGE_ANVIL, "Forge Anvil");

        add("tooltip." + mod + ".smithing_hammer", "Strike the Forge Anvil 3 times to forge or install upgrades");
        add("tooltip." + mod + ".smithing_hammer.remove", "Sneak + use: remove the last upgrade");

        String anvil = "message." + mod + ".forge_anvil.";
        add(anvil + "nothing", "Place an item and a material or upgrade on the anvil first");
        add(anvil + "no_upgrades", "This item has no upgrades installed");
        add(anvil + "no_slots", "This item has no upgrade slots");
        add(anvil + "full", "All upgrade slots are taken");
        add(anvil + "not_applicable", "This upgrade doesn't fit this item");
        add(anvil + "already_installed", "This upgrade is already installed");
        add(anvil + "invalid", "Nothing to forge with this");

    }
}
