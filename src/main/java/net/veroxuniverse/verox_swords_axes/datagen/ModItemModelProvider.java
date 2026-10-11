package net.veroxuniverse.verox_swords_axes.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;
import net.veroxuniverse.verox_swords_axes.SwordsNAxes;
import net.veroxuniverse.verox_swords_axes.registry.ModItems;

/**
 * PLACEHOLDERS: every item points to a vanilla texture for now.
 * Once the real sprites exist, replace the vanilla path with basicItem(...) / handheld(...) on textures/item/<name>.png.
 */
public class ModItemModelProvider extends ItemModelProvider {

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SwordsNAxes.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        placeholder(ModItems.DUELLIST_HELMET, "item/iron_helmet");
        placeholder(ModItems.DUELLIST_CHESTPLATE, "item/iron_chestplate");
        placeholder(ModItems.DUELLIST_LEGGINGS, "item/iron_leggings");
        placeholder(ModItems.DUELLIST_BOOTS, "item/iron_boots");
        placeholder(ModItems.DIAMOND_DUELLIST_HELMET, "item/diamond_helmet");
        placeholder(ModItems.DIAMOND_DUELLIST_CHESTPLATE, "item/diamond_chestplate");
        placeholder(ModItems.DIAMOND_DUELLIST_LEGGINGS, "item/diamond_leggings");
        placeholder(ModItems.DIAMOND_DUELLIST_BOOTS, "item/diamond_boots");
        placeholder(ModItems.NETHERITE_DUELLIST_HELMET, "item/netherite_helmet");
        placeholder(ModItems.NETHERITE_DUELLIST_CHESTPLATE, "item/netherite_chestplate");
        placeholder(ModItems.NETHERITE_DUELLIST_LEGGINGS, "item/netherite_leggings");
        placeholder(ModItems.NETHERITE_DUELLIST_BOOTS, "item/netherite_boots");

        handheldPlaceholder(ModItems.SHORT_SWORD, "item/iron_sword");

    }

    private void placeholder(DeferredItem<? extends Item> item, String vanillaTexture) {
        withExistingParent(item.getId().getPath(), mcLoc("item/generated")).texture("layer0", mcLoc(vanillaTexture));
    }

    private void handheldPlaceholder(DeferredItem<? extends Item> item, String vanillaTexture) {
        withExistingParent(item.getId().getPath(), mcLoc("item/handheld")).texture("layer0", mcLoc(vanillaTexture));
    }
}
