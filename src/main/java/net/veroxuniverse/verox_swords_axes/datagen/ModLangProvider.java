package net.veroxuniverse.verox_swords_axes.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.veroxuniverse.verox_swords_axes.SwordsNAxes;
import net.veroxuniverse.verox_swords_axes.registry.ModItems;
import net.veroxuniverse.verox_swords_axes.registry.ModCurioItems;
import net.veroxuniverse.verox_swords_axes.registry.ModUpgradeItems;

public class ModLangProvider extends LanguageProvider {

    public ModLangProvider(PackOutput output) {
        super(output, SwordsNAxes.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("itemGroup." + SwordsNAxes.MOD_ID, "Verox' Swords n' Axes");

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

        for (ModUpgradeItems.Entry upgrade : ModUpgradeItems.entries()) {
            addItem(upgrade.item(), upgrade.name());
        }
        for (ModCurioItems.Entry curio : ModCurioItems.entries()) {
            addItem(curio.item(), curio.name());
        }
    }
}
