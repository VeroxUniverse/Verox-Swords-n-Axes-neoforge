package net.veroxuniverse.verox_swords_axes;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.veroxuniverse.verox_swords_axes.registry.ModArmorMaterials;
import net.veroxuniverse.verox_swords_axes.registry.ModBlockEntities;
import net.veroxuniverse.verox_swords_axes.registry.ModBlocks;
import net.veroxuniverse.verox_swords_axes.registry.ModCreativeTabs;
import net.veroxuniverse.verox_swords_axes.registry.ModItems;
import net.veroxuniverse.verox_swords_axes.registry.ModRecipes;
import org.slf4j.Logger;

@Mod(SwordsNAxes.MOD_ID)
public class SwordsNAxes {
    public static final String MOD_ID = "verox_swords_axes";
    public static final Logger LOGGER = LogUtils.getLogger();

    public SwordsNAxes(IEventBus modEventBus, ModContainer modContainer) {
        ModArmorMaterials.ARMOR_MATERIALS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        ModRecipes.RECIPE_TYPES.register(modEventBus);
        ModRecipes.RECIPE_SERIALIZERS.register(modEventBus);
        ModCreativeTabs.TABS.register(modEventBus);
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
